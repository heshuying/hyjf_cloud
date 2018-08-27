package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.AccountChinapnr;
import com.hyjf.am.user.dao.model.auto.AccountChinapnrExample;

public interface AccountChinapnrMapper {
    int countByExample(AccountChinapnrExample example);

    int deleteByExample(AccountChinapnrExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountChinapnr record);

    int insertSelective(AccountChinapnr record);

    List<AccountChinapnr> selectByExample(AccountChinapnrExample example);

    AccountChinapnr selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountChinapnr record, @Param("example") AccountChinapnrExample example);

    int updateByExample(@Param("record") AccountChinapnr record, @Param("example") AccountChinapnrExample example);

    int updateByPrimaryKeySelective(AccountChinapnr record);

    int updateByPrimaryKey(AccountChinapnr record);
}