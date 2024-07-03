package com.llf.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llf.dto.MerchantLoginDto;
import com.llf.dto.MerchantRegisterDto;
import com.llf.exception.BussException;
import com.llf.servcie.MerchantLoginService;
import com.llf.utils.ResultPackage;
import com.llf.vo.MerchantRoleInfoVo;


@RestController
@RequestMapping("/merchant")
public class MerchantPwdController {
	
	@Autowired
	MerchantLoginService merchantLoginService;
	
	@RequestMapping("/updPwd")
	public ResultPackage<?> upd(@RequestBody MerchantRegisterDto merchantLoginDto) throws BussException{
		MerchantRoleInfoVo vo = merchantLoginService.register(merchantLoginDto);
		return ResultPackage.success(vo);
	}
	
	@RequestMapping("/resetPwd")
	public ResultPackage<?> reset(HttpServletRequest request,@RequestBody MerchantLoginDto merchantLoginDto){
		
		merchantLoginService.updPassword(merchantLoginDto);
		
		return ResultPackage.success();
	}
	
}
