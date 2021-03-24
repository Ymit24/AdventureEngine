package com.christian.adventureengine.ui.elements;

import java.awt.Color;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.BaseLayout;

public class TitleBar extends Element {
	private int Thickness = 20;
	private Label Title;
	
	public TitleBar(BaseLayout layout) {
		super(layout, "_");
	}

	public TitleBar SetThickness(int thickness) {
		Thickness = thickness;
		return this;
	}
	
	public TitleBar SetTitle(Label title) {
		Title = title;
		return this;
	}
	
	@Override
	public void UpdateBounds(Box bounds) {
		this.bounds = bounds;
		if (Title != null) {
			Title.UpdateBounds(
				new Box(
					bounds.position,
					bounds.size
				)
			);
		}
	}
	
	@Override
	public int CalculateHeight() {
		return Math.max(Thickness, Title != null ? Title.CalculateHeight() : 0);
	}
	
	@Override
	public void draw(IRenderer renderer) {
		System.out.println("Bounds::" + bounds);
		renderer.FillBox(OffsetByLayout(bounds), Color.blue);
		renderer.FillBox(OffsetByLayout(
			new Box(
				bounds.position.Add(new Vector2(bounds.size.x-20,0)),
				new Vector2(20,bounds.size.y)
			)
		), Color.red);
		Title.draw(renderer);
	}
	

}
