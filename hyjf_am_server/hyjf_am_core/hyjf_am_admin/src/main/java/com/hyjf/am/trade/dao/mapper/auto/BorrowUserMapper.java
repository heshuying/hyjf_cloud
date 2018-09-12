package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowUser;
import com.hyjf.am.trade.dao.model.auto.BorrowUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowUserMapper {
    int countByExample(BorrowUserExample example);

    int deleteByExample(BorrowUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowUser record);

    int insertSelective(BorrowUser record);

    List<BorrowUser> selectByExampleWithBLOBs(BorrowUserExample example);

    List<BorrowUser> selectByExample(BorrowUserExample example);

    BorrowUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowUser record, @Param("example") BorrowUserExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowUser record, @Param("example") BorrowUserExample example);

    int updateByExample(@Param("record") BorrowUser record, @Param("example") BorrowUserExample example);

    int updateByPrimaryKeySelective(BorrowUser record);

    int updateByPrimaryKeyWithBLOBs(BorrowUser record);

    int updateByPrimaryKey(BorrowUser record);
}