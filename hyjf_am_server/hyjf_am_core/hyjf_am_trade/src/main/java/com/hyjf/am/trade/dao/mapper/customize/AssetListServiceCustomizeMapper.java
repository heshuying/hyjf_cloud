package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.vo.api.ApiAssetStatusCustomizeVO;

/**
 * 资产
 * @author Zha Daojian
 * @date 2018/11/13 18:06
 * @param
 * @return
 **/
public interface AssetListServiceCustomizeMapper {
	


	/**
	 * 查询资产状态
	 * @author Zha Daojian
	 * @date 2018/8/27 10:27
	 * @param request
	 **/
	ApiAssetStatusCustomizeVO selectAssetStatusById(AsseStatusRequest request);
}
