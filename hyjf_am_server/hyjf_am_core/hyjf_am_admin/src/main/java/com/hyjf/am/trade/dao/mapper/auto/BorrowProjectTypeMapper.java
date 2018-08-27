package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectTypeExample;

public interface BorrowProjectTypeMapper {
    int countByExample(BorrowProjectTypeExample example);

    int deleteByExample(BorrowProjectTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowProjectType record);

    int insertSelective(BorrowProjectType record);

    List<BorrowProjectType> selectByExample(BorrowProjectTypeExample example);

    BorrowProjectType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowProjectType record, @Param("example") BorrowProjectTypeExample example);

    int updateByExample(@Param("record") BorrowProjectType record, @Param("example") BorrowProjectTypeExample example);

    int updateByPrimaryKeySelective(BorrowProjectType record);

    int updateByPrimaryKey(BorrowProjectType record);
}