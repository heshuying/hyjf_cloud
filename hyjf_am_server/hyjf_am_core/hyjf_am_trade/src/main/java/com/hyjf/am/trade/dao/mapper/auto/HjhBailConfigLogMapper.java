package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhBailConfigLog;
import com.hyjf.am.trade.dao.model.auto.HjhBailConfigLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhBailConfigLogMapper {
    int countByExample(HjhBailConfigLogExample example);

    int deleteByExample(HjhBailConfigLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhBailConfigLog record);

    int insertSelective(HjhBailConfigLog record);

    List<HjhBailConfigLog> selectByExample(HjhBailConfigLogExample example);

    HjhBailConfigLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhBailConfigLog record, @Param("example") HjhBailConfigLogExample example);

    int updateByExample(@Param("record") HjhBailConfigLog record, @Param("example") HjhBailConfigLogExample example);

    int updateByPrimaryKeySelective(HjhBailConfigLog record);

    int updateByPrimaryKey(HjhBailConfigLog record);
}