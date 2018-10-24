package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubCommissionListConfigMapper {
    int countByExample(SubCommissionListConfigExample example);

    int deleteByExample(SubCommissionListConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SubCommissionListConfig record);

    int insertSelective(SubCommissionListConfig record);

    List<SubCommissionListConfig> selectByExample(SubCommissionListConfigExample example);

    SubCommissionListConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SubCommissionListConfig record, @Param("example") SubCommissionListConfigExample example);

    int updateByExample(@Param("record") SubCommissionListConfig record, @Param("example") SubCommissionListConfigExample example);

    int updateByPrimaryKeySelective(SubCommissionListConfig record);

    int updateByPrimaryKey(SubCommissionListConfig record);
}