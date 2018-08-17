/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.cs.market.client.RepayCalendarClient;
import com.hyjf.cs.market.service.RepayCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author dangzw
 * @version RepayCalendarService, v0.1 2018/7/27 11:49
 */
@Service
public class RepayCalendarServiceImpl implements RepayCalendarService {

    @Autowired
    private RepayCalendarClient repayCalendarClient;

    /**
     * 查询回款日历总数
     * @param params
     * @return
     */
    @Override
    public Integer countRepaymentCalendar(Map<String, Object> params) {
        return repayCalendarClient.countRepaymentCalendar(params);
    }

    /**
     * 查询回款日历明细
     * @param params
     * @return
     */
    @Override
    public List<AppReapyCalendarResultVO> searchRepaymentCalendar(Map<String, Object> params) {
        return repayCalendarClient.searchRepaymentCalendar(params);
    }

    /**
     * 返回用户最近回款时间戳-秒
     * @param params
     * @return
     */
    @Override
    public Integer searchNearlyRepaymentTime(Map<String, Object> params) {
        return repayCalendarClient.searchNearlyRepaymentTime(params);
    }
}
