package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.AppUtmRegExample;

public interface AppUtmRegMapper {
    int countByExample(AppUtmRegExample example);

    int deleteByExample(AppUtmRegExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AppUtmReg record);

    int insertSelective(AppUtmReg record);

    List<AppUtmReg> selectByExample(AppUtmRegExample example);

    AppUtmReg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AppUtmReg record, @Param("example") AppUtmRegExample example);

    int updateByExample(@Param("record") AppUtmReg record, @Param("example") AppUtmRegExample example);

    int updateByPrimaryKeySelective(AppUtmReg record);

    int updateByPrimaryKey(AppUtmReg record);
}