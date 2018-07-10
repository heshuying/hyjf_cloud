package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.bean.TzjDayReportBean;

import java.util.Date;
import java.util.Set; /**
 * @author xiasq
 * @version TzjService, v0.1 2018/7/9 9:31
 */
public interface TzjService {
    /**
     * 查询投之家当日投资数据：每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
     * @param tzjUserIds 投之家所有注册用户
     * @param startTime
     * @param endTime
     * @return
     */
    TzjDayReportBean queryTradeDataOnToday(Set<Integer> tzjUserIds, Date startTime, Date endTime);
    /**
     * 查询投之家当日新充人数 新投人数
     * @param tzjUserIds  投之家当日注册用户
     * @param startTime
     * @param endTime
     * @return
     */
    TzjDayReportBean queryTradeNewDataOnToday(Set<Integer> tzjUserIds , Date startTime, Date endTime);
}
