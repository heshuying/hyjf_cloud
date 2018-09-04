package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.AleveErrorLog;
import com.hyjf.am.trade.dao.model.auto.AleveErrorLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AleveErrorLogMapper {
    int countByExample(AleveErrorLogExample example);

    int deleteByExample(AleveErrorLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AleveErrorLog record);

    int insertSelective(AleveErrorLog record);

    List<AleveErrorLog> selectByExample(AleveErrorLogExample example);

    AleveErrorLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AleveErrorLog record, @Param("example") AleveErrorLogExample example);

    int updateByExample(@Param("record") AleveErrorLog record, @Param("example") AleveErrorLogExample example);

    int updateByPrimaryKeySelective(AleveErrorLog record);

    int updateByPrimaryKey(AleveErrorLog record);
}