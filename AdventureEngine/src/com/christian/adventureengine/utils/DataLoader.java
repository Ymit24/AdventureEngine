package com.christian.adventureengine.utils;

import java.util.ArrayList;

public class DataLoader {
	
	public static class Block {
		public String header;
		public String[] keys;
		public String[] values;

		public String GetString(String key) {
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].equals(key)) {
					return values[i];
				}
			}
			return null;
		}

		public boolean GetBoolean(String key) {
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].equals(key)) {
					return values[i].equals("true");
				}
			}
			return false;
		}

		public float GetNumber(String key) {
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].equals(key)) {
					return Float.parseFloat(values[i]);
				}
			}
			System.out.println("FAILED TO FIND VALUE FOR KEY " + key + "!");
			return 0;
		}
	}
	
	public static Block[] LoadFile(String filename) {
		String[] lines = FileLoader.LoadFile("C:\\Dev\\Git\\AdventureGame\\resources\\data\\" + filename);

		ArrayList<Block> blocks = new ArrayList<>();
		
		Block currentBlock = null;
		
		ArrayList<String> keys = null;
		ArrayList<String> values = null;
		
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.length() > 2 && line.substring(0,1).equals("[")) {
				
				if (currentBlock != null) {
					currentBlock.keys = ArrayHelper.GetStringArray(keys);
					currentBlock.values = ArrayHelper.GetStringArray(values);
					blocks.add(currentBlock);
				}
				
				currentBlock = new Block();
				currentBlock.header = line.substring(1,line.length()-1);
				keys = new ArrayList<>();
				values = new ArrayList<>();
			} else if (line.length() > 0) {
				if (line.contains("=")) {
					String[] sections = line.split("=");
					if (sections.length != 2) {
						System.out.println("Invalid KVP detected!");
						continue;
					}
					
					keys.add(sections[0]);
					values.add(sections[1]);
				}
			}
		}

		if (currentBlock != null) {
			currentBlock.keys = ArrayHelper.GetStringArray(keys);
			currentBlock.values = ArrayHelper.GetStringArray(values);
			blocks.add(currentBlock);
		}
		
		Block[] blockArray = new Block[blocks.size()];
		for (int i = 0; i < blockArray.length; i++) {
			blockArray[i] = blocks.get(i);
		}
		
		return blockArray;
	}
}
