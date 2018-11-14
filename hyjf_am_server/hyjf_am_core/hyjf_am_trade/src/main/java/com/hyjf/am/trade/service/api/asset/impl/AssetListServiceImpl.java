package com.hyjf.am.trade.service.api.asset.impl;

import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.trade.service.api.asset.AssetListService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO;
import org.springframework.stereotype.Service;

/**
 * @author libin
 * @version AssetListServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class AssetListServiceImpl extends BaseServiceImpl implements AssetListService {

	/**
	 * 查询资产状态
	 * @author Zha Daojian
	 * @date 2018/8/27 10:27
	 * @param request
	 **/
	@Override
	public ApiAssetStatusCustomizeVO selectAssetStatusById(AsseStatusRequest request){
		return assetListServiceCustomizeMapper.selectAssetStatusById(request);
	}
}
