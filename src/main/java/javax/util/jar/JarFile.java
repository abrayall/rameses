package javax.util.jar;

import javax.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.jar.JarEntry;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.lang.System;
import javax.util.List;

import static javax.net.Urls.*;
import static javax.io.File.*;

public class JarFile extends java.util.jar.JarFile {

	protected File file;
	
	public JarFile(String file) throws IOException {
		this(file(file));	
	}
	
	public JarFile(java.io.File file) throws IOException {
		this(file(file));
	}
	
	public JarFile(File file) throws IOException {
		super(file.toFile());
		this.file = file;
	}
	
	public Stream<JarEntry> stream() {
		return stream(".*");
	}
	
	public Stream<JarEntry> stream(String pattern) {
		return stream(Pattern.compile(pattern));
	}
	
	public Stream<JarEntry> stream(Pattern pattern) {
		return stream(this.entries()).filter(entry -> pattern.matcher(entry.getName()).matches());
	}
	
	public List<JarEntry> list() {
		return List.list(this.stream());
	}

	public List<JarEntry> list(String pattern) {
		return list(Pattern.compile(pattern));
	}
	
	public List<JarEntry> list(Pattern pattern) {
		return List.list(stream(pattern));
	}
	
	public List<Class<?>> classes() {
		return classes(classloader(System.classloader()));
	}
	
	public List<Class<?>> classes(ClassLoader classloader) {
		return List.list(stream(".*\\.class").map(entry -> {
			try {
				return classloader.loadClass(entry.getName().replace(".class", "").replaceAll("/", "."));
			} catch (Exception e) {
				e.printStackTrace();
				return (Class<?>) null;
			}
		})).filter(clazz -> clazz != null);
	}

	public List<Class<?>> mains() {
		return mains(System.classloader());
	}
	
	public List<Class<?>> mains(ClassLoader classloader) {
		return classes().filter(clazz -> {
			try {
				return clazz.getMethod("main", String[].class) != null;
			} catch (Exception e) {
				return false;
			}
		});
	}
	
	public ClassLoader classloader() {
		return classloader(System.classloader());
	}
	
	public ClassLoader classloader(ClassLoader parent) {
		return new URLClassLoader(new URL[] { url(this.file.toFile().toURI()) });
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
}
