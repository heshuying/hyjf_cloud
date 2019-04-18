/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.response.config.WechatContentArticleResponse;
import com.hyjf.am.resquest.config.WechatContentArticleRequest;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.vo.app.AppFindAdCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleCustomizeVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.client.CsMessageClient;
import com.hyjf.cs.market.service.AppFindService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version AppFindServiceImpl, v0.1 2018/7/20 9:49
 */
@Service
public class AppFindServiceImpl extends BaseMarketServiceImpl implements AppFindService {
    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmMarketClient amMarketClient;

    @Autowired
    private CsMessageClient csMessageClient;

    /**
     * 查询文章条数
     * @return
     */
    @Override
    public Integer countContentArticleByType(String type) {
        return amConfigClient.countContentArticleByType(type);
    }

    /**
     * 查询文章列表
     * @param params
     * @return
     */
    @Override
    public List<ContentArticleCustomizeVO> getContentArticleListByType(Map<String, Object> params) {
        return amConfigClient.getContentArticleListByType(params);
    }

    /**
     * 上下翻页
     * @param params
     * @param offset
     * @return
     */
    @Override
    public ContentArticleCustomizeVO getContentArticleFlip(Map<String, Object> params, String offset) {
        return amConfigClient.getContentArticleFlip(params, offset);
    }

    /**
     * 获取首页公告内容
     * @param contentArticleId
     * @return
     */
    @Override
    public AppPushManageVO getAppPushManagerContentByID(Integer contentArticleId) {
        AppPushManageVO manageInfo = amTradeClient.getAnnouncementsByID(contentArticleId);
        return manageInfo;
    }

    @Override
    public ContentArticleVO getContentArticleById(Integer contentArticleId) {
        return amConfigClient.getContentArticleById(contentArticleId);
    }

    @Override
    public List<ContentArticleVO> searchHomeNoticeList(String noticeType, int limitStart, int limitEnd) {
        return amConfigClient.searchHomeNoticeList(noticeType,limitStart,limitEnd);
    }

    @Override
    public WechatContentArticleResponse searchContentArticleList(WechatContentArticleRequest form) {
        return amConfigClient.searchContentArticleList(form);
    }

    @Override
    public List<AppFindAdCustomizeVO> getFindModules(AdsRequest request){
        return amMarketClient.getFindModules(request);
    }

    @Override
    public AppFindAdCustomizeVO getFindBanner(AdsRequest request){
        return amMarketClient.getFindBanner(request);
    }

    /**
     * app发现页运营报告信息
     * @param isRelease
     * @return
     */
    public List getReportList(Integer isRelease){
        return csMessageClient.getReportList(isRelease);
    }
}
