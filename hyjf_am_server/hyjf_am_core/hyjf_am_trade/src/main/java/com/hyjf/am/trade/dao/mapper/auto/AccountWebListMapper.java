package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AccountWebList;
import com.hyjf.am.trade.dao.model.auto.AccountWebListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountWebListMapper {
    int countByExample(AccountWebListExample example);

    int deleteByExample(AccountWebListExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AccountWebList record);

    int insertSelective(AccountWebList record);

    List<AccountWebList> selectByExample(AccountWebListExample example);

    AccountWebList selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AccountWebList record, @Param("example") AccountWebListExample example);

    int updateByExample(@Param("record") AccountWebList record, @Param("example") AccountWebListExample example);

    int updateByPrimaryKeySelective(AccountWebList record);

    int updateByPrimaryKey(AccountWebList record);
}