package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.TemplateConfig;
import com.hyjf.am.user.dao.model.auto.TemplateConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TemplateConfigMapper {
    int countByExample(TemplateConfigExample example);

    int deleteByExample(TemplateConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TemplateConfig record);

    int insertSelective(TemplateConfig record);

    List<TemplateConfig> selectByExample(TemplateConfigExample example);

    TemplateConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TemplateConfig record, @Param("example") TemplateConfigExample example);

    int updateByExample(@Param("record") TemplateConfig record, @Param("example") TemplateConfigExample example);

    int updateByPrimaryKeySelective(TemplateConfig record);

    int updateByPrimaryKey(TemplateConfig record);
}