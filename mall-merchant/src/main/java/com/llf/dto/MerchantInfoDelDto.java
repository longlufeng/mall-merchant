package com.llf.dto;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantInfoDelDto {
	
	@NotBlank (message = "merchantId不能为空")
	private String merchantId;

}
