package com.christian.adventureengine.input.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.christian.adventureengine.input.IKeyListener;
import com.christian.adventureengine.input.IKeyboardListener;

public class KeyboardListener implements IKeyboardListener, KeyListener {
	private Map<Integer, Boolean> keyStates;
	private ArrayList<IKeyListener> keyListeners;
	
	public KeyboardListener() {
		keyStates = new HashMap<>();
		keyListeners = new ArrayList<IKeyListener>();
	}

	/**
	 * @param keycode, Use KeyEvent.VK_* key codes. 
	 */
	public boolean isKeyDown(int keycode) {
		if (keyStates.containsKey(keycode) == false)
			return false;
		return keyStates.get(keycode);
	}
	
	@Override
	public void AddKeyListener(IKeyListener listener) {
		this.keyListeners.add(listener);
	}

	@Override
	public void RemoveKeyListener(IKeyListener listener) {
		this.keyListeners.remove(listener);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keyStates.put(e.getKeyCode(), true);
		
		for (IKeyListener listener : keyListeners) {
			if (listener.OnKey(e.getKeyCode(), true))
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyStates.put(e.getKeyCode(), false);
		
		for (IKeyListener listener : keyListeners) {
			if (listener.OnKey(e.getKeyCode(), false))
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// not used
	}
	
}
