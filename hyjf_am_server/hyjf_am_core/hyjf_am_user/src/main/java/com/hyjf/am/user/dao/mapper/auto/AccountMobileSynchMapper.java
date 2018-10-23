package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynchExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMobileSynchMapper {
    int countByExample(AccountMobileSynchExample example);

    int deleteByExample(AccountMobileSynchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountMobileSynch record);

    int insertSelective(AccountMobileSynch record);

    List<AccountMobileSynch> selectByExample(AccountMobileSynchExample example);

    AccountMobileSynch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountMobileSynch record, @Param("example") AccountMobileSynchExample example);

    int updateByExample(@Param("record") AccountMobileSynch record, @Param("example") AccountMobileSynchExample example);

    int updateByPrimaryKeySelective(AccountMobileSynch record);

    int updateByPrimaryKey(AccountMobileSynch record);
}