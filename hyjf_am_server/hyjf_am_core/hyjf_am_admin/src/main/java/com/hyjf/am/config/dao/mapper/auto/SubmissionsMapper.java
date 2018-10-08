package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.Submissions;
import com.hyjf.am.config.dao.model.auto.SubmissionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubmissionsMapper {
    int countByExample(SubmissionsExample example);

    int deleteByExample(SubmissionsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Submissions record);

    int insertSelective(Submissions record);

    List<Submissions> selectByExample(SubmissionsExample example);

    Submissions selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Submissions record, @Param("example") SubmissionsExample example);

    int updateByExample(@Param("record") Submissions record, @Param("example") SubmissionsExample example);

    int updateByPrimaryKeySelective(Submissions record);

    int updateByPrimaryKey(Submissions record);
}