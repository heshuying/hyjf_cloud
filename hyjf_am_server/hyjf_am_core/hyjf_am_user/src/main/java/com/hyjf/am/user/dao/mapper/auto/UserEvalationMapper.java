package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UserEvalation;
import com.hyjf.am.user.dao.model.auto.UserEvalationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserEvalationMapper {
    int countByExample(UserEvalationExample example);

    int deleteByExample(UserEvalationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserEvalation record);

    int insertSelective(UserEvalation record);

    List<UserEvalation> selectByExample(UserEvalationExample example);

    UserEvalation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserEvalation record, @Param("example") UserEvalationExample example);

    int updateByExample(@Param("record") UserEvalation record, @Param("example") UserEvalationExample example);

    int updateByPrimaryKeySelective(UserEvalation record);

    int updateByPrimaryKey(UserEvalation record);
}