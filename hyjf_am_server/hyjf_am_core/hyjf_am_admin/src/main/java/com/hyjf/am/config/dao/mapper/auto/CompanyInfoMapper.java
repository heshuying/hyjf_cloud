package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.CompanyInfo;
import com.hyjf.am.config.dao.model.auto.CompanyInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CompanyInfoMapper {
    int countByExample(CompanyInfoExample example);

    int deleteByExample(CompanyInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CompanyInfo record);

    int insertSelective(CompanyInfo record);

    List<CompanyInfo> selectByExample(CompanyInfoExample example);

    CompanyInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CompanyInfo record, @Param("example") CompanyInfoExample example);

    int updateByExample(@Param("record") CompanyInfo record, @Param("example") CompanyInfoExample example);

    int updateByPrimaryKeySelective(CompanyInfo record);

    int updateByPrimaryKey(CompanyInfo record);
}