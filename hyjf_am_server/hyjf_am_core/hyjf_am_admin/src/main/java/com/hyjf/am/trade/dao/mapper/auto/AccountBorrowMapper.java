package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AccountBorrow;
import com.hyjf.am.trade.dao.model.auto.AccountBorrowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountBorrowMapper {
    int countByExample(AccountBorrowExample example);

    int deleteByExample(AccountBorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountBorrow record);

    int insertSelective(AccountBorrow record);

    List<AccountBorrow> selectByExample(AccountBorrowExample example);

    AccountBorrow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountBorrow record, @Param("example") AccountBorrowExample example);

    int updateByExample(@Param("record") AccountBorrow record, @Param("example") AccountBorrowExample example);

    int updateByPrimaryKeySelective(AccountBorrow record);

    int updateByPrimaryKey(AccountBorrow record);
}