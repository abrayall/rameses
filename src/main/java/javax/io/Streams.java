package javax.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public class Streams {
	
	public static OutputStream copy(InputStream source, OutputStream target) throws Exception {
		return copy(source, target, true);
	}
	
	public static OutputStream copy(InputStream source, OutputStream target, boolean shouldClose) throws Exception {
		int bytes = 0;
		byte[] buffer = new byte[1024];
		
		try {
			while ((bytes = source.read(buffer)) != -1)
				target.write(buffer, 0, bytes);
			
		} finally {
			if (shouldClose) close(source);
			if (shouldClose) close(target);
		}

		return target;
	}
	
	public static String read(InputStream source) throws Exception {
		return copy(source, new ByteArrayOutputStream()).toString();
	}
		
	public static OutputStream write(byte[] content, OutputStream target) throws Exception {
		return copy(new ByteArrayInputStream(content), target);
	}
	
	public static OutputStream write(String content, OutputStream target) throws Exception {
		return write(content.getBytes(), target);
	}
	
	public static void write(InputStream source, OutputStream target) throws Exception {
		copy(source, target);
	}
	
	public static void close(Closeable... closeables) {
		for (Closeable closeable : closeables)
			try { closeable.close(); } catch (Exception e) {}
	}
}
