package com.hyjf.am.trade.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.trade.service.admin.AssetListService;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;

/**
 * @author libin
 * @version AssetListController
 */
@Api(value = "根据条件查询资产列表")
@RestController
@RequestMapping("/am-trade/assetList")
public class AssetListController {
	
	@Autowired
	AssetListService assetListService;
	/**
	 * @Author: libin
	 * @Desc :根据条件查询资产列表
	 */
	@RequestMapping(value = "/findAssetList", method = RequestMethod.POST)
	public AssetListCustomizeResponse findAssetList(@RequestBody @Valid AssetListRequest request){
		AssetListCustomizeResponse response = new AssetListCustomizeResponse();
		String returnCode = "00";//代表成功
		List<AssetListCustomizeVO> assetList = assetListService.findAssetList(request);
		if(null!=assetList&&assetList.size()>0){
			List<AssetListCustomizeVO> assetListCustomizeVO = CommonUtils.convertBeanList(assetList,AssetListCustomizeVO.class);
            response.setResultList(assetListCustomizeVO);
			response.setRtn(returnCode);
		}
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :根据条件查询详情
	 */
	@RequestMapping("/findDetailById/{assetId}/{instCode}")
	public AssetDetailCustomizeVO findDetailById(@PathVariable String assetId,@PathVariable String instCode) {
		AssetDetailCustomizeVO assetDetailCustomizeVO = assetListService.findDetailById(assetId,instCode);
		if (assetDetailCustomizeVO != null) {
			return assetDetailCustomizeVO;
		}
		return null;
	}
}
