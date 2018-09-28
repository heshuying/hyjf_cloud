package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.dao.model.auto.CertificateAuthorityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CertificateAuthorityMapper {
    int countByExample(CertificateAuthorityExample example);

    int deleteByExample(CertificateAuthorityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertificateAuthority record);

    int insertSelective(CertificateAuthority record);

    List<CertificateAuthority> selectByExample(CertificateAuthorityExample example);

    CertificateAuthority selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertificateAuthority record, @Param("example") CertificateAuthorityExample example);

    int updateByExample(@Param("record") CertificateAuthority record, @Param("example") CertificateAuthorityExample example);

    int updateByPrimaryKeySelective(CertificateAuthority record);

    int updateByPrimaryKey(CertificateAuthority record);
}