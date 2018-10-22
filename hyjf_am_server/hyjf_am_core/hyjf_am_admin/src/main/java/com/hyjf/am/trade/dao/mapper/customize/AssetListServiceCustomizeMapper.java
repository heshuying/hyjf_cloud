package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version AssetListServiceImpl, v0.1 2018/6/13 17:23
 */
public interface AssetListServiceCustomizeMapper {
	
	/**
	 * 查询资产列表
	 * @author libin
	 * @param mapParam
	 * @return
	 */
	List<AssetListCustomizeVO> queryAssetList(Map<String, Object> mapParam);
	
	/**
	 * 查询资产详情
	 * @author libin
	 * @param mapParam
	 * @return
	 */
	AssetDetailCustomizeVO selectAssetDetail(Map<String, Object> mapParam);
	
	/**
	 * COUNT
	 * 
	 * @param request
	 * @return
	 */
	Integer countAssetList(AssetListRequest request);
	
	/**
	 * SUM
	 *
	 * @param request
	 * @return
	 */
	BigDecimal getSumAccount(AssetListRequest request);

	/**
	 * 获取保证金不足列表
	 * @param mapParam
	 * @return
	 */
	List<AssetListCustomizeVO> findBZJBZList(Map<String,Object> mapParam);
}
