package com.hyjf.admin.controller.assetcenter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.alibaba.fastjson.JSONObject;
/**
 * @author libin
 * @version AssetListController, v0.1 2018/6/27 15:16
 */
@Api(value = "资产列表")
@RestController
@RequestMapping("/hyjf-admin/assetcenter")
public class AssetListController extends BaseController {
	
	@Autowired
	private AssetListService assetListService;
	@Autowired 
	AssetListRequest request;
	// 开户状态
	private static final  String ACCOUNT_STATUS = "ACCOUNT_STATUS";
	// 审核状态
	private static final  String ASSET_APPLY_STATUS = "ASSET_APPLY_STATUS";
	// 项目状态
	private static final  String ASSET_STATUS = "ASSET_STATUS";
	
    @ApiOperation(value = "资产列表", notes = "资产列表页面初始化")
    @PostMapping(value = "/init")
    @ResponseBody
    public JSONObject init(HttpServletRequest request, AssetListRequest form) {
        JSONObject jsonObject = new JSONObject();
        // 初始化下拉菜单
        // 1.资产来源(可复用)
		List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.hjhInstConfigList(form);
		jsonObject.put("hjhInstConfigList", hjhInstConfigList);
		// 2.产品类型(可复用)
		List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(form.getInstCodeSrch());
		jsonObject.put("assetTypeList", assetTypeList);
        // 3.开户状态
		Map<String, String> accountStatusMap = this.assetListService.getParamNameMap(ACCOUNT_STATUS);
		jsonObject.put("accountStatusMap", accountStatusMap);
		// 4.审核状态
		Map<String, String> assetApplyStatusMap = this.assetListService.getParamNameMap(ASSET_APPLY_STATUS);
		jsonObject.put("assetApplyStatusMap", assetApplyStatusMap);
		// 5.项目状态
		Map<String, String> assetStatusMap = this.assetListService.getParamNameMap(ASSET_STATUS);
		jsonObject.put("assetStatusMap", assetStatusMap);
        return jsonObject;
    }
    
}
