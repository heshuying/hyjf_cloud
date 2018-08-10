/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.dao.model.customize.trade.PushMoneyCustomize;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;

import java.util.List;
import java.util.Map;

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

    /**
     * 提成列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer queryPushMoneyDetailCount(PushMoneyRequest request);

    /**
     * 提成列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PushMoneyCustomize> queryPushMoneyDetail(PushMoneyRequest request);

    /**
     * 提成列表总额
     * @auth sunpeikai
     * @param
     * @return
     */
    Map<String,Object> queryPushMoneyTotle(PushMoneyRequest request);

    /**
     * 根据userid查询 crm  cuttype
     * @auth sunpeikai
     * @param
     * @return
     */
    int queryCrmCuttype(Integer userId);

    UserInfoCustomizeVO queryUserInfoByUserId(Integer userId);
}
