package com.christian.adventureengine.rendering.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.rendering.Camera;
import com.christian.adventureengine.rendering.sprites.ISpriteManager;
import com.christian.adventureengine.rendering.sprites.ISpriteType;
import com.christian.adventureengine.rendering.sprites.Sprite;

public class SpriteManager implements ISpriteManager {
	private HashMap<ISpriteType,Sprite> sprites;
	private HashMap<String, Sprite> fileSprites;
	
	public SpriteManager() {
		sprites = new HashMap<ISpriteType, Sprite>();
		fileSprites = new HashMap<String, Sprite>();
	}
	
	private Sprite LoadSpriteFromFile(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("./res/" + filename));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new Sprite(filename, image);
	}

	private Sprite RegisterSprite(ISpriteType type, Sprite sprite) {
		if (sprites.containsKey(type)) {
			return sprites.get(type);
		}
		
		sprites.put(type, sprite);
		return sprite;
	}
	
	private Sprite RegisterSprite(String filename, Sprite sprite) {
		if (fileSprites.containsKey(filename)) {
			fileSprites.put(filename, sprite);
		}
		fileSprites.put(filename, sprite);
		return sprite;
	}

	@Override
	public Sprite RegisterSprite(String filename) {
		Sprite sprite = LoadSpriteFromFile(filename);
		sprite.PixelsToWorld = new Vector2(sprite.GetImage().getWidth(), sprite.GetImage().getHeight());
		
		if (fileSprites.containsKey(filename)) {
			return fileSprites.get(filename);
		}
		
		fileSprites.put(filename, sprite);
		return sprite;
	}

	public Sprite RegisterSprite(String filename, float worldSpace) {
		Sprite sprite = LoadSpriteFromFile(filename);
		sprite.PixelsToWorld = CalculatePixelsPerWorldUnit(sprite, worldSpace);

		if (fileSprites.containsKey(filename)) {
			return fileSprites.get(filename);
		}

		fileSprites.put(filename, sprite);
		return sprite;
	}
	@Override
	public Sprite RegisterSprite(ISpriteType type, String filename) {
		Sprite sprite = LoadSpriteFromFile(filename);
		sprite.PixelsToWorld = new Vector2(sprite.GetImage().getWidth(), sprite.GetImage().getHeight());
		return RegisterSprite(type, sprite);
	}
	
	@Override
	public Sprite RegisterSprite(ISpriteType type, String filename, float worldSpace) {
		Sprite sprite = LoadSpriteFromFile(filename);
		Vector2 ptw = CalculatePixelsPerWorldUnit(sprite, worldSpace); 
		
		sprite.PixelsToWorld = ptw;
		return RegisterSprite(type, sprite);
	}
	
	private Vector2 CalculatePixelsPerWorldUnit(Sprite sprite, float worldSpace) {
		return new Vector2(sprite.GetImage().getWidth() / worldSpace, sprite.GetImage().getHeight() / worldSpace);
	}
	
	@Override
	public Sprite RegisterSprite(ISpriteType type, String filename, Vector2 pixelsToWorld) {
		Sprite sprite = LoadSpriteFromFile(filename);
		sprite.PixelsToWorld = pixelsToWorld;
		return RegisterSprite(type,sprite);
	}
	
	@Override
	public void RegisterSpriteSheet(String filename, Vector2 size, float worldSpace) {
		Sprite sprite = LoadSpriteFromFile(filename);
		int total_width = sprite.GetImage().getWidth();
		int total_height = sprite.GetImage().getHeight();
		
		int width = (int)size.x;
		int height = (int)size.y;
		
		int imagesX = total_width / (int)size.x;
		int imagesY = total_height / (int)size.y;
		
		System.out.println("imagesX: " + imagesX + " imagesY: " + imagesY);
		
		for (int x = 0; x < imagesX; x++) {
			for (int y = 0; y < imagesY; y++) {
				BufferedImage image = sprite.GetImage().getSubimage(
						x * width,
						y * height,
						width,
						height
				);
				
				String subfilename = filename + "_" + x + "_" + y;
				Sprite subsprite = new Sprite(subfilename, image);
				subsprite.PixelsToWorld = CalculatePixelsPerWorldUnit(subsprite, worldSpace);
				
				RegisterSprite(subfilename, subsprite);
			}
		}
	}

	@Override
	public Sprite GetSprite(ISpriteType type) {
		if (sprites.containsKey(type) == false)
			return null;
		return sprites.get(type);
	}

	@Override
	public Sprite GetSprite(String filename) {
		for (ISpriteType type : sprites.keySet()) {
			if (sprites.get(type).GetFilename().equals(filename))
				return sprites.get(type);
		}
		if (fileSprites.containsKey(filename))
			return fileSprites.get(filename);
		return null;
	}

	@Override
	public Sprite GetSpriteFromSheet(String filename, int indexX, int indexY) {
		return GetSprite(filename + "_" + indexX + "_" + indexY);
	}
}
