package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.SpreadsLog;
import com.hyjf.am.user.dao.model.auto.SpreadsLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SpreadsLogMapper {
    int countByExample(SpreadsLogExample example);

    int deleteByExample(SpreadsLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpreadsLog record);

    int insertSelective(SpreadsLog record);

    List<SpreadsLog> selectByExample(SpreadsLogExample example);

    SpreadsLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpreadsLog record, @Param("example") SpreadsLogExample example);

    int updateByExample(@Param("record") SpreadsLog record, @Param("example") SpreadsLogExample example);

    int updateByPrimaryKeySelective(SpreadsLog record);

    int updateByPrimaryKey(SpreadsLog record);
}