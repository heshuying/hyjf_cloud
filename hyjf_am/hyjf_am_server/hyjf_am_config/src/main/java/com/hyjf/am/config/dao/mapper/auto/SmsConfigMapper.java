package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.config.dao.model.auto.SmsConfigExample;
import com.hyjf.am.config.dao.model.auto.SmsConfigWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsConfigMapper {
    int countByExample(SmsConfigExample example);

    int deleteByExample(SmsConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsConfigWithBLOBs record);

    int insertSelective(SmsConfigWithBLOBs record);

    List<SmsConfigWithBLOBs> selectByExampleWithBLOBs(SmsConfigExample example);

    List<SmsConfig> selectByExample(SmsConfigExample example);

    SmsConfigWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsConfigWithBLOBs record, @Param("example") SmsConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsConfigWithBLOBs record, @Param("example") SmsConfigExample example);

    int updateByExample(@Param("record") SmsConfig record, @Param("example") SmsConfigExample example);

    int updateByPrimaryKeySelective(SmsConfigWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SmsConfigWithBLOBs record);

    int updateByPrimaryKey(SmsConfig record);
}