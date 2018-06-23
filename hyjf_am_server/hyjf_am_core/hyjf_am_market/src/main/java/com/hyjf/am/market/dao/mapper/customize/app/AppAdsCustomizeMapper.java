
package com.hyjf.am.market.dao.mapper.customize.app;

import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;

import java.util.List;
import java.util.Map;

public interface AppAdsCustomizeMapper {

	/**
	 * 
	 * @param ads
	 * @return
	 */
	List<AppAdsCustomize> selectAdsList(Map<String, Object> ads);

}
