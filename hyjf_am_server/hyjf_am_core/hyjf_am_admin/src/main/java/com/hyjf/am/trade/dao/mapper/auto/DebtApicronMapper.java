package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtApicron;
import com.hyjf.am.trade.dao.model.auto.DebtApicronExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtApicronMapper {
    int countByExample(DebtApicronExample example);

    int deleteByExample(DebtApicronExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtApicron record);

    int insertSelective(DebtApicron record);

    List<DebtApicron> selectByExampleWithBLOBs(DebtApicronExample example);

    List<DebtApicron> selectByExample(DebtApicronExample example);

    DebtApicron selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtApicron record, @Param("example") DebtApicronExample example);

    int updateByExampleWithBLOBs(@Param("record") DebtApicron record, @Param("example") DebtApicronExample example);

    int updateByExample(@Param("record") DebtApicron record, @Param("example") DebtApicronExample example);

    int updateByPrimaryKeySelective(DebtApicron record);

    int updateByPrimaryKeyWithBLOBs(DebtApicron record);

    int updateByPrimaryKey(DebtApicron record);
}