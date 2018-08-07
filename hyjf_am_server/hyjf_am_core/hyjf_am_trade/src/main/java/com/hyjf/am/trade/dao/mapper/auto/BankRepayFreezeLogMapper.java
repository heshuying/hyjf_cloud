package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BankRepayFreezeLog;
import com.hyjf.am.trade.dao.model.auto.BankRepayFreezeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankRepayFreezeLogMapper {
    int countByExample(BankRepayFreezeLogExample example);

    int deleteByExample(BankRepayFreezeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankRepayFreezeLog record);

    int insertSelective(BankRepayFreezeLog record);

    List<BankRepayFreezeLog> selectByExample(BankRepayFreezeLogExample example);

    BankRepayFreezeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankRepayFreezeLog record, @Param("example") BankRepayFreezeLogExample example);

    int updateByExample(@Param("record") BankRepayFreezeLog record, @Param("example") BankRepayFreezeLogExample example);

    int updateByPrimaryKeySelective(BankRepayFreezeLog record);

    int updateByPrimaryKey(BankRepayFreezeLog record);
}