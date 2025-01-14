package com.llf.utils;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

public class GenerateStrUtil {
	
	public static String generateMercahntId() {
	
		return String.valueOf(RandomUtils.nextInt(new Random(), 99999));
		
	}
	
	public static String generateSmsImgCode() {
		
		return String.valueOf(String.valueOf((int)((Math.random()*9+1)*Math.pow(10,5))));
		
	}
	
	public static void main(String[] args) {
		
		String code = String.valueOf((int)((Math.random()*9+1)*Math.pow(10,5)));
		
		System.out.println(code);
	}

}
