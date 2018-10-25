package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.SpreadsUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpreadsUserMapper {
    int countByExample(SpreadsUserExample example);

    int deleteByExample(SpreadsUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpreadsUser record);

    int insertSelective(SpreadsUser record);

    List<SpreadsUser> selectByExample(SpreadsUserExample example);

    SpreadsUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpreadsUser record, @Param("example") SpreadsUserExample example);

    int updateByExample(@Param("record") SpreadsUser record, @Param("example") SpreadsUserExample example);

    int updateByPrimaryKeySelective(SpreadsUser record);

    int updateByPrimaryKey(SpreadsUser record);
}