package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfigExample;

public interface HjhInstConfigMapper {
    int countByExample(HjhInstConfigExample example);

    int deleteByExample(HjhInstConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhInstConfig record);

    int insertSelective(HjhInstConfig record);

    List<HjhInstConfig> selectByExample(HjhInstConfigExample example);

    HjhInstConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhInstConfig record, @Param("example") HjhInstConfigExample example);

    int updateByExample(@Param("record") HjhInstConfig record, @Param("example") HjhInstConfigExample example);

    int updateByPrimaryKeySelective(HjhInstConfig record);

    int updateByPrimaryKey(HjhInstConfig record);
}