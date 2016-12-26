package javax.io;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StringInputStream extends ByteArrayInputStream {

	public StringInputStream(String string) {
		super(string.getBytes());
	}
	
	public StringBuffer read(int length) {
		return read(0, length);
	}
	
	public StringBuffer read(int offset, int length) {
		return read(new StringBuffer(), offset, length);
	}
	
	public StringBuffer read(StringBuffer buffer, int offset, int length) {
		return read(buffer, Charset.defaultCharset(), offset, length);
	}
	
	public StringBuffer read(StringBuffer buffer, String characterSet, int offset, int length) {
		return read(buffer, Charset.forName(characterSet), offset, length);
	}
	
	public StringBuffer read(StringBuffer buffer, Charset characterSet, int offset, int length) {
		byte[] bytes = new byte[length];
		this.read(bytes, offset, length);
		buffer.append(string(bytes, characterSet));
		return buffer;
	}
	
	protected String string(byte[] bytes, Charset characterSet) {
		return characterSet.decode(ByteBuffer.wrap(bytes)).toString();
	}
}
