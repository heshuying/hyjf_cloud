package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.AccountBank;
import com.hyjf.am.user.dao.model.auto.AccountBankExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountBankMapper {
    int countByExample(AccountBankExample example);

    int deleteByExample(AccountBankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountBank record);

    int insertSelective(AccountBank record);

    List<AccountBank> selectByExample(AccountBankExample example);

    AccountBank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountBank record, @Param("example") AccountBankExample example);

    int updateByExample(@Param("record") AccountBank record, @Param("example") AccountBankExample example);

    int updateByPrimaryKeySelective(AccountBank record);

    int updateByPrimaryKey(AccountBank record);
}