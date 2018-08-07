package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtFreeze;
import com.hyjf.am.trade.dao.model.auto.DebtFreezeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtFreezeMapper {
    int countByExample(DebtFreezeExample example);

    int deleteByExample(DebtFreezeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtFreeze record);

    int insertSelective(DebtFreeze record);

    List<DebtFreeze> selectByExample(DebtFreezeExample example);

    DebtFreeze selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtFreeze record, @Param("example") DebtFreezeExample example);

    int updateByExample(@Param("record") DebtFreeze record, @Param("example") DebtFreezeExample example);

    int updateByPrimaryKeySelective(DebtFreeze record);

    int updateByPrimaryKey(DebtFreeze record);
}