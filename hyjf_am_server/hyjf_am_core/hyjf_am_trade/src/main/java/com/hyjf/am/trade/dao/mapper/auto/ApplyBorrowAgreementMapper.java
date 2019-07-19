package com.hyjf.am.trade.dao.mapper.auto;

import com.hyjf.am.trade.dao.model.auto.ApplyBorrowAgreement;
import com.hyjf.am.trade.dao.model.auto.ApplyBorrowAgreementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplyBorrowAgreementMapper {
    int countByExample(ApplyBorrowAgreementExample example);

    int deleteByExample(ApplyBorrowAgreementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyBorrowAgreement record);

    int insertSelective(ApplyBorrowAgreement record);

    List<ApplyBorrowAgreement> selectByExample(ApplyBorrowAgreementExample example);

    ApplyBorrowAgreement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplyBorrowAgreement record, @Param("example") ApplyBorrowAgreementExample example);

    int updateByExample(@Param("record") ApplyBorrowAgreement record, @Param("example") ApplyBorrowAgreementExample example);

    int updateByPrimaryKeySelective(ApplyBorrowAgreement record);

    int updateByPrimaryKey(ApplyBorrowAgreement record);
}