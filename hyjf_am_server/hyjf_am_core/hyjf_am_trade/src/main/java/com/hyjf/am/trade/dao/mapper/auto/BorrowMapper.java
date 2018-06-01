package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.borrow.dao.model.auto.Borrow;
import com.hyjf.am.borrow.dao.model.auto.BorrowExample;
import com.hyjf.am.borrow.dao.model.auto.BorrowWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowMapper {
    int countByExample(BorrowExample example);

    int deleteByExample(BorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowWithBLOBs record);

    int insertSelective(BorrowWithBLOBs record);

    List<BorrowWithBLOBs> selectByExampleWithBLOBs(BorrowExample example);

    List<Borrow> selectByExample(BorrowExample example);

    BorrowWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowWithBLOBs record, @Param("example") BorrowExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowWithBLOBs record, @Param("example") BorrowExample example);

    int updateByExample(@Param("record") Borrow record, @Param("example") BorrowExample example);

    int updateByPrimaryKeySelective(BorrowWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BorrowWithBLOBs record);

    int updateByPrimaryKey(Borrow record);
}