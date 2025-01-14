package com.llf.controller;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llf.constant.CachePrefix;
import com.llf.dto.MerchantForPwdDto;
import com.llf.dto.MerchantLoginDto;
import com.llf.dto.MerchantPwdUpdDto;
import com.llf.dto.MerchantRegisterDto;
import com.llf.exception.BussException;
import com.llf.servcie.MerchantLoginService;
import com.llf.utils.RedisUtil;
import com.llf.utils.ResultPackage;
import com.llf.vo.MerchantRegisterVo;


@RestController
@RequestMapping("/merchant")
public class MerchantLoginController {
	
	@Autowired
	MerchantLoginService merchantLoginService;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
	@Value("${session.timeout}")
	public long sessionTimeout;
	
	
	@RequestMapping("/merchantLogin/register")
	public ResultPackage<?> register(HttpServletRequest request,@RequestBody MerchantRegisterDto merchantRegisterDto) throws BussException{
		
		MerchantRegisterVo vo = merchantLoginService.register(merchantRegisterDto);
		
		// 设置会话，会将会话信息保存在redis
		HttpSession session = request.getSession();
		
		session.setAttribute("userType", "2");
		session.setAttribute("merchantId", vo.getMerchantId());
		session.setAttribute("merchantLvl", vo.getMerchantLvl());
		session.setAttribute("merchantLoginName", vo.getMerchantLoginName());
		session.setAttribute("roleId", vo.getRoleId());
		session.setAttribute("roleName", vo.getRoleName());
		
		return ResultPackage.success(vo);
	}
	
	@RequestMapping("/login")
	public ResultPackage<?> login(HttpServletRequest request,@RequestBody MerchantLoginDto merchantLoginDto) throws BussException{
		
		Map<String,Object> resMap = merchantLoginService.login(merchantLoginDto);
		resMap.put("userType", "2");
		
		String token = UUID.randomUUID().toString().replace("-", "");
		resMap.put("token", token);
		
		redisUtil.setObject(token, MapUtils.getString(resMap, "merchantId"), sessionTimeout, TimeUnit.SECONDS);
		redisUtil.setMap(CachePrefix.USER_ID + MapUtils.getString(resMap, "merchantId"), resMap, sessionTimeout, TimeUnit.SECONDS);
		return ResultPackage.success(resMap);
	}
	
	@RequestMapping("/exit")
	public ResultPackage<?> exit(HttpServletRequest request) throws BussException{
		
		// 清除会话，从redis删除会话信息
		HttpSession session = request.getSession();
		String merchantLoginName = (String)session.getAttribute("merchantLoginName");
		redisTemplate.delete("merchantLoginName:"+merchantLoginName);
		session.invalidate();
		
		return ResultPackage.success();
	}
	
	@RequestMapping("/pwdUpd")
	public ResultPackage<?> pwdUpd(HttpServletRequest request,@RequestBody MerchantPwdUpdDto merchantPwdUpdDto) throws BussException{
		
		// 1.获取正在处于登录状态商户的会话
		HttpSession session = request.getSession();
		// 2.从会话中获取当前商户的商户Id
		String merchantId = (String) session.getAttribute("merchantId");
		merchantPwdUpdDto.setMerchantId(merchantId);
		// 3.修改当前登录商户的密码
		merchantLoginService.updPassword(merchantPwdUpdDto);
		
		return ResultPackage.success();
		
	}
	
	@RequestMapping("/pwdReset")
	public ResultPackage<?> pwdReset(HttpServletRequest request) throws BussException{
		
		// 1.获取正在处于登录状态商户的会话
		HttpSession session = request.getSession();
		// 2.从会话中获取当前商户的商户Id
		String merchantId = (String) session.getAttribute("merchantId");
		
		MerchantPwdUpdDto merchantPwdUpdDto = new MerchantPwdUpdDto();
		merchantPwdUpdDto.setMerchantId(merchantId);
		// 3.重置当前登录商户的密码
		merchantLoginService.resetPassword(merchantPwdUpdDto);
		
		return ResultPackage.success();
	}
	
	@RequestMapping("/pwdForget")
	public ResultPackage<?> pwdForget(@RequestBody MerchantForPwdDto merchantForPwdDto) throws BussException{
		
		merchantLoginService.forPwd(merchantForPwdDto);
		
		return ResultPackage.success();
	}
	
}
