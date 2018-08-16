package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowStyleMapper {
    int countByExample(BorrowStyleExample example);

    int deleteByExample(BorrowStyleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowStyleWithBLOBs record);

    int insertSelective(BorrowStyleWithBLOBs record);

    List<BorrowStyleWithBLOBs> selectByExampleWithBLOBs(BorrowStyleExample example);

    List<BorrowStyle> selectByExample(BorrowStyleExample example);

    BorrowStyleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowStyleWithBLOBs record, @Param("example") BorrowStyleExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowStyleWithBLOBs record, @Param("example") BorrowStyleExample example);

    int updateByExample(@Param("record") BorrowStyle record, @Param("example") BorrowStyleExample example);

    int updateByPrimaryKeySelective(BorrowStyleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BorrowStyleWithBLOBs record);

    int updateByPrimaryKey(BorrowStyle record);
}