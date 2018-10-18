package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfigLog;
import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfigLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HjhUserAuthConfigLogMapper {
    int countByExample(HjhUserAuthConfigLogExample example);

    int deleteByExample(HjhUserAuthConfigLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhUserAuthConfigLog record);

    int insertSelective(HjhUserAuthConfigLog record);

    List<HjhUserAuthConfigLog> selectByExample(HjhUserAuthConfigLogExample example);

    HjhUserAuthConfigLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhUserAuthConfigLog record, @Param("example") HjhUserAuthConfigLogExample example);

    int updateByExample(@Param("record") HjhUserAuthConfigLog record, @Param("example") HjhUserAuthConfigLogExample example);

    int updateByPrimaryKeySelective(HjhUserAuthConfigLog record);

    int updateByPrimaryKey(HjhUserAuthConfigLog record);

}