package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.HjhUserAuth;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhUserAuthMapper {
    int countByExample(HjhUserAuthExample example);

    int deleteByExample(HjhUserAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhUserAuth record);

    int insertSelective(HjhUserAuth record);

    List<HjhUserAuth> selectByExample(HjhUserAuthExample example);

    HjhUserAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhUserAuth record, @Param("example") HjhUserAuthExample example);

    int updateByExample(@Param("record") HjhUserAuth record, @Param("example") HjhUserAuthExample example);

    int updateByPrimaryKeySelective(HjhUserAuth record);

    int updateByPrimaryKey(HjhUserAuth record);
}