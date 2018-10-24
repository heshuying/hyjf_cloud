package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsMailTemplateMapper {
    int countByExample(SmsMailTemplateExample example);

    int deleteByExample(SmsMailTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SmsMailTemplate record);

    int insertSelective(SmsMailTemplate record);

    List<SmsMailTemplate> selectByExample(SmsMailTemplateExample example);

    SmsMailTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SmsMailTemplate record, @Param("example") SmsMailTemplateExample example);

    int updateByExample(@Param("record") SmsMailTemplate record, @Param("example") SmsMailTemplateExample example);

    int updateByPrimaryKeySelective(SmsMailTemplate record);

    int updateByPrimaryKey(SmsMailTemplate record);
}