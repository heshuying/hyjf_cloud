package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UsersLog;
import com.hyjf.am.user.dao.model.auto.UsersLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersLogMapper {
    int countByExample(UsersLogExample example);

    int deleteByExample(UsersLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UsersLog record);

    int insertSelective(UsersLog record);

    List<UsersLog> selectByExampleWithBLOBs(UsersLogExample example);

    List<UsersLog> selectByExample(UsersLogExample example);

    UsersLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UsersLog record, @Param("example") UsersLogExample example);

    int updateByExampleWithBLOBs(@Param("record") UsersLog record, @Param("example") UsersLogExample example);

    int updateByExample(@Param("record") UsersLog record, @Param("example") UsersLogExample example);

    int updateByPrimaryKeySelective(UsersLog record);

    int updateByPrimaryKeyWithBLOBs(UsersLog record);

    int updateByPrimaryKey(UsersLog record);
}