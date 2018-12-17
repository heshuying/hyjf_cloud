package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ApplyAgreement;
import com.hyjf.am.trade.dao.model.auto.ApplyAgreementExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplyAgreementMapper {
    int countByExample(ApplyAgreementExample example);

    int deleteByExample(ApplyAgreementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyAgreement record);

    int insertSelective(ApplyAgreement record);

    List<ApplyAgreement> selectByExample(ApplyAgreementExample example);

    ApplyAgreement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplyAgreement record, @Param("example") ApplyAgreementExample example);

    int updateByExample(@Param("record") ApplyAgreement record, @Param("example") ApplyAgreementExample example);

    int updateByPrimaryKeySelective(ApplyAgreement record);

    int updateByPrimaryKey(ApplyAgreement record);
}