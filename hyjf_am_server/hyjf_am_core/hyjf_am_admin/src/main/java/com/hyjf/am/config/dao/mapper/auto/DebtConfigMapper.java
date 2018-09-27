package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.DebtConfig;
import com.hyjf.am.config.dao.model.auto.DebtConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtConfigMapper {
    int countByExample(DebtConfigExample example);

    int deleteByExample(DebtConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtConfig record);

    int insertSelective(DebtConfig record);

    List<DebtConfig> selectByExample(DebtConfigExample example);

    DebtConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtConfig record, @Param("example") DebtConfigExample example);

    int updateByExample(@Param("record") DebtConfig record, @Param("example") DebtConfigExample example);

    int updateByPrimaryKeySelective(DebtConfig record);

    int updateByPrimaryKey(DebtConfig record);
}