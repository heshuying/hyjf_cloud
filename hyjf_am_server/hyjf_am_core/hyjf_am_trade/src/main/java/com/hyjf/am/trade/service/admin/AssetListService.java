package com.hyjf.am.trade.service.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;

/**
 * @author libin
 * @version AssetListService, v0.1 2018/4/25 10:40
 */
public interface AssetListService {
	List<AssetListCustomizeVO> findAssetList(Map<String, Object> mapParam,int limitStart, int limitEnd);
	
	AssetDetailCustomizeVO findDetailById(Map<String, Object> mapParam);
	
	Integer getRecordCount(AssetListRequest request);
	
	BigDecimal getSumAccount(AssetListRequest request);

	boolean updateCashDepositeStatus(String assetId, String menuHide);
}


