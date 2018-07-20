package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.WhereaboutsPageConfig;
import com.hyjf.am.user.dao.model.auto.WhereaboutsPageConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WhereaboutsPageConfigMapper {
    int countByExample(WhereaboutsPageConfigExample example);

    int deleteByExample(WhereaboutsPageConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WhereaboutsPageConfig record);

    int insertSelective(WhereaboutsPageConfig record);

    List<WhereaboutsPageConfig> selectByExample(WhereaboutsPageConfigExample example);

    WhereaboutsPageConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WhereaboutsPageConfig record, @Param("example") WhereaboutsPageConfigExample example);

    int updateByExample(@Param("record") WhereaboutsPageConfig record, @Param("example") WhereaboutsPageConfigExample example);

    int updateByPrimaryKeySelective(WhereaboutsPageConfig record);

    int updateByPrimaryKey(WhereaboutsPageConfig record);
}