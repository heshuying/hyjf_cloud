package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.BankConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BankConfigMapper {
    int countByExample(BankConfigExample example);

    int deleteByExample(BankConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BankConfig record);

    int insertSelective(BankConfig record);

    List<BankConfig> selectByExample(BankConfigExample example);

    BankConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BankConfig record, @Param("example") BankConfigExample example);

    int updateByExample(@Param("record") BankConfig record, @Param("example") BankConfigExample example);

    int updateByPrimaryKeySelective(BankConfig record);

    int updateByPrimaryKey(BankConfig record);
}