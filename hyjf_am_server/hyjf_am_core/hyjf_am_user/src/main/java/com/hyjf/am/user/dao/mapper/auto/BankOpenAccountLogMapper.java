package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.BankOpenAccountLog;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountLogExample;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountLogKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankOpenAccountLogMapper {
    int countByExample(BankOpenAccountLogExample example);

    int deleteByExample(BankOpenAccountLogExample example);

    int deleteByPrimaryKey(BankOpenAccountLogKey key);

    int insert(BankOpenAccountLog record);

    int insertSelective(BankOpenAccountLog record);

    List<BankOpenAccountLog> selectByExample(BankOpenAccountLogExample example);

    BankOpenAccountLog selectByPrimaryKey(BankOpenAccountLogKey key);

    int updateByExampleSelective(@Param("record") BankOpenAccountLog record, @Param("example") BankOpenAccountLogExample example);

    int updateByExample(@Param("record") BankOpenAccountLog record, @Param("example") BankOpenAccountLogExample example);

    int updateByPrimaryKeySelective(BankOpenAccountLog record);

    int updateByPrimaryKey(BankOpenAccountLog record);
}