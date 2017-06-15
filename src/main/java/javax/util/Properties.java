package javax.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.lang.Try;
import javax.net.Urls;

@SuppressWarnings("serial")
public class Properties extends java.util.Properties {

	public Properties() {}
	
	public Properties(String key, String value) {
		this.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public Properties(Map.Entry<Object, Object>... values) {
		this.set(values);
	}
	
	public Properties(Property... properties) {
		this.set(properties);
	}
	
	public Properties(Map<Object, Object> values) {
		this.set(values);
	}

	public Properties(File file) {
		this(file, Map.map());
	}
	
	public Properties(File file, Properties defaultValues) {
		this(() -> new FileInputStream(file), defaultValues);
	}
	
	public Properties(File file, Map<Object, Object> defaultValues) {
		this(() -> new FileInputStream(file), defaultValues);
	}
	
	@SuppressWarnings("unchecked")
	public Properties(File file, Map.Entry<Object, Object>... defaultValues) {
		this(file, Map.map(defaultValues));
	}
	
	public Properties(File file, Property... defaultValues) {
		this(file, properties(defaultValues));
	}

	public Properties(String url) {
		this(Urls.url(url));
	}
	
	public Properties(URL url) {
		this(url, Map.map());
	}
	
	public Properties(URL url, Map<Object, Object> defaultValues) {
		this(url, properties(defaultValues));
	}
	
	public Properties(URL url, Properties defaultValues) {
		this(() -> url.openStream(), defaultValues);
	}
	
	@SuppressWarnings("unchecked")
	public Properties(URL url, Map.Entry<Object, Object>... defaultValues) {
		this(url, Map.map(defaultValues));
	}
	
	public Properties(URL url, Property... defaultValues) {
		this(url, properties(defaultValues));
	}
	
	public Properties(InputStream stream) {
		this(stream, Map.map());
	}
	
	public Properties(InputStream stream, Properties defaultValues) {
		this.load(stream, defaultValues);
	}
	
	public Properties(InputStream stream, Map<Object, Object> defaultValues) {
		this.load(stream, defaultValues);
	}	
	
	@SuppressWarnings("unchecked")
	public Properties(InputStream stream, Map.Entry<Object, Object>... defaultValues) {
		this.load(stream, properties(defaultValues));
	}

	public Properties(InputStream stream, Property... defaultValues) {
		this.load(stream, properties(defaultValues));
	}
	
	public Properties(Callable<InputStream> stream, Map<Object, Object> defaultValues) {
		this(stream, properties(defaultValues));
	}

	public Properties(Callable<InputStream> stream, Properties defaultValues) {
		Try.attempt(() -> this.load(stream.call()), () -> this.set(defaultValues));
	}

	
	@SuppressWarnings("unchecked")
	public Properties(Callable<InputStream> stream, Map.Entry<Object, Object>... defaultValues) {
		this(stream, Map.map(defaultValues));
	}

	public Properties(Callable<InputStream> stream, Property... defaultValues) {
		this(stream, properties(defaultValues));
	}

	public Properties empty() {
		this.clear();
		return this;
	}
	
	public Properties set(Map<Object, Object> values) {
		for (Object key : values.keySet())
			this.put(key, values.get(key));
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Properties set(Map.Entry<Object, Object>... values) {
		return this.set(Map.map(values));
	}
	
	public Properties set(Property... properties) {
		return this.set(properties(properties));
	}
		
	public Properties set(Properties properties) {
		for (Object key : properties.keySet())
			this.put(key, properties.get(key));
		
		return this;
	}
	
	public Properties load(InputStream stream, Properties defaultValues) {
		Try.attempt(() -> this.load(stream), () -> this.set(defaultValues));
		return this;
	}
	
	public Properties load(InputStream stream, Map<Object, Object> defaultValues) {
		return load(stream, properties(defaultValues));
	}
	
	@SuppressWarnings("unchecked")
	public Properties load(InputStream stream, Map.Entry<Object, Object>... defaultValues) {
		return load(stream, properties(defaultValues));
	}
	
	public Properties load(InputStream stream, Property... defaultValues) {
		return load(stream, properties(defaultValues));
	}
	
	public Properties load(File file, Properties defaultValues) {
		return this.load(() -> new FileInputStream(file), defaultValues);
	}
	
	public Properties load(Callable<InputStream> stream, Properties defaultValues) {
		Try.attempt(() -> this.load(stream.call()), () -> this.set(defaultValues));
		return this;
	}
	
	public static Properties properties() {
		return new Properties();
	}
	
	public static Properties properties(Map<Object, Object> values) {
		return new Properties(values);
	}
	
	@SuppressWarnings("unchecked")
	public static Properties properties(Map.Entry<Object, Object>... values) {
		return new Properties(values);
	}
	
	public static Properties properties(Property... properties) {
		return new Properties(properties);
	}
	
	public static Properties properties(String url) throws Exception {
		return new Properties(url);
	}
	
	public static Properties properties(URL url, Properties defaultValues) {
		return new Properties(url, defaultValues);
	}
	
	public static Properties properties(File file) throws Exception {
		return new Properties(file);
	}
	
	public static Properties properties(File file, Properties defaultValues) {
		return new Properties(file, defaultValues);
	}
	
	public static Properties properties(InputStream input) {
		return new Properties(input);
	}
	
	public static Properties properties(InputStream input, Properties defaultValues) {
		return new Properties(input, defaultValues);
	}
	
	public class Property extends Map.Entry<String, String> {
		public Property(String key, String value) {
			super(key, value);
		}
	}
}
