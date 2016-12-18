package javax.lang;

import static javax.lang.Assert.*;

import java.util.concurrent.TimeUnit;

public class ThreadsTest {

	public void testThreads() throws Exception {
		long now = System.currentTimeMillis();
		Threads.run(1, TimeUnit.SECONDS, () -> {
			try {
				check(System.currentTimeMillis() - 1000 > now);
				System.out.println("here");
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		});
	}
	
	public static void main(String[] arguments) throws Exception {
		new ThreadsTest().testThreads();
	}
}
