package com.llf.Enum;

public enum MerchantRole {
	ADMIN("001", "管理员"),
    OPER("009", "操作员");
	
	private String code;
	private String desc;
	
	private MerchantRole(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static MerchantRole getMerStatusByCode(String code) {
		MerchantRole[] values = MerchantRole.values();
        for (MerchantRole value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
