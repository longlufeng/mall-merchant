package com.llf.Enum;

public enum MerchantStatus {
	
	UP("1", "上架"),
    DOWN("2", "下架"),
    OUT("3", "注销");
	
	private String code;
	private String desc;
	
	private MerchantStatus(String code, String desc) {
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
	
	public static MerchantStatus getMerStatusByCode(String code) {
		MerchantStatus[] values = MerchantStatus.values();
        for (MerchantStatus value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
	
}
