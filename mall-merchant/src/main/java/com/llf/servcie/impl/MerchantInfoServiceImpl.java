package com.llf.servcie.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.llf.Enum.MerchantStatus;
import com.llf.dto.MerchantInfoAddDto;
import com.llf.dto.MerchantInfoDto;
import com.llf.mapper.MerchantInfoMapper;
import com.llf.po.MerchantDetailInfoPo;
import com.llf.po.MerchantBaseInfoPo;
import com.llf.servcie.MerchantInfoService;
import com.llf.utils.DateUtil;
import com.llf.utils.GenerateStrUtil;
import com.llf.vo.MerchantInfoVo;

@Service
public class MerchantInfoServiceImpl implements MerchantInfoService{
	
	@Autowired
	MerchantInfoMapper merchantInfoMapper;

	@Transactional( rollbackFor={Exception.class})
	public void add(MerchantInfoAddDto merchantInfoAddDto) {
		
		// 新增商户基本信息
		MerchantBaseInfoPo merchantBaseInfoPo = new MerchantBaseInfoPo();
		String mercahntId = GenerateStrUtil.generateMercahntId();
		merchantBaseInfoPo.setMerchantId(mercahntId);
		merchantBaseInfoPo.setMerchantName(merchantInfoAddDto.getMerchantName());
		merchantBaseInfoPo.setStatus(MerchantStatus.UP.getCode());
		merchantBaseInfoPo.setEnterDate(DateUtil.getCurDate());
		merchantBaseInfoPo.setEnterTime(DateUtil.getCurDateTime());
		merchantInfoMapper.addBaseInfo(merchantBaseInfoPo); 
		
		// 新增商户详细信息
		MerchantDetailInfoPo merchantDetailInfoPo = new MerchantDetailInfoPo();
		merchantDetailInfoPo.setMerchantId(mercahntId);
		merchantDetailInfoPo.setMerchantType(merchantInfoAddDto.getMerchantType());
		merchantDetailInfoPo.setMerchantAddr(merchantInfoAddDto.getMerchantAddr());
		merchantDetailInfoPo.setMerchantTel(merchantInfoAddDto.getMerchantTel());
		merchantDetailInfoPo.setLegalPerson(merchantInfoAddDto.getLegalPerson());
		merchantDetailInfoPo.setEstalDate(merchantInfoAddDto.getEstalDate());
		merchantDetailInfoPo.setCreateDate(DateUtil.getCurDate());
		merchantDetailInfoPo.setCreateTime(DateUtil.getCurDateTime());
		merchantInfoMapper.addDetailInfo(merchantDetailInfoPo);
	}

	@Transactional( rollbackFor={Exception.class})
	public void del(MerchantInfoDto merchantInfoDto) {
		
		// 删除商户基本信息
		MerchantBaseInfoPo merchantBaseInfoPo = new MerchantBaseInfoPo();
		merchantBaseInfoPo.setMerchantId(merchantInfoDto.getMerchantId());
		merchantInfoMapper.delBase(merchantBaseInfoPo);
		
		// 删除商户详细信息
		MerchantDetailInfoPo merchantDetailInfoPo = new MerchantDetailInfoPo();
		merchantDetailInfoPo.setMerchantId(merchantInfoDto.getMerchantId());
		merchantInfoMapper.delDetail(merchantDetailInfoPo);
		
	}

	public void upd(MerchantInfoDto merchantInfoDto) {
		MerchantBaseInfoPo MerchantBaseInfoPo = new MerchantBaseInfoPo();
		MerchantBaseInfoPo.setMerchantId(merchantInfoDto.getMerchantId());
		MerchantBaseInfoPo.setMerchantName(merchantInfoDto.getMerchantName());
		MerchantBaseInfoPo.setStatus(merchantInfoDto.getStatus());
		if(MerchantStatus.DOWN.getCode().equals(merchantInfoDto.getStatus())) {
			MerchantBaseInfoPo.setExitDate(DateUtil.getCurDate());
			MerchantBaseInfoPo.setExitTime(DateUtil.getCurDateTime());
		}
		merchantInfoMapper.upd(MerchantBaseInfoPo);
	}

	public MerchantInfoVo qryById(String id) {
		MerchantInfoVo merchantInfoVo = new MerchantInfoVo();
		
		MerchantBaseInfoPo merchantBaseInfoPo = merchantInfoMapper.qryBaseById(id);
		if(merchantBaseInfoPo != null) {
			BeanUtils.copyProperties(merchantBaseInfoPo, merchantInfoVo);
		}
		
		MerchantDetailInfoPo merchantDetailInfoPo = merchantInfoMapper.qryDetailById(id);
		if(merchantDetailInfoPo != null) {
			BeanUtils.copyProperties(merchantDetailInfoPo, merchantInfoVo);
		}
		
		return merchantInfoVo;
	}

	public List<MerchantInfoVo> qryList(MerchantInfoDto merchantInfoDto) {
		List<MerchantBaseInfoPo> listPo = merchantInfoMapper.qryList(merchantInfoDto);
		if(listPo == null || listPo.size() == 0) {
			return new ArrayList<MerchantInfoVo>();
		}
		
		List<MerchantInfoVo> listVo = new ArrayList<MerchantInfoVo>();
		for(MerchantBaseInfoPo po : listPo) {
			MerchantInfoVo vo = new MerchantInfoVo();
			BeanUtils.copyProperties(po, vo);
			vo.setStatusDesc(MerchantStatus.getMerStatusByCode(vo.getStatus()).getDesc());
			listVo.add(vo);
		}
		
		return listVo;
	}

}
