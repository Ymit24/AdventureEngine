package com.christian.adventureengine.rendering;

public abstract class View {
	public int layer;
	public View(int layer) {this.layer = layer;}
	public abstract void draw(IRenderer renderer);
}
