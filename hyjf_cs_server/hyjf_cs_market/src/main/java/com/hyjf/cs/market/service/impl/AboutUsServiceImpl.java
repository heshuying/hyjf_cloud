/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.CsMessageClient;
import com.hyjf.cs.market.service.AboutUsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version AboutUsServiceImpl, v0.1 2018/7/9 9:52
 */
@Service
public class AboutUsServiceImpl extends BaseMarketServiceImpl implements AboutUsService {

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private CsMessageClient amDataCollectClient;


    //TODO 路径配置
    @Value("${hyjf.api.web.url}")
    private String HYJF_API_WEB_URL;

    // 快捷银行列表
    private static final String BANK_LIST = "/quickbanklist/getbanklist.json";

    public static final  String INVEST_INVEREST_AMOUNT_URL = "http://AM-DATA-COLLECT/am-statistics/search/getTotalInvestAndInterestEntity";
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
    public List<ContentArticleVO> getHomeNoticeList(ContentArticleRequest request) {
        return amConfigClient.getknowsList(request);
    }


    @Override
    public List<ContentArticleVO> getIndex(ContentArticleRequest request) {
        return amConfigClient.getIndexList(request);
    }

    /**
     * 统计数据
     */
    @Override
    public TotalInvestAndInterestVO searchData() {
        TotalInvestAndInterestResponse response = amConfigClient.searchData();
        TotalInvestAndInterestVO totalInvestAndInterestVO = response.getResult();
        return totalInvestAndInterestVO;
    }

    /**
     * 返回快捷银行充值限额
     */
    @Override
    public  JSONObject getBanksList() {
        Map<String, String> params = new HashMap<String, String>();
        String requestUrl = HYJF_API_WEB_URL + BANK_LIST;
        String result = HttpClientUtils.post(requestUrl, params);
        JSONObject status = JSONObject.parseObject(result);
        return status;

    }

    @Override
    public List<ContentArticleVO> getHomeNoticeList() {
        return null;
    }

}
