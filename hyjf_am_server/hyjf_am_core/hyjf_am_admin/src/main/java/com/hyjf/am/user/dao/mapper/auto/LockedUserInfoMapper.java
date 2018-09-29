package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.user.dao.model.auto.LockedUserInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LockedUserInfoMapper {
    int countByExample(LockedUserInfoExample example);

    int deleteByExample(LockedUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LockedUserInfo record);

    int insertSelective(LockedUserInfo record);

    List<LockedUserInfo> selectByExample(LockedUserInfoExample example);

    LockedUserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LockedUserInfo record, @Param("example") LockedUserInfoExample example);

    int updateByExample(@Param("record") LockedUserInfo record, @Param("example") LockedUserInfoExample example);

    int updateByPrimaryKeySelective(LockedUserInfo record);

    int updateByPrimaryKey(LockedUserInfo record);
}