package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.CertUser;
import com.hyjf.am.user.dao.model.auto.CertUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CertUserMapper {
    int countByExample(CertUserExample example);

    int deleteByExample(CertUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertUser record);

    int insertSelective(CertUser record);

    List<CertUser> selectByExample(CertUserExample example);

    CertUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertUser record, @Param("example") CertUserExample example);

    int updateByExample(@Param("record") CertUser record, @Param("example") CertUserExample example);

    int updateByPrimaryKeySelective(CertUser record);

    int updateByPrimaryKey(CertUser record);
}