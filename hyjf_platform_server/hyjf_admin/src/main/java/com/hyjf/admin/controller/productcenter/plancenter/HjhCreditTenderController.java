/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.HjhCreditTenderService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version HjhCreditTenderController.java, v0.1 2018年7月11日 下午2:18:37
 */
@Api(value = "汇计划承接记录列表")
@RestController
@RequestMapping("/hyjf-admin/hjhcredittender")
public class HjhCreditTenderController extends BaseController{
	
	@Autowired
	private HjhCreditTenderService hjhCreditTenderService;
	@Autowired
	private SystemConfig systemConfig;
    @Autowired
    private AdminCommonService adminCommonService;
	
    /** 权限 */
	public static final String PERMISSIONS = "hjhcredittender";
	private static final String PARAM_NAME = "hyjf_param_name:";
	
	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 汇计划承接记录列表   已测试
	 */
	@ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, @RequestBody @Valid HjhCreditTenderRequest form) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
        // 还款方式
        List<BorrowStyleVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        jsonObject.put("borrowStyleList", borrowStyleList);
        // 承接方式
        Map<String, String> clientList = adminCommonService.getParamNameMap(PARAM_NAME + "PLAN_ASSIGN_TYPE");
        jsonObject.put("clientList", clientList);
		return jsonObject;
	}
	
    /**
     * 汇计划承接记录列表查询      已测试
     *
     * @param request
     * @return 汇计划承接记录列表查询       
     */
    @ApiOperation(value = "汇计划承接记录列表", notes = "汇计划承接记录列表查询")
    @PostMapping(value = "/searchAction")
    @ResponseBody
    /*@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW) */  
    public AdminResult<ListResult<HjhCreditTenderCustomizeVO>> search(HttpServletRequest request, @RequestBody @Valid HjhCreditTenderRequest form) {
    	//是否从加入明细列表跳转 1:是 0:否
		if(form.getIsAccedelist()!=1){
		    form.setIsAccedelist(0);
		}
    	// 根据删选条件获取计划列表
    	HjhCreditTenderResponse response = this.hjhCreditTenderService.getHjhCreditTenderListByParam(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<ListResult<HjhCreditTenderCustomizeVO>>(ListResult.build(response.getResultList(), response.getCount())) ;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
