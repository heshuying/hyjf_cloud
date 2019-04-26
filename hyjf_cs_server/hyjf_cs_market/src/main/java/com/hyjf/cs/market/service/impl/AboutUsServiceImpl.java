/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.CsMessageClient;
import com.hyjf.cs.market.service.AboutUsService;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import com.hyjf.cs.market.util.CdnUrlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        List<EventVO> eventsList = amConfigClient.getEventsList();
        List<EventVO> list = new ArrayList<>();
        for (EventVO vo : eventsList) {
            if (vo.getStatus() == 1) {
                list.add(vo);
            }
        }
        return list;
    }

    @Override
    public List<ContentArticleVO> getNoticeListCount(BasePage request) {
        return amConfigClient.aboutUsClient(request);
    }

    @Override
    public ContentArticleVO getNoticeInfo(Integer id) {
        ContentArticleVO contentArticle = amConfigClient.getContentArticleById(id);
        String cdnUrl = CdnUrlUtil.getCdnUrl();
        if(null == contentArticle){
            return null;
        }
        String content = contentArticle.getContent();
        if (StringUtils.isNotBlank(content)) {
            if (StringUtils.isNotBlank(content)) {
//            int start = content.indexOf("http");
//            int end = content.indexOf(".com");
//            if (start >=0 && end >=0) {
//            String imgUrl = content.substring(start, end + 4);
                contentArticle.setContent(content.replaceAll("https://www.huiyingdai.com/data", cdnUrl + "data"));
            }
        }
        return contentArticle;
    }

    /**
     * 根据ID获取公司历程详情
     *
     * @param id
     * @return
     * @Author : huanghui
     */
    @Override
    public EventVO getEventDetailById(Integer id) {
        return amConfigClient.getEventDetailById(id);
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
    public List<JxBankConfigVO> getBanksList() {
        return amConfigClient.getBankRecordList();

    }

    /**
     * 累计出借总额
     *
     * @return
     */
    @Override
    public BigDecimal selectTenderSum() {
        return amDataCollectClient.selectTenderSum();
    }

    /**
     * 累计收益
     *
     * @return
     */
    @Override
    public BigDecimal selectInterestSum() {
        return amDataCollectClient.selectInterestSum();
    }

    /**
     * 累计出借笔数
     *
     * @return
     */
    @Override
    public int selectTotalTenderSum() {
        return amDataCollectClient.selectTotalTenderSum();
    }

    /**
     * 获取公司公告列表
     *
     * @param request
     * @return
     */
    @Override
    public ContentArticleResponse getCompanyDynamicsListPage(ContentArticleRequest request) {
        return amConfigClient.getCompanyDynamicsListPage(request);
    }


}
