package javax.lang;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.util.List;
import javax.util.Map;
import static javax.util.Map.*;


public class Strings {
	
	public static String string(Object object) {
		return object.toString();
	}
	
	public static String string(String string) {
		return new String(string);
	}
	
	public static String string(char... characters) {
		return new String(characters);
	}
	
	public static String[] strings(Object... objects) {
		String[] strings = new String[objects.length];
		for (int i = 0; i < objects.length; i++)
			strings[i] = string(objects[i]);
		
		return strings;
	}
	
	public static String character(String string, int index) {
		return string(string.charAt(index));
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
	
	public static String between(String string, int start, int end) {
		if (string.length() > start && string.length() > end)
			return string.substring(start, end);
		else if (string.length() > start)
			return string.substring(start);
		
		return "";
	}
	
	public static String between(String string, String start, String end) {
		return List.list(extract(string, start, end)).get(0, "");
	}
	
	public static String[] extract(String string, String pattern) {
		List<String> tokens = List.list();
		Matcher matcher = Pattern.compile(pattern).matcher(string);
		while (matcher.find())
			tokens.add(matcher.group(1));
		
		return tokens.toArray(new String[0]);
	}
	
	public static String[] extract(String string, String start, String end) {
		return extract(string, Pattern.quote(start) + "(.*?)" + Pattern.quote(end));
	}
	
	public static String collapse(String string) {
		return collapse(string, "\\s.*", " ");
	}
	
	public static String collapse(String string, String pattern) {
		return collapse(string, pattern, character(string, 0));
	}
	
	public static String collapse(String string, String pattern, String replacement) {
		return string.replaceAll(pattern, replacement);
	}
	
	public static String head(String string) {
		return between(string, 0, 1);
	}
	
	public static String tail(String string) {
		return between(string, 1, string.length());
	}
	
	public static String first(String string) {
		return head(string);
	}
	
	public static String last(String string) {
		if (string.length() == 0)
			return "";
		
		return between(string, string.length() - 1, string.length());
	}
	
	public static String append(String string, String... suffixes) {
		StringBuffer buffer = new StringBuffer(string);
		for (String suffix : suffixes)
			buffer.append(suffix);
		
		return buffer.toString();
	}
	
	public static String prepend(String string, String... prefixes) {
		StringBuffer buffer = new StringBuffer();
		for (String prefix : prefixes)
			buffer.append(prefix);
		
		return buffer.append(string).toString();
	}
	
	public static String surround(String string, String prefix, String suffix) {
		return prepend(append(string, suffix), prefix);
	}
	
	public static String format(String string, Object... parameters) {
		return format(string, map(), parameters);
	}

	public static String format(String string, Map<String, Function<List<String>, String>> functions, Object... parameters) {
		return new Formatter(functions).format(string, parameters);
	}

	public static String format(String string, Map<String, Object> parameters) {
		return format(string, map(), parameters);
	}
	
	public static String format(String string, Map<String, Function<List<String>, String>> functions, Map<String, Object> parameters){
		return new Formatter(functions).format(string, parameters);
	}
	
	public static class Formatter {
		
		protected static String EXPRESSION_START = "${";
		protected static String EXPRESSION_END = "}";
		protected static Pattern FUNCTION = Pattern.compile("(.*)\\((.*)\\)");
		
		protected Map<String, Function<List<String>, String>> functions = map(
			entry("trim", parameters -> parameters.get(0, "").trim()),
			entry("truncate", parameters -> parameters.get(0, "").substring(0, Integer.parseInt(parameters.get(1, "1"))))
		);
		
		public Formatter() {
			this(map());
		}
		
		public Formatter(Map<String, Function<List<String>, String>> functions) {
			this.functions.putAll(functions);
		}
		
		public String format(String string, Object... parameters) {
			return format(string, map(parameters));
		}
		
		public String format(String string, java.util.Map<String, Object> parameters) {
			return format(string, map(parameters));
		}
		
		public String format(String string, Map<String, Object> parameters) {
			for (String expression : extract(string, EXPRESSION_START, EXPRESSION_END))
				string = handle(string, expression, parameters);
			
			return string;
		}
		
		protected String handle(String string, String expression, Map<String, Object> parameters) {
			String value = "";
			if (parameters.containsKey(expression))
				value = parameters.get(expression, "").toString();
			else if (expression.contains("(") && expression.contains(")"))
				value = execute(expression, parameters);

			return string.replaceAll(Pattern.quote(EXPRESSION_START + expression + EXPRESSION_END), value);
		}
		
		protected String execute(String expression, Map<String, Object> parameters) {
			Matcher matcher = FUNCTION.matcher(expression);
			if (matcher.matches() && this.functions.containsKey(matcher.group(1))) 
				return functions.get(matcher.group(1)).apply(parameters(matcher.group(2), parameters));
			
			return "";
		}
		
		protected List<String> parameters(String arguments, Map<String, Object> variables) {
			return List.list(arguments.split(",")).map(string -> string.trim()).replace(0, value -> {
				return variables.get(value, value).toString();
			});
		}
	}
}
