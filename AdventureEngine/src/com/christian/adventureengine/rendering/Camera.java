package com.christian.adventureengine.rendering;

import com.christian.adventureengine.data.Box;
import com.christian.adventureengine.data.Vector2;
import com.christian.adventureengine.utils.Collision;

public class Camera {
	private static Camera camera;
	private Box screenSpace;
	private Vector2 pixelsPerWorldUnit;
	private Vector2 worldSpace;
	private Vector2 position;
	
	public static Camera GetCamera() {
		return camera;
	}
	
	public Camera(Vector2 pixelsPerWorldUnit, Vector2 worldSpace, Box screenSpace) {
		this.pixelsPerWorldUnit = pixelsPerWorldUnit;
		this.position = new Vector2();
		this.worldSpace = worldSpace;
		this.screenSpace = screenSpace;
		
		Camera.camera = this;
	}
	
	public Vector2 GetPixelsPerWorldUnit() {
		return pixelsPerWorldUnit;
	}
	
	public Vector2 GetPosition() {
		return this.position;
	}
	
	public Vector2 GetWorldSpace() {
		return this.worldSpace;
	}
	
	public Box GetCameraBounds() {
		return new Box(position, worldSpace); // probably cache this or actually store position/size with this.
	}
	
	public Vector2 GetCenterPosition() {
		return position.Add(worldSpace.Div(2));
	}
	
	public void SetPosition(Vector2 position) {
		this.position = position;
	}
	
	public void Move(Vector2 delta) {
		position = position.Add(delta);
	}
	
	public Box GetScreenSpace() {
		return new Box(screenSpace);
	}

	public Vector2 CalculateWorldToScreen(Vector2 worldLocation) {
		Vector2 pixelLocation = new Vector2(
			screenSpace.position.x + (worldLocation.x-position.x) * pixelsPerWorldUnit.x,
			screenSpace.position.y + (worldLocation.y-position.y) * pixelsPerWorldUnit.y
		);
		return pixelLocation;
	}
	
	public void UpdateBounds(Vector2 worldSpaceView) {
		pixelsPerWorldUnit = new Vector2(
			screenSpace.size.x / worldSpaceView.x,
			screenSpace.size.y / worldSpaceView.y
		);
		pixelsPerWorldUnit.x = Math.round(pixelsPerWorldUnit.x * 2) / 2.0f;
		pixelsPerWorldUnit.y = Math.round(pixelsPerWorldUnit.y * 2) / 2.0f;
		this.worldSpace = worldSpaceView;
	}

	public void CenterZoom(Vector2 worldUnitSize) {
		UpdateBounds(worldUnitSize);
		position = position.Add(worldSpace.Sub(worldUnitSize).Div(2));
	}

	public void CenterZoom(float worldUnitSize) {
		float diff = worldSpace.x - worldUnitSize;
		UpdateBounds(Vector2.One().Mul(worldUnitSize));
		position = position.Add(Vector2.One().Mul(diff/2));
	}
	
	public Vector2 ConvertPixelToWorldUnit(Vector2 pixelUnits) {
		return new Vector2(
			pixelUnits.x / pixelsPerWorldUnit.x,
			pixelUnits.y / pixelsPerWorldUnit.y
		);
	}
	
	// might need to check
	public Vector2 CalculateScreenToWorld(Vector2 pixelLocation) {
		Vector2 worldLocation = new Vector2(pixelLocation).Sub(screenSpace.position);
		worldLocation.x /= pixelsPerWorldUnit.x;
		worldLocation.y /= pixelsPerWorldUnit.y;
		
		return worldLocation.Add(position);
	}
	
	public boolean isInCameraBounds(Vector2 worldLocation, Vector2 worldSize) {
		return Collision.AABB(position, worldLocation, worldSpace, worldSize);
	}
}
