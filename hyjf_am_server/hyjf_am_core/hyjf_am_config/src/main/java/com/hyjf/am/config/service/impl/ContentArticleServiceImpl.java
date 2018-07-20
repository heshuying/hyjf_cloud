package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ContentArticleMapper;
import com.hyjf.am.config.dao.mapper.customize.ContentArticleCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.dao.model.auto.ContentArticleExample;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 内容管理 - 文章管理
 *
 * @author yinhui
 */
@Service
public class ContentArticleServiceImpl implements ContentArticleService {

    @Autowired
    private ContentArticleMapper contentArticleMapper;

    @Autowired
    private ContentArticleCustomizeMapper contentArticleCustomizeMapper;

    @Override
    public List<ContentArticle> getContentArticleList(ContentArticleRequest request) {
        ContentArticleExample example = new ContentArticleExample();
        if (request.getLimitStart() !=null) {
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

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    @Override
    public ContentArticleResponse getContentArticleListPage(com.hyjf.am.resquest.config.ContentArticleRequest request) {
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
            Paginator paginator = new Paginator(request.getPaginatorPage(), count);
            //从那条开始
            request.setLimitStart(paginator.getOffset());
            //一页显示几条
            request.setLimitEnd(paginator.getLimit());
            list = contentArticleCustomizeMapper.selectContentArticle(request);
        }

        response.setCount(count);
        if (!CollectionUtils.isEmpty(list)) {
            List<ContentArticleVO> listVO = CommonUtils.convertBeanList(list, ContentArticleVO.class);
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



    @Override
    public void insertAction(com.hyjf.am.resquest.config.ContentArticleRequest request) {
        if (request != null) {

            request.setCreateTime(GetDate.getDate());
            request.setUpdateTime(GetDate.getDate());
            request.setClick(0);

            ContentArticle contentArticle = new ContentArticle();
            BeanUtils.copyProperties(request, contentArticle);
            contentArticleMapper.insert(contentArticle);
        }
    }

    @Override
    public void updateAction(com.hyjf.am.resquest.config.ContentArticleRequest request) {
        if (request != null) {
            ContentArticle contentArticle = new ContentArticle();
            BeanUtils.copyProperties(request, contentArticle);
            contentArticleMapper.updateByPrimaryKey(contentArticle);
        }
    }

    @Override
    public void delectAction(Integer id) {
        contentArticleMapper.deleteByPrimaryKey(id);
    }
}
