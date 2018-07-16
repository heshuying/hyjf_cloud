package com.hyjf.admin.client.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.AssetListClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AssetDetailCustomizeResponse;
import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.response.admin.HjhAssetTypeResponse;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;

/**
 * @author libin
 * @version AssetListClientImpl
 */
@Service
public class AssetListClientImpl implements AssetListClient {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 获取资金来源
	 *
	 * @param 
	 * @return List<HjhInstConfigVO>
	 */
	@Override
	public List<HjhInstConfigVO> findHjhInstConfigList() {
		HjhInstConfigResponse response = restTemplate.
				getForEntity("http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll", HjhInstConfigResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}
	
	/**
	 * 产品类型下拉联动
	 *
	 * @param instCodeSrch
	 * @return List<HjhAssetTypeVO>
	 */
	@Override
	public List<HjhAssetTypeVO> findHjhAssetTypeList(String instCodeSrch) {
		HjhAssetTypeResponse response = restTemplate.
				getForEntity("http://AM-TRADE/am-trade/hjhAssetType/selectAssetTypeAll/"+ instCodeSrch, HjhAssetTypeResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

    /**
     * 查询ParamName
     * @param request
     * @return
     */
	@Override
	public Map<String, String> findParamNameMap(String param) {
		Map<String,String> resultMap = CacheUtil.getParamNameMap(param);
		return resultMap;
	}

    /**
     * 查询资产列表
     * @param request
     * @return
     */
	@Override
	public AssetListCustomizeResponse findAssetList(AssetListRequest request) {
		AssetListCustomizeResponse response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/assetList/findAssetList" ,request,
						AssetListCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response;
		}
		return null;
	}
	
	/**
	 * 查询详情
	 *
	 * @param request
	 * @return 查询详情
	 */
	@Override
	public AssetDetailCustomizeVO findDetailById(AssetListRequest assetListRequest) {
		AssetDetailCustomizeResponse response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/assetList/findDetailById",assetListRequest,
						AssetDetailCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
	
	/**
	 * 查询记录总数
	 *
	 * @param request
	 * @return 查询详情
	 */
	@Override
	public Integer getRecordCount(AssetListRequest request) {
		AssetListCustomizeResponse response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/assetList/findRecordCount" ,request,
						AssetListCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getCount();
		}
		return null;
	}

	/**
	 * 查询列总计
	 *
	 * @param request
	 * @return 查询详情
	 */
	@Override
	public BigDecimal sumAccount(AssetListRequest request) {
		AssetListCustomizeResponse response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/assetList/sumAccount" ,request,
						AssetListCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getSum();
		}
		return null;
	}

	@Override
	public void updateCashDepositeStatus(String assetId, String menuHide) {
		String url = "http://AM-TRADE/am-trade/assetList/updateCashDepositeStatus/"+assetId+"/"+menuHide;
		restTemplate.getForEntity(url, String.class).getBody();
		
	}
}
