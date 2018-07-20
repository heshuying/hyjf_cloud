/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.config.*;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.AmDataCollectClient;
import com.hyjf.cs.market.service.AboutUsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;

/**
 * @author fuqiang
 * @version AboutUsServiceImpl, v0.1 2018/7/9 9:52
 */
@Service
public class AboutUsServiceImpl extends BaseMarketServiceImpl implements AboutUsService {

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private AmDataCollectClient amDataCollectClient;

    @Override
    public ContentArticleVO getAboutUs() {
        return amConfigClient.getAboutUs();
    }

    @Override
    public String getTotalInvestmentAmount() {
        return amDataCollectClient.getTotalInvestmentAmount();
    }

    @Override
    public TeamVO getFounder() {
        return amConfigClient.getFounder();
    }

    @Override
    public List<LinkVO> getPartnersList(Integer partnerType) {
        return amConfigClient.getPartnersList(partnerType);
    }

    @Override
    public List<EventVO> getEventsList() {
        return amConfigClient.getEventsList();
    }

    @Override
    public List<ContentArticleVO> getNoticeListCount() {
        return amConfigClient.aboutUsClient();
    }

    @Override
    public ContentArticleVO getNoticeInfo(Integer id) {
        return amConfigClient.getNoticeInfo(id);
    }

    @Override
    public List<JobsVo> getJobsList() {
        return amConfigClient.getJobsList();
    }

    @Override
    public ContentArticleVO getContactUs() {
        return amConfigClient.contactUs();
    }

    @Override
    public List<ContentArticleVO> getHomeNoticeList() {
        return amConfigClient.getknowsList();
    }

}
