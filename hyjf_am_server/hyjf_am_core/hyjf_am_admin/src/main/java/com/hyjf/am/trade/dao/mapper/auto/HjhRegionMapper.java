package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.HjhRegion;
import com.hyjf.am.trade.dao.model.auto.HjhRegionExample;

public interface HjhRegionMapper {
    int countByExample(HjhRegionExample example);

    int deleteByExample(HjhRegionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhRegion record);

    int insertSelective(HjhRegion record);

    List<HjhRegion> selectByExample(HjhRegionExample example);

    HjhRegion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhRegion record, @Param("example") HjhRegionExample example);

    int updateByExample(@Param("record") HjhRegion record, @Param("example") HjhRegionExample example);

    int updateByPrimaryKeySelective(HjhRegion record);

    int updateByPrimaryKey(HjhRegion record);
}