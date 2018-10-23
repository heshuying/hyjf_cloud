package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowApicronExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowApicronMapper {
    int countByExample(BorrowApicronExample example);

    int deleteByExample(BorrowApicronExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowApicron record);

    int insertSelective(BorrowApicron record);

    List<BorrowApicron> selectByExampleWithBLOBs(BorrowApicronExample example);

    List<BorrowApicron> selectByExample(BorrowApicronExample example);

    BorrowApicron selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowApicron record, @Param("example") BorrowApicronExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowApicron record, @Param("example") BorrowApicronExample example);

    int updateByExample(@Param("record") BorrowApicron record, @Param("example") BorrowApicronExample example);

    int updateByPrimaryKeySelective(BorrowApicron record);

    int updateByPrimaryKeyWithBLOBs(BorrowApicron record);

    int updateByPrimaryKey(BorrowApicron record);
}