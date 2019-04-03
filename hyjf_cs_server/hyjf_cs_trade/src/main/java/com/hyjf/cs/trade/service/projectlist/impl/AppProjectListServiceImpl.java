package com.hyjf.cs.trade.service.projectlist.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.resquest.app.AppProjectInvestBeanRequest;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.DebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhAccedeRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTenderCreditInvestListCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.bean.app.AppBorrowProjectInfoBeanVO;
import com.hyjf.cs.trade.bean.app.AppTransferDetailBean;
import com.hyjf.cs.trade.bean.repay.RepayPlanBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.projectlist.AppProjectListService;
import com.hyjf.cs.trade.service.projectlist.CacheService;
import com.hyjf.cs.trade.service.repay.RepayPlanService;
import com.hyjf.cs.trade.util.HomePageDefine;
import com.hyjf.cs.trade.util.ProjectConstant;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

/**
 * App端项目列表Service实现类
 *
 * @author zhangyk
 * @version WebProjectListServiceImpl, v0.1 2018/6/13 10:21
 */
@Service
public class AppProjectListServiceImpl extends BaseTradeServiceImpl implements AppProjectListService {

    private static Logger logger = LoggerFactory.getLogger(AppProjectListServiceImpl.class);

    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");


    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private RepayPlanService repayPlanService;
    @Autowired
    private AuthService authService;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private CacheService cacheService;


    /**
     * APP端出借散标项目列表
     *
     * @param request
     * @return
     * @author
     */
    @Override
    public JSONObject searchAppProjectList(ProjectListRequest request) {
        //CheckUtil.check(CustomConstants.HZT.equals(request.getProjectType()), MsgEnum.ERR_OBJECT_VALUE, "peojectType");
        // 初始化分页参数，并组合到请求参数
        // 所有产品列表只查2页之内的数据(app默认写死每页20条)
        int pageSizeCheck = 20;
        Page page = Page.initPage(1, pageSizeCheck);
        JSONObject info = new JSONObject();
        AppProjectListRequest req = new AppProjectListRequest();
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        req.setProjectType("CFH");  // 原来逻辑： 如果projectType == "HZT" ，则setProjectType == CFH；
        ProjectListRequest params = CommonUtils.convertBean(req,ProjectListRequest.class);

        // 合规审批需求  add by huanghui 20181123 start
        info.put("riskWarningHint", "散标是经过汇盈金服对借款人进行信息搜集和资信评估后在汇盈金服平台发布的借款标的统称。请您充分了解标的信息，谨慎出借。");
        info.put("riskWarningContent", " $散标介绍$ " +
                "\n" +
                "散标是经过汇盈金服对具有借款需求的借款人进行信息搜集和资信评估后在汇盈金服平台发布的借款标的统称，包括但不限于实物抵押标、第三方保证标以及汇盈金服平台不时增加和发布的其他类型借款标。" +
                "\n\n" +
                " $出借人适当性管理告知$ " +
                "\n" +
                "作为网络借贷的出借人，应当具备出借风险意识，风险识别能力，拥有一定的金融产品出借经验并熟悉互联网金融。请您在出借前，确保了解借款项目的主要风险，同时确认具有相应的风险认知和承受能力，并自行承担出借可能产生的相关损失。" +
                "\n\n" +
                "温馨提示：" +
                "\n" +
                "近期“散标”的固定发标时间在10:00、15:00，其余时间根据资产和运营情况随机发标。"
                );

        // 合规审批需求  add by huanghui 20181123 end

        // ①查询count
        Integer count = amTradeClient.countAppProjectList(params);
        // 对调用返回的结果进行转换和拼装
        // 先抛错方式，避免代码看起来头重脚轻。
        if (count == null) {
            logger.error("app端查询散标出借列表原子层count异常");
            throw new RuntimeException("app端查询散标出借列表原子层count异常");
        }
        if (count > pageSizeCheck){
            count = pageSizeCheck;
        }

        //由于result类在转json时会去掉null值，手动初始化为非null，保证json不丢失key
        info.put(ProjectConstant.APP_PROJECT_LIST, new ArrayList<>());
        info.put(ProjectConstant.APP_PROJECT_TOTAL, 0);
        if (count > 0) {
            info.put(ProjectConstant.APP_PROJECT_TOTAL,count);
            List<AppProjectListCsVO> result = new ArrayList<>();
            List<AppProjectListCustomizeVO> list = amTradeClient.searchAppProjectList(req);
            if (CollectionUtils.isEmpty(list)) {
                logger.error("app端查询散标出借列表原子层List异常");
                throw new RuntimeException("app端查询散标出借列表原子层list数据异常");
            }else {
                result = convertToAppProjectType(list);
                CommonUtils.convertNullToEmptyString(result);
                info.put(ProjectConstant.APP_PROJECT_LIST,result);
            }
        }
        info.put(CustomConstants.APP_STATUS,CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC,CustomConstants.APP_STATUS_DESC_SUCCESS);
        info.put(ProjectConstant.APP_PAGE,request.getPage());
        info.put(CustomConstants.APP_REQUEST,ProjectConstant.APP_REQUEST_MAPPING + ProjectConstant.APP_BORROW_PROJECT_METHOD);
        return info;


    }

    /**
     * 获取移动端散标详情
     *
     * @author zhangyk
     * @date 2018/6/28 16:15
     */
    @Override
    public JSONObject getAppProjectDetail(String borrowNid, HttpServletRequest req, Integer userId) {
        JSONObject jsonObject = new JSONObject();
        JSONObject userValidation = new JSONObject();
        String type = req.getParameter("borrowType");
        CheckUtil.check(StringUtils.isNotBlank(borrowNid), MsgEnum.ERR_PARAM_NUM);
        boolean isLogined = false;
        boolean isOpened = false;
        boolean isSetPassword = false;
        boolean isAllowed = false;
       // boolean isRiskTested = false;
        String isRiskTested = "0";
        boolean isAutoInves = false;
        boolean isInvested = false;
        //  0：未授权1：已授权
        Integer paymentAuthStatus = 0;
        //  用户角色
        Integer roleId = 0;

        if (userId != null) {
            isLogined = true;
            UserVO users = amUserClient.findUserById(userId);
            if (users != null) {
                //判断是否设置交易密码
                if (users.getIsSetPassword() != null && users.getIsSetPassword() == 1) {
                    isSetPassword = true;
                }
                //判断是否开户
                if (users.getBankOpenAccount() != null && users.getBankOpenAccount() == 1) {
                    isOpened = true;
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
                        //测评未过期
                        isRiskTested = "1";
                    }
                }
                boolean isTender = this.isTenderBorrow(userId, borrowNid, type);
                if (isTender) {
                    isInvested = true;
                }
                //是否缴费授权
                paymentAuthStatus = users.getPaymentAuthStatus();
                // 用户角色
                UserInfoVO usersInfo = amUserClient.findUsersInfoById(userId);
                if (usersInfo != null) {
                    roleId = usersInfo.getRoleId();
                }
            }
        }

        // 2.根据项目标号获取相应的项目信息
        AppBorrowProjectInfoBeanVO borrowProjectInfoBean = new AppBorrowProjectInfoBeanVO();
        Map<String, Object> map = new HashMap<>();
        map.put(ProjectConstant.PARAM_BORROW_NID, borrowNid);
        ProjectCustomeDetailVO tempBorrow = amTradeClient.searchProjectDetail(map);
        // 还款信息
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
        // 服务费授权状态
        userValidation.put("paymentAuthStatus", paymentAuthStatus);
        // 角色验证开关
        userValidation.put("isCheckUserRole", Boolean.parseBoolean(systemConfig.getRoleIsopen()));
        // 服务费授权开关
        userValidation.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        // 自动出借授权开关
        userValidation.put("invesAuthOn",authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
        // 自动债转授权开关
        userValidation.put("creditAuthOn",authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());
        userValidation.put("roleId", roleId);

        jsonObject.put("userValidation", userValidation);
        if (tempBorrow == null) {
            throw new CheckException("标的信息不存在");
        } else {
            // add by zyk  标的详情添加缓存 2019年1月22日13:52 begin
            // 转换一次是排除业务操作对缓存的干扰
            ProjectCustomeDetailVO borrow = CommonUtils.convertBean(tempBorrow,ProjectCustomeDetailVO.class);
            // 添加缓存后希望能拿到实时的标的剩余金额
            String investAccount = RedisUtils.get(RedisConstants.BORROW_NID + borrowNid);
            if (org.apache.commons.lang.StringUtils.isNotBlank(investAccount)){
                borrow.setInvestAccount(investAccount);
            }
            // add by zyk  标的详情添加缓存 2019年1月22日13:52 end
            borrowProjectInfoBean.setBorrowRemain(borrow.getInvestAccount());
            borrowProjectInfoBean.setBorrowProgress(borrow.getBorrowSchedule());
            borrowProjectInfoBean.setOnTime(borrow.getOnTime());
            borrowProjectInfoBean.setAccount(borrow.getBorrowAccount());
            borrowProjectInfoBean.setBorrowApr(borrow.getBorrowApr());
            borrowProjectInfoBean.setBorrowId(borrowNid);
            borrowProjectInfoBean.setOnAccrual((borrow.getReverifyTime() == null ? "放款成功立即计息" : borrow.getReverifyTime()));
            borrowProjectInfoBean.setInvestLevel(borrow.getInvestLevel());
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

            /**
             * 产品加息
             */
            BigDecimal borrowExtraYield = new BigDecimal(borrow.getBorrowExtraYield()==null?"0":borrow.getBorrowExtraYield());
            if (Validator.isIncrease(Integer.valueOf(borrow.getIncreaseInterestFlag()), borrowExtraYield)) {
                borrowProjectInfoBean.setBorrowExtraYield(borrow.getBorrowExtraYield());
            }else{
                borrowProjectInfoBean.setBorrowExtraYield("");
            }

            jsonObject.put(ProjectConstant.RES_PROJECT_INFO, borrowProjectInfoBean);

            //借款人企业信息
            BorrowUserVO borrowUsers = cacheService.getCacheBorrowUser(borrowNid);
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
            //其他信息
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
                //其他信息
                otherTableData = ProjectConstant.packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel());
            } else {
                if (borrowManinfo != null) {
                    //基础信息
                    baseTableData = ProjectConstant.packDetail(borrowManinfo, 1, borrowType, borrow.getBorrowLevel());
                    //信用状况
                    credTableData = ProjectConstant.packDetail(borrowManinfo, 4, borrowType, borrow.getBorrowLevel());
                    //审核信息
                    reviewTableData = ProjectConstant.packDetail(borrowManinfo, 5, borrowType, borrow.getBorrowLevel());
                    //其他信息
                    otherTableData = ProjectConstant.packDetail(borrowManinfo, 6, borrowType, borrow.getBorrowLevel());
                }
            }

            //项目介绍
            intrTableData = ProjectConstant.packDetail(borrow, 3, borrowType, borrow.getBorrowLevel());
            // 风控信息
            BorrowInfoWithBLOBsVO borrowInfoWithBLOBsVO = amTradeClient.selectBorrowInfoWithBLOBSVOByBorrowId(borrowNid);
            Map<String,String> riskControl = new HashMap<>();
            if (borrowInfoWithBLOBsVO != null){
                riskControl.put("controlMeasures",borrowInfoWithBLOBsVO.getBorrowMeasuresMea() == null ? "" : borrowInfoWithBLOBsVO.getBorrowMeasuresMea().replace("\r\n",""));
                riskControl.put("controlMort",borrowInfoWithBLOBsVO.getBorrowMeasuresMort() == null ? "" : borrowInfoWithBLOBsVO.getBorrowMeasuresMort().replace("\r\n",""));
                riskControl.put("partner",borrowInfoWithBLOBsVO.getBorrowMeasuresInstit());
                riskControl.put("agencyIntroduction",borrowInfoWithBLOBsVO.getBorrowCompanyInstruction());
                riskControl.put("operatingProcess",borrowInfoWithBLOBsVO.getBorrowOperatingProcess());
            }
            jsonObject.put("riskControl", riskControl);

            //处理借款信息
            List<BorrowProjectDetailBean> projectDetailList = new ArrayList<>();
            projectDetailList = dealDetail(projectDetailList, baseTableData, "baseTableData", null);
            if (userId != null) {
                projectDetailList = dealDetail(projectDetailList, intrTableData, "intrTableData", null);
            } else {
                projectDetailList = dealDetail(projectDetailList, new ArrayList<BorrowDetailBean>(), "intrTableData", null);
            }
            projectDetailList = dealDetail(projectDetailList, credTableData, "credTableData", null);
            projectDetailList = dealDetail(projectDetailList, reviewTableData, "reviewTableData", null);
            // 信批需求新增(放款后才显示)
            if (Integer.parseInt(borrow.getBorrowStatus()) >= 4 && borrowRepay != null) {
                //其他信息
                Integer loanTime = (borrowRepay.getCreateTime() == null? null : GetDate.getTime10(borrowRepay.getCreateTime()));
                String updateTime = ProjectConstant.getUpdateTime(loanTime, borrowRepay.getRepayYestime());
                projectDetailList = dealDetail(projectDetailList, otherTableData, "otherTableData", updateTime);
            }
            jsonObject.put(ProjectConstant.RES_PROJECT_DETAIL, projectDetailList);

            //处理借款信息
            List<BorrowRepayPlanBean> repayPlanList = new ArrayList<>();
            if (CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle())) {
                List<BorrowRepayPlanCsVO> repayPlanLists = repayPlanService.getRepayPlan(borrowNid);
                BorrowRepayPlanCsVO borrowRepayPlan = repayPlanLists.get(0);
                BorrowRepayPlanBean borrowRepayPlanBean = new BorrowRepayPlanBean();
                if ("-".equals(borrowRepayPlan.getRepayTime())) {
                    borrowRepayPlanBean.setTime("-");
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
                            borrowRepayPlanBean.setTime("-");
                        } else {
                            borrowRepayPlanBean.setTime(borrowRepayPlan.getRepayTime());
                        }
                        borrowRepayPlanBean.setNumber("第" + (i + 1) + "期");
                        borrowRepayPlanBean.setAccount(DF_FOR_VIEW.format(new BigDecimal(borrowRepayPlan.getRepayTotal())));
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
                            Map<String, Object> mapParam = new HashMap<String, Object>();
                            mapParam.put(ProjectConstant.PARAM_USER_ID, userId);
                            mapParam.put(ProjectConstant.PARAM_BORROW_NID, borrowNid);
                            amTradeClient.countCreditTenderByBorrowNidAndUserId(mapParam);
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
                if ("3".equals(borrow.getBorrowStatus()) || "4".equals(borrow.getBorrowStatus())
                        || "5".equals(borrow.getBorrowStatus())) {
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
            // 是否发生过债转
            userValidation.put("isDept", isDept);
            jsonObject.put("userValidation", userValidation);
            // add 汇计划二期前端优化  针对区分原始标与债转标  nxl 20180424 end
            jsonObject.put("repayPlan", repayPlanList);
            jsonObject.put(CustomConstants.APP_STATUS,BaseResult.SUCCESS);
            jsonObject.put(CustomConstants.APP_STATUS_DESC,CustomConstants.APP_STATUS_DESC_SUCCESS);
            return jsonObject;
        }
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
     * 封装项目详情页
     *
     * @param objBean
     * @param type        1 基础信息 2资产信息 3项目介绍 4信用状况 5审核状况
     * @param borrowType  1借款人 2企业借款
     * @param borrowLevel 信用评级
     * @return
     */
    private List<BorrowMsgBean> packDetail(Object objBean, int type, int borrowType, String borrowLevel, String investLevel) {
        List<BorrowMsgBean> detailBeanList = new ArrayList<BorrowMsgBean>();
        String currencyName = "元";
        // 得到对象
        Class c = objBean.getClass();
        // 得到方法
        Field fieldlist[] = c.getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++) {
            // 获取类属性
            Field f = fieldlist[i];
            // 得到方法名
            String fName = f.getName();
            try {
                // 参数方法获取
                String paramName = fName.substring(0, 1).toUpperCase() + fName.substring(1, fName.length());
                // 取得结果
                Method getMethod = c.getMethod(BankCallConstant.GET + paramName);
                if (getMethod != null) {
                    Object result = getMethod.invoke(objBean);
                    // 结果不为空时
                    if (Validator.isNotNull(result)) {
                        //封装bean
                        BorrowMsgBean detailBean = new BorrowMsgBean();
                        detailBean.setId(fName);
                        detailBean.setVal(result.toString());
                        if (type == 1) {
                            if (borrowType == 2) {//个人借款
                                switch (fName) {
                                    case "name":
                                        detailBean.setKey("姓名");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 1, 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "cardNo":
                                        detailBean.setKey("身份证号");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "sex":
                                        detailBean.setKey("性别");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("男");
                                        } else {
                                            detailBean.setVal("女");
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "old":
                                        if (!"0".equals(detailBean.getVal())) {
                                            detailBean.setKey("年龄");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "merry":
                                        if (!("0".equals(result.toString()) || result.toString() == null)) {
                                            detailBean.setKey("婚姻状况");
                                            if ("1".equals(result.toString())) {
                                                detailBean.setVal("已婚");
                                            } else if ("2".equals(result.toString())) {
                                                detailBean.setVal("未婚");
                                            } else if ("3".equals(result.toString())) {
                                                detailBean.setVal("离异");
                                            } else if ("4".equals(result.toString())) {
                                                detailBean.setVal("丧偶");
                                            }
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "city":
                                        detailBean.setKey("工作城市");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "domicile":
                                        detailBean.setKey("户籍地");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "position":
                                        detailBean.setKey("职业类型");
                                        if ("10000".equals(result.toString())) {
                                            detailBean.setVal("机关企事业单位负责人");
                                        } else if ("20000".equals(result.toString())) {
                                            detailBean.setVal("专业技术人员");
                                        } else if ("30000".equals(result.toString())) {
                                            detailBean.setVal("办事人员和有关人员");
                                        } else if ("40000".equals(result.toString())) {
                                            detailBean.setVal("会生产服务和生活服务人员");
                                        } else if ("50000".equals(result.toString())) {
                                            detailBean.setVal("农、林、牧、渔业生产及辅助人员");
                                        } else if ("60000".equals(result.toString())) {
                                            detailBean.setVal("生产制造及有关人员");
                                        } else if ("70000".equals(result.toString())) {
                                            detailBean.setVal("军人");
                                        } else if ("80000".equals(result.toString())) {
                                            detailBean.setVal("不便分类的其他从业人员");
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "annualIncome":
                                        detailBean.setKey("年收入");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "overdueReport":
                                        detailBean.setKey("征信报告逾期情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "debtSituation":
                                        detailBean.setKey("重大负债状况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "otherBorrowed":
                                        detailBean.setKey("其他平台借款情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    default:
                                        break;
                                }
                            } else {//企业借款

                                switch (fName) {
                                    case "currencyName":
                                        currencyName = detailBean.getVal();
                                        break;
                                    case "username":
                                        detailBean.setKey("借款主体");
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), detailBean.getVal().length() - 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "city":
                                        detailBean.setKey("注册地区");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "regCaptial":
                                        detailBean.setKey("注册资本");
                                        if (StringUtils.isNotBlank(detailBean.getVal())) {
                                            detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + currencyName);
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "comRegTime":
                                        detailBean.setKey("注册时间");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "socialCreditCode":
                                        detailBean.setKey("统一社会信用代码");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "registCode":
                                        detailBean.setKey("注册号");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "legalPerson":
                                        detailBean.setKey("法定代表人");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 1, 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "industry":
                                        detailBean.setKey("所属行业");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "mainBusiness":
                                        detailBean.setKey("主营业务");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "overdueReport":
                                        detailBean.setKey("征信报告逾期情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "debtSituation":
                                        detailBean.setKey("重大负债状况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "otherBorrowed":
                                        detailBean.setKey("其他平台借款情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else if (type == 2) {
                            switch (fName) {
                                case "housesType":
                                    detailBean.setKey("资产类型");
                                    String key = "hyjf_param_name:HOUSES_TYPE";
                                    Map<String, String> houseTypeMap = RedisUtils.hgetall(key);
                                    if (!CollectionUtils.isEmpty(houseTypeMap)) {
                                        detailBean.setVal(houseTypeMap.get(detailBean.getVal()));
                                    } else {
                                        detailBean.setVal("住宅");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesArea":
                                    detailBean.setKey("资产面积");
                                    detailBean.setVal(detailBean.getVal() + "m<sup>2</sup>");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesCnt":
                                    detailBean.setKey("资产数量");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesToprice":
                                    detailBean.setKey("评估价值");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesBelong":
                                    detailBean.setKey("资产所属");
                                    detailBeanList.add(detailBean);
                                    break;
                                //车辆
                                case "brand":
                                    BorrowMsgBean carBean = new BorrowMsgBean();
                                    carBean.setId("carType");
                                    carBean.setKey("资产类型");
                                    carBean.setVal("车辆");
                                    detailBeanList.add(carBean);
                                    detailBean.setKey("品牌");
                                    detailBeanList.add(detailBean);
                                    break;

                                case "model":
                                    detailBean.setKey("型号");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "place":
                                    detailBean.setKey("产地");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "price":
                                    detailBean.setKey("购买价格");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "toprice":
                                    detailBean.setKey("评估价值");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "number":
                                    detailBean.setKey("车牌号");
                                    //数据脱敏
                                    detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 2, 4));
                                    detailBeanList.add(detailBean);
                                    break;
                                case "registration":
                                    detailBean.setKey("车辆登记地");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "vin":
                                    detailBean.setKey("车架号");
                                    //数据脱敏
                                    detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 5));
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }

                        } else if (type == 3) {
                            switch (fName) {
                                case "borrowContents":
                                    detailBean.setKey("项目信息");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "fianceCondition":
                                    detailBean.setKey("财务状况 ");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "financePurpose":
                                    detailBean.setKey("借款用途");
                                    if ("01".equals(result.toString())) {
                                        detailBean.setVal("个人消费");
                                    } else if ("02".equals(result.toString())) {
                                        detailBean.setVal("个人经营");
                                    } else if ("03".equals(result.toString())) {
                                        detailBean.setVal("个人资金周转");
                                    } else if ("04".equals(result.toString())) {
                                        detailBean.setVal("房贷");
                                    } else if ("05".equals(result.toString())) {
                                        detailBean.setVal("企业经营");
                                    } else if ("06".equals(result.toString())) {
                                        detailBean.setVal("企业周转");
                                    } else if ("99".equals(result.toString())) {
                                        detailBean.setVal("其他");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "monthlyIncome":
                                    detailBean.setKey("月薪收入");
                                    if (StringUtils.isNotBlank(DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())))) {
                                        detailBean.setVal(detailBean.getVal());
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "payment":
                                    detailBean.setKey("还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "firstPayment":
                                    detailBean.setKey("第一还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "secondPayment"://还没有
                                    detailBean.setKey("第二还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "costIntrodution":
                                    detailBean.setKey("费用说明");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        } else if (type == 4) {
                            switch (fName) {
                                case "overdueTimes":
                                    detailBean.setKey("在平台逾期次数");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "overdueAmount":
                                    detailBean.setKey("在平台逾期金额");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "litigation":
                                    detailBean.setKey("涉诉情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        } else if (type == 5) {
                            if (borrowType == 2) {
                                switch (fName) {
                                    case "isCard":
                                        detailBean.setKey("身份证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isIncome":
                                        detailBean.setKey("收入状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isCredit":
                                        detailBean.setKey("信用状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAsset":
                                        detailBean.setKey("资产状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isVehicle":
                                        detailBean.setKey("车辆状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isDrivingLicense":
                                        detailBean.setKey("行驶证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isVehicleRegistration":
                                        detailBean.setKey("车辆登记证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isMerry":
                                        detailBean.setKey("婚姻状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isWork":
                                        detailBean.setKey("工作状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAccountBook":
                                        detailBean.setKey("户口本");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                switch (fName) {
                                    case "isCertificate":
                                        detailBean.setKey("企业证件");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isOperation":
                                        detailBean.setKey("经营状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isFinance":
                                        detailBean.setKey("财务状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isEnterpriseCreidt":
                                        detailBean.setKey("企业信用");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isLegalPerson":
                                        detailBean.setKey("法人信息");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAsset":
                                        detailBean.setKey("资产状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isPurchaseContract":
                                        detailBean.setKey("购销合同");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isSupplyContract":
                                        detailBean.setKey("供销合同");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else if (type == 6) {
                            switch (fName) {
                                case "isFunds":
                                    detailBean.setKey("借款资金运用情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isManaged":
                                    detailBean.setKey("借款人经营状况及财务状况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isAbility":
                                    detailBean.setKey("借款人还款能力变化情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isOverdue":
                                    detailBean.setKey("借款人逾期情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isComplaint":
                                    detailBean.setKey("借款人涉诉情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isPunished":
                                    detailBean.setKey("借款人受行政处罚情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                continue;
            }
        }
        if (type == 1 || type == 4) {
            // 信用评级单独封装
            BorrowMsgBean detailBean = new BorrowMsgBean();
            detailBean.setId("borrowLevel");
            detailBean.setKey("信用评级");
            detailBean.setVal(borrowLevel);
            detailBeanList.add(detailBean);
            // 标的等级单独封装
            BorrowMsgBean detailBeanLeve = new BorrowMsgBean();
            detailBeanLeve.setId("investLevel");
            detailBeanLeve.setKey("标的等级");
            detailBeanLeve.setVal(investLevel);
            detailBeanList.add(detailBeanLeve);
        }
        return detailBeanList;
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
            logger.error("查询承接信息出错...", e);
        }
        if (count != null && count > 0) {
            return true;
        }
        return false;
    }



    /**
     * APP端出借债转列表数据
     *
     * @author zhangyk
     * @date 2018/6/20 9:11
     */
    @Override
    public JSONObject searchAppCreditList(ProjectListRequest request) {
        // 初始化分页参数，并组合到请求参数
        JSONObject info = new JSONObject();
        Page page = Page.initPage(request.getPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        request.setCreditStatus("0");
        ProjectListResponse res = amTradeClient.countAppCreditList(request);
        if (!Response.isSuccess(res)) {
            logger.error("查询债权转让原子层count异常");
            throw new RuntimeException("查询债权转让原子层count异常");
        }

        // 合规审批需求  add by huanghui 20181123 start
        info.put("riskWarningHint", "债权转让是债权持有人在汇盈金服平台将债权挂出并将所持有的债权转让给受让人的操作。请您充分了解标的信息，谨慎出借。");
        info.put("riskWarningContent", " $债权转让介绍$ " +
                "\n" +
                "债权持有人通过汇盈金服平台债权转让系统将债权挂出且与承接人签订债权转让协议，将所持有的债权转让给承接人的操作。" +
                "\n\n" +
                " $出借人适当性管理告知$ " +
                "\n" +
                "作为网络借贷的出借人，应当具备出借风险意识，风险识别能力，拥有一定的金融产品出借经验并熟悉互联网金融。请您在出借前，确保了解借款项目的主要风险，同时确认具有相应的风险认知和承受能力，并自行承担出借可能产生的相关损失。");
        // 合规审批需求  add by huanghui 20181123 end

        int count = res.getCount();
        info.put(ProjectConstant.APP_PROJECT_LIST, new ArrayList<>());
        if (count > 0) {
            ProjectListRequest request2 = CommonUtils.convertBean(request,ProjectListRequest.class);
            info.put(ProjectConstant.APP_PROJECT_TOTAL, count);
            ProjectListResponse dataResponseCache = amTradeClient.searchAppCreditList(request2);
            if (!Response.isSuccess(dataResponseCache)) {
                logger.error("查询债权转让原子层list数据异常");
                throw new RuntimeException("查询债权转让原子层list数据异常");
            }
            ProjectListResponse dataResponse = CommonUtils.convertBean(dataResponseCache,ProjectListResponse.class);
            if (!CollectionUtils.isEmpty(dataResponse.getResultList())) {
                List<AppProjectListCsVO> result = convertToAppProjectHZRType(dataResponse.getResultList());
                info.put(ProjectConstant.APP_PROJECT_LIST, result);
            }
        } else {
            info.put(ProjectConstant.APP_PROJECT_TOTAL, 0);
            info.put(ProjectConstant.APP_PROJECT_LIST, new ArrayList<>());
        }
        info.put(ProjectConstant.APP_PAGE, request.getPage());
        info.put(CustomConstants.APP_STATUS,CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC,CustomConstants.APP_STATUS_DESC_SUCCESS);
        info.put(CustomConstants.APP_REQUEST,ProjectConstant.APP_REQUEST_MAPPING + ProjectConstant.APP_CREDIT_LIST_METHOD);
        return info;
    }

    private List<AppProjectListCsVO> convertToAppProjectHZRType(List<WebProjectListCustomizeVO> resultList) {
        List<AppProjectListCsVO> appProjectTypes = new ArrayList<>();
        for (WebProjectListCustomizeVO listCustomize : resultList) {
            AppProjectListCsVO appProjectType = new AppProjectListCsVO();
            appProjectType.setBorrowNid(listCustomize.getBorrowNid());
            appProjectType.setBorrowName(listCustomize.getBorrowNid());
            appProjectType.setBorrowDesc(listCustomize.getBorrowDesc());
            appProjectType.setBorrowTheFirst(listCustomize.getBorrowApr() + "%");
            appProjectType.setBorrowTheFirstDesc("历史年回报率");
            String borrowNid = listCustomize.getBorrowNid();
            String creditNid = borrowNid.substring(3);
            appProjectType.setBorrowTheSecond(String.valueOf(listCustomize.getCreditDiscount()) + "%");
            appProjectType.setBorrowTheSecondDesc("折让率");
            appProjectType.setBorrowTheThird(listCustomize.getBorrowPeriod() + "天");
            appProjectType.setBorrowTheThirdDesc("项目期限");
            String status = listCustomize.getStatus();
            if ("11".equals(status)) {
                appProjectType.setStatusName("立即承接");
                //可投金额
                String borrowAccountWait = String.valueOf(listCustomize.getCreditCapital().subtract(listCustomize.getCreditCapitalAssigned()));
                borrowAccountWait = CommonUtils.formatAmount(borrowAccountWait);
                appProjectType.setStatusNameDesc(org.apache.commons.lang.StringUtils.isBlank(borrowAccountWait) ? "" : "剩余" + borrowAccountWait);
            }

            appProjectType.setBorrowUrl(systemConfig.getAppFrontHost() + ProjectConstant.CREDIT_DETAIL + "/" + creditNid);
            appProjectType.setStatus(listCustomize.getStatus());
            appProjectType.setOnTime(listCustomize.getOnTime());

            if ("ZXH".equals(listCustomize.getBorrowType())) {
                appProjectType.setMark("尊享");
            } else if ("RTB".equals(listCustomize.getBorrowType())) {
                appProjectType.setMark("优选");
            }
            appProjectType.setBorrowType(listCustomize.getBorrowType());
            CommonUtils.convertNullToEmptyString(appProjectType);
            appProjectTypes.add(appProjectType);
        }
        return appProjectTypes;
    }

    /**
     * 获取移动端债转详情
     *
     * @author zhangyk
     * @date 2018/6/30 10:41
     */
    @Override
    public JSONObject getAppCreditDetail(String creditNid, Integer userId) {
        JSONObject resultMap = new JSONObject();

        CheckUtil.check(StringUtils.isNotBlank(creditNid), MsgEnum.ERR_PARAM_NUM);

        resultMap.put("userValidation", this.createUserValidation(userId));

        AppCreditDetailCustomizeVO appCreditDetailCustomizeVO = amTradeClient.selectHjhCreditByCreditNid(creditNid);


        if (appCreditDetailCustomizeVO != null) {
            List<Object> projectDetail = new ArrayList<>();

            resultMap.put(ProjectConstant.RES_PROJECT_INFO, createTenderCreditDetail(appCreditDetailCustomizeVO));
            String borrowNid = appCreditDetailCustomizeVO.getBidNid();

            List<BorrowRepayVO> borrowRepayVOList = amTradeClient.getBorrowRepayList(borrowNid);
            BorrowRepayVO borrowRepay = null;
            if (!CollectionUtils.isEmpty(borrowRepayVOList)) {
                borrowRepay = borrowRepayVOList.get(0);
            }
            // 2.根据项目标号获取相应的项目信息
            BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
            //借款人企业消息
            BorrowUserVO borrowUsers = cacheService.getCacheBorrowUser(borrowNid);
            //借款人信息
            BorrowManinfoVO borrowManinfoVO = cacheService.getCacheBorrowManInfo(borrowNid);
            //房产抵押信息
            List<BorrowHousesVO> borrowHousesList = amTradeClient.getBorrowHousesByNid(borrowNid);
            //车辆抵押信息
            List<BorrowCarinfoVO> borrowCarinfoList = amTradeClient.getBorrowCarinfoByNid(borrowNid);
            //基础信息
            List<BorrowMsgBean> baseTableData = new ArrayList<>();
            //资产信息
            List<BorrowMsgBean> assetsTableData = new ArrayList<>();
            //项目介绍
            List<BorrowMsgBean> intrTableData = new ArrayList<>();
            //信用状况
            List<BorrowMsgBean> credTableData = new ArrayList<>();
            //审核信息
            List<BorrowMsgBean> reviewTableData = new ArrayList<>();
            //其他信息
            List<BorrowMsgBean> otherTableData = new ArrayList<>();
            //借款类型
            BorrowInfoVO borrowInfoVO = amTradeClient.getBorrowInfoByNid(borrowNid);
            int borrowType = borrowInfoVO.getCompanyOrPersonal();
            if (borrowType == 1 && borrowUsers != null) {
                //基础信息
                baseTableData = packDetail(borrowUsers, 1, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
                //信用状况
                credTableData = packDetail(borrowUsers, 4, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
                //审核信息
                reviewTableData = packDetail(borrowUsers, 5, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
                //其他信息
                otherTableData = packDetail(borrowUsers, 6, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
            } else {
                if (borrowManinfoVO != null) {
                    //基础信息
                    baseTableData = packDetail(borrowManinfoVO, 1, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
                    //信用状况
                    credTableData = packDetail(borrowManinfoVO, 4, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
                    //审核信息
                    reviewTableData = packDetail(borrowManinfoVO, 5, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
                    //其他信息
                    otherTableData = packDetail(borrowManinfoVO, 6, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
                }
            }

            //资产信息
            if (!CollectionUtils.isEmpty(borrowHousesList)) {
                for (BorrowHousesVO borrowHouses : borrowHousesList) {
                    assetsTableData.addAll(packDetail(borrowHouses, 2, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel()));
                }
            }
            if (!CollectionUtils.isEmpty(borrowCarinfoList)) {
                for (BorrowCarinfoVO borrowCarinfo : borrowCarinfoList) {
                    assetsTableData.addAll(packDetail(borrowCarinfo, 2, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel()));
                }
            }
            //项目介绍
            intrTableData = packDetail(borrow, 3, borrowType, borrow.getBorrowLevel(), borrow.getInvestLevel());
            JSONObject baseTableDataJson = new JSONObject();
            JSONObject assetsTableDataJson = new JSONObject();
            JSONObject intrTableDataJson = new JSONObject();
            JSONObject credTableDataJson = new JSONObject();
            JSONObject reviewTableDataJson = new JSONObject();
            JSONObject otherTableDataJson = new JSONObject();
            baseTableDataJson.put("title", "基础信息");
            baseTableDataJson.put("msg", baseTableData);
            assetsTableDataJson.put("title", "资产信息");
            assetsTableDataJson.put("msg", assetsTableData);
            intrTableDataJson.put("title", "项目介绍");
            intrTableDataJson.put("msg", intrTableData);
            credTableDataJson.put("title", "信用状况");
            credTableDataJson.put("msg", credTableData);
            reviewTableDataJson.put("title", "审核信息");
            reviewTableDataJson.put("msg", reviewTableData);
            // 信批需求新增(放款后才显示)
            if (borrow.getStatus() >= 4 && borrowRepay != null) {
                //其他信息
                Integer loanTime = (borrowRepay.getCreateTime() == null? null : GetDate.getTime10(borrowRepay.getCreateTime()));
                String updateTime = ProjectConstant.getUpdateTime(loanTime, borrowRepay.getRepayYestime());
                otherTableDataJson.put("title", "其他信息（更新于" + updateTime + "）");
                otherTableDataJson.put("msg", otherTableData);
            }
            projectDetail.add(baseTableDataJson);
            projectDetail.add(assetsTableDataJson);
            projectDetail.add(intrTableDataJson);
            projectDetail.add(credTableDataJson);
            projectDetail.add(reviewTableDataJson);
            projectDetail.add(otherTableDataJson);
            resultMap.put(ProjectConstant.RES_PROJECT_DETAIL, projectDetail);
            // 查询相应的还款计划
            List<RepayPlanBean> repayPlanList = repayPlanService.getAppRepayPlan(borrowNid);
            resultMap.put("repayPlan", repayPlanList);
            // 风控信息
            BorrowInfoWithBLOBsVO borrowInfoWithBLOBsVO = amTradeClient.selectBorrowInfoWithBLOBSVOByBorrowId(borrowNid);
            Map<String,String> riskControl = new HashMap<>();
            if (borrowInfoWithBLOBsVO != null){
                riskControl.put("controlMeasures",borrowInfoWithBLOBsVO.getBorrowMeasuresMea() == null ? "" : borrowInfoWithBLOBsVO.getBorrowMeasuresMea().replace("\r\n",""));
                riskControl.put("controlMort",borrowInfoWithBLOBsVO.getBorrowMeasuresMort() == null ? "" : borrowInfoWithBLOBsVO.getBorrowMeasuresMort().replace("\r\n",""));
                riskControl.put("partner",StringUtils.isBlank(borrowInfoWithBLOBsVO.getBorrowMeasuresInstit())? "" : borrowInfoWithBLOBsVO.getBorrowMeasuresInstit());
                riskControl.put("agencyIntroduction",StringUtils.isBlank(borrowInfoWithBLOBsVO.getBorrowCompanyInstruction()) ? "" : borrowInfoWithBLOBsVO.getBorrowCompanyInstruction());
                riskControl.put("operatingProcess",StringUtils.isBlank(borrowInfoWithBLOBsVO.getBorrowOperatingProcess()) ? "" : borrowInfoWithBLOBsVO.getBorrowOperatingProcess());
            }

            resultMap.put("riskControl",riskControl);

        } else {
            resultMap.put(ProjectConstant.RES_PROJECT_INFO, new AppTransferDetailBean());
            resultMap.put(ProjectConstant.RES_PROJECT_DETAIL, new ArrayList<Object>());
            resultMap.put("repayPlan", new ArrayList<BorrowRepayPlanCsVO>());
        }
        resultMap.put(CustomConstants.APP_STATUS,BaseResult.SUCCESS);
        resultMap.put(CustomConstants.APP_STATUS_DESC,CustomConstants.APP_STATUS_DESC_SUCCESS);
        return resultMap;

    }


    /**
     * 组装债转信息
     *
     * @author zhangyk
     * @date 2018/6/30 14:14
     */
    private AppTransferDetailBean createTenderCreditDetail(AppCreditDetailCustomizeVO tenderCredit) {
        AppTransferDetailBean tenderCreditDetail = new AppTransferDetailBean();
        tenderCreditDetail.setBorrowRemain(CommonUtils.formatAmount(tenderCredit.getCreditCapital()));
        tenderCreditDetail.setBorrowProgress(tenderCredit.getBorrowSchedule());
        tenderCreditDetail.setTransferId(tenderCredit.getCreditNid() + "");
        tenderCreditDetail.setOnTime(tenderCredit.getCreditTime());
        tenderCreditDetail.setTransferDiscount(tenderCredit.getCreditDiscount());
        tenderCreditDetail.setAccount(tenderCredit.getCreditCapital());
        tenderCreditDetail.setBorrowApr(tenderCredit.getBidApr());
        tenderCreditDetail.setBorrowId(tenderCredit.getBidNid());
        tenderCreditDetail.setTransferLeft(tenderCredit.getCreditTermHold());
        tenderCreditDetail.setStatus(tenderCredit.getStatus());
        tenderCreditDetail.setBorrowProgressStatus("4");
        tenderCreditDetail.setBorrowPeriod(tenderCredit.getCreditTerm());
        tenderCreditDetail.setBorrowPeriodUnit("天");
        tenderCreditDetail.setTag("");
        tenderCreditDetail.setType("HZR");
        tenderCreditDetail.setRepayStyle(tenderCredit.getRepayStyle());
        tenderCreditDetail.setInvestLevel(tenderCredit.getInvestLevel());
        return tenderCreditDetail;
    }


    /**
     * 构建用户基本信息
     *
     * @author zhangyk
     * @date 2018/6/30 10:42
     */
    private JSONObject createUserValidation(Integer userId) {
        JSONObject userValidation = new JSONObject();

        boolean loginFlag = false;
        UserVO userVO = null;
		if (userId != null) {
			userVO = amUserClient.findUserById(userId);
			if (userVO != null) {
				loginFlag = true;
			}
		}

        if (!loginFlag) {
            userValidation.put("isLogined", false);
            userValidation.put("isOpened", false);
            userValidation.put("isSetPassword", false);
            userValidation.put("investflag", false);
            userValidation.put("isAllowed", false);
            userValidation.put("isAutoInves", false);
            userValidation.put("roleId", 0);

        } else {// 用户已经登陆
            userValidation.put("isLogined", true);
            //是否开户0未开户  1已开户
            if (userVO.getBankOpenAccount() == 0) {
                userValidation.put("isOpened", false);
            } else {
                userValidation.put("isOpened", true);
            }
            //是否设置过交易密码0未设置  1已设置
            if (userVO.getIsSetPassword() == 1) {
                userValidation.put("isSetPassword", true);
            } else {
                userValidation.put("isSetPassword", false);
            }
            // 用户角色
            UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
            if (userInfo != null) {
                userValidation.put("roleId", userInfo.getRoleId());
            } else {
                userValidation.put("roleId", 0);
            }

            // 是否是新手0新手 1老手
          /*  if ( null != userVO.getInvestflag() && userVO.getInvestflag() == 0) {
                userValidation.put("investflag", true);
            } else {
                userValidation.put("investflag", false);
            }*/
            //0未锁定,1锁定
            if (userVO.getStatus() == 0) {
                userValidation.put("isAllowed", true);
            } else {
                userValidation.put("isAllowed", false);
            }
            // 缴费授权状态
            userValidation.put("paymentAuthStatus", userVO.getPaymentAuthStatus());
            // 角色验证开关
            userValidation.put("isCheckUserRole", Boolean.parseBoolean(systemConfig.getRoleIsopen()));
            // 服务费授权开关
            userValidation.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());

            try{
                if(userVO.getIsEvaluationFlag()==1 && null != userVO.getEvaluationExpiredTime()){
                    //测评到期日
                    Long lCreate = userVO.getEvaluationExpiredTime().getTime();
                    //当前日期
                    Long lNow = System.currentTimeMillis();
                    if (lCreate <= lNow) {
                        //已过期需要重新评测
                        userValidation.put("isRiskTested", "2");
                    } else {
                        //未到一年有效期
                        userValidation.put("isRiskTested", "1");
                    }
                }else{
                    userValidation.put("isRiskTested", "0");
                }
                // modify by liuyang 20180411 用户是否完成风险测评标识 end
            }catch (Exception e){
                logger.error("是否进行过风险测评查询出错....", e);
                userValidation.put("isRiskTested", "2");
            }

            //
            HjhUserAuthVO userAuthVO = amTradeClient.getUserAuthByUserId(userId);
            if (Validator.isNotNull(userAuthVO) && (userAuthVO.getAutoInvesStatus() == 0 || userAuthVO.getAutoCreditStatus() == 0)) {
                userValidation.put("isAutoInves", false);
            } else {
                userValidation.put("isAutoInves", true);
            }
        }
        return userValidation;
    }


    /**
     * 移动端计划列表
     *
     * @author zhangyk
     * @date 2018/6/21 19:12
     */
    @Override
    public JSONObject searchAppPlanList(ProjectListRequest request) {
        JSONObject info = new JSONObject();
        Page page = Page.initPage(request.getPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        Integer count = amTradeClient.countAppPlanList(request);
        AppResult appResult = new AppResult();
        appResult.setData(new ArrayList<>());
        if (count == null) {
            logger.error("app查询计划原子层count异常");
            throw new RuntimeException("app查询计划原子层count异常");
        }

        // 合规审批需求  add by huanghui 20181123 start
        info.put("riskWarningHint", "智投服务是平台根据出借人授权，帮助出借人分散投标、循环出借的服务。到期后退出时效以实际债权转让完成用时为准 。");
        info.put("riskWarningContent", " $智投介绍$ " +
                "\n" +
                "智投服务是汇盈金服为您提供的本金自动循环出借及到期自动转让退出的自动投标服务，自动投标授权服务期限自授权出借之日起至退出完成。出借范围仅限于平台发布的借款标的或服务中被转让债权，您可随时查看持有的债权标的列表及标的详情。" +
                "\n\n" +
                " $出借人适当性管理告知$ " +
                "\n" +
                "作为网络借贷的出借人，应当具备出借风险意识，风险识别能力，请您在出借前，确保了解借款项目的主要风险，谨慎出借。\n" +
                "汇盈金服展示的参考回报不代表对实际回报的承诺；您的出借本金及对应回报可能无法按时收回。服务回报期限届满，系统对尚未结清标的自动发起债权转让。退出完成需债权标的全部结清，并且债权转让全部完成。您所持债权转让完成的具体时间，视债权转让市场交易情况而定。" +
                "\n\n" +
                "温馨提示：" +
                "\n" +
                "近期“智投服务”的额度开放时间在10:00，其余时间根据资产和运营情况适时开放。"
        );
        // 合规审批需求  add by huanghui 20181123 end

        page.setTotal(count);
        if (count > 0) {
            List<HjhPlanCustomizeVO> list = amTradeClient.searchAppPlanList(request);
            if (CollectionUtils.isEmpty(list)) {
                logger.error("app查询计划原子层list异常");
                throw new RuntimeException("app查询计划原子层list异常");
            }
            List<AppProjectListCustomizeVO> list2 = convertToAppProjectList(list);
            info.put("projectTotal", count);
            info.put("projectList", list2);
        } else {
            info.put("projectTotal", 0);
            info.put("projectList", new ArrayList<>());
        }
        info.put(ProjectConstant.APP_PAGE, request.getPage());
        info.put(CustomConstants.APP_STATUS, CustomConstants.APP_STATUS_SUCCESS);
        info.put(CustomConstants.APP_STATUS_DESC, CustomConstants.APP_STATUS_DESC_SUCCESS);
        info.put(CustomConstants.APP_REQUEST, ProjectConstant.REQUEST_HOME + ProjectConstant.APP_PROJECT_LIST + "/planList");
        return info;

    }


    /**
     * 适应客户端返回数据格式
     * @param planList
     * @return
     */
    private List<AppProjectListCustomizeVO> convertToAppProjectList(List<HjhPlanCustomizeVO> planList) {
        List<AppProjectListCustomizeVO> appProjectList = new ArrayList<>();
        String url = "";
        AppProjectListCustomizeVO appProjectListCustomize;
        if (!CollectionUtils.isEmpty(planList)) {
            appProjectList = new ArrayList<AppProjectListCustomizeVO>();
            String host = systemConfig.getAppFrontHost() ;
            for (HjhPlanCustomizeVO entity : planList) {
                appProjectListCustomize = new AppProjectListCustomizeVO();
                /*重构整合 开始*/
                appProjectListCustomize.setBorrowTheFirst(entity.getPlanApr() + "%");
                // mod by nxl 智投服务修改历史年回报率->参考年回报率
//                appProjectListCustomize.setBorrowTheFirstDesc("历史年回报率");
                appProjectListCustomize.setBorrowTheFirstDesc("参考年回报率");
                appProjectListCustomize.setBorrowTheSecond(entity.getPlanPeriod());
                // mod by nxl 智投服务修改锁定期限->服务回报期限
//                appProjectListCustomize.setBorrowTheSecondDesc("锁定期限");
                appProjectListCustomize.setBorrowTheSecondDesc("服务回报期限");
               // appProjectListCustomize.setStatusNameDesc(StringUtils.isNotBlank(entity.getAvailableInvestAccount()) ? "额度"+ entity.getAvailableInvestAccount() : "");

                if ("稍后开启".equals(entity.getStatusName())){    //1.启用  2.关闭
                    // 20.立即加入  21.稍后开启
                    appProjectListCustomize.setStatus("21");
                    appProjectListCustomize.setStatusName("稍后开启");
                }/*else if("立即加入".equals(entity.getStatusName())){  //1.启用  2.关闭
                    appProjectListCustomize.setStatus("20");
                    appProjectListCustomize.setStatusName("立即加入");
                }*/
                //mod by nxl 智投服务 修改立即加入->授权服务
                else if("授权服务".equals(entity.getStatusName())){  //1.启用  2.关闭
                    appProjectListCustomize.setStatus("20");
                    appProjectListCustomize.setStatusName("授权服务");
                    appProjectListCustomize.setStatusNameDesc(StringUtils.isNotBlank(entity.getAvailableInvestAccountNew()) ? "额度"+ CommonUtils.formatAmount(entity.getAvailableInvestAccountNew()) : "");
                }
                /*重构整合 结束*/
                appProjectListCustomize.setBorrowName(entity.getPlanName());
                appProjectListCustomize.setPlanApr(entity.getPlanApr());
                appProjectListCustomize.setBorrowApr(entity.getPlanApr());
                appProjectListCustomize.setPlanPeriod(entity.getPlanPeriod());
                appProjectListCustomize.setBorrowPeriod(entity.getPlanPeriod());
                appProjectListCustomize.setBorrowAccountWait(entity.getAvailableInvestAccount());
                appProjectListCustomize.setStatusName(entity.getStatusName());
                appProjectListCustomize.setBorrowNid(entity.getPlanNid());
                appProjectListCustomize.setBorrowAccountWait(entity.getAvailableInvestAccount());
                String couponEnable = entity.getCouponEnable();
                if (org.apache.commons.lang.StringUtils.isEmpty(couponEnable) || "0".equals(couponEnable)) {
                    couponEnable = "0";
                } else {
                    couponEnable = "1";
                }
                appProjectListCustomize.setCouponEnable(couponEnable);
                // 项目详情url
                url = host + ProjectConstant.HJH_DETAIL_INFO_URL+  entity.getPlanNid() ;
                appProjectListCustomize.setBorrowUrl(url);
                appProjectListCustomize.setProjectType("HJH");
                appProjectListCustomize.setBorrowType("HJH");
                appProjectListCustomize.setMark("");
                // 应客户端要求，返回空串
                CommonUtils.convertNullToEmptyString(appProjectListCustomize);
                appProjectList.add(appProjectListCustomize);
            }
        }
        return appProjectList;
    }







    /**
     * 移动端计划详情
     *
     * @author zhangyk
     * @date 2018/6/29 18:54
     */
    @Override
    public JSONObject getAppPlanDetail(String planNid, Integer userId) {
        JSONObject result = new JSONObject();
        //Map<String, Object> resultMap = new HashMap<>();
        CheckUtil.check(StringUtils.isNotBlank(planNid), MsgEnum.ERR_PARAM_NUM);


        PlanDetailCustomizeVO customize = amTradeClient.getPlanDetailByPlanNid(planNid);
        if (customize == null) {
            logger.error("传入计划id无对应计划,planNid is {}...", planNid);
            throw new RuntimeException("传入计划id无对应计划信息");
        }

        logger.info("customize:{}", JSONObject.toJSONString(customize));
        // 计划基本信息
        this.setPlanInfo(result, customize);
        // 用户的用户验证
        this.setUserValidationInfo(result, userId);

        result.put(CustomConstants.APP_STATUS,BaseResult.SUCCESS);
        result.put(CustomConstants.APP_STATUS_DESC,CustomConstants.APP_STATUS_DESC_SUCCESS);
        return  result;


    }

    /**
     * 散标出借记录列表
     * @param info
     * @param form
     */
    @Override
    public void createProjectInvestPage(JSONObject info, AppProjectInvestBeanRequest form) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid", form.getBorrowNid());
        int recordTotal = this.amTradeClient.countProjectInvestRecordTotal(params);
        String count = this.amTradeClient.countMoneyByBorrowId(params);
        if(count != null && !"".equals(count)){
            info.put("account", DF_FOR_VIEW.format(new BigDecimal(count)));
        }else{
            info.put("account", "0");
        }
        if (recordTotal > 0) { // 查询相应的汇直投列表数据
            int limit = form.getPageSize();
            int page = form.getCurrPage();
            int offSet = (page - 1) * limit;
            if (offSet == 0 || offSet > 0) {
                params.put("limitStart", offSet);
            }
            if (limit > 0) {
                params.put("limitEnd", limit);
            }
            List<AppProjectInvestListCustomizeVO> recordList = amTradeClient.selectProjectInvestList(params);
            Map<String, String> clientMap = CacheUtil.getParamNameMap(CustomConstants.CLIENT);
            for (AppProjectInvestListCustomizeVO obj : recordList){
                obj.setClientName(clientMap.get(String.valueOf(obj.getClient())));
            }

            info.put("list", recordList);
            info.put("userCount", String.valueOf(recordTotal));
            //判断本次查询是否已经全部查出数据
            if((page * limit) > recordTotal){
                info.put("isEnd", true);
            }else{
                info.put("isEnd", false);
            }
        } else {
            info.put("list", new ArrayList<AppProjectInvestListCustomizeVO>());
            info.put("userCount", "0");
            info.put("isEnd", true);
        }
    }


    /**
     * 创建计划的标的组成分页信息
     * @param result
     * @param planNid
     * @param pageNo
     * @param pageSize
     */
    @Override
    public void searchHjhPlanBorrow(HjhPlanBorrowResultBean result, String planNid, int pageNo, int pageSize) {
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
                List<HjhPlanBorrowResultBean.BorrowList> borrowList = result.getBorrowList();
                HjhPlanBorrowResultBean.BorrowList borrow = null;
                for (DebtPlanBorrowCustomizeVO entity : consumeList) {
                    borrow = new HjhPlanBorrowResultBean.BorrowList();
                    borrow.setBorrowApr(entity.getBorrowApr());
                    borrow.setBorrowNid(entity.getBorrowNid());
                    borrow.setBorrowPeriod(entity.getBorrowPeriod());
                    borrow.setTureName(entity.getTrueName());
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
     * app 端汇计划加入记录
     * @param result
     * @param planNid
     * @param pageNo
     * @param pageSize
     */
    @Override
    public void getHjhPlanAccede(HjhPlanAccedeResultBean result, String planNid, int pageNo, int pageSize) {

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
                List<HjhPlanAccedeResultBean.AccedeList> accedeList = result.getAccedeList();
                HjhPlanAccedeResultBean.AccedeList accede = null;
                Map<String, String> relationMap = CacheUtil.getParamNameMap("USER_RELATION");
                for (DebtPlanAccedeCustomizeVO entity : recordList) {
                    entity.setClientName(relationMap.get(String.valueOf(entity.getClient())));
                    accede = new HjhPlanAccedeResultBean.AccedeList();
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
     * app端债转承接记录
     * @param transferId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public BaseResultBeanFrontEnd investRecord(String transferId, Integer currentPage, Integer pageSize) {
        TransferInvestRecordResultBean result = new TransferInvestRecordResultBean();
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("creditNid", transferId);
            int recordTotal = amTradeClient.countTenderCreditInvestRecordTotal(params);
            if (recordTotal > 0) { // 查询相应的汇直投列表数据
                int limit = pageSize;
                int page = currentPage;
                int offSet = (page - 1) * limit;
                if (offSet == 0 || offSet > 0) {
                    params.put("limitStart", offSet);
                }
                if (limit > 0) {
                    params.put("limitEnd", limit);
                }
                List<AppTenderCreditInvestListCustomizeVO> recordList = amTradeClient.searchTenderCreditInvestList(params);
                //获取债转出借人次和已债转金额
                List<BorrowCreditVO> creditList = amTradeClient.selectBorrowCreditByNid(transferId);
                if (creditList != null && creditList.size() == 1) {
                    BorrowCreditVO credit = creditList.get(0);
                    result.setUserCount(String.valueOf(credit.getAssignNum()));
                    result.setAccount(CommonUtils.formatAmount(credit.getCreditCapitalAssigned()));
                }else{
                    result.setAccount("0");
                    result.setUserCount("0");
                }
                //判断是否最后一页
                if(recordTotal<=page*limit){
                    result.setIsEnd(true);
                }else{
                    result.setIsEnd(false);
                }
                result.setIsEnd(true);
                if (!CollectionUtils.isEmpty(recordList)){
                    result.setList(recordList);
                }else{
                    result.setList(new ArrayList<>());
                }
            } else {
                result.setAccount("0");
                result.setUserCount("0");
                result.setIsEnd(true);
                result.setList(new ArrayList<AppTenderCreditInvestListCustomizeVO>());
            }
            result.setStatus(BaseResultBeanFrontEnd.SUCCESS);
            result.setStatusDesc(BaseResultBeanFrontEnd.SUCCESS_MSG);
        } catch (Exception e) {
            result.setStatus(BaseResultBeanFrontEnd.FAIL);
            result.setStatusDesc(BaseResultBeanFrontEnd.FAIL_MSG);
        }

        return result;
    }

    /**
     * 根据planNid获取计划加入金额
     * @param params
     * @return
     */
    private String getPlanAccedeAccount(Map<String,Object> params) {
        Long sum = amTradeClient.selectPlanAccedeSum(params);// 加入总金额
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        if (sum == null || sum == 0) {
            return "0";
        } else {
            return df.format(sum);
        }
    }


    /**
     * 检查当前访问用户是否登录、是否开户、是否设置交易密码、是否允许使用、是否完成风险测评、是否授权
     *
     */
    private void setUserValidationInfo(JSONObject resultMap, Integer userId) {

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
        if (!loginFlag) {  // 未登录
            userLoginInfo.setLogined(Boolean.FALSE);
        } else {
            userLoginInfo.setLogined(Boolean.TRUE);
            // 2. 用户是否被禁用
            userLoginInfo.setAllowed(userVO.getStatus() == 0 ? Boolean.TRUE : Boolean.FALSE);
            // 3. 是否开户
            userLoginInfo.setOpened(userVO.getBankOpenAccount() == 0 ? Boolean.FALSE : Boolean.TRUE);
            // 4. 是否设置过交易密码
            userLoginInfo.setSetPassword(userVO.getIsSetPassword() == 1 ? Boolean.TRUE : Boolean.FALSE);
            // 检查用户角色是否能出借  合规接口改造之后需要判断
            UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
            // 返回前端角色
            userLoginInfo.setRoleId(userInfo.getRoleId());
            if (null != userInfo) {
                userLoginInfo.setIsAllowedTender(Boolean.TRUE);
                // 担保机构用户
                if (userInfo.getRoleId() == 3) {
                    userLoginInfo.setIsAllowedTender(Boolean.FALSE);
                }
                if("true".equals(systemConfig.getRoleIsopen())){
                    if (userInfo.getRoleId() == 2) {// 借款人不能出借
                        userLoginInfo.setIsAllowedTender(Boolean.FALSE);
                    }
                }
            } else {
                userLoginInfo.setIsAllowedTender(Boolean.FALSE);
            }


            try {

                if (userVO.getIsEvaluationFlag() == 1 && null != userVO.getEvaluationExpiredTime()) {
                    //测评到期日
                    Long lCreate = userVO.getEvaluationExpiredTime().getTime();
                    //当前日期
                    Long lNow = System.currentTimeMillis();
                    if (lCreate <= lNow) {
                        //已过期需要重新评测
                        userLoginInfo.setRiskTested("2");
                    } else {
                        //未到一年有效期
                        userLoginInfo.setRiskTested("1");
                    }
                } else {
                    userLoginInfo.setRiskTested("0");
                }


            } catch (Exception e) {
                logger.error("查询用户是否完成风险测评标识出错....", e);
                userLoginInfo.setRiskTested("0");
            }
            // 缴费授权状态
            userLoginInfo.setPaymentAuthStatus(userVO.getPaymentAuthStatus());
            // 服务费授权开关
            userLoginInfo.setPaymentAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
            userLoginInfo.setInvesAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH).getEnabledStatus());
            userLoginInfo.setCreditAuthOn(authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH).getEnabledStatus());

            // 是否校验用户角色
            userLoginInfo.setIsCheckUserRole(Boolean.parseBoolean(systemConfig.getRoleIsopen()));
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
    private void setPlanInfo(JSONObject resultMap, PlanDetailCustomizeVO customize) {

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

        projectInfo.setPlanApr(customize.getPlanApr());
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

        //标的等级
        projectInfo.setInvestLevel(customize.getInvestLevel());
        String minInvestment = customize.getDebtMinInvestment();
        String investIncrement = customize.getDebtInvestmentIncrement();
        projectInfo.setMinInvestment(StringUtils.isBlank(minInvestment)? "0" : new DecimalFormat("######").format(new BigDecimal(minInvestment)));
        projectInfo.setInvestmentIncrement(StringUtils.isBlank(investIncrement)? "0" : new DecimalFormat("######").format(new BigDecimal(investIncrement)));

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
     * 适应客户端数据返回
     * @param list
     * @return
     */
    private List<AppProjectListCsVO> convertToAppProjectType(List<AppProjectListCustomizeVO> list) {
        List<AppProjectListCsVO> appProjectTypes = new ArrayList<>();
        for(AppProjectListCustomizeVO listCustomize : list){
            AppProjectListCsVO appProjectType = new AppProjectListCsVO();
            appProjectType.setBorrowNid(listCustomize.getBorrowNid());
            appProjectType.setBorrowName(listCustomize.getBorrowNid());
            appProjectType.setBorrowDesc(listCustomize.getBorrowDesc());
            appProjectType.setBorrowTheFirst(listCustomize.getBorrowApr() + "%");
            appProjectType.setBorrowTheFirstDesc("历史年回报率");
            appProjectType.setBorrowTheSecond(listCustomize.getBorrowPeriod());
            appProjectType.setBorrowTheSecondDesc("项目期限");
            String status = listCustomize.getStatus();
            // 设置产品加息 显示收益率
            if (Validator.isIncrease(listCustomize.getIncreaseInterestFlag(), listCustomize.getBorrowExtraYieldOld())) {
                appProjectType.setBorrowExtraYield(listCustomize.getBorrowExtraYield());
            }else{
                appProjectType.setBorrowExtraYield("");
            }
            String borrowAccountWait = listCustomize.getBorrowAccountWait();
            if (status.equals("10")){

                appProjectType.setStatusName(listCustomize.getOnTime());
                //可投金额
                borrowAccountWait = CommonUtils.formatAmount(borrowAccountWait);
                appProjectType.setStatusNameDesc(org.apache.commons.lang.StringUtils.isBlank(borrowAccountWait)?"":"剩余" + borrowAccountWait);
            }else if(status.equals("11")){
                appProjectType.setStatusName("立即出借");
                //可投金额
                borrowAccountWait = CommonUtils.formatAmount(borrowAccountWait);
                appProjectType.setStatusNameDesc(org.apache.commons.lang.StringUtils.isBlank(borrowAccountWait)?"":"剩余" + borrowAccountWait);
            }else if (status.equals("12")){
                appProjectType.setStatusName("复审中");
            }else if (status.equals("13")){
                appProjectType.setStatusName("还款中");
            }else if (status.equals("14")){
                appProjectType.setStatusName("已还款");
            }
            appProjectType.setBorrowUrl(systemConfig.getAppFrontHost()  + HomePageDefine.BORROW  + listCustomize.getBorrowNid());
            appProjectType.setStatus(listCustomize.getStatus());
            appProjectType.setOnTime(listCustomize.getOnTime());

            if ("ZXH".equals(listCustomize.getBorrowType())){
                appProjectType.setMark("尊享");
            }else if("RTB".equals(listCustomize.getBorrowType())){
                appProjectType.setMark("优选");
            }
            appProjectType.setBorrowType(listCustomize.getBorrowType());
            // 应客户端要求，返回空串
            CommonUtils.convertNullToEmptyString(appProjectType);
            appProjectTypes.add(appProjectType);
        }
        return appProjectTypes;
    }


    /**
     * 承接记录列表
     * @author zhangyk
     * @date 2018/8/9 10:39
     */
    @Override
    public JSONObject searchProjectUndertakeList(String borrowNid, HttpServletRequest request) {
        JSONObject info = new JSONObject();
        CheckUtil.check(StringUtils.isNotBlank(borrowNid),MsgEnum.ERR_OBJECT_REQUIRED,"标的编号");
        Integer currentPage = 1;
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
        Integer size = 10;
        if (request.getParameter("pageSize") != null) {
            size = Integer.parseInt(request.getParameter("pageSize"));
        }
        info.put("status", BaseResultBeanFrontEnd.SUCCESS);
        info.put("statusDesc", BaseResultBeanFrontEnd.SUCCESS_MSG);

        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("page", currentPage);
        mapParam.put("pageSize", size);
        mapParam.put("borrowNid", borrowNid);
        this.createProjectUndertakePage(info, mapParam);
        return info;
    }


    /**
     * 承接记录列表分页
     * @param info
     * @param mapParam
     */
    private void createProjectUndertakePage(JSONObject info, Map<String, Object> mapParam) {
        String borrowNid =  mapParam.get("borrowNid").toString();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid",borrowNid);
        int undertRecordCount = amTradeClient.countCreditTenderByBorrowNidAndUserId(params);
        String strAccount = amTradeClient.sumUndertakeAccount(borrowNid);
        String strUndertakeAccount = DF_FOR_VIEW.format(new BigDecimal("0"));
        if (StringUtils.isNotEmpty(strAccount)) {
            BigDecimal bdAccount = new BigDecimal(strAccount);
            strUndertakeAccount = DF_FOR_VIEW.format(bdAccount);
        }
        if (undertRecordCount > 0) {
            // 查询相应的汇直投列表数据
            int limit = Integer.parseInt(mapParam.get("pageSize").toString());
            int page = Integer.parseInt(mapParam.get("page").toString());
            int offSet = (page - 1) * limit;
            if (offSet == 0 || offSet > 0) {
                params.put("limitStart", offSet);
            }
            if (limit > 0) {
                params.put("limitEnd", limit);
            }
            List<ProjectUndertakeListVO> undertRecordList = amTradeClient.selectProjectUndertakeList(params);
            info.put("list", undertRecordList);
            // 总承接金额
            info.put("undertakeAccount", String.valueOf(strUndertakeAccount));
            // 承接总人次
            info.put("userCount", undertRecordCount);
            // 判断本次查询是否已经全部查出数据
            if ((page * limit) > undertRecordCount) {
                info.put("isEnd", true);
            } else {
                info.put("isEnd", false);
            }
        } else {
            info.put("list", new ArrayList<ProjectUndertakeListVO>());
            // 总承接金额
            info.put("undertakeAccount", "0");
            // 承接总人次
            info.put("userCount", 0);
            info.put("isEnd", true);
        }
    }
}
