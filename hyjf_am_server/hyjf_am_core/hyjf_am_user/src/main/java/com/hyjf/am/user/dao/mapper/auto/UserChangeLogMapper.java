package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UserChangeLog;
import com.hyjf.am.user.dao.model.auto.UserChangeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserChangeLogMapper {
    int countByExample(UserChangeLogExample example);

    int deleteByExample(UserChangeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserChangeLog record);

    int insertSelective(UserChangeLog record);

    List<UserChangeLog> selectByExample(UserChangeLogExample example);

    UserChangeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserChangeLog record, @Param("example") UserChangeLogExample example);

    int updateByExample(@Param("record") UserChangeLog record, @Param("example") UserChangeLogExample example);

    int updateByPrimaryKeySelective(UserChangeLog record);

    int updateByPrimaryKey(UserChangeLog record);
}