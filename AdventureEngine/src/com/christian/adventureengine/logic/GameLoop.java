package com.christian.adventureengine.logic;

import com.christian.adventureengine.rendering.IRenderer;

public class GameLoop {
	public static final int TARGET_FPS = 120;
	
	private static Thread thread;
	private static boolean isRunning;
	
	private static final double ONE_BILLION = 1000000000.0;
	private static final int FRAME_SMOOTH_PERIODS = 12;
	private static int[] frameSmoothingBuffer;
	private static int frameIndex;
	private static int averageFPS;
	
	private static InnerLoop innerLoop;
	
	private static IUpdater updater;
	private static IRenderer renderer;
	
	public static void Initialize(IUpdater updater, IRenderer renderer) {
		frameSmoothingBuffer = new int[FRAME_SMOOTH_PERIODS];
		frameIndex = 0;
		innerLoop = new InnerLoop();
		
		GameLoop.updater = updater;
		GameLoop.renderer = renderer;
	}
	
	public static void Start() {
		isRunning = true;
		thread = new Thread(innerLoop);
		thread.start();
	}
	
	public static void Stop() {
		isRunning = false;
		try {
			thread.join();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int GetAverageFPS() {
		return averageFPS;
	}
	
	public static class InnerLoop implements Runnable {
		public void run() {
			double desiredTime = ONE_BILLION / (double)TARGET_FPS;
			long currentTime = System.nanoTime();
			long nextTime = (long)(currentTime + desiredTime);
			
			int frames = 0;
			long lastSecond = System.nanoTime();
			long lastTime = System.nanoTime();
			double delta = 0;
			while (isRunning) {
				if (System.nanoTime() > nextTime) {
					if (System.nanoTime() - lastSecond > ONE_BILLION) {
						frameSmoothingBuffer[frameIndex] = frames;
						frameIndex = (frameIndex + 1) % FRAME_SMOOTH_PERIODS;
						
						int totalFPS = 0;
						int goodPeriods = 0;
						for (int fps : frameSmoothingBuffer) {
							if (fps < 1) continue;
							totalFPS += fps;
							goodPeriods++;
						}
						
						if (goodPeriods > 0) {
							averageFPS = (int)((double)totalFPS / (double)goodPeriods);
//							System.out.println("Average FPS: " + averageFPS + " delta: " + delta);
						}
						frames = 0;
						delta = 0;
						lastSecond = System.nanoTime();
					}
					
					frames++;
					nextTime += desiredTime;
					double frameTime = (System.nanoTime() - lastTime);
					lastTime = System.nanoTime();
					double deltaTime = (frameTime / ONE_BILLION);
					
					delta += deltaTime;
					
					// update & render here
					updater.OnUpdate((float)deltaTime);
					renderer.OnRender();
				}
			}
		}
	}
}






