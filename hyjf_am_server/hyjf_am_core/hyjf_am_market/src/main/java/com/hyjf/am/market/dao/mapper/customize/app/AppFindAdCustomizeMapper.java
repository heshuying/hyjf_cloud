
package com.hyjf.am.market.dao.mapper.customize.app;

import com.hyjf.am.market.dao.model.customize.app.AppFindAdCustomize;
import com.hyjf.am.resquest.market.AdsRequest;

import java.util.List;

public interface AppFindAdCustomizeMapper {

	List<AppFindAdCustomize> selectAppFindAdList(AdsRequest request);
}
