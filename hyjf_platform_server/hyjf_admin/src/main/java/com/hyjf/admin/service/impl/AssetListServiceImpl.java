package com.hyjf.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AssetListClient;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.am.resquest.admin.AssetListRequest;
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
	public List<HjhInstConfigVO> hjhInstConfigList(AssetListRequest request) {
		List<HjhInstConfigVO> hjhInstConfigList = assetListClient.findHjhInstConfigList(request);
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

}
