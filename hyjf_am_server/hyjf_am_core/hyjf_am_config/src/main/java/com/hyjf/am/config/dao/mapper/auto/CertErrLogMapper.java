package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.CertErrLog;
import com.hyjf.am.config.dao.model.auto.CertErrLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CertErrLogMapper {
    int countByExample(CertErrLogExample example);

    int deleteByExample(CertErrLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertErrLog record);

    int insertSelective(CertErrLog record);

    List<CertErrLog> selectByExample(CertErrLogExample example);

    CertErrLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertErrLog record, @Param("example") CertErrLogExample example);

    int updateByExample(@Param("record") CertErrLog record, @Param("example") CertErrLogExample example);

    int updateByPrimaryKeySelective(CertErrLog record);

    int updateByPrimaryKey(CertErrLog record);
}