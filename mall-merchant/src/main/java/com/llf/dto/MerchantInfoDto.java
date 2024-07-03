package com.llf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoDto {
	
	private String merchantId;
	private String merchantName;
	private String status;
	private String startDate;
	private String endDate;

}
