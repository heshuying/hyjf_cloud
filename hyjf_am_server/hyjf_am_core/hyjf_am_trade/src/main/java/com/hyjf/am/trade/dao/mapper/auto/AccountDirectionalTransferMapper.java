package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AccountDirectionalTransfer;
import com.hyjf.am.trade.dao.model.auto.AccountDirectionalTransferExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountDirectionalTransferMapper {
    int countByExample(AccountDirectionalTransferExample example);

    int deleteByExample(AccountDirectionalTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AccountDirectionalTransfer record);

    int insertSelective(AccountDirectionalTransfer record);

    List<AccountDirectionalTransfer> selectByExample(AccountDirectionalTransferExample example);

    AccountDirectionalTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AccountDirectionalTransfer record, @Param("example") AccountDirectionalTransferExample example);

    int updateByExample(@Param("record") AccountDirectionalTransfer record, @Param("example") AccountDirectionalTransferExample example);

    int updateByPrimaryKeySelective(AccountDirectionalTransfer record);

    int updateByPrimaryKey(AccountDirectionalTransfer record);
}