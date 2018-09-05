package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowConfigMapper {
    int countByExample(BorrowConfigExample example);

    int deleteByExample(BorrowConfigExample example);

    int deleteByPrimaryKey(String configCd);

    int insert(BorrowConfig record);

    int insertSelective(BorrowConfig record);

    List<BorrowConfig> selectByExample(BorrowConfigExample example);

    BorrowConfig selectByPrimaryKey(String configCd);

    int updateByExampleSelective(@Param("record") BorrowConfig record, @Param("example") BorrowConfigExample example);

    int updateByExample(@Param("record") BorrowConfig record, @Param("example") BorrowConfigExample example);

    int updateByPrimaryKeySelective(BorrowConfig record);

    int updateByPrimaryKey(BorrowConfig record);
}