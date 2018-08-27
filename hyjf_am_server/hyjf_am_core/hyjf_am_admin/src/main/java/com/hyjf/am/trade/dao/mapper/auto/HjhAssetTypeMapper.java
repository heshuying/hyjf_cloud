package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.HjhAssetType;
import com.hyjf.am.trade.dao.model.auto.HjhAssetTypeExample;

public interface HjhAssetTypeMapper {
    int countByExample(HjhAssetTypeExample example);

    int deleteByExample(HjhAssetTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhAssetType record);

    int insertSelective(HjhAssetType record);

    List<HjhAssetType> selectByExample(HjhAssetTypeExample example);

    HjhAssetType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhAssetType record, @Param("example") HjhAssetTypeExample example);

    int updateByExample(@Param("record") HjhAssetType record, @Param("example") HjhAssetTypeExample example);

    int updateByPrimaryKeySelective(HjhAssetType record);

    int updateByPrimaryKey(HjhAssetType record);
}