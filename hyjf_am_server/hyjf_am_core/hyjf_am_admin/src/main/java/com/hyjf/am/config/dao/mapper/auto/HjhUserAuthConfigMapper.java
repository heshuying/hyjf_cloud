package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfig;
import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HjhUserAuthConfigMapper {
    int countByExample(HjhUserAuthConfigExample example);

    int deleteByExample(HjhUserAuthConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HjhUserAuthConfig record);

    int insertSelective(HjhUserAuthConfig record);

    List<HjhUserAuthConfig> selectByExample(HjhUserAuthConfigExample example);

    HjhUserAuthConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HjhUserAuthConfig record, @Param("example") HjhUserAuthConfigExample example);

    int updateByExample(@Param("record") HjhUserAuthConfig record, @Param("example") HjhUserAuthConfigExample example);

    int updateByPrimaryKeySelective(HjhUserAuthConfig record);

    int updateByPrimaryKey(HjhUserAuthConfig record);
}