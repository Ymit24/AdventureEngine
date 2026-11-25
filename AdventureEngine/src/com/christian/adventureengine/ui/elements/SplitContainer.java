package com.christian.adventureengine.ui.elements;

import java.awt.Color;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.BaseLayout;

public class SplitContainer extends Element {
	private float splitPercent;
	private Color backgroundColor;

	public SplitContainer(BaseLayout layout, String id, Element left, Element right, float splitPercent) {
		super(layout, id);
		children = new Element[2];
		children[0] = left;
		children[1] = right;
		this.backgroundColor = Color.black;
		this.splitPercent = splitPercent;
	}

	public SplitContainer(BaseLayout layout, String id, Element[] children) {
		super(layout, id);
		this.children = children;
		this.splitPercent = 1 / (float) children.length;
		this.backgroundColor = Color.black;
	}

	public SplitContainer SetBackgroundColor(Color color) {
		this.backgroundColor = color;
		return this;
	}

	@Override
	public void UpdateBounds(Box bounds) {
		this.bounds = bounds;
		if (children.length == 2) {
			children[0].UpdateBounds(new Box(
				bounds.position.x,
				bounds.position.y,
				bounds.size.x * splitPercent,
				bounds.size.y
			));
			children[1].UpdateBounds(new Box(
				bounds.position.x + bounds.size.x * splitPercent,
				bounds.position.y,
				bounds.size.x * (1 - splitPercent),
				bounds.size.y
			));
		} else {
			for (int i = 0; i < children.length; i++) {
				children[i].UpdateBounds(
					new Box(
						bounds.position.x + (bounds.size.x * (splitPercent * i)),
						bounds.position.y,
						bounds.size.x * splitPercent,
						bounds.size.y
					)
				);
			}
		}
	}

	@Override
	public void draw(IRenderer renderer) {
		renderer.FillBox(OffsetByLayout(bounds), backgroundColor);
		super.draw(renderer);
	}
}