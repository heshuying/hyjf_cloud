package com.hyjf.cs.trade.service.home.impl;

import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.message.UserDeviceUniqueCodeResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.AppHomePageRequest;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.vo.UserDeviceUniqueCodeVO;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.ConvertUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.bean.app.AppHomePageCustomize;
import com.hyjf.cs.trade.bean.app.AppHomePageRecommendProject;
import com.hyjf.cs.trade.bean.app.AppModuleBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.home.AppHomeService;
import com.hyjf.cs.trade.util.HomePageDefine;
import com.hyjf.cs.trade.util.ProjectConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * APP首页service
 *
 * @author zhangyk
 * @date 2018/7/5 13:45
 */
@Service
public class AppHomeServiceImpl implements AppHomeService {


    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private BaseClient baseClient;

    /** 首页汇计划显示条数*/
    private final int INDEX_HJH_SHOW_SIZE = 1;

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 获取app首页各项数据
     *
     * @author zhangyk
     * @date 2018/7/5 14:24
     */
    @Override
    public JSONObject getAppHomeData(HttpServletRequest request, String userId) {
        JSONObject info = new JSONObject();
        String platform = request.getParameter("realPlatform");
        String version = request.getParameter("version");
        String uniqueIdentifier = request.getParameter("UniqueIdentifier");
        // 是否需要查询新手标的标志
        Boolean getNewProjectFlag = false;


        if (StringUtils.isBlank(platform)) {
            platform = request.getParameter("platform");
        }
        String HOST = systemConfig.getAppFrontHost();

        //首页展示的项目集合
        List<AppProjectListCustomizeVO> list = new ArrayList<>();

        info.put("warning", "市场有风险 投资需谨慎");
        //判断用户是否登录
        Boolean loginFlag = Boolean.FALSE;
        UserVO userVO = null;
        if (StringUtils.isNotBlank(userId)) {
            userVO = amUserClient.findUserById(Integer.valueOf(userId));
            if (userVO != null) {
                loginFlag = Boolean.TRUE;
            }
        }
        if (!loginFlag) {
            String type = "0";  //未注册
            info.put("userType", "0");
            info.put("totalAssets", "0.00");
            info.put("availableBalance", "0.00");
            info.put("accumulatedEarnings", "0.00");
            info.put("coupons", "0");

            //创建首页广告
            createAdPic(info, platform, type, HOST);
            info.put("adDesc", "立即注册");
            //新手标标志
            getNewProjectFlag = Boolean.TRUE;
            this.createProjectNewPage(info, list, HOST);//加缓存

            //获取首页项目列表
            /*this.createProjectListPage(info, version, list, HOST);*/
//            this.createHjhExtensionProjectListPage(info, list, HOST);
            //add by yangchangwei app3.1.1 迁移 2018-10-16
            this.creatNoSignProjectListPage(info,list,HOST);
        } else {

            //查询用户是否开户
            Integer userType = userVO.getBankOpenAccount();
            if (userType == 0) {//未开户
                info.put("userType", "1");
                info.put("totalAssets", "0.00");
                info.put("availableBalance", "0.00");
                info.put("accumulatedEarnings", "0.00");
                info.put("coupons", "0");
                String type = "1";//未开户
                createAdPic(info, platform, type, HOST);
                info.put("adDesc", "立即开户");
                //获取新手标
                getNewProjectFlag = Boolean.TRUE;
                this.createProjectNewPage(info, list, HOST);

                //获取首页项目列表
//                this.createHjhExtensionProjectListPage(info, list, HOST);
                //add by yangchangwei app3.1.1 迁移 2018-10-16
                this.creatNoSignProjectListPage(info,list,HOST);
            } else if (userType == 1) {//已开户
                info.put("userType", "2");

                boolean isnew =false;
                //获取用户累计投资条数
                Integer count = amTradeClient.getTotalInverestCount(userId);
                if (count <= 0 || count == null) {
                    //获取新手标
                    this.createProjectNewPage(info, list, HOST);
                    isnew = true;
                }

                //获取首页项目列表
//                this.createHjhExtensionProjectListPage(info, list, HOST);
                if(isnew){
                    this.creatNoSignProjectListPage(info,list,HOST);
                }else{
                    this.createHjhExtensionProjectListPage_new(info, list, HOST,version);
                }
                //获取用户资产总额
                AccountVO accountVO = amTradeClient.getAccountByUserId(Integer.valueOf(userId));
                info.put("totalAssets", accountVO != null ? DF_FOR_VIEW.format(accountVO.getBankTotal()) : "0.00");
                //获取可用余额
                info.put("availableBalance", accountVO != null ? DF_FOR_VIEW.format(accountVO.getBankBalance()) : "0.00");
                //获取累计收益
                info.put("accumulatedEarnings", accountVO != null ? DF_FOR_VIEW.format(accountVO.getBankInterestSum()) : "0.00");
                //获取(未使用的)优惠券数量
                Integer couponCount = amTradeClient.getUserCouponCount(Integer.valueOf(userId), "0");
                info.put("coupons", couponCount != null ? String.valueOf(couponCount) : "0");
                info.put("adPicUrl", "");
                info.put("adClickPicUrl", "");
            }
        }

//        if (getNewProjectFlag) {
//            this.createProjectNewPage(info, list, HOST);
//        }
        //获取累计投资金额
        TotalInvestAndInterestResponse res = baseClient.getExe(HomePageDefine.INVEST_INVEREST_AMOUNT_URL,TotalInvestAndInterestResponse.class);
        TotalInvestAndInterestVO totalInvestAndInterestVO = res.getResult();
        if (null != totalInvestAndInterestVO){
            BigDecimal totalInvestAmount = totalInvestAndInterestVO.getTotalInvestAmount();
            String totalInvest = totalInvestAmount.toString();
            String totalInvestStr = formatTotalInvest(totalInvest);
            info.put("totalInvestmentAmount", totalInvestStr);
        }else{
            info.put("totalInvestmentAmount", DF_FOR_VIEW.format(new BigDecimal("0")));
        }
        info.put("moduleTotal", "4");
        List<AppModuleBean> moduleList = new ArrayList<>();

        //platform 2.安卓 3.ios

        //添加首页module
        //资金存管
        createModule(moduleList, platform, "android_module1", "ios_module1", HOST);
        //美国上市
        createModule(moduleList, platform, "android_module2", "ios_module2", HOST);
        //运营数据
        AppAdsCustomizeVO appAdsCustomize = getOperationalDataUrl(platform, "android_module3", "ios_module3", HOST);
        String url = "";
        if(appAdsCustomize != null){
            url = appAdsCustomize.getUrl();
        }
        info.put("operationalDataURL",url);
        //关于我们
        createModule(moduleList, platform, "android_module4", "ios_module4", HOST);

        // 为3.1.0加的判断
        String verson3 = "";
        boolean is310 = false;
        if (version!=null&&version.length() >= 5) {
            verson3 = version.substring(0, 5);
        }
        if("3.1.0".equals(verson3)){
            is310 = true;
            if(moduleList.size()<4){
                moduleList.add(moduleList.get(0));
            }
            info.put("projectList", new ArrayList<>());
        }
        info.put("moduleList", moduleList);

        //add by yangchangwei 2018-06-26 app3.0.9 增加协议相关参数
        String agreementUrl = systemConfig.getAppRegistAgreementUrl();
        Map protocalInfo = new HashMap();
        protocalInfo.put("name","《相关协议》");
        protocalInfo.put("URL",HOST + agreementUrl);
        info.put("registerProtocol",protocalInfo);
        //增加公告内容
        this.getAnnouncements(info,HOST);
        Integer days = GetDate.countDate(GetDate.stringToDate("2013-12-23 00:00:00"), new Date());
        info.put("survivalDays",days);//安全运营天数
        //end
        //添加顶部活动图片总数和顶部活动图片数组
        this.createBannerPage(info, platform, request, HOST);//加缓存
        this.createBannerlittlePage(info,getNewProjectFlag);
        this.createPopImgPage(info, uniqueIdentifier);
        this.createForceUpdateInfo(info, platform, version, HOST);

        String serviceAgreementUrl = systemConfig.getAppServiceAgreementUrl();
        String privacyPolicyUrl = systemConfig.getAppPrivacyPolicyUrl();
        info.put("serviceAgreementUrl",HOST + "/public/agreement?name=fwxy");//服务协议
        info.put("privacyPolicyUrl",HOST + "/public/agreement?name=yszc");//隐私政策

        info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
        info.put(CustomConstants.APP_REQUEST,
                ProjectConstant.REQUEST_HOME + HomePageDefine.REQUEST_MAPPING + HomePageDefine.PROJECT_LIST_ACTION);
        CommonUtils.convertNullToEmptyString(info);
        return info;
    }

    /**
     * 格式化金额
     * @param totalInvest
     */
    private String formatTotalInvest(String totalInvest) {

        String[] split = totalInvest.split("\\.");
        String money = split[0];
        String substring ;
        if(money.length() > 8){
            substring = money.substring(0, money.length() - 8);
            totalInvest = substring + "亿元";
        }else if (money.length() <= 8){
            int index = 8 - money.length();
            String flagStr = "";
            for (int i = 0; i < index; i++) {
                flagStr += "0";
            }
            money = flagStr + money;
            substring = money.substring(0, 2);
            totalInvest = "0." + substring + "亿元";
        }
        return totalInvest;
    }


    /**
     * 获取有效的公告内容
     * @param info
     * @param HOST
     */
    private void getAnnouncements(JSONObject info, String HOST) {

        List<AppPushManageVO> manageInfoList = amTradeClient.getAnnouncements();

        // 从am_trade层获取有效公告信息
        if(manageInfoList != null){

            List<Map> announMap = new ArrayList<>();
            for (AppPushManageVO manager : manageInfoList) {
                Map cements = new HashMap();
                String title = manager.getTitle();//标题
                String jumpUrl = manager.getJumpUrl();//跳转URL
                Integer jumpType = manager.getJumpType();//跳转类型
                Integer jumpContent = manager.getJumpContent();//内容类型
                if(2 == jumpContent){//跳转H5页面
                    jumpUrl = HOST + systemConfig.getAppPushManagerUrl();
                    jumpUrl = jumpUrl + manager.getId();
                }
                cements.put("title",title);
                cements.put("URL", jumpUrl);
                announMap.add(cements);
            }
            info.put("announcements",announMap);
        }else{
            logger.info("-------------获取首页列表时，未获得有效公告！---------");
        }
    }

    /**
     * 获取运营数据连接
     * @param platform
     * @param android
     * @param ios
     */
    private AppAdsCustomizeVO getOperationalDataUrl(String platform, String android, String ios, String HOST) {
        Map<String, Object> ads = new HashMap<String, Object>();
        ads.put("limitStart", HomePageDefine.BANNER_SIZE_LIMIT_START);
        ads.put("limitEnd", HomePageDefine.BANNER_SIZE_LIMIT_END);
        ads.put("host", HOST);
        String code = "";
        if (platform.equals("2")) {
            code = android;
        } else if (platform.equals("3")) {
            code = ios;
        }
        ads.put("code", code);
        List<AppAdsCustomizeVO> picList = this.searchBannerList(ads);
        if(picList!=null&&picList.size()>0){
            return picList.get(0);
        }
        return null;

    }

    /**
     * 查询首页的bannner列表
     * @param ads
     * @return
     */
    public List<AppAdsCustomizeVO> searchBannerList(Map<String, Object> ads) {

        AdsRequest request = (AdsRequest) ConvertUtils.convertMapToObject(ads, AdsRequest.class);
        List<AppAdsCustomizeVO> homeBannerList = amTradeClient.getHomeBannerList(request);
        return homeBannerList;
    }

    /**
     *3.0.9 版本重新填充首页推荐标的
     * @param info
     * @param list
     * @param host
     */
    private void createHjhExtensionProjectListPage_new(JSONObject info, List<AppProjectListCustomizeVO> list, String host,String version) {

        List<AppHomePageCustomize> homeHjhPageCustomizes=convertToAppHomePageHJHCustomize(this.createAppExtensionListInfoPage_new(info,version,host),host);//计划
        AppHomePageCustomize appHomePageCustomize = new AppHomePageCustomize();
        if(homeHjhPageCustomizes != null && homeHjhPageCustomizes.size() > 0){
            appHomePageCustomize = homeHjhPageCustomizes.get(0);
        }
        appHomePageCustomize.setTitle("推荐产品");
        CommonUtils.convertNullToEmptyString(appHomePageCustomize);
        AppHomePageRecommendProject recommendProject = convertToAppHomePageRecommendProject(appHomePageCustomize);
        info.put("recommendProject", recommendProject);
    }

    /**
     * 首页推荐标的 3.0.9
     */
    private List<AppProjectListCustomizeVO> createAppExtensionListInfoPage_new(JSONObject info,String version,String HOST) {

        // 构造分页信息 首页只取第一条
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("limitStart", 0);
        params.put("limitEnd", INDEX_HJH_SHOW_SIZE);
        List<HjhPlanCustomizeVO> planList = this.searchIndexHjhExtensionPlanList(params);
        boolean hasInvestment = false;
        //判断是否有可投资计划
        //mod by nxl 智投服务 修改立即加入->授权服务
        for (HjhPlanCustomizeVO hjhPlanCustomize:planList){
            /*if("立即加入".equals(hjhPlanCustomize.getStatusName())){
                hasInvestment = true;
                break;
            }*/
            if("授权服务".equals(hjhPlanCustomize.getStatusName())){
                hasInvestment = true;
                break;
            }
            //mod by nxl 智投服务 修改立即加入->授权服务
        }
        //无可投计划
        if(!hasInvestment){
            List<AppProjectListCustomizeVO> projectList = this.createAppOldUserProject(info, version, HOST);
            if(projectList.size() > 0){
                List<AppProjectListCustomizeVO> list = new ArrayList<AppProjectListCustomizeVO>();
                AppProjectListCustomizeVO customize = projectList.get(0);
                customize.setTag("优质资产");
                customize.setBorrowDesc("项目剩余" + customize.getBorrowAccountWait());
                list.add(customize);
                return  list;
            }else{
                planList = this.selectIndexHjhExtensionPlanListByLockTime(params);
            }

        }
        List<AppProjectListCustomizeVO> projectListCustomizes = convertToAppProjectList(planList,HOST);
        for (AppProjectListCustomizeVO appInfo :
                projectListCustomizes) {
            appInfo.setTag("稳健回报");
            appInfo.setBorrowDesc("开放额度" + appInfo.getAvailableInvestAccount());
        }
        return projectListCustomizes;
    }

    /**
     * 首页汇计划推广计划列表 - 首页显示 ②	若没有可投计划，则显示锁定期限短的
     */
    public List<HjhPlanCustomizeVO> selectIndexHjhExtensionPlanListByLockTime(Map<String, Object> params) {
        AppHomePageRequest request = (AppHomePageRequest) ConvertUtils.convertMapToObject(params, AppHomePageRequest.class);
        List<HjhPlanCustomizeVO> hjhPlanList = this.amTradeClient.selectIndexHjhExtensionPlanListByLockTime(request);
        return hjhPlanList;
    }

    /**
     * 首页汇计划推广计划列表 - 首页显示
     */
	@Cached(name="appIndexHjhExtensionPlanListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public List<HjhPlanCustomizeVO> searchIndexHjhExtensionPlanList(Map<String, Object> params) {
        AppHomePageRequest request  = (AppHomePageRequest) ConvertUtils.convertMapToObject(params,AppHomePageRequest.class);
        List<HjhPlanCustomizeVO> hjhPlanList = this.amTradeClient.selectIndexHjhExtensionPlanList(request);
        return hjhPlanList;
    }

    /**
     * 查询已登录投资用户的项目详情
     * @param info
     * @param version
     * @param HOST
     */
    private List<AppProjectListCustomizeVO> createAppOldUserProject(JSONObject info, String version, String HOST) {
        Map<String, Object> projectMap = new HashMap<String, Object>();
        projectMap.put("host", HOST + HomePageDefine.REQUEST_HOME + HomePageDefine.PROJECT_REQUEST_MAPPING
                + HomePageDefine.PROJECT_DETAIL_ACTION);
        projectMap.put("userId", info.getString("userId"));
        projectMap.put("version", StringUtils.isEmpty(version)?"":version);

        // 汇盈金服app首页定向标过滤
        projectMap.put("publishInstCode", CustomConstants.HYJF_INST_CODE);
        List<AppProjectListCustomizeVO> list = this.searchProjectList_new(projectMap);
        return list;
    }

    /***
     * 查询首页项目列表 3.0.9
     */
    public List<AppProjectListCustomizeVO> searchProjectList_new(Map<String, Object> projectMap) {

        List<AppProjectListCustomizeVO> projectList = new ArrayList<>();

        AppHomePageRequest request = (AppHomePageRequest) ConvertUtils.convertMapToObject(projectMap, AppHomePageRequest.class);
        //查询首页定时发标,投资中,复审中的项目
        List<AppProjectListCustomizeVO> list = amTradeClient.selectHomeProjectList(request);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                AppProjectListCustomizeVO appProjec = list.get(i);
                String status = appProjec.getStatus();
                if("10".equals(status) || "11".equals(status)){
                    projectList.add(appProjec);
                }
            }
        }
        return projectList;
    }

    /**
     * 填充新手标信息
     * @param info
     * @param list
     * @param host
     */
    private void creatNoSignProjectListPage(JSONObject info, List<AppProjectListCustomizeVO> list, String host) {
        List<AppHomePageCustomize> homePageCustomizes = convertToAppHomePageCustomize(list,host);//新手标
        AppHomePageCustomize pageCustomize = homePageCustomizes.get(0);
        pageCustomize.setTitle("新手专享");
        pageCustomize.setTag("新手限投1次");
        pageCustomize.setBorrowDesc("100起投，最高投资5000");
        CommonUtils.convertNullToEmptyString(pageCustomize);
        AppHomePageRecommendProject recommendProject = convertToAppHomePageRecommendProject(pageCustomize);
        info.put("recommendProject", recommendProject);
    }


    /**
     * app3.0.9 组装推荐项目 add by cwyang
     * @param appHomePageCustomize
     * @return
     */
    private AppHomePageRecommendProject convertToAppHomePageRecommendProject(AppHomePageCustomize appHomePageCustomize) {
        AppHomePageRecommendProject project = new AppHomePageRecommendProject();
        project.setTitle(appHomePageCustomize.getTitle());
        project.setTag(appHomePageCustomize.getTag());
        project.setBorrowNid(appHomePageCustomize.getBorrowNid());
        project.setStatus(appHomePageCustomize.getStatus());
        project.setBorrowUrl(appHomePageCustomize.getBorrowUrl());
        project.setBorrowName(appHomePageCustomize.getBorrowName());
        project.setBorrowType(appHomePageCustomize.getBorrowType());
        project.setBorrowApr(appHomePageCustomize.getBorrowApr());
        project.setBorrowPeriod(appHomePageCustomize.getBorrowTheSecondDesc() + appHomePageCustomize.getBorrowTheSecond());
        project.setBorrowDesc(appHomePageCustomize.getBorrowDesc());
        project.setButtonText(appHomePageCustomize.getStatusName());
        // 产品加息
        project.setBorrowExtraYield(appHomePageCustomize.getBorrowExtraYield());
        project.setBorrowTheFirstDesc(appHomePageCustomize.getBorrowTheFirstDesc());
        return project;
    }

    /**
     * 查询首页项目列表
     * @param info
     */
    private void createHjhExtensionProjectListPage(JSONObject info,List<AppProjectListCustomizeVO> appProjectListCustomizeList, String HOST) {

        List<AppHomePageCustomize> homePageCustomizes = convertToAppHomePageCustomize(appProjectListCustomizeList,HOST);//新手标
        List<AppHomePageCustomize> homeHjhPageCustomizes=convertToAppHomePageHJHCustomize(this.createHjhExtensionListInfoPage(HOST),HOST);//计划
        if (appProjectListCustomizeList.size() == 0){
            homePageCustomizes.addAll(homeHjhPageCustomizes);
        }else {
            if(homeHjhPageCustomizes!=null&&homeHjhPageCustomizes.size()==3){
                homePageCustomizes.add(homeHjhPageCustomizes.get(0));
                homePageCustomizes.add(homeHjhPageCustomizes.get(1));
            } else {
                homePageCustomizes.addAll(homeHjhPageCustomizes);
            }

        }
        CommonUtils.convertNullToEmptyString(homePageCustomizes);
        info.put("projectTotal",homePageCustomizes.size());
        info.put("projectList", homePageCustomizes);
    }

    /**
     * 首页汇计划列表
     */
    private List<AppProjectListCustomizeVO> createHjhExtensionListInfoPage(String HOST) {

        // 构造分页信息 首页只取前三条
        HjhPlanRequest request = new HjhPlanRequest();
        request.setLimitStart(0);
        request.setLimitEnd(3);
        List<HjhPlanCustomizeVO> planList = amTradeClient.getAppHomePlanList(request);
        boolean hasInvestment = false;
        //判断是否有可投资计划
        for (HjhPlanCustomizeVO hjhPlanCustomize:planList){
            /*if("立即加入".equals(hjhPlanCustomize.getStatusName())){
                hasInvestment = true;
                break;
            }*/
            // mod by nxl 智投服务 立即加入->授权服务
            if("授权服务".equals(hjhPlanCustomize.getStatusName())){
                hasInvestment = true;
                break;
            }

        }
        //无可投计划
        if(!hasInvestment){
            request.setLockFlag("1");
            planList =  amTradeClient.getAppHomePlanList(request);
        }
        List<AppProjectListCustomizeVO> projectListCustomizes = convertToAppProjectList(planList,HOST);
        return projectListCustomizes;
    }


    /**
     * 适应客户端返回数据格式
     * @param planList
     * @return
     */
    private List<AppProjectListCustomizeVO> convertToAppProjectList(List<HjhPlanCustomizeVO> planList, String HOST) {
        List<AppProjectListCustomizeVO> appProjectList = null;
        String url = "";
        AppProjectListCustomizeVO appProjectListCustomize;
        if (!CollectionUtils.isEmpty(planList)) {
            appProjectList = new ArrayList<AppProjectListCustomizeVO>();
            for (HjhPlanCustomizeVO entity : planList) {
                appProjectListCustomize = new AppProjectListCustomizeVO();
                appProjectListCustomize.setStatus(entity.getStatus());
                appProjectListCustomize.setBorrowName(entity.getPlanName());
                appProjectListCustomize.setPlanApr(entity.getPlanApr());
                appProjectListCustomize.setBorrowApr(entity.getPlanApr());
                appProjectListCustomize.setPlanPeriod(entity.getPlanPeriod());
                appProjectListCustomize.setBorrowPeriod(entity.getPlanPeriod());
                appProjectListCustomize.setAvailableInvestAccount(entity.getAvailableInvestAccount());
                appProjectListCustomize.setStatusName(entity.getStatusName());
                appProjectListCustomize.setBorrowNid(entity.getPlanNid());
                String couponEnable = entity.getCouponEnable();
                if (org.apache.commons.lang.StringUtils.isEmpty(couponEnable) || "0".equals(couponEnable)) {
                    couponEnable = "0";
                } else {
                    couponEnable = "1";
                }
                appProjectListCustomize.setCouponEnable(couponEnable);
                // 项目详情url
                url = HOST + ProjectConstant.REQUEST_HOME + HomePageDefine.PLAN
                        + HomePageDefine.HJH_PLAN_DETAIL_ACTION + "?planNid='" + entity.getPlanNid() + "'";
                appProjectListCustomize.setBorrowUrl(url);
                appProjectListCustomize.setProjectType("HJH");
                appProjectListCustomize.setBorrowType("HJH");

                // 应客户端要求，返回空串
                CommonUtils.convertNullToEmptyString(appProjectListCustomize);
                appProjectList.add(appProjectListCustomize);
            }
        }
        return appProjectList;
    }


    /**
     * 适应客户端数据返回HJH
     * @param list
     * @return
     */
    private List<AppHomePageCustomize> convertToAppHomePageHJHCustomize(List<AppProjectListCustomizeVO> list, String HOST) {
        List<AppHomePageCustomize> homePageCustomizes = new ArrayList<>();
        for (AppProjectListCustomizeVO listCustomize : list) {
            AppHomePageCustomize homePageCustomize = new AppHomePageCustomize();
            homePageCustomize.setBorrowNid(listCustomize.getBorrowNid());
            homePageCustomize.setBorrowName( listCustomize.getBorrowName());
            homePageCustomize.setBorrowDesc("计划");
            homePageCustomize.setBorrowDesc(listCustomize.getBorrowDesc());
            homePageCustomize.setBorrowType(listCustomize.getBorrowType());
            homePageCustomize.setBorrowTheFirst(listCustomize.getBorrowApr() + "%");
            // mod by nxl 智投服务 计划的历史回报率->参考年回报率
//			homePageCustomize.setBorrowTheFirstDesc("历史年回报率");
            homePageCustomize.setBorrowTheFirstDesc("参考年回报率");
            homePageCustomize.setBorrowTheSecond(listCustomize.getBorrowPeriod());
            // mod by nxl 智投服务 修改显示 start
//			homePageCustomize.setBorrowTheSecondDesc("锁定期限");
            homePageCustomize.setBorrowTheSecondDesc("服务回报期限");
            PlanDetailCustomizeVO planDetailCustomizeVO = amTradeClient.getPlanDetailByPlanNid(listCustomize.getBorrowNid());
            String statusNameDesc = planDetailCustomizeVO != null ? planDetailCustomizeVO.getAvailableInvestAccount() : "0.00";
            if(StringUtils.isNotBlank(statusNameDesc)){
                BigDecimal openAmount = new BigDecimal(statusNameDesc);
                if(openAmount.compareTo(BigDecimal.ZERO) != 0){
                    homePageCustomize.setStatusNameDesc("额度" + DF_FOR_VIEW.format(openAmount));
                } else {
                    homePageCustomize.setStatusNameDesc("");
                }
            }

            homePageCustomize.setBorrowUrl(HOST + HomePageDefine.PLAN + listCustomize.getBorrowNid());
            homePageCustomize.setBorrowApr(listCustomize.getBorrowApr() + "%");
            homePageCustomize.setBorrowPeriod(listCustomize.getBorrowPeriod());
            String borrowExtraYield = listCustomize.getBorrowExtraYield();
            if(StringUtils.isNotBlank(borrowExtraYield)){
                borrowExtraYield = borrowExtraYield.substring(1,borrowExtraYield.length());
            }
            homePageCustomize.setBorrowExtraYield(borrowExtraYield);
            String status = listCustomize.getStatus();
            if ("稍后开启".equals(listCustomize.getStatusName())){    //1.启用  2.关闭
                // 20.立即加入  21.稍后开启
                homePageCustomize.setStatus("21");
                homePageCustomize.setStatusName("稍后开启");
            }
            // mod by nxl 智投服务 立即加入->授权服务
            /*else if("立即加入".equals(listCustomize.getStatusName())){  //1.启用  2.关闭
                homePageCustomize.setStatus("20");
                homePageCustomize.setStatusName("立即加入");
            }*/else if(listCustomize.getStatusName().equals("授权服务")){  //1.启用  2.关闭
                homePageCustomize.setStatus("20");
                homePageCustomize.setStatusName("授权服务");
            }
            homePageCustomize.setOnTime(listCustomize.getOnTime());
            homePageCustomize.setBorrowSchedule(listCustomize.getBorrowSchedule());
            homePageCustomize.setBorrowTotalMoney(StringUtils.isBlank(listCustomize.getBorrowTotalMoney())?"0":listCustomize.getBorrowTotalMoney());
            homePageCustomize.setTag(listCustomize.getTag());
            homePageCustomizes.add(homePageCustomize);
        }
        CommonUtils.convertNullToEmptyString(homePageCustomizes);
        return homePageCustomizes;
    }



    /**
     * 适应客户端数据返回
     * @param list
     * @return
     */
    private List<AppHomePageCustomize> convertToAppHomePageCustomize(List<AppProjectListCustomizeVO> list, String HOST) {
        List<AppHomePageCustomize> homePageCustomizes = new ArrayList<>();
        for (AppProjectListCustomizeVO listCustomize : list) {
            AppHomePageCustomize homePageCustomize = new AppHomePageCustomize();
            homePageCustomize.setBorrowNid(listCustomize.getBorrowNid());
            if (listCustomize.getBorrowNid().startsWith("NEW")) {
                homePageCustomize.setBorrowName( listCustomize.getBorrowNid());
                homePageCustomize.setBorrowDesc("新手");
            } else {
                homePageCustomize.setBorrowName(listCustomize.getBorrowNid());
                homePageCustomize.setBorrowDesc("散标");
            }
            // 产品加息
            homePageCustomize.setBorrowExtraYield(listCustomize.getBorrowExtraYield());
            homePageCustomize.setBorrowType(listCustomize.getBorrowType());
            homePageCustomize.setBorrowTheFirst(listCustomize.getBorrowApr() + "%");
            homePageCustomize.setBorrowTheFirstDesc("历史年回报率");
            homePageCustomize.setBorrowTheSecond(listCustomize.getBorrowPeriod());
            homePageCustomize.setBorrowTheSecondDesc("项目期限");

            //定时开标
            String onTime = listCustomize.getOnTime();
            if(!("0".equals(onTime)||"".equals(onTime))){
                homePageCustomize.setStatusName(onTime);
            } else {
                homePageCustomize.setStatusName("立即投资");
            }

           // PlanDetailCustomizeVO planDetailCustomizeVO = amTradeClient.getPlanDetailByPlanNid(listCustomize.getBorrowNid());
            BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(listCustomize.getBorrowNid());
            String statusNameDesc = borrow != null ? String.valueOf(borrow.getBorrowAccountWait()) : "0.00";
            homePageCustomize.setStatusNameDesc("剩余" + DF_FOR_VIEW.format(new BigDecimal(statusNameDesc)));
            homePageCustomize.setBorrowUrl(HOST + HomePageDefine.BORROW + listCustomize.getBorrowNid());
            homePageCustomize.setBorrowApr(listCustomize.getBorrowApr() + "%");
            homePageCustomize.setBorrowPeriod(listCustomize.getBorrowPeriod());
            homePageCustomize.setStatus(listCustomize.getStatus());
            homePageCustomize.setOnTime(listCustomize.getOnTime());
            homePageCustomize.setBorrowSchedule(listCustomize.getBorrowSchedule());
            homePageCustomize.setBorrowTotalMoney(StringUtils.isBlank(listCustomize.getBorrowTotalMoney())?"0":listCustomize.getBorrowTotalMoney());
            homePageCustomizes.add(homePageCustomize);
        }
        return homePageCustomizes;
    }


    /**
     *
     * 获取IOS强制更新
     * @author hsy
     * @param info
     */
    private void createForceUpdateInfo(JSONObject info, String platform, String version, String HOST){
        // 从配置文件中加载配置信息
        String noticeStatus = systemConfig.noticeStatus;
        String noticeUrlIOS = systemConfig.iosNoticeRequestUrl;
        String iosVersion = systemConfig.iosNoticeVersion;
        noticeUrlIOS = HOST + ProjectConstant.REQUEST_HOME + "/" + noticeUrlIOS+"?version="+version;

        boolean isNeedUpdate = false;
        if(StringUtils.isEmpty(version)){
            isNeedUpdate = true;
        }else if(version.length()>=5){
            version = version.substring(0, 5);
        }

        if( version != null && version.compareTo(iosVersion)<0){
            isNeedUpdate = true;
        }

//       System.out.println("noticeStatus: " + noticeStatus);
        // 是否需要收到通知
        if("true".equals(noticeStatus) && StringUtils.isNotBlank(platform) && "3".equals(platform) && isNeedUpdate){
            info.put("needForcedToUpdate", "1");
            info.put("forcedToUpdateUrl", noticeUrlIOS);
        }else {
            info.put("needForcedToUpdate", "0");
            info.put("forcedToUpdateUrl", "");
        }

    }

    /**
     * 查询首页banner图
     *
     * @param info
     */
    private void createBannerPage(JSONObject info, String platform, HttpServletRequest request, String HOST) {

        AdsRequest adsRequest = new AdsRequest();
        adsRequest.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        adsRequest.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        adsRequest.setHost(HOST);
        String code = "";
        if ("2".equals(platform)) {
            code = "android_banner";
            adsRequest.setPlatformType("1");
        } else if ("3".equals(platform)) {
            code = "ios_banner";
            adsRequest.setPlatformType("2");
        }
        adsRequest.setCode(code);
        List<AppAdsCustomizeVO> picList =amTradeClient.getHomeBannerList(adsRequest);
        if (picList != null && picList.size() > 0) {
            for(AppAdsCustomizeVO appAdsCustomize : picList){
                appAdsCustomize.setPicUrl(appAdsCustomize.getImage());
                // 安卓需加sign和token
                if ("2".equals(platform)) {
                    String picH5Url = appAdsCustomize.getUrl();
                    appAdsCustomize.setPicH5Url(CommonUtils.concatReturnUrl(request, picH5Url));
                } else {
                    appAdsCustomize.setPicH5Url(appAdsCustomize.getUrl());
                }
            }
            info.put("picList", picList);
            info.put("picTotal", String.valueOf(picList.size()));
        } else {
            info.put("picList", new ArrayList<AppAdsCustomizeVO>());
            info.put("picTotal", "0");
        }
    }


    /**
     *
     * 首页横幅广告
     * @param info
     */
    private void createBannerlittlePage(JSONObject info,Boolean isNewFlag) {
        AdsRequest request = new AdsRequest();
        request.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        request.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        request.setHost(systemConfig.fileDomainUrl);
        request.setCode("bannerlittle");
        List<AppAdsCustomizeVO> picList = amTradeClient.getHomeBannerList(request);
        if (picList != null && picList.size() > 0) {
            AppAdsCustomizeVO appads = picList.get(0);
            System.out.println("adImageUrl: " + appads.getImage());
            System.out.println("adImageUrlOperation: " + appads.getUrl());
            info.put("adImageUrl", appads.getImage());
            info.put("adImageUrlOperation", appads.getUrl());

            if("2".equals(appads.getNewUserShow()) || ("1".equals(appads.getNewUserShow()) && isNewFlag)){
                info.put("adExist", "1");
            }else{
                info.put("adExist", "0");
            }
        } else {
            info.put("adExist", "0");
            info.put("adImageUrl", "");
            info.put("adImageUrlOperation", "");
        }
        //System.out.println("jsonInfoBannerLittle: " + info.toJSONString());
    }


    /**
     *
     * 首页弹窗广告图片
     * @author hsy
     * @param info
     */
    private void createPopImgPage(JSONObject info, String uniqueIdentifier) {
        AdsRequest request = new AdsRequest();
        request.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        request.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        request.setHost(systemConfig.fileDomainUrl);
        request.setCode("popup");

        Integer userId = info.get("userId") == null? null: (Integer)info.get("userId");
        List<AppAdsCustomizeVO> picList = amTradeClient.getHomeBannerList(request);
        if (picList != null && picList.size() > 0) {
            AppAdsCustomizeVO appads = picList.get(0);
            info.put("imageUrl", appads.getImage());
            info.put("imageUrlOperation", appads.getUrl());
            uniqueIdentifier = "this is test uniqueidellll";
            int requestTimes = updateCurrentDayRequestTimes(uniqueIdentifier,userId);
            if("2".equals(appads.getNewUserShow()) || ("1".equals(appads.getNewUserShow()) && userId == null)){
                if(requestTimes <= 1){
               // if (true){
                    // 如果是今天第一次请求则显示
                    info.put("imageUrlExist", "1");
                }else{
                    // 不是今天第一次请求则不显示
                    info.put("imageUrlExist", "0");
                }

            }else{
                info.put("imageUrlExist", "0");
            }
        } else {

            info.put("imageUrlExist", "0");
            info.put("imageUrl", "");
            info.put("imageUrlOperation", "");
        }
    }

    private int updateCurrentDayRequestTimes(String uniqueIdentifier,Integer userId){
        // 如果没有收到设备唯一码在返回0次
        if(StringUtils.isEmpty(uniqueIdentifier)){
            return 0;
        }
        String baseUrl= "http://CS-MESSAGE/cs-message/userdeviceuniquecode/";
        String url = baseUrl + uniqueIdentifier;
        UserDeviceUniqueCodeResponse response = baseClient.getExe(url,UserDeviceUniqueCodeResponse.class);
        List<UserDeviceUniqueCodeVO> list = response.getResultList();
        if (CollectionUtils.isEmpty(list)){
            UserDeviceUniqueCodeVO vo = new UserDeviceUniqueCodeVO();
            vo.setCurrentDay(GetDate.formatDate());
            vo.setRequestTimesDay(1);
            vo.setUpdateTime(GetDate.getNowTime10());
            if (null != userId){
                vo.setUserId(userId);
            }
            String insertUrl = baseUrl + "insert";
            baseClient.postExe(insertUrl,vo, UserDeviceUniqueCodeResponse.class);
            return 0;
        }else{
            UserDeviceUniqueCodeVO vo = list.get(0);
            String updateUrl = baseUrl + "update";
            // 有记录但不是今天
            if (!GetDate.formatDate().equals(vo.getCurrentDay())){
                vo.setCurrentDay(GetDate.formatDate());
                vo.setRequestTimesDay(1);
                vo.setUpdateTime(GetDate.getNowTime10());
                if (null != userId){
                    vo.setUserId(userId);
                }
                baseClient.postExe(updateUrl,vo,UserDeviceUniqueCodeResponse.class);
                return 0;
            }else{
                int times = vo.getRequestTimesDay() +1;
                vo.setRequestTimesDay(times);
                vo.setUpdateTime(GetDate.getNowTime10());
                if (null != userId){
                    vo.setUserId(userId);
                }
                baseClient.postExe(updateUrl,vo,UserDeviceUniqueCodeResponse.class);
                return times;

            }
        }

    }

    /**
     * 创建首页module
     *
     * @param moduleList
     * @param platform
     * @param android
     * @param ios
     */
    private void createModule(List<AppModuleBean> moduleList, String platform, String android, String ios, String HOST) {
        AdsRequest adsRequest = new AdsRequest();
        adsRequest.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        adsRequest.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        adsRequest.setHost(HOST);
        String code = "";
        if ("2".equals(platform)) {
            code = android;
            adsRequest.setPlatformType("1");
        } else if ("3".equals(platform)) {
            code = ios;
            adsRequest.setPlatformType("2");
        }
        adsRequest.setCode(code);
        List<AppAdsCustomizeVO> picList = amTradeClient.getHomeBannerList(adsRequest);
        if (picList != null && picList.size() > 0) {
            AppModuleBean appModule = new AppModuleBean();
            appModule.setModuleUrl(picList.get(0).getImage());
            appModule.setModuleH5Url(picList.get(0).getUrl());
            moduleList.add(appModule);
        } else {
            AppModuleBean appModule = new AppModuleBean();
            appModule.setModuleUrl("");
            appModule.setModuleH5Url("");
            moduleList.add(appModule);
        }

    }


    /**
     * 获取新手标数据
     *
     */
    private void createProjectNewPage(JSONObject info, List<AppProjectListCustomizeVO> list, String HOST) {
        String host = HOST + ProjectConstant.REQUEST_HOME + ProjectConstant.REQUEST_MAPPING
                + ProjectConstant.PROJECT_DETAIL_ACTION;
        List<AppProjectListCustomizeVO> projectList = searchProjectNew(host);
        Integer userId = info.getInteger("userId");
        boolean isNewUser = this.checkNewUser(userId);

        if(isNewUser){
            if(!CollectionUtils.isEmpty(projectList)){
                AppProjectListCustomizeVO project = projectList.get(0);
                if (list.size() == 0) {
                    list.add(project);
                }
                info.put("sprogExist", "1");
                info.put("sprogBorrowApr", project.getBorrowApr());
                info.put("sprogBorrowPeriod", project.getBorrowPeriod());
                String balance = project.getBorrowAccountWait();//add by cwyang 根据borrowNid 获取项目可投金额
                info.put("sprogBorrowMoney", balance);//新手标取得是可投余额不是投资总额 add by cwyang
                info.put("sprogBorrowNid", project.getBorrowNid());
                info.put("sprogBorrowUrl", project.getBorrowUrl());
                info.put("sprogTime", project.getOnTime());
                if(!Validator.isIncrease(project.getIncreaseInterestFlag(), project.getBorrowExtraYieldOld())){
                    project.setBorrowExtraYield("");
                }
                info.put("borrowExtraYield", project.getBorrowExtraYield());
            }else {
                info.put("sprogExist", "0");
                info.put("sprogBorrowApr", "");
                info.put("sprogBorrowPeriod", "");
                info.put("sprogBorrowMoney", "");
                info.put("sprogBorrowNid", "");
                info.put("sprogBorrowUrl", "");
                info.put("sprogTime", "");
                info.put("borrowExtraYield", "");
            }
        }else {
            info.put("sprogExist", "0");
            info.put("sprogBorrowApr", "");
            info.put("sprogBorrowPeriod", "");
            info.put("sprogBorrowMoney", "");
            info.put("sprogBorrowNid", "");
            info.put("sprogBorrowUrl", "");
            info.put("sprogTime", "");
            info.put("borrowExtraYield", "");
        }

    }
    /**
     *
     * 检查是否是新手(未登录或已登录未投资)
     * @author sunpeikai
     * @param
     * @return
     */
    private boolean checkNewUser(Integer userId){
        //未登录则认为是新用户
        if(userId == null || userId <= 0){
            return true;
        }

        int tenderCount = amTradeClient.selectUserTenderCount(userId);

        return tenderCount <= 0;
    }

    private List<AppProjectListCustomizeVO> searchProjectNew(String host) {
        List<AppProjectListCustomizeVO> projectList = new ArrayList<>();
        // 取得新手汇项目（投资中）
        String statusNewInvest = "15";
        AppProjectListRequest request = new AppProjectListRequest();
        request.setStatus(statusNewInvest);
        request.setType("4");
        request.setHost(host);
        // 查询首页定时发标的项目
        List<AppProjectListCustomizeVO> listNewInvest = amTradeClient.searchAppProjectList(request);
        if (listNewInvest != null && listNewInvest.size() > 0) {
            for (int i = 0; i < listNewInvest.size(); i++) {
                AppProjectListCustomizeVO newInvest = listNewInvest.get(i);
                if(!Validator.isIncrease(newInvest.getIncreaseInterestFlag(), newInvest.getBorrowExtraYieldOld())){
                    newInvest.setBorrowExtraYield("");
                }
                projectList.add(newInvest);
            }
            return projectList;
        }
        // 新手汇项目（投资中）为空
        // 取得新手汇项目（定时发标）
        String statusNewOnTime = "14";
        request.setStatus(statusNewOnTime);
        // 查询首页定时发标的项目
        List<AppProjectListCustomizeVO> listNewOnTime = amTradeClient.searchAppProjectList(request);
        if (listNewOnTime != null && listNewOnTime.size() > 0) {
            for (int i = 0; i < listNewOnTime.size(); i++) {
                AppProjectListCustomizeVO newOnTime = listNewOnTime.get(i);
                if(!Validator.isIncrease(newOnTime.getIncreaseInterestFlag(), newOnTime.getBorrowExtraYieldOld())){
                    newOnTime.setBorrowExtraYield("");
                }
                projectList.add(newOnTime);
            }
            return projectList;
        }

        //复审
        String status = "16";
        request.setStatus(status);
        List<AppProjectListCustomizeVO> reviewList = amTradeClient.searchAppProjectList(request);
        if (reviewList != null && reviewList.size() > 0) {
            for (int i = 0; i < reviewList.size(); i++) {
                AppProjectListCustomizeVO newOnTime = reviewList.get(i);
                if(!Validator.isIncrease(newOnTime.getIncreaseInterestFlag(), newOnTime.getBorrowExtraYieldOld())){
                    newOnTime.setBorrowExtraYield("");
                }
                projectList.add(newOnTime);
            }
            return projectList;
        }
        //还款
        status = "17";
        request.setStatus(status);
        List<AppProjectListCustomizeVO> repaymentList = amTradeClient.searchAppProjectList(request);
        if (repaymentList != null && repaymentList.size() > 0) {
            for (int i = 0; i < repaymentList.size(); i++) {
                AppProjectListCustomizeVO newOnTime = repaymentList.get(i);
                if(!Validator.isIncrease(newOnTime.getIncreaseInterestFlag(), newOnTime.getBorrowExtraYieldOld())){
                    newOnTime.setBorrowExtraYield("");
                }
                projectList.add(newOnTime);
            }
            return projectList;
        }

        return projectList;
    }

    /**
     * 创建首页广告
     *
     * @param info
     * @param platform
     */
    private void createAdPic(JSONObject info, String platform, String type, String HOST) {
        AdsRequest adsRequest = new AdsRequest();
        adsRequest.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        adsRequest.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        adsRequest.setHost(HOST);
        String code = "";
        if ("2".equals(platform)) {
            if ("0".equals(type)) {//未注册
                code = "android_regist_888";
            } else if ("1".equals(type)) {
                code = "android_open_888";
            }
        } else if ("3".equals(platform)) {
            if ("0".equals(type)) {//未注册
                code = "ios_regist_888";
            } else if ("1".equals(type)) {
                code = "ios_open_888";
            }
        }
        adsRequest.setCode(code);
        List<AppAdsCustomizeVO> picList = amTradeClient.getHomeBannerList(adsRequest);
        if (!CollectionUtils.isEmpty(picList)) {
            info.put("adPicUrl", picList.get(0).getImage());
            info.put("adClickPicUrl", picList.get(0).getUrl());
        } else {
            info.put("adPicUrl", "");
            info.put("adClickPicUrl", "");
        }
    }
}
