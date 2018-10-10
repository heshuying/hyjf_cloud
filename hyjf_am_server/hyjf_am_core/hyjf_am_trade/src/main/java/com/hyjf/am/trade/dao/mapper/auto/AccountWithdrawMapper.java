package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.dao.model.auto.AccountWithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountWithdrawMapper {
    int countByExample(AccountWithdrawExample example);

    int deleteByExample(AccountWithdrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountWithdraw record);

    int insertSelective(AccountWithdraw record);

    List<AccountWithdraw> selectByExample(AccountWithdrawExample example);

    AccountWithdraw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountWithdraw record, @Param("example") AccountWithdrawExample example);

    int updateByExample(@Param("record") AccountWithdraw record, @Param("example") AccountWithdrawExample example);

    int updateByPrimaryKeySelective(AccountWithdraw record);

    int updateByPrimaryKey(AccountWithdraw record);
}