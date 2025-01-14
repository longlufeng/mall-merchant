package com.llf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.llf.dto.MerchantInfoQryDto;
import com.llf.po.MerchantInfoPo;

@Mapper
public interface MerchantInfoMapper {
	
	void add(MerchantInfoPo merchantInfoPo);
	
	void del(MerchantInfoPo merchantInfoPo);
	
	void upd(MerchantInfoPo merchantInfoPo);
	
	MerchantInfoPo detailQry(@Param("merchantId") String merchantId);
	
	Map<String,String> detailQryByMerchantId(@Param("merchantId") String merchantId);
	
	List<MerchantInfoPo> listQry(MerchantInfoQryDto merchantInfoQryDto);

}
