package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.HjhLabel;
import com.hyjf.am.trade.dao.model.auto.HjhLabelExample;

public interface HjhLabelMapper {
    int countByExample(HjhLabelExample example);

    int deleteByExample(HjhLabelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhLabel record);

    int insertSelective(HjhLabel record);

    List<HjhLabel> selectByExample(HjhLabelExample example);

    HjhLabel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhLabel record, @Param("example") HjhLabelExample example);

    int updateByExample(@Param("record") HjhLabel record, @Param("example") HjhLabelExample example);

    int updateByPrimaryKeySelective(HjhLabel record);

    int updateByPrimaryKey(HjhLabel record);
}