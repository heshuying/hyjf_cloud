package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AccountRecharge;
import com.hyjf.am.trade.dao.model.auto.AccountRechargeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountRechargeMapper {
    int countByExample(AccountRechargeExample example);

    int deleteByExample(AccountRechargeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountRecharge record);

    int insertSelective(AccountRecharge record);

    List<AccountRecharge> selectByExample(AccountRechargeExample example);

    AccountRecharge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountRecharge record, @Param("example") AccountRechargeExample example);

    int updateByExample(@Param("record") AccountRecharge record, @Param("example") AccountRechargeExample example);

    int updateByPrimaryKeySelective(AccountRecharge record);

    int updateByPrimaryKey(AccountRecharge record);
}