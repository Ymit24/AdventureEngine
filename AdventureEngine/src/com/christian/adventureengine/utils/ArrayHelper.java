package com.christian.adventureengine.utils;

import java.util.ArrayList;

public class ArrayHelper {
	public static String[] GetStringArray(ArrayList<String> list) {
		String[] array = new String[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
}
