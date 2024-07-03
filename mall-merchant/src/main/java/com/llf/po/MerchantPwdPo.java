package com.llf.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPwdPo {
	
	private String merchantId;
	private String userName;
	private String userPassword;
	private String createDate;
	private String createTime;
	private String updateDate;
	private String updateTime;

}
