package javax.lang;

import static javax.util.Map.map;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Runtime {
	public static Process execute(String... command) throws IOException {
		return execute(new File("."), command);
	}
	
	public static Process execute(File directory, String... command) throws IOException {
		return execute(map(), directory, command);
	}
	
	public static Process execute(Map<String, String> environment, File directory, String... command) throws IOException {
		return process(environment, directory, command).start();
	}
	
	public static ProcessBuilder process(Map<String, String> environment, File directory, String... command) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory(directory);
		builder.environment().putAll(environment);
		return builder;
	}
}