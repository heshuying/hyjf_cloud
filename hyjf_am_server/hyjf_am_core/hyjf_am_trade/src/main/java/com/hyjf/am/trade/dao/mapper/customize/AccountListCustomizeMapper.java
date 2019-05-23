package com.hyjf.am.trade.dao.mapper.customize;

import java.math.BigDecimal;
import java.util.List;

public interface AccountListCustomizeMapper {

    /**
     * 计算月坐席下用户的月初站岗资金
     * @param userIds
     * @return
     */
    BigDecimal getUsersMonthBeginBalance(List<Integer> userIds);

    /**
     * 计算月坐席下用户的当前站岗资金
     * @param userIds
     * @return
     */
    BigDecimal getUsersMonthNowBalance(List<Integer> userIds);
}