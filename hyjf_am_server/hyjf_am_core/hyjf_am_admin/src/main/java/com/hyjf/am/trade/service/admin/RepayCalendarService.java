/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import java.util.List;
import java.util.Map;

import com.hyjf.am.vo.market.AppReapyCalendarResultVO;

/**
 * @author dangzw
 * @version RepayCalendarService, v0.1 2018/7/27 14:53
 */
public interface RepayCalendarService {

    /**
     * 查询回款日历总数
     * @param params
     * @return
     */
    Integer countRepaymentCalendar(Map<String, Object> params);

    /**
     * 查询回款日历明细
     * @param params
     * @return
     */
    List<AppReapyCalendarResultVO> searchRepaymentCalendar(Map<String, Object> params);

    /**
     * 返回用户最近回款时间戳-秒
     * @param params
     * @return
     */
    Integer searchNearlyRepaymentTime(Map<String, Object> params);
}
