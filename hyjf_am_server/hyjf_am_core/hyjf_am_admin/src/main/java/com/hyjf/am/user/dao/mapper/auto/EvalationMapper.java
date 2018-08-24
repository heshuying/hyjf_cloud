package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.Evalation;
import com.hyjf.am.user.dao.model.auto.EvalationExample;

public interface EvalationMapper {
    int countByExample(EvalationExample example);

    int deleteByExample(EvalationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Evalation record);

    int insertSelective(Evalation record);

    List<Evalation> selectByExample(EvalationExample example);

    Evalation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Evalation record, @Param("example") EvalationExample example);

    int updateByExample(@Param("record") Evalation record, @Param("example") EvalationExample example);

    int updateByPrimaryKeySelective(Evalation record);

    int updateByPrimaryKey(Evalation record);
}