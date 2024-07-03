package com.llf.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantBaseInfoPo {
	
	private String merchantId;
	private String merchantName;
	private String status;
	private String enterDate;
	private String enterTime;
	private String exitDate;
	private String exitTime;
	private String updateDate;
	private String updateTime;
	private String deleteFlag;

}
