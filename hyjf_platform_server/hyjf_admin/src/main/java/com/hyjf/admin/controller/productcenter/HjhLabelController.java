/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
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
		// 3.项目类型(可复用)
		List<BorrowProjectTypeVO> borrowProjectTypeList = this.labelService.getBorrowProjectTypeList();
		jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
		// 4.还款方式(可复用)
		List<BorrowStyleVO> borrowStyleList = this.labelService.getBorrowStyleList();
		jsonObject.put("borrowStyleList", borrowStyleList);
		return jsonObject;
	}
	
	/**
	 * 下拉联动
	 *
	 * @param request
	 * @return 进入资产列表页面
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表下拉联动")
	@PostMapping(value = "/link")
	@ResponseBody
	public JSONObject assetTypeAction(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(StringUtils.isNotEmpty(map.get("instCodeSrch").toString())){
			// 资金来源动态下拉框传入机构编号再查产品类型表
			List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(map.get("instCodeSrch").toString());
			if (assetTypeList != null && assetTypeList.size() > 0) {
				for (HjhAssetTypeVO hjhAssetTypeVO : assetTypeList) {
					Map<String, Object> mapTemp = new HashMap<String, Object>();
					mapTemp.put("id", hjhAssetTypeVO.getAssetType());
					mapTemp.put("text", hjhAssetTypeVO.getAssetTypeName());
					resultList.add(mapTemp);
				}
			}
			jsonObject.put("assetTypeList", assetTypeList);
			return jsonObject;
		} else {
			jsonObject.put("未传入机构编号", "未传入机构编号");
			return jsonObject;
		}
	}
	
	/**
	 * 标签配置列表查询
	 *
	 * @param request
	 * @return 进入标签配置列表页面
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表查询")
	@PostMapping(value = "/search")
	@ResponseBody
	public JSONObject selectLabelConfigList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelRequest hjhLabelRequest = setRequese(map);
		List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelList(hjhLabelRequest);
        if (null != list && list.size() > 0) {
            jsonObject.put("record", list);
            success();
        } else {
        	fail("标签配置列表查询为空！");
        }
        return jsonObject;
	}
	
	/**
	 * 拼裝參數
	 *
	 * @param request
	 * @return 进入标签配置列表页面
	 */
    private HjhLabelRequest setRequese(Map<String, Object> mapParam) {
    	HjhLabelRequest request = new HjhLabelRequest();
        if (null != mapParam && mapParam.size() > 0) {
        	if (mapParam.containsKey("labelNameSrch")) {
        		request.setLabelNameSrch(mapParam.get("labelNameSrch").toString());
        	}
        	if (mapParam.containsKey("instCodeSrch")) {
        		request.setInstCodeSrch(mapParam.get("instCodeSrch").toString());
        	}
        	if (mapParam.containsKey("assetTypeSrch")) {
        		request.setAssetTypeSrch(mapParam.get("assetTypeSrch").toString());
        	}
        	if (mapParam.containsKey("projectTypeSrch")) {
        		request.setProjectTypeSrch(mapParam.get("projectTypeSrch").toString());
        	}
        	if (mapParam.containsKey("borrowStyleSrch")) {
        		request.setBorrowStyleSrch(mapParam.get("borrowStyleSrch").toString());
        	}
        	if (mapParam.containsKey("labelStateSrch")) {
        		request.setLabelStateSrch(mapParam.get("labelStateSrch").toString());
        	}
        	if (mapParam.containsKey("engineIdSrch")) {
        		request.setEngineIdSrch(mapParam.get("engineIdSrch").toString());
        	}
        	if (mapParam.containsKey("createTimeStartSrch")) {
        		request.setCreateTimeStartSrch(mapParam.get("createTimeStartSrch").toString());
        	}
        	if (mapParam.containsKey("createTimeEndSrch")) {
        		request.setCreateTimeEndSrch(mapParam.get("createTimeEndSrch").toString());
        	}
            if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
            	request.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return request;
    }
	
	
	
	
}
