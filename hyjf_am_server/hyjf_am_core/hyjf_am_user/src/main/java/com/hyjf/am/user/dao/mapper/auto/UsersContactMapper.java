package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UsersContact;
import com.hyjf.am.user.dao.model.auto.UsersContactExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersContactMapper {
    int countByExample(UsersContactExample example);

    int deleteByExample(UsersContactExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UsersContact record);

    int insertSelective(UsersContact record);

    List<UsersContact> selectByExample(UsersContactExample example);

    UsersContact selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UsersContact record, @Param("example") UsersContactExample example);

    int updateByExample(@Param("record") UsersContact record, @Param("example") UsersContactExample example);

    int updateByPrimaryKeySelective(UsersContact record);

    int updateByPrimaryKey(UsersContact record);
}