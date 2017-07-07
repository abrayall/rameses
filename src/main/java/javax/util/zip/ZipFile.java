package javax.util.zip;

import static javax.io.File.*;
import static javax.io.Streams.*;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;

import javax.io.File;


public class ZipFile extends java.util.zip.ZipFile {

	public ZipFile(String path) throws Exception {
		this(file(path));
	}
	
	public ZipFile(File file) throws Exception {
		this(file.toFile());
	}

	public ZipFile(java.io.File file) throws Exception {
		super(file);
	}
	
	public ZipFile extract(java.io.File path) throws Exception {
		return extract(file(path));
	}
	
	public ZipFile extract(java.io.File path, BiFunction<ZipEntry, File, File> handler) throws Exception {
		return extract(file(path), handler);
	}
	
	public ZipFile extract(File path) throws Exception {
		return extract(path, (entry, file) -> file);
	}
	
	public ZipFile extract(File path, BiConsumer<ZipEntry, File> handler) throws Exception {
		return extract(path, (entry, file) -> {
			handler.accept(entry,  file);
			return file;
		});
	}
	
	public ZipFile extract(File path, BiFunction<ZipEntry, File, File> handler) throws Exception {
		stream(this.entries()).forEach(entry -> {
			File file = file(path, entry.getName());
			try {
				extract(entry, handler.apply(entry, file));
			} catch (Exception e) {
			}
		});
		
		return this;
	}
	
	public ZipFile extract(ZipEntry entry, File path) throws Exception {
		if (entry != null && entry.isDirectory())
			path.mkdirs();
		else if (entry != null)
			copy(this.getInputStream(entry), path.mkdirs().outputStream()).close();
		
		return this;
	}
	
	protected <T> Stream<T> stream(Enumeration<T> e) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
			new Iterator<T>() {
				public T next() {
					return e.nextElement();
				}
                 
                public boolean hasNext() {
                	return e.hasMoreElements();
                }
			}, Spliterator.ORDERED), false
		);
	}
	
	public static ZipFile zip(File file) throws Exception {
		return new ZipFile(file);
	}
	
	public static ZipFile zip(java.io.File file) throws Exception {
		return new ZipFile(file);
	}
	
	public static ZipFile zip(String file) throws Exception {
		return new ZipFile(file);
	}
	
	public static File unzip(File file, File path) throws Exception {
		return extract(file, path);
	}
	
	public static File unzip(File file, File path, BiConsumer<ZipEntry, File> progress) throws Exception {
		return extract(file, path, progress);
	}
	
	public static File extract(File file, File path) throws Exception {
		return extract(file, path, (e, f) -> {});
	}
	
	public static File extract(File file, File path, BiConsumer<ZipEntry, File> progress) throws Exception {
		return extract(file, path, (e, f) -> {
			progress.accept(e, f);
			return f;
		});
	}
	
	public static File extract(File file, File path, BiFunction<ZipEntry, File, File> progress) throws Exception {
		zip(file).extract(path, progress).close();
		return path;
	}
}
