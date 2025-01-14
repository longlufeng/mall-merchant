package com.llf.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

/**
 * md5加密
   *  以下三种方式都可以对字符创进行MD5加密，方式都是现将字符创转化为字节数组，然后转化为16进制
 *
 */
public class Md5Utils {
    
	/**
	 * 使用apache的DigestUtils进行MD5加密
	 * @param str
	 * @return
	 */
    public static String encryptToMd5(String str) {
    	
        return org.apache.commons.codec.digest.DigestUtils.md5Hex(str);
        
    }
 
    /**
     * 自定义MD5加密
     * @param str
     * @return
     */
    public static String encrypt2ToMd5(String str) {
 
        // 加密后的16进制字符串
        String hexStr = "";
        try {
 
            // 此 MessageDigest 类为应用程序提供信息摘要算法的功能
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // 转换为MD5码
            byte[] digest = md5.digest(str.getBytes(StandardCharsets.UTF_8));
            hexStr = ByteUtils.toHexString(digest);
        } catch (Exception e) {
 
            e.printStackTrace();
        }
        return hexStr;
    }
 
    /**
	 * 使用springframework的DigestUtils进行MD5加密
	 * @param str
	 * @return
	 */
    public static String encrypt3ToMd5(String str) {
        return org.springframework.util.DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }
    
    public static void main(String[] args) {
        String str = "11fgf";
        System.out.println("MD5加密方法一：" + Md5Utils.encryptToMd5(str));
        System.out.println("MD5加密方法二：" + Md5Utils.encrypt2ToMd5(str));
        System.out.println("MD5加密方法三：" + Md5Utils.encrypt3ToMd5(str));
    }
}
