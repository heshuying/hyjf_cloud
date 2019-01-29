package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfigExample;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfigKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsNoticeConfigMapper {
    int countByExample(SmsNoticeConfigExample example);

    int deleteByExample(SmsNoticeConfigExample example);

    int deleteByPrimaryKey(SmsNoticeConfigKey key);

    int insert(SmsNoticeConfig record);

    int insertSelective(SmsNoticeConfig record);

    List<SmsNoticeConfig> selectByExample(SmsNoticeConfigExample example);

    SmsNoticeConfig selectByPrimaryKey(SmsNoticeConfigKey key);

    int updateByExampleSelective(@Param("record") SmsNoticeConfig record, @Param("example") SmsNoticeConfigExample example);

    int updateByExample(@Param("record") SmsNoticeConfig record, @Param("example") SmsNoticeConfigExample example);

    int updateByPrimaryKeySelective(SmsNoticeConfig record);

    int updateByPrimaryKey(SmsNoticeConfig record);
}