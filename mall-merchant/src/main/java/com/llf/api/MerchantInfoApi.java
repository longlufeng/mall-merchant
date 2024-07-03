package com.llf.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llf.dto.MerchantInfoDto;
import com.llf.servcie.MerchantInfoService;
import com.llf.vo.MerchantInfoVo;


@RestController
@RequestMapping("/api")
public class MerchantInfoApi {
	
	@Autowired
	MerchantInfoService merchantInfoService;
	
	
	@RequestMapping("/qryByMerchantId")
	public MerchantInfoVo qryByMerchantId(@RequestBody MerchantInfoDto merchantInfoDto){
		
		MerchantInfoVo merchantInfoVo = merchantInfoService.qryById(merchantInfoDto.getMerchantId());
		
		return merchantInfoVo;
	}
	
	
}
