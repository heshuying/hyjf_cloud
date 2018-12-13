package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BankCreditEnd;
import com.hyjf.am.trade.dao.model.auto.BankCreditEndExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankCreditEndMapper {
    int countByExample(BankCreditEndExample example);

    int deleteByExample(BankCreditEndExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankCreditEnd record);

    int insertSelective(BankCreditEnd record);

    List<BankCreditEnd> selectByExample(BankCreditEndExample example);

    BankCreditEnd selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankCreditEnd record, @Param("example") BankCreditEndExample example);

    int updateByExample(@Param("record") BankCreditEnd record, @Param("example") BankCreditEndExample example);

    int updateByPrimaryKeySelective(BankCreditEnd record);

    int updateByPrimaryKey(BankCreditEnd record);
}