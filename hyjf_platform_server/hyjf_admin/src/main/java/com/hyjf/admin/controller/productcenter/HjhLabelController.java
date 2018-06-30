/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.HjhLabelService;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author libin
 * @version HjhLabelController.java, v0.1 2018年6月30日 上午9:14:22
 */
@Api(value = "标签配置列表")
@RestController
@RequestMapping("/hyjf-admin/label")
public class HjhLabelController extends BaseController{

	@Autowired
	private HjhLabelService labelService;
	@Autowired
	private AssetListService assetListService;
	

	
/*	// 开户状态
	private static final  String ACCOUNT_STATUS = "ACCOUNT_STATUS";
	// 审核状态
	private static final  String ASSET_APPLY_STATUS = "ASSET_APPLY_STATUS";
	// 项目状态
	private static final  String ASSET_STATUS = "ASSET_STATUS";*/
	
	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 标签配置列表
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
		// 1.资产来源(可复用)
		List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.getHjhInstConfigList();
		jsonObject.put("hjhInstConfigList", hjhInstConfigList);
		
		// 2.产品类型(可复用)
		/*List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(map.get("instCodeSrch").toString());*/
		/*jsonObject.put("assetTypeList", assetTypeList);*/
		
		// 3.标签状态 (停用/启用)
		
		// 4.项目类型(可复用)
		List<BorrowProjectTypeVO> borrowProjectTypeList = this.labelService.getBorrowProjectTypeList();
		jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
		
		// 5.还款方式(可复用)
		List<BorrowStyleVO> borrowStyleList = this.labelService.getBorrowStyleList();
		jsonObject.put("borrowStyleList", borrowStyleList);
		
		// 6.使用状态 (未使用/已使用)
		return jsonObject;
	}
}
