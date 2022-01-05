package com.murali.rough;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTest {

	public static void main(String[] args) {


		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");		
		Date d = new Date();
		String strDate = sdf.format(d);		
		System.out.println(strDate.toString().replace(":", "_").replace(" ", "_").replace("-", "_").replace(".", "_"));
		 
	}

}
