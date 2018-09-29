package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.auto.AccountTradeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountTradeMapper {
    int countByExample(AccountTradeExample example);

    int deleteByExample(AccountTradeExample example);

    int deleteByPrimaryKey(Short id);

    int insert(AccountTrade record);

    int insertSelective(AccountTrade record);

    List<AccountTrade> selectByExample(AccountTradeExample example);

    AccountTrade selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") AccountTrade record, @Param("example") AccountTradeExample example);

    int updateByExample(@Param("record") AccountTrade record, @Param("example") AccountTradeExample example);

    int updateByPrimaryKeySelective(AccountTrade record);

    int updateByPrimaryKey(AccountTrade record);
}