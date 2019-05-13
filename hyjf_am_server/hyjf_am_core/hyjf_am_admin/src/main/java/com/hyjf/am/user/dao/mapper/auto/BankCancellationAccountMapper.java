package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.BankCancellationAccount;
import com.hyjf.am.user.dao.model.auto.BankCancellationAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankCancellationAccountMapper {
    int countByExample(BankCancellationAccountExample example);

    int deleteByExample(BankCancellationAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankCancellationAccount record);

    int insertSelective(BankCancellationAccount record);

    List<BankCancellationAccount> selectByExample(BankCancellationAccountExample example);

    BankCancellationAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankCancellationAccount record, @Param("example") BankCancellationAccountExample example);

    int updateByExample(@Param("record") BankCancellationAccount record, @Param("example") BankCancellationAccountExample example);

    int updateByPrimaryKeySelective(BankCancellationAccount record);

    int updateByPrimaryKey(BankCancellationAccount record);
}