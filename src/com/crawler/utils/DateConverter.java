package com.crawler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

	 private static SimpleDateFormat HH_mm = new SimpleDateFormat("HH:mm");
	 
	 public static Date dateFromStringHH_mm(String dateString) throws ParseException{
		 return HH_mm.parse(dateString);
	 }
	 
	 public static String stringHH_mmFromDate(Date date){
		 return HH_mm.format(date);
	 }
}
