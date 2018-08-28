/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.AdminSystemVO;

import io.swagger.annotations.Api;

/**
 * @author DongZeShan
 * @version BaseController.java, v0.1 2018年6月21日 下午7:26:10
 * admin 基础类
 */
@Api(value = "admin基类",tags ="admin基类")
@RestController
public class BaseController {
	public static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	//redis存有用户信息的key
	public static final String USER="user";
	public static final String STATUS="status";
	public static final String MSG="msg";
	public static final String SUCCESS="000";
	public static final String SUCCESS_DESC = "成功";
	public static final String FAIL="99";
	public static final String FAIL_DESC = "失败";
	//返回集合
	public static final String LIST="list";
	public static final String RECORD="record";

	public static final String CURR="currPage";
	public static final String PAGE="pageSize";
	public static final String TRCORD="recordTotal";
	
	
	//取得session中用户信息
	public AdminSystemVO getUser(HttpServletRequest request) {
		AdminSystemVO ar=null;
		ar=(AdminSystemVO) request.getSession().getAttribute(USER);
		return ar;
	}
	//存入session中用户信息
	public void setUser(HttpServletRequest request,AdminSystemVO prs) {
		request.getSession().setAttribute(USER, prs);
	}
	// 移除session
	public void removeUser(HttpServletRequest request) {
		request.getSession().removeAttribute(USER);
	}
	//返回成功
	public JSONObject success() {
		JSONObject info = new JSONObject();
		info.put(STATUS, SUCCESS);
		info.put(MSG, "成功");
		return info;
	}
	//返回成功带总数的成功
	public JSONObject success(String recordTotal) {
		JSONObject info = new JSONObject();
		info.put(STATUS, SUCCESS);
		info.put(MSG, "成功");
		info.put(TRCORD, recordTotal);
		return info;
	}
	//返回成功带总数带集合的
	public JSONObject success(String recordTotal,List<?> List) {
		JSONObject info = new JSONObject();
		info.put(STATUS, SUCCESS);
		info.put(MSG, "成功");
		info.put(TRCORD, recordTotal);
		info.put(LIST, List);
		return info;
	}
	//返回成功带总数带集合的,总数为int型 add by yangchangwei
	public JSONObject success(Integer recordTotal,List<?> List) {
		JSONObject info = new JSONObject();
		info.put(STATUS, SUCCESS);
		info.put(MSG, "成功");
		info.put(TRCORD, recordTotal);
		info.put(LIST, List);
		return info;
	}

	//返回失败
	public JSONObject  fail(String failmsg) {
		JSONObject info = new JSONObject();
		if("".equals(failmsg)) {
			info.put(MSG, "失败");
		}else {
			info.put(MSG, failmsg);
		}
		info.put(STATUS, FAIL);
		return info;
	}
	//获取页数
	public String  getCurrPage(Map<String, ?> map) {

		return String.valueOf(map.get(CURR)) ;
	}
	//获取条数
	public String  getPageSize(Map<String, ?> map) {

		return String.valueOf(map.get(PAGE)) ;
	}
}
