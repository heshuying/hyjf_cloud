package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.TenderCommission;
import com.hyjf.am.trade.dao.model.auto.TenderCommissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenderCommissionMapper {
    int countByExample(TenderCommissionExample example);

    int deleteByExample(TenderCommissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TenderCommission record);

    int insertSelective(TenderCommission record);

    List<TenderCommission> selectByExample(TenderCommissionExample example);

    TenderCommission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TenderCommission record, @Param("example") TenderCommissionExample example);

    int updateByExample(@Param("record") TenderCommission record, @Param("example") TenderCommissionExample example);

    int updateByPrimaryKeySelective(TenderCommission record);

    int updateByPrimaryKey(TenderCommission record);
}