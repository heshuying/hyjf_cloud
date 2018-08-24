package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.NifaContractStatus;
import com.hyjf.am.trade.dao.model.auto.NifaContractStatusExample;

public interface NifaContractStatusMapper {
    int countByExample(NifaContractStatusExample example);

    int deleteByExample(NifaContractStatusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NifaContractStatus record);

    int insertSelective(NifaContractStatus record);

    List<NifaContractStatus> selectByExample(NifaContractStatusExample example);

    NifaContractStatus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NifaContractStatus record, @Param("example") NifaContractStatusExample example);

    int updateByExample(@Param("record") NifaContractStatus record, @Param("example") NifaContractStatusExample example);

    int updateByPrimaryKeySelective(NifaContractStatus record);

    int updateByPrimaryKey(NifaContractStatus record);
}