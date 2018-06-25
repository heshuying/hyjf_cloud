package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtCommissionConfig;
import com.hyjf.am.trade.dao.model.auto.DebtCommissionConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtCommissionConfigMapper {
    int countByExample(DebtCommissionConfigExample example);

    int deleteByExample(DebtCommissionConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtCommissionConfig record);

    int insertSelective(DebtCommissionConfig record);

    List<DebtCommissionConfig> selectByExample(DebtCommissionConfigExample example);

    DebtCommissionConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtCommissionConfig record, @Param("example") DebtCommissionConfigExample example);

    int updateByExample(@Param("record") DebtCommissionConfig record, @Param("example") DebtCommissionConfigExample example);

    int updateByPrimaryKeySelective(DebtCommissionConfig record);

    int updateByPrimaryKey(DebtCommissionConfig record);
}