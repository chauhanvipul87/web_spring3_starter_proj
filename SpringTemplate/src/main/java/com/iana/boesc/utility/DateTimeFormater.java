package com.iana.boesc.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeFormater {
	
	public static java.sql.Timestamp getSqlSysTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	
	
	public static String format_datetime(){
		Calendar c = Calendar.getInstance();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = f.format(c.getTime());
		System.out.println(date);
		return date;
	}
	
	public static String getCurrentDate(){
		Calendar c = Calendar.getInstance();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(c.getTime());
		System.out.println(date);
		return date;
	}
	
}
