package javax.util;

import javax.lang.Assert;
import javax.lang.Numbers;

public class NumbersTest {
	public static void main(String[] arguments) throws Exception {
		new NumbersTest().test();
	}
	
	public void test() throws Exception {
		Assert.equals("zero", Numbers.toWords(0));
		Assert.equals("zero", Numbers.toWords(-0));
		Assert.equals("one", Numbers.toWords(1));
		Assert.equals("negative one", Numbers.toWords(-1));
		Assert.equals("seven", Numbers.toWords(7));
		Assert.equals("thirteen", Numbers.toWords(13));
		Assert.equals("sixteen", Numbers.toWords(16));
		Assert.equals("negative sixteen", Numbers.toWords(-16));
		Assert.equals("twenty", Numbers.toWords(20));
		Assert.equals("eighty-five", Numbers.toWords(85));
		Assert.equals("ninety-nine", Numbers.toWords(99));
		Assert.equals("one hundred", Numbers.toWords(100));
		Assert.equals("one hundred and one", Numbers.toWords(101));
		Assert.equals("nine hundred", Numbers.toWords(900));
		Assert.equals("nine hundred and ninety-nine", Numbers.toWords(999));
		Assert.equals("five thousand four hundred and thirty-seven", Numbers.toWords(5437));
		Assert.equals("one thousand", Numbers.toWords(1000));
		Assert.equals("ten thousand", Numbers.toWords(10000));
		Assert.equals("one hundred thousand", Numbers.toWords(100000));
		Assert.equals("one million", Numbers.toWords(1000000));
		Assert.equals("ten million", Numbers.toWords(10000000));
		Assert.equals("one hundred million", Numbers.toWords(100000000));
		Assert.equals("one billion", Numbers.toWords(1000000000));
		Assert.equals("ten billion", Numbers.toWords(10000000000l));
		Assert.equals("one hundred billion", Numbers.toWords(100000000000l));
		Assert.equals("one trillion", Numbers.toWords(1000000000000l));
		Assert.equals("ten trillion", Numbers.toWords(10000000000000l));
		Assert.equals("one hundred trillion", Numbers.toWords(100000000000000l));		
		Assert.equals("one hundred twenty-three thousand four hundred and fifty-six", Numbers.toWords(123456));
		Assert.equals("nine hundred eighty-seven million six hundred fifty-four thousand three hundred and twenty-one", Numbers.toWords(987654321));
		Assert.equals("nine quintillion two hundred twenty-three quadrillion three hundred seventy-two trillion thirty-six billion eight hundred fifty-four million seven hundred seventy-five thousand eight hundred and seven", Numbers.toWords(Long.MAX_VALUE));
		Assert.equals("negative nine quintillion two hundred twenty-three quadrillion three hundred seventy-two trillion thirty-six billion eight hundred fifty-four million seven hundred seventy-five thousand eight hundred and seven", Numbers.toWords(-Long.MAX_VALUE));
	}
}
