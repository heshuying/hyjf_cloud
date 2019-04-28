package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.CertBorrow;
import com.hyjf.am.trade.dao.model.auto.CertBorrowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CertBorrowMapper {
    int countByExample(CertBorrowExample example);

    int deleteByExample(CertBorrowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertBorrow record);

    int insertSelective(CertBorrow record);

    List<CertBorrow> selectByExample(CertBorrowExample example);

    CertBorrow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertBorrow record, @Param("example") CertBorrowExample example);

    int updateByExample(@Param("record") CertBorrow record, @Param("example") CertBorrowExample example);

    int updateByPrimaryKeySelective(CertBorrow record);

    int updateByPrimaryKey(CertBorrow record);
}