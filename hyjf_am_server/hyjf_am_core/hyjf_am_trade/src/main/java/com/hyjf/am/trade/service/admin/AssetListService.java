package com.hyjf.am.trade.service.admin;

import java.util.List;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;

/**
 * @author libin
 * @version AssetListService, v0.1 2018/4/25 10:40
 */
public interface AssetListService {
	List<AssetListCustomizeVO> findAssetList(AssetListRequest request);
	
	AssetDetailCustomizeVO findDetailById(String assetId, String instCode);
}


