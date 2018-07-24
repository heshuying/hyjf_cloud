/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize.admin.finance;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.user.dao.model.customize.admin.finance.PushMoneyCustomize;

import java.util.List;

/**
 * @author zdj
 * @version PushMoneyCustomizeMapper, v0.1 2018/7/4 14:05
 */
public interface PushMoneyCustomizeMapper {

    /**
     * 提成管理 （列表数量）
     * @param request
     * @return
     */
    Integer queryPushMoneyCount(PushMoneyRequest request) ;

    /**
     * 提成管理 （列表）
     * @param request
     * @return
     */
    List<PushMoneyCustomize> queryPushMoneyList(PushMoneyRequest request) ;
}
