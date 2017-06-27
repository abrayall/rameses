package javax.lang;

import static javax.lang.Process.*;
import static javax.util.Map.map;

import java.io.File;
import java.util.Map;
import javax.lang.Process;

public class Runtime {
	
	public static Process execute(String... command) throws Exception {
		return process(command);
	}
	
	public static Process execute(File directory, String... command) throws Exception {
		return execute(map(), directory, command);
	}
	
	public static Process execute(Map<String, String> environment, File directory, String... command) throws Exception {
		return process(environment, directory, command);
	}
	
	public static Process execute(ProcessBuilder builder) throws Exception {
		return Process.process(builder);
	}
	
	public static void addShutdownHook(Thread hook) {
		java.lang.Runtime.getRuntime().addShutdownHook(hook);
	}
	
	public static void addShutdownHook(Executable hook) {
		addShutdownHook(new Thread() {
			public void run() {
				Try.attempt(hook);
			}
		});
	}
}