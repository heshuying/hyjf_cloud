package com.hyjf.admin.client.impl;

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
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
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
	 * 资产来源
	 * @param request
	 * @return
	 */
	@Override
	public List<HjhInstConfigVO> findHjhInstConfigList(AssetListRequest request) {
		// usercenter 中的方法复用
		HjhInstConfigResponse response = restTemplate.
				getForEntity("http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll", HjhInstConfigResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<HjhAssetTypeVO> findHjhAssetTypeList(String instCodeSrch) {
		HjhAssetTypeResponse response = restTemplate.
				getForEntity("http://AM-TRADE/am-trade/hjhAssetType/selectAssetTypeAll"+ instCodeSrch, HjhAssetTypeResponse.class).
				getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public Map<String, String> findParamNameMap(String param) {
		Map<String,String> resultMap = CacheUtil.getParamNameMap(param);
		return resultMap;
	}

	@Override
	public List<AssetListCustomizeVO> findAssetList(AssetListRequest request) {
		AssetListCustomizeResponse response = restTemplate
				.postForEntity("http://AM-TRADE/am-trade/assetList/findAssetList" ,request,
						AssetListCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public AssetDetailCustomizeVO findDetailById(String assetId, String instCode) {
		AssetDetailCustomizeResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/assetList/findDetailById/" + assetId + "/" + instCode,
						AssetDetailCustomizeResponse.class)
				.getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
}
