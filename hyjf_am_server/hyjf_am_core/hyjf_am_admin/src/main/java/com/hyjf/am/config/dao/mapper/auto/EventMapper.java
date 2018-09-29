package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.Event;
import com.hyjf.am.config.dao.model.auto.EventExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EventMapper {
    int countByExample(EventExample example);

    int deleteByExample(EventExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Event record);

    int insertSelective(Event record);

    List<Event> selectByExample(EventExample example);

    Event selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Event record, @Param("example") EventExample example);

    int updateByExample(@Param("record") Event record, @Param("example") EventExample example);

    int updateByPrimaryKeySelective(Event record);

    int updateByPrimaryKey(Event record);
}