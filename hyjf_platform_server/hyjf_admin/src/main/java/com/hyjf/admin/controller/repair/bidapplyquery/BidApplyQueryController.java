/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.repair.bidapplyquery;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BidApplyQueryBean;
import com.hyjf.admin.beans.request.BidApplyQueryViewRequest;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.exception.BidApplyQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author libin
 * @version BidApplyQueryController.java, v0.1 2018年8月21日 上午9:13:52
 * test: borrow tender 的 nid and bankopenaccount 的 account
 * start:pay server
 */
@Api(value = "异常中心-出借人投标申请查询",tags = "异常中心-出借人投标申请查询")
@RestController
@RequestMapping("/hyjf-admin/exception/bidapplyquery")
public class BidApplyQueryController extends BaseController{
	
	@Autowired
	private BidApplyQueryService bidApplyQueryService;
	
	private static final String PERMISSIONS = "bidapplyquery";
	
    @ApiOperation(value = "异常中心-出借人投标申请查询", notes = "异常中心-出借人投标申请查询")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject search(HttpServletRequest request, @RequestBody @Valid BidApplyQueryViewRequest viewRequest) {
    	// 初始化原子层请求实体
    	BidApplyQueryBean form = new BidApplyQueryBean();
    	// 将画面请求request赋值给原子层 request
    	BeanUtils.copyProperties(viewRequest, form);
    	JSONObject json = new JSONObject();
    	// 初始化前端直接做成
		//单笔资金类业务交易查询
		this.bidApplyQuerySearch(request, json, form);
    	return json;
    }
    
	/**
	 * 出借人投标申请查询      已测试
	 * @param request
	 * @param form
	 * @author libin
	 * @date 2018年8月16日 上午10:04:35
	 */
	private void bidApplyQuerySearch(HttpServletRequest request, JSONObject json, BidApplyQueryBean form) {
		JSONObject result = this.bidApplyQueryService.bidApplyQuerySearch(form);
		form.setResult(result);
		json.put("bidApplyQueryForm", form);
	}
}
