package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.CardBin;
import com.hyjf.am.config.dao.model.auto.CardBinExample;

public interface CardBinMapper {
    int countByExample(CardBinExample example);

    int deleteByExample(CardBinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CardBin record);

    int insertSelective(CardBin record);

    List<CardBin> selectByExample(CardBinExample example);

    CardBin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CardBin record, @Param("example") CardBinExample example);

    int updateByExample(@Param("record") CardBin record, @Param("example") CardBinExample example);

    int updateByPrimaryKeySelective(CardBin record);

    int updateByPrimaryKey(CardBin record);
}