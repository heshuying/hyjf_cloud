/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client;

import com.hyjf.am.response.admin.JxBankConfigResponse;
import com.hyjf.am.response.config.WechatContentArticleResponse;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.config.WechatContentArticleRequest;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.market.ShareNewsBeanVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version AmConfigClient, v0.1 2018/7/9 10:09
 */
public interface AmConfigClient {
    /**
     * 获取公司简介
     *
     * @return
     */
    ContentArticleVO getAboutUs();

    /**
     * 获取创始人信息
     *
     * @return
     */
    TeamVO getFounder();

    /**
     * 获取所有公司记事记录
     *
     * @return
     */
    List<EventVO> getEventsList();

    /**
     * 检索活动列表数据
     *
     * @return
     */
    List<ContentArticleVO> aboutUsClient();

    /**
     * 根据主键ID获取Aricle
     *
     * @param id
     * @return
     */
    ContentArticleVO getNoticeInfo(Integer id);

    /**
     * 获取招贤纳士列表
     * @return
     */
    public List<JobsVo> getJobsList();

    /**
     * 获取关于联系我们信息
     * @return
     */
    public ContentArticleVO contactUs();

    /**
     * 获取网贷知识
     * @return
     */
    ContentArticleResponse getknowsList(ContentArticleRequest request);

    /**
     * 获取合作伙伴列表
     *
     * @param partnerType 合作类型
     * @return
     */
    List<LinkVO> getPartnersList(Integer partnerType);

    /**
     * 获取
     * @param request
     * @return
     */
    List<Map<String, Object>> getIndexList(ContentArticleRequest request);


    TotalInvestAndInterestResponse searchData();

    /**
     * 查询文章条数
     * @return
     */
    Integer countContentArticleByType();

    /**
     * 查询文章列表
     * @return
     */
    List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String, Object> params);

    /**
     * 根据文章id查找文章
     * @param contentArticleId
     * @return
     */
    ContentArticleVO getContentArticleById(Integer contentArticleId);

    /**
     * 上下翻页
     * @param params
     * @param offset
     * @return
     */
    ContentArticleCustomizeVO getContentArticleFlip(Map<String, Object> params, String offset);

    /**
     * @Author walter.limeng
     * @user walter.limeng
     * @Description  获取分享信息
     * @Date 9:08 2018/7/27
     * @Param
     * @return ShareNewsBeanVO
     */
    ShareNewsBeanVO queryShareNews();
    /**
     * 用于展示发布的信息
     * @param noticeType
     * @param limitStart
     * @param limitEnd
     * @return
     */
    public List<ContentArticleVO> searchHomeNoticeList(String noticeType,int limitStart, int limitEnd) ;

    /**
     * 根据文章类型获取文章列表
     * @param form
     * @return
     */
    public WechatContentArticleResponse searchContentArticleList(WechatContentArticleRequest form);

    /**
     * 添加反馈信息
     * @param submissionsVO
     * @return
     */
    int addSubmission(SubmissionsVO submissionsVO);

    List<JxBankConfigVO> getBankRecordList();
}
