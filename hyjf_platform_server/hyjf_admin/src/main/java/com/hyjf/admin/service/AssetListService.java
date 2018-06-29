package com.hyjf.admin.service;

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
 * @version AssetListService, v0.1 2018/6/27 15:34
 */
public interface AssetListService {
	// 资金来源
	List<HjhInstConfigVO>  hjhInstConfigList();
	// 产品类型
	List<HjhAssetTypeVO> hjhAssetTypeList(String instCodeSrch);
	// param原相关查询
	Map<String, String> getParamNameMap(String param);
	// 资产列表查询
	List<AssetListCustomizeVO> findAssetList(AssetListRequest request);
	// 详情查询
	AssetDetailCustomizeVO getDetailById(AssetListRequest request);
	// 总数
	Integer getRecordCount(AssetListRequest request);
	// 列总计
	BigDecimal sumAccount(AssetListRequest request);
}

