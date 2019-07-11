package com.hyjf.am.user.dao.mapper.auto;

import com.hyjf.am.user.dao.model.auto.TemplateDispose;
import com.hyjf.am.user.dao.model.auto.TemplateDisposeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TemplateDisposeMapper {
    int countByExample(TemplateDisposeExample example);

    int deleteByExample(TemplateDisposeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TemplateDispose record);

    int insertSelective(TemplateDispose record);

    List<TemplateDispose> selectByExample(TemplateDisposeExample example);

    TemplateDispose selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TemplateDispose record, @Param("example") TemplateDisposeExample example);

    int updateByExample(@Param("record") TemplateDispose record, @Param("example") TemplateDisposeExample example);

    int updateByPrimaryKeySelective(TemplateDispose record);

    int updateByPrimaryKey(TemplateDispose record);
}