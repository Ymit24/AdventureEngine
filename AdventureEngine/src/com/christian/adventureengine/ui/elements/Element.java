package com.christian.adventureengine.ui.elements;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.BaseLayout;
import com.christian.adventureengine.ui.VerticalPushLayout;

public abstract class Element {
	public static final Vector2 DEFAULT_PADDING = new Vector2(4,4);
	
	public BaseLayout layout;
	public Vector2 padding;
	public Box bounds;
	public String id;
	public String data;
	
	public Element[] children;
	public boolean isActive;
	
	public Element(BaseLayout layout, String id) {
		this.layout = layout;
		this.id = id;
		bounds = new Box(0,0,0,0);
		padding = Vector2.Zero();
		isActive = false;
		children = new Element[] {};
	}
	
	public Element HitTest(Vector2 screenLocation) {
		if (children != null) {
			for (Element child : children) {
				Element hit = child.HitTest(screenLocation);
				if (hit != null) {
					return hit;
				}
			}
		}
		return bounds.Includes(screenLocation) ? this : null;
	}
	
	public void HandleKey(int keycode) {
		if (children != null) {
			for (Element child : children) {
				child.HandleKey(keycode);
			}
		}
	}
	
	public void HandleClick(boolean isDown) {
		if (children != null) {
			for (Element child : children) {
				child.HandleClick(isDown);
			}
		}
	}
	
	public Element FindId(String id) {
		if (this.id.equals(id)) {
			return this;
		}
		if (children != null) {
			for (Element child : children) {
				Element found = child.FindId(id);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}
	
	public abstract void UpdateBounds(Box bounds);
	public int CalculateHeight() {
		int maxHeight = 0;
		for (Element child : children) {
			int height = child.CalculateHeight();
			if (height > maxHeight)
				maxHeight = height;
		}
		return maxHeight;
	}
	
	protected Box OffsetByLayout(Box old) {
		Box new_bounds = new Box(old);
		new_bounds.position = new_bounds.position.Add(layout.Bounds.position);
		return new_bounds;
	}
	
	public void draw(IRenderer renderer) {
		if (children != null) {
			for (Element child : children) {
				child.draw(renderer);
			}
		}
	}
}
