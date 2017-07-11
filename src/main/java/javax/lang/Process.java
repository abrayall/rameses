package javax.lang;

import static javax.lang.Threads.thread;
import static javax.lang.Try.attempt;
import static javax.util.Map.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.util.concurrent.Future;

public class Process extends java.lang.Process {

	protected java.lang.Process process;
	
	public Process(java.lang.Process process) {
		this.process = process;
	}
	
	public OutputStream getOutputStream() {
		return this.process.getOutputStream();
	}

	public InputStream getInputStream() {
		return this.process.getInputStream();
	}

	public InputStream getErrorStream() {
		return this.process.getErrorStream();
	}

	public int waitFor() throws InterruptedException {
		return this.process.waitFor();
	}

	public int exitValue() {
		return this.process.exitValue();
	}

	public void destroy() {
		this.process.destroy();
	}
	
	public Process kill() {
		this.destroyForcibly();
		return this;
	}
	
	public ProcessFuture<Integer> future() {
		return new ProcessFuture<Integer>(this).onTerminate((code, future) -> future.complete(code));
	}
	
	public <T> ProcessFuture<T> future(java.lang.Class<? extends T> type) {
		return new ProcessFuture<T>(this);
	}
	
	public Process onExit(Consumer<Integer> callback) {
		thread(() -> {
			callback.accept(attempt(() -> waitFor()));
		}).start();
		
		return this;
	}
	
	public static Process process(java.lang.Process process) {
		return new Process(process);
	}
	
	public static Process process(java.lang.ProcessBuilder builder) throws Exception {
		return new Process(builder.start());
	}
	
	public static Process process(String... command) throws Exception {
		return process(new File("."), command);
	}
	
	public static Process process(File directory, String... command) throws Exception {
		return process(map(), directory, command);
	}
	
	public static Process process(Map<String, String> environment, File directory, String... command) throws Exception {
		return process(builder(environment, directory, command));
	}
	
	public static ProcessBuilder builder(String... command) throws IOException {
		return builder(new File("."), command);
	}
	
	public static ProcessBuilder builder(File directory, String... command) throws IOException {
		return builder(map(), directory, command);
	}
	
	public static ProcessBuilder builder(Map<String, String> environment, File directory, String... command) throws IOException {
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.directory(directory);
		builder.environment().putAll(environment);
		return builder;
	}
	
	public static class ProcessFuture<T> extends Future<T> {
		
		protected Process process;
		
		public ProcessFuture(Process process) {
			this.process = process;
		}
		
		public ProcessFuture(java.lang.Process process) {
			this.process = process(process);
		}
		
		public ProcessFuture<T> onOutput(BiConsumer<String, ProcessFuture<T>> handler) {
			new BufferedReader(new InputStreamReader(process.getInputStream())).lines().forEach(line -> {
				handler.accept(line, this);
			});
			
			return this;
		}
		
		public void cancel() {
			this.process.destroyForcibly();
		}
		
		public ProcessFuture<T> onTerminate(BiConsumer<Integer, ProcessFuture<T>> handler) {
			ProcessFuture<T> future = this;
			thread(() -> {
				attempt(() -> process.waitFor());
				handler.accept(process.exitValue(), future);
			}).start();
			return this;
		}
	}
}
