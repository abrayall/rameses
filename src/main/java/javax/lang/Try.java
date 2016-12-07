package javax.lang;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static javax.lang.Functions.function;

public class Try<T> {

	private Supplier<T> code;
	private Exception exception;
	
	public Try(Supplier<T> code) {
		this.code = code;
	}
		
	public Try(Callable<T> code) {
		this(function(code));
	}
	
	public T run() {
		return run((T)null);
	}
	
	public T run(T defaultValue) {
		try {
			return code.get();
		} catch (Exception e) {
			this.exception = e;
			return defaultValue;
		}
	}
	
	public T run(Supplier<T> defaultValue) {
		return run(defaultValue, null);
	}
	
	public T run(Supplier<T> defaultValue, Supplier<Void> finish) {
		return run(e -> defaultValue.get(), finish);
	}
	
	public T run(Consumer<Exception> error) {
		return run(e -> {
			error.accept(e);
			return null;
		});
	}
		
	public T run(Function<Exception, T> defaultValue) {
		return run(defaultValue, () -> null);
	}
	
	public T run(Function<Exception, T> defaultValue, Supplier<Void> finish) {
		try {
			return code.get();
		} catch (Exception e) {
			this.exception = e;
			return defaultValue.apply(e);
		} finally {
			if (finish != null) finish.get();
		}
	}
	
	public boolean failed() {
		return this.exception != null;
	}
	
	public Exception exception() {
		return this.exception;
	}
	
	public static <T> T attempt(Callable<T> code) {
		return attempt(code, (T)null);
	}
	
	public static <T> T attempt(Callable<T> code, T defaultValue) {
		return new Try<T>(code).run(defaultValue);
	}
	
	public static <T> T attempt(Callable<T> code, Supplier<T> defaultValue) {
		return new Try<T>(code).run(defaultValue);
	}
	
	public static <T> T attempt(Callable<T> code, Supplier<T> defaultValue, Runnable finish) {
		return new Try<T>(code).run(defaultValue, function(finish));
	}
	
	public static void attempt(Executable code) {
		new Try<Void>(function(code)).run();
	}

	public static void attempt(Executable code, boolean dump) {
		new Try<Void>(function(code)).run(dump ? e -> e.printStackTrace() : e -> {});
	}
	
	public static void attempt(Executable code, Executable error) {
		new Try<Void>(function(code)).run(function(error));
	}
	
	public static void attempt(Executable code, Executable error, Executable finish) {
		new Try<Void>(function(code)).run(function(error), function(finish));
	}
	
	public static void attempt(Executable code, Consumer<Exception> error) {
		new Try<Void>(function(code)).run(error);
	}
}
