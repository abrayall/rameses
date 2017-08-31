package javax.util;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class List<T> implements java.util.List<T> {
	
	protected java.util.List<T> list;
	
	public List()  {
		this(new ArrayList<T>());
	}
	
	protected List(java.util.List<T> list) {
		this.list = list;
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean contains(Object object) {
		return this.list.contains(object);
	}

	@Override
	public Iterator<T> iterator() {
		return this.list.iterator();
	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}

	@Override
	public <A> A[] toArray(A[] array) {
		return this.list.toArray(array);
	}
	
	@SuppressWarnings("unchecked")
	public T[] array() {
		return this.toArray((T[]) java.lang.reflect.Array.newInstance(this.get(0).getClass(), this.size())); 
	}

	@Override
	public boolean add(T object) {
		return this.list.add(object);
	}
	
	public boolean addIfNotNull(T object) {
		if (object != null)
			return this.list.add(object);	
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(T... objects) {
		for (T object : objects) 
			this.add(object);
		
		return true;
	}
	
	public List<T> append(T object) {
		this.add(object);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> append(T... objects) {
		this.add(objects);
		return this;
	}
	
	public List<T> append(List<T> objects){
		this.list.addAll(objects);
		return this;
	}
	
	public List<T> append(Supplier<T> function) {
		return this.append(function.get());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> append(Supplier<T>... functions) {
		for (Supplier<T> function : functions)
			this.append(function);
		
		return this;
	}
	
	public List<T> appendAll(java.util.Collection<? extends T> collection) {
		this.list.addAll(collection);
		return this;
	}

	@Override
	public boolean remove(Object object) {
		return this.list.remove(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return this.list.containsAll(collection);
	}

	@Override
	public boolean addAll(Collection<? extends T> collection) {
		return this.list.addAll(collection);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> collection) {
		return this.list.addAll(index, collection);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return this.list.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return this.list.retainAll(collection);
	}
	
	public List<T> concat(Collection<T> collection) {
		this.addAll(collection);
		return this;
	}

	@Override
	public void clear() {
		this.list.clear();
	}
	
	public List<T> empty() {
		this.clear();
		return this;
	}
		
	public Stream<T> stream() {
		return this.list.stream();
	}

	@Override
	public T get(int index) {  
		return this.list.size() > 0 ? this.list.get(index) : null;
	}
	
	public T get(int index, T defaultValue) {
		return this.list.size() > index ? this.get(index) : defaultValue;
	}
	
	public T get(int index, Supplier<T> defaultValue) {
		return this.list.size() > index ? this.get(index) : defaultValue.get();
	}

	@Override
	public T set(int index, T object) {
		return this.list.set(index, object);
	}

	public List<T> put(int index, T object) {
		this.set(index, object);
		return this;
	}
	
	@Override
	public void add(int index, T object) {
		this.list.add(index, object);
	}
	
	public List<T> insert(int index, T object) {
		this.list.add(index, object);
		return this;
	}
	
	public List<T> replace(int index, T object) {
		this.set(index, object);
		return this;
	}
	
	public List<T> replace(int index, Function<T, T> handler) {
		return this.replace(index, handler.apply(this.get(index)));
	}

	@Override
	public T remove(int index) {
		return this.list.remove(index);
	}
	
	public List<T> delete(int index) {
		this.list.remove(index);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> delete(T... objects) {
		for (T object : objects)
			this.list.remove(object);
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> without(T... objects) {
		return this.clone().delete(objects);
	}
	
	@Override
	public int indexOf(Object object) {
		return this.list.indexOf(object);
	}

	@Override
	public int lastIndexOf(Object object) {
		return this.list.lastIndexOf(object);
	}
	
	public int lastIndex() {
		return this.size() - 1;
	}
	
	public T last() {
		return this.list.get(this.lastIndex());
	}
	
	public T first() {
		return this.list.get(0);
	}
	
	public List<T> head() {
		return list(this.list.get(0));
	}
	
	public List<T> tail() {
		return list(this.list.subList(1, this.list.size()));
	}
	
	public List<T> reverse() {
		List<T> list = list(new ArrayList<T>(this.list.size()));
		for (int i = 0; i < this.list.size(); i++) 
			list.add(i, this.list.get(this.list.size() - (i + 1)));
					
		return list;
	}
	
	public List<T> clone() {
		List<T> list = list();
		for (int i = 0; i < this.list.size(); i++)
			list.put(i, this.list.get(i));
		
		return list;
	}

	@Override
	public ListIterator<T> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return this.list.listIterator(index);
	}

	@Override
	public java.util.List<T> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}
	
	public List<T> synchronize() {
		this.list = Collections.synchronizedList(this.list);
		return this;
	}
	
	public List<T> diff(java.util.List<T> target, BiConsumer<T, String> handler) {
		return this.diff(list(target), handler);
	}
	
	public List<T> diff(List<T> target, BiConsumer<T, String> handler) {
		target.forEach(item -> {
			T other = this.list.contains(item) ? target.get(target.indexOf(item)) : null;
			if (other == null)
				handler.accept(item, "add");
			else if (other.equals(item) == false)
				handler.accept(item, "modify");
		});
		
		this.list.forEach(item -> {
			if (target.contains(item) == false)
				handler.accept(item, "delete");
		});
		
		return this;
	}

	public String join() {
		return this.join(",");
	}
	
	public String join(String seperator) {
		return this.join("", "", seperator);
	}

	public String join(String prefix, String suffix, String seperator) {
		StringBuffer buffer = new StringBuffer(prefix);
		for (int i = 0; i < this.list.size(); i++) {
			buffer.append(this.list.get(i));
			if (i != this.list.size() - 1)
				buffer.append(seperator);
		}
			
		return buffer.append(suffix).toString();
	}
	
	public List<T> each(Consumer<T> action) {
		return this.foreach(action);
	}
	
	public List<T> foreach(Consumer<T> action) {
		this.list.stream().forEach(action);
		return this;
	}
	
	public List<T> each(BiConsumer<T, Integer> action) {
		return this.foreach(action);
	}
	
	public List<T> foreach(BiConsumer<T, Integer> action) {
		this.foldLeft(0, (index, item) -> {
			action.accept(item, index);
			return index + 1;
		});
		return this;
	}
		
	public List<T> filter(Predicate<T> filter) {
		return this.list.stream().filter(filter).collect(Collectors.toCollection(List<T>::new));
	}
	
	public <M> List<M> map(Function<T, M> mapper) {
		return this.list.stream().map(mapper).collect(Collectors.toCollection(List<M>::new));
	}
	
	public <M> List<M> flatMap(Function<T, List<M>> mapper) {
		return list(this.list.stream().flatMap(item -> {
			return mapper.apply(item).stream();
		}));
	}
	
	public <R> R fold(R object, BiFunction<R, T, R> folder) {
		for (T item : this.list)
			object = folder.apply(object, item);
		
		return object;
	}
	
	public <R> R foldLeft(R object, BiFunction<R, T, R> folder) {
		return this.fold(object, folder);
	}
	
	public <R> R foldRight(R object, BiFunction<R, T, R> folder) {
		return this.reverse().fold(object, folder);
	}
	
	@Override
	public String toString() {
		return this.list.toString();
	}

	
	@SafeVarargs
	public static <T> List<T> list(T... items) {
		return (List<T>) list(new List<T>(), items);
	}
	
	@SafeVarargs
	public static <T> List<T> list(java.util.List<T> list, T... items) {
		return new List<T>(list).append(items);
	}
	
	@SafeVarargs
	public static <T> List<T> list(Class<T> clazz, T... items) {
		return new List<T>().append(items);
	}
	
	public static <T> List<T> list(Stream<T> stream) {
		return new List<T>(stream.collect(Collectors.toList()));
	}
	
	public static <T> List<T> synchronize(java.util.List<T> list) {
		return list(Collections.synchronizedList(list));
	}
	
	public static <T> List<T> synchronize(List<T> list) {
		return list.synchronize();
	}
}
