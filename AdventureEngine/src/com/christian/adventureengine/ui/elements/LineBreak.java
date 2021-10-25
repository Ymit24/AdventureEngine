package com.christian.adventureengine.ui.elements;

import java.awt.Color;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.BaseLayout;

public class LineBreak extends Element {
	private int height;
	private Color color;
	
	public LineBreak(BaseLayout layout, String id, int height) {
		super(layout, id);
		this.height = height;
		color = Color.white;
	}

	public LineBreak SetColor(Color color) {
		this.color = color;
		return this;
	}
	
	@Override
	public int CalculateHeight() {
		return height;
	}

	@Override
	public void UpdateBounds(Box bounds) {
		this.bounds = bounds;
	}
	
	@Override
	public void draw(IRenderer renderer) {
		renderer.FillBox(OffsetByLayout(bounds), color);
	}
}
