package com.christian.adventureengine.data;

import com.christian.adventureengine.utils.Deserializer;
import com.christian.adventureengine.utils.Serializer;

public interface ISerializable {
	public void Serialize(Serializer serializer);
//	public void Deserialize(Deserializer deserializer);
}
