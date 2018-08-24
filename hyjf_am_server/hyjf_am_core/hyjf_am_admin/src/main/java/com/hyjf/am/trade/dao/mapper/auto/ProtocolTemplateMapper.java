package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.ProtocolTemplate;
import com.hyjf.am.trade.dao.model.auto.ProtocolTemplateExample;

public interface ProtocolTemplateMapper {
    int countByExample(ProtocolTemplateExample example);

    int deleteByExample(ProtocolTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProtocolTemplate record);

    int insertSelective(ProtocolTemplate record);

    List<ProtocolTemplate> selectByExample(ProtocolTemplateExample example);

    ProtocolTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProtocolTemplate record, @Param("example") ProtocolTemplateExample example);

    int updateByExample(@Param("record") ProtocolTemplate record, @Param("example") ProtocolTemplateExample example);

    int updateByPrimaryKeySelective(ProtocolTemplate record);

    int updateByPrimaryKey(ProtocolTemplate record);
}