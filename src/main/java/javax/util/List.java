package javax.util;

import java.util.ArrayList;

public class List {
	@SafeVarargs
	public static <T> java.util.List<T> list(T... items) {
		ArrayList<T> list = new ArrayList<T>();
		for (T item : items)
			list.add(item);
		
		return list;
	}
	
	@SafeVarargs
	public static <T> java.util.List<T> of(T... items) {
		return list(items);
	}
}
