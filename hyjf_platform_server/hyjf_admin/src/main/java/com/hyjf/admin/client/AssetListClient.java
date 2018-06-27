package com.hyjf.admin.client;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.AssetListRequest;
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
    List<HjhInstConfigVO> findHjhInstConfigList(AssetListRequest request);
    
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
}
