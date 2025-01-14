package com.llf.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoAddDto {
	
	@NotNull(message = "用户id不能为空")
	private String merchantName;
	private String merchantType;
	private String merchantAddr;
	private String merchantTel;
	private String legalPerson;
	private String estalDate;

}
