package com.llf.utils;

import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

public class GenerateStrUtil {
	
	public static String generateMercahntId() {
	
		return String.valueOf(RandomUtils.nextInt(new Random(), 99999));
		
	}
	
	public static void main(String[] args) {
		System.out.println(RandomUtils.nextInt(new Random(), 19));
	}

}
