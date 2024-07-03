package com.llf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDetailVo {
	
	private String merchantId;
	private String merchantName;
	private String status;
	private String statusDesc;
	private String enterDate;
	private String enterTime;
	private String exitDate;
	private String exitTime;
	private String updateDate;
	private String updateTime;
	private String merchantType;
	private String merchantAddr;
	private String merchantTel;
	private String legalPerson;
	private String estalDate;
}
