package com.christian.adventureengine.net;

import java.util.Map;

import com.christian.adventureengine.utils.Deserializer;
import com.christian.adventureengine.utils.Serializer;

public abstract class Message {
	public IMessageType Type;
	
	public interface IMessageDeserializer {
		Message Deserialize(Deserializer deserializer);
	}
	
	public static Map<IMessageType,IMessageDeserializer> deserializationCallbacks;
	
	public static void RegisterMessageDeserializer(IMessageType type, IMessageDeserializer callback) {
		deserializationCallbacks.put(type,  callback);
	}
		
	public Message() {}
	
	public Message(IMessageType type) {
		Type = type;
		
	}
	
	public void Serialize(Serializer serializer) {
		serializer.WriteString(Type.toString());
		SerializeBody(serializer);
	}
	public static Message Deserialize(IMessageType type, Deserializer deserializer) {
		// TODO: Add not registered catching.
		return deserializationCallbacks.get(type).Deserialize(deserializer);
	}
	protected abstract void SerializeBody(Serializer serializer);
}
