package com.christian.adventureengine.ui.elements;

import java.awt.Color;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.rendering.IRenderer;
import com.christian.adventureengine.ui.BaseLayout;
import com.christian.adventureengine.ui.Frame;
import com.christian.adventureengine.ui.IButtonCallback;

public class TitleBar extends Element implements IButtonCallback {
	private int Thickness = 20;
	private Label Title;
	private Button QuitButton;
	private Frame parent;
	private boolean closeable;
	
	public TitleBar(BaseLayout layout, Frame parent, boolean closeable) {
		super(layout, "_");
		this.parent = parent;
		this.closeable = closeable;
		if (closeable) {
			QuitButton = new Button(layout, "_titlebar_quit_", new Label(layout,"_","X"))
					.SetCallback(this);
		}
	}

	public TitleBar SetThickness(int thickness) {
		Thickness = thickness;
		return this;
	}
	
	public TitleBar SetTitle(Label title) {
		Title = title;
		return this;
	}
	
	@Override
	public void UpdateBounds(Box bounds) {
		this.bounds = bounds;
		if (Title != null) {
			Title.UpdateBounds(
				new Box(
					bounds.position,
					bounds.size
				)
			);
		}
		if (closeable) {
			QuitButton.UpdateBounds(
				new Box(
					bounds.position.Add(new Vector2(bounds.size.x-Thickness,0)),
					new Vector2(Thickness, bounds.size.y)
				)
			);
		}
	}
	
	@Override
	public Element HitTest(Vector2 screenLocation) {
		if (closeable) {
			return QuitButton.HitTest(screenLocation);
		} else {
			return null;
		}
	}
	
	@Override
	public void HandleClick(boolean isDown) {
		if (closeable) {
			QuitButton.HandleClick(isDown);
		}
		super.HandleClick(isDown);
	}
	
	@Override
	public int CalculateHeight() {
		return Math.max(Thickness, Title != null ? Title.CalculateHeight() : 0);
	}
	
	@Override
	public void draw(IRenderer renderer) {
		renderer.FillBox(OffsetByLayout(bounds), Color.blue);
//		renderer.FillBox(OffsetByLayout(
//			new Box(
//				bounds.position.Add(new Vector2(bounds.size.x-20,0)),
//				new Vector2(20,bounds.size.y)
//			)
//		), Color.red);
		Title.draw(renderer);
		if (closeable) {
			QuitButton.draw(renderer);
		}
	}

	@Override
	public void OnButtonClicked(String id) {
		if (closeable) {
			parent.Renderer.RemoveFrame(parent);
		}
	}
}
