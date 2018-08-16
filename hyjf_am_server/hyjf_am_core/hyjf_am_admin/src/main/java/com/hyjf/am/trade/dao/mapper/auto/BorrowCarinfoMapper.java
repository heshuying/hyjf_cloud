package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowCarinfo;
import com.hyjf.am.trade.dao.model.auto.BorrowCarinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowCarinfoMapper {
    int countByExample(BorrowCarinfoExample example);

    int deleteByExample(BorrowCarinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowCarinfo record);

    int insertSelective(BorrowCarinfo record);

    List<BorrowCarinfo> selectByExample(BorrowCarinfoExample example);

    BorrowCarinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowCarinfo record, @Param("example") BorrowCarinfoExample example);

    int updateByExample(@Param("record") BorrowCarinfo record, @Param("example") BorrowCarinfoExample example);

    int updateByPrimaryKeySelective(BorrowCarinfo record);

    int updateByPrimaryKey(BorrowCarinfo record);
}