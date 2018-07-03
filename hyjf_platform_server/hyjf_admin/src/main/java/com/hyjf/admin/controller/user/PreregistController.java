/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.PreRegistRequestBean;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PreregistService;
import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;
import com.hyjf.am.vo.user.AdminPreRegistListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author DongZeShan
 * @version LoginController.java, v0.1 2018年6月15日 上午9:32:30
 */
@Api(value = "预注册用户")
@RestController
@RequestMapping("/preregist")
public class PreregistController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PreregistController.class);
	//权限名称
	private static final String PERMISSIONS = "preregist";
	@Autowired
	private PreregistService preregistService;

	/**
	 * @Author: dongzeshan
	 * @Desc :预注册用户页面载入
	 * @Param: * @param map
	 * @Date: 16:43 2018/6/15
	 * @Return: BaseResult<ListResult<AdminPreRegistListVO>>
	 */
	@ApiOperation(value = "预注册用户页面载入", notes = "预注册用户页面载入")
	@PostMapping(value = "/init")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public BaseResult<ListResult<AdminPreRegistListVO>> init(HttpServletRequest request,
			@RequestBody PreRegistRequestBean adminPreRegistListRequest) {
		 BaseResult<ListResult<AdminPreRegistListVO>> rs=new BaseResult<ListResult<AdminPreRegistListVO>>();
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		aprlr.setMobile(adminPreRegistListRequest.getMobile());
		aprlr.setReferrer(adminPreRegistListRequest.getReferrer());
		aprlr.setSource(adminPreRegistListRequest.getReferrer());
		aprlr.setRegistFlag(adminPreRegistListRequest.getRegistFlag());
		aprlr.setCurrPage(adminPreRegistListRequest.getCurrPage());
		aprlr.setPageSize(adminPreRegistListRequest.getPageSize());
		/*
		 * 也可以直接使用
		 * BeanUtils.copyProperties(adminPreRegistListRequest, aprlr);
		 */
		AdminPreRegistListResponse prs = preregistService.getRecordList(aprlr);
		
		if(prs==null) {
			rs.setStatus(FAIL);
			rs.setStatusDesc(FAIL_DESC);
			return rs;
		}
		if (prs.getRtn().equals("99")) {
			rs.setStatus(FAIL);
			rs.setStatusDesc(prs.getMessage());
			return rs;
		}
		ListResult<AdminPreRegistListVO> lrs=new ListResult<AdminPreRegistListVO>();
		lrs.setCount(prs.getRecordTotal());
		lrs.setList(prs.getResultList());
		rs.setData(lrs);
		rs.setStatus(SUCCESS);
		rs.setStatusDesc(SUCCESS_DESC);
		return rs;
	}

	/**
	 * @Author: dongzeshan
	 * @Desc :编辑页信息
	 * @Param: * @param map
	 * @Date: 16:43 2018/6/15
	 * @Return: JSONObject
	 */

	@ApiOperation(value = "编辑页信息", notes = "编辑页信息")
	@PostMapping(value = "/updatepreregistlist")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String")
	public BaseResult<AdminPreRegistListVO> updatepreregistlist(HttpServletRequest request, 
			@RequestBody String id) {
		BaseResult<AdminPreRegistListVO> result=new BaseResult<AdminPreRegistListVO>();
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		aprlr.setId(id);
		AdminPreRegistListResponse prs = preregistService.getPreRegist(aprlr);
		if(prs==null) {
			result.setStatus(FAIL);
			result.setStatusDesc(FAIL_DESC);
			return result;
		}
		if (prs!=null&&prs.getRtn().equals("99")) {
			result.setStatus(FAIL);
			result.setStatusDesc(prs.getMessage());
			return result;
		}
		result.setData(prs.getResult());
		result.setStatus(SUCCESS);
		result.setStatusDesc(SUCCESS_DESC);
		return result;
	}

	/**
	 * @Author: dongzeshan
	 * @Desc :获取权限列表
	 * @Param: *
	 * @Date: 16:43 2018/6/15
	 * @Return: JSONObject
	 */
	@ApiOperation(value = "编辑页保存", notes = "编辑页保存")
	@PostMapping(value = "/savepreregistlist")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
	public BaseResult getUserPermission(HttpServletRequest request,
			@RequestBody PreRegistRequestBean adminPreRegistListRequest) {
		BaseResult result=new BaseResult<>();
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		BeanUtils.copyProperties(adminPreRegistListRequest, aprlr);
		AdminPreRegistListResponse prs = preregistService.savePreRegist(aprlr);
		if(prs==null) {
			result.setStatus(FAIL);
			result.setStatusDesc(FAIL_DESC);
			return result;
		}
		if (prs.getRtn().equals("99")) {
			result.setStatus(FAIL);
			result.setStatusDesc(prs.getMessage());
			return result;
		}
		result.setStatus(SUCCESS);
		result.setStatusDesc(SUCCESS_DESC);
		return result;
	}

}
