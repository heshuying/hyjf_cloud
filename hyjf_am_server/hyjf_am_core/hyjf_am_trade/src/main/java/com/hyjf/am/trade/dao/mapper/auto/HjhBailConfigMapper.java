package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhBailConfig;
import com.hyjf.am.trade.dao.model.auto.HjhBailConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhBailConfigMapper {
    int countByExample(HjhBailConfigExample example);

    int deleteByExample(HjhBailConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhBailConfig record);

    int insertSelective(HjhBailConfig record);

    List<HjhBailConfig> selectByExample(HjhBailConfigExample example);

    HjhBailConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhBailConfig record, @Param("example") HjhBailConfigExample example);

    int updateByExample(@Param("record") HjhBailConfig record, @Param("example") HjhBailConfigExample example);

    int updateByPrimaryKeySelective(HjhBailConfig record);

    int updateByPrimaryKey(HjhBailConfig record);
}