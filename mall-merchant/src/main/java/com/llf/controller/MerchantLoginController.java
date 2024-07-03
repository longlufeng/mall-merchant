package com.llf.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
public class MerchantLoginController {
	
	@Autowired
	MerchantLoginService merchantLoginService;
	
	@Autowired
    private StringRedisTemplate redisTemplate;
	
	@RequestMapping("/register")
	public ResultPackage<?> register(@RequestBody MerchantRegisterDto merchantRegisterDto) throws BussException{
		MerchantRoleInfoVo vo = merchantLoginService.register(merchantRegisterDto);
		return ResultPackage.success(vo);
	}
	
	@RequestMapping("/login")
	public ResultPackage<?> login(HttpServletRequest request,@RequestBody MerchantLoginDto merchantLoginDto) throws BussException{
		
		Map<String,Object> resMap = merchantLoginService.login(merchantLoginDto);
		if(resMap != null) {
			HttpSession session = request.getSession();
            session.setAttribute("loginUserName", resMap.get("userName"));
            session.setAttribute("loginMerchantId", resMap.get("merchantId"));
            redisTemplate.opsForValue().set("loginUserName:" + resMap.get("userName"), session.getId());
		}
		
		return ResultPackage.success(resMap);
	}
	
}
