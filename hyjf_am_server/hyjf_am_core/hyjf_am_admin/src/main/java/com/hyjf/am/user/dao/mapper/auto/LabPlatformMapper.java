package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.LabPlatform;
import com.hyjf.am.user.dao.model.auto.LabPlatformExample;

public interface LabPlatformMapper {
    int countByExample(LabPlatformExample example);

    int deleteByExample(LabPlatformExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LabPlatform record);

    int insertSelective(LabPlatform record);

    List<LabPlatform> selectByExample(LabPlatformExample example);

    LabPlatform selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LabPlatform record, @Param("example") LabPlatformExample example);

    int updateByExample(@Param("record") LabPlatform record, @Param("example") LabPlatformExample example);

    int updateByPrimaryKeySelective(LabPlatform record);

    int updateByPrimaryKey(LabPlatform record);
}