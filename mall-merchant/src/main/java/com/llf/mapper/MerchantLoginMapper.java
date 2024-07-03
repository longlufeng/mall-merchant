package com.llf.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.llf.po.MerchantPwdPo;
import com.llf.po.MerchantRolePo;

@Mapper
public interface MerchantLoginMapper {
	
	void add(MerchantPwdPo merchantLoginPo);
	
	void addRole(MerchantRolePo merchantRolePo);
	
	void upd(MerchantPwdPo merchantLoginPo);
	
	void del(MerchantPwdPo merchantLoginPo);
	
	MerchantPwdPo qry(MerchantPwdPo merchantLoginPo);
	
	Map<String,Object> qryMerchantLoginInfo(MerchantPwdPo merchantLoginPo);
	
	
	
}
