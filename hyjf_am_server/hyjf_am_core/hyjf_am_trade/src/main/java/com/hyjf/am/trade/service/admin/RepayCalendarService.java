/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.vo.market.AppReapyCalendarResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version RepayCalendarService, v0.1 2018/7/27 14:53
 */
public interface RepayCalendarService {

    Integer countRepaymentCalendar(Map<String, Object> params);

    List<AppReapyCalendarResultVO> searchRepaymentCalendar(Map<String, Object> params);

    Integer searchNearlyRepaymentTime(Map<String, Object> params);
}
