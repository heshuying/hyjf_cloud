package com.hyjf.am.trade.dao.mapper.customize;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AccountListCustomizeMapper {

    List<Integer> getUserIdsByCurrentOwnerAndCustomerGroup(@Param("currentOwner") String currentOwner, @Param("customerGroup") int i);

    BigDecimal getUsersMonthBeginBalance(List<Integer> userIds);

    BigDecimal getUsersMonthNowBalance(List<Integer> userIds);
}