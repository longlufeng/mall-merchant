package com.llf.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoUpdBo {
	
	private String merchantName;
	private String merchantId;
	private String merchantLvl;
	private String merchantType;
	private String merchantAddr;
	private String merchantTel;
	private String legalPerson;
	private String estalDate;
	private String status;

}
