package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.FeerateModifyLog;
import com.hyjf.am.config.dao.model.auto.FeerateModifyLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeerateModifyLogMapper {
    int countByExample(FeerateModifyLogExample example);

    int deleteByExample(FeerateModifyLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FeerateModifyLog record);

    int insertSelective(FeerateModifyLog record);

    List<FeerateModifyLog> selectByExample(FeerateModifyLogExample example);

    FeerateModifyLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FeerateModifyLog record, @Param("example") FeerateModifyLogExample example);

    int updateByExample(@Param("record") FeerateModifyLog record, @Param("example") FeerateModifyLogExample example);

    int updateByPrimaryKeySelective(FeerateModifyLog record);

    int updateByPrimaryKey(FeerateModifyLog record);
}