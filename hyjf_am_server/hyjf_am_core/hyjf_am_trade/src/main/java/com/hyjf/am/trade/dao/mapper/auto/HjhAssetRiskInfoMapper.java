package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhAssetRiskInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetRiskInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhAssetRiskInfoMapper {
    int countByExample(HjhAssetRiskInfoExample example);

    int deleteByExample(HjhAssetRiskInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhAssetRiskInfo record);

    int insertSelective(HjhAssetRiskInfo record);

    List<HjhAssetRiskInfo> selectByExample(HjhAssetRiskInfoExample example);

    HjhAssetRiskInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhAssetRiskInfo record, @Param("example") HjhAssetRiskInfoExample example);

    int updateByExample(@Param("record") HjhAssetRiskInfo record, @Param("example") HjhAssetRiskInfoExample example);

    int updateByPrimaryKeySelective(HjhAssetRiskInfo record);

    int updateByPrimaryKey(HjhAssetRiskInfo record);
}