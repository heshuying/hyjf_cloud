package com.hyjf.am.trade.dao.mapper.customize;

import java.math.BigDecimal;
import java.util.List;

public interface AccountListCustomizeMapper {

    BigDecimal getUsersMonthBeginBalance(List<Integer> userIds);

    BigDecimal getUsersMonthNowBalance(List<Integer> userIds);
}