package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowRepayMapper {
    int countByExample(BorrowRepayExample example);

    int deleteByExample(BorrowRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowRepay record);

    int insertSelective(BorrowRepay record);

    List<BorrowRepay> selectByExample(BorrowRepayExample example);

    BorrowRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowRepay record, @Param("example") BorrowRepayExample example);

    int updateByExample(@Param("record") BorrowRepay record, @Param("example") BorrowRepayExample example);

    int updateByPrimaryKeySelective(BorrowRepay record);

    int updateByPrimaryKey(BorrowRepay record);
}