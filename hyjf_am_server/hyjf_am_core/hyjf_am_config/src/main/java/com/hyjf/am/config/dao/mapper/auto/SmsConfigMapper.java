package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.config.dao.model.auto.SmsConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsConfigMapper {
    int countByExample(SmsConfigExample example);

    int deleteByExample(SmsConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsConfig record);

    int insertSelective(SmsConfig record);

    List<SmsConfig> selectByExample(SmsConfigExample example);

    SmsConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsConfig record, @Param("example") SmsConfigExample example);

    int updateByExample(@Param("record") SmsConfig record, @Param("example") SmsConfigExample example);

    int updateByPrimaryKeySelective(SmsConfig record);

    int updateByPrimaryKey(SmsConfig record);
}