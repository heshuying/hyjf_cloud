package com.hyjf.am.trade.service.impl.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.AssetListServiceCustomizeMapper;
import com.hyjf.am.trade.service.admin.AssetListService;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;

/**
 * @author libin
 * @version AssetListServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class AssetListServiceImpl implements AssetListService{
	
    @Autowired
    private AssetListServiceCustomizeMapper assetListServiceCustomizeMapper;

	@Override
	public List<AssetListCustomizeVO> findAssetList(AssetListRequest request) {
		
		List<AssetListCustomizeVO> list = assetListServiceCustomizeMapper.queryAssetList(request);
		if(!CollectionUtils.isEmpty(list)){
	        Map<String, String> assetStatusMap = CacheUtil.getParamNameMap("ASSET_STATUS");
	        Map<String, String> assetApplyStatusMap = CacheUtil.getParamNameMap("ASSET_APPLY_STATUS");
	        Map<String, String> accountStatusMap = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
			for(AssetListCustomizeVO assetListCustomizeVO : list){
				assetListCustomizeVO.setStatus(assetStatusMap.getOrDefault(assetListCustomizeVO.getStatus(),null));
				assetListCustomizeVO.setVerifyStatus(assetApplyStatusMap.getOrDefault(assetListCustomizeVO.getVerifyStatus(),null));
				assetListCustomizeVO.setBankOpenAccount(accountStatusMap.getOrDefault(assetListCustomizeVO.getBankOpenAccount(),null));
			}
		}
		return list;
	}

	@Override
	public AssetDetailCustomizeVO findDetailById(String assetId, String instCode) {
		return assetListServiceCustomizeMapper.selectAssetDetail(assetId, instCode);
	}
}
