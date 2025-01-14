package com.llf.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantLoginDto {
	
	@NotNull(message = "商户登录名不能为空")
	private String merchantLoginName;
	
	@NotNull(message = "商户登录密码不能为空")
	private String merchantLoginPwd;

}
