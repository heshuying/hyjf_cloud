package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.UtmSource;
import com.hyjf.am.user.dao.model.auto.UtmSourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UtmSourceMapper {
    int countByExample(UtmSourceExample example);

    int deleteByExample(UtmSourceExample example);

    int deleteByPrimaryKey(Integer sourceId);

    int insert(UtmSource record);

    int insertSelective(UtmSource record);

    List<UtmSource> selectByExample(UtmSourceExample example);

    UtmSource selectByPrimaryKey(Integer sourceId);

    int updateByExampleSelective(@Param("record") UtmSource record, @Param("example") UtmSourceExample example);

    int updateByExample(@Param("record") UtmSource record, @Param("example") UtmSourceExample example);

    int updateByPrimaryKeySelective(UtmSource record);

    int updateByPrimaryKey(UtmSource record);
}