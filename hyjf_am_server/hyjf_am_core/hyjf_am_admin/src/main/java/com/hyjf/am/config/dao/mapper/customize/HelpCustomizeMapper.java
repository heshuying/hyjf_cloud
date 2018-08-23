package com.hyjf.am.config.dao.mapper.customize;

import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.auto.ContentArticleExample;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version HelpCustomizeMapper, v0.1 2018/7/16 16:37
 */

public interface HelpCustomizeMapper {

    /**
     *
     * @description:        根据help查出大分类
     */
    List<HelpCategoryCustomize> selectCategory(String group);
    /**
     *
     * @description:    根据大类id查询子类
     */
    List<HelpCategoryCustomize> selectSunCategory(String pageName);
    /**
     *
     * @description: 根据子类id和直属于大类的id查询出所属帮助内容
     */
    List<HelpContentCustomize> selectSunContentCategory(Map<String, Object> map);

    /**
     * 查询首页文章数据 单独
     */
    List<ContentArticle> selectByExampleWithBLOBsAlone(ContentArticleExample example);
}
