package javax.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Timestamp {

	private static SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	static {
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
	}
	
	public static String format(long timestamp) {
		return format.format(timestamp);
	}
}
