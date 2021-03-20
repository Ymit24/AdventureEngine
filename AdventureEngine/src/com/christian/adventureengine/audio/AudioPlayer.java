package com.christian.adventureengine.audio;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
	private static HashMap<String, Clip> clips = new HashMap<String, Clip>();
	
	/**
	 * Load an audio clip which will be stored by filename.
	 * @param filename This path is relative to your res folder.
	 */
	public static void Load(String filename) {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("./res/" + filename).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(stream);

			clips.put(filename, clip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Play a loaded audio clip.
	 * @param filename must be the same as what was used to load the clip.
	 */
	public static void Play(String filename) {
		if (clips.containsKey(filename) == false) {
			Load(filename);
		}
		Clip clip = clips.get(filename);
		clip.setMicrosecondPosition(0);
		clip.start();
	}
}
