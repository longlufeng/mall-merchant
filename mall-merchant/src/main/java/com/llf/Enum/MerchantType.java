package com.llf.Enum;

public enum MerchantType {
	SMALL("1", "小企业"),
    MIDDLE("2", "中型企业"),
    BIG("3", "大型企业");
	
	private String code;
	private String desc;
	
	private MerchantType(String code, String desc) {
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
	
	public static MerchantType getMerTypeByCode(String code) {
		MerchantType[] values = MerchantType.values();
        for (MerchantType value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
