package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.LandingPage;
import com.hyjf.am.config.dao.model.auto.LandingPageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LandingPageMapper {
    int countByExample(LandingPageExample example);

    int deleteByExample(LandingPageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LandingPage record);

    int insertSelective(LandingPage record);

    List<LandingPage> selectByExample(LandingPageExample example);

    LandingPage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LandingPage record, @Param("example") LandingPageExample example);

    int updateByExample(@Param("record") LandingPage record, @Param("example") LandingPageExample example);

    int updateByPrimaryKeySelective(LandingPage record);

    int updateByPrimaryKey(LandingPage record);
}