/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.config.AppReapyCalendarResponse;
import com.hyjf.am.response.config.ContentArticleCustomizeResponse;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.RepayCalendarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version RepayCalendarClientImpl, v0.1 2018/7/27 11:51
 */
@Cilent
public class RepayCalendarClientImpl implements RepayCalendarClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询回款日历总数
     * @param params
     * @return
     */
    @Override
    public Integer countRepaymentCalendar(Map<String, Object> params) {
        AppReapyCalendarResponse response = restTemplate.postForObject(
                "http://AM-TRADE/am-trade/user/repayCalendar/countBorrowRepayment",
                params, AppReapyCalendarResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return null;
    }

    /**
     * 查询回款日历明细
     * @param params
     * @return
     */
    @Override
    public List<AppReapyCalendarResultVO> searchRepaymentCalendar(Map<String, Object> params) {
        AppReapyCalendarResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/user/repayCalendar/searchRepaymentCalendar",
                AppReapyCalendarResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 返回用户最近回款时间戳-秒
     * @param params
     * @return
     */
    @Override
    public Integer searchNearlyRepaymentTime(Map<String, Object> params) {
        AppReapyCalendarResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/user/repayCalendar/searchNearlyRepaymentTime",
                AppReapyCalendarResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return null;
    }
}
