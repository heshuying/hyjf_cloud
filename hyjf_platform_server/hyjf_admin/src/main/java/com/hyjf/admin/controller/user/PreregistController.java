/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.user;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hyjf.admin.beans.request.PreRegistRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PreregistService;
import com.hyjf.am.response.Response;
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
	public AdminResult<ListResult<AdminPreRegistListVO>> init(HttpServletRequest request,
			@RequestBody PreRegistRequestBean adminPreRegistListRequest) {
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
//		aprlr.setMobile(adminPreRegistListRequest.getMobile());
//		aprlr.setReferrer(adminPreRegistListRequest.getReferrer());
//		aprlr.setSource(adminPreRegistListRequest.getReferrer());
//		aprlr.setRegistFlag(adminPreRegistListRequest.getRegistFlag());
//		aprlr.setCurrPage(adminPreRegistListRequest.getCurrPage());
//		aprlr.setPageSize(adminPreRegistListRequest.getPageSize());
		
		 //可以直接使用
		 BeanUtils.copyProperties(adminPreRegistListRequest, aprlr);
		 
		AdminPreRegistListResponse prs = preregistService.getRecordList(aprlr);
		
		if(prs==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<ListResult<AdminPreRegistListVO>>(ListResult.build(prs.getResultList(), prs.getRecordTotal())) ;
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
	public AdminResult<AdminPreRegistListVO> updatepreregistlist(HttpServletRequest request, 
			@RequestBody String id) {
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		aprlr.setId(id);
		AdminPreRegistListResponse prs = preregistService.getPreRegist(aprlr);
		if(prs==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<AdminPreRegistListVO>(prs.getResult());
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
	public AdminResult getUserPermission(HttpServletRequest request,
			@RequestBody PreRegistRequestBean adminPreRegistListRequest) {
		AdminPreRegistListRequest aprlr = new AdminPreRegistListRequest();
		BeanUtils.copyProperties(adminPreRegistListRequest, aprlr);
		AdminPreRegistListResponse prs = preregistService.savePreRegist(aprlr);
		if(prs==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

}
