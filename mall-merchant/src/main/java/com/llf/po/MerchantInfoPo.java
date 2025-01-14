package com.llf.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoPo {
	
	private String merchantId;
	private String merchantName;
	private String merchantLvl;
	private String merchantType;
	private String merchantAddr;
	private String merchantTel;
	private String legalPerson;
	private String estalDate;
	private String status;
	private String enterDate;
	private String enterTime;
	private String exitDate;
	private String exitTime;
	private String updateDate;
	private String updateTime;
	private String deleteFlag;

}
