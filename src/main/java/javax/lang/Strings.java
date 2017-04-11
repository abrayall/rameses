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
	
	public static String pad(String string, int length) {
		return pad(string, ' ', length);
	}
	
	public static String pad(String string, Character character, int length) {
		return pad(string, character, length, "right");
	}
	
	public static String pad(String string, Character character, int length, String side) {
		if (string.length() >= length)
			return string;
		
		String padding = generate(character, length - string.length());
		if (side.equalsIgnoreCase("right"))
			return string + padding;
		else
			return padding + string;
	}

	public static String[] pad(String[] strings, int length) {
		return pad(strings, ' ', length, "right");
	}
	
	public static String[] pad(String[] strings, Character character, int length, String side) {
		for (int i = 0; i < strings.length; i++)
			strings[i] = pad(strings[i], character, length, side);
		
		return strings;
	}

	public static String generate(Character character, int length) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++)
			buffer.append(character == null ? "" : character);
		
		return buffer.toString();
	}
	
	public static String wrap(String string, int length) {
		return block(string, length, null);
	}
	
	public static String block(String string, int length) {
		return block(string, length, ' ');
	}
	
	public static String block(String string, int length, Character pad) {
		return join(pad(divide(string, length), pad, length, "right"), "\n");
	}
	
	public static String truncate(String string, int length) {
		return truncate(string, length, "");
	}
	
	public static String truncate(String string, int length, String tail) {
		if (string.length() > length)
			return string.substring(0, length - tail.length() > 0 ? length - tail.length() : length) + ((length - tail.length()) > 0 ? tail : "");
		
		return string;
	}
	
	public static String[] divide(String string, int size) {
		int count = size == 0 ? 0 : new Double(Math.ceil(string.length() / (size * 1.00))).intValue();
		String[] strings = new String[count >= 0 ? count : 0];
		for (int i = 0; i < count; i++) {
			int end = (i + 1) * size;
			strings[i] = string.substring(i * size, end < string.length() ? end : string.length());
		}
		
		return strings;
	}
}
