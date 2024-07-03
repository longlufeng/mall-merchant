package com.llf.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.llf.dto.MerchantInfoAddDto;
import com.llf.dto.MerchantInfoDto;
import com.llf.servcie.MerchantInfoService;
import com.llf.utils.DateUtil;
import com.llf.utils.ResultPackage;
import com.llf.vo.MerchantInfoVo;


@RestController
@RequestMapping("/merchant")
public class MerchantInfoController {
	
	@Autowired
	MerchantInfoService merchantInfoService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@RequestMapping("/test")
	public ResultPackage<?> test(@RequestParam("merchantId") String merchantId){
		
		return ResultPackage.success(merchantId);
	}
	
	@RequestMapping("/add")
	public ResultPackage<?> add(@RequestBody MerchantInfoAddDto merchantInfoAddDto){
		
		merchantInfoService.add(merchantInfoAddDto);
		
		return ResultPackage.success();
	}
	
	@RequestMapping("/del")
	public ResultPackage<?> del(@RequestBody MerchantInfoDto merchantInfoDto){
		
		merchantInfoService.del(merchantInfoDto);
		
		return ResultPackage.success();
	}
	
	@RequestMapping("/upd")
	public ResultPackage<?> upd(@RequestBody MerchantInfoDto merchantInfoDto){
		
		merchantInfoService.upd(merchantInfoDto);
		
		return ResultPackage.success();
	}
	
	@RequestMapping("/qryById")
	public ResultPackage<?> qryById(@RequestBody MerchantInfoDto merchantInfoDto){
		
		MerchantInfoVo merchantInfoVo = merchantInfoService.qryById(merchantInfoDto.getMerchantId());
		
		return ResultPackage.success(merchantInfoVo);
	}
	
	@RequestMapping("/qryList")
	public ResultPackage<?> qryList(@RequestBody MerchantInfoDto merchantInfoDto){
		return ResultPackage.success(merchantInfoService.qryList(merchantInfoDto));
	}
	
	@RequestMapping("/getCurDate")
	public ResultPackage<?> getCurDate(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startDate", DateUtil.getCurDateMonthAgo(1));
		map.put("endDate", DateUtil.getCurDate());
		
		return ResultPackage.success(map);
	}
	
}
