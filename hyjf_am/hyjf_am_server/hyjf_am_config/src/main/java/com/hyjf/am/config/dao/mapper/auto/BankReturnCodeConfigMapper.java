package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BankReturnCodeConfigMapper {
    int countByExample(BankReturnCodeConfigExample example);

    int deleteByExample(BankReturnCodeConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankReturnCodeConfig record);

    int insertSelective(BankReturnCodeConfig record);

    List<BankReturnCodeConfig> selectByExample(BankReturnCodeConfigExample example);

    BankReturnCodeConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankReturnCodeConfig record, @Param("example") BankReturnCodeConfigExample example);

    int updateByExample(@Param("record") BankReturnCodeConfig record, @Param("example") BankReturnCodeConfigExample example);

    int updateByPrimaryKeySelective(BankReturnCodeConfig record);

    int updateByPrimaryKey(BankReturnCodeConfig record);
}