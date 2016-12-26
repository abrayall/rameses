package javax.lang;

import java.util.concurrent.Callable;

public class AssertTest {

	public void testAssert() throws Exception {
		shouldPass(true);
		shouldPass(true, "Foo");
		shouldPass(() -> true);
		shouldPass(() -> true, "1 == 1");
		
		shouldFail(false);
		shouldFail(false, "Foo");
		
		shouldPass(() -> Assert.equals("String", "String"));
		shouldPass(() -> Assert.equals(true, true));
		shouldPass(() -> Assert.equals(1, 1));
		shouldPass(() -> Assert.equals(1.0, 1.0));
		
		shouldFail(() -> Assert.equals("String", "Not Same"));
		shouldFail(() -> Assert.equals(true, false));
		shouldFail(() -> Assert.equals(1, 10));
		shouldFail(() -> Assert.equals(1.0, 1.1));		
	}
	
	public static void shouldPass(Callable<Boolean> code) throws Exception {
		shouldPass(code, "");
	}
	
	public static void shouldPass(Callable<Boolean> code, String message) throws Exception {
		shouldPass(code.call(), message);
	}
	
	public static void shouldPass(boolean value) throws Exception {
		shouldPass(value, "");
	}
	
	public static void shouldPass(boolean value, String message) throws Exception {
		try {
			Assert.check(value, message);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("assertion should have passed", e);
		}
	}
	
	public static void shouldFail(Callable<Boolean> code) throws Exception {
		shouldFail(code, "assertion should have failed");
	}
	
	public static void shouldFail(Callable<Boolean> code, String message) throws Exception {
		try {
			Assert.check(code, message);
			throw new Exception("assertion should have failed");
		} catch (Exception e) {
			if (e.getMessage().equals(message) == false)
				throw new Exception("assertion message should have been '" + message + "'");
		}
	}
	
	public static void shouldFail(boolean value) throws Exception {
		shouldFail(value, Assert.DEFAULT_MESSAGE);
	}
	
	public static void shouldFail(boolean value, String message) throws Exception {
		shouldFail(() -> value, message);
	}
	
	public static void main(String[] arguments) throws Exception {
		new AssertTest().testAssert();
	}
}
