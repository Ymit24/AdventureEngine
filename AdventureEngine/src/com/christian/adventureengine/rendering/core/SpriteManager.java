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
	
	/**
	 * Load an image from file and return it as a Sprite.
	 * @param filename relative to the ./res/ directory.
	 * @return a Sprite object with the image and filename.
	 */
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

	/**
	 * Adds the sprite to the HashMap by ISpriteType.
	 * @param type
	 * @param sprite
	 * @return A reference to the sprite passed in.
	 */
	private Sprite RegisterSprite(ISpriteType type, Sprite sprite) {
		if (sprites.containsKey(type)) {
			return sprites.get(type);
		}
		
		sprites.put(type, sprite);
		return sprite;
	}
	
	/**
	 * Adds the sprite to the HashMap by filename.
	 * @param filename
	 * @param sprite
	 * @return A reference to the sprite passed in.
	 */
	private Sprite RegisterSprite(String filename, Sprite sprite) {
		if (fileSprites.containsKey(filename)) {
			fileSprites.put(filename, sprite);
		}
		fileSprites.put(filename, sprite);
		return sprite;
	}

	/**
	 * Load a sprite file disk and register it by filename.
	 * @param filename relative to the ./res/ directory.
	 * @return A reference to the sprite loaded and configured.
	 */
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

	/**
	 * Load a sprite file disk and register it by filename.
	 * @param filename relative to the ./res/ directory.
	 * @param worldSpace how many world units for this entire sprite.
	 * @return A reference to the sprite loaded and configured.
	 */
	public Sprite RegisterSprite(String filename, float worldSpace) {
		Sprite sprite = LoadSpriteFromFile(filename);
		sprite.PixelsToWorld = CalculatePixelsPerWorldUnit(sprite, worldSpace);

		if (fileSprites.containsKey(filename)) {
			return fileSprites.get(filename);
		}

		fileSprites.put(filename, sprite);
		return sprite;
	}
	
	/**
	 * Load a sprite file disk and register it by ISpriteType.
	 * @param type
	 * @param filename relative to the ./res/ directory.
	 * @return A reference to the sprite loaded and configured.
	 */
	@Override
	public Sprite RegisterSprite(ISpriteType type, String filename) {
		Sprite sprite = LoadSpriteFromFile(filename);
		sprite.PixelsToWorld = new Vector2(sprite.GetImage().getWidth(), sprite.GetImage().getHeight());
		return RegisterSprite(type, sprite);
	}
	
	/**
	 * Load a sprite file disk and register it by ISpriteType.
	 * @param type
	 * @param filename relative to the ./res/ directory.
	 * @param worldSpace how many world units for this entire sprite.
	 * @return A reference to the sprite loaded and configured.
	 */
	@Override
	public Sprite RegisterSprite(ISpriteType type, String filename, float worldSpace) {
		Sprite sprite = LoadSpriteFromFile(filename);
		Vector2 ptw = CalculatePixelsPerWorldUnit(sprite, worldSpace); 
		
		sprite.PixelsToWorld = ptw;
		return RegisterSprite(type, sprite);
	}
	
	/**
	 * Computes the Vector of pixels per world unit.
	 * @param sprite
	 * @param worldSpace
	 * @return
	 */
	private Vector2 CalculatePixelsPerWorldUnit(Sprite sprite, float worldSpace) {
		return new Vector2(sprite.GetImage().getWidth() / worldSpace, sprite.GetImage().getHeight() / worldSpace);
	}
	
	/**
	 * Load a sprite file disk and register it by ISpriteType.
	 * @param type
	 * @param filename relative to the ./res/ directory.
	 * @param pixelsToWorld how many source pixels converts to a world unit.
	 * @return A reference to the sprite loaded and configured.
	 */
	@Override
	public Sprite RegisterSprite(ISpriteType type, String filename, Vector2 pixelsToWorld) {
		Sprite sprite = LoadSpriteFromFile(filename);
		sprite.PixelsToWorld = pixelsToWorld;
		return RegisterSprite(type,sprite);
	}
	
	/**
	 * Load an image from disk as a SpriteSheet and slice it into subsprites.
	 * Should use the designated method to fetch these sprites back later.
	 * @param filename relative to ./res/ directory.
	 * @param size how many source pixels per subimage. All sprites in the sheet
	 * should have the same dimensions.
	 * @param worldSpace how many world units for this entire sprite.
	 */
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

	/**
	 * Get a registered Sprite by the ISpriteType it was registered with.
	 * @param type
	 * @return A reference to the loaded Sprite.
	 */
	@Override
	public Sprite GetSprite(ISpriteType type) {
		if (sprites.containsKey(type) == false)
			return null;
		return sprites.get(type);
	}

	/**
	 * Get a registered Sprite by the filename is was registered with.
	 * @param filename Use the same filename that it was loaded with.
	 * @return A reference to the loaded Sprite.
	 */
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

	/**
	 * Get a subsprite from a registered SpriteSheet.
	 * @param filename Use the same filename that it was loaded with.
	 * @param indexX the tile coordinate of the Sprite on the X-Axis
	 * @param indexY the tile coordinate of the Sprite on the Y-Axis
	 * @return A reference to the loaded Sprite.
	 */
	@Override
	public Sprite GetSpriteFromSheet(String filename, int indexX, int indexY) {
		return GetSprite(filename + "_" + indexX + "_" + indexY);
	}
}
