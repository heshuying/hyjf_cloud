package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.HolidaysConfig;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigExample;

public interface HolidaysConfigMapper {
    int countByExample(HolidaysConfigExample example);

    int deleteByExample(HolidaysConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HolidaysConfig record);

    int insertSelective(HolidaysConfig record);

    List<HolidaysConfig> selectByExample(HolidaysConfigExample example);

    HolidaysConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HolidaysConfig record, @Param("example") HolidaysConfigExample example);

    int updateByExample(@Param("record") HolidaysConfig record, @Param("example") HolidaysConfigExample example);

    int updateByPrimaryKeySelective(HolidaysConfig record);

    int updateByPrimaryKey(HolidaysConfig record);
}