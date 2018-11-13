package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.HolidaysConfigNew;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HolidaysConfigMapper {
    int countByExample(HolidaysConfigExample example);

    int deleteByExample(HolidaysConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HolidaysConfigNew record);

    int insertSelective(HolidaysConfigNew record);

    List<HolidaysConfigNew> selectByExample(HolidaysConfigExample example);

    HolidaysConfigNew selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HolidaysConfigNew record, @Param("example") HolidaysConfigExample example);

    int updateByExample(@Param("record") HolidaysConfigNew record, @Param("example") HolidaysConfigExample example);

    int updateByPrimaryKeySelective(HolidaysConfigNew record);

    int updateByPrimaryKey(HolidaysConfigNew record);
}