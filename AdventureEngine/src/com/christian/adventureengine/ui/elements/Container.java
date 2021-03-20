package com.christian.adventureengine.ui.elements;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.logic.Controller;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.VerticalPushLayout;

import java.awt.*;

public class Container extends Element {
    private Color backgroundColor;
    public Container(VerticalPushLayout layout, String id, Element[] children) {
        super(layout, id);
        this.children = children;
        this.backgroundColor = Color.black;
    }

    public Container SetBackgroundColor(Color color) {
        this.backgroundColor = color;
        return this;
    }

    @Override
    public int CalculateHeight() {
        int accumulatedHeight = 0;
        for (Element child : children) {
            accumulatedHeight += child.CalculateHeight();
        }
        return accumulatedHeight;
    }


    @Override
    public void UpdateBounds(Box bounds) {
        this.bounds = bounds;
        float totalHeight = CalculateHeight();
        float accumulatedPosition = bounds.position.y;
        for (Element child : children) {
            float childHeight = child.CalculateHeight();
            float relHeight = (childHeight / totalHeight) * bounds.size.y;
            child.UpdateBounds(new Box(
                new Vector2(
                    bounds.position.x,
                    accumulatedPosition
                ),
                new Vector2(
                    bounds.size.x,
                    relHeight
                )
            ));
            accumulatedPosition += relHeight;
        }
    }

    @Override
    public void draw(IRenderer renderer) {
        renderer.FillBox(bounds, backgroundColor);
        super.draw(renderer);
    }
}
