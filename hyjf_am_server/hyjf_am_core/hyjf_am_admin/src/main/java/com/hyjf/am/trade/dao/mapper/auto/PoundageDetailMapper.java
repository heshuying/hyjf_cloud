package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.PoundageDetail;
import com.hyjf.am.trade.dao.model.auto.PoundageDetailExample;

public interface PoundageDetailMapper {
    int countByExample(PoundageDetailExample example);

    int deleteByExample(PoundageDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PoundageDetail record);

    int insertSelective(PoundageDetail record);

    List<PoundageDetail> selectByExample(PoundageDetailExample example);

    PoundageDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PoundageDetail record, @Param("example") PoundageDetailExample example);

    int updateByExample(@Param("record") PoundageDetail record, @Param("example") PoundageDetailExample example);

    int updateByPrimaryKeySelective(PoundageDetail record);

    int updateByPrimaryKey(PoundageDetail record);
}