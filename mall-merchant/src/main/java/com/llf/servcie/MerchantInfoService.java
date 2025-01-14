package com.llf.servcie;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.llf.bo.MerchantInfoUpdBo;
import com.llf.dto.MerchantInfoAddDto;
import com.llf.dto.MerchantInfoDelDto;
import com.llf.dto.MerchantInfoQryDto;
import com.llf.dto.TestDto;
import com.llf.vo.MerchantInfoVo;

public interface MerchantInfoService {
	
	JSONObject test(JSONObject testDto);
	
	void add(MerchantInfoAddDto merchantInfoAddDto);
	
	void del(MerchantInfoDelDto merchantInfoDelDto);
	
	Map<String,String> upd(MerchantInfoUpdBo merchantInfoUpdBo);
	
	MerchantInfoVo detailQry(String id);
	
	Map<String,String> detailQryByMerchantId(String merchantId);
	
	List<MerchantInfoVo> listQry(MerchantInfoQryDto merchantInfoQryDto);
	
}
