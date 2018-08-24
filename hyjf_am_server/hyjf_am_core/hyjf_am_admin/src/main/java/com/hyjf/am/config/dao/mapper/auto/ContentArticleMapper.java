package com.hyjf.am.config.dao.mapper.auto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.auto.ContentArticleExample;

public interface ContentArticleMapper {
    int countByExample(ContentArticleExample example);

    int deleteByExample(ContentArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ContentArticle record);

    int insertSelective(ContentArticle record);

    List<ContentArticle> selectByExample(ContentArticleExample example);

    ContentArticle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ContentArticle record, @Param("example") ContentArticleExample example);

    int updateByExample(@Param("record") ContentArticle record, @Param("example") ContentArticleExample example);

    int updateByPrimaryKeySelective(ContentArticle record);

    int updateByPrimaryKey(ContentArticle record);
}