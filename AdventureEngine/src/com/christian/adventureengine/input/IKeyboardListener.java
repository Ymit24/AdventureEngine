package com.christian.adventureengine.input;

public interface IKeyboardListener {
	public boolean isKeyDown(int keycode);
	
	public void AddKeyListener(IKeyListener listener);
	public void RemoveKeyListener(IKeyListener listener);
}
