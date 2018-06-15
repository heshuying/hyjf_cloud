package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UserContract;
import com.hyjf.am.user.dao.model.auto.UserContractExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserContractMapper {
    int countByExample(UserContractExample example);

    int deleteByExample(UserContractExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserContract record);

    int insertSelective(UserContract record);

    List<UserContract> selectByExample(UserContractExample example);

    UserContract selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserContract record, @Param("example") UserContractExample example);

    int updateByExample(@Param("record") UserContract record, @Param("example") UserContractExample example);

    int updateByPrimaryKeySelective(UserContract record);

    int updateByPrimaryKey(UserContract record);
}