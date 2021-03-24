package com.christian.adventureengine.ui.elements;

import java.awt.Color;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.BaseLayout;

public class Label extends Element {
	public enum Alignment {
		LEFT, CENTER, RIGHT
	}
	public static final int DEFAULT_FONT_SIZE = 16;
	public static final String DEFAULT_FONT_FAMILY = "Arial";
	public String text;
	public int fontSize;
	public String fontFamily;
	public Color fontColor;
	
	public Alignment alignment;
	
	public Label(BaseLayout layout, String id, String text) {
		super(layout, id);
		
		this.text = text;
		this.fontColor = Color.white;
		this.fontSize = DEFAULT_FONT_SIZE;
		this.fontFamily = DEFAULT_FONT_FAMILY;
		this.alignment = Alignment.CENTER;
	}

	public Label SetPadding(Vector2 padding) {
		this.padding = padding;
		layout.Recalculate();
		return this;
	}
	
	public Label SetFontSize(int fontSize) {
		this.fontSize = fontSize;
		layout.Recalculate();
		return this;
	}

	public Label SetFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
		layout.Recalculate();
		return this;
	}
	
	public Label SetFontColor(Color color) {
		this.fontColor = color;
		return this;
	}
	
	public Label SetAlignment(Alignment alignment) {
		this.alignment = alignment;
		return this;
	}

	@Override
	public void UpdateBounds(Box bounds) {
		this.bounds = bounds;
	}
	
	@Override
	public int CalculateHeight() {
		return (int)(fontSize + padding.y*2);
	}
	
	@Override
	public void draw(IRenderer renderer) {
		renderer.SetColor(fontColor);
		renderer.SetFontSize(fontSize);
		int x = (int)bounds.position.x;
		int y = (int)bounds.position.y;
		if (alignment == Alignment.CENTER) {
			Vector2 center = Box.Center(bounds, new Vector2(renderer.GetFontWidth(text), fontSize));
			x = (int)center.x;
			y = (int)center.y;
		} else if (alignment == Alignment.LEFT) {
			x = (int)(bounds.position.x + padding.x);
			y = (int)(bounds.position.y + ((bounds.size.y / 2) - fontSize / 2));
		} else if (alignment == Alignment.RIGHT) {
			x = (int)(bounds.position.x + bounds.size.x - renderer.GetFontWidth(text) - padding.x);
			y = (int)(bounds.position.y + ((bounds.size.y / 2) - fontSize / 2));
		}
		renderer.DrawScreenText(text, new Vector2(x, y));
	}
}
