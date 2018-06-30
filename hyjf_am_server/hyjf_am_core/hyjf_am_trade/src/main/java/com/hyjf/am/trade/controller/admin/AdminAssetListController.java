package com.hyjf.am.trade.controller.admin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.AssetDetailCustomizeResponse;
import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.trade.service.admin.AssetListService;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

import io.swagger.annotations.Api;

/**
 * @author libin
 * @version AssetListController
 */
@Api(value = "根据条件查询资产列表")
@RestController
@RequestMapping("/am-trade/assetList")
public class AdminAssetListController {
	
	@Autowired
	AssetListService assetListService;
	/**
	 * @Author: libin
	 * @Desc :根据条件查询资产列表
	 */
	@RequestMapping(value = "/findAssetList", method = RequestMethod.POST)
	public AssetListCustomizeResponse findAssetList(@RequestBody @Valid AssetListRequest request){
		Map<String, Object> mapParam = paramSet(request);
		AssetListCustomizeResponse response = new AssetListCustomizeResponse();
		Integer registCount = assetListService.getRecordCount(request);
        Paginator paginator = new Paginator(request.getPaginatorPage(), registCount,request.getLimit());
		String returnCode = "00";//代表成功
		List<AssetListCustomizeVO> assetList = assetListService.findAssetList(mapParam,paginator.getOffset(), paginator.getLimit());
		if(registCount>0){
			if(null!=assetList&&assetList.size()>0){
				List<AssetListCustomizeVO> assetListCustomizeVO = CommonUtils.convertBeanList(assetList,AssetListCustomizeVO.class);
	            response.setResultList(assetListCustomizeVO);
	            response.setCount(registCount);
				response.setRtn(returnCode);
			}
		}
		return response;
	}
	/**
	 * @Author: libin
	 * @Desc :根据条件查询详情
	 */
	@RequestMapping(value = "/findDetailById", method = RequestMethod.POST)
	public AssetDetailCustomizeResponse findDetailById(@RequestBody @Valid AssetListRequest request) {
		AssetDetailCustomizeResponse response = new AssetDetailCustomizeResponse();
		Map<String, Object> mapParam = paramSet(request);
		AssetDetailCustomizeVO assetDetailCustomizeVO = assetListService.findDetailById(mapParam);
		if (assetDetailCustomizeVO != null) {
			response.setResult(assetDetailCustomizeVO);
			return response;
		}
		return null;
	}
	
	/**
	 * @Author: libin
	 * @Desc :获取总数
	 */
	@RequestMapping(value = "/findRecordCount", method = RequestMethod.POST)
	public AssetListCustomizeResponse findRecordCount(@RequestBody @Valid AssetListRequest request) {
		AssetListCustomizeResponse response = new AssetListCustomizeResponse();
		Integer registCount = assetListService.getRecordCount(request);
		if(registCount>0){
			response.setCount(registCount);
		}
		return response;
	}
	
	/**
	 * @Author: libin
	 * @Desc :获取总数
	 */
	@RequestMapping(value = "/sumAccount", method = RequestMethod.POST)
	public AssetListCustomizeResponse sumAccount(@RequestBody @Valid AssetListRequest request) {
		AssetListCustomizeResponse response = new AssetListCustomizeResponse();
		BigDecimal sum = assetListService.getSumAccount(request);
		response.setSum(sum);
		return response;
	}
	
    /**
	 * 查询条件设置
	 *
	 * @param userRequest
	 * @return
	 */
	private Map<String, Object> paramSet(AssetListRequest request) {
	    Map<String, Object> mapParam = new HashMap<String, Object>();
	    mapParam.put("assetIdSrch", request.getAssetIdSrch());
	    mapParam.put("instCodeSrch", request.getInstCodeSrch());
	    mapParam.put("assetTypeSrch", request.getAssetTypeSrch());
	    mapParam.put("borrowNidSrch", request.getBorrowNidSrch());
	    mapParam.put("planNidSrch", request.getPlanNidSrch());
	    mapParam.put("userNameSrch", request.getUserNameSrch());
	    mapParam.put("labelNameSrch", request.getLabelNameSrch());
	    mapParam.put("bankOpenAccountSrch", request.getBankOpenAccountSrch());
	    mapParam.put("verifyStatusSrch", request.getVerifyStatusSrch());
	    mapParam.put("statusSrch", request.getStatusSrch());
	    mapParam.put("recieveTimeStartSrch", request.getRecieveTimeStartSrch());
	    mapParam.put("recieveTimeEndSrch", request.getRecieveTimeEndSrch());
	    return mapParam;
	}
}
