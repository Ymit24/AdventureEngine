package com.christian.adventureengine.input;

public class Input {
	private static IKeyboardListener keyboardListener;
	private static IMouseListener mouseListener;
	
	public static IKeyboardListener GetKeyboardListener() {
		return keyboardListener;
	}
	
	public static void SetKeyboardListener(IKeyboardListener keyboardListener) {
		Input.keyboardListener = keyboardListener;
	}
	
	public static IMouseListener GetMouseListener() {
		return mouseListener;
	}
	
	public static void SetMouseListener(IMouseListener mouseListener) {
		Input.mouseListener = mouseListener;
	}
}
