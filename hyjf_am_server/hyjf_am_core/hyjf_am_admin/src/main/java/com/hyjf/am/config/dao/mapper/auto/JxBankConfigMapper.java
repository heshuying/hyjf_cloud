package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.dao.model.auto.JxBankConfigExample;

public interface JxBankConfigMapper {
    int countByExample(JxBankConfigExample example);

    int deleteByExample(JxBankConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(JxBankConfig record);

    int insertSelective(JxBankConfig record);

    List<JxBankConfig> selectByExample(JxBankConfigExample example);

    JxBankConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") JxBankConfig record, @Param("example") JxBankConfigExample example);

    int updateByExample(@Param("record") JxBankConfig record, @Param("example") JxBankConfigExample example);

    int updateByPrimaryKeySelective(JxBankConfig record);

    int updateByPrimaryKey(JxBankConfig record);
}