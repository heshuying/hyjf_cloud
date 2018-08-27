package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.DebtCreditTenderLog;
import com.hyjf.am.trade.dao.model.auto.DebtCreditTenderLogExample;

public interface DebtCreditTenderLogMapper {
    int countByExample(DebtCreditTenderLogExample example);

    int deleteByExample(DebtCreditTenderLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtCreditTenderLog record);

    int insertSelective(DebtCreditTenderLog record);

    List<DebtCreditTenderLog> selectByExample(DebtCreditTenderLogExample example);

    DebtCreditTenderLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtCreditTenderLog record, @Param("example") DebtCreditTenderLogExample example);

    int updateByExample(@Param("record") DebtCreditTenderLog record, @Param("example") DebtCreditTenderLogExample example);

    int updateByPrimaryKeySelective(DebtCreditTenderLog record);

    int updateByPrimaryKey(DebtCreditTenderLog record);
}