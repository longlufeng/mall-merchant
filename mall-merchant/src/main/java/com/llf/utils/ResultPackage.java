package com.llf.utils;

import java.io.Serializable;
 
public class ResultPackage<T> implements Serializable {
 
    private static final long serialVersionUID = -3948389268046368059L;
 
    private String code;
 
    private String msg;
 
    private T data;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResultPackage() {
    }
 
    public ResultPackage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
 
    public static ResultPackage<Object> success() {
    	ResultPackage<Object> result = new ResultPackage<Object>();
        result.setCode("200");
        result.setMsg("交易成功");
        return result;
    }
 
    public static ResultPackage<Object> success(Object data) {
    	ResultPackage<Object> result = new ResultPackage<Object>();
        result.setCode("200");
        result.setMsg("交易成功");
        result.setData(data);
        return result;
    }
    
    public static ResultPackage<Object> failure(String msg) {
    	ResultPackage<Object> result = new ResultPackage<Object>();
        result.setCode("999999");
        result.setMsg(msg);
        return result;
    }
 
    public static ResultPackage<Object> failure(String code,String msg) {
        ResultPackage<Object> result = new ResultPackage<Object>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
 
}