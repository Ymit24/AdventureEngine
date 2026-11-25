package com.christian.adventureengine.data;

import com.christian.adventureengine.utils.Deserializer;
import com.christian.adventureengine.utils.Serializer;

public class WorldObject implements ISerializable {
	public Vector2 Position;
	public Vector2 Size;
	private Box box;

	public WorldObject(Vector2 position) {
		this.Position = position;
		this.Size = Vector2.One();
	}
	
	public WorldObject(Vector2 position, Vector2 size) {
		this.Position = position;
		this.Size = size;
	}

	public Box GetBox() {
		if (box == null) {
			box = new Box(Position, Size);
			return box;
		}
		if (box.position.equals(Position) && box.size.equals(Size))
			return box;
		box = new Box(Position, Size);
		return box;
	}

	// used for serialization
	private WorldObject() {}

	@Override
	public void Serialize(Serializer serializer) {
		Position.Serialize(serializer);
		Size.Serialize(serializer);
	}

	public static WorldObject Deserialize(Deserializer deserializer) {
		WorldObject worldObject = new WorldObject();
		worldObject.Position = Vector2.Deserialize(deserializer);
		worldObject.Size = Vector2.Deserialize(deserializer);
		
		return worldObject;
	}
}
