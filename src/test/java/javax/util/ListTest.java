package javax.util;

import static javax.util.List.*;
import static javax.lang.Assert.*;

public class ListTest {

	public void testList() throws Exception {
		check(list().size() == 0);
		
		List<Integer> list = list(0,1,2,3,4,5,6,7,8,9);
		check(list.size() == 10);
		for (int i = 0; i < list.size(); i++)
			check(list.get(i) == i);
		
		List<String> of = list("0","1","2","3","4","5","6","7","8","9");
		check(of.size() == 10);
		for (int i = 0; i < list.size(); i++) 
			isEqual(of.get(i), new Integer(i).toString());
		
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
		
		of.delete("0", "1", "2");
		check(of.size() == 7);
		check(of.get(0) == "3");
		checkJavaUtilList(of);
	}
	
	public void checkJavaUtilList(List<String> list) throws Exception {
		check(list.get(0) == "3");
	}
	
	public static void main(String[] arguments) throws Exception {
		new ListTest().testList();
	}
}
