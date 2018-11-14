package com.hyjf.am.trade.service.api.asset;

import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO;

/**
*第三方资产状态查询接口
* @author Zha Daojian
* @date 2018/11/13 18:03
* @param
* @return
**/
public interface AssetListService {
	/**
	 * 查询资产状态
	 * @author Zha Daojian
	 * @date 2018/8/27 10:27
	 * @param request
	 **/
	ApiAssetStatusCustomizeVO selectAssetStatusById(AsseStatusRequest request);

}


