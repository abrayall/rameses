package javax.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import javax.io.Streams;
import javax.lang.Try;

public class Urls {
	public static URL url(String string) {
		return Try.attempt(() -> new URL(string));
	}
	
	public static URL url(URI uri) {
		return Try.attempt(() -> uri.toURL());
	}
	
	public static InputStream stream(String url) throws Exception {
		return url(url).openStream();
	}
	
	public static String get(String url) throws Exception {
		return Streams.read(stream(url));
	}
	
	public static File download(String url, File path) throws Exception {
		return download(stream(url), path);
	}
	
	public static File download(InputStream inputStream, File path) throws Exception {
		path.getParentFile().mkdirs();
		download(inputStream, new FileOutputStream(path));
		return path;
	}
	
	public static void download(InputStream inputStream, OutputStream outputStream) throws Exception {
		int bytes = 0;
		byte[] buffer = new byte[128];
		
		try {
			while ((bytes = inputStream.read(buffer)) != -1)
				outputStream.write(buffer, 0, bytes);
			
		} finally {
			Streams.close(inputStream);
			Streams.close(outputStream);
		}
	}
}
