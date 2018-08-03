package com.hyjf.cs.trade.service.home.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.WechatProjectListResponse;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.bean.app.AppBorrowProjectInfoBeanVO;
import com.hyjf.cs.trade.bean.app.AppModuleBean;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.util.HomePageDefine;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.HjhAccedeRequest;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.trade.service.RepayPlanService;
import com.hyjf.cs.trade.service.home.WechatProjectListService;
import com.hyjf.cs.trade.util.ProjectConstant;

import javax.servlet.http.HttpServletRequest;

@Service
public class WechatProjectListServiceImpl implements WechatProjectListService {


    private static Logger logger = LoggerFactory.getLogger(WechatProjectListServiceImpl.class);

    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");


    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private RepayPlanService repayPlanService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private AmAdsClient amAdsClient;

    @Autowired
    private SystemConfig systemConfig;


    /**
     * 获取散标详情
     *
     * @author zhangyk
     * @date 2018/7/2 11:33
     */
    @Override
    public JSONObject getProjectDetail(String borrowNid, String type, String token) {
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
        boolean isPaymentAuth = false;
        Integer userId = null;
        // 判断用户是否登录
        WebViewUser webViewUser = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(token)) {
            webViewUser = RedisUtils.getObj(RedisConstants.USER_TOKEN_REDIS + token, WebViewUser.class);
        }
        userId = webViewUser.getUserId();
        if (userId != null && userId > 0) {
            UserVO users = amUserClient.findUserById(userId);
            if (users != null) {
                //判断是否开户
                if (users.getBankOpenAccount() != null && users.getBankOpenAccount() == 1) {
                    isOpened = true;
                }
                // 检查用户角色是否能投资  合规接口改造之后需要判断
                UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
                if (userInfo.getRoleId() == 3) {// 担保机构用户
                    //borrowDetailResultBean.setStatus("99");
                    //borrowDetailResultBean.setStatusDesc("担保机构用户不能进行投资");
                    //borrowDetailResultBean.setIsAllowedTender(Boolean.FALSE);
                    borrowDetailResultBean.put("isAllowedTender", Boolean.FALSE);
                }
                //判断是否设置交易密码
                if (users.getIsSetPassword() != null && users.getIsSetPassword() == 1) {
                    isSetPassword = true;
                }

                // TODO: 2018/7/2   字段不全  后期处理 //是否授权
                /*if (users.getAuthStatus() != null && users.getAuthStatus() == 1) {
                    isAutoInves = true;
                }*/

                // TODO: 2018/7/2 字段类型不一致待处理 //缴费授权状态
               /* if(users.getPaymentAuthStatus()!=null && users.getPaymentAuthStatus()==1){
                    isPaymentAuth = true;
                }*/

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
        ProjectCustomeDetailVO borrow = amTradeClient.searchProjectDetail(map);
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
        userValidation.put("isPaymentAuth", isPaymentAuth);
        borrowDetailResultBean.put("userValidation", userValidation);

        //获取标的信息
        if (borrow == null) {
            borrowDetailResultBean.put("status", "100");
            borrowDetailResultBean.put("statusDesc", "标的信息不存在");
            //weChatResult = new WeChatResult().buildErrorResponse(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
            return borrowDetailResultBean;
        } else {
            borrowDetailResultBean.put("status", WeChatResult.SUCCESS);
            borrowDetailResultBean.put("statusDesc", WeChatResult.SUCCESS_DESC);
            borrowProjectInfoBean.setBorrowRemain(borrow.getInvestAccount());
            borrowProjectInfoBean.setBorrowProgress(borrow.getBorrowSchedule());
            borrowProjectInfoBean.setOnTime(borrow.getOnTime());
            borrowProjectInfoBean.setAccount(borrow.getBorrowAccount());
            borrowProjectInfoBean.setBorrowApr(borrow.getBorrowApr());
            borrowProjectInfoBean.setBorrowId(borrowNid);
            borrowProjectInfoBean.setOnAccrual((borrow.getRecoverLastTime() == null ? "放款成功立即计息" : borrow.getRecoverLastTime()));
            //0：备案中 1：初审中 2：投资中 3：复审中 4：还款中 5：已还款 6：已流标 7：待授权
            borrowProjectInfoBean.setStatus(borrow.getStatus());
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
            BorrowUserVO borrowUsers = amTradeClient.getBorrowUser(borrowNid);
            //借款人信息
            BorrowManinfoVO borrowManinfo = amTradeClient.getBorrowManinfo(borrowNid);
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

            // TODO: 2018/7/2  后期处理 // 风控信息
            /*AppRiskControlCustomize riskControl = wxBorrowService.selectRiskControl(borrowNid);
            if(riskControl==null){
                riskControl = new AppRiskControlCustomize();
                riskControl.setControlMeasures("");
                riskControl.setControlMort("");
            }else {
                riskControl.setControlMeasures(riskControl.getControlMeasures()==null?"":riskControl.getControlMeasures().replace("\r\n", ""));
                riskControl.setControlMort(riskControl.getControlMort()==null?"":riskControl.getControlMort().replace("\r\n", ""));
            }
            //风控信息对象返回给前端
            borrowDetailResultBean.setAppRiskControlCustomize(riskControl); */

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
            if (Integer.parseInt(borrow.getStatus()) >= 4) {
                //其他信息
                String updateTime = ProjectConstant.getUpdateTime(borrowRepay.getAddTime(), borrowRepay.getRepayYestime());
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
                borrowRepayPlanBean.setAccount(DF_FOR_VIEW.format(borrowRepayPlan.getRepayTotal()));
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
                        borrowRepayPlanBean.setAccount(DF_FOR_VIEW.format(borrowRepayPlan.getRepayTotal()));
                        repayPlanList.add(borrowRepayPlanBean);
                    }
                }
            }
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
     * 散标投资记录列表
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
    public JSONObject getPlanDetail(String planId, String token) {
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
        this.setUserValidationInfo(resultMap, token);

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
    public HjhPlanBorrowResultBean getPlanBorrowList(String planId, int currPage, int pageSize) {
        CheckUtil.check(StringUtils.isNotBlank(planId), MsgEnum.ERR_OBJECT_BLANK,"计划编号");
        HjhPlanBorrowResultBean vo = new HjhPlanBorrowResultBean();
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
     * 获取计划标的加入记录
     * @author zhangyk
     * @date 2018/7/30 11:28
     */
    @Override
    public Object getPlanAccedeList(String planId, int currPage, int pageSize) {
        CheckUtil.check(StringUtils.isNotBlank(planId),MsgEnum.ERR_OBJECT_REQUIRED,"计划编号");
        HjhPlanAccedeResultBean resultBean = new HjhPlanAccedeResultBean();

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
    private void getHjhPlanAccede(HjhPlanAccedeResultBean result, String planNid, int pageNo, int pageSize) {

        HjhAccedeRequest request = new HjhAccedeRequest();
        request.setPlanNid(planNid);
        int recordTotal = this.amTradeClient.countPlanAccedeRecordTotal(request);

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
        result.setWarning("市场有风险 投资需谨慎");
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
                if (hjhUserAuthVO != null) {
                    //自动投标授权状态
                    result.setAutoInvesStatus(hjhUserAuthVO.getAutoInvesStatus());
                    //自动债转授权状态
                    result.setAutoCreditStatus(hjhUserAuthVO.getAutoCreditStatus());
                } else {
                    //自动投标授权状态
                    result.setAutoInvesStatus(0);
                    //自动债转授权状态
                    result.setAutoCreditStatus(0);
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

        // 获取累计投资金额
        TotalInvestAndInterestResponse res2 = baseClient.getExe(HomePageDefine.INVEST_INVEREST_AMOUNT_URL, TotalInvestAndInterestResponse.class);
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
        this.createBannerPage(result);

        return result;
    }

    /**
     * 获取首页项目列表信息
     *
     * @author zhangyk
     * @date 2018/7/24 10:46
     */
    @Override
    public WechatHomePageResult getHomeProejctList(int currPage, int pageSize, String showPlanFlag, String token) {
        WechatHomePageResult result = new WechatHomePageResult();
        Page page = Page.initPage(currPage, pageSize);
        WechatHomePageResult vo = new WechatHomePageResult();
        vo.setCurrentPage(currPage);
        vo.setPageSize(pageSize);
        result = getProjectListAsyn(result, currPage, pageSize, showPlanFlag);

        if (currPage == 1) {
            //获取用户id
            WebViewUser webViewUser = null;
            Integer userId = null;
            if (org.apache.commons.lang3.StringUtils.isNotBlank(token)) {
                webViewUser = RedisUtils.getObj(RedisConstants.USER_TOKEN_REDIS + token, WebViewUser.class);
            }
            if (webViewUser != null) {
                userId = webViewUser.getUserId();
            }
            String HOST = systemConfig.getWebHost();
            //判断用户是否登录
            if (userId == null || userId <= 0) {
                //获取新手标
                vo.setHomeXshProjectList(this.createProjectNewPage(userId, HOST));
            } else {
                //查询用户是否开户
                UserVO userVO = amUserClient.findUserById(userId);
                Integer userType = userVO == null ? 0 : userVO.getBankOpenAccount();
                if (userType == 0) {//未开户
                    //获取新手标
                    vo.setHomeXshProjectList(this.createProjectNewPage(userId, HOST));
                } else if (userType == 1) {//已开户
                    //获取用户累计投资条数
                    Integer count = amTradeClient.getTotalInverestCount(String.valueOf(userId));
                    if (count == null || count <= 0) {
                        //获取新手标
                        vo.setHomeXshProjectList(this.createProjectNewPage(userId, HOST));
                    }
                }
            }
        }
        return result;
    }


    /**
     * 创建首页广告
     */
    private void createAdPic(WechatHomePageResult vo, String type) {
        AdsRequest request = new AdsRequest();
        request.setLimitStart(HomePageDefine.BANNER_SIZE_LIMIT_START);
        request.setLimitEnd(HomePageDefine.BANNER_SIZE_LIMIT_END);
        request.setHost(systemConfig.getWebHost());

        String code = "";

        if ("0".equals(type)) {// 未注册
            code = "wechat_regist_888";
        } else if ("1".equals(type)) {
            code = "wechat_open_888";
        }
        request.setCode(code);
        List<AppAdsCustomizeVO> picList = amAdsClient.getBannerList(request);
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
        request.setHost("");
        String code = "wechat_banner";
        request.setCode(code);

        List<AppAdsCustomizeVO> picList = amAdsClient.getBannerList(request);
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
        request.setHost(systemConfig.getWebHost());
        request.setCode(module);

        List<AppAdsCustomizeVO> picList = amAdsClient.getBannerList(request);
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
     * @param token
     */
    private void setUserValidationInfo(Map<String, Object> resultMap, String token) {

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        boolean loginFlag = false;
        UserVO userVO = null;
        Integer userId = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(token)) {
            WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisConstants.USER_TOKEN_REDIS + token, WebViewUserVO.class);
            if (webViewUserVO != null) {
                userId = webViewUserVO.getUserId();
                userVO = amUserClient.findUserById(userId);
                if (userVO != null) {
                    loginFlag = true;
                }
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

            // 7.缴费授权状态
            userLoginInfo.setPaymentAuthStatus(userVO.getPaymentAuthStatus());

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
     * 是否投资flag
     *
     * @param userId
     * @param borrowNid
     * @param borrowType
     * @return
     */
    private boolean isTenderBorrow(Integer userId, String borrowNid,
                                   String borrowType) {
        //根据borrowNid查询borrow表
        BorrowVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
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
     * 分页获取首页数据
     *
     * @param
     * @return
     */
    private WechatHomePageResult getProjectListAsyn(WechatHomePageResult vo, int currentPage, int pageSize, String showPlanFlag) {

        List<WechatHomeProjectListVO> list = null;
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
        WechatProjectListResponse response = baseClient.postExe(HomePageDefine.WECHAT_HOME_PROJECT_LIST_URL, projectMap, WechatProjectListResponse.class);
        list = response.getResultList();
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


        if (showPlanFlag == null) {
            if (currentPage == 1) {
                WechatProjectListResponse res = baseClient.getExe(HomePageDefine.WECHAT_HOME_PLAN_LATER_URL, WechatProjectListResponse.class);
                List<WechatHomeProjectListVO> hjhList = res.getResultList();
                if (list.size() == 0) {
                    //补两条
                    hjhList.addAll(list);
                    list = hjhList;
                } else if (list.size() > 0 && !"HJH".equals(list.get(0).getBorrowType())) {
                    //补两条
                    hjhList.addAll(list);
                    list = hjhList;
                } else if (list.size() > 1 && !"HJH".equals(list.get(1).getBorrowType())) {
                    //补一条
                    list.add(1, hjhList.get(0));
                }
            }
        }
        if (vo.getEndPage() == 1) {
            WechatProjectListResponse res = baseClient.getExe(HomePageDefine.WECHAT_HOME_REPAYMENT_URL, WechatProjectListResponse.class);
            List<WechatHomeProjectListVO> hjhList = res.getResultList();
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
                            wechatHomeProjectListCustomize.setOnTime("立即投资");
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
            wechatHomeProjectListCustomize.setAccountWait(df.format(new com.ibm.icu.math.BigDecimal(wechatHomeProjectListCustomize.getAccountWait())));
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
        List<AppProjectListCustomizeVO> projectList = searchProjectNewList(host);
        if (projectList != null && !projectList.isEmpty() && projectList.size() != 0) {
            if (list.size() == 0 && projectList.size() != 0) {
                AppProjectListCustomizeVO project = projectList.get(0);
                WechatHomeProjectListVO customize = new WechatHomeProjectListVO();
                customize.setBorrowNid(project.getBorrowNid());
                customize.setBorrowName(project.getBorrowName());
                customize.setBorrowType(project.getBorrowType());
                customize.setBorrowApr(project.getBorrowApr());
                customize.setBorrowPeriod(project.getBorrowPeriodInt() + "");
                customize.setBorrowPeriodType(project.getBorrowPeriodType());
                if ("0".equals(project.getOnTime()) || "".equals(project.getOnTime())) {
                    switch (project.getStatus()) {
                        case "10":
                            customize.setOnTime(project.getOnTime());
                            break;
                        case "11":
                            customize.setOnTime("立即投资");
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


    private List<AppProjectListCustomizeVO> searchProjectNewList(String host) {
        List<AppProjectListCustomizeVO> projectList = new ArrayList<>();

        // 取得新手汇项目（定时发标）
        AppProjectListRequest request = new AppProjectListRequest();
        request.setPlatform("wx");
        request.setType("4");
        request.setHost(host);
        // 查询首页定时发标的项目
        List<AppProjectListCustomizeVO> listNewOnTime = amTradeClient.searchAppProjectList(request);


        // 取得新手汇项目（投资中）
        String statusNewInvest = "15";
        request.setStatus(statusNewInvest);
        // 查询首页定时发标的项目
        List<AppProjectListCustomizeVO> listNewInvest = amTradeClient.searchAppProjectList(request);
        if (listNewInvest != null && listNewInvest.size() > 0) {
            for (int i = 0; i < listNewInvest.size(); i++) {
                AppProjectListCustomizeVO newInvest = listNewInvest.get(i);
                projectList.add(newInvest);
            }
        }
        // 新手汇项目（投资中）为空
        if (projectList.size() == 0) {
            if (listNewOnTime != null && listNewOnTime.size() > 0) {
                for (int i = 0; i < listNewOnTime.size(); i++) {
                    AppProjectListCustomizeVO newOnTime = listNewOnTime.get(i);
                    projectList.add(newOnTime);
                }
            }
        }
        return projectList;
    }


}
