package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowHouses;
import com.hyjf.am.trade.dao.model.auto.BorrowHousesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowHousesMapper {
    int countByExample(BorrowHousesExample example);

    int deleteByExample(BorrowHousesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowHouses record);

    int insertSelective(BorrowHouses record);

    List<BorrowHouses> selectByExample(BorrowHousesExample example);

    BorrowHouses selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowHouses record, @Param("example") BorrowHousesExample example);

    int updateByExample(@Param("record") BorrowHouses record, @Param("example") BorrowHousesExample example);

    int updateByPrimaryKeySelective(BorrowHouses record);

    int updateByPrimaryKey(BorrowHouses record);
}