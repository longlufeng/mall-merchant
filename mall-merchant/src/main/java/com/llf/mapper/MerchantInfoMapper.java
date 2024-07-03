package com.llf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.llf.dto.MerchantInfoDto;
import com.llf.po.MerchantDetailInfoPo;
import com.llf.po.MerchantBaseInfoPo;

@Mapper
public interface MerchantInfoMapper {
	
	void addBaseInfo(MerchantBaseInfoPo merchantBaseInfoPo);
	
	void addDetailInfo(MerchantDetailInfoPo merchantDetailInfoPo);
	
	void delBase(MerchantBaseInfoPo MerchantBaseInfoPo);
	
	void delDetail(MerchantDetailInfoPo merchantDetailInfoPo);
	
	void upd(MerchantBaseInfoPo MerchantBaseInfoPo);
	
	MerchantBaseInfoPo qryBaseById(@Param("id") String id);
	
	MerchantDetailInfoPo qryDetailById(@Param("id") String id);
	
	List<MerchantBaseInfoPo> qryList(MerchantInfoDto merchantInfoDto);

}
