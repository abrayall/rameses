package javax.lang;

import javax.lang.Assert;
import javax.util.Map;

public class StringsTest {

	public void testTruncate() throws Exception {
		Assert.equals("this is a test", Strings.truncate("this is a test of the national broadcast system", 14));
		Assert.equals("this is a t...", Strings.truncate("this is a test of the national broadcast system", 14, "..."));
		Assert.equals("this is a test", Strings.truncate("this is a test", 100));
	}
	
	public void testGenerate() throws Exception {
		Assert.equals("55555", Strings.generate('5', 5));
		Assert.equals("------------------------------", Strings.generate('-', 30));
		Assert.equals("", Strings.generate('%', 0));
		Assert.equals("", Strings.generate('%', -10));
	}
	
	public void testJoin() throws Exception {
		Assert.equals("this is a test", Strings.join(new String[] {"this", "is", "a", "test"}));
		Assert.equals("this,is,a,test", Strings.join(new String[] {"this", "is", "a", "test"}, ","));
		Assert.equals("foo,this,is,a,test,bar", Strings.join(new String[] {"this", "is", "a", "test"}, "foo,", ",", ",bar"));
	}
	
	public void testString() throws Exception {
		Assert.equals("1", Strings.string(1));
		Assert.equals("1", Strings.string("1"));
		Assert.equals("1.0", Strings.string(1.0));
	}
	
	public void testStrings() throws Exception {
		String[] strings = Strings.strings(new Object[] {0,1,2,3,4,5});
		for (int i = 0; i < strings.length; i++)
			Assert.equals(new Integer(i).toString(), strings[i]);
	}
	
	public void testPad() throws Exception {
		Assert.equals("foo   ", Strings.pad("foo", 6));
		Assert.equals("foo---", Strings.pad("foo", '-', 6));
		Assert.equals("foooooo", Strings.pad("foooooo", 3));
		Assert.equals("foooooo", Strings.pad("foooooo", '-', 3));
		Assert.equals("---foo", Strings.pad("foo", '-', 6, "left"));
	}
	
	public void testDivide() throws Exception {
		Assert.equals(3, Strings.divide("this is a test", 5).length);
		Assert.equals("this ", Strings.divide("this is a test", 5)[0]);
		Assert.equals("is a ", Strings.divide("this is a test", 5)[1]);
		Assert.equals("test", Strings.divide("this is a test", 5)[2]);
		Assert.equals(1, Strings.divide("this is a test", 15).length);
		Assert.equals(44, Strings.divide("this is a test of the nation brodcast system", 1).length);
		Assert.equals(0, Strings.divide("sdfaf", 0).length);
		Assert.equals(0, Strings.divide("sdfaf", -1).length);
	}
	
	public void testWrap() throws Exception {
		Assert.equals("this \nis a \ntest", Strings.wrap("this is a test", 5));
		Assert.equals("t\nh\ni\ns", Strings.wrap("this", 1));
		Assert.equals("", Strings.wrap("this", 0));
		Assert.equals("", Strings.wrap("this", -1));
		Assert.equals("this", Strings.wrap("this", 15));
	}
	
	public void testBlock() throws Exception {
		Assert.equals("this \nis a \ntest ", Strings.block("this is a test", 5));
		Assert.equals("test ", Strings.block("test", 5));
		Assert.equals("t\ne\ns\nt", Strings.block("test", 1));
	}
	
	public void testExtract() throws Exception {
		String[] tokens = Strings.extract("this is a {test}, {test} {bar}", "{", "}");
		Assert.equals(3, tokens.length);
		Assert.equals("test", tokens[0]);
		Assert.equals("test", tokens[1]);
		Assert.equals("bar", tokens[2]);
		
		tokens = Strings.extract("this is a [test], {test} [bar]", "\\[(.*?)\\]");
		Assert.equals(2, tokens.length);
		Assert.equals("test", tokens[0]);
		Assert.equals("bar", tokens[1]);
	}
	
	public void testBetween() throws Exception {
		Assert.equals("foo", Strings.between("barfoobar", 3, 6));
		Assert.equals("foobar", Strings.between("barfoobar", 3, 16));
		Assert.equals("", Strings.between("barfoobar", 13, 16));
		Assert.equals("foo", Strings.between("barfoobar", "bar", "bar"));
	}
	
	public void testPositions() throws Exception {
		Assert.equals("b", Strings.head("barfoobar"));
		Assert.equals("b", Strings.head("b"));
		Assert.equals("", Strings.head(""));
		
		Assert.equals("arfoobar", Strings.tail("barfoobar"));
		Assert.equals("r", Strings.tail("ar"));
		Assert.equals("", Strings.tail("r"));
		Assert.equals("", Strings.tail(""));
		
		Assert.equals("b", Strings.first("barfoobar"));
		Assert.equals("b", Strings.first("b"));
		Assert.equals("", Strings.first(""));
	
		Assert.equals("r", Strings.last("barfoobar"));
		Assert.equals("b", Strings.last("b"));
		Assert.equals("", Strings.last(""));
	}
	
	public void testAppend() throws Exception {
		Assert.equals("foobarabc", Strings.append("foobar", "a", "b", "c"));
		Assert.equals("foobarabcde", Strings.append("foobar", "a", "b", "cde"));
		Assert.equals("abc", Strings.append("", "a", "b", "c"));
		Assert.equals("aaaaabcde", Strings.append("aaa", "a", "ab", "cde"));

		Assert.equals("abcfoobar", Strings.prepend("foobar", "a", "b", "c"));
		Assert.equals("abcdefoobar", Strings.prepend("foobar", "a", "b", "cde"));
		Assert.equals("abc", Strings.prepend("", "a", "b", "c"));
		Assert.equals("aabcdeaaa", Strings.prepend("aaa", "a", "ab", "cde"));

		Assert.equals("aaafoobarbbb", Strings.surround("foobar", "aaa", "bbb"));
		Assert.equals("<div>", Strings.surround("div", "<", ">"));
	}
	
	public void testFormat() throws Exception {
		Assert.equals("this is a test of the national broadcast system 0.0", Strings.format("this ${0} a test ${1} ${2} ${3}", "is", "of the national", "broadcast system", 0.0));
		Assert.equals("this is a test of the national broadcast system 0.0", Strings.format("this ${verb} a test ${subject1} ${subject2} ${number}", Map.map(
			Map.entry("verb", "is"),
			Map.entry("subject1", "of the national"),
			Map.entry("subject2", "broadcast system"),
			Map.entry("number", 0.0)
		)));
	}
	
	public static void main(String[] arguments) throws Exception {
		StringsTest test = new StringsTest();
		test.testString();
		test.testStrings();
		test.testJoin();
		test.testPad();
		test.testDivide();
		test.testWrap();
		test.testBlock();
		test.testTruncate();
		test.testGenerate();
		test.testExtract();
		test.testBetween();
		test.testPositions();
		test.testAppend();
		test.testFormat();
	}
}
