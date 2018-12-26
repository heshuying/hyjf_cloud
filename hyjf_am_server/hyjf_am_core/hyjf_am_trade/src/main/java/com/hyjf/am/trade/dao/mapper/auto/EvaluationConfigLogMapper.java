package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.EvaluationConfigLog;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EvaluationConfigLogMapper {
    int countByExample(EvaluationConfigLogExample example);

    int deleteByExample(EvaluationConfigLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EvaluationConfigLog record);

    int insertSelective(EvaluationConfigLog record);

    List<EvaluationConfigLog> selectByExample(EvaluationConfigLogExample example);

    EvaluationConfigLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EvaluationConfigLog record, @Param("example") EvaluationConfigLogExample example);

    int updateByExample(@Param("record") EvaluationConfigLog record, @Param("example") EvaluationConfigLogExample example);

    int updateByPrimaryKeySelective(EvaluationConfigLog record);

    int updateByPrimaryKey(EvaluationConfigLog record);
}