package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.BorrowCompanyAuthen;
import com.hyjf.am.trade.dao.model.auto.BorrowCompanyAuthenExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BorrowCompanyAuthenMapper {
    int countByExample(BorrowCompanyAuthenExample example);

    int deleteByExample(BorrowCompanyAuthenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowCompanyAuthen record);

    int insertSelective(BorrowCompanyAuthen record);

    List<BorrowCompanyAuthen> selectByExample(BorrowCompanyAuthenExample example);

    BorrowCompanyAuthen selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BorrowCompanyAuthen record, @Param("example") BorrowCompanyAuthenExample example);

    int updateByExample(@Param("record") BorrowCompanyAuthen record, @Param("example") BorrowCompanyAuthenExample example);

    int updateByPrimaryKeySelective(BorrowCompanyAuthen record);

    int updateByPrimaryKey(BorrowCompanyAuthen record);
}