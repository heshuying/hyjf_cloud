package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ContentArticleMapper;
import com.hyjf.am.config.dao.mapper.customize.ContentArticleCustomizeMapper;
import com.hyjf.am.config.dao.mapper.customize.HelpCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.auto.ContentArticleExample;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;

import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContentArticleServiceImpl implements ContentArticleService {

    @Autowired
    private ContentArticleMapper contentArticleMapper;

    @Autowired
    private HelpCustomizeMapper helpCustomizeMapper;

    @Autowired
    private ContentArticleCustomizeMapper contentArticleCustomizeMapper;

    @Value("hyjf.web.host")
    private String webUrl;

    @Override
    public List<ContentArticle> getContentArticleList(ContentArticleRequest request) {
        ContentArticleExample example = new ContentArticleExample();
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        ContentArticleExample.Criteria crt = example.createCriteria();
        crt.andTypeEqualTo(request.getNoticeType());
        crt.andStatusEqualTo(1);
        example.setOrderByClause("create_time Desc");
        List<ContentArticle> list = contentArticleMapper.selectByExample(example);
        return list;
    }

    @Override
    public ContentArticleResponse getContentArticleListPage(ContentArticleRequest request) {
        return null;
    }


    @Override
    public ContentArticle getAboutUs() {
        ContentArticleExample example = new ContentArticleExample();
        ContentArticleExample.Criteria cra = example.createCriteria();
        // 关于我们
        cra.andTypeEqualTo("5");
        // 启用状态
        cra.andStatusEqualTo(1);
        List<ContentArticle> conlist = contentArticleMapper.selectByExample(example);
        if (conlist != null && conlist.size() > 0) {
            return conlist.get(0);
        }
        return new ContentArticle();
    }

    /**
     * 获取联系我们
     *
     * @return
     * @author Michael
     */
    @Override
    public ContentArticle getContactUs() {
        ContentArticleExample example = new ContentArticleExample();
        ContentArticleExample.Criteria cra = example.createCriteria();
        // 联系我们
        cra.andTypeEqualTo("8");
        // 启用状态
        cra.andStatusEqualTo(1);

        List<ContentArticle> conlist = contentArticleMapper.selectByExample(example);
        if (conlist != null && conlist.size() > 0) {
            return conlist.get(0);
        }
        return new ContentArticle();
    }

    @Override
    public ContentArticle getArticleById(Integer id) {
        return contentArticleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询公告列表
     * @author zhangyk
     * @date 2018/7/16 11:42
     */
    @Override
    public List<ContentArticle> getNoticeList(ContentArticleRequest request) {
        ContentArticleExample example = new ContentArticleExample();
        example.setLimitStart(request.getLimitStart());
        example.setLimitEnd(request.getLimitEnd());
        ContentArticleExample.Criteria cra = example.createCriteria();
        cra.andTypeEqualTo(request.getNoticeType());
        cra.andStatusEqualTo(1);
        example.setOrderByClause("create_time Desc");
        List<ContentArticle> conlist = contentArticleMapper.selectByExample(example);
        return conlist;
    }



    /**
     * 获取风险教育总数
     *
     * @return List
     */
    @Override
    public int countHomeNoticeList(String noticeType) {
        ContentArticleExample example = new ContentArticleExample();
        ContentArticleExample.Criteria crt = example.createCriteria();
        crt.andTypeEqualTo(noticeType);
        crt.andStatusEqualTo(1);
        return contentArticleMapper.countByExample(example);
    }

    /**
     * 取首页公告(风险教育..)列表
     * @return
     */
    @Override
    public List<ContentArticle> searchHomeNoticeList(String noticeType, int offset, int limit) {
        ContentArticleExample example = new ContentArticleExample();
        if (offset != -1) {
            example.setLimitStart(offset);
            example.setLimitEnd(limit);
        }
        ContentArticleExample.Criteria crt = example.createCriteria();
        crt.andTypeEqualTo(noticeType);
        crt.andStatusEqualTo(1);
        example.setOrderByClause("create_time Desc");
        List<ContentArticle> contentArticles = contentArticleMapper.selectByExample(example);
        return contentArticles;
    }

    /**
     *
     * @description: 根据help查出大分类
     */
    @Override
    public List<HelpCategoryCustomize> selectCategory(String group) {
        return helpCustomizeMapper.selectCategory(group);
    }

    /**
     *
     * @description: 	根据大类id查询子类
     */
    @Override
    public List<HelpCategoryCustomize> selectSunCategory(String pageName) {
        return helpCustomizeMapper.selectSunCategory(pageName);
    }

    /**
     *
     * @description: 根据子类id和直属于大类的id查询出所属帮助内容
     */
    @Override
    public List<HelpContentCustomize> selectSunContentCategory(String type,String pid) {
        Map<String, Object> tmpmap=new HashMap<String, Object>();
        tmpmap.put("type", type);
        tmpmap.put("pid", pid);
        return helpCustomizeMapper.selectSunContentCategory(tmpmap);
    }

    /**
     * 添加文章管理
     *
     * @param request
     */
    @Override
    public void insertAction(ContentArticleRequest request) {
        if (request != null) {

            request.setCreateTime(GetDate.getDate());
            request.setUpdateTime(GetDate.getDate());
            request.setClick(0);

            ContentArticle contentArticle = new ContentArticle();
            BeanUtils.copyProperties(request, contentArticle);
            contentArticleMapper.insert(contentArticle);
        }
    }

    /**
     * 修改文章
     *
     * @param request
     */
    @Override
    public void updateAction(ContentArticleRequest request) {
        if (request != null) {
            ContentArticle contentArticle = new ContentArticle();
            BeanUtils.copyProperties(request, contentArticle);
            contentArticleMapper.updateByPrimaryKey(contentArticle);
        }
    }

    /**
     * 删除文章
     *
     * @param id
     */
    @Override
    public void delectAction(Integer id) {
        contentArticleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询文章条数
     * @return
     */
    @Override
    public Integer countContentArticleByType(Map<String, Object> params) {
        return contentArticleCustomizeMapper.countContentArticleByType(params);
    }

    /**
     * 查询文章列表
     * @param params
     * @return
     */
    @Override
    public List<ContentArticleCustomize> getContentArticleListByType(Map<String, Object> params) {
        List<ContentArticle> list = contentArticleCustomizeMapper.getContentArticleListByType(params);
        List<ContentArticleCustomize> knowledgeCustomizes=new ArrayList<ContentArticleCustomize>();
        for (ContentArticle contentArticle : list) {
            ContentArticleCustomize customize=new ContentArticleCustomize();
            customize.setTitle(contentArticle.getTitle());
            customize.setTime(new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
            customize.setMessageId(contentArticle.getId()+"");
            System.out.println(webUrl);
            customize.setMessageUrl(webUrl +"/find/contentArticle"+
                    "/{type}/{contentArticleId}".replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type")));
            customize.setShareTitle(contentArticle.getTitle());
            customize.setShareContent(contentArticle.getSummary());
            customize.setSharePicUrl("https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
            customize.setShareUrl(webUrl +"/find/contentArticle"+
                    "/{type}/{contentArticleId}".replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type")));

            knowledgeCustomizes.add(customize);
        }
        return knowledgeCustomizes;
    }

    /**
     * 上下翻页
     * @param params
     * @param offset
     * @return
     */
    @Override
    public ContentArticleCustomize getContentArticleFlip(Map<String, Object> params, String offset) {
        ContentArticle contentArticle=null;
        if("0".equals(offset)){
            contentArticle=contentArticleCustomizeMapper.getContentArticleUp(params);
        }else{
            contentArticle=contentArticleCustomizeMapper.getContentArticleDown(params);
        }


        if(contentArticle!=null){
            ContentArticleCustomize customize=new ContentArticleCustomize();
            customize.setTitle(contentArticle.getTitle());
            customize.setTime(new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
            customize.setMessageId(contentArticle.getId()+"");
            //customize.setMessageUrl(PropUtils.getSystem("hyjf.web.host")+AppContentArticleDefine.REQUEST_MAPPING+
            //        AppContentArticleDefine.GET_CONTENT_ARTICLE_ID_ACTION.replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type")));
            customize.setShareTitle(contentArticle.getTitle());
            customize.setShareContent(contentArticle.getSummary());
            customize.setSharePicUrl("https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
            //customize.setShareUrl(PropUtils.getSystem("hyjf.web.host")+AppContentArticleDefine.REQUEST_MAPPING+
            //        AppContentArticleDefine.GET_CONTENT_ARTICLE_ID_ACTION.replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type")));
            return customize;
        }
        return null;
    }
}
