package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowConsume;
import com.hyjf.am.trade.dao.model.auto.BorrowConsumeExample;
import com.hyjf.am.trade.dao.model.auto.BorrowConsumeWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BorrowConsumeMapper {
    int countByExample(BorrowConsumeExample example);

    int deleteByExample(BorrowConsumeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowConsumeWithBLOBs record);

    int insertSelective(BorrowConsumeWithBLOBs record);

    List<BorrowConsumeWithBLOBs> selectByExampleWithBLOBs(BorrowConsumeExample example);

    List<BorrowConsume> selectByExample(BorrowConsumeExample example);

    BorrowConsumeWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowConsumeWithBLOBs record, @Param("example") BorrowConsumeExample example);

    int updateByExampleWithBLOBs(@Param("record") BorrowConsumeWithBLOBs record, @Param("example") BorrowConsumeExample example);

    int updateByExample(@Param("record") BorrowConsume record, @Param("example") BorrowConsumeExample example);

    int updateByPrimaryKeySelective(BorrowConsumeWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BorrowConsumeWithBLOBs record);

    int updateByPrimaryKey(BorrowConsume record);
}