package javax.lang;

public class Strings {
	
	public static String string(Object object) {
		return object.toString();
	}
	
	public static String[] strings(Object... objects) {
		String[] strings = new String[objects.length];
		for (int i = 0; i < objects.length; i++)
			strings[i] = string(objects[i]);
		
		return strings;
	}
	
	public static String join(String... strings) {
		return join(strings, " ");
	}
	
	public static String join(String[] strings, String seperator) {
		return join(strings, "", seperator, "");
	}
	
	public static String join(String[] strings, String start, String seperator, String end) {
		StringBuffer buffer = new StringBuffer(start);
		for (int i = 0; i < strings.length; i++) {
			buffer.append(strings[i]);
			if (i < strings.length - 1)
				buffer.append(seperator);
		}
		
		buffer.append(end);
		return buffer.toString();
	}
}
