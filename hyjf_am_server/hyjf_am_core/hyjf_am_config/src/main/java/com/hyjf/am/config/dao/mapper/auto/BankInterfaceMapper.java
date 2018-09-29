package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.BankInterface;
import com.hyjf.am.config.dao.model.auto.BankInterfaceExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankInterfaceMapper {
    int countByExample(BankInterfaceExample example);

    int deleteByExample(BankInterfaceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankInterface record);

    int insertSelective(BankInterface record);

    List<BankInterface> selectByExample(BankInterfaceExample example);

    BankInterface selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankInterface record, @Param("example") BankInterfaceExample example);

    int updateByExample(@Param("record") BankInterface record, @Param("example") BankInterfaceExample example);

    int updateByPrimaryKeySelective(BankInterface record);

    int updateByPrimaryKey(BankInterface record);
}