package javax.lang;

import static javax.lang.Strings.*;

public class System {
	public static void print(Object... objects) {
		println(strings(objects));
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
		
	public static void print(String string) {
		java.lang.System.out.print(string);
	}
	
	public static void println(String string) {
		java.lang.System.out.println(string);
	}
	
	public static long now() {
		return java.lang.System.currentTimeMillis();
	}
}
