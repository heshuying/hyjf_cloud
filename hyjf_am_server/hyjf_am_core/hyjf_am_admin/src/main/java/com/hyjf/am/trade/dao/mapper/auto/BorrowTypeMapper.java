package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowType;
import com.hyjf.am.trade.dao.model.auto.BorrowTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowTypeMapper {
    int countByExample(BorrowTypeExample example);

    int deleteByExample(BorrowTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowType record);

    int insertSelective(BorrowType record);

    List<BorrowType> selectByExample(BorrowTypeExample example);

    BorrowType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowType record, @Param("example") BorrowTypeExample example);

    int updateByExample(@Param("record") BorrowType record, @Param("example") BorrowTypeExample example);

    int updateByPrimaryKeySelective(BorrowType record);

    int updateByPrimaryKey(BorrowType record);
}