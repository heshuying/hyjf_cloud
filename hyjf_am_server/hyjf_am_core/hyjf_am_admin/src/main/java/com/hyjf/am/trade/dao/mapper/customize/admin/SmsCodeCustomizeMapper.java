/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.trade.dao.model.customize.SmsCodeCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version SmsCodeCustomizeMapper, v0.1 2018/8/20 20:33
 */
public interface SmsCodeCustomizeMapper {
    /**
     * 筛选用户
     * @param params
     * @return
     */
    List<SmsCodeCustomize> queryUser(Map<String, Object> params);

    /**
     * 查询符合条件用户数量
     * @param params
     * @return
     */
    int countUser(Map<String, Object> params);
}
