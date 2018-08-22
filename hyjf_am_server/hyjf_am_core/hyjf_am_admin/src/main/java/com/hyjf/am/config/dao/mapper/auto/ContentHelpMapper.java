package com.hyjf.am.config.dao.mapper.auto;

import com.hyjf.am.config.dao.model.auto.ContentHelp;
import com.hyjf.am.config.dao.model.auto.ContentHelpExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ContentHelpMapper {
    int countByExample(ContentHelpExample example);

    int deleteByExample(ContentHelpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ContentHelp record);

    int insertSelective(ContentHelp record);

    List<ContentHelp> selectByExample(ContentHelpExample example);

    ContentHelp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ContentHelp record, @Param("example") ContentHelpExample example);

    int updateByExample(@Param("record") ContentHelp record, @Param("example") ContentHelpExample example);

    int updateByPrimaryKeySelective(ContentHelp record);

    int updateByPrimaryKey(ContentHelp record);
}