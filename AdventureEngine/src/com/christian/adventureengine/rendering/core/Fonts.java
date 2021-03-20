package com.christian.adventureengine.rendering.core;

import java.awt.Font;
import java.util.HashMap;

public class Fonts {
	private static HashMap<Integer, Font> fontsBySize = new HashMap<Integer, Font>();
	private static HashMap<String, Font> fontsByFamily = new HashMap<>();
	public static Font GetFont(int size) {
		if (fontsBySize.containsKey(size)) {
			return fontsBySize.get(size);
		}
		
		Font font = new Font("Lucida Console", Font.BOLD, size);
		fontsBySize.put(size, font);
		return font;
	}

	public static Font GetFont(String family, int size) {
		if (fontsByFamily.containsKey(family)) {
			if (fontsByFamily.get(family).getSize() != size) {
				Font font = new Font(family, Font.BOLD, size);
				fontsByFamily.put(family, font);
				return font;
			}
			return fontsByFamily.get(family);
		}
		Font font = new Font(family, Font.BOLD, size);
		fontsByFamily.put(family, font);
		return font;
	}
}
