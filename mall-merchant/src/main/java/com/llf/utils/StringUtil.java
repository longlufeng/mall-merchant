package com.llf.utils;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equals("null");
    } 
	
	public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    } 

}
