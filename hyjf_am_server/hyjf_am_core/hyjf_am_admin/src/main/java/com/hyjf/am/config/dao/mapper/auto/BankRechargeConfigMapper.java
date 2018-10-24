package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.BankRechargeConfig;
import com.hyjf.am.config.dao.model.auto.BankRechargeConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankRechargeConfigMapper {
    int countByExample(BankRechargeConfigExample example);

    int deleteByExample(BankRechargeConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankRechargeConfig record);

    int insertSelective(BankRechargeConfig record);

    List<BankRechargeConfig> selectByExample(BankRechargeConfigExample example);

    BankRechargeConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankRechargeConfig record, @Param("example") BankRechargeConfigExample example);

    int updateByExample(@Param("record") BankRechargeConfig record, @Param("example") BankRechargeConfigExample example);

    int updateByPrimaryKeySelective(BankRechargeConfig record);

    int updateByPrimaryKey(BankRechargeConfig record);
}