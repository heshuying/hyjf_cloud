package com.hyjf.admin.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * @author libin
 * @version AssetListService, v0.1 2018/6/27 15:34
 */
public interface AssetListService {
	
	/**
	 * 获取资金来源
	 *
	 * @param 
	 * @return List<HjhInstConfigVO>
	 */
	List<HjhInstConfigVO>  getHjhInstConfigList();
	
	/**
	 * 产品类型下拉联动
	 *
	 * @param instCodeSrch
	 * @return List<HjhAssetTypeVO>
	 */
	List<HjhAssetTypeVO> hjhAssetTypeList(String instCodeSrch);
	
	/**
	 * 资产列表查询
	 *
	 * @param form
	 * @return List<HjhAssetTypeVO>
	 */
	AssetListCustomizeResponse findAssetList(AssetListRequest form);
	
	/**
	 * param原相关查询
	 *
	 * @param param
	 * @return List<HjhAssetTypeVO>
	 */
	Map<String, String> getParamNameMap(String param);

	/**
	 * 查询详情
	 *
	 * @param request
	 * @return 查询详情
	 */
	AssetDetailCustomizeVO getDetailById(AssetListRequest request);
	
	/**
	 * 查询记录总数
	 *
	 * @param request
	 * @return 查询详情
	 */
	Integer getRecordCount(AssetListRequest request);
	
	/**
	 * 列总计查询
	 *
	 * @param request
	 * @return 
	 */
	BigDecimal sumAccount(AssetListRequest request);
}
