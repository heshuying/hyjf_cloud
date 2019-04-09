package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.UserOperateList;
import com.hyjf.am.trade.dao.model.auto.UserOperateListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserOperateListMapper {
    int countByExample(UserOperateListExample example);

    int deleteByExample(UserOperateListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserOperateList record);

    int insertSelective(UserOperateList record);

    List<UserOperateList> selectByExample(UserOperateListExample example);

    UserOperateList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserOperateList record, @Param("example") UserOperateListExample example);

    int updateByExample(@Param("record") UserOperateList record, @Param("example") UserOperateListExample example);

    int updateByPrimaryKeySelective(UserOperateList record);

    int updateByPrimaryKey(UserOperateList record);
}