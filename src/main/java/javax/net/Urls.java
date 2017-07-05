package javax.net;

import static javax.io.File.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.BiConsumer;

import javax.io.File;
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
	
	public static URLConnection connection(String url) throws Exception {
		return url(url).openConnection();
	}
	
	public static String get(String url) throws Exception {
		return Streams.read(stream(url));
	}
	
	public static File download(String url, java.io.File path) throws Exception {
		return download(url, path, (bytes, size) -> {});
	}
	
	public static File download(String url, File path) throws Exception {
		return download(url, path, (bytes, size) -> {});
	}
	
	public static File download(String url, java.io.File path, BiConsumer<Long, Long> progress) throws Exception {
		return download(connection(url), file(path), progress);
	}
	
	public static File download(String url, File path, BiConsumer<Long, Long> progress) throws Exception {
		return download(connection(url), path, progress);
	}
	
	public static File download(URLConnection connection, File path) throws Exception {
		return download(connection, path, (bytes, size) -> {});
	}
	
	public static File download(URLConnection connection, java.io.File path) throws Exception {
		return download(connection, file(path), (bytes, size) -> {});
	}
	
	public static File download(URLConnection connection, java.io.File path, BiConsumer<Long, Long> progress) throws Exception {
		return download(connection, file(path), progress);
	}

	public static File download(URLConnection connection, File path, BiConsumer<Long, Long> progress) throws Exception {
		return download(connection.getInputStream(), path, (bytes, total) -> {
			progress.accept(total, new Long(connection.getContentLength()));
		});
	}
	
	public static File download(InputStream inputStream, java.io.File path) throws Exception {
		return download(inputStream, file(path));
	}
	
	public static File download(InputStream inputStream, File path) throws Exception {
		return download(inputStream, path, (bytes, total) -> {});
	}
	
	public static File download(InputStream inputStream, File path, BiConsumer<Integer, Long> progress) throws Exception {
		path.mkdirs();
		download(inputStream, new FileOutputStream(path.toFile()), progress);
		return path;
	}
	
	public static void download(InputStream inputStream, OutputStream outputStream) throws Exception {
		download(inputStream, outputStream, (bytes, total) -> {});
	}
	
	public static void download(InputStream inputStream, OutputStream outputStream, BiConsumer<Integer, Long> progress) throws Exception {
		int bytes = 0;
		long total = 0;
		byte[] buffer = new byte[8192];
		
		try {
			while ((bytes = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytes);
				progress.accept(bytes, total = total + bytes);
			}
			
		} finally {
			Streams.close(inputStream);
			Streams.close(outputStream);
		}
	}
}
