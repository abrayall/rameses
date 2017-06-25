package javax.lang;

import static javax.lang.Strings.*;

public class System {
	public static void print(Object... objects) {
		print(strings(objects));
	}
	
	public static void println(Object... objects) {
		println(strings(objects));
	}
	
	public static void print(String... strings) {
		print(join(strings));
	}
	
	public static void println(String... strings) {
		println(join(strings));
	}
	
	public static void printf(String string, Object... objects) {
		print(format(string, objects));
	}
	
	public static void printlnf(String string, Object... objects) {
		println(format(string, objects));
	}
		
	public static void print(String string) {
		java.lang.System.out.print(string);
	}
	
	public static void println(String string) {
		java.lang.System.out.println(string);
	}
	
	public static long now() {
		return java.lang.System.currentTimeMillis();
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
