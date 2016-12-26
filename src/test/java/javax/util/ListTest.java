package javax.util;

import static javax.lang.Assert.check;
import static javax.lang.Assert.isEqual;
import static javax.util.List.list;
import static javax.util.List.synchronize;

public class ListTest {

	public void testList() throws Exception {
		check(list().size() == 0);
		
		List<Integer> list = list(0,1,2,3,4,5,6,7,8,9);
		check(list.size() == 10);
		for (int i = 0; i < list.size(); i++)
			check(list.get(i) == i);
		
		List<String> list2 = list("0","1","2","3","4","5","6","7","8","9");
		check(list2.size() == 10);
		for (int i = 0; i < list.size(); i++) 
			isEqual(list2.get(i), new Integer(i).toString());
		
		List<Object> mixed = list("1", 2, 3, "foo", new Long(5), new Object());
		check(mixed.size() == 6);
		
		
		list.append(1);
		list.add(2,3,4,5);
		check(list.size() == 15);
		
		check(list.get(1) == 1);
		check(list.get(14) == 5);
		
		list.delete(1).delete(1);
		check(list.size() == 13);
		check(list.get(0) == 0);
		check(list.get(1) == 3);
		check(list.get(2) == 4);
		
		list2.delete("0", "1", "2");
		check(list2.size() == 7);
		check(list2.get(0) == "3");
		checkJavaUtilList(list2);
		
		isEqual(list(String.class).append("test").get(0), "test");
		
		List<String> synced = list(String.class).synchronize();
		isEqual(synced.list.getClass().getName(), "java.util.Collections$SynchronizedRandomAccessList");

		List<String> synced2 = synchronize(list());
		isEqual(synced2.list.getClass().getName(), "java.util.Collections$SynchronizedList");

	}
	
	public void checkJavaUtilList(List<String> list) throws Exception {
		check(list.get(0) == "3");
	}
	
	public static void main(String[] arguments) throws Exception {
		new ListTest().testList();
	}
}
