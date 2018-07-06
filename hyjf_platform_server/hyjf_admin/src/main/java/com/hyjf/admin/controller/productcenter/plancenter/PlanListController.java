/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hyjf.am.response.Response;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanListService;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version PlanListController.java, v0.1 2018年7月6日 上午9:08:43
 */
@Api(value = "计划列表")
@RestController
@RequestMapping("/hyjf-admin/hjhplan")
public class PlanListController extends BaseController{
	
	@Autowired
	private PlanListService planListService;
    /** 权限 */
	public static final String PERMISSIONS = "planlist";
	
    /**
     * 画面初始化
     *
     * @param request
     * @return 标的分配规则引擎列表
     */
    @ApiOperation(value = "计划列表", notes = "计划列表初始化")
    @PostMapping(value = "/search")
    @ResponseBody
    /*@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)*/
    public AdminResult<ListResult<HjhPlanVO>> search(HttpServletRequest request, @RequestBody @Valid PlanListRequest form) {
    	// 画面检索条件无需初始化 还款方式 endday 和  end
    	// 根据删选条件获取计划列表
    	HjhPlanResponse response = this.planListService.getHjhPlanListByParam(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
    	return new AdminResult<ListResult<HjhPlanVO>>(ListResult.build(response.getResultList(), response.getCount())) ;
    }
    
	/**
	 * 计划列表开放额度/累积加入/待还总额 累计
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "计划列表", notes = "计划列表开放额度/累积加入/待还总额累计")
	@PostMapping(value = "/sum")
	@ResponseBody
	public JSONObject getSumTotal(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid PlanListRequest form) {
		JSONObject jsonObject = new JSONObject();
		HjhPlanSumVO sumVO = this.planListService.getCalcSumByParam(form);
		jsonObject.put("sumWaitTotal", sumVO.getSumWaitTotal());
		jsonObject.put("sumOpenAccount", sumVO.getSumOpenAccount());
		jsonObject.put("sumJoinTotal", sumVO.getSumJoinTotal());
		return jsonObject;
	}
    
    
}
