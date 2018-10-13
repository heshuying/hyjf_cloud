package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UserAlias;
import com.hyjf.am.user.dao.model.auto.UserAliasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAliasMapper {
    int countByExample(UserAliasExample example);

    int deleteByExample(UserAliasExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserAlias record);

    int insertSelective(UserAlias record);

    List<UserAlias> selectByExample(UserAliasExample example);

    UserAlias selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserAlias record, @Param("example") UserAliasExample example);

    int updateByExample(@Param("record") UserAlias record, @Param("example") UserAliasExample example);

    int updateByPrimaryKeySelective(UserAlias record);

    int updateByPrimaryKey(UserAlias record);
}