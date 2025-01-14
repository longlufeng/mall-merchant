package com.llf.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llf.dto.MerchantInfoQryDto;
import com.llf.servcie.MerchantInfoService;
import com.llf.vo.MerchantInfoVo;


@RestController
@RequestMapping("/api")
public class MerchantInfoApi {
	
	@Autowired
	MerchantInfoService merchantInfoService;
	
	
	@RequestMapping("/qryByMerchantId")
	public MerchantInfoVo qryByMerchantId(@RequestBody MerchantInfoQryDto merchantInfoDto){
		
		MerchantInfoVo merchantInfoVo = merchantInfoService.detailQry(merchantInfoDto.getMerchantId());
		
		return merchantInfoVo;
	}
	
	
}
