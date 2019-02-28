package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ContentArticleMapper;
import com.hyjf.am.config.dao.mapper.customize.ContentArticleCustomizeMapper;
import com.hyjf.am.config.dao.mapper.customize.HelpCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.auto.ContentArticleExample;
import com.hyjf.am.config.dao.model.customize.ContentArticleCustomize;
import com.hyjf.am.config.dao.model.customize.HelpCategoryCustomize;
import com.hyjf.am.config.dao.model.customize.HelpContentCustomize;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Value("${hyjf.web.host}")
    private String webUrl;

    @Override
    public List<ContentArticle> getContentArticleList(ContentArticleRequest request) {
        ContentArticleExample example = new ContentArticleExample();
        if (request != null) {
            if (request.getLimitStart() != null && request.getLimitStart() != -1) {
                example.setLimitStart(request.getLimitStart());
                example.setLimitEnd(request.getLimitEnd());
            }
            if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
                int limitStart = (request.getCurrPage() - 1) * (request.getPageSize());
                int limitEnd = request.getPageSize();
                example.setLimitStart(limitStart);
                example.setLimitEnd(limitEnd);
            }
            ContentArticleExample.Criteria crt = example.createCriteria();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(request.getNoticeType())) {
                crt.andTypeEqualTo(request.getNoticeType());
            }
            crt.andStatusEqualTo(1);
        }
        example.setOrderByClause("create_time Desc");
        List<ContentArticle> list = contentArticleMapper.selectByExample(example);
        return list;
    }


    /**
     *
     * 获取公司公告件数
     * @author liuyang
     * @param noticeType
     * @return
     */
    @Override
    public int getNoticeListCount(String noticeType) {
        ContentArticleExample example = new ContentArticleExample();
        ContentArticleExample.Criteria crt = example.createCriteria();
        crt.andTypeEqualTo(noticeType);
        crt.andStatusEqualTo(1);
        return contentArticleMapper.countByExample(example);
    }

    /**
     *
     * 获取公司公告列表
     * @author liuyang
     * @param noticeType
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<ContentArticle> searchNoticeList(String noticeType, int offset, int limit) {
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
     * 分页查询
     *
     * @param request
     * @return
     */
    @Override
    public ContentArticleResponse getContentArticleListPage(ContentArticleRequest request) {
        List<ContentArticleVO> listVO = new ArrayList<>();
        List<ContentArticle> list = null;
        ContentArticleResponse response = new ContentArticleResponse();

        if (request.getStatus() == null) {
            request.setStatus(3);
        }

        if (StringUtils.isNotEmpty(request.getStartCreate())) {
            request.setStartCreateTime(GetDate.str2Timestamp(request.getStartCreate()));
        }
        if (StringUtils.isNotEmpty(request.getEndCreate())) {
            request.setEndCreateTime(GetDate.str2Timestamp(request.getEndCreate()));
        }

        //查询全部
        request.setLimitStart(-1);
        Integer count = contentArticleCustomizeMapper.countContentArticle(request);

        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize()==0?10:request.getPageSize());
            //从那条开始
            request.setLimitStart(paginator.getOffset());
            //一页显示几条
            request.setLimitEnd(paginator.getLimit());
            list = contentArticleCustomizeMapper.selectContentArticle(request);
        }

        response.setCount(count);
        if (!CollectionUtils.isEmpty(list)) {
            ContentArticleVO vo = null;
            for (ContentArticle ca : list) {
                vo = new ContentArticleVO();
                vo.setPublishTime(GetDate.dateToString(ca.getCreateTime()));
                BeanUtils.copyProperties(ca, vo);
                listVO.add(vo);
            }
//            List<ContentArticleVO> listVO = CommonUtils.convertBeanList(list, ContentArticleVO.class);
            response.setResultList(listVO);
        }
        return response;
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
     *
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
     *
     * @return
     */
    @Override
    public List<ContentArticle> searchHomeNoticeList(String noticeType, Integer offset, Integer limit) {
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
     * @description: 根据help查出大分类
     */
    @Override
    public List<HelpCategoryCustomize> selectCategory(String group) {
        return helpCustomizeMapper.selectCategory(group);
    }

    /**
     * @description: 根据大类id查询子类
     */
    @Override
    public List<HelpCategoryCustomize> selectSunCategory(String pageName) {
        return helpCustomizeMapper.selectSunCategory(pageName);
    }

    /**
     * @description: 根据子类id和直属于大类的id查询出所属帮助内容
     */
    @Override
    public List<HelpContentCustomize> selectSunContentCategory(String type, String pid) {
        Map<String, Object> tmpmap = new HashMap<String, Object>();
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
            contentArticle.setUpdateTime(GetDate.getDate());
            contentArticleMapper.updateByPrimaryKeySelective(contentArticle);
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
     *
     * @return
     */
    @Override
    public Integer countContentArticleByType(String type) {
        ContentArticleExample example = new ContentArticleExample();
        example.createCriteria().andTypeEqualTo(type).andStatusEqualTo(1);
        return contentArticleMapper.countByExample(example);
    }

    /**
     * 查询文章列表
     *
     * @param params
     * @return
     */
    @Override
    public List<ContentArticleCustomize> getContentArticleListByType(Map<String, Object> params) {
        List<ContentArticle> list = contentArticleCustomizeMapper.getContentArticleListByType(params);
        List<ContentArticleCustomize> knowledgeCustomizes = new ArrayList<ContentArticleCustomize>();
        for (ContentArticle contentArticle : list) {
            ContentArticleCustomize customize = new ContentArticleCustomize();
            customize.setTitle(contentArticle.getTitle());
            customize.setTime(new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
            customize.setMessageId(contentArticle.getId() + "");
            customize.setMessageUrl(webUrl +"/find/contentArticle" +
                    "/{type}/{contentArticleId}".replace("{contentArticleId}", contentArticle.getId() + "").replace("{type}", (String) params.get("type")));
            customize.setShareTitle(contentArticle.getTitle());
            customize.setShareContent(contentArticle.getSummary());
            customize.setSharePicUrl("https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
            customize.setShareUrl(webUrl + "/find/contentArticle" +
                    "/{type}/{contentArticleId}".replace("{contentArticleId}", contentArticle.getId() + "").replace("{type}", (String) params.get("type")));

            knowledgeCustomizes.add(customize);
        }
        return knowledgeCustomizes;
    }

    /**
     * 上下翻页
     *
     * @param params
     * @param offset
     * @return
     */
    @Override
    public ContentArticleCustomize getContentArticleFlip(Map<String, Object> params, String offset) {
        ContentArticle contentArticle = null;
        if ("0".equals(offset)) {
            contentArticle = contentArticleCustomizeMapper.getContentArticleUp(params);
        } else {
            contentArticle = contentArticleCustomizeMapper.getContentArticleDown(params);
        }


        if (contentArticle != null) {
            ContentArticleCustomize customize = new ContentArticleCustomize();
            customize.setTitle(contentArticle.getTitle());
            customize.setTime(new SimpleDateFormat("yyyy-MM-dd").format(contentArticle.getCreateTime()));
            customize.setMessageId(contentArticle.getId() + "");
            customize.setMessageUrl(webUrl + "/find/contentArticle" +
                    "/{type}/{contentArticleId}".replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type")));
            customize.setShareTitle(contentArticle.getTitle());
            customize.setShareContent(contentArticle.getSummary());
            customize.setSharePicUrl("https://www.hyjf.com/data/upfiles/image/20140617/1402991818340.png");
            // 分享url ,添加app sign忽略标识， 在zuul不检查sign
            customize.setShareUrl(webUrl + "/find/contentArticle" +
                    "/{type}/{contentArticleId}".replace("{contentArticleId}", contentArticle.getId()+"").replace("{type}", (String)params.get("type"))
                    + "?ignoreSign=true"
            );
            return customize;
        }
        return null;
    }
}
