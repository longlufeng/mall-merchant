package com.llf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantForPwdDto {
	
	private String merchantLoginName;
	private String merchantLoginPwd;
	private String imgCode;
	
}
