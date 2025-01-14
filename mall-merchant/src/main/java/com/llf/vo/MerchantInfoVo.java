package com.llf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoVo {
	
	private String merchantId;
	private String merchantName;
	private String merchantLvl;
	private String merchantLvlDesc;
	private String merchantType;
	private String merchantTypeDesc;
	private String merchantAddr;
	private String merchantTel;
	private String legalPerson;
	private String estalDate;
	private String status;
	private String statusDesc;
	private String enterDate;
	private String enterTime;
	private String exitDate;
	private String exitTime;
	private String updateDate;
	private String updateTime;
	

}
