package com.llf.servcie;

import java.util.Map;

import com.llf.dto.MerchantForPwdDto;
import com.llf.dto.MerchantLoginDto;
import com.llf.dto.MerchantPwdUpdDto;
import com.llf.dto.MerchantRegisterDto;
import com.llf.exception.BussException;
import com.llf.vo.MerchantRegisterVo;

public interface MerchantLoginService {
	
	/**
	 * 商户注册
	 * @param merchantRegisterDto
	 * @return
	 * @throws BussException
	 */
	MerchantRegisterVo register(MerchantRegisterDto merchantRegisterDto) throws BussException;
	
	/**
	 * 商户登录
	 * @param merchantLoginDto
	 * @return
	 * @throws BussException
	 */
	Map<String,Object> login(MerchantLoginDto merchantLoginDto) throws BussException;
	
	/**
	 * 修改密码
	 * @param merchantPwdUpdDto
	 * @throws BussException
	 */
	void updPassword(MerchantPwdUpdDto merchantPwdUpdDto) throws BussException;
	
	/**
	 * 重置密码
	 * @param merchantPwdUpdDto
	 * @throws BussException
	 */
	void resetPassword(MerchantPwdUpdDto merchantPwdUpdDto) throws BussException;
	
	/**
	 * 忘记密码
	 * @param merchantForPwdDto
	 * @throws BussException
	 */
	void forPwd(MerchantForPwdDto merchantForPwdDto) throws BussException;
	
}
