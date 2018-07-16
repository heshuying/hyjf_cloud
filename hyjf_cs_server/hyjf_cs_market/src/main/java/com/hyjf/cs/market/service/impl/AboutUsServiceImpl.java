/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.vo.config.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.cs.market.client.AboutUsClient;
import com.hyjf.cs.market.service.AboutUsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;

import java.util.List;

/**
 * @author fuqiang
 * @version AboutUsServiceImpl, v0.1 2018/7/9 9:52
 */
@Service
public class AboutUsServiceImpl extends BaseMarketServiceImpl implements AboutUsService {

    @Autowired
    private AboutUsClient aboutUsClient;

    @Override
    public ContentArticleVO getAboutUs() {
        return aboutUsClient.getAboutUs();
    }

    @Override
    public String getTotalInvestmentAmount() {
        return aboutUsClient.getTotalInvestmentAmount();
    }

    @Override
    public TeamVO getFounder() {
        return aboutUsClient.getFounder();
    }

    @Override
    public List<LinkVO> getPartnersList(Integer partnerType) {
        return aboutUsClient.getPartnersList(partnerType);
    }

    @Override
    public List<EventVO> getEventsList() {
        return aboutUsClient.getEventsList();
    }

    @Override
    public List<ContentArticleVO> getNoticeListCount() {
        return aboutUsClient.aboutUsClient();
    }

    @Override
    public ContentArticleVO getNoticeInfo(Integer id) {
        return aboutUsClient.getNoticeInfo(id);
    }

    @Override
    public List<JobsVo> getJobsList() {
        return aboutUsClient.getJobsList();
    }

    @Override
    public ContentArticleVO getContactUs() {
        return aboutUsClient.contactUs();
    }

    @Override
    public List<ContentArticleVO> getHomeNoticeList() {
        return aboutUsClient.getknowsList();
    }

}
