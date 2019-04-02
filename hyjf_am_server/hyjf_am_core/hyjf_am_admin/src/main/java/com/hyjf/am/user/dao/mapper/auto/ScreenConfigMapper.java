package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.user.dao.model.auto.ScreenConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScreenConfigMapper {
    int countByExample(ScreenConfigExample example);

    int deleteByExample(ScreenConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScreenConfig record);

    int insertSelective(ScreenConfig record);

    List<ScreenConfig> selectByExample(ScreenConfigExample example);

    ScreenConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScreenConfig record, @Param("example") ScreenConfigExample example);

    int updateByExample(@Param("record") ScreenConfig record, @Param("example") ScreenConfigExample example);

    int updateByPrimaryKeySelective(ScreenConfig record);

    int updateByPrimaryKey(ScreenConfig record);
}