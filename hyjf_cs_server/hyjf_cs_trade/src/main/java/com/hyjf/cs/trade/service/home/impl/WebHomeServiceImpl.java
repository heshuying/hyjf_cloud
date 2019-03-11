package com.hyjf.cs.trade.service.home.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.trade.util.CdnUrlUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.ContentArticleRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.config.VersionVO;
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
import com.hyjf.cs.trade.bean.HomeDataResultBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.home.WebHomeService;

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
    private SystemConfig systemConfig;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmConfigClient amConfigClient;


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
            int count = amTradeClient.getUserCouponCount(Integer.valueOf(userId), "0");
            result.setCouponCount(count);
            if (null != userVO.getBankOpenAccount() && userVO.getBankOpenAccount() == 1) { // 已开户
                result.setOpenFlag(CustomConstants.FLAG_OPENACCOUNT_YES);
                AccountVO accountVO = amTradeClient.getAccountByUserId(Integer.valueOf(userId));
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
        // modify by libin 缓存
        //TotalInvestAndInterestResponse res2 = baseClient.getExe(HomePageDefine.INVEST_INVEREST_AMOUNT_URL,TotalInvestAndInterestResponse.class);//加缓存
        TotalInvestAndInterestResponse res2 =amTradeClient.getTotalInvestAndInterestResponse();//加缓存
        TotalInvestAndInterestVO totalInvestAndInterestVO = null;
        if(res2 == null){
        	logger.error("统计信息查询为空");
        }else {
            // modify by libin 缓存
            totalInvestAndInterestVO = res2.getResult();
        }

        BigDecimal interestSum = (totalInvestAndInterestVO == null ||totalInvestAndInterestVO.getTotalInterestAmount() == null) ? new BigDecimal(0) : totalInvestAndInterestVO.getTotalInterestAmount();
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
        
        // mod by libin by jetcache start
/*        ContentArticleResponse response = baseClient.postExe(NOTICE_LIST_URL,contentArticleRequest,ContentArticleResponse.class);
        List<ContentArticleVO> noticeList = response.getResultList();
        if(!CollectionUtils.isEmpty(noticeList)){
            result.setNoticeInfo(noticeList.get(0));
        }*/
        List<ContentArticleVO> noticeList = amTradeClient.getNoticeList(contentArticleRequest);//加缓存
        if(!CollectionUtils.isEmpty(noticeList)){
            ContentArticleVO noticeInfo = noticeList.get(0);
            // 精简首页不要的返回参数，减少网络消耗 begin add by zyk 2018年12月10日11:36:07
            noticeInfo.setImgurl("");
            noticeInfo.setSummary("");
            noticeInfo.setContent("");
            // 精简首页不要的返回参数，减少网络消耗 end add by zyk 2018年12月10日11:36:07
            result.setNoticeInfo(noticeInfo);
        }
        // mod by libin by jetcache end
 
        // banner
        AdsRequest adsRequest = new AdsRequest();
        adsRequest.setLimitStart(0);
        adsRequest.setLimitEnd(4);
        adsRequest.setTypeId(6);
        adsRequest.setIsIndex(0);
        
        // mod by libin by jetcache start
/*        AppAdsCustomizeResponse res = baseClient.postExe(BANNER_LIST_URL,adsRequest,AppAdsCustomizeResponse.class);
        List<AppAdsCustomizeVO> bannerList = res.getResultList();
        if ( !CollectionUtils.isEmpty(bannerList)){
            result.setBannerList(bannerList);
        }else {
            result.setBannerList(new ArrayList<>());
        }*/
        
        List<AppAdsCustomizeVO> AppAdsCustomizeTemp = amTradeClient.getWebHomeBannerList(adsRequest);//加缓存
        List<AppAdsCustomizeVO> bannerList = new ArrayList<>();
        for (AppAdsCustomizeVO vo : AppAdsCustomizeTemp){
            bannerList.add(CommonUtils.convertBean(vo,AppAdsCustomizeVO.class));
        }
        if ( !CollectionUtils.isEmpty(bannerList)){
            String cdnDomainUrl = CdnUrlUtil.getCdnUrl();
            if (StringUtils.isNotBlank(cdnDomainUrl)){
                String image = "";
                for (AppAdsCustomizeVO ads : bannerList){
                    image = ads.getImage();
                    ads.setImage(cdnDomainUrl + image);
                }
            }
            result.setBannerList(bannerList);
        }else {
            result.setBannerList(new ArrayList<>());
        }
        // mod by libin by jetcache end

        //获取新手专区项目信息(仅查第一条)
        ProjectListRequest request = new ProjectListRequest();
        request.setProjectType("HZT");
        request.setBorrowClass("NEW");
        request.setLimitStart(0);
        request.setLimitEnd(1);
        List<WebProjectListCustomizeVO> newProjectList = amTradeClient.searchProjectList(request);//加缓存
        result.setNewProjectList(CollectionUtils.isEmpty(newProjectList) ? new ArrayList<>() : newProjectList);

        //首页散标推荐
        request.setProjectType("HZT");
        request.setBorrowClass("");
        request.setLimitStart(0);
        request.setLimitEnd(4);
        List<WebProjectListCustomizeVO> projectList = amTradeClient.searchProjectList(request);//加缓存
        result.setProjectList(CollectionUtils.isEmpty(projectList) ? new ArrayList<>() : projectList);


        request = new ProjectListRequest();
        request.setLimitStart(0);
        request.setLimitEnd(4);
        request.setIsHome("1");
        List<HjhPlanCustomizeVO> planList = amTradeClient.searchPlanList(request);//加缓存
        result.setHjhPlanList(CollectionUtils.isEmpty(planList) ? new ArrayList<>() :planList);
        ContentArticleRequest req = new ContentArticleRequest();
        req.setNoticeType(NOTICE_TYPE_COMPANY_DYNAMICS);
        // 避免页面上出现未转换的HTML特殊字符 add by huanghui start
        // 直接取前四条数据
        req.setLimitStart(0);
        req.setLimitEnd(3);
        List<ContentArticleVO> companyDynamicsList = amConfigClient.searchContentArticleList(req);
        if(companyDynamicsList != null && companyDynamicsList.size() > 0){
            int thisIndex = 0;
            List<ContentArticleVO> companyDynamicsListSon = new ArrayList<>();
            for (ContentArticleVO companyDynamics : companyDynamicsList) {
                ++thisIndex;
                if (companyDynamics.getContent().contains("../../../..")) {
                    companyDynamics.setContent(companyDynamics.getContent().replaceAll("../../../..", systemConfig.getWebHost()));
                } else if (companyDynamics.getContent().contains("src=\"/")) {
                    companyDynamics.setContent(companyDynamics.getContent().replaceAll("src=\"/","src=\"" + systemConfig.getWebHost())+ "//");
                }

                if (thisIndex > 1){
                    companyDynamicsListSon.add(companyDynamics);
                }else {
                    //去除html标签 <div 等标签
                    companyDynamics.setContent(HtmlUtil.getTextFromHtml(companyDynamics.getContent()));
                    // 避免页面上出现未转换的HTML特殊字符
                    companyDynamics.setContent(StringEscapeUtils.unescapeHtml4(companyDynamics.getContent()));
                }
            }

            // 精简首页不要的返回参数，减少网络消耗 begin add by zyk 2018年12月10日11:42:37
            ContentArticleVO companArticle =  companyDynamicsList.get(0);
            companArticle.setImgurl("");
            companArticle.setSummary("");
            companArticle.setAuthor("");
            if (!CollectionUtils.isEmpty(companyDynamicsListSon)){
                for (ContentArticleVO contentArticleVO : companyDynamicsListSon){
                    contentArticleVO.setImgurl("");
                    contentArticleVO.setSummary("");
                    contentArticleVO.setContent("");
                }
            }
            result.setCompanyArticle(companArticle);
            result.setCompanyDynamicsList(companyDynamicsListSon);
            // 精简首页不需要的返回参数，减少网络消耗 end add by zyk 2018年12月10日11:42:41
        } else {
            result.setCompanyArticle(new ContentArticleVO());
            result.setCompanyDynamicsList(new ArrayList<>());
        }
        // add by huanghui end
        /**req.setLimitStart(0);
        req.setLimitEnd(1);
        List<ContentArticleVO> list1 = amConfigClient.searchContentArticleList(req);
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
        }else{
            result.setCompanyArticle(new ContentArticleVO());
        }

        req.setLimitStart(1);
        req.setLimitEnd(3);
        List<ContentArticleVO> list2 = amConfigClient.searchContentArticleList(req);
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
        */
        result.setNowTime(String.valueOf(System.currentTimeMillis()/1000));
         /*首页原来接口 有推广session部分,此处暂时不做处理，如果需要再加*/
        webResult.setData(result);
        return webResult;
    }

    /**
     * 安卓下载
     * @author zhangyk
     * @date 2018/9/5 11:38
     */
    @Override
    public void androidDownload(HttpServletResponse response) {
        String url = "";
        VersionVO versionVO = amConfigClient.getLastestVersion();
        if( versionVO != null && StringUtils.isNotBlank(versionVO.getUrl())){
            url = versionVO.getUrl().trim();
        }else{
            logger.error("没有获取到安卓app最新版本信息，请核实");
            return;
        }
        try {
            logger.info("android download url = {}",url);
            response.sendRedirect(url);
        } catch (IOException e) {
            logger.error("安卓apk下载失败,{}",e);
        }
    }
    

}
