package javax.lang;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class TryTest {

	public void testAttempt() throws Exception {
		Assert.isNotNull(Try.attempt(() -> 1 + 1));
		Assert.isNull(Try.attempt(() -> 1 / 0));
		
		Assert.check(Try.attempt(() -> 1 + 1, 24) == 2);
		Assert.check(Try.attempt(() -> 1 / 0, 24) == 24);
		
		Assert.check(Try.attempt((Callable<Integer>)() -> 1 + 1, (Supplier<Integer>)() -> 4) == 2);
		Assert.check(Try.attempt((Callable<Integer>)() -> 1 / 0, (Supplier<Integer>)() -> 24) == 24);
	}
	
	public static void main(String[] arguments) throws Exception {
		new TryTest().testAttempt();
	}
}
