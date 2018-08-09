package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoExample;
import com.hyjf.am.trade.dao.model.auto.BorrowInfoWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowInfoMapper {
    int countByExample(BorrowInfoExample example);

    int deleteByExample(BorrowInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowInfoWithBLOBs record);

    int insertSelective(BorrowInfoWithBLOBs record);

    List<BorrowInfoWithBLOBs> selectByExampleWithBLOBs(BorrowInfoExample example);

    List<BorrowInfo> selectByExample(BorrowInfoExample example);

    BorrowInfoWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowInfoWithBLOBs record, @Param("example") BorrowInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowInfoWithBLOBs record, @Param("example") BorrowInfoExample example);

    int updateByExample(@Param("record") BorrowInfo record, @Param("example") BorrowInfoExample example);

    int updateByPrimaryKeySelective(BorrowInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BorrowInfoWithBLOBs record);

    int updateByPrimaryKey(BorrowInfo record);
}