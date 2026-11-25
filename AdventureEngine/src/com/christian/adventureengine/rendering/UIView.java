package com.christian.adventureengine.rendering;

import com.christian.adventureengine.ui.VerticalPushLayout;
import com.christian.adventureengine.ui.elements.Element;

public class UIView extends View {
    public UIView(int layer) {
        super(layer);
    }

    @Override
    public void draw(IRenderer renderer) {
        for (VerticalPushLayout layout : renderer.GetLayouts()) {
            for (Element el : layout.elements) {
                el.draw(renderer);
            }
        }
    }
}
