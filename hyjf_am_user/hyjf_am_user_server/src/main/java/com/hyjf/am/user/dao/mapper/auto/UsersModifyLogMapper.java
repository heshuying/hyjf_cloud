package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UsersModifyLog;
import com.hyjf.am.user.dao.model.auto.UsersModifyLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersModifyLogMapper {
    int countByExample(UsersModifyLogExample example);

    int deleteByExample(UsersModifyLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UsersModifyLog record);

    int insertSelective(UsersModifyLog record);

    List<UsersModifyLog> selectByExample(UsersModifyLogExample example);

    UsersModifyLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UsersModifyLog record, @Param("example") UsersModifyLogExample example);

    int updateByExample(@Param("record") UsersModifyLog record, @Param("example") UsersModifyLogExample example);

    int updateByPrimaryKeySelective(UsersModifyLog record);

    int updateByPrimaryKey(UsersModifyLog record);
}