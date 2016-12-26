package javax.util;

import static javax.util.Map.*;
import static javax.lang.Assert.*;

import java.util.LinkedHashMap;

public class MapTest {

	public void testMap() throws Exception {
		check(map().size() == 0);
		
		Map<String, Integer> map = map(entry("String", 1), entry("Foo", 2));
		check(map.size() == 2);
		
		Map<String, String> synced = map(String.class, String.class).synchronize();
		isEqual(synced.map.getClass().getName(), "java.util.Collections$SynchronizedMap");

		Map<String, String> synced2 = synchronize(map());
		isEqual(synced2.map.getClass().getName(), "java.util.Collections$SynchronizedMap");

		Map<String, String> linked = map(LinkedHashMap.class, String.class, String.class, entry("Foo", "2"));
		isEqual(linked.map.getClass().getName(), "java.util.LinkedHashMap");
		
		check(map.set("Test", 4).set("test", 4).delete("Foo").size() == 3);
		check(map.get("Test") == 4);
		check(map.get("Test2", 5) == 5);
		check(map.get("Test2", () -> 6) == 6);
		check(map.check("Test2", 7) == 7);
		check(map.get("Test2") == 7);
		check(map.check("Test3", () -> 8) == 8);
		check(map.get("Test3") == 8);
	}
	
	public static void main(String[] arguments) throws Exception {
		new MapTest().testMap();
	}
}
