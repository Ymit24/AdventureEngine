package com.christian.adventureengine.ui.elements;

import java.awt.Color;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.BaseLayout;

public class ProgressBar extends Element {
    private Label label;
    private Color filledColor;
    private Color emptyColor;

    private int currentValue;
    private int maxValue;
    private String prefix;

    public ProgressBar(BaseLayout layout, String id, Label label) {
        super(layout, id);
        this.label = label;
        this.children = new Element[]{label};

        this.filledColor = Color.WHITE;
        this.emptyColor = Color.BLACK;

        this.prefix = "";
        this.currentValue = 5;
        this.maxValue = 10;
    }

    public ProgressBar SetFillColor(Color fillColor) {
        this.filledColor = fillColor;
        return this;
    }

    public ProgressBar SetEmptyColor(Color emptyColor) {
        this.emptyColor = emptyColor;
        return this;
    }

    public ProgressBar SetPrefix(String prefix) {
        this.prefix = prefix;
        RecalculateText();
        return this;
    }

    public ProgressBar SetCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        RecalculateText();
        return this;
    }

    public ProgressBar SetMaxValue(int maxValue) {
        this.maxValue = maxValue;
        RecalculateText();
        return this;
    }

    public ProgressBar RecalculateText() {
        this.label.text = prefix + currentValue + "/" + maxValue;
        return this;
    }

    public ProgressBar SetPadding(Vector2 padding) {
        this.padding = padding;
        return this;
    }

    @Override
    public void UpdateBounds(Box bounds) {
        this.bounds = new Box(
            bounds.position.Add(padding),
            bounds.size.Sub(padding.Mul(2))
        );

        label.UpdateBounds(this.bounds);
    }

    @Override
    public void draw(IRenderer renderer) {
        renderer.FillBox(OffsetByLayout(bounds), emptyColor);

        float percent = (float)currentValue / (float)maxValue;
        renderer.FillBox(OffsetByLayout(new Box(bounds.position, new Vector2(bounds.size.x * percent, bounds.size.y))), filledColor);

        label.draw(renderer);
    }
}
