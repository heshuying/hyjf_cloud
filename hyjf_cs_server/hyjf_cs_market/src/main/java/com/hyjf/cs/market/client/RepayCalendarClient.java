/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.vo.market.AppReapyCalendarResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version RepayCalendarClient, v0.1 2018/7/27 11:51
 */
public interface RepayCalendarClient {

    Integer countRepaymentCalendar(Map<String, Object> params);

    List<AppReapyCalendarResultVO> searchRepaymentCalendar(Map<String, Object> params);

    Integer searchNearlyRepaymentTime(Map<String, Object> params);
}
