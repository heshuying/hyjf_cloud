package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowUsers;
import com.hyjf.am.trade.dao.model.auto.BorrowUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowUsersMapper {
    int countByExample(BorrowUsersExample example);

    int deleteByExample(BorrowUsersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowUsers record);

    int insertSelective(BorrowUsers record);

    List<BorrowUsers> selectByExampleWithBLOBs(BorrowUsersExample example);

    List<BorrowUsers> selectByExample(BorrowUsersExample example);

    BorrowUsers selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowUsers record, @Param("example") BorrowUsersExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowUsers record, @Param("example") BorrowUsersExample example);

    int updateByExample(@Param("record") BorrowUsers record, @Param("example") BorrowUsersExample example);

    int updateByPrimaryKeySelective(BorrowUsers record);

    int updateByPrimaryKeyWithBLOBs(BorrowUsers record);

    int updateByPrimaryKey(BorrowUsers record);
}