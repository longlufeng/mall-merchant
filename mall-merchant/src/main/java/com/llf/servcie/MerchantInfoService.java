package com.llf.servcie;

import java.util.List;

import com.llf.dto.MerchantInfoAddDto;
import com.llf.dto.MerchantInfoDto;
import com.llf.vo.MerchantInfoVo;

public interface MerchantInfoService {
	
	void add(MerchantInfoAddDto merchantInfoAddDto);
	
	void del(MerchantInfoDto merchantInfoDto);
	
	void upd(MerchantInfoDto merchantInfoDto);
	
	MerchantInfoVo qryById(String id);
	
	List<MerchantInfoVo> qryList(MerchantInfoDto merchantInfoDto);

}
