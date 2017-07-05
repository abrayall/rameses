package javax.lang;

import static javax.lang.Strings.*;

import java.io.InputStream;
import java.io.PrintStream;

import javax.io.File;
import javax.util.List;
import javax.util.Map;
import javax.util.Properties;


public class System {
	
	public static PrintStream out = java.lang.System.out;
	public static PrintStream err = java.lang.System.err;
	public static InputStream in = java.lang.System.in;
	
	public static PrintStream out() {
		return out;
	}
	
	public static PrintStream err() {
		return err;
	}
	
	public static InputStream in() {
		return in;
	}
	
	public static void print(Object... objects) {
		print(out(), strings(objects));
	}
	
	public static void println(Object... objects) {
		println(out(), strings(objects));
	}
	
	public static void print(PrintStream stream, Object... objects) {
		print(stream, strings(objects));
	}
	
	public static void println(PrintStream stream, Object... objects) {
		println(stream, strings(objects));
	}
	
	public static void print(String... strings) {
		print(out(), join(strings));
	}
	
	public static void println(String... strings) {
		println(out(), join(strings));
	}

	public static void print(PrintStream stream, String... strings) {
		print(stream, join(strings));
	}
	
	public static void println(PrintStream stream, String... strings) {
		println(stream, join(strings));
	}

	public static void printf(String string, Object... objects) {
		print(out(), format(string, objects));
	}
	
	public static void printlnf(String string, Object... objects) {
		println(out(), format(string, objects));
	}
	
	public static void printf(PrintStream stream, String string, Object... objects) {
		print(stream, format(string, objects));
	}
	
	public static void printlnf(PrintStream stream, String string, Object... objects) {
		println(stream, format(string, objects));
	}
		
	public static void print(String string) {
		print(out(), string);
	}
	
	public static void println(String string) {
		println(out(), string);
	}
	
	public static void print(PrintStream stream, String string) {
		stream.print(string);
	}
	
	public static void println(PrintStream stream, String string) {
		stream.println(string);
	}
	
	public static long now() {
		return java.lang.System.currentTimeMillis();
	}
	
	public static void exit() {
		java.lang.System.exit(1);
	}
	
	public static void exit(int code) {
		java.lang.System.exit(code);
	}
	
	public static Runtime getRuntime() {
		return runtime();
	}
	
	public static Runtime runtime() {
		return new Runtime();
	}
	
	public static ClassLoader getClassloader() {
		return ClassLoader.getSystemClassLoader();
	}
	
	public static ClassLoader classloader() {
		return getClassloader();
	}
	
	public static Classloader classloader(File... classpath) {
		return new Classloader(classpath);
	}
	
	public static Classloader classloader(List<File> classpath) {
		return new Classloader(classpath);
	}
	
	public static java.lang.Class<?> loadClass(String name) throws ClassNotFoundException {
		return classloader().loadClass(name);
	}
	
	public static List<File> classpath() {
		return List.list(System.getProperties().getOrDefault("java.class.path", "").toString().split(java.io.File.pathSeparator)).map(path -> new File(path));
	}
	
	public static Map<String, String> getEnvironment() {
		return Map.map(java.lang.System.getenv());
	}
	
	public static Map<String, String> environment() {
		return getEnvironment();
	}
	
	public static Properties getProperties() {
		return Properties.properties(java.lang.System.getProperties());
	}
	
	public static Properties properties() {
		return getProperties();
	}
	
	public static String operatingSystem() {
		return java.lang.System.getProperty("os.name").toLowerCase();
	}

	public static boolean isWindows() {
        return operatingSystem().indexOf("win") >= 0;
    }

	public static boolean isMac() {
        return operatingSystem().indexOf("mac") >= 0;
    }

	public static boolean isUnix() {
        return operatingSystem().indexOf("nux") >= 0;
    }
	
	public static boolean isSolaris() {
		return operatingSystem().contains("solaris") || operatingSystem().contains("sunos");
	}
	
	public static boolean isAndroid() {
		return java.lang.System.getProperty("os.name").toLowerCase().contains("android");
	}
}
