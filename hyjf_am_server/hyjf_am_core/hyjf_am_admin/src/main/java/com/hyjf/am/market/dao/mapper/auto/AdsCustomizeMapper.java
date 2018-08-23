/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.customize.app.AppAdsCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version AdsCustomizeMapper, v0.1 2018/7/26 17:22
 */
public interface AdsCustomizeMapper {

    /**
     * 查询首页的bannner列表
     */
    List<AppAdsCustomize> selectAdsList(Map<String, Object> ads);
}
