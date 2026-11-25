package com.christian.adventureengine.ui;

import java.awt.Color;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.input.Input;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.elements.Element;
import com.christian.adventureengine.ui.elements.Label;
import com.christian.adventureengine.ui.elements.TitleBar;
import com.christian.adventureengine.ui.elements.Label.Alignment;

public class Frame {
	public final String Title, Id;
	public final Box Bounds;
	public final LayoutType layoutType;
	public final boolean Moveable;
	public final boolean Resizeable;
	public final int TitlebarThickness;
	
	public final BaseLayout Layout;
	public final IRenderer Renderer;
	
	public Frame(
		IRenderer renderer,
		String title,
		String id,
		Box bounds,
		LayoutType layoutType,
		boolean moveable,
		boolean resizeable,
		boolean closeable,
		int titleBarThickness
	)
	{
		Renderer = renderer;
		Id = id;
		Title = title;
		Bounds = bounds;
		Moveable = moveable;
		Resizeable = resizeable;
		this.layoutType = layoutType;
		TitlebarThickness = titleBarThickness;
		
		switch (layoutType) {
			case VERTICAL_PUSH: {
				Layout = VerticalPushLayout.Create(Bounds);
				if (titleBarThickness != 0) {
					Layout.PushElement(new TitleBar(Layout, this, closeable)
						.SetTitle(new Label(Layout, "_titlebar_title_", Title)
							.SetFontSize(18)
							.SetFontColor(Color.white)
							.SetPadding(Vector2.One().Mul(2))
							.SetAlignment(Alignment.CENTER)));
				}
				Layout.Recalculate();
				Input.GetMouseListener().AddMouseClickListener(Layout);
				Input.GetKeyboardListener().AddKeyListener(Layout);
				break;
			}
			default: {
				Layout = null;
				break;
			}
		}
	}
	
	/**
	 * This is the region within the Bounds
	 * that consists of the Title bar.
	 * @return A Box which describes the
	 * content region.
	 */
	public Box GetTitleBarRegion() {
		return new Box(
			Bounds.position.x,
			Bounds.position.y,
			Bounds.size.x,
			Math.min(Bounds.size.y, TitlebarThickness)
		);
	}
	
	/**
	 * Render all elements within this frame.
	 * @param renderer The renderer to render
	 * this frame with.
	 */
	public void Render(IRenderer renderer) {
		//TODO: MAYBE ADD WAY TO GIVE FRAME A BACKGROUND OPTIONALLY. 
		//renderer.FillBox(Bounds, Color.gray);
		for (Element el : Layout.elements) {
			el.draw(renderer);
		}
//		drawTitleBar(renderer);
	}
}
