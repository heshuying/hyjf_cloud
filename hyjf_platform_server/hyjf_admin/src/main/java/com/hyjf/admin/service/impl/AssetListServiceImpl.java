package com.hyjf.admin.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AssetListClient;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * @author libin
 * @version AssetListServiceImpl, v0.1 2018/6/27
 */
@Service
public class AssetListServiceImpl implements AssetListService {

	@Autowired
	public AssetListClient assetListClient;

	@Override
	public List<HjhInstConfigVO> getHjhInstConfigList() {
		List<HjhInstConfigVO> hjhInstConfigList = assetListClient.findHjhInstConfigList();
		return hjhInstConfigList;
	}

	@Override
	public List<HjhAssetTypeVO> hjhAssetTypeList(String instCodeSrch) {
		List<HjhAssetTypeVO> hjhAssetTypeList = assetListClient.findHjhAssetTypeList(instCodeSrch);
		return hjhAssetTypeList;
	}

	@Override
	public Map<String, String> getParamNameMap(String param) {
		Map<String, String> paramMap = assetListClient.findParamNameMap(param);
		return paramMap;
	}

	@Override
	public List<AssetListCustomizeVO> findAssetList(AssetListRequest request) {
		List<AssetListCustomizeVO> assetList = assetListClient.findAssetList(request);
		return assetList;
	}

	@Override
	public AssetDetailCustomizeVO getDetailById(AssetListRequest assetListRequest) {
		AssetDetailCustomizeVO assetDetailCustomizeVO = assetListClient.findDetailById(assetListRequest);
		return assetDetailCustomizeVO;
	}

	@Override
	public Integer getRecordCount(AssetListRequest request) {
		Integer count = assetListClient.getRecordCount(request);
		return count;
	}

	@Override
	public BigDecimal sumAccount(AssetListRequest request) {
		BigDecimal sum = assetListClient.sumAccount(request);
		return sum;
	}
}
