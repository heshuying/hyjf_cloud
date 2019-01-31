package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.bifa;

import java.math.BigDecimal;

public interface BifaBorrowRecoverCustomizeMapper {

    BigDecimal selectServiceCostSum(String borrowNid);
}