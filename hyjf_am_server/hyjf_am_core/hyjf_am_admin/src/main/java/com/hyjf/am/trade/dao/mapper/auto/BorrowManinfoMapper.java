package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowManinfoMapper {
    int countByExample(BorrowManinfoExample example);

    int deleteByExample(BorrowManinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowManinfo record);

    int insertSelective(BorrowManinfo record);

    List<BorrowManinfo> selectByExample(BorrowManinfoExample example);

    BorrowManinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowManinfo record, @Param("example") BorrowManinfoExample example);

    int updateByExample(@Param("record") BorrowManinfo record, @Param("example") BorrowManinfoExample example);

    int updateByPrimaryKeySelective(BorrowManinfo record);

    int updateByPrimaryKey(BorrowManinfo record);
}