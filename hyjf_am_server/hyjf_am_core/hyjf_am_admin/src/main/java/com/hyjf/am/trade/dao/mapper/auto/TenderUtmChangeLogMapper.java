package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.TenderUtmChangeLog;
import com.hyjf.am.trade.dao.model.auto.TenderUtmChangeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenderUtmChangeLogMapper {
    int countByExample(TenderUtmChangeLogExample example);

    int deleteByExample(TenderUtmChangeLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TenderUtmChangeLog record);

    int insertSelective(TenderUtmChangeLog record);

    List<TenderUtmChangeLog> selectByExample(TenderUtmChangeLogExample example);

    TenderUtmChangeLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TenderUtmChangeLog record, @Param("example") TenderUtmChangeLogExample example);

    int updateByExample(@Param("record") TenderUtmChangeLog record, @Param("example") TenderUtmChangeLogExample example);

    int updateByPrimaryKeySelective(TenderUtmChangeLog record);

    int updateByPrimaryKey(TenderUtmChangeLog record);
}