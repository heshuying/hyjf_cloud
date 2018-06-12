package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.HjhAssetRiskInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetRiskInfoExample;
import com.hyjf.am.trade.dao.model.auto.HjhAssetRiskInfoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhAssetRiskInfoMapper {
    int countByExample(HjhAssetRiskInfoExample example);

    int deleteByExample(HjhAssetRiskInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhAssetRiskInfoWithBLOBs record);

    int insertSelective(HjhAssetRiskInfoWithBLOBs record);

    List<HjhAssetRiskInfoWithBLOBs> selectByExampleWithBLOBs(HjhAssetRiskInfoExample example);

    List<HjhAssetRiskInfo> selectByExample(HjhAssetRiskInfoExample example);

    HjhAssetRiskInfoWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhAssetRiskInfoWithBLOBs record, @Param("example") HjhAssetRiskInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") HjhAssetRiskInfoWithBLOBs record, @Param("example") HjhAssetRiskInfoExample example);

    int updateByExample(@Param("record") HjhAssetRiskInfo record, @Param("example") HjhAssetRiskInfoExample example);

    int updateByPrimaryKeySelective(HjhAssetRiskInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(HjhAssetRiskInfoWithBLOBs record);

    int updateByPrimaryKey(HjhAssetRiskInfo record);
}