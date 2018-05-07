package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsNoticeConfigMapper {
    int countByExample(SmsNoticeConfigExample example);

    int deleteByExample(SmsNoticeConfigExample example);

    int deleteByPrimaryKey(@Param("id") Integer id, @Param("name") String name);

    int insert(SmsNoticeConfig record);

    int insertSelective(SmsNoticeConfig record);

    List<SmsNoticeConfig> selectByExampleWithBLOBs(SmsNoticeConfigExample example);

    List<SmsNoticeConfig> selectByExample(SmsNoticeConfigExample example);

    SmsNoticeConfig selectByPrimaryKey(@Param("id") Integer id, @Param("name") String name);

    int updateByExampleSelective(@Param("record") SmsNoticeConfig record, @Param("example") SmsNoticeConfigExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsNoticeConfig record, @Param("example") SmsNoticeConfigExample example);

    int updateByExample(@Param("record") SmsNoticeConfig record, @Param("example") SmsNoticeConfigExample example);

    int updateByPrimaryKeySelective(SmsNoticeConfig record);

    int updateByPrimaryKeyWithBLOBs(SmsNoticeConfig record);

    int updateByPrimaryKey(SmsNoticeConfig record);
}