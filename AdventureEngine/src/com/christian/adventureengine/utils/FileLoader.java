package com.christian.adventureengine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileLoader {
	public static String[] LoadFile(String filePath) {
		ArrayList<String> lines = new ArrayList<>();
		BufferedReader reader;
		try {
			File file = new File(filePath);
			reader = new BufferedReader(new FileReader(file));
			
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ArrayHelper.GetStringArray(lines);
	}
}
