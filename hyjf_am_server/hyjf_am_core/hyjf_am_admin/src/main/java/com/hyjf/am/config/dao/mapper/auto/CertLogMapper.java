package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.config.dao.model.auto.CertLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CertLogMapper {
    int countByExample(CertLogExample example);

    int deleteByExample(CertLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CertLog record);

    int insertSelective(CertLog record);

    List<CertLog> selectByExample(CertLogExample example);

    CertLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CertLog record, @Param("example") CertLogExample example);

    int updateByExample(@Param("record") CertLog record, @Param("example") CertLogExample example);

    int updateByPrimaryKeySelective(CertLog record);

    int updateByPrimaryKey(CertLog record);
}