/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service;

import com.hyjf.am.vo.admin.SellDailyDistributionVO;

import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version DailyAutoSendService, v0.1 2018/11/16 9:28
 */
public interface DailyAutoSendService {

    /**
     * 获取后台发送邮件配置
     * @return
     */
    List<SellDailyDistributionVO> listSellDailyDistribution();
    /**
     * 判断某天是否是工作日
     * @return
     */
    boolean isWorkdateOnSomeDay();

    /**
     * 判断当天是不是当月第一个工作日
     * @return
     */
    boolean isTodayFirstWorkdayOnMonth();

    /**
     * 发送销售日报邮件
     * @param sellDailyDistribution
     */
    void sendMail(SellDailyDistributionVO sellDailyDistribution);
}
