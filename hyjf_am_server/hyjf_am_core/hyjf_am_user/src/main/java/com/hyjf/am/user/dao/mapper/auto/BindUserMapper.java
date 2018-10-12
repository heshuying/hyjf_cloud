package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.BindUser;
import com.hyjf.am.user.dao.model.auto.BindUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BindUserMapper {
    int countByExample(BindUserExample example);

    int deleteByExample(BindUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BindUser record);

    int insertSelective(BindUser record);

    List<BindUser> selectByExample(BindUserExample example);

    BindUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BindUser record, @Param("example") BindUserExample example);

    int updateByExample(@Param("record") BindUser record, @Param("example") BindUserExample example);

    int updateByPrimaryKeySelective(BindUser record);

    int updateByPrimaryKey(BindUser record);
}