package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtDeleteLog;
import com.hyjf.am.trade.dao.model.auto.DebtDeleteLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtDeleteLogMapper {
    int countByExample(DebtDeleteLogExample example);

    int deleteByExample(DebtDeleteLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtDeleteLog record);

    int insertSelective(DebtDeleteLog record);

    List<DebtDeleteLog> selectByExample(DebtDeleteLogExample example);

    DebtDeleteLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtDeleteLog record, @Param("example") DebtDeleteLogExample example);

    int updateByExample(@Param("record") DebtDeleteLog record, @Param("example") DebtDeleteLogExample example);

    int updateByPrimaryKeySelective(DebtDeleteLog record);

    int updateByPrimaryKey(DebtDeleteLog record);
}