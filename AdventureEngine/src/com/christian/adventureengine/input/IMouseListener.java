package com.christian.adventureengine.input;

import com.christian.adventureengine.data.Vector2;

public interface IMouseListener {
	/**
	 * This gets the current Mouse
	 * coordinates in screen space.
	 * @return The current Mouse position in screen space.
	 */
	public Vector2 GetPosition();
	
	public boolean isMouseButtonDown(int button);
	
	public void AddMouseClickListener(IMouseClickListener listener);
	public void RemoveMouseClickListener(IMouseClickListener listener);

	public void AddMouseScrollListener(IMouseScrollListener listener);
	public void RemoveMouseScrollListener(IMouseScrollListener listener);
}
