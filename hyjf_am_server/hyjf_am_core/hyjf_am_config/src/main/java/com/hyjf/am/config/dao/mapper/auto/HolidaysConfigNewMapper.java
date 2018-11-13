package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.HolidaysConfigNew;
import com.hyjf.am.config.dao.model.auto.HolidaysConfigNewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HolidaysConfigNewMapper {
    int countByExample(HolidaysConfigNewExample example);

    int deleteByExample(HolidaysConfigNewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HolidaysConfigNew record);

    int insertSelective(HolidaysConfigNew record);

    List<HolidaysConfigNew> selectByExample(HolidaysConfigNewExample example);

    HolidaysConfigNew selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HolidaysConfigNew record, @Param("example") HolidaysConfigNewExample example);

    int updateByExample(@Param("record") HolidaysConfigNew record, @Param("example") HolidaysConfigNewExample example);

    int updateByPrimaryKeySelective(HolidaysConfigNew record);

    int updateByPrimaryKey(HolidaysConfigNew record);
}