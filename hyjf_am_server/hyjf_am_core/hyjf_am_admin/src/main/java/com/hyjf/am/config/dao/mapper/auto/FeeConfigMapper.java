package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.FeeConfig;
import com.hyjf.am.config.dao.model.auto.FeeConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FeeConfigMapper {
    int countByExample(FeeConfigExample example);

    int deleteByExample(FeeConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FeeConfig record);

    int insertSelective(FeeConfig record);

    List<FeeConfig> selectByExample(FeeConfigExample example);

    FeeConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FeeConfig record, @Param("example") FeeConfigExample example);

    int updateByExample(@Param("record") FeeConfig record, @Param("example") FeeConfigExample example);

    int updateByPrimaryKeySelective(FeeConfig record);

    int updateByPrimaryKey(FeeConfig record);
}