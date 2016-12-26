package javax.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class StringOutputStream extends ByteArrayOutputStream {

	protected Charset characterSet = Charset.defaultCharset();
	
	public StringOutputStream() {
		super();
	}
	
	public StringOutputStream(Charset characterSet) {
		super();
		this.characterSet = characterSet;
	}
	
	public StringOutputStream(int size, Charset characterSet) {
		super(size);
		this.characterSet = characterSet;
	}
	
	public StringOutputStream write(String string) throws IOException {
		return write(string, this.characterSet);
	}
	
	public StringOutputStream write(String string, Charset characterSet) throws IOException {
		write(bytes(string, characterSet));
		return this;
	}
	
	public byte[] bytes(String string, Charset characterSet) {
		return characterSet.encode(string).array();
	}
	
	//public String toString() {
	//	return this.characterSet.decode(ByteBuffer.wrap(this.buf)).toString();
	//}
}
