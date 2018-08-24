/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;
import java.util.Map;

import com.hyjf.am.user.dao.model.customize.VipDetailListCustomize;

/**
 * @author yaoyong
 * @version VipDetailListCustomizeMapper, v0.1 2018/7/3 14:41
 */
public interface VipDetailListCustomizeMapper {
    /**
     * 查询用户的VIP详情列表
     *
     * @param user
     * @return
     */
    List<VipDetailListCustomize> selectRecordList(Map<String, Object> user);

    /**
     * 查询用户的vip详情列表条数
     * @param user
     * @return
     */
    int countRecordTotal(Map<String, Object> user);
}
