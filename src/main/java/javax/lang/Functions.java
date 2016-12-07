package javax.lang;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class Functions {
	public static <T> Supplier<T> function(Callable<T> callable) {
		return () -> {
            try {
                return callable.call();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        };		
	}
	
	public static Supplier<Void> function(Runnable runnable) {
		return () -> {
           	runnable.run();
            return null;
        };	
	}
	
	public static Supplier<Void> function(Executable executable) {
		return () -> {
            try {
            	executable.execute();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            
            return null;
        };	
	}
}