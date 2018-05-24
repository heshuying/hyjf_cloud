package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.AppointmentRecodLog;
import com.hyjf.am.user.dao.model.auto.AppointmentRecodLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppointmentRecodLogMapper {
    int countByExample(AppointmentRecodLogExample example);

    int deleteByExample(AppointmentRecodLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppointmentRecodLog record);

    int insertSelective(AppointmentRecodLog record);

    List<AppointmentRecodLog> selectByExample(AppointmentRecodLogExample example);

    AppointmentRecodLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppointmentRecodLog record, @Param("example") AppointmentRecodLogExample example);

    int updateByExample(@Param("record") AppointmentRecodLog record, @Param("example") AppointmentRecodLogExample example);

    int updateByPrimaryKeySelective(AppointmentRecodLog record);

    int updateByPrimaryKey(AppointmentRecodLog record);
}