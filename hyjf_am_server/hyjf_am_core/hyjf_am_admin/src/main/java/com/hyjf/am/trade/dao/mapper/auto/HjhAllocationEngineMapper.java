package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.HjhAllocationEngine;
import com.hyjf.am.trade.dao.model.auto.HjhAllocationEngineExample;

public interface HjhAllocationEngineMapper {
    int countByExample(HjhAllocationEngineExample example);

    int deleteByExample(HjhAllocationEngineExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhAllocationEngine record);

    int insertSelective(HjhAllocationEngine record);

    List<HjhAllocationEngine> selectByExample(HjhAllocationEngineExample example);

    HjhAllocationEngine selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhAllocationEngine record, @Param("example") HjhAllocationEngineExample example);

    int updateByExample(@Param("record") HjhAllocationEngine record, @Param("example") HjhAllocationEngineExample example);

    int updateByPrimaryKeySelective(HjhAllocationEngine record);

    int updateByPrimaryKey(HjhAllocationEngine record);
}