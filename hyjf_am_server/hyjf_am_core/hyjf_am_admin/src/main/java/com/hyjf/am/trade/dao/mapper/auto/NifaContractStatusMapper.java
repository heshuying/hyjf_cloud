package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.NifaContractStatus;
import com.hyjf.am.trade.dao.model.auto.NifaContractStatusExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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