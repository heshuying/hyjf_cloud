package com.hyjf.am.trade.dao.mapper.customize.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;

/**
 * @author libin
 * @version AssetListServiceImpl, v0.1 2018/6/13 17:23
 */
public interface AssetListServiceCustomizeMapper {
	
	/**
	 * 查询资产列表
	 * @author libin
	 * @param callCenterAccountDetailRequest
	 * @return
	 */
	List<AssetListCustomizeVO> queryAssetList(AssetListRequest assetListRequest);
	
	/**
	 * 查询资产详情
	 * @author libin
	 * @param callCenterAccountDetailRequest
	 * @return
	 */
	AssetDetailCustomizeVO selectAssetDetail(@Param("assetId") String assetId, @Param("instCode") String instCode);
}
