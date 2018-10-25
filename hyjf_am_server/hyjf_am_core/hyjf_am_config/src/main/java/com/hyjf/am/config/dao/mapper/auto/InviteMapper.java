package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.Invite;
import com.hyjf.am.config.dao.model.auto.InviteExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InviteMapper {
    int countByExample(InviteExample example);

    int deleteByExample(InviteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Invite record);

    int insertSelective(Invite record);

    List<Invite> selectByExample(InviteExample example);

    Invite selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Invite record, @Param("example") InviteExample example);

    int updateByExample(@Param("record") Invite record, @Param("example") InviteExample example);

    int updateByPrimaryKeySelective(Invite record);

    int updateByPrimaryKey(Invite record);
}