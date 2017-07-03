package javax.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class Map<K, V> implements java.util.Map<K, V> {

	protected java.util.Map<K, V> map;
	
	public Map() {
		this(new HashMap<K, V>());
	}
	
	public Map(java.util.Map<K, V> map) {
		this.map = map;
	}
	
	@Override
	public int size() {
		return this.map.size();
	}

	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return this.map.get(key);
	}
	
	public V get(K key, V defaultValue) {
		return this.getOrDefault(key, defaultValue);
	}
	
	public V get(K key, Supplier<V> defaultValue) {
		if (this.map.containsKey(key) == false)
			return defaultValue.get();
		
		return this.map.get(key);
	}
	
	public V check(K key, V defaultValue) {
		return this.check(key, () -> defaultValue);
	}
	
	public V check(K key, Supplier<V> defaultValue) {
		if (this.map.containsKey(key) == false)
			return this.put(key, defaultValue.get());
		
		return this.map.get(key);
	}

	@Override
	public V put(K key, V value) {
		this.map.put(key, value);
		return value;
	}
	
	public Map<K, V> set(K key, V value) {
		this.put(key, value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Map<K, V> set(Entry<K, V>... entries) {
		for (Entry<K, V> entry : entries)
			this.map.put(entry.key, entry.value);
		
		return this;
	}
	
	public Map<K, V> set(java.util.Map<? extends K, ? extends V> map) {
		this.map.clear();
		this.map.putAll(map);
		return this;
	}
	
	@Override
	public V remove(Object key) {
		return this.map.remove(key);
	}
	
	public Map<K, V> delete(K key) {
		this.map.remove(key);
		return this;
	}

	@Override
	public void putAll(java.util.Map<? extends K, ? extends V> map) {
		this.map.putAll(map);
	}
	
	public Map<K, V> put(java.util.Map<? extends K, ? extends V> map) {
		this.map.putAll(map);
		return this;
	}
	
	public Map<K, V> concat(java.util.Map<? extends K, ? extends V> map) {
		this.putAll(map);
		return this;
	}

	@Override
	public void clear() {
		this.map.clear();
	}
	
	public Map<K, V> empty() {
		this.clear();
		return this;
	}

	@Override
	public Set<K> keySet() {
		return this.map.keySet();
	}

	@Override
	public Collection<V> values() {
		return this.map.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return this.map.entrySet();
	}
	
	public Map<K, V> synchronize() {
		this.map = Collections.synchronizedMap(this.map);
		return this;
	}
	
	public Map<K, V> each(BiConsumer<K, V> action) {
		return this.foreach(action);
	}
	
	public Map<K, V> foreach(BiConsumer<K, V> action) {
		for (K key : this.keySet())
			action.accept(key,  this.get(key));
		
		return this;
	}
	
	public Map<K, V> map(BiFunction<K, V, V> mapper) {
		for (K key : this.keySet())
			this.put(key, mapper.apply(key, this.get(key)));
		
		return this;
	}
	
	public Map<K, V> filter(BiPredicate<K, V> filter) {
		for (K key : this.keySet()) {
			if (filter.test(key, this.get(key)) == false)
				this.remove(key);
		}
		
		return this;
	}
	
	public Map<V, K> reverse() {
		Map<V, K> reversed = map();
		for (K key : this.keySet())
			reversed.put(this.get(key), key);
			
		return reversed;
	}
	
	@Override
	public String toString() {
		return this.map.toString();
	}

	@SafeVarargs
	public static <K, V> Map<K,V> map(Entry<K, V>... entries) {
		return map(new HashMap<K, V>(), entries);
	}
	
	@SafeVarargs
	public static <K, V> Map<K, V> map(java.util.Map<K, V> base, Entry<K, V>... entries) {
		return new Map<K, V>(base).set(entries);
	}
	
	@SafeVarargs
	public static <K, V>  Map<K, V> map(Class<K> key, Class<V> value, Entry<K, V>... entries) {
		return new Map<K, V>().set(entries);
	}
	
	@SafeVarargs
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Map<K, V> map(Class<? extends java.util.Map> clazz, Class<K> key, Class<V> value, Entry<K, V>... entries) throws Exception {
		return map(clazz.newInstance(), entries);
	}
	
	@SafeVarargs
	public static <V> Map<String, V> map(V... items) {
		return map(List.list(items));
	}
	
	public static <V> Map<String, V> map(List<V> list) {
		return map(list, (index, item) -> index.toString());
	}
	
	public static <K, V> Map<K, V> map(List<V> list, BiFunction<Integer, V, K> converter) {
		Map<K, V> map = map();
		list.foreach((item, index) -> {
			map.put(converter.apply(index, item), item);
		});
		
		return map;
	}
	
	public static <K, V> Entry<K, V> entry(K key, V value) {
		return new Entry<K, V>(key, value);
	}
	
	public static <K, V> Map<K, V> synchronize(java.util.Map<K, V> map) {
		return map(Collections.synchronizedMap(map));
	}
	
	public static <K, V> Map<K, V> synchronize(Map<K, V> map) {
		return map.synchronize();
	}
	
	public static class Entry<K, V> {
		public K key;
		public V value;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K key() {
			return this.key;
		}
		
		public V value() {
			return this.value;
		}
	}
}
