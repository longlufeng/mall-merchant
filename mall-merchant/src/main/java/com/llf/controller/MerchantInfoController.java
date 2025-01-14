package com.llf.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.llf.bo.MerchantInfoUpdBo;
import com.llf.dto.MerchantInfoAddDto;
import com.llf.dto.MerchantInfoDelDto;
import com.llf.dto.MerchantInfoQryDto;
import com.llf.dto.MerchantInfoUpdDto;
import com.llf.dto.TestDto;
import com.llf.servcie.MerchantInfoService;
import com.llf.utils.ResultPackage;
import com.llf.utils.StringUtil;
import com.llf.vo.MerchantInfoVo;

import cn.hutool.core.bean.BeanUtil;


@RestController
@RequestMapping("/merchant")
public class MerchantInfoController {
	
	private static Logger logger = LogManager.getLogger(MerchantInfoController.class);
	
	@Autowired
	MerchantInfoService merchantInfoService;
	
	@RequestMapping("/merchantInfo/test")
	public ResultPackage<?> test(@RequestBody JSONObject requestBody){
		
		merchantInfoService.test(requestBody);
		
		return ResultPackage.success(requestBody);
	}
	
	@RequestMapping("/add")
	public ResultPackage<?> add(@RequestBody MerchantInfoAddDto merchantInfoAddDto){
		
		merchantInfoService.add(merchantInfoAddDto);
		
		return ResultPackage.success();
	}
	
	@RequestMapping("/merchantInfo/del")
	public ResultPackage<?> del(@RequestBody  @Validated MerchantInfoDelDto merchantInfoDelDto){
		
		merchantInfoService.del(merchantInfoDelDto);
		
		return ResultPackage.success();
	}
	
	@RequestMapping("/merchantInfo/upd")
	public ResultPackage<?> upd(HttpServletRequest request,@RequestBody MerchantInfoUpdDto merchantInfoUpdDto){
		
		HttpSession session = request.getSession();
		String merchantId = (String)session.getAttribute("merchantId"); 
		
		MerchantInfoUpdBo merchantInfoUpdBo = new MerchantInfoUpdBo();
		BeanUtil.copyProperties(merchantInfoUpdDto, merchantInfoUpdBo);
		merchantInfoUpdBo.setMerchantId(merchantId);
		
		Map<String,String> resMap = merchantInfoService.upd(merchantInfoUpdBo);
		
		session.setAttribute("userType", "2");
		session.setAttribute("merchantId", MapUtils.getString(resMap, "merchantId"));
		session.setAttribute("merchantName", MapUtils.getString(resMap, "merchantName"));
		session.setAttribute("merchantLvl", MapUtils.getString(resMap, "merchantLvl"));
		session.setAttribute("status", MapUtils.getString(resMap, "status"));
		session.setAttribute("merchantType", MapUtils.getString(resMap, "merchantType"));
		session.setAttribute("merchantAddr", MapUtils.getString(resMap, "merchantAddr"));
		session.setAttribute("merchantTel", MapUtils.getString(resMap, "merchantTel"));
		session.setAttribute("legalPerson", MapUtils.getString(resMap, "legalPerson"));
		session.setAttribute("estalDate", MapUtils.getString(resMap, "estalDate"));
		session.setAttribute("userName", MapUtils.getString(resMap, "userName"));
		session.setAttribute("roleId", MapUtils.getString(resMap, "roleId"));
		session.setAttribute("roleName", MapUtils.getString(resMap, "roleName"));
		
		return ResultPackage.success(resMap);
	}
	
	@RequestMapping("/merchantInfo/detailQry")
	public ResultPackage<?> detailQry(HttpServletRequest request,@RequestBody MerchantInfoQryDto merchantInfoQryDto){
		
		String merchantId = merchantInfoQryDto.getMerchantId();
		if(StringUtil.isEmpty(merchantId)) {
			HttpSession session = request.getSession();
			merchantId = (String)session.getAttribute("merchantId"); 
		}
		
		MerchantInfoVo merchantInfoVo = merchantInfoService.detailQry(merchantInfoQryDto.getMerchantId());
		
		return ResultPackage.success(merchantInfoVo);
	}
	
	@RequestMapping("/merchantInfo/listQry")
	public ResultPackage<?> listQry(HttpServletRequest request,@RequestBody MerchantInfoQryDto merchantInfoQryDto){
		
		HttpSession session = request.getSession();
		merchantInfoQryDto.setMerchantId(((String)session.getAttribute("merchantId")));
		
		return ResultPackage.success(merchantInfoService.listQry(merchantInfoQryDto));
	}
	
}
