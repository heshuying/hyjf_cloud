package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.NifaContractTemplate;
import com.hyjf.am.trade.dao.model.auto.NifaContractTemplateExample;

public interface NifaContractTemplateMapper {
    int countByExample(NifaContractTemplateExample example);

    int deleteByExample(NifaContractTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NifaContractTemplate record);

    int insertSelective(NifaContractTemplate record);

    List<NifaContractTemplate> selectByExample(NifaContractTemplateExample example);

    NifaContractTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NifaContractTemplate record, @Param("example") NifaContractTemplateExample example);

    int updateByExample(@Param("record") NifaContractTemplate record, @Param("example") NifaContractTemplateExample example);

    int updateByPrimaryKeySelective(NifaContractTemplate record);

    int updateByPrimaryKey(NifaContractTemplate record);
}