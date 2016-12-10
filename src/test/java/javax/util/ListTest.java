package javax.util;

import java.util.List;

import static javax.util.List.*;
import static javax.lang.Assert.*;

public class ListTest {

	public void testList() throws Exception {
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
	}
	
	public static void main(String[] arguments) throws Exception {
		new ListTest().testList();
	}
}
