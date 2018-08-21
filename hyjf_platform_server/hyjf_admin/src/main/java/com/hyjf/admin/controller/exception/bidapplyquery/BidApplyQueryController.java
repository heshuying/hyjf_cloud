/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.bidapplyquery;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BidApplyQueryBean;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.exception.BidApplyQueryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version BidApplyQueryController.java, v0.1 2018年8月21日 上午9:13:52
 */
@Api(value = "异常中心-投资人投标申请查询",tags = "异常中心-投资人投标申请查询")
@RestController
@RequestMapping("/hyjf-admin/exception/bidapplyquery")
public class BidApplyQueryController extends BaseController{
	
	@Autowired
	private BidApplyQueryService bidApplyQueryService;
	
	private static final String PERMISSIONS = "bidapplyquery";
	
    @ApiOperation(value = "异常中心-投资人投标申请查询", notes = "异常中心-投资人投标申请查询")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject search(HttpServletRequest request, BidApplyQueryBean form) {
    	JSONObject json = new JSONObject();
    	// 初始化前端直接做成
		//单笔资金类业务交易查询
		this.bidApplyQuerySearch(request, json, form);
    	return json;
    }
    
	/**
	 * 投资人投标申请查询
	 * @param request
	 * @param modelAndView
	 * @param form
	 * @author libin
	 * @date 2018年8月16日 上午10:04:35
	 */
	private void bidApplyQuerySearch(HttpServletRequest request, JSONObject json, BidApplyQueryBean form) {
		/*JSONObject result = this.bidApplyQueryService.bidApplyQuerySearch(form);*/
		//TODO
		/*form.setResult(result);*/
		json.put("bidApplyQueryForm", form);
	}
}
