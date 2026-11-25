package com.christian.adventureengine.utils;

import java.util.Random;

public class Randomizer {
	public static Random random = new Random();
	
	public static float Between(float a, float b) {
		return a + (random.nextFloat() * (b - a));
	}
}
