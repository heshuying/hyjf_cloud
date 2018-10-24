package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowLog;
import com.hyjf.am.trade.dao.model.auto.BorrowLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowLogMapper {
    int countByExample(BorrowLogExample example);

    int deleteByExample(BorrowLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowLog record);

    int insertSelective(BorrowLog record);

    List<BorrowLog> selectByExample(BorrowLogExample example);

    BorrowLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowLog record, @Param("example") BorrowLogExample example);

    int updateByExample(@Param("record") BorrowLog record, @Param("example") BorrowLogExample example);

    int updateByPrimaryKeySelective(BorrowLog record);

    int updateByPrimaryKey(BorrowLog record);
}