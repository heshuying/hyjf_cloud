package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UserEvalationResult;
import com.hyjf.am.user.dao.model.auto.UserEvalationResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserEvalationResultMapper {
    int countByExample(UserEvalationResultExample example);

    int deleteByExample(UserEvalationResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserEvalationResult record);

    int insertSelective(UserEvalationResult record);

    List<UserEvalationResult> selectByExample(UserEvalationResultExample example);

    UserEvalationResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserEvalationResult record, @Param("example") UserEvalationResultExample example);

    int updateByExample(@Param("record") UserEvalationResult record, @Param("example") UserEvalationResultExample example);

    int updateByPrimaryKeySelective(UserEvalationResult record);

    int updateByPrimaryKey(UserEvalationResult record);
}