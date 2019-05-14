package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithdrawRuleConfigCustomizeMapper {
    int countByExample(WithdrawRuleConfigExample example);

    int deleteByExample(WithdrawRuleConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawRuleConfig record);

    int insertSelective(WithdrawRuleConfig record);

    List<WithdrawRuleConfig> selectByExample(WithdrawRuleConfigExample example);

    WithdrawRuleConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WithdrawRuleConfig record, @Param("example") WithdrawRuleConfigExample example);

    int updateByExample(@Param("record") WithdrawRuleConfig record, @Param("example") WithdrawRuleConfigExample example);

    int updateByPrimaryKeySelective(WithdrawRuleConfig record);

    int updateByPrimaryKey(WithdrawRuleConfig record);
}