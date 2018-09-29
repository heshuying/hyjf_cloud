package com.hyjf.am.market.dao.mapper.auto;

import com.hyjf.am.market.dao.model.auto.ActivityList;
import com.hyjf.am.market.dao.model.auto.ActivityListExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityListMapper {
    int countByExample(ActivityListExample example);

    int deleteByExample(ActivityListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ActivityList record);

    int insertSelective(ActivityList record);

    List<ActivityList> selectByExample(ActivityListExample example);

    ActivityList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ActivityList record, @Param("example") ActivityListExample example);

    int updateByExample(@Param("record") ActivityList record, @Param("example") ActivityListExample example);

    int updateByPrimaryKeySelective(ActivityList record);

    int updateByPrimaryKey(ActivityList record);
}