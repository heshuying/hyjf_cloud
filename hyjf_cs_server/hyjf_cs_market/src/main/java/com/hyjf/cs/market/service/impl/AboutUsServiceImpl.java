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
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.PropertiesConstants;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hyjf.cs.market.client.AboutUsClient;
import com.hyjf.cs.market.service.AboutUsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;

import java.math.BigDecimal;
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
    private AboutUsClient aboutUsClient;

    @Autowired
    private BaseClient baseClient;

    //TODO 路径配置
    @Value("${hyjf.api.web.url}")
    private String HYJF_API_WEB_URL;

    // 快捷银行列表
    private static final String BANK_LIST = "/quickbanklist/getbanklist.json";

    public static final  String INVEST_INVEREST_AMOUNT_URL = "http://AM-DATA-COLLECT/am-statistics/search/getTotalInvestAndInterestEntity";
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
    public List<ContentArticleVO> getHomeNoticeList(ContentArticleRequest request) {
        return aboutUsClient.getknowsList(request);
    }

    @Override
    public List<ContentArticleVO> getIndex(ContentArticleRequest request) {
        return aboutUsClient.getIndexList(request);
    }

    /**
     * 统计数据
     */
    @Override
    public TotalInvestAndInterestVO searchData() {
        TotalInvestAndInterestResponse response = baseClient.getExe(INVEST_INVEREST_AMOUNT_URL,TotalInvestAndInterestResponse.class);
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

}
