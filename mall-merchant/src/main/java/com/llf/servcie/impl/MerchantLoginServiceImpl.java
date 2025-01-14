package com.llf.servcie.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.llf.Enum.ErrorCode;
import com.llf.Enum.MerchantRole;
import com.llf.Enum.MerchantStatus;
import com.llf.dto.MerchantForPwdDto;
import com.llf.dto.MerchantLoginDto;
import com.llf.dto.MerchantPwdUpdDto;
import com.llf.dto.MerchantRegisterDto;
import com.llf.exception.BussException;
import com.llf.mapper.MerchantInfoMapper;
import com.llf.mapper.MerchantLoginMapper;
import com.llf.po.MerchantInfoPo;
import com.llf.po.MerchantPwdPo;
import com.llf.po.MerchantRolePo;
import com.llf.servcie.MerchantLoginService;
import com.llf.utils.Base64Util;
import com.llf.utils.DateUtil;
import com.llf.utils.GenerateStrUtil;
import com.llf.utils.Md5Utils;
import com.llf.utils.SM2Util;
import com.llf.vo.MerchantRegisterVo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MerchantLoginServiceImpl implements MerchantLoginService{
	
	@Autowired
	MerchantLoginMapper merchantLoginMapper;
	
	@Autowired
	MerchantInfoMapper merchantInfoMapper;
	
	@Value("${sm2.privateKey}")
	String privateKey;
	
	@Value("${default.pwd:123456}")
	String defaultPwd;
	
	@Value("${isTest:false}")
	boolean isTest;
	
	/**
	 * 商户注册
	 */
	@Transactional(rollbackFor = Exception.class)
	public MerchantRegisterVo register(MerchantRegisterDto merchantRegisterDto) throws BussException {
		
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setMerchantLoginName(merchantRegisterDto.getMerchantLoginName());
		
		// 1.校验用户是否已经被注册
		checkUserRegistered(merchantPwdPo);
		
		// 2.登录密码base64解密
		String merchantLoginPwd = null;
		if(isTest) {
			merchantLoginPwd = merchantRegisterDto.getMerchantLoginPwd();
		}else {
			merchantLoginPwd = Base64Util.decode(merchantRegisterDto.getMerchantLoginPwd());
			// 3.登录密码SM2解密
			merchantLoginPwd = SM2Util.decrypt(merchantLoginPwd, privateKey);
		}
		log.info("merchantLoginPwd为{}：", merchantLoginPwd);
		
		// 4.新增商户密码
		merchantPwdPo.setMerchantLoginPwd(merchantLoginPwd);
		// 4.1生成商户Id
		String merchantId = GenerateStrUtil.generateMercahntId();
		merchantPwdPo.setMerchantId(merchantId);
		merchantPwdPo.setCreateDate(DateUtil.getCurDate());
		merchantPwdPo.setCreateTime(DateUtil.getCurDateTime());
		merchantLoginMapper.add(merchantPwdPo); 
		
		// 5.新增商户信息
		MerchantInfoPo merchantInfoPo = new MerchantInfoPo();
		merchantInfoPo.setMerchantId(merchantId);
		merchantInfoPo.setMerchantLvl("01");
		merchantInfoPo.setStatus(MerchantStatus.UP.getCode());
		merchantInfoPo.setEnterDate(DateUtil.getCurDate());
		merchantInfoPo.setEnterTime(DateUtil.getCurDateTime());
		merchantInfoMapper.add(merchantInfoPo); 
		
		
		// 6.新增商户角色
		MerchantRolePo merchantRolePo = new MerchantRolePo();
		merchantRolePo.setMerchantId(merchantId);
		merchantRolePo.setRoleId(MerchantRole.OPER.getCode());
		merchantRolePo.setCreateDate(DateUtil.getCurDate());
		merchantRolePo.setCreateTime(DateUtil.getCurDateTime());
		merchantLoginMapper.addRole(merchantRolePo); 
		
		// 返回商户用户权限
		MerchantRegisterVo merchantRegisterVo = new MerchantRegisterVo();
		merchantRegisterVo.setMerchantId(merchantId);
		merchantRegisterVo.setMerchantLvl("01");
		merchantRegisterVo.setMerchantLoginName(merchantRegisterDto.getMerchantLoginName());
		merchantRegisterVo.setRoleId(MerchantRole.OPER.getCode());
		merchantRegisterVo.setRoleName(MerchantRole.OPER.getDesc());
		return merchantRegisterVo;
		
	}
	
	/**
	 * 商户登录
	 */
	public Map<String,Object> login(MerchantLoginDto merchantLoginDto) throws BussException {
		
		String merchantLoginPwd = null;
		if(isTest) {
			merchantLoginPwd = merchantLoginDto.getMerchantLoginPwd();
		}else {
			// 1.登录密码base64解密
			merchantLoginPwd = Base64Util.decode(merchantLoginDto.getMerchantLoginPwd());
			// 2.登录密码SM2解密
			merchantLoginPwd = SM2Util.decrypt(merchantLoginPwd, privateKey);
		}
		
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setMerchantLoginName(merchantLoginDto.getMerchantLoginName());
		merchantPwdPo.setMerchantLoginPwd(merchantLoginPwd);
		
		// 3.通过商户登录名和商户登录密码到商户密码表查询数据
		MerchantPwdPo resMerchantPwdPo = merchantLoginMapper.merchantPwdQry(merchantPwdPo); 
		if(resMerchantPwdPo == null) {
			throw new BussException(ErrorCode.USER_OR_PWD_ERROR.getCode(), ErrorCode.USER_OR_PWD_ERROR.getMsg());
		}
		
		// 4.通过商户Id查询商户详细信息
		Map<String,Object> resMap = merchantLoginMapper.merchantDetailInfoQry(resMerchantPwdPo);
		
		return resMap;
	}
	
	private void checkUserRegistered(MerchantPwdPo merchantPwdPo) throws BussException {
		
		MerchantPwdPo queryMerchantLoginPo = merchantLoginMapper.merchantPwdQry(merchantPwdPo); 
		if(queryMerchantLoginPo != null) {
			throw new BussException(ErrorCode.USER_REGISTERD.getCode(), ErrorCode.USER_REGISTERD.getMsg());
		}
		
	}

	/**
	 * 修改商户密码
	 */
	public void updPassword(MerchantPwdUpdDto merchantPwdUpdDto) throws BussException{
		
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setMerchantId(merchantPwdUpdDto.getMerchantId());
		
		// 1.旧密码先base64解密然后sm2解密
		String merchantLoginPwd = null;
		if(isTest) {
			merchantLoginPwd = merchantPwdUpdDto.getOldPwd();
		}else {
			merchantLoginPwd = SM2Util.decrypt(Base64Util.decode(merchantPwdUpdDto.getOldPwd()), privateKey);
		}
		merchantPwdPo.setMerchantLoginPwd(merchantLoginPwd);
		
		// 2.通过商户Id和旧密码查询商户密码信息
		MerchantPwdPo qryMerchantPwdPo = merchantLoginMapper.merchantPwdQry(merchantPwdPo);
		if(qryMerchantPwdPo == null) {
			throw new BussException(ErrorCode.USER_OLD_PWD_ERROR.getCode(), ErrorCode.USER_OLD_PWD_ERROR.getMsg());
		}
		
		// 3.新密码先base64解密然后sm2解密
		if(isTest) {
			merchantLoginPwd = merchantPwdUpdDto.getNewPwd();
		}else {
			merchantLoginPwd = SM2Util.decrypt(Base64Util.decode(merchantPwdUpdDto.getNewPwd()), privateKey);
		}
		merchantPwdPo.setMerchantLoginPwd(merchantLoginPwd);
		merchantPwdPo.setUpdateDate(DateUtil.getCurDate());
		merchantPwdPo.setUpdateTime(DateUtil.getCurDateTime());
		
		// 4.通过商户Id为条件更新商户密码信息
		merchantLoginMapper.upd(merchantPwdPo); 
		
	}
	
	/**
	 * 重置商户密码
	 */
	public void resetPassword(MerchantPwdUpdDto merchantPwdUpdDto) throws BussException{
		
		String MerchantLoginPwd = null;
		if(isTest) {
			MerchantLoginPwd = defaultPwd;
		}else {
			MerchantLoginPwd = Md5Utils.encrypt2ToMd5(defaultPwd);
		}
		
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setMerchantId(merchantPwdUpdDto.getMerchantId());
		merchantPwdPo.setMerchantLoginPwd(MerchantLoginPwd);
		merchantPwdPo.setUpdateDate(DateUtil.getCurDate());
		merchantPwdPo.setUpdateTime(DateUtil.getCurDateTime());
		
		// 通过商户Id为条件更新商户密码信息
		merchantLoginMapper.upd(merchantPwdPo); 
		
	}
	
	/**
	 * 忘记密码
	 */
	public void forPwd(MerchantForPwdDto merchantForPwdDto) throws BussException {
		
		//修改密码
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setMerchantLoginName (merchantForPwdDto.getMerchantLoginName());
		String userPassword = SM2Util.decrypt(Base64Util.decode(merchantForPwdDto.getMerchantLoginPwd()), privateKey);
		merchantPwdPo.setMerchantLoginPwd(userPassword);
		merchantPwdPo.setUpdateDate(DateUtil.getCurDate());
		merchantPwdPo.setUpdateTime(DateUtil.getCurDateTime());
		merchantLoginMapper.upd(merchantPwdPo); 
		
	}
	
}
