package javax.lang;

import java.util.LinkedHashMap;
import javax.util.Map;

import static javax.util.Map.*;

/**
 * Helper class that provides convenience methods for dealing with numbers
 */
public class Numbers {
	
	private static final Long thousand    = 1000l;
	private static final Long million     = thousand * thousand;
	private static final Long billion     = million * thousand;
	private static final Long trillion    = billion * thousand;
	private static final Long quadrillion = trillion * thousand;
	private static final Long quintillion = quadrillion * thousand;
	
	private static Map<String, WordFormatter> formatters = map(
		entry("english", new EnglishWordFormatter())
	);
	
	
	/**
	 * Converts a given long to an integer
	 * @param number the long to convert to an integer
	 * @return the integer value of the given long
	 */
	public static int toInteger(Long number) {
		return number.intValue();
	}
	
	
	/**
	 * Converts a given integer to a long 
	 * @param number the integer to convert to a long
	 * @return the long value of the given integer
	 */
	public static long toLong(Integer number) {
		return number.longValue();
	}
	
	
	/**
	 * Converts a number to a string containing the words describing the number
	 * @param number the number to convert
	 * @return a string containing the word version describing the given number
	 */
	public static String toWords(Integer number) {
		return toWords(toLong(number));
	}
	
	
	/**
	 * Converts a number to a string containing the words describing the number
	 * @param number the number to convert
	 * @return a string containing the word version describing the given number
	 */
	public static String toWords(Long number) {
		return toWords(number, "english");
	}
	
	
	/**
	 * Converts a number to a string containing the words describing the number
	 * @param number the number to convert
	 * @param language the language that the number should be converted to (english)
	 * @return a string containing the word version describing the given number
	 */
	public static String toWords(Integer number, String language) {
		return toWords(toLong(number), language);
	}
	
	
	/**
	 * Converts a number to a string containing the words describing the number
	 * @param number the number to convert
	 * @param language the language that the number should be converted to (english)
	 * @return a string containing the word version describing the given number
	 */
	public static String toWords(Long number, String language) {
		return formatters.get(language, formatters.get("english")).toWords(number);
	}	
	
	
	/**
	 * Interface for converting numbers to strings containing words describing the number
	 */
	protected static interface WordFormatter {
		/**
		 * Converts the given number to a string containing the words describing the given number
		 * @param number the number to be converted to a string
		 * @return a string containing the words describing the given number
		 */
		public String toWords(Long number);
	}
	
	
	/**
	 * Implementation of word formatter that supports converting numbers into string with English descriptions of the given numbers
	 */
	protected static class EnglishWordFormatter implements WordFormatter {

		private static final String[] ONES = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
		private static final String[] TENS = {"ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
		private static final String[] SPECIALS = Arrays.concat(ONES, new String[] {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"});

		private static final Map<String, Long> scales = map(new LinkedHashMap<String, Long>(),
			entry("quintillion", quintillion),
			entry("quadrillion", quadrillion),
			entry("trillion", trillion),
			entry("billion", billion),
			entry("million", million),
			entry("thousand", thousand)
		);
		
		/*
		 * (non-Javadoc)
		 * @see javax.lang.Numbers.WordFormatter#toWords(java.lang.Long)
		 */
		@Override
		public String toWords(Long number) {
			StringBuffer buffer = new StringBuffer(number < 0 ? "negative " : "");
			
			long absolute = Math.abs(number);
			if (absolute < 19)
				buffer.append(SPECIALS[toInteger(absolute)]);
			else {
				for (String name : scales.keySet()) {
					Long scale = scales.get(name);
					if (absolute > scale) {
						buffer.append(format(toInteger(absolute / scale), name, false) + " ");
						absolute = absolute - ((absolute / scale) * scale);
					}
				}
				
				buffer.append(format(toInteger(absolute), "", true));
			}

			return buffer.toString().trim();
		}
		
		
		/**
		 * Formats a given number to a given scale
		 * @param number the number to be formatted
		 * @param scale the scale that the number should be formatted to
		 * @param and boolean indicating whether and word "and" should be used between numbers
		 * @return a string formatted version of the given number
		 */
		protected String format(int number, String scale, boolean and) {
			int hundreds = number / 100;
			int tens = (number - (hundreds * 100)) / 10;
			int ones = (number - (hundreds * 100) - tens * 10);
			return format(hundreds, tens, ones, scale, and);
		}
		
		
		/**
		 * Formats a number broken down into the hundreds, tens and ones places
		 * @param hundreds the number of hundreds in the given number
		 * @param tens the number of tens in the given number
		 * @param ones the number of ones in the given number
		 * @param scale the scale at which the number should be formatted
		 * @param and boolean indicating whether and word "and" should be used between numbers
		 * @return a string formatted version of the given number
		 */
		protected String format(int hundreds, int tens, int ones, String scale, boolean and) {
			StringBuffer buffer = new StringBuffer();
			if (hundreds > 0)
				buffer.append(ONES[hundreds] + " hundred ");
			
			if (tens > 1 && ones > 0 && and)
				buffer.append("and ");
			
			if (tens == 1)
				buffer.append(SPECIALS[10 + ones]);
			else {
				if (tens > 1)
					buffer.append(TENS[tens - 1]);
				
				if (tens > 1 && ones > 0)
					buffer.append("-");
				
				if (ones > 0)
					buffer.append(ONES[ones]);
			}
			
			buffer.append(" " + scale);
			return buffer.toString().trim();
		}
	}
}
