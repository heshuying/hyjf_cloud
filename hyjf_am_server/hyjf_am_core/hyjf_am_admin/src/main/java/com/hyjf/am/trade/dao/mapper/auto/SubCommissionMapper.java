package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.SubCommission;
import com.hyjf.am.trade.dao.model.auto.SubCommissionExample;

public interface SubCommissionMapper {
    int countByExample(SubCommissionExample example);

    int deleteByExample(SubCommissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubCommission record);

    int insertSelective(SubCommission record);

    List<SubCommission> selectByExample(SubCommissionExample example);

    SubCommission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubCommission record, @Param("example") SubCommissionExample example);

    int updateByExample(@Param("record") SubCommission record, @Param("example") SubCommissionExample example);

    int updateByPrimaryKeySelective(SubCommission record);

    int updateByPrimaryKey(SubCommission record);
}