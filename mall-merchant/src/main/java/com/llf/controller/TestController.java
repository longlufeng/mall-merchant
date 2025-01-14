package com.llf.controller;

import java.util.HashMap;
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
@RequestMapping("/test")
public class TestController {
	
	private static Logger logger = LogManager.getLogger(TestController.class);
	
	public static void main(String[] args) {
		
		Map<String,Object> map = new HashMap<>();
		map.put("11", "22");
		map.put("12", "22");
		
		
		System.out.println(MapUtils.getBoolean(map, "11"));
		if(map == null && map.size() == 0) {
			System.out.println("为空");
		}else {
			System.out.println("不为空");
		}
		
	}
	
}
