package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.bean.HomeDataResultBean;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.WebHomeService;
import com.hyjf.cs.trade.util.AppHomePageDefine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WebHomeServiceImpl implements WebHomeService {

    private static Logger logger = LoggerFactory.getLogger(WebHomeServiceImpl.class);

    // 通知列表
    private static final  String NOTICE_LIST_URL = "http://AM-CONFIG/am-config/article/noticeList";
    // banner 列表
    private static final  String BANNER_LIST_URL = "http://AM-MARKET/am-market/ads/getBannerList";

    /**
     * 平台上线时间
     */
    private static final String PUT_ONLINE_TIME = "2013-12";

    /** 消息类型-公司动态 */
    public static final String NOTICE_TYPE_COMPANY_DYNAMICS = "20";

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private CouponUserClient couponUserClient;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private WebProjectListClient webProjectListClient;

    @Autowired
    private ContentArticleClient contentArticleClient;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private BaseClient baseClient;


    /**
     * 获取首页数据
     *
     * @author zhangyk
     * @date 2018/7/4 13:52
     */
    @Override
    public WebResult getHomeData(String userId) {
        HomeDataResultBean result = new HomeDataResultBean();
        WebResult webResult = new WebResult();
        UserVO userVO = null;
        boolean loginFlag = Boolean.FALSE;
        if (StringUtils.isNotBlank(userId)) {
            userVO = amUserClient.findUserById(Integer.valueOf(userId));
            if (userVO != null) {
                loginFlag = Boolean.TRUE;
            }
        }
        if (!loginFlag) {  // 未登录
            result.setLoginFlag("0");
        } else {
            result.setLoginFlag("1");
            int count = couponUserClient.getUserCouponCount(Integer.valueOf(userId), "0");
            result.setCouponCount(count);
            if (userVO.getBankOpenAccount() == 1) { // 已开户
                result.setOpenFlag(CustomConstants.FLAG_OPENACCOUNT_YES);
                AccountVO accountVO = accountClient.getAccountByUserId(Integer.valueOf(userId));
                result.setUserInterest(accountVO != null ? (accountVO.getBankInterestSum() != null ? accountVO.getBankInterestSum() : BigDecimal.valueOf(0.00)) : BigDecimal.valueOf(0.00));

                UserInfoVO userinfo = amUserClient.findUsersInfoById(Integer.valueOf(userId));
                if (userinfo != null) {
                    //欢迎语
                    String trueName = userinfo.getTruename();
                    if (StringUtils.isNotEmpty(trueName)) {
                        StringBuffer helloWord = new StringBuffer();
                        int helloFlag = 0;
                        int currentHour = GetDate.getHour(new Date());
                        if (currentHour >= 6 && currentHour < 12) {
                            helloWord.append("上午好，");
                            helloFlag = 0;
                        } else if (currentHour >= 12 && currentHour < 18) {
                            helloWord.append("下午好，");
                            helloFlag = 1;
                        } else {
                            helloWord.append("晚上好，");
                            helloFlag = 2;
                        }
                        helloWord.append(trueName.substring(0, 1));
                        if (userinfo.getSex() == 2) { //女
                            helloWord.append("女士");
                        } else {
                            helloWord.append("先生");
                        }
                        result.setHelloWord(helloWord.toString());
                        result.setHelloFlag(helloFlag);
                    }
                } else {
                    logger.info("查询用户信息userinfo为空");
                }


            } else {
                result.setOpenFlag(CustomConstants.FLAG_OPENACCOUNT_NO);
            }
        }

        // 标的信息 和 统计信息

        TotalInvestAndInterestResponse res2 = baseClient.getExe(AppHomePageDefine.INVEST_INVEREST_AMOUNT_URL,TotalInvestAndInterestResponse.class);
        TotalInvestAndInterestVO totalInvestAndInterestVO = res2.getResult();
        BigDecimal interestSum = totalInvestAndInterestVO.getTotalInterestAmount() == null ? new BigDecimal(0) : totalInvestAndInterestVO.getTotalInterestAmount();
        result.setInterestSum(interestSum.divide(new BigDecimal("100000000")).setScale(0,BigDecimal.ROUND_DOWN).toString());
        //累计上线年数
        Integer yearSum = GetDate.getYearFromDate(PUT_ONLINE_TIME);
        //上线年数
        result.setYearSum(yearSum);
        // 公告
        ContentArticleRequest contentArticleRequest = new ContentArticleRequest();
        contentArticleRequest.setLimitStart(0);
        contentArticleRequest.setLimitEnd(1);
        contentArticleRequest.setNoticeType("2");//  公告类型
        ContentArticleResponse response = baseClient.postExe(NOTICE_LIST_URL,contentArticleRequest,ContentArticleResponse.class);
        List<ContentArticleVO> noticeList = response.getResultList();
        if(!CollectionUtils.isEmpty(noticeList)){
            result.setNoticeInfo(noticeList.get(0));
        }
        // banner
        AdsRequest adsRequest = new AdsRequest();
        adsRequest.setLimitStart(0);
        adsRequest.setLimitEnd(4);
        adsRequest.setTypeId(6);
        adsRequest.setIsIndex(1);

        AppAdsCustomizeResponse res = baseClient.postExe(BANNER_LIST_URL,adsRequest,AppAdsCustomizeResponse.class);
        List<AppAdsCustomizeVO> bannerList = res.getResultList();
        if ( !CollectionUtils.isEmpty(bannerList)){
            result.setBannerList(bannerList);
        }else {
            result.setBannerList(new ArrayList<>());
        }




        //获取新手专区项目信息(仅查第一条)
        ProjectListRequest request = new ProjectListRequest();
        request.setProjectType("HZT");
        request.setBorrowClass("NEW");
        request.setLimitStart(0);
        request.setLimitEnd(1);
        List<WebProjectListCustomizeVO> newProjectList = webProjectListClient.searchProjectList(request);
        result.setNewProjectList(CollectionUtils.isEmpty(newProjectList) ? new ArrayList<>() : newProjectList);

        //首页散标推荐
        request.setProjectType("HZT");
        request.setBorrowClass("");
        request.setLimitStart(0);
        request.setLimitEnd(4);
        List<WebProjectListCustomizeVO> projectList = webProjectListClient.searchProjectList(request);
        result.setProjectList(CollectionUtils.isEmpty(projectList) ? new ArrayList<>() : projectList);


        request = new ProjectListRequest();
        request.setLimitStart(0);
        request.setLimitEnd(4);
        request.setIsHome("1");
        List<HjhPlanCustomizeVO> planList = webProjectListClient.searchPlanList(request);
        result.setHjhPlanList(planList);
        ContentArticleRequest req = new ContentArticleRequest();
        req.setNoticeType(NOTICE_TYPE_COMPANY_DYNAMICS);
        req.setLimitStart(0);
        req.setLimitEnd(1);
        List<ContentArticleVO> list1 = contentArticleClient.searchContentArticleList(req);
        if (!CollectionUtils.isEmpty(list1)){
            for (ContentArticleVO contentArticleVO : list1) {
                if (contentArticleVO.getContent().contains("../../../..")) {
                    contentArticleVO.setContent(contentArticleVO.getContent().replaceAll("../../../..", systemConfig.getWebHost()));
                } else if (contentArticleVO.getContent().contains("src=\"/")) {
                    contentArticleVO.setContent(contentArticleVO.getContent().replaceAll("src=\"/","src=\"" + systemConfig.getWebHost() )+ "//");
                }
                //去除html标签
                contentArticleVO.setContent(HtmlUtil.getTextFromHtml(contentArticleVO.getContent()));
            }
            result.setCompanyArticle(list1.get(0));
        }

        req.setLimitStart(1);
        req.setLimitEnd(3);
        List<ContentArticleVO> list2 = contentArticleClient.searchContentArticleList(req);
        if (!CollectionUtils.isEmpty(list2)){
            for (ContentArticleVO companyDynamics : list2) {
                if (companyDynamics.getContent().contains("../../../..")) {
                    companyDynamics.setContent(companyDynamics.getContent().replaceAll("../../../..", systemConfig.getWebHost() ));
                } else if (companyDynamics.getContent().contains("src=\"/")) {
                    companyDynamics.setContent(companyDynamics.getContent().replaceAll("src=\"/","src=\"" + systemConfig.getWebHost() )+ "//");
                }
            }
        }else{
            list2 = new ArrayList<>();
        }
        result.setCompanyDynamicsList(list2);
        result.setNowTime(GetDate.formatDate(System.currentTimeMillis()));
         /*首页原来接口 有推广session部分,此处暂时不做处理，如果需要再加*/
        webResult.setData(result);
        return webResult;
    }
}
