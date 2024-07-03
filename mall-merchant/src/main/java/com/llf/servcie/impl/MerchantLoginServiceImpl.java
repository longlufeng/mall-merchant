package com.llf.servcie.impl;


import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.llf.Enum.ErrorCode;
import com.llf.Enum.MerchantRole;
import com.llf.dto.MerchantLoginDto;
import com.llf.dto.MerchantRegisterDto;
import com.llf.exception.BussException;
import com.llf.mapper.MerchantLoginMapper;
import com.llf.po.MerchantPwdPo;
import com.llf.po.MerchantRolePo;
import com.llf.servcie.MerchantLoginService;
import com.llf.utils.Base64Util;
import com.llf.utils.DateUtil;
import com.llf.utils.GenerateStrUtil;
import com.llf.utils.SM2Util;
import com.llf.vo.MerchantRoleInfoVo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MerchantLoginServiceImpl implements MerchantLoginService{
	
	@Autowired
	MerchantLoginMapper merchantLoginMapper;
	
	@Value("${sm2.privateKey}")
	String privateKey;

	@Transactional(rollbackFor = Exception.class)
	public MerchantRoleInfoVo register(MerchantRegisterDto merchantRegisterDto) throws BussException {
		
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		String mercahntId = GenerateStrUtil.generateMercahntId();
		merchantPwdPo.setMerchantId(mercahntId);
		merchantPwdPo.setUserName(merchantRegisterDto.getUserName());
		
		MerchantPwdPo queryMerchantLoginPo = merchantLoginMapper.qry(merchantPwdPo); 
		if(queryMerchantLoginPo != null) {
			throw new BussException(ErrorCode.USER_REGISTERD.getCode(), ErrorCode.USER_REGISTERD.getMsg());
		}
		
		// 密码base64解密
		String userPassword = Base64Util.decode(merchantRegisterDto.getUserPassword());
		// 密码SM2解密
		userPassword = SM2Util.decrypt(userPassword, privateKey);
		log.info("userPassword为{}：", userPassword);
		merchantPwdPo.setUserPassword(userPassword);
		
		merchantPwdPo.setCreateDate(DateUtil.getCurDate());
		merchantPwdPo.setCreateTime(DateUtil.getCurDateTime());
		
		// 新增商户密码信息
		merchantLoginMapper.add(merchantPwdPo); 
		
		// 新增商户权限
		MerchantRolePo merchantRolePo = new MerchantRolePo();
		String roleId = StringUtils.isEmpty(merchantRegisterDto.getRoleId()) ? "009" : merchantRegisterDto.getRoleId();
		merchantRolePo.setRoleId(roleId);
		merchantRolePo.setRoleName(MerchantRole.getMerStatusByCode(roleId).getDesc());
		merchantRolePo.setUserName(merchantRegisterDto.getUserName() );
		merchantRolePo.setCreateDate(DateUtil.getCurDate());
		merchantRolePo.setCreateTime(DateUtil.getCurDateTime());
		merchantLoginMapper.addRole(merchantRolePo); 
		
		// 返回商户用户权限
		MerchantRoleInfoVo merchantRoleInfoVo = new MerchantRoleInfoVo();
		merchantRoleInfoVo.setUserName(merchantRegisterDto.getUserName());
		merchantRoleInfoVo.setRoleId(merchantRegisterDto.getRoleId());
		merchantRoleInfoVo.setRoleName(MerchantRole.getMerStatusByCode(roleId).getDesc());
		return merchantRoleInfoVo;
		
	}
	
	public void updPassword(MerchantLoginDto merchantLoginDto) {
		
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setUserName(merchantLoginDto.getUserName());
		merchantPwdPo.setUserPassword(merchantLoginDto.getUserPassword());
		merchantPwdPo.setUpdateDate(DateUtil.getCurDate());
		merchantPwdPo.setUpdateTime(DateUtil.getCurDateTime());
		
		merchantLoginMapper.upd(merchantPwdPo); 
		
	}

	public Map<String,Object> login(MerchantLoginDto merchantLoginDto) throws BussException {
		
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setUserName(merchantLoginDto.getUserName());
		
		// 密码base64解密
		String userPassword = Base64Util.decode(merchantLoginDto.getUserPassword());
		// 密码SM2解密
		userPassword = SM2Util.decrypt(userPassword, privateKey);
		
		merchantPwdPo.setUserPassword(userPassword);
		
		Map<String,Object> resMap = merchantLoginMapper.qryMerchantLoginInfo(merchantPwdPo); 
		if(resMap == null) {
			throw new BussException(ErrorCode.USER_OR_PWD_ERROR.getCode(), ErrorCode.USER_OR_PWD_ERROR.getMsg());
		}
		
		return resMap;
	}


}
