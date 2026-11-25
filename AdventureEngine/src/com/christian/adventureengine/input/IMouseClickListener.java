package com.christian.adventureengine.input;

import com.christian.adventureengine.data.Vector2;

public interface IMouseClickListener {
	public boolean OnClick(Vector2 screenLocation, int button, boolean isDown);
}
