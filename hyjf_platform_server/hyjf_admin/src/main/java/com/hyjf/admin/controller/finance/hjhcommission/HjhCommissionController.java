/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.hjhcommission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.HjhCommissionViewRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.HjhCommissionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author libin
 * @version HjhCommissionController.java, v0.1 2018年8月7日 下午2:38:45
 */
@Api(value = "汇计划提成列表",tags = "汇计划提成列表")
@RestController
@RequestMapping("/hyjf-admin/hjhcommission")
public class HjhCommissionController extends BaseController{
	
	// 原 private PushMoneyManageHjhService pushMoneyService;
    @Autowired
    private HjhCommissionService hjhCommissionService;

    //@Autowired
    //private ReturncashService returncashService;
    
	// 权限
	public static final String PERMISSIONS = "hjhcommission";
	
	/**
	 * 列表查询(初始无参/查询带参 共用)    已测试
	 *
	 * @param request
	 * @return 进入汇计划提成列表
	 */
	@ApiOperation(value = "汇计划提成列表", notes = "汇计划提成列表查询")
	@PostMapping(value = "/search")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
	public AdminResult<ListResult<HjhCommissionCustomizeVO>> init(HttpServletRequest request, @RequestBody @Valid HjhCommissionViewRequest viewRequest) { 
		// 初始化原子层请求实体
		HjhCommissionRequest form = new HjhCommissionRequest();
		// 初始化返回LIST
		List<HjhCommissionCustomizeVO> returnList = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 默认为汇计划类的投资
		form.setTenderType(2);
		// 列表查询
		HjhCommissionResponse response = hjhCommissionService.selectHjhCommissionList(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			returnList = CommonUtils.convertBeanList(response.getResultList(), HjhCommissionCustomizeVO.class);
			return new AdminResult<ListResult<HjhCommissionCustomizeVO>>(ListResult.build(returnList, response.getCount()));
		} else {
			return new AdminResult<ListResult<HjhCommissionCustomizeVO>>(ListResult.build(returnList, 0));
		}
	}
	
	/**
	 * 汇计划提成列表 加入金额/提成金额 累计             已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "汇计划提成列表", notes = "汇计划提成列表 加入金额/提成金额 累计")
	@PostMapping(value = "/sum")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject getSumTotal(HttpServletRequest request, @RequestBody @Valid HjhCommissionViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 初始化原子层请求实体
		HjhCommissionRequest form = new HjhCommissionRequest();
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 默认为汇计划类的投资
		form.setTenderType(2);
		HjhCommissionResponse response = hjhCommissionService.selecthjhCommissionTotal(form);
		if(response == null) {
			jsonObject.put("error", FAIL);
		}
		if (!Response.isSuccess(response)) {
			jsonObject.put("error", FAIL);
		}
		jsonObject.put("totalMap", response.getTotalMap());
		jsonObject.put("status", SUCCESS);
		return jsonObject;
	}
	
    /**
     * 汇计划提成列表-校验发提成状态是不是已经发放   已测试
     *
     * @param request
     * @param form
     * @return
     */
	@ApiOperation(value = "汇计划提成列表", notes = "汇计划提成列表-校验发提成状态是不是已经发放")
	@PostMapping(value = "/checkstatus")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject checkStatusAction(HttpServletRequest request , @RequestBody @Valid HjhCommissionViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		int ids = 0;
		int status = 0;
		if(viewRequest.getIds() == 0){
			// 前端未传
			jsonObject.put("error", "请传入ids!");
		} else {
			ids = viewRequest.getIds();
		}
		TenderCommissionVO tenderCommission = this.hjhCommissionService.queryTenderCommissionByPrimaryKey(ids);
		if(tenderCommission != null){
			// 0:未发放;1:已发放;100:删除
			status = tenderCommission.getStatus();
			jsonObject.put("msg", "state : 0:未发放;1:已发放;100:删除");
			jsonObject.put("state", status);
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("error", "未查询到该记录！");
			jsonObject.put("status", FAIL);
		}
		return jsonObject;
	}
	
}
