package javax.util;

import static javax.util.Map.*;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@SuppressWarnings("serial")
public class Timestamp extends java.sql.Timestamp {

	public static TimeZone GMT = TimeZone.getTimeZone("GMT");
	public static TimeZone TIMEZONE = TimeZone.getDefault();

	private static String FORMAT = "YYYY-MM-dd HH:mm:ss";
	private static Map<String, SimpleDateFormat> formatters = map();
	
	public Timestamp(long time) {
		super(time);
	}
	
	public String format() {
		return Timestamp.format(this.getTime());
	}
	
	public String format(String format) {
		return Timestamp.format(this.getTime(), format);
	}
	
	public String format(TimeZone timezone) {
		return Timestamp.format(this.getTime(), timezone);
	}
	
	public String format(String format, TimeZone timezone) {
		return Timestamp.format(this.getTime(), format, timezone);
	}
	
	public String toString() {
		return format();
	}
	
	public static Timestamp now() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static String format(long timestamp) {
		return format(timestamp, FORMAT);
	}
	
	public static String format(long timestamp, String format) {
		return format(timestamp, format, TIMEZONE);
	}
	
	public static String format(long timestamp, TimeZone timezone) {
		return format(timestamp, FORMAT, timezone);
	}
	
	public static String format(long timestamp, String format, TimeZone timezone) {
		return formatters.get(timezone.getID(), () -> formatter(format, timezone)).format(timestamp);		
	}
	
	public static SimpleDateFormat formatter() {
		return formatter(FORMAT);
	}
	
	public static SimpleDateFormat formatter(String format) {
		return formatter(format, TIMEZONE);
	}
	
	public static SimpleDateFormat formatter(TimeZone timezone) {
		return formatter(FORMAT, timezone);
	}
	
	public static SimpleDateFormat formatter(String format, TimeZone timezone) {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.setTimeZone(timezone);
		return formatter;
	}
}
