package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.AccountException;
import com.hyjf.am.trade.dao.model.auto.AccountExceptionExample;

public interface AccountExceptionMapper {
    int countByExample(AccountExceptionExample example);

    int deleteByExample(AccountExceptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountException record);

    int insertSelective(AccountException record);

    List<AccountException> selectByExample(AccountExceptionExample example);

    AccountException selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountException record, @Param("example") AccountExceptionExample example);

    int updateByExample(@Param("record") AccountException record, @Param("example") AccountExceptionExample example);

    int updateByPrimaryKeySelective(AccountException record);

    int updateByPrimaryKey(AccountException record);
}