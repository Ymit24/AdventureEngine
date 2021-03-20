package com.christian.adventureengine.data;

import com.christian.adventureengine.utils.Collision;
import com.christian.adventureengine.utils.Randomizer;

import java.util.Random;

public class Box {
	public Vector2 position;
	public Vector2 size;
	
	public Box() {
		position = Vector2.One();
		size = Vector2.One();
	}
	
	public Box(float x, float y, float w, float h) {
		this.position = new Vector2(x,y);
		this.size = new Vector2(w,h);
	}
	
	public Box(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}
	
	public float GetTop() {
		return position.y;
	}
	
	public float GetBottom() {
		return position.y + size.y;
	}
	
	public float GetLeft() {
		return position.x;
	}
	
	public float GetRight() {
		return position.x + size.x;
	}
	
	public static Vector2 Center(Box bounds, Vector2 content) {
		return new Vector2(
			bounds.position.x + (bounds.size.x / 2) - (content.x / 2),
			bounds.position.y + (bounds.size.y / 2) - (content.y / 2)
		);
	}
	
	public boolean Includes(Vector2 point) {
		return point.x <= GetRight() && point.x >= GetLeft() && point.y <= GetBottom() && point.y >= GetTop();
	}

	public Vector2 RingTile(int ringThickness) {
		return new Vector2(
			Randomizer.random.nextBoolean() ? GetLeft() - Randomizer.random.nextInt(ringThickness) : GetRight() + Randomizer.random.nextInt((ringThickness)),
			Randomizer.random.nextBoolean() ? GetTop() - Randomizer.random.nextInt(ringThickness) : GetBottom() + Randomizer.random.nextInt((ringThickness))
		);
	}
	
	@Override
	public String toString() {
		return "{" + position + "," + size + "}";
	}
}
