package com.christian.adventureengine.input;

import com.christian.adventureengine.data.Vector2;

public interface IMouseListener {
	public Vector2 GetPosition();
	public boolean isMouseButtonDown(int button);
	
	public void AddMouseClickListener(IMouseClickListener listener);
	public void RemoveMouseClickListener(IMouseClickListener listener);

	public void AddMouseScrollListener(IMouseScrollListener listener);
	public void RemoveMouseScrollListener(IMouseScrollListener listener);
}
