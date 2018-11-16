package com.hyjf.am.trade.controller.api.asset;

import com.hyjf.am.response.admin.AssetDetailCustomizeResponse;
import com.hyjf.am.response.api.ApiAssetStatusCustomizeResponse;
import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.api.asset.AssetListService;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 *
  第三方资产状态查询接口
 * @author Zha Daojian
 * @date 2018/11/13 18:05
 * @param
 * @return
 **/
@Api(value = "根据条件查询资产列表")
@RestController
@RequestMapping("/am-trade/assetList")
public class ApiAssetListController extends BaseController {
	
	@Autowired
	AssetListService assetListService;

	/**
	 * @Author: zdj
	 * @Desc :根据assetId,instCode查询资产状态
	 */
	@RequestMapping(value = "/selectAssetStatusById", method = RequestMethod.POST)
	public ApiAssetStatusCustomizeResponse selectAssetStatusById(@RequestBody @Valid AsseStatusRequest request) {
        ApiAssetStatusCustomizeResponse response = new ApiAssetStatusCustomizeResponse();
        ApiAssetStatusCustomizeVO assetStatusCustomizeVO = assetListService.selectAssetStatusById(request);
		if (assetStatusCustomizeVO != null) {
			response.setResult(assetStatusCustomizeVO);
			return response;
		}
		return null;
	}
}
