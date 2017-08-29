package javax.lang;

public class NumbersTest {
	public static void main(String[] arguments) throws Exception {
		new NumbersTest().test();
	}
	
	public void test() throws Exception {
		Assert.equals("zero", Numbers.toWords(0));
		Assert.equals("one", Numbers.toWords(1));
		Assert.equals("negative one", Numbers.toWords(-1));
		Assert.equals("sixteen", Numbers.toWords(16));
		Assert.equals("negative sixteen", Numbers.toWords(-16));
		Assert.equals("twenty", Numbers.toWords(20));
		Assert.equals("one hundred twenty-three thousand four hundred and fifty-six", Numbers.toWords(123456));
		Assert.equals("nine hundred eighty-seven million six hundred fifty-four thousand three hundred and twenty-one", Numbers.toWords(987654321));
		Assert.equals("nine quintillion two hundred twenty-three quadrillion three hundred seventy-two trillion thirty-six billion eight hundred fifty-four million seven hundred seventy-five thousand eight hundred seven", Numbers.toWords(Long.MAX_VALUE));
		Assert.equals("negative nine quintillion two hundred twenty-three quadrillion three hundred seventy-two trillion thirty-six billion eight hundred fifty-four million seven hundred seventy-five thousand eight hundred seven", Numbers.toWords(-Long.MAX_VALUE));
	}
}
