package com.llf.servcie.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.utils.StringUtils;
import com.llf.Enum.MerchantRole;
import com.llf.Enum.MerchantStatus;
import com.llf.Enum.MerchantType;
import com.llf.bo.MerchantInfoUpdBo;
import com.llf.dto.MerchantInfoAddDto;
import com.llf.dto.MerchantInfoDelDto;
import com.llf.dto.MerchantInfoQryDto;
import com.llf.dto.MerchantInfoUpdDto;
import com.llf.dto.TestDto;
import com.llf.mapper.MerchantInfoMapper;
import com.llf.mapper.MerchantLoginMapper;
import com.llf.po.MerchantInfoPo;
import com.llf.po.MerchantPwdPo;
import com.llf.po.MerchantRolePo;
import com.llf.servcie.MerchantInfoService;
import com.llf.utils.DateUtil;
import com.llf.utils.GenerateStrUtil;
import com.llf.utils.Md5Utils;
import com.llf.vo.MerchantInfoVo;

import cn.hutool.core.collection.CollectionUtil;

@Service
public class MerchantInfoServiceImpl implements MerchantInfoService {

	@Autowired
	MerchantInfoMapper merchantInfoMapper;

	@Autowired
	MerchantLoginMapper merchantLoginMapper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${merchant.default-pwd}")
	String defaultPwd;

	@Value("${merchant.salt}")
	String salt;
	
	public JSONObject test(JSONObject testDto) {
		return testDto;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Deprecated
	public void add(MerchantInfoAddDto merchantInfoAddDto) {

		// 1.新增商户基本信息
		MerchantInfoPo merchantInfoPo = new MerchantInfoPo();
		String merchantId = GenerateStrUtil.generateMercahntId();
		merchantInfoPo.setMerchantId(merchantId);
		merchantInfoPo.setMerchantName(merchantInfoAddDto.getMerchantName());
		merchantInfoPo.setMerchantLvl("01");
		merchantInfoPo.setStatus(MerchantStatus.UP.getCode());
		merchantInfoPo.setEnterDate(DateUtil.getCurDate());
		merchantInfoPo.setEnterTime(DateUtil.getCurDateTime());
		merchantInfoMapper.add(merchantInfoPo);

		// 2.新增商户密码
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setMerchantId(merchantId);
		merchantPwdPo.setMerchantLoginName(merchantInfoAddDto.getMerchantTel());// 用户姓名默认商户电话
		String str = merchantInfoAddDto.getMerchantTel() + defaultPwd + salt;
		merchantPwdPo.setMerchantLoginPwd(Md5Utils.encryptToMd5(str));
		merchantPwdPo.setCreateDate(DateUtil.getCurDate());
		merchantPwdPo.setCreateTime(DateUtil.getCurDateTime());
		merchantLoginMapper.add(merchantPwdPo);

		// 3.新增商户角色
		MerchantRolePo merchantRolePo = new MerchantRolePo();
		merchantRolePo.setMerchantId(merchantId);
		merchantRolePo.setRoleId(MerchantRole.OPER.getCode());
		merchantRolePo.setCreateDate(DateUtil.getCurDate());
		merchantRolePo.setCreateTime(DateUtil.getCurDateTime());
		merchantLoginMapper.addRole(merchantRolePo);
	}

	@Transactional(rollbackFor = { Exception.class })
	public void del(MerchantInfoDelDto merchantInfoDelDto) {

		// 删除商户基本信息
		MerchantInfoPo merchantInfoPo = new MerchantInfoPo();
		merchantInfoPo.setMerchantId(merchantInfoDelDto.getMerchantId());
		merchantInfoMapper.del(merchantInfoPo);

		// 删除商户密码
		MerchantPwdPo merchantPwdPo = new MerchantPwdPo();
		merchantPwdPo.setMerchantId(merchantInfoDelDto.getMerchantId());
		merchantLoginMapper.del(merchantPwdPo);

		// 删除商户角色
		MerchantRolePo merchantRolePo = new MerchantRolePo();
		merchantRolePo.setMerchantId(merchantInfoDelDto.getMerchantId());
		merchantLoginMapper.delRole(merchantRolePo);

	}

	public Map<String, String> upd(MerchantInfoUpdBo merchantInfoUpdBo) {

		MerchantInfoPo merchantInfoPo = new MerchantInfoPo();
		merchantInfoPo.setMerchantId(merchantInfoUpdBo.getMerchantId());
		merchantInfoPo.setMerchantLvl(merchantInfoUpdBo.getMerchantLvl());
		merchantInfoPo.setMerchantType(merchantInfoUpdBo.getMerchantType());
		merchantInfoPo.setMerchantAddr(merchantInfoUpdBo.getMerchantAddr());
		merchantInfoPo.setMerchantTel(merchantInfoUpdBo.getMerchantTel());
		merchantInfoPo.setLegalPerson(merchantInfoUpdBo.getLegalPerson());
		merchantInfoPo.setEstalDate(merchantInfoUpdBo.getEstalDate());
		merchantInfoPo.setStatus(MerchantStatus.UP.getCode());
		merchantInfoPo.setEnterDate(DateUtil.getCurDate());
		merchantInfoPo.setEnterTime(DateUtil.getCurDateTime());
		merchantInfoPo.setUpdateDate(DateUtil.getCurDate());
		merchantInfoPo.setUpdateTime(DateUtil.getCurDateTime());
		merchantInfoMapper.upd(merchantInfoPo);

		Map<String, String> resMap = detailQryByMerchantId(merchantInfoPo.getMerchantId());

		return resMap;
	}

	public MerchantInfoVo detailQry(String merchantId) {
		MerchantInfoVo merchantInfoVo = new MerchantInfoVo();

		MerchantInfoPo merchantInfoPo = merchantInfoMapper.detailQry(merchantId);
		if (merchantInfoPo != null) {
			BeanUtils.copyProperties(merchantInfoPo, merchantInfoVo);
		}

		merchantInfoVo.setStatusDesc(MerchantStatus.getMerStatusByCode(merchantInfoVo.getStatus()).getDesc());
		merchantInfoVo.setMerchantTypeDesc(MerchantType.getMerTypeByCode(merchantInfoVo.getMerchantType()).getDesc());

		return merchantInfoVo;
	}

	public List<MerchantInfoVo> listQry(MerchantInfoQryDto merchantInfoDto) {
		List<MerchantInfoPo> listPo = merchantInfoMapper.listQry(merchantInfoDto);
		if (listPo == null || listPo.size() == 0) {
			return new ArrayList<MerchantInfoVo>();
		}

		List<MerchantInfoVo> listVo = new ArrayList<MerchantInfoVo>();
		for (MerchantInfoPo po : listPo) {
			MerchantInfoVo vo = new MerchantInfoVo();
			BeanUtils.copyProperties(po, vo);
			vo.setStatusDesc(MerchantStatus.getMerStatusByCode(vo.getStatus()).getDesc());
			listVo.add(vo);
		}

		return listVo;
	}

	@Override
	public Map<String, String> detailQryByMerchantId(String merchantId) {

		Map<String, String> resMap = merchantInfoMapper.detailQryByMerchantId(merchantId);

		if (CollectionUtil.isEmpty(resMap)) {
			return new HashMap<String, String>();
		}

		resMap.put("statusDesc", StringUtils.isEmpty(resMap.get("status")) ? ""
				: MerchantStatus.getMerStatusByCode(resMap.get("status")).getDesc());
		resMap.put("merchantTypeDesc", StringUtils.isEmpty(resMap.get("merchantType")) ? ""
				: MerchantType.getMerTypeByCode(resMap.get("merchantType")).getDesc());

		return resMap;
	}


}
