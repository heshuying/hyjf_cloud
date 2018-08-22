/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.CsMessageClient;
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
    private CsMessageClient amDataCollectClient;

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
    public ContentArticleResponse getHomeNoticeList(ContentArticleRequest request) {
        return amConfigClient.getknowsList(request);
    }


    @Override
    public List<Map<String, Object>> getIndex(ContentArticleRequest request) {
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
    public  List<JxBankConfigVO> getBanksList() {
        return amConfigClient.getBankRecordList();

    }

    /**
     * 累计投资总额
     * @return
     */
    @Override
    public BigDecimal selectTenderSum() {
        return amDataCollectClient.selectTenderSum();
    }
    /**
     * 累计收益
     * @return
     */
    @Override
    public BigDecimal selectInterestSum() {
        return amDataCollectClient.selectInterestSum();
    }
    /**
     * 累计投资笔数
     * @return
     */
    @Override
    public int selectTotalTenderSum() {
        return amDataCollectClient.selectTotalTenderSum();
    }

    /**
     *获取公司公告列表
     * @param request
     * @return
     */
    @Override
    public ContentArticleResponse getCompanyDynamicsListPage(ContentArticleRequest request) {
        return amConfigClient.getCompanyDynamicsListPage(request);
    }


}
