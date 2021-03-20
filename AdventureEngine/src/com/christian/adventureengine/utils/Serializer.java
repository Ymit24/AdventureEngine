package com.christian.adventureengine.utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Serializer {
	private ByteArrayOutputStream stream;
	
	public Serializer() {
		stream = new ByteArrayOutputStream();
	}
	
	public byte[] getBytes() {
		return stream.toByteArray();
	}
	
	public void WriteInt(int val) {
		try {
			byte[] data = new byte[] {
					(byte)((val & 0xFF000000) >> 24),
					(byte)((val & 0x00FF0000) >> 16),
					(byte)((val & 0x0000FF00) >> 8),
					(byte)((val & 0x000000FF)),
			};
			stream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void WriteFloat(float val) {
		int intBits = Float.floatToIntBits(val);
		try {
			stream.write(
				new byte[] {
					(byte)(intBits >> 24),
					(byte)(intBits >> 16),
					(byte)(intBits >> 8),
					(byte)(intBits)
				}
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void WriteString(String val) {
		try {
			WriteInt(val.length());
			stream.write(val.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void WriteBoolean(boolean val) {
		stream.write(val ? (byte)1 : (byte)0);
	}
}
