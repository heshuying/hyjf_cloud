package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.DebtCompanyAuthen;
import com.hyjf.am.trade.dao.model.auto.DebtCompanyAuthenExample;

public interface DebtCompanyAuthenMapper {
    int countByExample(DebtCompanyAuthenExample example);

    int deleteByExample(DebtCompanyAuthenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DebtCompanyAuthen record);

    int insertSelective(DebtCompanyAuthen record);

    List<DebtCompanyAuthen> selectByExample(DebtCompanyAuthenExample example);

    DebtCompanyAuthen selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DebtCompanyAuthen record, @Param("example") DebtCompanyAuthenExample example);

    int updateByExample(@Param("record") DebtCompanyAuthen record, @Param("example") DebtCompanyAuthenExample example);

    int updateByPrimaryKeySelective(DebtCompanyAuthen record);

    int updateByPrimaryKey(DebtCompanyAuthen record);
}