package com.christian.adventureengine.logic;

import java.util.ArrayList;

public class ControllerManager implements IUpdater {
	private static ArrayList<Controller> controllers = new ArrayList<Controller>();
	
	public static void AddController(Controller controller) {
		controllers.add(controller);
	}
	
	public static void RemoveController(Controller controller) {
		controllers.remove(controller);
	}
	
	@Override
	public void OnUpdate(float deltaTime) {
		for (Controller controller : controllers) {
			controller.Update(deltaTime);
		}
	}
}
