package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UsersInfo;
import com.hyjf.am.user.dao.model.auto.UsersInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersInfoMapper {
    int countByExample(UsersInfoExample example);

    int deleteByExample(UsersInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UsersInfo record);

    int insertSelective(UsersInfo record);

    List<UsersInfo> selectByExample(UsersInfoExample example);

    UsersInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UsersInfo record, @Param("example") UsersInfoExample example);

    int updateByExample(@Param("record") UsersInfo record, @Param("example") UsersInfoExample example);

    int updateByPrimaryKeySelective(UsersInfo record);

    int updateByPrimaryKey(UsersInfo record);
}