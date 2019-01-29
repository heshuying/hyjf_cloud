package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.DebtConfigLog;
import com.hyjf.am.config.dao.model.auto.DebtConfigLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtConfigLogMapper {
    int countByExample(DebtConfigLogExample example);

    int deleteByExample(DebtConfigLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtConfigLog record);

    int insertSelective(DebtConfigLog record);

    List<DebtConfigLog> selectByExample(DebtConfigLogExample example);

    DebtConfigLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtConfigLog record, @Param("example") DebtConfigLogExample example);

    int updateByExample(@Param("record") DebtConfigLog record, @Param("example") DebtConfigLogExample example);

    int updateByPrimaryKeySelective(DebtConfigLog record);

    int updateByPrimaryKey(DebtConfigLog record);
}