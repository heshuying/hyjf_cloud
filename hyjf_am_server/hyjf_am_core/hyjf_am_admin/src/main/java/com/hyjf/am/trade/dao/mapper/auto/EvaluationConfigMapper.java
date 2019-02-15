package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.EvaluationConfig;
import com.hyjf.am.trade.dao.model.auto.EvaluationConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EvaluationConfigMapper {
    int countByExample(EvaluationConfigExample example);

    int deleteByExample(EvaluationConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EvaluationConfig record);

    int insertSelective(EvaluationConfig record);

    List<EvaluationConfig> selectByExample(EvaluationConfigExample example);

    EvaluationConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EvaluationConfig record, @Param("example") EvaluationConfigExample example);

    int updateByExample(@Param("record") EvaluationConfig record, @Param("example") EvaluationConfigExample example);

    int updateByPrimaryKeySelective(EvaluationConfig record);

    int updateByPrimaryKey(EvaluationConfig record);
}