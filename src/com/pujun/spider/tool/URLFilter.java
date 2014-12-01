package com.pujun.spider.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLFilter {
	private static final String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|][^.png|.jpg|.js|.gif|.doc|.xls|.xls|.pdf|.css]" ;
	
    public static boolean filt(String url) {
    	 Pattern patt = Pattern. compile(regex );
    	 Matcher matcher = patt.matcher(url);
    	 return matcher.matches();
	}
}
