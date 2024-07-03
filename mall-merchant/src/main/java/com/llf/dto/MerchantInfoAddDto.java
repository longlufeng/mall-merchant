package com.llf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoAddDto {
	
	private String merchantName;
	private String merchantType;
	private String merchantAddr;
	private String merchantTel;
	private String legalPerson;
	private String estalDate;

}
