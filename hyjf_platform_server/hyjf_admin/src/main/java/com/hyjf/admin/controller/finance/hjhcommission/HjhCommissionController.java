/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.hjhcommission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.HjhCommissionViewRequest;
import com.hyjf.admin.beans.vo.HjhCommissionVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.HjhCommissionService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
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
	 * 列表查询(初始无参/查询带参 共用)
	 *
	 * @param request
	 * @return 进入汇计划提成列表
	 */
	@ApiOperation(value = "汇计划提成列表", notes = "汇计划提成列表查询")
	@PostMapping(value = "/search")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
	public AdminResult<ListResult<HjhCommissionCustomizeVO>> init(HttpServletRequest request, @RequestBody HjhCommissionViewRequest viewRequest) { 
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
}
