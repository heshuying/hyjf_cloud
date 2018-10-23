package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthority;
import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthorityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanSubjectCertificateAuthorityMapper {
    int countByExample(LoanSubjectCertificateAuthorityExample example);

    int deleteByExample(LoanSubjectCertificateAuthorityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LoanSubjectCertificateAuthority record);

    int insertSelective(LoanSubjectCertificateAuthority record);

    List<LoanSubjectCertificateAuthority> selectByExample(LoanSubjectCertificateAuthorityExample example);

    LoanSubjectCertificateAuthority selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LoanSubjectCertificateAuthority record, @Param("example") LoanSubjectCertificateAuthorityExample example);

    int updateByExample(@Param("record") LoanSubjectCertificateAuthority record, @Param("example") LoanSubjectCertificateAuthorityExample example);

    int updateByPrimaryKeySelective(LoanSubjectCertificateAuthority record);

    int updateByPrimaryKey(LoanSubjectCertificateAuthority record);
}