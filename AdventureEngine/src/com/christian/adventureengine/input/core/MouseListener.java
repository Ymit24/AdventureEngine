package com.christian.adventureengine.input.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.input.IMouseClickListener;
import com.christian.adventureengine.input.IMouseListener;
import com.christian.adventureengine.input.IMouseScrollListener;

public class MouseListener implements IMouseListener, MouseInputListener, MouseWheelListener {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int MIDDLE = 2;

	private Vector2 position;
	private boolean[] states;
	
	private int scrollDelta = 0;
	
	private ArrayList<IMouseClickListener> mouseClickListeners;
	private ArrayList<IMouseScrollListener> mouseScrollListeners;
	
	public MouseListener() {
		states = new boolean[3];
		position = new Vector2();
		mouseClickListeners = new ArrayList<>();
		mouseScrollListeners = new ArrayList<>();
	}
	
	public int getX() {
		return (int)position.x;
	}
	
	public int getY() {
		return (int)position.y;
	}
	
	public Vector2 GetPosition() {
		return this.position;
	}
	
	public boolean isMouseButtonDown(int button) {
		if (button < 0 && button > 2) {
			return false;
		}
		
		return states[button];
	}

	@Override
	public void AddMouseClickListener(IMouseClickListener listener) {
		mouseClickListeners.add(listener);
	}

	@Override
	public void RemoveMouseClickListener(IMouseClickListener listener) {
		mouseClickListeners.add(listener);
	}

	@Override
	public void AddMouseScrollListener(IMouseScrollListener listener) {
		mouseScrollListeners.add(listener);
	}

	@Override
	public void RemoveMouseScrollListener(IMouseScrollListener listener) {
		mouseScrollListeners.add(listener);
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		position.x = event.getX();
		position.y = event.getY();

		int mappedButton = 0;
		switch (event.getButton()) {
		case MouseEvent.BUTTON1:
			mappedButton = LEFT;
			break;
		case MouseEvent.BUTTON2:
			mappedButton = RIGHT;
			break;
		case MouseEvent.BUTTON3:
			mappedButton = MIDDLE;
			break;
		}
		states[mappedButton] = true;
		
		for (IMouseClickListener listener : mouseClickListeners) {
			if (listener.OnClick(position, mappedButton, true))
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		position.x = event.getX();
		position.y = event.getY();

		int mappedButton = 0;
		switch (event.getButton()) {
		case MouseEvent.BUTTON1:
			states[LEFT] = false;
			mappedButton = LEFT;
			break;
		case MouseEvent.BUTTON2:
			states[RIGHT] = false;
			mappedButton = RIGHT;
			break;
		case MouseEvent.BUTTON3:
			states[MIDDLE] = false;
			mappedButton = MIDDLE;
			break;
		}

		for (IMouseClickListener listener : mouseClickListeners) {
			if (listener.OnClick(position, mappedButton, false))
				break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		position.x = event.getX();
		position.y = event.getY();
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		position.x = event.getX();
		position.y = event.getY();
	}
	
	@Override
	public  void mouseWheelMoved(MouseWheelEvent e) {
		for (IMouseScrollListener listener : mouseScrollListeners) {
			listener.OnScroll(e.getWheelRotation());
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

}
