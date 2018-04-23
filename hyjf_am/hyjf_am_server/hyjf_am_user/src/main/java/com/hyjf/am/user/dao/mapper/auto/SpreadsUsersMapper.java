package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.SpreadsUsers;
import com.hyjf.am.user.dao.model.auto.SpreadsUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpreadsUsersMapper {
    int countByExample(SpreadsUsersExample example);

    int deleteByExample(SpreadsUsersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpreadsUsers record);

    int insertSelective(SpreadsUsers record);

    List<SpreadsUsers> selectByExampleWithBLOBs(SpreadsUsersExample example);

    List<SpreadsUsers> selectByExample(SpreadsUsersExample example);

    SpreadsUsers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpreadsUsers record, @Param("example") SpreadsUsersExample example);

    int updateByExampleWithBLOBs(@Param("record") SpreadsUsers record, @Param("example") SpreadsUsersExample example);

    int updateByExample(@Param("record") SpreadsUsers record, @Param("example") SpreadsUsersExample example);

    int updateByPrimaryKeySelective(SpreadsUsers record);

    int updateByPrimaryKeyWithBLOBs(SpreadsUsers record);

    int updateByPrimaryKey(SpreadsUsers record);
}