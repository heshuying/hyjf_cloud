package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version AssetListServiceImpl, v0.1 2018/6/27
 */
@Service
public class AssetListServiceImpl implements AssetListService {

	@Autowired
	public AmTradeClient amTradeClient;

	/**
	 * 获取资金来源
	 * @param 
	 * @return List<HjhInstConfigVO>
	 */
	@Override
	public List<HjhInstConfigVO> getHjhInstConfigList() {
		List<HjhInstConfigVO> hjhInstConfigList = amTradeClient.findHjhInstConfigList();
		return hjhInstConfigList;
	}

	/**
	 * 产品类型下拉联动
	 *
	 * @param instCodeSrch
	 * @return List<HjhAssetTypeVO>
	 */
	@Override
	public List<HjhAssetTypeVO> hjhAssetTypeList(String instCodeSrch) {
		List<HjhAssetTypeVO> hjhAssetTypeList = amTradeClient.findHjhAssetTypeList(instCodeSrch);
		return hjhAssetTypeList;
	}
	
	/**
	 * param原相关查询
	 *
	 * @return List<HjhAssetTypeVO>
	 */
	@Override
	public Map<String, String> getParamNameMap(String param) {
		Map<String, String> paramMap = amTradeClient.findParamNameMap(param);
		return paramMap;
	}

	/**
	 * 资产列表查询
	 *
	 * @return List<HjhAssetTypeVO>
	 */
	@Override
	public AssetListCustomizeResponse findAssetList(AssetListRequest request) {
		AssetListCustomizeResponse response = amTradeClient.findAssetList(request);
		return response;
	}
	
	/**
	 * 查询详情
	 *
	 * @return 查询详情
	 */
	@Override
	public AssetDetailCustomizeVO getDetailById(AssetListRequest assetListRequest) {
		AssetDetailCustomizeVO assetDetailCustomizeVO = amTradeClient.findDetailById(assetListRequest);
		return assetDetailCustomizeVO;
	}

	/**
	 * 查询记录总数
	 *
	 * @param request
	 * @return 查询详情
	 */
	@Override
	public Integer getRecordCount(AssetListRequest request) {
		Integer count = amTradeClient.getRecordCount(request);
		return count;
	}

	/**
	 * 列总计查询
	 *
	 * @param request
	 * @return 
	 */
	@Override
	public BigDecimal sumAccount(AssetListRequest request) {
		BigDecimal sum = amTradeClient.sumAccount(request);
		return sum;
	}

	/**
	 * 资产列表查询不分页
	 *
	 * @return List<HjhAssetTypeVO>
	 */
	@Override
	public AssetListCustomizeResponse findAssetListWithoutPage(AssetListRequest request) {
		AssetListCustomizeResponse response = amTradeClient.findAssetListWithoutPage(request);
		return response;
	}
}
