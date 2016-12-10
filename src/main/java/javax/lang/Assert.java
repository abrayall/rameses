package javax.lang;

import java.util.concurrent.Callable;

public class Assert {

	public static String DEFAULT_MESSAGE = "asserion check failed";
	
	public static boolean equals(Object expected, Object actual) throws Exception {
		return equals(expected, actual, DEFAULT_MESSAGE + ": " + expected.toString() + " is not equal to " + actual);
	}

	public static boolean equals(Object expected, Object actual, String message) throws Exception {
		return check(expected.equals(actual), message);
	}
	
	public static boolean isEqual(Object expected, Object actual) throws Exception {
		return equals(expected, actual);
	}

	public static boolean isEqual(Object expected, Object actual, String message) throws Exception {
		return equals(expected, actual, message);
	}
	
	public static boolean notEqual(Object expected, Object actual) throws Exception {
		return notEqual(expected, actual, DEFAULT_MESSAGE + ": " + expected.toString() + " is equal to " + actual);
	}
	
	public static boolean notEqual(Object expected, Object actual, String message) throws Exception {
		return check(expected.equals(actual) == false, message);
	}
	
	public static boolean isNull(Object actual) throws Exception {
		return isNull(actual, DEFAULT_MESSAGE + ": " + actual + " is not null");
	}
	
	public static boolean isNull(Object actual, String message) throws Exception {
		return check(actual == null, message);
	}
	
	public static boolean isNotNull(Object actual) throws Exception {
		return isNotNull(actual, DEFAULT_MESSAGE + ": " + actual.toString() + " is null");
	}
	
	public static boolean isNotNull(Object actual, String message) throws Exception {
		return check(actual != null, message);
	}

	public static boolean check(Callable<Boolean> code) {
		return check(code, DEFAULT_MESSAGE);
	}
	
	public static boolean check(Callable<Boolean> code, String message) {
		try {
			return check(code.call(), message);
		} catch (Exception e) {
			throw new RuntimeException(message, e);
		}
	}
	
	public static boolean check(boolean value) throws Exception {
		return check(value, DEFAULT_MESSAGE);
	}
	
	public static boolean check(boolean value, String message) throws Exception {
		if (value == false)
			throw new RuntimeException(message);
		
		return true;
	}
}
