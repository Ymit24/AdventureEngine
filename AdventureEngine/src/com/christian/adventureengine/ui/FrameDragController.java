package com.christian.adventureengine.ui;

import java.util.ArrayList;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.input.Input;
import com.christian.adventureengine.logic.Controller;
import com.christian.adventureengine.rendering.IRenderer;

public class FrameDragController extends Controller {
	private IRenderer renderer;
	private Frame dragging;
	private Vector2 offsetFromTitleBar;
	
	public FrameDragController(IRenderer renderer) {
		this.renderer = renderer;
		offsetFromTitleBar = Vector2.Zero();
	}
	
	@Override
	public void Update(float deltaTime) {
		if (Input.GetMouseListener().isMouseButtonDown(0) == false) {
			dragging = null;
			return;
		}
		
		ArrayList<Frame> frames = renderer.GetFrames();

		Vector2 mousePosition = Input.GetMouseListener().GetPosition();
		if (dragging != null) {
			dragging.Bounds.position = new Vector2(mousePosition).Add(offsetFromTitleBar);
			return;
		}
		
		for (Frame frame : frames) {
			if (frame.Resizeable == false)
				continue;
			
			// Handle click to drag.
			Box clickableRegion = frame.GetTitleBarRegion();
			
			if (clickableRegion.Includes(mousePosition)) {
				// Frame is being dragged now
				dragging = frame;
				offsetFromTitleBar = frame.Bounds.position.Sub(mousePosition);
				break;
			}
		}
	}
}
