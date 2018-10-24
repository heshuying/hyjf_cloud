package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SellDailyDistribution;
import com.hyjf.am.config.dao.model.auto.SellDailyDistributionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SellDailyDistributionMapper {
    int countByExample(SellDailyDistributionExample example);

    int deleteByExample(SellDailyDistributionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SellDailyDistribution record);

    int insertSelective(SellDailyDistribution record);

    List<SellDailyDistribution> selectByExample(SellDailyDistributionExample example);

    SellDailyDistribution selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SellDailyDistribution record, @Param("example") SellDailyDistributionExample example);

    int updateByExample(@Param("record") SellDailyDistribution record, @Param("example") SellDailyDistributionExample example);

    int updateByPrimaryKeySelective(SellDailyDistribution record);

    int updateByPrimaryKey(SellDailyDistribution record);
}