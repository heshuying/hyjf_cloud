package com.hyjf.admin.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * @author libin
 * @version AssetListClient
 */
public interface AssetListClient {
    /**
     * 资产来源
     * @param request
     * @return
     */
    List<HjhInstConfigVO> findHjhInstConfigList();

    /**
     * 产品类型
     * @param request
     * @return
     */
    List<HjhAssetTypeVO> findHjhAssetTypeList(String instCodeSrch);

    /**
     * param
     * @param request
     * @return
     */
    Map<String, String> findParamNameMap(String param);

    /**
     * 资产列表
     * @param request
     * @return
     */
    List<AssetListCustomizeVO> findAssetList(AssetListRequest request);

    /**
     * 详情查询
     * @param request
     * @return
     */
    AssetDetailCustomizeVO findDetailById(AssetListRequest assetListRequest);
    
    /**
     * 总计
     * @param request
     * @return
     */
    Integer getRecordCount(AssetListRequest request);
    
    /**列总数
     * @param request
     * @return
     */
    BigDecimal sumAccount(AssetListRequest request);

    /**
     * 处理保证金不足的资产
     * @param assetId
     * @param menuHide
     */
	void updateCashDepositeStatus(String assetId, String menuHide);
}
