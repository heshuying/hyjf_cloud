package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.ConfigApplicant;
import com.hyjf.am.config.dao.model.auto.ConfigApplicantExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigApplicantMapper {
    int countByExample(ConfigApplicantExample example);

    int deleteByExample(ConfigApplicantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConfigApplicant record);

    int insertSelective(ConfigApplicant record);

    List<ConfigApplicant> selectByExample(ConfigApplicantExample example);

    ConfigApplicant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConfigApplicant record, @Param("example") ConfigApplicantExample example);

    int updateByExample(@Param("record") ConfigApplicant record, @Param("example") ConfigApplicantExample example);

    int updateByPrimaryKeySelective(ConfigApplicant record);

    int updateByPrimaryKey(ConfigApplicant record);
}