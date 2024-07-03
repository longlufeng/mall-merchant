package com.llf.servcie;

import java.util.Map;

import com.llf.dto.MerchantLoginDto;
import com.llf.dto.MerchantRegisterDto;
import com.llf.exception.BussException;
import com.llf.vo.MerchantRoleInfoVo;

public interface MerchantLoginService {
	
	/**
	 * 新增商户密码信息
	 * @param merchantRegisterDto
	 */
	MerchantRoleInfoVo register(MerchantRegisterDto merchantRegisterDto) throws BussException;
	
	void updPassword(MerchantLoginDto merchantLoginDto);
	
	Map<String,Object> login(MerchantLoginDto merchantLoginDto) throws BussException;
	
}
