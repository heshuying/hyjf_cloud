package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.trade.dao.model.auto.HjhAssetType;

import java.util.List;

/**
 * @author libin
 * @version HjhAssetTypeService, v0.1 2018/4/25 10:40
 */
public interface HjhAssetTypeService {
	
	List<HjhAssetType> selectAssetTypeAll(String instCodeSrch);

}
