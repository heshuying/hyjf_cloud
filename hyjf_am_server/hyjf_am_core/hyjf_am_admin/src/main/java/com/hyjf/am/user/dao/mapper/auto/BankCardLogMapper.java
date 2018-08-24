package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.BankCardLog;
import com.hyjf.am.user.dao.model.auto.BankCardLogExample;

public interface BankCardLogMapper {
    int countByExample(BankCardLogExample example);

    int deleteByExample(BankCardLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankCardLog record);

    int insertSelective(BankCardLog record);

    List<BankCardLog> selectByExample(BankCardLogExample example);

    BankCardLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankCardLog record, @Param("example") BankCardLogExample example);

    int updateByExample(@Param("record") BankCardLog record, @Param("example") BankCardLogExample example);

    int updateByPrimaryKeySelective(BankCardLog record);

    int updateByPrimaryKey(BankCardLog record);
}