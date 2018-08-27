package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.ConsumeList;
import com.hyjf.am.trade.dao.model.auto.ConsumeListExample;

public interface ConsumeListMapper {
    int countByExample(ConsumeListExample example);

    int deleteByExample(ConsumeListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumeList record);

    int insertSelective(ConsumeList record);

    List<ConsumeList> selectByExample(ConsumeListExample example);

    ConsumeList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumeList record, @Param("example") ConsumeListExample example);

    int updateByExample(@Param("record") ConsumeList record, @Param("example") ConsumeListExample example);

    int updateByPrimaryKeySelective(ConsumeList record);

    int updateByPrimaryKey(ConsumeList record);
}