package com.hyjf.cs.market.client;

import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/7/6 11:04
 */
public interface AmUserClient {
    /**
     * 查询投之家当天注册人数、开户人数、绑卡人数
     * @param startTime
     * @param endTime
     * @return
     */
    TzjDayReportVO queryUserDataOnToday(Date startTime,Date endTime);

    /**
     * 查询投之家当天注册用户
     * @param startTime
     * @param endTime
     * @return
     */
    Set<Integer> queryRegisterUsersOnToday(Date startTime,Date endTime);
    /**
     * 查询投之家所有注册用户
     * @return
     */
    Set<Integer> queryAllTzjUsers();

    /**
     * 获取所有渠道
     * @return
     */
    List<UtmVO> selectUtmPlatList();

    /**
     * 访问数
     * @param sourceId 账户推广平台
     * @return
     */
    Integer getAccessNumber(Integer sourceId);

    /**
     * 注册数
     * @param sourceId 账户推广平台
     * @return
     */
    Integer getRegistNumber(Integer sourceId);

    /**
     * 开户数
     * @param sourceId
     * @return
     */
    Integer getOpenAccountNumber(Integer sourceId);

    /**
     * 投资人数
     * @param sourceId
     * @return
     */
    Integer getTenderNumber(Integer sourceId);

    /**
     * 累计充值
     * @param sourceId
     * @return
     */
    BigDecimal getCumulativeRecharge(Integer sourceId);

    /**
     * 汇直投投资金额
     * @param sourceId
     * @return
     */
    BigDecimal getHztTenderPrice(Integer sourceId);

    /**
     * 汇消费投资金额
     * @param sourceId
     * @return
     */
    BigDecimal getHxfTenderPrice(Integer sourceId);

    /**
     * 汇天利投资金额
     * @param sourceId
     * @return
     */
    BigDecimal gethtlTenderPrice(Integer sourceId);

    /**
     * 汇添金投资金额
     * @param sourceId
     * @return
     */
    BigDecimal getHtjTenderPrice(Integer sourceId);

    /**
     * 汇金理财投资金额
     * @param sourceId
     * @return
     */
    BigDecimal getRtbTenderPrice(Integer sourceId);

    /**
     * 汇转让投资金额
     * @param sourceId
     * @return
     */
    BigDecimal getHzrTenderPrice(Integer sourceId);
}
