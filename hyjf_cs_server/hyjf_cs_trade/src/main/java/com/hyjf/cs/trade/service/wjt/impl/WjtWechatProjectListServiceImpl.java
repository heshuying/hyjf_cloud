package com.hyjf.cs.trade.service.wjt.impl;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.HjhAccedeRequest;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.bean.app.AppModuleBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.projectlist.CacheService;
import com.hyjf.cs.trade.service.repay.RepayPlanService;
import com.hyjf.cs.trade.service.wjt.WjtWechatProjectListService;
import com.hyjf.cs.trade.util.CdnUrlUtil;
import com.hyjf.cs.trade.util.HomePageDefine;
import com.hyjf.cs.trade.util.ProjectConstant;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WjtWechatProjectListServiceImpl extends BaseTradeServiceImpl implements WjtWechatProjectListService {


    private static Logger logger = LoggerFactory.getLogger(WjtWechatProjectListServiceImpl.class);


    @Autowired
    private RepayPlanService repayPlanService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private SystemConfig systemConfig;



    /**
     * 获取首页项目列表信息
     *
     * @author pcc
     * @date 2018/7/24 10:46
     */
    @Override
    public WechatHomePageResult getHomeProejctList(int currPage, int pageSize, String showPlanFlag, Integer userId) {
        WechatHomePageResult result = new WechatHomePageResult();

        if(currPage<=0){
            currPage=1;
        }
        if(pageSize<=0){
            pageSize=10;
        }
        WechatHomePageResult vo = new WechatHomePageResult();
        vo.setCurrentPage(currPage);
        vo.setPageSize(pageSize);
        result = getProjectListAsyn(result, currPage, pageSize, showPlanFlag);//加缓存(新手标之外的散标和计划)


        result.setHomeXshProjectList(vo.getHomeXshProjectList() != null?vo.getHomeXshProjectList():new ArrayList<>());
        result.setStatus(HomePageDefine.WECHAT_STATUS_SUCCESS);
        result.setStatusDesc(HomePageDefine.WECHAT_STATUC_DESC);
        return result;
    }



    /**
     * 分页获取首页数据(新手标之外的散标还有计划)
     *
     * @param
     * @return
     */
	@Cached(name="wechatProjectListAsynCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    private WechatHomePageResult getProjectListAsyn(WechatHomePageResult vo, int currentPage, int pageSize, String showPlanFlag) {

        Map<String, Object> projectMap = new HashMap<String, Object>();
        // 汇盈金服app首页定向标过滤
        projectMap.put("publishInstCode", systemConfig.getWjtInstCode());
        int offSet = (currentPage - 1) * pageSize;
        if (offSet == 0 || offSet > 0) {
            projectMap.put("limitStart", offSet);
        }
        if (pageSize > 0) {
            projectMap.put("limitEnd", pageSize + 1);
        }
        if (showPlanFlag != null) {
            projectMap.put("showPlanFlag", showPlanFlag);
        }
        //WechatProjectListResponse response = baseClient.postExe(HomePageDefine.WECHAT_HOME_PROJECT_LIST_URL, projectMap, WechatProjectListResponse.class);

        List<WechatHomeProjectListVO> tempList  = amTradeClient.getWjtWechatProjectList(projectMap);
        logger.info("微信端首页数据 tempList：{}", JSON.toJSONString(tempList));
        List<WechatHomeProjectListVO> list = new ArrayList<>();
        WechatHomeProjectListVO temp ;
        for (WechatHomeProjectListVO vo1 : tempList){
            temp = CommonUtils.convertBean(vo1,WechatHomeProjectListVO.class);
            list.add(temp);
        }

        if (!CollectionUtils.isEmpty(list)) {
            if (list.size() == (pageSize + 1)) {
                list.remove(list.size() - 1);
                // 不是最后一页 每次在每页显示条数的基础上多查一条，然后根据查询结果判断是不是最后一页
                vo.setEndPage(0);
            } else {
                // 是最后一页
                vo.setEndPage(1);
            }
        } else {
            // 是最后一页
            vo.setEndPage(1);
        }
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        for (WechatHomeProjectListVO wechatHomeProjectListCustomize : list) {
            wechatHomeProjectListCustomize.setBorrowApr(FormatRateUtil.formatBorrowApr(wechatHomeProjectListCustomize.getBorrowApr()));
            wechatHomeProjectListCustomize.setBorrowExtraYield(FormatRateUtil.formatBorrowApr(wechatHomeProjectListCustomize.getBorrowExtraYield()));
            if ("0".equals(wechatHomeProjectListCustomize.getOnTime()) || "".equals(wechatHomeProjectListCustomize.getOnTime())) {
                switch (wechatHomeProjectListCustomize.getStatus()) {
                    case "10":
                        wechatHomeProjectListCustomize.setOnTime(wechatHomeProjectListCustomize.getOnTime());
                        break;
                    case "11":
                        wechatHomeProjectListCustomize.setOnTime("立即出借");
                        break;
                    case "12":
                        wechatHomeProjectListCustomize.setOnTime("复审中");
                        break;
                    case "13":
                        wechatHomeProjectListCustomize.setOnTime("还款中");
                        break;
                    case "14":
                        wechatHomeProjectListCustomize.setOnTime("已退出");
                        break;
                    case "16":
                        wechatHomeProjectListCustomize.setOnTime("逾期中");
                }

            } else {
                wechatHomeProjectListCustomize.setOnTime(wechatHomeProjectListCustomize.getOnTime());
            }
            wechatHomeProjectListCustomize.setAccountWait(df.format(new BigDecimal(StringUtils.isBlank(wechatHomeProjectListCustomize.getAccountWait()) ? "0.00" :wechatHomeProjectListCustomize.getAccountWait().replaceAll(",",""))));
        }
        // 字段为null时，转为""
        CommonUtils.convertNullToEmptyString(list);
        vo.setHomeProjectList(list);
        logger.info("微信端散标列表接口 VO:{}", JSON.toJSONString(vo));
        return vo;
    }

}
