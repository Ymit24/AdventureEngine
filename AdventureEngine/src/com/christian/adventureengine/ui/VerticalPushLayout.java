package com.christian.adventureengine.ui;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.ui.elements.Element;
	
public class VerticalPushLayout extends BaseLayout {
	protected int currentY;
	
	protected Element active;

	protected VerticalPushLayout(Box bounds) {
		super(bounds);
	}
	
	/**
	 * Factory method to generate a VerticalPushLayout object.
	 * @param bounds The screen space region that this layout
	 * is responsible for.
	 * @return The newly created VerticalPushLayout.
	 */
	public static VerticalPushLayout Create(Box bounds) {
		VerticalPushLayout layout = new VerticalPushLayout(bounds);
		
		layout.currentY = (int)bounds.position.y;
		
		return layout;
	}
	
	@Override
	public void Recalculate() {
		currentY = 0;
		for (Element el : elements) {
			int height = el.CalculateHeight();
			el.UpdateBounds(new Box(0, currentY, Bounds.size.x, height));
			currentY += height;
		}
	}

	@Override
	public void PushElement(Element element) {
		element.bounds.position = new Vector2(0, currentY);
		currentY += element.bounds.size.y;
		
		elements.add(element);
	}
	
	@Override
	public Element FindElementById(String id) {
		for (Element el : elements) {
			Element found = el.FindId(id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	@Override
	public boolean OnClick(Vector2 screenLocation, int button, boolean isDown) {
		if (Bounds.Includes(screenLocation)) {
			for (Element el : elements) {
				Element hit = el.HitTest(screenLocation);
				if (hit != null) {
					if (isDown) {
						if (active != null) {
							active.isActive = false;
						}
						active = hit;
						hit.isActive = true;

						hit.HandleClick(isDown);
					}
					else {
//						if (active != null) {
//							active.isActive = false;
//						}
//						active = null;
//						hit.HandleClick(isDown);
					}
					return true;
				}
				else if (isDown){
					if (active != null) {
						active.isActive = false;
					}
					active = null;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean OnKey(int keycode, boolean isDown) {
		if (!isDown)
			return false;
		if (active != null) {
			active.HandleKey(keycode);
			return true;
		}
		return false;
	}
}
