package com.christian.adventureengine.utils;

public class ResourceManager {
	private static ResourceManager instance;
	
	/**
	 * Set the Singleton instance that will be used to evaluate resource paths.
	 * @param manager An instance of ResourceManager.
	 */
	public static void Initialize(ResourceManager manager) {
		instance = manager;
	}
	
	/**
	 * Retrieves the Singleton instance of ResourceManager. Throws exception if not initialized.
	 * @return The Singleton instance of ResourceManager.
	 */
	public static ResourceManager GetResourceManager() {
		if (instance == null) {
			throw new IllegalStateException("Tried to access a ResourceManager before initializing it.");
		}
		return instance;
	}
	
	/**
	 * This is the path to the base resource location for the project. 
	 * @return the path to the base resource location. Should end with "/".
	 */
	public String GetBaseResourcePath() {
		return "./res/";
	}
	
	/**
	 * Get's the filepath for the audio file based off the standard set path.
	 * @param filename Relative to your set root audio directory.
	 * @return The filepath to the file.
	 */
	public String GetAudioFilepath(String filename) {
		return GetBaseResourcePath() + "audio/" + filename;
	}
	
	/**
	 * Get's the filepath for the sprite file based off the standard set path.
	 * @param filename Relative to your set root sprites directory.
	 * @return The filepath to the file.
	 */
	public String GetSpriteFilepath(String filename) {
		return GetBaseResourcePath() + "sprites/" + filename;
	}
}
