package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.BankSmsAuthCode;
import com.hyjf.am.user.dao.model.auto.BankSmsAuthCodeExample;

public interface BankSmsAuthCodeMapper {
    int countByExample(BankSmsAuthCodeExample example);

    int deleteByExample(BankSmsAuthCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankSmsAuthCode record);

    int insertSelective(BankSmsAuthCode record);

    List<BankSmsAuthCode> selectByExample(BankSmsAuthCodeExample example);

    BankSmsAuthCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankSmsAuthCode record, @Param("example") BankSmsAuthCodeExample example);

    int updateByExample(@Param("record") BankSmsAuthCode record, @Param("example") BankSmsAuthCodeExample example);

    int updateByPrimaryKeySelective(BankSmsAuthCode record);

    int updateByPrimaryKey(BankSmsAuthCode record);
}