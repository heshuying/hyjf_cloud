package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StzhWhiteListMapper {
    int countByExample(StzhWhiteListExample example);

    int deleteByExample(StzhWhiteListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StzhWhiteList record);

    int insertSelective(StzhWhiteList record);

    List<StzhWhiteList> selectByExample(StzhWhiteListExample example);

    StzhWhiteList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StzhWhiteList record, @Param("example") StzhWhiteListExample example);

    int updateByExample(@Param("record") StzhWhiteList record, @Param("example") StzhWhiteListExample example);

    int updateByPrimaryKeySelective(StzhWhiteList record);

    int updateByPrimaryKey(StzhWhiteList record);
}