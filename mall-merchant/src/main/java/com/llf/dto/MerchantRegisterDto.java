package com.llf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRegisterDto {
	
	private String merchantLoginName;
	private String merchantLoginPwd;

}
