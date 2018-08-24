package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.Config;
import com.hyjf.am.config.dao.model.auto.ConfigExample;

public interface ConfigMapper {
    int countByExample(ConfigExample example);

    int deleteByExample(ConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Config record);

    int insertSelective(Config record);

    List<Config> selectByExample(ConfigExample example);

    Config selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Config record, @Param("example") ConfigExample example);

    int updateByExample(@Param("record") Config record, @Param("example") ConfigExample example);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
}