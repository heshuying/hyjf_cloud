package com.hyjf.am.trade.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.auto.TenderAgreementExample;

public interface TenderAgreementMapper {
    int countByExample(TenderAgreementExample example);

    int deleteByExample(TenderAgreementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TenderAgreement record);

    int insertSelective(TenderAgreement record);

    List<TenderAgreement> selectByExample(TenderAgreementExample example);

    TenderAgreement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TenderAgreement record, @Param("example") TenderAgreementExample example);

    int updateByExample(@Param("record") TenderAgreement record, @Param("example") TenderAgreementExample example);

    int updateByPrimaryKeySelective(TenderAgreement record);

    int updateByPrimaryKey(TenderAgreement record);
}