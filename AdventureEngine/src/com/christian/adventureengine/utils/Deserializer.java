package com.christian.adventureengine.utils;

public class Deserializer {
	private byte[] data;
	private int index;
	
	public Deserializer(byte[] data) {
		this.data = data;
		index = 0;
	}
	
	public int ReadInt() {
		int val =
			data[index] << 24 | (data[index+1] & 0xFF) << 16 |
			(data[index+2] & 0xFF) << 8 | (data[index+3] & 0xFF);
		index += 4;
		return val;
	}
	
	public float ReadFloat() {
		return Float.intBitsToFloat(ReadInt());
	}
	
	public String ReadString() {
		int length = ReadInt();
		byte[] stringBytes = new byte[length];
		for (int i = index; i < index+length; i++) {
			stringBytes[i-index] = data[i];
		}
		index += length;
		return new String(stringBytes);
	}
	
	public boolean ReadBoolean() {
		boolean val = data[index] == 1;
		index++;
		return val;
	}
}
