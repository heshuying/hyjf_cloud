package com.hyjf.admin.service;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * @author libin
 * @version AssetListService, v0.1 2018/6/27 15:34
 */
public interface AssetListService {
	// 资金来源
	List<HjhInstConfigVO>  hjhInstConfigList(AssetListRequest request);
	// 产品类型
	List<HjhAssetTypeVO> hjhAssetTypeList(String instCodeSrch);
	// param原相关查询
	Map<String, String> getParamNameMap(String param);
}
