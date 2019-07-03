package com.hyjf.wbs.user.dao.mapper.auto;

import com.hyjf.wbs.user.dao.model.auto.BankOpenAccountLog;
import com.hyjf.wbs.user.dao.model.auto.BankOpenAccountLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankOpenAccountLogMapper {
    int countByExample(BankOpenAccountLogExample example);

    int deleteByExample(BankOpenAccountLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankOpenAccountLog record);

    int insertSelective(BankOpenAccountLog record);

    List<BankOpenAccountLog> selectByExample(BankOpenAccountLogExample example);

    BankOpenAccountLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankOpenAccountLog record, @Param("example") BankOpenAccountLogExample example);

    int updateByExample(@Param("record") BankOpenAccountLog record, @Param("example") BankOpenAccountLogExample example);

    int updateByPrimaryKeySelective(BankOpenAccountLog record);

    int updateByPrimaryKey(BankOpenAccountLog record);
}