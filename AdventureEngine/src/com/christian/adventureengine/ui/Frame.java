package com.christian.adventureengine.ui;

import com.christian.adventureengine.data.Box;

public class Frame {
	public final String Title, Id;
	public final Box Bounds;
	public final LayoutType layoutType;
	public final boolean Moveable;
	public final boolean Resizeable;
	public final int TitlebarThickness;
	
	public final BaseLayout Layout;
	
	public Frame(
		String title,
		String id,
		Box bounds,
		LayoutType layoutType,
		boolean moveable,
		boolean resizeable,
		int titleBarThickness
	)
	{
		Title = title;
		Id = id;
		Bounds = bounds;
		this.layoutType = layoutType;
		Moveable = moveable;
		Resizeable = resizeable;
		TitlebarThickness = titleBarThickness;
		
		switch (layoutType) {
			case VERTICAL_PUSH: {
				Layout = VerticalPushLayout.Create(bounds);
				break;
			}
			default: {
				Layout = null;
				break;
			}
		}
	}
	
	/**
	 * This is the region within the Bounds that consists of the Title bar.
	 * @return
	 */
	public Box GetTitleBarRegion() {
		return new Box(
			Bounds.position.x,
			Bounds.position.y,
			Bounds.size.x,
			Math.min(Bounds.size.y, TitlebarThickness)
		);
	}
}
