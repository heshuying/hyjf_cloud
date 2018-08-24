package com.hyjf.am.user.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.AppointmentAuthLog;
import com.hyjf.am.user.dao.model.auto.AppointmentAuthLogExample;

public interface AppointmentAuthLogMapper {
    int countByExample(AppointmentAuthLogExample example);

    int deleteByExample(AppointmentAuthLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppointmentAuthLog record);

    int insertSelective(AppointmentAuthLog record);

    List<AppointmentAuthLog> selectByExample(AppointmentAuthLogExample example);

    AppointmentAuthLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppointmentAuthLog record, @Param("example") AppointmentAuthLogExample example);

    int updateByExample(@Param("record") AppointmentAuthLog record, @Param("example") AppointmentAuthLogExample example);

    int updateByPrimaryKeySelective(AppointmentAuthLog record);

    int updateByPrimaryKey(AppointmentAuthLog record);
}