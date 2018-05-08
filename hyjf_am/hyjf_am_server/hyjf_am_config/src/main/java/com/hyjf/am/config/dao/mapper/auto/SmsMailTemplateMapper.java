package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmsMailTemplateMapper {
    int countByExample(SmsMailTemplateExample example);

    int deleteByExample(SmsMailTemplateExample example);

    int deleteByPrimaryKey(@Param("id") Integer id, @Param("mailValue") String mailValue);

    int insert(SmsMailTemplate record);

    int insertSelective(SmsMailTemplate record);

    List<SmsMailTemplate> selectByExampleWithBLOBs(SmsMailTemplateExample example);

    List<SmsMailTemplate> selectByExample(SmsMailTemplateExample example);

    SmsMailTemplate selectByPrimaryKey(@Param("id") Integer id, @Param("mailValue") String mailValue);

    int updateByExampleSelective(@Param("record") SmsMailTemplate record, @Param("example") SmsMailTemplateExample example);

    int updateByExampleWithBLOBs(@Param("record") SmsMailTemplate record, @Param("example") SmsMailTemplateExample example);

    int updateByExample(@Param("record") SmsMailTemplate record, @Param("example") SmsMailTemplateExample example);

    int updateByPrimaryKeySelective(SmsMailTemplate record);

    int updateByPrimaryKeyWithBLOBs(SmsMailTemplate record);

    int updateByPrimaryKey(SmsMailTemplate record);
}