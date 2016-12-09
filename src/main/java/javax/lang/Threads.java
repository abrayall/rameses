package javax.lang;

import static javax.lang.Time.*;

import java.util.concurrent.TimeUnit;

public class Threads {

	public static Thread thread(Runnable code) {
		return new Thread(code);
	}
	
	public static Thread run(Runnable code) {
		Thread thread = thread(code);
		thread.start();
		return thread;
	}
	
	public static Thread run(int seconds, Runnable code) {
		return run(seconds, TimeUnit.SECONDS, code);
	}
	
	public static Thread run(int delay, TimeUnit unit, Runnable code) {
		sleep(delay, unit);
		return run(code);
	}
	
	public static Thread interval(int timeout, Runnable code) {
		return interval(timeout, TimeUnit.SECONDS, code);
	}
	
	public static Thread interval(int timeout, TimeUnit unit, Runnable code) {
		return launch(timeout, unit, () -> {
			while (Thread.interrupted() != true) {
				code.run();
				Time.sleep(timeout, unit);
			}
		});
	}
	
	public static Thread launch(Runnable code) {
		return launch(0, code);
	}
	
	public static Thread launch(int seconds, Runnable code) {
		return launch(seconds, TimeUnit.SECONDS, code);
	}
	
	public static Thread launch(int delay, TimeUnit unit, Runnable code) {
		return run(() -> {
			sleep(delay, unit);
			run(code);
		});
	}
	
	public static void interrupt(Thread thread) {
		thread.interrupt();
	}
	
	public static void interrupt(int seconds, Thread thread) {
		interrupt(seconds, TimeUnit.SECONDS, thread);
	}
	
	public static void interrupt(int delay, TimeUnit unit, Thread thread) {
		Threads.run(delay, unit, () -> {
			thread.interrupt();
		});
	}
}
