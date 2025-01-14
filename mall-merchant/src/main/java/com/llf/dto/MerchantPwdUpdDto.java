package com.llf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPwdUpdDto {
	
	private String merchantId;
	private String oldPwd;
	private String newPwd;
	
}
