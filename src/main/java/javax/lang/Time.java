package javax.lang;

import static javax.lang.Try.*;

import java.util.concurrent.TimeUnit;

public class Time {

	public static long now() {
		return System.currentTimeMillis();
	}
	
	public static void sleep(int seconds) {
		sleep(seconds, TimeUnit.SECONDS);
	}
	
	public static void sleep(int delay, TimeUnit unit) {
		sleep(delay, unit, () -> {});
	}
		
	public static void sleep(int seconds, Executable complete) {
		sleep(seconds, TimeUnit.SECONDS, complete);
	}
	
	public static void sleep(int interval, TimeUnit unit, Executable complete) {
		sleep(interval, unit, complete, () -> {});
	}
	
	public static void sleep(int seconds, Executable complete, Executable error) {
		sleep(seconds, TimeUnit.SECONDS, complete, error);
	}
	
	public static void sleep(int interval, TimeUnit unit, Executable complete, Executable error) {
		attempt(() -> {
			Thread.sleep(unit.toMillis(interval));
		}, error, complete);
	}
}
