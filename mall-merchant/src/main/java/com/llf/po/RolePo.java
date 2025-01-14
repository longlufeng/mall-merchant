package com.llf.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePo {
	
	private String roleId;
	private String roleName;
	private String createDate;
	private String createTime;
	private String updateDate;
	private String updateTime;

}
