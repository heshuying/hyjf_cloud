package com.hyjf.cs.trade.service.home.impl;

import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.DebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhAccedeRequest;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.bean.app.AppBorrowProjectInfoBeanVO;
import com.hyjf.cs.trade.bean.app.AppModuleBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.home.WechatProjectListService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.projectlist.CacheService;
import com.hyjf.cs.trade.service.repay.RepayPlanService;
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

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WechatProjectListServiceImpl extends BaseTradeServiceImpl implements WechatProjectListService {


    private static Logger logger = LoggerFactory.getLogger(WechatProjectListServiceImpl.class);

    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");


    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private RepayPlanService repayPlanService;

    @Autowired
    private AuthService authService;
    @Autowired
    private BaseClient baseClient;

    @Autowired
    private CacheService cacheService;


    @Autowired
    private SystemConfig systemConfig;


    /**
     * 获取散标详情
     *
     * @author zhangyk
     * @date 2018/7/2 11:33
     */
    @Override
    public JSONObject getProjectDetail(String borrowNid, String type, Integer userId) {
        CheckUtil.check(StringUtils.isNotBlank(borrowNid), MsgEnum.ERR_PARAM_NUM);
        JSONObject userValidation = new JSONObject();

        JSONObject borrowDetailResultBean = new JSONObject();
        //获取用户个人信息
        boolean isLogined = false;
        boolean isOpened = false;
        boolean isSetPassword = false;
        boolean isAllowed = false;
        String isRiskTested = "0";
        boolean isAutoInves = false;
        boolean isInvested = false;
        Integer isPaymentAuth = 0;
        Integer roleId = 0;

        if (userId != null && userId > 0) {
            UserVO users = amUserClient.findUserById(userId);
            if (users != null) {
                //判断是否开户
                isLogined = true;
                if (users.getBankOpenAccount() != null && users.getBankOpenAccount() == 1) {
                    isOpened = true;
                }
                // 检查用户角色是否能出借  合规接口改造之后需要判断
                UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
                roleId=userInfo.getRoleId();
                String roleIsOpen = systemConfig.getRoleIsopen();
                if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
                    if (userInfo.getRoleId() != 1) {// 担保机构用户
                        borrowDetailResultBean.put("isAllowedTender", Boolean.FALSE);
                    }
                }

                //判断是否设置交易密码
                if (users.getIsSetPassword() != null && users.getIsSetPassword() == 1) {
                    isSetPassword = true;
                }
                //是否授权
                /*if (users.getAuthStatus() != null && users.getAuthStatus() == 1) {
                    isAutoInves = true;
                }*/

                //服务费权状态
                if(users.getPaymentAuthStatus()!=null && users.getPaymentAuthStatus()==1){
                    isPaymentAuth = users.getPaymentAuthStatus();
                }

                //是否允许使用
                if (users.getStatus() != null && users.getStatus() == 0) {
                    isAllowed = true;
                }
                //是否完成风险测评
                if (users.getIsEvaluationFlag() == 1 && null != users.getEvaluationExpiredTime()) {
                    //测评到期日
                    Long lCreate = users.getEvaluationExpiredTime().getTime();
                    //当前日期
                    Long lNow = System.currentTimeMillis();
                    if (lCreate <= lNow) {
                        //已过期需要重新评测
                        isRiskTested = "2";
                    } else {
                        //未到一年有效期
                        isRiskTested = "1";
                    }
                } else {
                    isRiskTested = "0";
                }
                boolean isTender = this.isTenderBorrow(userId, borrowNid, type);
                if (isTender) {
                    isInvested = true;
                }
            } else {
                borrowDetailResultBean.put("status", "99");
                borrowDetailResultBean.put("statusDesc", "用户信息不存在");
                borrowDetailResultBean.put("isAllowedTender", Boolean.FALSE);
            }
        }

        // 2.根据项目标号获取相应的项目信息
        AppBorrowProjectInfoBeanVO borrowProjectInfoBean = new AppBorrowProjectInfoBeanVO();
        Map<String, Object> map = new HashMap<>();
        map.put(ProjectConstant.PARAM_BORROW_NID, borrowNid);
        ProjectCustomeDetailVO tempBorrow = amTradeClient.searchProjectDetail(map);
        // 获取还款信息 add by jijun 2018/04/27
        BorrowRepayVO borrowRepay = null;
        List<BorrowRepayVO> list = amTradeClient.selectBorrowRepayList(borrowNid, null);
        if (!CollectionUtils.isEmpty(list)) {
            borrowRepay = list.get(0);
        }

        userValidation.put("isLogined", isLogined);
        userValidation.put("isOpened", isOpened);
        userValidation.put("isSetPassword", isSetPassword);
        userValidation.put("isAllowed", isAllowed);
        userValidation.put("isRiskTested", isRiskTested);
        userValidation.put("isAutoInves", isAutoInves);
        userValidation.put("isInvested", isInvested);
        //是否缴费授权
        userValidation.put("paymentAuthStatus", isPaymentAuth);
        //角色认证是否打开
        userValidation.put("isCheckUserRole", Boolean.parseBoolean(systemConfig.getRoleIsopen()));
        userValidation.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        //自动出借开关
        userValidation.put("invesAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
        //自动债转开关
        userValidation.put("creditAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());
        userValidation.put("roleId", roleId);


        //获取标的信息
        if (tempBorrow == null) {
            borrowDetailResultBean.put("status", "100");
            borrowDetailResultBean.put("statusDesc", "标的信息不存在");
            //weChatResult = new WeChatResult().buildErrorResponse(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
            return borrowDetailResultBean;
        } else {
            // add by zyk  标的详情添加缓存 2019年1月22日13:52 begin
            // 转换一次是排除业务操作对缓存的干扰
            ProjectCustomeDetailVO borrow = CommonUtils.convertBean(tempBorrow,ProjectCustomeDetailVO.class);
            // 添加缓存后希望能拿到实时的标的剩余金额
            String investAccount = RedisUtils.get(RedisConstants.BORROW_NID + borrowNid);
            if (StringUtils.isNotBlank(investAccount)){
                borrow.setInvestAccount(investAccount);
            }
            // add by zyk  标的详情添加缓存 2019年1月22日13:52 end

            borrowDetailResultBean.put("status", WeChatResult.SUCCESS);
            borrowDetailResultBean.put("statusDesc", WeChatResult.SUCCESS_DESC);
            borrowProjectInfoBean.setBorrowRemain(borrow.getInvestAccount());
            borrowProjectInfoBean.setBorrowProgress(borrow.getBorrowSchedule());
            borrowProjectInfoBean.setOnTime(borrow.getOnTime());
            borrowProjectInfoBean.setAccount(borrow.getBorrowAccount());
            borrowProjectInfoBean.setBorrowApr(FormatRateUtil.formatBorrowApr(borrow.getBorrowApr()));
            borrowProjectInfoBean.setBorrowId(borrowNid);
            borrowProjectInfoBean.setInvestLevel(borrow.getInvestLevel());
            if (StringUtils.isNotBlank(borrow.getIncreaseInterestFlag()) && borrow.getIncreaseInterestFlag().equals("1")){
                borrowProjectInfoBean.setBorrowExtraYield(FormatRateUtil.formatBorrowApr(borrow.getBorrowExtraYield()));
            }else{
                borrowProjectInfoBean.setBorrowExtraYield("");
            }
            borrowProjectInfoBean.setOnAccrual((borrow.getRecoverLastTime() == null ? "放款成功立即计息" : borrow.getRecoverLastTime()));
            //0：备案中 1：初审中 2：出借中 3：复审中 4：还款中 5：已还款 6：已流标 7：待授权
            borrowProjectInfoBean.setStatus(borrow.getBorrowStatus());
            //0初始 1放款请求中 2放款请求成功 3放款校验成功 4放款校验失败 5放款失败 6放款成功
            borrowProjectInfoBean.setBorrowProgressStatus(String.valueOf(borrow.getProjectStatus()));

            if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
                borrowProjectInfoBean.setBorrowPeriodUnit("天");
            } else {
                borrowProjectInfoBean.setBorrowPeriodUnit("月");
            }
            borrowProjectInfoBean.setBorrowPeriod(borrow.getBorrowPeriod());
            borrowProjectInfoBean.setType(borrowNid.substring(0, 3));
            //只需要处理优享汇（融通宝）和尊享汇
            if ("11".equals(borrow.getType())) {
                borrowProjectInfoBean.setTag("尊享汇");
            } else if ("13".equals(borrow.getType())) {
                borrowProjectInfoBean.setTag("优享汇");
            } else {
                borrowProjectInfoBean.setTag("");
            }
            borrowProjectInfoBean.setRepayStyle(borrow.getRepayStyle());
            borrowDetailResultBean.put(ProjectConstant.RES_PROJECT_INFO, borrowProjectInfoBean);

            //获取项目详情信息
            //借款人企业信息
            BorrowUserVO borrowUsers =  cacheService.getCacheBorrowUser(borrowNid);
            //借款人信息
            BorrowManinfoVO borrowManinfo = cacheService.getCacheBorrowManInfo(borrowNid);
            //基础信息
            List<BorrowDetailBean> baseTableData = null;
            //项目介绍
            List<BorrowDetailBean> intrTableData = null;
            //信用状况
            List<BorrowDetailBean> credTableData = null;
            //审核信息
            List<BorrowDetailBean> reviewTableData = null;
            //其他信息 add by jijun 2018/04/27
            List<BorrowDetailBean> otherTableData = null;
            //借款类型
            int borrowType = Integer.parseInt(borrow.getComOrPer());

            if (borrowType == 1 && borrowUsers != null) {
                //基础信息
                baseTableData = ProjectConstant.packDetail(borrowUsers, 1, borrowType, borrow.getBorrowLevel());
                //信用状况
                credTableData = ProjectConstant.packDetail(borrowUsers, 4, borrowType, borrow.getBorrowLevel());
                //审核信息
                reviewTableData = ProjectConstant.packDetail(borrowUsers, 5, borrowType, borrow.getBorrowLevel());
                //其他信息 add by jijun 2018/04/27
                otherTableData = ProjectConstant.packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel());
            } else {
                if (borrowManinfo != null) {
                    //基础信息
                    baseTableData = ProjectConstant.packDetail(borrowManinfo, 1, borrowType, borrow.getBorrowLevel());
                    //信用状况
                    credTableData = ProjectConstant.packDetail(borrowManinfo, 4, borrowType, borrow.getBorrowLevel());
                    //审核信息
                    reviewTableData = ProjectConstant.packDetail(borrowManinfo, 5, borrowType, borrow.getBorrowLevel());
                    //其他信息 add by jijun 2018/04/27
                    otherTableData = ProjectConstant.packDetail(borrowManinfo, 6, borrowType, borrow.getBorrowLevel());
                }
            }

            //项目介绍
            intrTableData = ProjectConstant.packDetail(borrow, 3, borrowType, borrow.getBorrowLevel());


            BorrowInfoWithBLOBsVO borrowInfoWithBLOBsVO = amTradeClient.selectBorrowInfoWithBLOBSVOByBorrowId(borrowNid);
            Map<String,String> riskControl = new HashMap<>();
            if (borrowInfoWithBLOBsVO != null){
                riskControl.put("controlMeasures", StringUtils.isBlank(borrowInfoWithBLOBsVO.getBorrowMeasuresMea())? "" : borrowInfoWithBLOBsVO.getBorrowMeasuresMea().replace("\r\n",""));
                riskControl.put("controlMort", StringUtils.isBlank(borrowInfoWithBLOBsVO.getBorrowMeasuresMort()) ? "" : borrowInfoWithBLOBsVO.getBorrowMeasuresMort().replace("\r\n",""));
            }else{
                riskControl.put("controlMeasures", "");
                riskControl.put("controlMort","");
            }
            borrowDetailResultBean.put("appRiskControlCustomize", riskControl);

            //处理借款信息
            List<BorrowProjectDetailBean> projectDetailList = new ArrayList<>();
            projectDetailList = this.dealDetail(projectDetailList, baseTableData, "baseTableData", null);
            if (userId != null) {
                projectDetailList = this.dealDetail(projectDetailList, intrTableData, "intrTableData", null);
            } else {
                projectDetailList = this.dealDetail(projectDetailList, new ArrayList<BorrowDetailBean>(), "intrTableData", null);
            }
            projectDetailList = this.dealDetail(projectDetailList, credTableData, "credTableData", null);
            projectDetailList = this.dealDetail(projectDetailList, reviewTableData, "reviewTableData", null);
            // 信批需求新增(放款后才显示)
            if (Integer.parseInt(borrow.getBorrowStatus()) >= 4) {
                //其他信息
                String updateTime = ProjectConstant.getUpdateTime(GetDate.getTime10(borrowRepay.getCreateTime()), borrowRepay.getRepayYestime());
                projectDetailList = this.dealDetail(projectDetailList, otherTableData, "otherTableData", updateTime);
            }


            borrowDetailResultBean.put(ProjectConstant.RES_PROJECT_DETAIL, projectDetailList);


            //获取还款计划
            //处理借款信息
            List<BorrowRepayPlanBean> repayPlanList = new ArrayList<>();
            if (CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
                List<BorrowRepayPlanCsVO> repayPlanLists = repayPlanService.getRepayPlan(borrowNid);
                BorrowRepayPlanCsVO borrowRepayPlan = repayPlanLists.get(0);
                BorrowRepayPlanBean borrowRepayPlanBean = new BorrowRepayPlanBean();
                if ("-".equals(borrowRepayPlan.getRepayTime())) {
                    borrowRepayPlanBean.setTime("--");
                } else {
                    borrowRepayPlanBean.setTime(borrowRepayPlan.getRepayTime());
                }
                borrowRepayPlanBean.setNumber("第1期");
                borrowRepayPlanBean.setAccount(DF_FOR_VIEW.format(new BigDecimal(borrowRepayPlan.getRepayTotal())));
                repayPlanList.add(borrowRepayPlanBean);
            } else {
                List<BorrowRepayPlanCsVO> repayPlanLists = repayPlanService.getRepayPlan(borrowNid);
                if (repayPlanLists != null && repayPlanLists.size() > 0) {
                    BorrowRepayPlanCsVO borrowRepayPlan = null;
                    for (int i = 0; i < repayPlanLists.size(); i++) {
                        borrowRepayPlan = repayPlanLists.get(i);
                        BorrowRepayPlanBean borrowRepayPlanBean = new BorrowRepayPlanBean();
                        if ("-".equals(borrowRepayPlan.getRepayTime())) {
                            borrowRepayPlanBean.setTime("--");
                        } else {
                            borrowRepayPlanBean.setTime(borrowRepayPlan.getRepayTime());
                        }
                        borrowRepayPlanBean.setNumber("第" + (i + 1) + "期");
                        if (StringUtils.isNotBlank(borrowRepayPlan.getRepayTotal())){
                            borrowRepayPlanBean.setAccount(DF_FOR_VIEW.format(new BigDecimal(borrowRepayPlan.getRepayTotal())));
                        }
                        repayPlanList.add(borrowRepayPlanBean);
                    }
                }
            }

            // add 汇计划二期前端优化  针对区分原始标与债转标 nxl 20180424 start
            /**
             * 查看标的详情
             * 原始标：复审中、还款中、已还款状态下 如果当前用户是否投过此标，是：可看，否则不可见
             * 债转标的：未被完全承接时，项目详情都可看；被完全承接时，只有出借者和承接者可查看
             */
            int count = 0;
            if (userId != null){
                count = amTradeClient.countUserInvest(userId, borrowNid);
            }
            Boolean viewableFlag = false;
            String statusDescribe = "";
            DebtCreditRequest request = new DebtCreditRequest();
            request.setBorrowNid(borrowNid);
            List<HjhDebtCreditVO> listHjhDebtCredit = amTradeClient.selectHjhDebtCreditListByBorrowNidAndStatus(request);
            // add by nxl 是否发生过债转
            Boolean isDept = false;
            if (null != listHjhDebtCredit && listHjhDebtCredit.size() > 0) {
                // 部分承接
                request.setCreditStatus(Arrays.asList(0, 1));
                List<HjhDebtCreditVO> listHjhDebtCreditOnePlace = amTradeClient.selectHjhDebtCreditListByBorrowNidAndStatus(request);
                if (null != listHjhDebtCreditOnePlace && listHjhDebtCreditOnePlace.size() > 0) {
                    // 部分债转
                    viewableFlag = true;
                } else {
                    // 完全债转
                    for (HjhDebtCreditVO deptcredit : listHjhDebtCredit) {
                        //待承接本金 = 0
                        if (null != deptcredit.getCreditCapitalWait() && deptcredit.getCreditCapitalWait().intValue() == 0) {
                            int intCount = 0;
                            if (userId != null){
                                Map<String, Object> mapParam = new HashMap<String, Object>();
                                mapParam.put(ProjectConstant.PARAM_USER_ID, userId);
                                mapParam.put(ProjectConstant.PARAM_BORROW_NID, borrowNid);
                                amTradeClient.countCreditTenderByBorrowNidAndUserId(mapParam);
                            }
                            if (intCount > 0 || count > 0) {
                                viewableFlag = true;
                            }
                        }
                    }
                }
                statusDescribe = "还款中";
                isDept = true;
            } else {
                // 原始标
                // 复审中，还款中和已还款状态出借者(可看)
                if ("3".equals(borrow.getStatus()) || "4".equals(borrow.getStatus())
                        || "5".equals(borrow.getStatus())) {
                    if (count > 0) {
                        // 可以查看标的详情
                        viewableFlag = true;
                    } else {
                        // 提示仅出借者可看
                        viewableFlag = false;
                    }
                } else {
                    viewableFlag = true;
                }
                // 原始标根据状态显示
                switch (borrow.getBorrowStatus()) {
                    case "0":
                        statusDescribe = "备案中";
                        break;
                    case "1":
                        statusDescribe = "初审中";
                        break;
                    case "2":
                        statusDescribe = "出借中";
                        break;
                    case "3":
                        statusDescribe = "复审中";
                        break;
                    case "4":
                        statusDescribe = "还款中";
                        break;
                    case "5":
                        statusDescribe = "已还款";
                        break;
                    case "6":
                        statusDescribe = "已流标";
                        break;
                    case "7":
                        statusDescribe = "待授权";
                        break;
                    default:
                        break;
                }
            }

            userValidation.put("viewableFlag", viewableFlag);
            userValidation.put("statusDescribe", statusDescribe);
            userValidation.put("isDept", isDept);
            borrowDetailResultBean.put("userValidation", userValidation);
            borrowDetailResultBean.put("repayPlan", repayPlanList);
            borrowDetailResultBean.put("borrowMeasuresMea", borrow.getBorrowMeasuresMea());
            borrowDetailResultBean.put(CustomConstants.APP_STATUS, HomePageDefine.WECHAT_STATUS_SUCCESS);
            borrowDetailResultBean.put(CustomConstants.APP_STATUS_DESC, HomePageDefine.WECHAT_STATUC_DESC);
            return borrowDetailResultBean;

        }
    }


    /**
     * 获取散标详情：加入记录
     *
     * @author zhangyk
     * @date 2018/7/30 10:28
     */
    @Override
    public JSONObject getProjectInvestRecord(String borrowId, HttpServletRequest request, String userId) {
        JSONObject result = new JSONObject();
        CheckUtil.check(StringUtils.isNotBlank(borrowId),MsgEnum.ERR_OBJECT_REQUIRED,"借款编号");
        Integer currentPage = 1;
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
        Integer pageSize = 10;
        if (request.getParameter("pageSize") != null) {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        this.createProjectInvestPage(result,borrowId,currentPage,pageSize);
        result.put(CustomConstants.APP_STATUS, HomePageDefine.WECHAT_STATUS_SUCCESS);
        result.put(CustomConstants.APP_STATUS_DESC,HomePageDefine.WECHAT_STATUC_DESC);
        return result;
    }

    /**
     * 散标出借记录列表
     * @param info
     *
     */
    private void createProjectInvestPage(JSONObject info, String borrowNid,int currPage, int limit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid", borrowNid);
        int recordTotal = this.amTradeClient.countProjectInvestRecordTotal(params);
        String count = this.amTradeClient.countMoneyByBorrowId(params);
        if(count != null && !"".equals(count)){
            info.put("account", DF_FOR_VIEW.format(new BigDecimal(count)));
        }else{
            info.put("account", "0.00");
        }
        if (recordTotal > 0) { // 查询相应的汇直投列表数据
            int page = currPage;
            int offSet = (page - 1) * limit;
            if (offSet == 0 || offSet > 0) {
                params.put("limitStart", offSet);
            }
            if (limit > 0) {
                params.put("limitEnd", limit);
            }
            List<AppProjectInvestListCustomizeVO> recordList = amTradeClient.selectProjectInvestList(params);
            Map<String, String> relationMap = CacheUtil.getParamNameMap("USER_RELATION");
            for (AppProjectInvestListCustomizeVO obj : recordList){
                obj.setClientName(relationMap.get(String.valueOf(obj.getClient())));
            }

            info.put("list", recordList);
            info.put("userCount", String.valueOf(recordTotal));
            //判断本次查询是否已经全部查出数据
            if((page * limit) > recordTotal){
                info.put("end", true);
            }else{
                info.put("end", false);
            }
        } else {
            info.put("list", new ArrayList<AppProjectInvestListCustomizeVO>());
            info.put("userCount", "0");
            info.put("end", true);
        }
    }

    @Override
    public JSONObject getPlanDetail(String planId, Integer userId) {
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> resultMap = new HashMap<>();
        CheckUtil.check(StringUtils.isNotBlank(planId), MsgEnum.ERR_PARAM_NUM);

        // 根据计划编号获取相应的计划详情信息
        PlanDetailCustomizeVO customize = amTradeClient.getPlanDetailByPlanNid(planId);
        if (customize == null) {
            logger.error("传入计划id无对应计划,planNid is {}...", planId);
            throw new RuntimeException("传入计划id无对应计划信息");
        }
        // 计划基本信息
        this.setPlanInfo(resultMap, customize);
        // 用户的用户验证
        this.setUserValidationInfo(resultMap, userId);

        jsonObject.put("object", resultMap);
        jsonObject.put(CustomConstants.APP_STATUS, HomePageDefine.WECHAT_STATUS_SUCCESS);
        jsonObject.put(CustomConstants.APP_STATUS_DESC, HomePageDefine.WECHAT_STATUC_DESC);
        return jsonObject;
    }


    /**
     * 获取计划标的组成
     * @author zhangyk
     * @date 2018/7/30 11:09
     */
    @Override
    public WechatPlanBorrowResultBean getPlanBorrowList(String planId, int currPage, int pageSize) {
        CheckUtil.check(StringUtils.isNotBlank(planId), MsgEnum.ERR_OBJECT_BLANK,"计划编号");
        WechatPlanBorrowResultBean vo = new WechatPlanBorrowResultBean();
        this.searchHjhPlanBorrow(vo,planId,currPage,pageSize);
        return vo;
    }


    /**
     * 创建计划的标的组成分页信息
     * @param result
     * @param planNid
     * @param pageNo
     * @param pageSize
     */
    public void searchHjhPlanBorrow(WechatPlanBorrowResultBean result, String planNid, int pageNo, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planNid", planNid);
        Date date = GetDate.getDate();
        int dayStart10 = GetDate.getDayStart10(date);
        int dayEnd10 = GetDate.getDayEnd10(date);
        params.put("startTime", dayStart10);
        params.put("endTime", dayEnd10);
        int recordTotal = this.amTradeClient.countPlanBorrowRecordTotal(params);
        // 加入总人次
        result.setUserCount(recordTotal);
        // 加入总金额
        result.setAccount(this.getPlanAccedeAccount(params));
        if (recordTotal > 0) {
            int limit = pageSize;
            int page = pageNo;
            int offSet = (page - 1) * limit;
            if (offSet == 0 || offSet > 0) {
                params.put("limitStart", offSet);
            }
            if (limit > 0) {
                params.put("limitEnd", limit);
            }
            List<DebtPlanBorrowCustomizeVO> consumeList = amTradeClient.selectPlanBorrowList(params);

            if (!CollectionUtils.isEmpty(consumeList)) {
                List<WechatPlanBorrowResultBean.BorrowList> borrowList = result.getBorrowList();
                WechatPlanBorrowResultBean.BorrowList borrow = null;
                for (DebtPlanBorrowCustomizeVO entity : consumeList) {
                    borrow = new WechatPlanBorrowResultBean.BorrowList();
                    borrow.setBorrowApr(FormatRateUtil.formatBorrowApr(entity.getBorrowApr()));
                    borrow.setBorrowNid(entity.getBorrowNid());
                    borrow.setBorrowPeriod(entity.getBorrowPeriod());
                    borrow.setTrueName(entity.getTrueName());
                    borrowList.add(borrow);
                }
            }

            // 判断本次查询是否已经全部查出数据
            if ((page * limit) > recordTotal) {
                result.setEnd(Boolean.TRUE);
            } else {
                result.setEnd(Boolean.FALSE);
            }
        }
    }


    /**
     * 获取计划标的加入记录
     * @author zhangyk
     * @date 2018/7/30 11:28
     */
    @Override
    public Object getPlanAccedeList(String planId, int currPage, int pageSize) {
        CheckUtil.check(StringUtils.isNotBlank(planId),MsgEnum.ERR_OBJECT_REQUIRED,"计划编号");
        WechatPlanAccedeResultBean resultBean = new WechatPlanAccedeResultBean();

        this.getHjhPlanAccede(resultBean,planId,currPage,pageSize);

        return resultBean;
    }

    /**
     * 端汇计划加入记录
     * @param result
     * @param planNid
     * @param pageNo
     * @param pageSize
     */
    private void getHjhPlanAccede(WechatPlanAccedeResultBean result, String planNid, int pageNo, int pageSize) {

        HjhAccedeRequest request = new HjhAccedeRequest();
        request.setPlanNid(planNid);
        // mod by nxl 修改统计数量
        // 统计最后三天的服务记录
//        int recordTotal = this.amTradeClient.countPlanAccedeRecordTotal(request);
        int recordTotal = this.amTradeClient.countPlanAccedeRecord(request);

        // 加入总人次
        result.setUserCount(recordTotal);
        // 加入总金额
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planNid", planNid);
        result.setAccount(this.getPlanAccedeAccount(params));
        if (recordTotal > 0) {
            int limit = pageSize;
            int page = pageNo;
            int offSet = (page - 1) * limit;
            if (offSet == 0 || offSet > 0) {
                params.put("limitStart", offSet);
            }
            if (limit > 0) {
                params.put("limitEnd", limit);
            }
            List<DebtPlanAccedeCustomizeVO> recordList = this.amTradeClient.selectPlanAccedeList(params);

            if (!CollectionUtils.isEmpty(recordList)) {
                List<WechatPlanAccedeResultBean.AccedeList> accedeList = result.getAccedeList();
                WechatPlanAccedeResultBean.AccedeList accede = null;
                Map<String, String> relationMap = CacheUtil.getParamNameMap("USER_RELATION");
                for (DebtPlanAccedeCustomizeVO entity : recordList) {
                    entity.setClientName(relationMap.get(String.valueOf(entity.getClient())));
                    accede = new WechatPlanAccedeResultBean.AccedeList();
                    accede.setAccedeAccount(entity.getAccedeAccount());
                    accede.setAccedeTime(entity.getAccedeTime());
                    accede.setUserName(entity.getUserName());
                    accedeList.add(accede);
                }
            }

            // 判断本次查询是否已经全部查出数据
            if ((page * limit) > recordTotal) {
                result.setEnd(Boolean.TRUE);
            } else {
                result.setEnd(Boolean.FALSE);
            }
        }

    }


    /**
     * 根据planNid获取计划加入金额
     *
     * @param params
     * @return
     */
    private String getPlanAccedeAccount(Map<String, Object> params) {
        Long sum = amTradeClient.selectPlanAccedeSum(params);// 加入总金额
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        if (sum == null || sum == 0) {
            return "0";
        } else {
            return df.format(sum);
        }
    }


    /**
     * 获取微信首页统计数据
     *
     * @author zhangyk
     * @date 2018/7/23 16:29
     */
    @Override
    public WechatHomePageResult getHomeIndexData(String userId) {
        WechatHomePageResult result = new WechatHomePageResult();
        result.setWarning("市场有风险 出借需谨慎");
        if (StringUtils.isBlank(userId)) { // 未登录
            result.setTotalAssets("0.00");
            result.setAvailableBalance("0.00");
            result.setAccumulatedEarnings("0.00");
            // 创建首页广告
            String type = "0"; // 未注册
            // 首页顶端轮播图
            createAdPic(result, type);
            result.setAdDesc("立即注册");
        } else {
            // 检查是否开户
            UserVO userVO = amUserClient.findUserById(Integer.valueOf(userId));
            if (userVO != null) {
                result.setIsOpenAccount(userVO.getBankOpenAccount());
                result.setIsSetPassword(userVO.getIsSetPassword());
                result.setIsCheckUserRole(Boolean.parseBoolean(systemConfig.getRoleIsopen()));

                result.setPaymentAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
                result.setInvesAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
                result.setCreditAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());
                UserInfoVO usersInfo=amUserClient.findUsersInfoById(Integer.valueOf(userId));
                result.setRoleId(usersInfo.getRoleId());
                if (userVO.getIsEvaluationFlag() == 1 && null != userVO.getEvaluationExpiredTime()) {
                    // 测评到期日
                    long lCreate = userVO.getEvaluationExpiredTime().getTime();
                    Long lNow = System.currentTimeMillis();
                    if (lCreate <= lNow) {
                        //已过期需要重新评测
                        result.setIsEvaluationFlag(2);
                    } else {
                        //未到一年有效期
                        result.setIsEvaluationFlag(1);
                    }

                } else {
                    result.setIsEvaluationFlag(0);
                }
                result.setPaymentAuthStatus(userVO.getPaymentAuthStatus());//是否缴费授权
                result.setUserStatus(userVO.getStatus());

                HjhUserAuthVO hjhUserAuthVO = amTradeClient.getUserAuthByUserId(Integer.valueOf(userId));
                if(hjhUserAuthVO!=null){
                    //自动投标授权状态
                    result.setAutoInvesStatus(hjhUserAuthVO.getAutoInvesStatus());
                    //自动债转授权状态
                    result.setAutoCreditStatus(hjhUserAuthVO.getAutoCreditStatus());
                    //缴费授权状态
                    result.setPaymentAuthStatus(hjhUserAuthVO.getAutoPaymentStatus());
                }else{
                    //自动投标授权状态
                    result.setAutoInvesStatus(0);
                    //自动债转授权状态
                    result.setAutoCreditStatus(0);
                    //缴费授权状态
                    result.setPaymentAuthStatus(0);
                }

                Integer openFlag = userVO.getBankOpenAccount();
                // 未开户
                if (openFlag == 0) {
                    result.setTotalAssets("0.00");
                    result.setAvailableBalance("0.00");
                    result.setAccumulatedEarnings("0.00");
                    String type = "1";// 未开户
                    createAdPic(result, type);
                    result.setAdDesc("立即开户");
                } else if (openFlag == 1) {
                    AccountVO accountVO = amTradeClient.getAccount(Integer.valueOf(userId));
                    BigDecimal totalAssets = accountVO.getBankTotal();
                    result.setTotalAssets(totalAssets == null ? "0.00" : DF_FOR_VIEW.format(totalAssets));

                    BigDecimal bankBalance = accountVO.getBankBalance();
                    result.setAvailableBalance(bankBalance == null ? "0.00" : DF_FOR_VIEW.format(bankBalance));

                    BigDecimal bankInterestSum = accountVO.getBankInterestSum();
                    result.setAccumulatedEarnings(bankInterestSum == null ? "0.00" : DF_FOR_VIEW.format(bankInterestSum));
                }

            }
        }

        // 获取累计出借金额
        //TotalInvestAndInterestResponse res2 = baseClient.getExe(HomePageDefine.INVEST_INVEREST_AMOUNT_URL, TotalInvestAndInterestResponse.class);
        TotalInvestAndInterestResponse res2 = amTradeClient.getTotalInvestAndInterestResponse();
        // modify by libin 加缓存
        if(res2 == null){
        	logger.error("获取累计出借金额为空！");
        }
        // modify by libin 加缓存
        
        TotalInvestAndInterestVO totalInvestAndInterestVO = res2.getResult();
        BigDecimal totalInvestAmount = null;
        if (totalInvestAndInterestVO != null && totalInvestAndInterestVO.getTotalInvestAmount() != null) {
            totalInvestAmount = totalInvestAndInterestVO.getTotalInvestAmount();
        } else {
            totalInvestAmount = new BigDecimal("0.00");
        }
        result.setTotalInvestmentAmount(DF_FOR_VIEW.format(totalInvestAmount));
        result.setModuleTotal("4");

        List<AppModuleBean> moduleList = new ArrayList<>();

        // 添加首页大栏目
        // 微信安全保障
        this.createModule(moduleList, "wechat_module1");
        // 微信信息披露
        this.createModule(moduleList, "wechat_module4");
        // 微信运营数据
        this.createModule(moduleList, "wechat_module2");
        // 微信关于我们
        this.createModule(moduleList, "wechat_module3");


        result.setModuleList(moduleList);

        result.setStatus(HomePageDefine.WECHAT_STATUS_SUCCESS);
        result.setStatusDesc(HomePageDefine.WECHAT_STATUC_DESC);
        result.setRequest(
                HomePageDefine.WECHAT_REQUEST_MAPPING + HomePageDefine.WECHAT_HOME_INDEX_DATA_METHOD);

        // 添加顶部活动图片总数和顶部活动图片数组
        this.createBannerPage(result);//加缓存

        return result;
    }

    /**
     * 获取首页项目列表信息
     *
     * @author zhangyk
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
        Page page = Page.initPage(currPage, pageSize);
        WechatHomePageResult vo = new WechatHomePageResult();
        vo.setCurrentPage(currPage);
        vo.setPageSize(pageSize);
        result = getProjectListAsyn(result, currPage, pageSize, showPlanFlag);//加缓存(新手标之外的散标和计划)

        if (currPage == 1) {
            String HOST = systemConfig.getWebHost();
            //判断用户是否登录
            if (userId == null || userId <= 0) {
                //获取新手标
                vo.setHomeXshProjectList(this.createProjectNewPage(userId, HOST));//加缓存(新手标)
            } else {
                //查询用户是否开户
                UserVO userVO = amUserClient.findUserById(userId);
                Integer userType = userVO == null ? 0 : userVO.getBankOpenAccount();
                //未开户
                if (userType == 0) {
                    //获取新手标
                    vo.setHomeXshProjectList(this.createProjectNewPage(userId, HOST));
                    //已开户
                } else if (userType == 1) {
                    //获取用户累计出借条数
                    Integer count = amTradeClient.getTotalInverestCount(String.valueOf(userId));
                    if (count == null || count <= 0) {
                        //获取新手标
                        vo.setHomeXshProjectList(this.createProjectNewPage(userId, HOST));
                    }
                }
            }
        }
        result.setHomeXshProjectList(vo.getHomeXshProjectList() != null?vo.getHomeXshProjectList():new ArrayList<>());
        result.setStatus(HomePageDefine.WECHAT_STATUS_SUCCESS);
        result.setStatusDesc(HomePageDefine.WECHAT_STATUC_DESC);
        return result;
    }


    /**
     * 创建首页广告
     */
    private void createAdPic(WechatHomePageResult vo, String type) {
        AdsRequest request = new AdsRequest();
        request.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        request.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        request.setHost(systemConfig.getWeiFrontHost());

        String code = "";
        // 未注册
        if ("0".equals(type)) {
            code = "wechat_regist_888";
        } else if ("1".equals(type)) {
            code = "wechat_open_888";
        }
        request.setCode(code);
        request.setPlatformType("3");
        List<AppAdsCustomizeVO> picList = amTradeClient.getHomeBannerList(request);
        if (picList != null && picList.size() > 0) {
            vo.setAdPicUrl(picList.get(0).getImage());
            vo.setAdClickPicUrl(picList.get(0).getUrl());
        } else {
            vo.setAdPicUrl("");
            vo.setAdClickPicUrl("");
        }
    }

    /**
     * 查询首页banner图
     *
     * @param
     */
    private void createBannerPage(WechatHomePageResult vo) {
        AdsRequest request = new AdsRequest();
        request.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        request.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        //去掉host前缀
        String cdnDomainUrl = CdnUrlUtil.getCdnUrl();
        request.setHost(cdnDomainUrl);
        String code = "wechat_banner";
        request.setCode(code);
        request.setPlatformType("3");
        List<AppAdsCustomizeVO> picList = amTradeClient.getHomeBannerList(request);
        if (picList != null && picList.size() > 0) {
            for (AppAdsCustomizeVO appAdsCustomize : picList) {
                appAdsCustomize.setPicUrl(appAdsCustomize.getImage());
                appAdsCustomize.setPicH5Url(appAdsCustomize.getUrl());
            }
            vo.setPicList(picList);
            vo.setPicTotal(String.valueOf(picList.size()));
        } else {
            vo.setPicList(new ArrayList<AppAdsCustomizeVO>());
            vo.setPicTotal("0");
        }
    }


    /**
     * 创建首页module
     *
     * @param
     */
    private void createModule(List<AppModuleBean> moduleList, String module) {
        AdsRequest request = new AdsRequest();
        request.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        request.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        request.setHost(systemConfig.getWeiFrontHost());
        request.setCode(module);
        request.setPlatformType("3");
        List<AppAdsCustomizeVO> picList = amTradeClient.getHomeBannerList(request);
        if (picList != null && picList.size() > 0) {
            AppModuleBean appModule = new AppModuleBean();
            appModule.setModuleUrl(picList.get(0).getImage() == null ? "" : picList.get(0).getImage());
            appModule.setModuleH5Url(picList.get(0).getUrl() == null ? "" : picList.get(0).getUrl());
            appModule.setModuleTitle(picList.get(0).getBannerName() == null ? "" : picList.get(0).getBannerName());
            moduleList.add(appModule);
        } else {
            AppModuleBean appModule = new AppModuleBean();
            appModule.setModuleUrl("");
            appModule.setModuleH5Url("");
            appModule.setModuleTitle("");
            moduleList.add(appModule);
        }

    }


    /**
     * 检查当前访问用户是否登录、是否开户、是否设置交易密码、是否允许使用、是否完成风险测评、是否授权
     *
     * @param
     */
    private void setUserValidationInfo(Map<String, Object> resultMap, Integer userId) {

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        boolean loginFlag = false;
        UserVO userVO = null;
		if (userId != null) {
			userVO = amUserClient.findUserById(userId);
			if (userVO != null) {
				loginFlag = true;
			}
		}
        // 1. 检查登录状态
        // 未登录
        if (!loginFlag) {
            userLoginInfo.setLogined(Boolean.FALSE);
        } else {
            //角色认证是否打开
            userLoginInfo.setIsCheckUserRole(Boolean.parseBoolean(systemConfig.getRoleIsopen()));
            UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
            userLoginInfo.setRoleId(userInfoVO.getRoleId());
            userLoginInfo.setLogined(Boolean.TRUE);
            // 2. 用户是否被禁用
            userLoginInfo.setAllowed(userVO.getStatus() == 0 ? Boolean.TRUE : Boolean.FALSE);
            // 3. 是否开户
            userLoginInfo.setOpened(userVO.getBankOpenAccount() == 0 ? Boolean.FALSE : Boolean.TRUE);
            // 4. 是否设置过交易密码
            userLoginInfo.setSetPassword(userVO.getIsSetPassword() == 1 ? Boolean.TRUE : Boolean.FALSE);

            // 7.缴费授权状态
            userLoginInfo.setPaymentAuthStatus(userVO.getPaymentAuthStatus());
            userLoginInfo.setPaymentAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
            userLoginInfo.setInvesAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
            userLoginInfo.setCreditAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());

            // 5. 用户是否完成风险测评标识：0未测评 1已测评
            if (userVO.getIsEvaluationFlag() == 1 && null != userVO.getEvaluationExpiredTime()) {
                //测评到期日
                Long lCreate = userVO.getEvaluationExpiredTime().getTime();
                //当前日期
                Long lNow = System.currentTimeMillis();
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    userLoginInfo.setRiskTested("2");
                } else {
                    //已测评并未过有效期
                    userLoginInfo.setRiskTested("1");
                }
            } else {
                //未测评
                userLoginInfo.setRiskTested("0");
            }
            try {
                // 6. 用户是否完成自动授权标识: 0: 未授权 1:已授权
                HjhUserAuthVO userAuthVO = amTradeClient.getUserAuthByUserId(userId);
                if (userAuthVO != null && userAuthVO.getAutoInvesStatus() == 1) {
                    userLoginInfo.setAutoInves(Boolean.TRUE);
                } else {
                    userLoginInfo.setAutoInves(Boolean.FALSE);
                }

                if (userAuthVO != null && userAuthVO.getAutoCreditStatus() == 1) {
                    userLoginInfo.setAutoTransfer(Boolean.TRUE);
                } else {
                    userLoginInfo.setAutoTransfer(Boolean.FALSE);
                }
            } catch (Exception e) {
                logger.error("用户是否完成自动授权标识出错....", e);
                userLoginInfo.setAutoInves(Boolean.FALSE);
                userLoginInfo.setAutoTransfer(Boolean.FALSE);
            }

        }
        resultMap.put("userValidation", userLoginInfo);
    }


    /**
     * 返回汇计划基本信息
     *
     * @param customize
     */
    private void setPlanInfo(Map<String, Object> resultMap, PlanDetailCustomizeVO customize) {

        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setType(ProjectConstant.PLAN_TYPE_NAME);
        // 计划开放额度
        String openAccount = customize.getAvailableInvestAccount();
        projectInfo.setAccount(CommonUtils.formatAmount(openAccount));

        //计划如果关闭，开发额度为0.00
        if (!"1".equals(customize.getPlanStatus())) {
            projectInfo.setAccount("0.00");
        }

        boolean isOpenAccountMoreZero = new BigDecimal(openAccount).compareTo(BigDecimal.ZERO) > 0;
        if ("1".equals(customize.getPlanStatus()) && isOpenAccountMoreZero) {
            // 立即加入
            projectInfo.setStatus("1");
        } else {
            // 稍后开启
            projectInfo.setStatus("0");
        }

        projectInfo.setPlanApr(FormatRateUtil.formatBorrowApr(customize.getPlanApr()));
        projectInfo.setPlanPeriod(customize.getPlanPeriod());
        projectInfo.setPlanPeriodUnit(CommonUtils.getPeriodUnitByRepayStyle(customize.getBorrowStyle()));

        // 计划的加入人次
        HjhAccedeRequest request = new HjhAccedeRequest();
        request.setPlanNid(customize.getPlanNid());
        int count = amTradeClient.countPlanAccedeRecordTotal(request);
        projectInfo.setPlanPersonTime(String.valueOf(count));

        // 项目进度 本期预留，填写固定值
        projectInfo.setPlanProgressStatus("4");
        projectInfo.setPlanName(customize.getPlanName());
        // 计息时间
        projectInfo.setOnAccrual(ProjectConstant.PLAN_ON_ACCRUAL);
        projectInfo.setRepayStyle(customize.getBorrowStyleName());
        // 标的等级
        projectInfo.setInvestLevel(customize.getInvestLevel());

        Map<String, Object> projectDetail = new HashMap<>();
        projectDetail.put("addCondition", MessageFormat.format(ProjectConstant.PLAN_ADD_CONDITION, customize.getDebtMinInvestment(),
                customize.getDebtInvestmentIncrement()));

        // 数据库保存的p标签取出，避免影响前端排版
        String planInfo = customize.getPlanConcept();
        if (!org.apache.commons.lang.StringUtils.isEmpty(planInfo)) {
            // jsoup 解析html富文本
            Document doc = Jsoup.parseBodyFragment(planInfo);
            // 获取P标签里面的值
            if (doc != null) {
                planInfo = doc.getElementsByTag("p").text();
            }
        }
        projectDetail.put("planInfo", planInfo);

        resultMap.put(ProjectConstant.RES_PROJECT_INFO, projectInfo);
        resultMap.put(ProjectConstant.RES_PROJECT_DETAIL, projectDetail);
    }


    /**
     * 处理对象数据
     *
     * @param projectDetailList 返回List对象
     * @param tableData         传入参数
     * @param keys              参数含义
     * @return
     */
    private List<BorrowProjectDetailBean> dealDetail(List<BorrowProjectDetailBean> projectDetailList, List<BorrowDetailBean> tableData, String keys, String updateTime) {
        if (tableData != null && tableData.size() > 0) {
            BorrowProjectDetailBean projectDetailBean = new BorrowProjectDetailBean();
            if ("baseTableData".equals(keys)) {
                projectDetailBean.setTitle("基础信息");
            }
            if ("intrTableData".equals(keys)) {
                projectDetailBean.setTitle("项目介绍");
            }
            if ("credTableData".equals(keys)) {
                projectDetailBean.setTitle("信用状况");
            }
            if ("reviewTableData".equals(keys)) {
                projectDetailBean.setTitle("审核状态");
            }
            //add by jijun 2018/04/27
            if ("otherTableData".equals(keys)) {
                projectDetailBean.setTitle("其他信息（更新于" + updateTime + "）");
            }

            projectDetailBean.setId("");
            projectDetailBean.setMsg(tableData);
            projectDetailList.add(projectDetailBean);
        }
        return projectDetailList;
    }


    /**
     * 是否出借flag
     *
     * @param userId
     * @param borrowNid
     * @param borrowType
     * @return
     */
    private boolean isTenderBorrow(Integer userId, String borrowNid,
                                   String borrowType) {
        //根据borrowNid查询borrow表
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
        if (borrow.getPlanNid() != null && borrow.getPlanNid().length() > 1) {
            return true;
        }
        Integer count = null;
        try {
            Map<String, Object> param = new HashMap<>();
            if (borrowType != null && borrowType.contains("1")) {
                count = amTradeClient.countCreditTenderByBorrowNidAndUserId(param);
            } else {
                count = amTradeClient.countUserInvest(userId, borrowNid);
            }
        } catch (Exception e) {
            // logger.error("查询承接信息出错...", e);
        }
        if (count != null && count > 0) {
            return true;
        }
        return false;
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
        projectMap.put("publishInstCode", CustomConstants.HYJF_INST_CODE);
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

        List<WechatHomeProjectListVO> tempList  = amTradeClient.getWechatProjectList(projectMap);
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


        if (StringUtils.isBlank(showPlanFlag)) {
            if (currentPage == 1) {
                //WechatProjectListResponse res = baseClient.getExe(HomePageDefine.WECHAT_HOME_PLAN_LATER_URL, WechatProjectListResponse.class);
                List<WechatHomeProjectListVO> hjhList = amTradeClient.getWechatHomePlanLater();
                //List<WechatHomeProjectListVO> hjhList = res.getResultList();
                if (list.size() == 0) {
                    //补两条
                    hjhList.addAll(list);
                    list = hjhList;
                } else if (list.size() > 0 && !"HJH".equals(list.get(0).getBorrowType())) {
                    //补两条
                    hjhList.addAll(list);
                    list = hjhList;
                } else if (list.size() > 1 && !"HJH".equals(list.get(1).getBorrowType()) && !CollectionUtils.isEmpty(hjhList)) {
                    //补一条
                    list.add(1, hjhList.get(0));
                }
            }
        }
        if (vo.getEndPage() == 1) {
           // WechatProjectListResponse res = baseClient.getExe(HomePageDefine.WECHAT_HOME_REPAYMENT_URL, WechatProjectListResponse.class);
            List<WechatHomeProjectListVO> hjhList = amTradeClient.getWechatHomeRepaymentsProjectList();
            //List<WechatHomeProjectListVO> hjhList = res.getResultList();
            if (list.size() > 0 && "HJH".equals(list.get(list.size() - 1).getBorrowType())) {
                list.addAll(hjhList);
                //补两条
            } else if (list.size() > 1 && "HJH".equals(list.get(list.size() - 2).getBorrowType())) {
                list.add(hjhList.get(0));
                //补一条
            }
        }


        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        for (WechatHomeProjectListVO wechatHomeProjectListCustomize : list) {
        	wechatHomeProjectListCustomize.setBorrowApr(FormatRateUtil.formatBorrowApr(wechatHomeProjectListCustomize.getBorrowApr()));
        	//wechatHomeProjectListCustomize.setBorrowExtraYield(FormatRateUtil.formatBorrowApr(wechatHomeProjectListCustomize.getBorrowExtraYield()));
        	wechatHomeProjectListCustomize.setBorrowExtraYield("999");
            if ("HJH".equals(wechatHomeProjectListCustomize.getBorrowType())) {
                if ("1".equals(wechatHomeProjectListCustomize.getStatus())) {
                    wechatHomeProjectListCustomize.setStatus("20");
                } else {
                    wechatHomeProjectListCustomize.setStatus("21");
                }
            } else {

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
                    }

                } else {
                    wechatHomeProjectListCustomize.setOnTime(wechatHomeProjectListCustomize.getOnTime());
                }


            }
            wechatHomeProjectListCustomize.setAccountWait(df.format(new BigDecimal(StringUtils.isBlank(wechatHomeProjectListCustomize.getAccountWait()) ? "0.00" :wechatHomeProjectListCustomize.getAccountWait().replaceAll(",",""))));
        }

        // 字段为null时，转为""
        CommonUtils.convertNullToEmptyString(list);
        vo.setHomeProjectList(list);
        return vo;
    }


    /**
     * 获取新手标数据
     *
     * @param
     * @author
     */
    private List<WechatHomeProjectListVO> createProjectNewPage(Integer userId, String HOST) {
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        List<WechatHomeProjectListVO> list = new ArrayList<>();
        String host = HomePageDefine.WECHAT_DETAIL_REQUEST_MAPPING + HomePageDefine.WECHAT_DETAIL_METHOD;
        List<AppProjectListCustomizeVO> projectList = searchProjectNewList(host);//加缓存
        if (projectList != null && !projectList.isEmpty() && projectList.size() != 0) {
            if (list.size() == 0 && projectList.size() != 0) {
                AppProjectListCustomizeVO project = projectList.get(0);
                WechatHomeProjectListVO customize = new WechatHomeProjectListVO();
                customize.setBorrowNid(project.getBorrowNid());
                customize.setBorrowName(project.getBorrowName());
                customize.setBorrowType(project.getBorrowType());
                customize.setBorrowApr(FormatRateUtil.formatBorrowApr(project.getBorrowApr()));
                customize.setBorrowPeriod(project.getBorrowPeriodInt() + "");
                customize.setBorrowPeriodType(project.getBorrowPeriodType());
                customize.setBorrowExtraYield(FormatRateUtil.formatBorrowApr(project.getBorrowExtraYield()));
                if ("0".equals(project.getOnTime()) || "".equals(project.getOnTime())) {
                    switch (project.getStatus()) {
                        case "10":
                            customize.setOnTime(project.getOnTime());
                            break;
                        case "11":
                            customize.setOnTime("立即出借");
                            break;
                        case "12":
                            customize.setOnTime("复审中");
                            break;
                        case "13":
                            customize.setOnTime("还款中");
                            break;
                        case "14":
                            customize.setOnTime("已退出");
                            break;
                    }

                } else {
                    customize.setOnTime(project.getOnTime());
                }
                customize.setStatus(project.getStatus());
                customize.setAccountWait(df.format(new BigDecimal(project.getBorrowAccountWait())));
                list.add(customize);
            }
        }

        return list;
    }

    /**
     * 获取新手标数据
     *
     * @param
     * @author
     */
	/*@Cached(name="wechatHomeProjectNewListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)*/
    public List<AppProjectListCustomizeVO> searchProjectNewList(String host) {
        List<AppProjectListCustomizeVO> projectList = new ArrayList<>();

        // 取得新手汇项目（定时发标）
        AppProjectListRequest request = new AppProjectListRequest();
        request.setPlatform("wx");
        request.setType("4");
        request.setHost(host);
        // 查询首页定时发标的项目
        List<AppProjectListCustomizeVO> listNewOnTime = amTradeClient.searchAppProjectList(request);


        // 取得新手汇项目（出借中）
        String statusNewInvest = "15";
        AppProjectListRequest request2 = new AppProjectListRequest();
        request2 = CommonUtils.convertBean(request,AppProjectListRequest.class);
        request2.setStatus(statusNewInvest);

        // 查询首页定时发标的项目
        List<AppProjectListCustomizeVO> listNewInvest = amTradeClient.searchAppProjectList(request2);
        if (listNewInvest != null && listNewInvest.size() > 0) {
            for (int i = 0; i < listNewInvest.size(); i++) {
                AppProjectListCustomizeVO newInvest = listNewInvest.get(i);
                if(!Validator.isIncrease(newInvest.getIncreaseInterestFlag(), newInvest.getBorrowExtraYieldOld())){
                    newInvest.setBorrowExtraYield("");
                }
                projectList.add(newInvest);
            }
        }
        // 新手汇项目（出借中）为空
        if (projectList.size() == 0) {
            if (listNewOnTime != null && listNewOnTime.size() > 0) {
                for (int i = 0; i < listNewOnTime.size(); i++) {
                    AppProjectListCustomizeVO newOnTime = listNewOnTime.get(i);
                    if(!Validator.isIncrease(newOnTime.getIncreaseInterestFlag(), newOnTime.getBorrowExtraYieldOld())){
                        newOnTime.setBorrowExtraYield("");
                    }
                    projectList.add(newOnTime);
                }
            }
        }
        return projectList;
    }
	
//    /**
//     * @author libin
//     * 抽出查询统计信息的方法
//     * @date 2018/9/5 11:38
//     */
//	@Cached(name="wechatTotalInvestAndInterestCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
//	@CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.MINUTES)
//    public TotalInvestAndInterestResponse getTotalInvestAndInterestResponse(){
//		TotalInvestAndInterestResponse res2 = baseClient.getExe(HomePageDefine.INVEST_INVEREST_AMOUNT_URL, TotalInvestAndInterestResponse.class);
//    	return res2;
//    }



}
