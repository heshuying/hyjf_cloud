package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowBail;
import com.hyjf.am.trade.dao.model.auto.BorrowBailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowBailMapper {
    int countByExample(BorrowBailExample example);

    int deleteByExample(BorrowBailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowBail record);

    int insertSelective(BorrowBail record);

    List<BorrowBail> selectByExample(BorrowBailExample example);

    BorrowBail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowBail record, @Param("example") BorrowBailExample example);

    int updateByExample(@Param("record") BorrowBail record, @Param("example") BorrowBailExample example);

    int updateByPrimaryKeySelective(BorrowBail record);

    int updateByPrimaryKey(BorrowBail record);
}