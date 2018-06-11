package com.hyjf.am.borrow.dao.mapper.auto;

import com.hyjf.am.borrow.dao.model.auto.AccountList;
import com.hyjf.am.borrow.dao.model.auto.AccountListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountListMapper {
    int countByExample(AccountListExample example);

    int deleteByExample(AccountListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountList record);

    int insertSelective(AccountList record);

    List<AccountList> selectByExample(AccountListExample example);

    AccountList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountList record, @Param("example") AccountListExample example);

    int updateByExample(@Param("record") AccountList record, @Param("example") AccountListExample example);

    int updateByPrimaryKeySelective(AccountList record);

    int updateByPrimaryKey(AccountList record);
}