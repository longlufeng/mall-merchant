package com.llf.Enum;

public enum ErrorCode {
	USER_REGISTERD("100001", "用户已经被注册"),
	USER_OR_PWD_ERROR("100002", "用户名或者密码错误");
	
	private String code;
	private String msg;
	
	private ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
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

	public static ErrorCode getErrorCodeByCode(String code) {
		ErrorCode[] values = ErrorCode.values();
        for (ErrorCode value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
