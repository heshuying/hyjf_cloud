package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowDelete;
import com.hyjf.am.trade.dao.model.auto.BorrowDeleteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowDeleteMapper {
    int countByExample(BorrowDeleteExample example);

    int deleteByExample(BorrowDeleteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowDelete record);

    int insertSelective(BorrowDelete record);

    List<BorrowDelete> selectByExample(BorrowDeleteExample example);

    BorrowDelete selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowDelete record, @Param("example") BorrowDeleteExample example);

    int updateByExample(@Param("record") BorrowDelete record, @Param("example") BorrowDeleteExample example);

    int updateByPrimaryKeySelective(BorrowDelete record);

    int updateByPrimaryKey(BorrowDelete record);
}