package javax.lang;

public class Class {
	public static <T> T cast(Object object, java.lang.Class<T> clazz) {
		return clazz.cast(object);
	}
}
