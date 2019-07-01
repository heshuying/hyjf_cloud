package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowApicronLog;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowApicronLogMapper {
    int countByExample(BorrowApicronLogExample example);

    int deleteByExample(BorrowApicronLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowApicronLog record);

    int insertSelective(BorrowApicronLog record);

    List<BorrowApicronLog> selectByExampleWithBLOBs(BorrowApicronLogExample example);

    List<BorrowApicronLog> selectByExample(BorrowApicronLogExample example);

    BorrowApicronLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowApicronLog record, @Param("example") BorrowApicronLogExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowApicronLog record, @Param("example") BorrowApicronLogExample example);

    int updateByExample(@Param("record") BorrowApicronLog record, @Param("example") BorrowApicronLogExample example);

    int updateByPrimaryKeySelective(BorrowApicronLog record);

    int updateByPrimaryKeyWithBLOBs(BorrowApicronLog record);

    int updateByPrimaryKey(BorrowApicronLog record);
}