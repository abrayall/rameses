package javax.lang;

import java.lang.reflect.Array;

public class Arrays {
	
	@SafeVarargs
	public static <T> T[] append(T[] base, T... others) {
		return concat(base, others);
	}
	
	public static <T> T[] concat(T[] first, T[] second) {
	    @SuppressWarnings("unchecked")
		T[] full = (T[]) Array.newInstance(first.getClass().getComponentType(), first.length + second.length);
		java.lang.System.arraycopy(first, 0, full, 0, first.length);
		java.lang.System.arraycopy(second, 0, full, first.length, second.length);
		return full;
	}
}
