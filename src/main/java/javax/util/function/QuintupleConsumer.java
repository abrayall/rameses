package javax.util.function;

import java.util.Objects;

@FunctionalInterface
public interface QuintupleConsumer<T, U, V, W, X> {
	void accept(T t, U u, V v, W w, X x);
	
    default QuintupleConsumer<T, U, V, W, X> andThen(QuintupleConsumer<? super T, ? super U, ? super V, ? super W, ? super X> after) {
        Objects.requireNonNull(after);

        return (t, u, v, w, x) -> {
            accept(t, u, v, w, x);
            after.accept(t, u, v, w, x);
        };
    }
}
