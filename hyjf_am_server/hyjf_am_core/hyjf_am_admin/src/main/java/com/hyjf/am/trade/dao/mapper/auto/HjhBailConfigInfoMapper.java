package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhBailConfigInfo;
import com.hyjf.am.trade.dao.model.auto.HjhBailConfigInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhBailConfigInfoMapper {
    int countByExample(HjhBailConfigInfoExample example);

    int deleteByExample(HjhBailConfigInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhBailConfigInfo record);

    int insertSelective(HjhBailConfigInfo record);

    List<HjhBailConfigInfo> selectByExample(HjhBailConfigInfoExample example);

    HjhBailConfigInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhBailConfigInfo record, @Param("example") HjhBailConfigInfoExample example);

    int updateByExample(@Param("record") HjhBailConfigInfo record, @Param("example") HjhBailConfigInfoExample example);

    int updateByPrimaryKeySelective(HjhBailConfigInfo record);

    int updateByPrimaryKey(HjhBailConfigInfo record);
}