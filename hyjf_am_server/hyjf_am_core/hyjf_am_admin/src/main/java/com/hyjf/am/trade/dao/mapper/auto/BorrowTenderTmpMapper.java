package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmp;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowTenderTmpMapper {
    int countByExample(BorrowTenderTmpExample example);

    int deleteByExample(BorrowTenderTmpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowTenderTmp record);

    int insertSelective(BorrowTenderTmp record);

    List<BorrowTenderTmp> selectByExample(BorrowTenderTmpExample example);

    BorrowTenderTmp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowTenderTmp record, @Param("example") BorrowTenderTmpExample example);

    int updateByExample(@Param("record") BorrowTenderTmp record, @Param("example") BorrowTenderTmpExample example);

    int updateByPrimaryKeySelective(BorrowTenderTmp record);

    int updateByPrimaryKey(BorrowTenderTmp record);
}