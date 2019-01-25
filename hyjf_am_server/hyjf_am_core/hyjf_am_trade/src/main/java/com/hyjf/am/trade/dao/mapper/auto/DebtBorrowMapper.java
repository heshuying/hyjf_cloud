package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.DebtBorrow;
import com.hyjf.am.trade.dao.model.auto.DebtBorrowExample;
import com.hyjf.am.trade.dao.model.auto.DebtBorrowWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DebtBorrowMapper {
    int countByExample(DebtBorrowExample example);

    int deleteByExample(DebtBorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtBorrowWithBLOBs record);

    int insertSelective(DebtBorrowWithBLOBs record);

    List<DebtBorrowWithBLOBs> selectByExampleWithBLOBs(DebtBorrowExample example);

    List<DebtBorrow> selectByExample(DebtBorrowExample example);

    DebtBorrowWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtBorrowWithBLOBs record, @Param("example") DebtBorrowExample example);

    int updateByExampleWithBLOBs(@Param("record") DebtBorrowWithBLOBs record, @Param("example") DebtBorrowExample example);

    int updateByExample(@Param("record") DebtBorrow record, @Param("example") DebtBorrowExample example);

    int updateByPrimaryKeySelective(DebtBorrowWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DebtBorrowWithBLOBs record);

    int updateByPrimaryKey(DebtBorrow record);
}