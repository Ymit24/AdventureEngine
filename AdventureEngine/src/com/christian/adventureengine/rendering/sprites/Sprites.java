package com.christian.adventureengine.rendering.sprites;

public class Sprites {
	private static ISpriteManager spriteManager;

	public static ISpriteManager GetSpriteManager() {
		return spriteManager;
	}
	
	public static void SetSpriteManager(ISpriteManager spriteManager) {
		Sprites.spriteManager = spriteManager;
	}
}
