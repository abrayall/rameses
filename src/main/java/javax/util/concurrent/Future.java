package javax.util.concurrent;

import static javax.lang.Try.attempt;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Future<T> extends java.util.concurrent.CompletableFuture<T> {
	
	public Future<T> onComplete(Consumer<T> handler) {
		this.thenAccept(handler);
		return this;
	}
	
	public Future<T> onError(Consumer<Throwable> handler) {
		this.exceptionally(exception -> {
			handler.accept(exception);
			return null;
		});
		
		return this;
	}
	
	public static <T> Future<T> future(Callable<T> action) {
		return future(action, Executors.newCachedThreadPool());
	}

	public static Future<Void> future() {
		return future(Void.class);
	}
	
	public static <T> Future<T> future(Class<? extends T> type) {
		return new Future<T>();
	}
	
	public static <T> Future<T> future(Callable<T> callable, Executor executor) {
	    Future<T> future = new Future<>();
	    executor.execute(() -> {
	        try {
	            future.complete(callable.call());
	        } catch (Throwable t) {
	            future.completeExceptionally(t);
	        }
	    });
	    
	    return future;
	}
	
	public static <T> Future<T> future(javax.util.concurrent.Future<T> future, Executor executor) {
		Future<T> result = new Future<T>();
		executor.execute(() -> {
			try {
				result.complete(future.get());
			} catch (Throwable t) {
				result.completeExceptionally(t);
			}
		});
		
		return result;
	}

	
	public static <T> T wait(javax.util.concurrent.Future<T> future, int timeout, TimeUnit unit) {
		return wait(future, (T)null, timeout, unit);
	}
	
	public static <T> T wait(javax.util.concurrent.Future<T> future, T defaultValue, int timeout, TimeUnit unit) {
		return wait(future, (Supplier<T>)() -> defaultValue, timeout, unit);
	}
	
	public static <T> T wait(javax.util.concurrent.Future<T> future, Supplier<T> function, int timeout, TimeUnit unit) {
		return attempt((Callable<T>)() -> future.get(timeout, unit), function);
	}
}
