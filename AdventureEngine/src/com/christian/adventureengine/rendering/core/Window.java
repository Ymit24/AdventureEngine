package com.christian.adventureengine.rendering.core;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
	private JFrame frame;
	public Window(String title, int width, int height, Canvas canvas, boolean isFullscreen) {
		Dimension size = new Dimension(width, height);
		frame = new JFrame(title);

		if (isFullscreen) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setUndecorated(true);
		}
		frame.setVisible(true);

		canvas.setPreferredSize(size);
		frame.add(canvas);
		frame.setResizable(false);
		
		frame.pack();
		frame.pack();
		canvas.requestFocus();
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JFrame GetFrame() {
		return this.frame;
	}
}
