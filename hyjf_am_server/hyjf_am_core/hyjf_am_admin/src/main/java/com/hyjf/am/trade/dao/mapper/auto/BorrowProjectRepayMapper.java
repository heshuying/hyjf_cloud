package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.BorrowProjectRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectRepayExample;

public interface BorrowProjectRepayMapper {
    int countByExample(BorrowProjectRepayExample example);

    int deleteByExample(BorrowProjectRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowProjectRepay record);

    int insertSelective(BorrowProjectRepay record);

    List<BorrowProjectRepay> selectByExample(BorrowProjectRepayExample example);

    BorrowProjectRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowProjectRepay record, @Param("example") BorrowProjectRepayExample example);

    int updateByExample(@Param("record") BorrowProjectRepay record, @Param("example") BorrowProjectRepayExample example);

    int updateByPrimaryKeySelective(BorrowProjectRepay record);

    int updateByPrimaryKey(BorrowProjectRepay record);
}