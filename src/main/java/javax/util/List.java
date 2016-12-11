package javax.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Supplier;

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

	@Override
	public boolean add(T object) {
		return this.list.add(object);
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
	
	public List<T> append(Supplier<T> function) {
		return this.append(function.get());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> append(Supplier<T>... functions) {
		for (Supplier<T> function : functions)
			this.append(function);
		
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

	@Override
	public T get(int index) {  
		return this.list.get(index);
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
	
	@Override
	public int indexOf(Object object) {
		return this.list.indexOf(object);
	}

	@Override
	public int lastIndexOf(Object object) {
		return this.list.lastIndexOf(object);
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
	
	public static <T> List<T> synchronize(java.util.List<T> list) {
		return list(Collections.synchronizedList(list));
	}
	
	public static <T> List<T> synchronize(List<T> list) {
		return list.synchronize();
	}
}
