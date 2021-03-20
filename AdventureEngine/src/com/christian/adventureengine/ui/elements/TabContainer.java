package com.christian.adventureengine.ui.elements;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.VerticalPushLayout;

public class TabContainer extends Element {
    private Container[] containers;
    private int activeIndex;

    public TabContainer(VerticalPushLayout layout, String id, Container[] containers) {
        super(layout, id);
        this.containers = containers;
        activeIndex = 0;
    }

    public TabContainer SetActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
        layout.RecalculateHeights();
        return this;
    }

    public int GetActiveIndex() {
        return activeIndex;
    }

    @Override
    public Element HitTest(Vector2 screenLocation) {
        return containers[activeIndex].HitTest(screenLocation);
    }

    @Override
    public Element FindId(String id) {
        if (this.id.equals(id))
            return this;
        return containers[activeIndex].FindId(id);
    }

    @Override
    public int CalculateHeight() {
        return containers[activeIndex].CalculateHeight();
    }

    @Override
    public void UpdateBounds(Box bounds) {
        this.bounds = bounds; // so we can track our bounds
        containers[activeIndex].UpdateBounds(bounds);
    }

    @Override
    public void draw(IRenderer renderer) {
        containers[activeIndex].draw(renderer);
    }
}
