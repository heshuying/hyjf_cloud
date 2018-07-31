/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.mapper.customize.market;

import com.hyjf.am.market.dao.model.customize.app.ActivityListCustomize;

import java.util.List;

/**
 * @author yaoyong
 * @version AppActivityListCustomizeMapper, v0.1 2018/7/24 17:24
 */
public interface ActivityListCustomizeMapper {
    List<ActivityListCustomize> queryActivityListValid(ActivityListCustomize example);
}
