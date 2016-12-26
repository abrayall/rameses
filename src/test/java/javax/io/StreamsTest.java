package javax.io;

import javax.lang.Assert;

public class StreamsTest {

	public void testStreams() throws Exception {
		for (String string : new String[] {"foo", "bar", "test", "test\nthisisatest", "alkjasdlfkjasdlfkjasldfkjasdf\nlkjasdflkjasdflkjasdflkjasldkfjlaskjflaksjdf\\\\nlkasjdflkjasdflkjasofiyoawiejrqop4i7poiajdf;kajdflkasjdlfkjasdf"}) {
			Assert.equals(Streams.write(string, new StringOutputStream()).toString(), string);
			Assert.equals(Streams.read(new StringInputStream(string)), string);
			Assert.equals(Streams.copy(new StringInputStream(string), new StringOutputStream()).toString(), string);
		}
	}

	public static void main(String[] arguments) throws Exception {
		new StreamsTest().testStreams();
	}
}
