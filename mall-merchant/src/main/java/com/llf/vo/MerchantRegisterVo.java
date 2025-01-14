package com.llf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRegisterVo {
	
	private String merchantId;
	private String merchantLvl;
	private String merchantLoginName;
	private String roleId;
	private String roleName;

}
