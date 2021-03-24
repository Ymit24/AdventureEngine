package com.christian.adventureengine.rendering.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.data.WorldObject;
import com.christian.adventureengine.input.Input;
import com.christian.adventureengine.input.core.KeyboardListener;
import com.christian.adventureengine.input.core.MouseListener;
import com.christian.adventureengine.rendering.Camera;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.rendering.UIView;
import com.christian.adventureengine.rendering.View;
import com.christian.adventureengine.rendering.sprites.Sprite;
import com.christian.adventureengine.rendering.sprites.Sprites;
import com.christian.adventureengine.ui.Frame;
import com.christian.adventureengine.ui.LayoutType;
import com.christian.adventureengine.ui.VerticalPushLayout;
import com.christian.adventureengine.ui.elements.Element;

public class CoreRenderer implements IRenderer {
	private int displayWidth = 1280;
	private int displayHeight = 720;
	
	private Window window;
	private Canvas canvas;
	
	private BufferStrategy bufferStrategy;

	private ArrayList<View> orderedViews;

	private Graphics graphics;
	private boolean canDraw;
	
	private static final int DEFAULT_FONTSIZE = 24;
	private static final Color DEFAULT_COLOR = Color.white;
	private static final int UI_LAYER = 10;
	
	private Font currentFont;
	private Color currentColor;
	
	private Camera camera;
	
	private ArrayList<VerticalPushLayout> uiLayouts;
	private ArrayList<Frame> frames;
	
	public CoreRenderer() {
		canDraw = false;
	}
	
	@Override
	public void Initialize(String windowTitle, int width, int height, boolean isFullscreen) {
		displayWidth = width;
		displayHeight = height;
		canvas = new Canvas();
		window = new Window(windowTitle, displayWidth, displayHeight, canvas, isFullscreen);
		
		if (bufferStrategy == null) {
			canvas.createBufferStrategy(2);
			bufferStrategy = canvas.getBufferStrategy();
		}
		
		graphics = bufferStrategy.getDrawGraphics();
		
		SetFontSize(DEFAULT_FONTSIZE);
		currentColor = DEFAULT_COLOR;
		uiLayouts = new ArrayList<VerticalPushLayout>();
		frames = new ArrayList<>();

		orderedViews = new ArrayList<>();
		orderedViews.add(new UIView(UI_LAYER));
	}

	public int GetDisplayWidth() {
		return displayWidth;
	}
	public int GetDisplayHeight() {
		return displayHeight;
	}

	@Override
	public void CreateInput() {
		KeyboardListener keyboardListener = new KeyboardListener();
		MouseListener mouseListener = new MouseListener();
		
		canvas.addKeyListener(keyboardListener);
		canvas.addMouseListener(mouseListener);
		Input.SetKeyboardListener(keyboardListener);
		Input.SetMouseListener(mouseListener);
		canvas.addMouseMotionListener(mouseListener);
		canvas.addMouseWheelListener(mouseListener);
	}

	@Override
	public void CreateCamera(Vector2 worldSpaceView) {
		CreateCamera(worldSpaceView, new Box(0, 0, displayWidth, displayHeight));
	}
	
	@Override
	public void CreateCamera(Vector2 worldSpaceView, Box screenSpace) {
		assert(screenSpace.position.x >= 0 && screenSpace.position.x + screenSpace.size.x <= displayWidth && screenSpace.position.y >= 0 && screenSpace.position.y  + screenSpace.size.y <= displayHeight);
		Vector2 pixelsPerWorldUnit = new Vector2(
			screenSpace.size.x / worldSpaceView.x,
			screenSpace.size.y / worldSpaceView.y
		);
		camera = new Camera(pixelsPerWorldUnit, worldSpaceView, screenSpace);
		
		System.out.println("Pixels per world unit: " + pixelsPerWorldUnit.toString());
	}

	/**
	 * Create a vertical push layout user interface layer.
	 * @param bounds these are measured in screen space for
	 * the bounding region that the interface will take up.
	 * @return A reference to the layout created.
	 */
	@Override
	public VerticalPushLayout CreateUILayout(Box bounds) {
		VerticalPushLayout layout = VerticalPushLayout.Create(bounds);
		uiLayouts.add(layout);
		
		Input.GetMouseListener().AddMouseClickListener(layout);
		Input.GetKeyboardListener().AddKeyListener(layout);
		return layout;
	}
	
	@Override
	public Frame CreateFrame(
		String title,
		String id,
		Box bounds,
		LayoutType layoutType,
		boolean moveable,
		boolean resizeable,
		int titleBarThickness
	) {
		Frame frame = new Frame(
			title,
			id,
			bounds,
			layoutType,
			moveable,
			resizeable,
			titleBarThickness
		);
		
		frames.add(frame);
		return frame;
	}

	@Override
	public ArrayList<Frame> GetFrames() {
		return frames;
	}
	
	@Override
	public void CreateSpriteManager() {
		SpriteManager spriteManager = new SpriteManager();
		Sprites.SetSpriteManager(spriteManager);
	}


	@Override
	public void AddView(View view) {
		orderedViews.add(view);
		orderedViews.sort(new Comparator<View>() {
			@Override
			public int compare(View o1, View o2) {
				return o1.layer - o2.layer;
			}
		});
	}

	@Override
	public void RemoveView(View view) {
		orderedViews.remove(view);
	}

	@Override
	public ArrayList<VerticalPushLayout> GetLayouts() {
		return uiLayouts;
	}

	@Override
	public int GetUILayer() {
		return UI_LAYER;
	}

	@Override
	public void SetFontSize(int size) {
		currentFont = Fonts.GetFont(size);
		graphics.setFont(currentFont);
	}

	@Override
	public void SetFontFamily(String family) {
		Font newFont = Fonts.GetFont(family, currentFont.getSize());
		if (newFont != null)
		{
			currentFont = newFont;
			graphics.setFont(currentFont);
		}
	}

	@Override
	public void SetFontFamily(String family, int size) {
		Font newFont = Fonts.GetFont(family, size);
		if (newFont != null)
		{
			currentFont = newFont;
			graphics.setFont(currentFont);
		}
	}
	
	@Override
	public void SetColor(Color color) {
		currentColor = color;
	}
	
	@Override
	public int GetFontWidth(String message) {
		return graphics.getFontMetrics().stringWidth(message);
	}
	
	@Override
	public void DrawWorldText(String message, Vector2 worldLocation) {
		if (canDraw == false)
			return;
		
		graphics.setColor(Color.white);
		Vector2 pixelLocation = camera.CalculateWorldToScreen(worldLocation);
		graphics.setFont(currentFont);
		graphics.setColor(currentColor);
		graphics.drawString(message, (int)pixelLocation.x, (int)pixelLocation.y);
	}
	
	@Override
	public void DrawWorldSprite(Sprite sprite, WorldObject object) {
		DrawWorldSprite(sprite, object.Position, object.Size);
	}
	
	@Override
	public void DrawWorldSprite(Sprite sprite, Vector2 location, Vector2 size) {
		if (canDraw == false || camera.isInCameraBounds(location, size) == false)
			return;
		Vector2 pixelLocation = camera.CalculateWorldToScreen(location);
		
		Vector2 cameraEndCorner = camera.CalculateWorldToScreen(camera.GetPosition().Add(camera.GetWorldSpace()));
		graphics.setClip(0, 0, (int)cameraEndCorner.x, (int)cameraEndCorner.y);
		
		graphics.drawImage(
			sprite.GetImage(),
			Math.round(pixelLocation.x),
			Math.round(pixelLocation.y),
			Math.round((camera.GetPixelsPerWorldUnit().x / sprite.PixelsToWorld.x) * sprite.GetImage().getWidth()),
			Math.round((camera.GetPixelsPerWorldUnit().y / sprite.PixelsToWorld.y) * sprite.GetImage().getHeight()),
			null
		);
	}
	
	@Override
	public void DrawScreenText(String message, Vector2 screenLocation) {
		if (canDraw == false)
			return;
		
		graphics.setColor(Color.white);
		graphics.setClip(0, 0, displayWidth, displayHeight);
		graphics.setFont(currentFont);
		FontMetrics m = graphics.getFontMetrics(currentFont);
		graphics.setColor(currentColor);
		graphics.drawString(message, (int)screenLocation.x, (int)screenLocation.y + m.getAscent());
	}
	
	@Override
	public void DrawScreenSprite(Sprite sprite, Vector2 screenLocation) {
		if (canDraw == false)
			return;
		
		graphics.setClip(0, 0, displayWidth, displayHeight);
		graphics.drawImage(
			sprite.GetImage(),
			(int)screenLocation.x,
			(int)screenLocation.y,
			(int)((camera.GetPixelsPerWorldUnit().x / sprite.PixelsToWorld.x) * sprite.GetImage().getWidth()),
			(int)((camera.GetPixelsPerWorldUnit().y / sprite.PixelsToWorld.y) * sprite.GetImage().getHeight()),
			null
		);
	}

	@Override
	public void DrawScreenSprite(Sprite sprite, Box screenBounds) {
		if (canDraw == false)
			return;

		graphics.setClip(0, 0, displayWidth, displayHeight);
		graphics.drawImage(
				sprite.GetImage(),
				(int)screenBounds.position.x,
				(int)screenBounds.position.y,
				(int)screenBounds.size.x,
				(int)screenBounds.size.y,
				null
		);
	}
	
	@Override
	public void FillBox(Box box, Color color) {
		if (canDraw == false)
			return;
		
		graphics.setClip(0, 0, displayWidth, displayHeight);
		graphics.setColor(color);
		graphics.fillRect((int)box.position.x, (int)box.position.y, (int)box.size.x, (int)box.size.y);
	}

	@Override
	public void OnRender() {
		graphics = bufferStrategy.getDrawGraphics();
		
		canDraw = true;
		
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, displayWidth, displayHeight);

		for (View view : orderedViews) {
			view.draw(this);
		}
		
//		for (VerticalPushLayout layout : uiLayouts) {
//			for (Element el : layout.elements) {
//				el.draw(this);
//			}
//		}
		
		for (Frame frame : frames) {
			FillBox(frame.Bounds, Color.gray);
			for (Element el : frame.Layout.elements) {
				el.draw(this);
			}
		}
		
		canDraw = false;
		bufferStrategy.show();
		graphics.dispose();
	}
}
