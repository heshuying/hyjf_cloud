package com.hyjf.cs.trade.controller.web.repay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequest;
import com.hyjf.am.resquest.trade.RepayRequestDetailRequest;
import com.hyjf.am.resquest.user.WebUserRepayTransferRequest;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayPlanListVO;
import com.hyjf.am.vo.trade.repay.RepayWaitOrgVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.ZIPGenerator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.repay.RepayManageService;
import com.hyjf.cs.trade.vo.BatchRepayPageRequestVO;
import com.hyjf.cs.trade.vo.RepayDetailRequestVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 还款管理相关页面接口
 * @author hesy
 * @version RepayManageController, v0.1 2018/6/23 14:09
 */
@Api(value = "web端-还款管理相关页面接口", tags ="web端-还款管理相关页面接口")
@RestController
@RequestMapping("/hyjf-web/repay")
public class RepayManageController extends BaseTradeController {
    /** 用户ID */
    private static final String VAL_USERID = "userId";
    /**总还款数量*/
    private static final String VAL_ALLCOUNT = "allCount";
    /**成功数量*/
    private static final String VAL_SUCCESSCOUNT = "successCount";
    /**失败数量*/
    private static final String VAL_FAILCOUNT = "failCount";
    // 根据用户ID和模版号给某用户发短信
    public static final String SMSSENDFORUSER = "smsSendForUser";

    //初始化放款/承接时间(大于2018年3月28号法大大上线时间)
    private static final int ADD_TIME = 1922195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final int ADD_TIME328 = 1522195200;

    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    RepayManageService repayManageService;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    private AuthService authService;

    /**
     * 用户还款页面统计数据查询
     */
    @ApiOperation(value = "用户还款页面统计数据", notes = "用户还款页面统计数据查询")
    @PostMapping(value = "/repay_page_data", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectRepayPageData(@RequestHeader(value = "userId") Integer userId){
        WebResult<Map<String,Object>> result = new WebResult<>();
        logger.info("用户还款页面统计数据repay_page_data开始，userId：" + userId);
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        if ("2".equals(userVO.getRoleId())) {
            AccountVO account = repayManageService.getAccountByUserId(userVO.getUserId());
            // 1.待还款总额
            BigDecimal repay = account.getBankWaitCapital().add(account.getBankWaitInterest());
            BigDecimal repayMangeFee = repayManageService.getUserRepayFeeWaitTotal(userVO.getUserId());
            BigDecimal borrowAccountTotal = repayManageService.getUserBorrowAccountTotal(userVO.getUserId());
            repay = repay.add(repayMangeFee);
            resultMap.put("capitalTotal", CustomConstants.DF_FOR_VIEW.format(borrowAccountTotal));
            resultMap.put("capitalWaitTotal",CustomConstants.DF_FOR_VIEW.format(account.getBankWaitCapital()));
            resultMap.put("interestWaitTotal",CustomConstants.DF_FOR_VIEW.format(account.getBankWaitInterest()));
            resultMap.put("feeWaitTotal",CustomConstants.DF_FOR_VIEW.format(repayMangeFee));
            resultMap.put("repayMoney",CustomConstants.DF_FOR_VIEW.format(repay));
        }
        // 根据RoleId 判断用户为担保机构
        else if ("3".equals(userVO.getRoleId())) {
            // 1.待垫付总额
            RepayWaitOrgVO repayWaitOrgVO = repayManageService.getOrgRepayWaitTotal(userVO.getUserId());
            BigDecimal repayMangeFee = repayManageService.getOrgRepayFeeWaitTotal(userVO.getUserId());
            resultMap.put("capitalWaitTotal",CustomConstants.DF_FOR_VIEW.format(repayWaitOrgVO.getCapitalWaitTotal()));
            resultMap.put("interestWaitTotal",CustomConstants.DF_FOR_VIEW.format(repayWaitOrgVO.getInterestWaitTotal()));
            resultMap.put("feeWaitTotal",CustomConstants.DF_FOR_VIEW.format(repayMangeFee));
            resultMap.put("repayMoney",CustomConstants.DF_FOR_VIEW.format(repayWaitOrgVO.getWaitTotal().add(repayMangeFee)));
        }
        resultMap.put("roleId", userVO.getRoleId());
        resultMap.put("userId", userVO.getUserId());
        resultMap.put("repayAuthOn","0");
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        resultMap.put("repayAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoRepayStatus());
        // 是否开启服务费授权 0未授权  1已授权
        resultMap.put("paymentAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoPaymentStatus());
        // 是否开启服务费授权 0未开启  1已开启
        resultMap.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        result.setData(resultMap);
        logger.info("用户还款页面统计数据repay_page_data结束，返回数据：" + result);
        return result;
    }

    @ApiOperation(value = "平台登录密码校验", notes = "平台登录密码校验")
    @ApiImplicitParam(name = "paraMap", value = "{password:string}", dataType = "Map")
    @PostMapping(value = "/pwd_check", produces = "application/json; charset=utf-8")
    public WebResult<String> pwdCheck(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String,String> paraMap){
        WebResult<String> result = new WebResult<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        if(userVO == null){
            result.setData("false");
            result.setStatusDesc("用户不存在");
            return result;
        }
        String password = paraMap.get("password");
        if(StringUtils.isBlank(password)){
            result.setData("false");
            result.setStatusDesc("请输入密码");
            return result;
        }
        // 密码校验
        if(!repayManageService.checkPassword(userVO.getUserId(),password)){
            result.setData("false");
            result.setStatusDesc("密码不正确");
            return result;
        }
        result.setData("true");
        return result;
    }

    /**
     * 用户待还款列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "用户待还款列表", notes = "用户待还款列表")
    @PostMapping(value = "/repay_wait_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectRepayWaitList(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<>();
        result.setData(Collections.emptyList());
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        logger.info("用户待还款列表开始，userId:{}", userVO.getUserId());
        // 请求参数校验
        requestBean.setStatus("0");
        requestBean.setUserId(String.valueOf(userVO.getUserId()));
        repayManageService.checkForRepayList(requestBean);
        // 分页信息
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        Integer repayCount = repayManageService.selectRepayCount(requestBean);
        page.setTotal(repayCount);
        requestBean.setLimitStart(page.getOffset());
        requestBean.setLimitEnd(page.getLimit());
        try {
            List<RepayListCustomizeVO> resultList = repayManageService.selectRepayList(requestBean);
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取用户待还款列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }
        result.setPage(page);
        return result;
    }

    /**
     * 用户已还款列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "用户已还款列表", notes = "用户已还款列表")
    @PostMapping(value = "/repayed_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectRepayedList(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        result.setData(Collections.emptyList());
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        logger.info("用户已还款列表开始，userId:{}", userVO.getUserId());
        // 请求参数校验
        requestBean.setStatus("1");
        requestBean.setUserId(String.valueOf(userVO.getUserId()));
        repayManageService.checkForRepayList(requestBean);
        // 分页信息
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        Integer repayCount = repayManageService.selectRepayCount(requestBean);
        page.setTotal(repayCount);
        requestBean.setLimitStart(page.getOffset());
        requestBean.setLimitEnd(page.getLimit());
        try {
            List<RepayListCustomizeVO> resultList = repayManageService.selectRepayList(requestBean);
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取用户已还款列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }
        result.setPage(page);
        return result;
    }

    /**
     * 用户还款计划列表
     */
    @ApiOperation(value = "用户还款计划列表", notes = "用户还款计划列表")
    @PostMapping(value = "/repay_plan_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayPlanListVO>> selectRepayPlanList(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayListRequest requestBean){
        WebResult<List<RepayPlanListVO>> result = new WebResult<>();
        result.setData(Collections.emptyList());
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        logger.info("用户还款计划列表开始，userId:{}, requestBean:{}", userVO.getUserId(), requestBean);
        // 请求参数校验
        if(requestBean == null || StringUtils.isBlank(requestBean.getBorrowNid())){
            logger.error("borrowNid为空");
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc("borrowNid为空");
        }

        try {
            List<RepayPlanListVO> resultList = repayManageService.selectRepayPlanList(requestBean.getBorrowNid());
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("获取用户还款计划列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }
        return result;
    }

    /**
     * 用户代还标的,债转详情.
     * @param userId
     * @param transferRequest
     * @return
     * @Author : huanghui
     */
    @ApiOperation(value = "用户待还标的-债转详情", notes = "用户待还标的-债转详情")
    @PostMapping(value = "/user_repay_detail", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>>  userRepayDetail(@RequestHeader(value = "userId") Integer userId, @RequestBody WebUserRepayTransferRequest transferRequest){
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        logger.info("用户待还标的-债转详情. userId=" + userId + ";BorrowNid=" + transferRequest.getBorrowNid());

        // 根据用户ID 查询用户信息
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        if (userVO == null){
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc("无法获取到用户信息");
            result.setData(Collections.emptyMap());
            return result;
        }

        resultMap.put("userId", userVO.getUserId());

        /** 当前用户已登录并且标的NID不为空 */
        String verificationFlag = null;
        if (userVO != null && StringUtils.isNotBlank(transferRequest.getBorrowNid())){
            WebUserTransferBorrowInfoCustomizeVO borrowInfo = this.repayManageService.getUserTransferBorrowInfo(transferRequest.getBorrowNid());

            // 单纯的作为验证标识.
            if (borrowInfo.getPlanNid() != null) {
                verificationFlag = borrowInfo.getPlanNid();
            } else {
                verificationFlag = null;
            }
            //居间协议
            Integer fddStatus = 0;
            List<TenderAgreementVO> tenderAgreementsNid = null;
            tenderAgreementsNid = this.repayManageService.selectTenderAgreementByNid(transferRequest.getBorrowNid());
            if (tenderAgreementsNid != null && tenderAgreementsNid.size() > 0) {
                TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                fddStatus = tenderAgreement.getStatus();
                //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                if (fddStatus.equals(3)) {
                    fddStatus = 1;
                } else {
                    //隐藏下载按钮
                    fddStatus = 2;
                }
            } else {
            /**
             * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
             * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
             * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
             */
                BorrowRecoverVO borrowRecoverVO = repayManageService.selectBorrowRecoverByNid(transferRequest.getBorrowNid());
                int addTime = ADD_TIME;
                if(borrowRecoverVO != null){
                    addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                }
                if (addTime<ADD_TIME328) {
                    //下载老版本协议
                    fddStatus = 1;
                }else{
                    //隐藏下载按钮
                    fddStatus = 0;
                }
            }

            // 计算到账金额
            if (borrowInfo.getSucSmount() != null) {
                BigDecimal oldYesAccount = borrowInfo.getSucSmount();
                borrowInfo.setSucSmount(oldYesAccount.subtract(borrowInfo.getServiceFee()));
            } else {
                borrowInfo.setSucSmount(new BigDecimal(0));
            }
            resultMap.put("verificationFlag", verificationFlag);
            resultMap.put("borrowInfo", borrowInfo);
            resultMap.put("fddStatus", fddStatus);
            result.setData(resultMap);
        }

        return result;
    }

    /**
     *
     * @param repayTransferRequest
     * @return
     */
    @ApiOperation(value = "用户待还标的-债转列表", notes = "用户待还标的-债转列表")
    @PostMapping(value = "/userRepayDetailAjax", produces = "application/json; charset=utf-8")
    public  WebResult userRepayDetailAjax(@RequestBody WebUserRepayTransferRequest repayTransferRequest){
        WebResult result = repayManageService.selectUserRepayTransferDetailList(repayTransferRequest);
        return result;
    }

    /**
     * 担保机构待还款列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "担保机构待还款列表", notes = "担保机构待还款列表")
    @PostMapping(value = "/wait_org_list", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectOrgRepayWaitList(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        logger.info("担保机构待还款列表开始，userId:{}, requestBean:{}", userId, JSON.toJSONString(requestBean));
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        requestBean.setStatus("0");
        requestBean.setUserId(String.valueOf(userVO.getUserId()));
        // 请求参数校验
        repayManageService.checkForRepayList(requestBean);
        // 分页信息
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        Integer repayCount = repayManageService.selectOrgRepayCount(requestBean);
        page.setTotal(repayCount);
        requestBean.setLimitStart(page.getOffset());
        requestBean.setLimitEnd(page.getLimit());
        try {
            List<RepayListCustomizeVO> resultList = repayManageService.selectOrgRepayList(requestBean);
            resultMap.put("recordList",resultList);
            BigDecimal repayMoneyTotal = repayManageService.selectOrgRepayWaitTotalCurrent(requestBean);
            resultMap.put("repayMoneyTotal", repayMoneyTotal);
            resultMap.put("repayMoneyNum", repayCount);
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("【担保机构待还款列表】获取列表发生异常！", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }
        result.setPage(page);
        logger.info("担保机构待还款列表结束，userId:{}, result:{}", userId, JSON.toJSONString(result));
        return result;
    }

    /**
     * 担保机构已还款列表
     * @param requestBean
     */
    @ApiOperation(value = "担保机构已还款列表", notes = "担保机构已还款列表")
    @PostMapping(value = "/repayed_org_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectOrgRepayedList(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        logger.info("担保机构待还款列表开始，userId:{}", userVO.getUserId());
        requestBean.setStatus("1");
        requestBean.setUserId(String.valueOf(userVO.getUserId()));
        // 请求参数校验
        repayManageService.checkForRepayList(requestBean);
        // 分页信息
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        Integer repayCount = repayManageService.selectOrgRepayedCount(requestBean);
        page.setTotal(repayCount);
        requestBean.setLimitStart(page.getOffset());
        requestBean.setLimitEnd(page.getLimit());
        try {
            List<RepayListCustomizeVO> resultList = repayManageService.selectOrgRepayedList(requestBean);
            result.setData(resultList);
        } catch (Exception e) {
            logger.error("【担保机构已还款列表】获取列表发生异常！", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }
        result.setPage(page);
        return result;
    }

    /**
     * 还款详情页面数据
     * @auther: hesy
     * @date: 2018/7/9
     */
    @ApiOperation(value = "还款详情页面数据", notes = "还款详情页面数据")
    @PostMapping(value = "/repay_detail", produces = "application/json; charset=utf-8")
    public WebResult repayDetail(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayDetailRequestVO requestBean, HttpServletRequest request){
        logger.info("还款详情页面数据接口开始:requestBean:" + JSON.toJSONString(requestBean));
        WebResult result = new WebResult();
        JSONObject detaiResult;
        RepayRequestDetailRequest detailRequestBean = new RepayRequestDetailRequest();
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        if(StringUtils.isBlank(requestBean.getBorrowNid())){
            logger.error("【还款详情页面】借款编号为空！");
            result.setStatus(WebResult.FAIL);
            result.setStatusDesc("借款编号为空");
            return result;
        }
        // 是否一次性还款
        boolean isAllRepay = false;
        if(requestBean.getIsAllRepay() != null && "1".equals(requestBean.getIsAllRepay())) {
            isAllRepay = true;
        }
        // 提交的逾期还款期数
        int latePeriod = 0;
        if(requestBean.getLatePeriod() != null) {
            if(requestBean.getLatePeriod() == -1){// -1为全部还款
                isAllRepay = true;
            }
            latePeriod = requestBean.getLatePeriod();
        }
        detailRequestBean.setAllRepay(isAllRepay);
        detailRequestBean.setLatePeriod(latePeriod);
        detailRequestBean.setBorrowNid(requestBean.getBorrowNid());
        if(userVO != null){
            detailRequestBean.setUserId(userVO.getUserId());
            detailRequestBean.setUserName(userVO.getUsername());
            detailRequestBean.setRoleId(userVO.getRoleId());
        }
        try {
            detaiResult = repayManageService.getRepayDetailData(detailRequestBean);
            if(detaiResult == null){
                result.setStatus("404");
                result.setStatusDesc("未找到数据");
                result.setData(Collections.emptyMap());
                return result;
            }
        } catch (Exception e) {
            logger.error("【还款详情页面】获取页面数据发生异常！", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            result.setData(Collections.emptyMap());
            return result;
        }
        if(detaiResult!= null && "1".equals(detaiResult.getString("onlyAllRepay"))) {
            isAllRepay = true;
        }
        resultMap.put("isAllRepay", isAllRepay ? "1" : "0");
        resultMap.put("repayAuthOn","0");
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        resultMap.put("repayAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoRepayStatus());
        // 是否开启服务费授权 0未授权  1已授权
        resultMap.put("paymentAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoPaymentStatus());
        // 是否开启服务费授权 0未开启  1已开启
        resultMap.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        resultMap.put("repayProject", detaiResult);
        resultMap.put("roleId", userVO.getRoleId());
        // 主要用于判断冻结表是否存在数据
        boolean isFreeze = !repayManageService.checkRepayInfo(null,requestBean.getBorrowNid());
        resultMap.put("isFreeze", isFreeze);
        result.setData(resultMap);
        return result;
    }

    /**
     * 还款申请
     * @auther: hesy
     * @date: 2018/7/10
     */
    @ApiOperation(value = "还款申请", notes = "还款申请")
    @PostMapping(value = "/repay_request", produces = "application/json; charset=utf-8")
    public WebResult repayRequest(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayRequest requestBean, HttpServletRequest request){
        WebResult webResult = new WebResult();
        Map<String,String> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        String borrowNid = requestBean.getBorrowNid();
        BorrowInfoVO borrowInfoVO = repayManageService.getBorrowInfoByNid(borrowNid);
        if(borrowInfoVO == null){
            logger.error("【还款申请】标的不存在！借款编号：{}", borrowNid);
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("标的不存在！借款编号：" + borrowNid);
            return webResult;
        }
        resultMap.put("borrowNid", borrowInfoVO.getBorrowNid());
        resultMap.put("borrowName", StringUtils.isBlank(borrowInfoVO.getName()) ? borrowInfoVO.getProjectName() : borrowInfoVO.getName());
        webResult.setData(resultMap);
        /** redis 锁 */
        boolean isRepay = RedisUtils.tranactionSet(RedisConstants.CONCURRENCE_REPAY_REQUEST + borrowNid, 60);
        if(!isRepay){
            logger.error("【还款申请】借款编号：{}，已提交还款，正在处理中！还款用户ID：{}", borrowNid, userId);
            long retTime = RedisUtils.ttl(RedisConstants.CONCURRENCE_REPAY_REQUEST + borrowNid);
            String dateStr = DateUtils.nowDateAddSecond((int) retTime);
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("该项目已提交还款，正在处理中，请" + dateStr + "后再试！");
            return webResult;
        }
        String pAllRepay = requestBean.getIsAllRepay();
        boolean isAllRepay = false;
        if(pAllRepay != null && "1".equals(pAllRepay)) {
            isAllRepay = true;
        }
        int latePeriod = 0;// 提交的逾期还款期数
        if(requestBean.getLatePeriod() != null){
            if(requestBean.getLatePeriod() == -1){// -1为全部还款
                isAllRepay = true;
            }
            latePeriod = requestBean.getLatePeriod();// 用户选择的逾期期数
        }
        String roleId = userVO.getRoleId();
        repayManageService.checkForSingleRepayRequest(borrowNid,requestBean.getPassword(),userVO);// 校验基本信息
        RepayBean repayBean = repayManageService.getRepayBean(userVO.getUserId(), userVO.getRoleId(), borrowNid, isAllRepay, latePeriod);
        repayManageService.checkForBankBalance(userVO,repayBean);// 校验银行余额
        String ip = GetCilentIP.getIpAddr(request);
        repayBean.setIp(ip);
        BigDecimal repayTotal = repayBean.getRepayAccountAll();
        String account = userVO.getBankAccount();
        // 用户还款
        try {
            if(StringUtils.isNotEmpty(roleId) && "3".equals(roleId)){// 担保机构还款
                String txDate = GetOrderIdUtils.getTxDate();// 交易日期
                String txTime = GetOrderIdUtils.getTxTime();// 交易时间
                String seqNo = GetOrderIdUtils.getSeqNo(6);// 交易流水号
                String orderId = txDate + txTime + seqNo;// 交易日期+交易时间+交易流水号
                //add by cwyang 2017-07-25 还款去重
                boolean result = repayManageService.checkRepayInfo(null, borrowNid);
                if (!result) {
                    webResult.setStatus(WebResult.ERROR);
                    webResult.setStatusDesc("该项目已提交还款，正在处理中，请20分钟后再试！");
                    return webResult;
                }
                //插入担保机构冻结信息日志表 add by wgx 2018-09-11
                repayManageService.insertRepayOrgFreezeLog(userId, orderId, account, borrowNid, repayBean, userVO.getUsername(), isAllRepay, latePeriod);
                Map<String, Object> map = repayManageService.getBankRefinanceFreezePage(userId, userVO.getUsername(), ip, orderId, borrowNid, repayTotal, account);
                webResult.setData(map);
                return webResult;
            } else {
                String orderId = GetOrderIdUtils.getOrderId2(userVO.getUserId());
                //add by cwyang 2017-07-25 还款去重
                boolean result = repayManageService.checkRepayInfo(null, borrowNid);
                if (!result) {
                    webResult.setStatus(WebResult.ERROR);
                    webResult.setStatusDesc("该项目已提交还款，正在处理中，请20分钟后再试！");
                    return webResult;
                }
                //插入冻结信息日志表 add by cwyang 2017-07-08
                repayManageService.addFreezeLog(userVO.getUserId(), orderId, account, borrowNid, repayTotal, userVO.getUsername());
                // 申请还款冻结资金
                // 调用江西银行还款申请冻结资金
                return repayManageService.getBalanceFreeze(userVO, borrowNid, repayBean, orderId, account, webResult, isAllRepay, latePeriod);
            }
        } catch (Exception e) {
            logger.error("还款申请冻结资金异常", e);
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("还款申请冻结资金异常");
            return webResult;
        }
    }

    /**
     * 担保机构批量还款页面数据
     * @auther: hesy
     * @date: 2018/7/9
     */
    @ApiOperation(value = "担保机构批量还款页面数据", notes = "担保机构批量还款页面数据")
    @PostMapping(value = "/batch_repaydata", produces = "application/json; charset=utf-8")
    public WebResult orgUserBatchRepayData(@RequestHeader(value = "userId") Integer userId, @RequestBody BatchRepayPageRequestVO requestBean){
        WebResult webResult = new WebResult();
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        if(userVO == null){
            logger.error("【担保机构批量还款页面】用户未登录！担保机构ID：{}", userId);
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("用户未登录");
            return webResult;
        }
        resultMap.put("orgUserId", userVO.getUserId());
        if (StringUtils.isNotBlank(requestBean.getStartDate()) && StringUtils.isNotBlank(requestBean.getEndDate())) {
            resultMap.put("startDate",requestBean.getStartDate());
            resultMap.put("endDate",requestBean.getEndDate());
            int endTime = Integer.parseInt(GetDate.get10Time(requestBean.getEndDate()));
            int startTime = Integer.parseInt(GetDate.get10Time(requestBean.getStartDate()));
            if (endTime < startTime) {
                webResult.setStatus(WebResult.ERROR);
                webResult.setStatusDesc("开始时间大于结束时间");
                return webResult;
            }
            int lastTime = endTime - startTime;
            if (lastTime > 60*60*24*7) {
                webResult.setStatus(WebResult.ERROR);
                webResult.setStatusDesc("时间差不能大于七天");
                return webResult;
            }
        }else{
            String startDate  = GetDate.formatDate(new java.util.Date());
            String endDate = GetDate.formatDate(new java.util.Date());
            requestBean.setStartDate(startDate);
            requestBean.setEndDate(endDate);
            resultMap.put("startDate",startDate);
            resultMap.put("endDate",endDate);
        }
        // 获取批量还款数据信息
        ProjectBean projectBean = repayManageService.getOrgBatchRepayData(String.valueOf(userVO.getUserId()),requestBean.getStartDate(),requestBean.getEndDate());
        resultMap.put("repayInfo", projectBean);
        resultMap.put("repayAuthStatus","");
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        // 是否开启服务费授权 0未授权  1已授权
        resultMap.put("paymentAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoPaymentStatus());
        // 是否开启服务费授权 0未开启  1已开启
        resultMap.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        webResult.setData(resultMap);
        return webResult;
    }

    /**
     * 担保机构批量还款条件校验
     * @auther: hesy
     * @date: 2018/7/9
     */
    @ApiOperation(value = "担保机构批量还款条件校验", notes = "担保机构批量还款条件校验")
    @PostMapping(value = "/batchrepay_check", produces = "application/json; charset=utf-8")
    public WebResult orgUserStartBatchRepayCheck(@RequestHeader(value = "userId") Integer userId, @RequestBody BatchRepayPageRequestVO requestBean) {
        WebResult webResult = new WebResult();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        if(userVO == null){
            logger.info("【担保机构批量还款校验】用户未登录！担保机构ID：{}", userId);
            webResult.setStatus(WebResult.ERROR);
            webResult.setData("990");
            webResult.setStatusDesc("未登录");
            return webResult;
        }
        String msg = "";
        if (!userVO.isBankOpenAccount()) {
            msg = "998";
            logger.info("【担保机构批量还款校验】用户未开户！担保机构ID：{}", userId);
            webResult.setData(msg);
            webResult.setStatusDesc("用户未开户");
            return webResult;
        }
        boolean isBalance = comperToOrgUserBalance(userVO.getUserId(), userVO.getBankAccount(), new BigDecimal(requestBean.getRepayTotal()));
        if (!isBalance) {//余额不足
            msg = "997";
            logger.info("【担保机构批量还款校验】用户银行可用余额不足！担保机构ID：{}", userId);
            webResult.setData(msg);
            webResult.setStatusDesc("余额不足");
            return webResult;
        }
        boolean result = RedisUtils.exists(RedisConstants.CONCURRENCE_BATCH_ORGREPAY_USERID + userVO.getUserId());
        if(result){
            msg = "999";
            logger.info("【担保机构批量还款校验】项目已提交还款，正在处理中！担保机构ID：{}", userId);
            long retTime = RedisUtils.ttl(RedisConstants.CONCURRENCE_BATCH_ORGREPAY_USERID + userVO.getUserId());
            String dateStr = DateUtils.nowDateAddSecond((int) retTime);
            webResult.setData(msg);
            webResult.setStatusDesc("您已提交过还款，正在处理中，请" + dateStr + "后再试！");
            return webResult;
        }
        boolean isTime = companyRepayTime(requestBean.getStartDate(),requestBean.getEndDate(),userVO.getUserId());
        if(isTime){
            msg = "996";
            logger.info("【担保机构批量还款校验】还款区间大于28天！担保机构ID：{}", userId);
            webResult.setData(msg);
            webResult.setStatusDesc("还款区间大于28天");
            return webResult;
        }
        // 服务费授权校验
        boolean isPaymentAuth = this.authService.checkPaymentAuthStatus(userId);
        if (!isPaymentAuth) {
            msg = "996";
            logger.info("【担保机构批量还款校验】未进行服务费授权!", userId);
            webResult.setData(msg);
            webResult.setStatusDesc("未进行服务费授权");
            return webResult;
        }
        webResult.setData(msg);
        return webResult;
    }

    /**
     * 担保机构批量还款
     * @auther: hesy
     * @date: 2018/7/9
     */
    @ApiOperation(value = "担保机构批量还款", notes = "担保机构批量还款")
    @PostMapping(value = "/batchrepay_action", produces = "application/json; charset=utf-8")
    public WebResult orgUserStartBatchRepay(@RequestHeader(value = "userId") Integer userId, @RequestBody BatchRepayPageRequestVO requestBean, HttpServletRequest request) {
        WebResult webResult = new WebResult();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        String startDate = requestBean.getStartDate();
        String endDate = requestBean.getEndDate();
        //查询该时间段的所有担保户的待还款记录并进行还款
        // 还款方法10分钟只能一次
        // 合规四期修改  wgx 2018/10/16
        if (userVO != null) {
            // 还款方法10分钟只能一次
            boolean result = RedisUtils.tranactionSet(RedisConstants.CONCURRENCE_BATCH_ORGREPAY_USERID + userId, 600);
            if (result) {
                BankOpenAccountVO userBankOpenAccount = this.repayManageService.getBankOpenAccount(userId);
                Map<String, Object> map = repayManageService.startOrgRepay(startDate, endDate, userId, requestBean.getPassword(), GetCilentIP.getIpAddr(request), userBankOpenAccount);
                webResult.setData(map);
            }else{
                logger.error("【担保机构批量还款】项目已提交还款，正在处理中！担保机构ID：{}", userId);
                long retTime = RedisUtils.ttl(RedisConstants.CONCURRENCE_BATCH_ORGREPAY_USERID + userId);
                String dateStr = DateUtils.nowDateAddSecond((int) retTime);
                webResult.setStatus(WebResult.ERROR);
                webResult.setStatusDesc("您已批量提交过还款，正在处理中，请" + dateStr + "后再试！");
                webResult.setData(Collections.emptyMap());
            }
        }else{
            webResult.setStatus(WebResult.ERROR);
            webResult.setData(Collections.emptyMap());
        }
        return webResult;
    }

    /**
     * 推送消息
     *
     * @param userid
     * @author Administrator
     * @param successRepaySize,int userid
     * @param allRepaySize
     */
    private void sendMessage(int allRepaySize, int successRepaySize,int userid) {
        Map<String, String> msg = new HashMap<String, String>();
        msg.put(VAL_ALLCOUNT, allRepaySize + "");// 所有还款项目
        msg.put(VAL_SUCCESSCOUNT, successRepaySize + "");//成功还款项目
        msg.put(VAL_FAILCOUNT, (allRepaySize - successRepaySize) + "");//成功还款项目
        msg.put(VAL_USERID, String.valueOf(userid));
        if (Validator.isNotNull(msg.get(VAL_USERID))) {
            try {
                UserVO users = repayManageService.getUserByUserId(Integer.valueOf(msg.get(VAL_USERID)));
                if (users == null) {
                    return;
                } else {
                    if (allRepaySize == successRepaySize) {//全部成功
                        SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, null, SMSSENDFORUSER, null, CustomConstants.JYTZ_PLAN_REPAYALL_SUCCESS,
                                CustomConstants.CHANNEL_TYPE_NORMAL);
                        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
                                UUID.randomUUID().toString(), smsMessage));
                    }else{// 改批次代偿，失败不发短信 2018-9-13 wgx
                        //SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USERID)), msg, null, null, SMSSENDFORUSER, null, CustomConstants.JYTZ_PLAN_REPAYPART_SUCCESS,
                        //        CustomConstants.CHANNEL_TYPE_NORMAL);
                        //smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
                        //        UUID.randomUUID().toString(), smsMessage));
                    }
                }
            } catch (MQException e) {
                logger.error("担保机构还款申请完成，发送短信时发生系统异常！", e);
            }
        }
    }

    private boolean comperToOrgUserBalance(Integer userId, String accountId,  BigDecimal repayTotal) {
        BigDecimal userBankBalance = this.repayManageService.getBankBalancePay(userId, accountId);
        if (repayTotal.compareTo(userBankBalance) == 0 || repayTotal.compareTo(userBankBalance) == -1) {
            return true;
        }
        return false;
    }

    /**
     * 查询是否存在提前28天以上的还款标的
     * @param endDate
     * @param userId
     * @return
     */
    private boolean companyRepayTime(String startDate,String endDate, Integer userId) {
        RepayListRequest form = new RepayListRequest();
        form.setUserId(userId + "");
        form.setRoleId("3");
        form.setStartDate(startDate);
        form.setEndDate(endDate);
        form.setStatus("0");
        form.setRepayStatus("0");
        form.setRepayTimeOrder("1");
        List<RepayListCustomizeVO> list = repayManageService.selectOrgRepayList(form);
        if(list != null && list.size() > 0){
            RepayListCustomizeVO customize = list.get(0);
            String repayTime = customize.getRepayTime();
            try {
                int day = GetDate.daysBetween(GetDate.getDayStart(new java.util.Date()), GetDate.getDayStart(repayTime));
                if(day >= 28){
                    return true;
                }
            } catch (ParseException e) {
                logger.error("校验还款区间是否大于28天时发生系统异常！", e);
                return true;
            }
        }
        return false;
    }

    /**
     * 还款收到报文后对合法性检查后的异步回调
     * @auther: hesy
     * @date: 2018/7/17
     */
    @ApiOperation(value = "还款收到报文后对合法性检查后的异步回调", notes = "还款收到报文后对合法性检查后的异步回调")
    @PostMapping("/repayVerifyReturn")
    public String repayVerifyReturnAction(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCallBean bean) throws Exception {
        BankCallResult result = new BankCallResult();
        bean.convert();
        String respCode = StringUtils.isBlank(bean.getRetCode()) ? null : bean.getRetCode();// 返回码
        if (StringUtils.isBlank(respCode)) {
            logger.error("【还款合法性检查异步通知】返回码为空！");
            return JSONObject.toJSONString(result, true);
        }
        String txDate = bean.getTxDate();
        String txTime = bean.getTxTime();
        String seqNo = bean.getSeqNo();
        String bankSeqNo = txDate + txTime + seqNo;
        BorrowApicronVO apicron = this.repayManageService.selectBorrowApicron(bankSeqNo);
        if (Validator.isNull(apicron)) {
            logger.error("【还款合法性检查异步通知】未查询到还款请求记录！银行唯一订单号：{}", bankSeqNo);
            // 更新相应的还款请求校验失败
            return JSONObject.toJSONString(result, true);
        }
        int repayStatus = apicron.getStatus();        // 当前批次还款状态
        String borrowNid = apicron.getBorrowNid();    // 当前批次还款标的号
        logger.info("【还款合法性检查异步通知】借款编号：{}，开始处理合法性检查异步回调。当前状态：{}", borrowNid, repayStatus);
        if (repayStatus == CustomConstants.BANK_BATCH_STATUS_SENDED) {
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                String retMsg = bean.getRetMsg();
                logger.error("【还款合法性检查异步通知】借款编号：{}，数据合法性异常！银行返回信息：{}",borrowNid, retMsg);
                apicron.setData(retMsg);
                apicron.setFailTimes((apicron.getFailTimes() + 1));
                // 更新任务API状态为还款校验失败
                boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_VERIFY_FAIL);
                if (!apicronResultFlag) {
                    throw new Exception("批次还款任务表(ht_borrow_apicron)更新状态(还款校验失败)失败！[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
                }
                // 更新相应的放款请求校验失败
                return JSONObject.toJSONString(result, true);
            }
            // 更新相应的放款请求校验成功
            boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_VERIFY_SUCCESS);
            if (!apicronResultFlag) {
                throw new Exception("批次还款任务表(ht_borrow_apicron)更新状态(还款校验成功)失败！[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
            }
        }
        result.setStatus(true);
        logger.info("【还款合法性检查异步通知】借款编号：{}，合法性检查异步回调结束。", borrowNid);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 还款业务处理结果的异步回调
     *
     * @param request
     * @param response
     * @param bean
     * @throws Exception
     */
    @ApiOperation(value = "还款业务处理结果的异步回调", notes = "还款业务处理结果的异步回调")
    @PostMapping("/repayResultReturn")
    public String repayResultReturn(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCallBean bean) throws Exception {
        BankCallResult result = new BankCallResult();
        bean.convert();
        String respCode = StringUtils.isBlank(bean.getRetCode()) ? null : bean.getRetCode();// 返回码
        if (StringUtils.isBlank(respCode)) {
            logger.error("【还款业务处理结果异步通知】返回码为空！");
            return JSONObject.toJSONString(result, true);
        }
        String txDate = bean.getTxDate();
        String txTime = bean.getTxTime();
        String seqNo = bean.getSeqNo();
        String bankSeqNo = txDate + txTime + seqNo;
        BorrowApicronVO apicron = this.repayManageService.selectBorrowApicron(bankSeqNo);
        if (Validator.isNull(apicron)) {
            logger.error("【还款业务处理结果异步通知】未查询到还款请求记录！银行唯一订单号：{}", bankSeqNo);
            return JSONObject.toJSONString(result, true);
        }
        // 当前批次还款状态
        int repayStatus = apicron.getStatus();
        String borrowNid = apicron.getBorrowNid();// 借款编号
        if (repayStatus == CustomConstants.BANK_BATCH_STATUS_SENDED // 还款请求成功状态可能是未收到银行回调 update by wgx 2019/02/12
                || repayStatus == CustomConstants.BANK_BATCH_STATUS_VERIFY_SUCCESS) {
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                String retMsg = bean.getRetMsg();
                logger.error("【还款业务处理结果异步通知】借款编号：{}，还款失败！银行返回信息：{}", retMsg, borrowNid);
                apicron.setData(retMsg);
                apicron.setFailTimes((apicron.getFailTimes() + 1));
                // 更新任务API状态为还款校验失败
                boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
                if (!apicronResultFlag) {
                    throw new Exception("批次还款任务表(ht_borrow_apicron)更新状态(还款失败)失败！[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
                }
                // 更新相应的还款请求校验失败
                return JSONObject.toJSONString(result, true);
            } else {
                // update 2018/10/17   wgx  银行异步回调成功认为还款成功, 直接发送消息处理还款
                String sucCount = bean.getSucCounts();// 成功笔数
                Integer txCounts = apicron.getTxCounts();
                if (sucCount != null && txCounts != null && !sucCount.equals(txCounts.toString())) {
                    String failCounts = bean.getFailCounts();// 失败笔数
                    String failAmount = bean.getFailAmount();// 失败金额
                    if(StringUtils.isNotBlank(failCounts)) {
                        logger.error("【还款业务处理结果异步通知】借款编号：{}，业务处理存在失败笔数！失败笔数：{}，失败本金：{}"
                                , borrowNid, failCounts, failAmount);
                    }
                }
                try {
                    // 还款任务
                    if (StringUtils.isBlank(apicron.getPlanNid())) {
                        // 直投类还款
                        commonProducer.messageSend(new MessageContent(MQConstant.BORROW_REPAY_ZT_RESULT_TOPIC,
                                borrowNid, apicron));
                        logger.info("【还款业务处理结果异步通知】借款编号：{}，发送散标还款结果处理消息。", borrowNid);
                    } else {
                        // 计划类还款
                        commonProducer.messageSend(new MessageContent(MQConstant.BORROW_REPAY_PLAN_RESULT_TOPIC,
                                borrowNid, apicron));
                        logger.info("【还款业务处理结果异步通知】借款编号：{}，智投编号：{}，发送智投还款结果处理消息。", borrowNid, apicron.getPlanNid());
                    }
                } catch (MQException e) {
                    logger.error(e.getMessage());
                    logger.error("【还款业务处理结果异步通知】借款编号：{}，{}发送还款结果处理消息失败！",
                            borrowNid, StringUtils.isNotBlank(apicron.getPlanNid()) ? "智投编号:" + apicron.getPlanNid() : "，");
                }
            }
        } else {
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     *
     * 下载借款用户的协议
     *
     * @author renxingchen
     */
    @ApiOperation(value = "下载借款用户的协议", notes = "下载借款用户的协议")
    @GetMapping("/downloadBorrowerPdf/{borrowNid}/{random}")
    public void downloadBorrowerPdf(@PathVariable Integer random, @PathVariable String borrowNid, HttpServletRequest request, HttpServletResponse response) {
        List<File> files = new ArrayList<File>();

        //CreateAgreementController createAgreementController = new CreateAgreementController();
        // 查询用户项目的出借情况
        List<WebUserInvestListCustomizeVO> investlist;
        investlist = repayManageService.selectUserInvestList(borrowNid);

        String flag = "1";
        for (WebUserInvestListCustomizeVO tmp : investlist) {
            File file;
            //居间服务于借款协议时展示标的维度的借款方与出借方的关系的，出借方来自于 huiyingdai_borrow_tender
            //原居间协议(注掉) file = createAgreementPDFFile(request, response, form, tmp.getUserId());
            //(1)调用新作的居间借款协议
            //file = createAgreementController.createAgreementPDFFile(request, response, form);
            file = repayManageService.createAgreementPDFFileRepay(request, response, borrowNid, tmp.getNid(), flag, random);

            if (file != null) {
                files.add(file);
            }
        }
        ZIPGenerator.generateZip(response, files, borrowNid);
        return;

        /*************************************借款人借款列表先不下载债转**********************************/
		/*// 散标进计划--》建标时打上计划的标签，so散标债转只能转成散标，计划中的标的债转也只能转成计划中的标的
		// 一个标的一旦开始被出借要么是散标中中的标的，要么是计划中用的标的

		// (2.1)散标的债转协议(原)
		Borrow borrow = this.repayService.getBorrowByNid(borrowNid);
		if( borrow != null ){
			if(StringUtils.isNotEmpty(borrow.getPlanNid())){
				//计划中的标的
				List<HjhDebtCreditTender> hjhCreditTenderList = this.repayService.selectHjhCreditTenderList(borrowNid);//hyjf_hjh_debt_credit_tender
				for (HjhDebtCreditTender hjhCreditTender : hjhCreditTenderList) {
					//调用下载计划债转协议的方法
					CreditAssignedBean tenderCreditAssignedBean  = new CreditAssignedBean();
					tenderCreditAssignedBean.setBidNid(hjhCreditTender.getBorrowNid());// 标号
					tenderCreditAssignedBean.setCreditNid(hjhCreditTender.getCreditNid());// 债转编号
					tenderCreditAssignedBean.setCreditTenderNid(hjhCreditTender.getInvestOrderId());//原始出借订单号
					tenderCreditAssignedBean.setAssignNid(hjhCreditTender.getAssignOrderId());//债转后的新的"出借"订单号
					if(currentUserId != null){
						tenderCreditAssignedBean.setCurrentUserId(currentUserId);
					}
					// 模板参数对象(查新表)
					Map<String, Object> creditContract = tenderCreditService.selectHJHUserCreditContract(tenderCreditAssignedBean);
					try {
						File file = PdfGenerator.generatePdfFile(request, response, ((HjhDebtCreditTender) creditContract.get("creditTender")).getAssignOrderId() + ".pdf", CustomConstants.HJH_CREDIT_CONTRACT, creditContract);
						if(file!=null){
							files.add(file);
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}

			} else {
				//散标中的标的
				// 根据标号判断标的是否有承接
				List<CreditTender> creditTenderList = this.repayService.selectCreditTenderList(borrowNid);//huiyingdai_credit_tender
				// 债转协议
				if(creditTenderList!= null && creditTenderList.size()>0){
					for (CreditTender creditTender : creditTenderList) {
						Map<String,String> param = new HashMap<String,String>();
						// 标号
						param.put("bidNid", creditTender.getBidNid());
						// 债转编号
						param.put("creditNid", creditTender.getCreditNid());
						// 债转出借订单号
						param.put("creditTenderNid", creditTender.getCreditTenderNid());
						// 承接订单号
						param.put("assignNid", creditTender.getAssignNid());
						// 当前登陆者
						param.put("currentUserId", currentUserId.toString());

						// 模板参数对象
						Map<String, Object> creditContract = this.repayService.selectUserCreditContract(param);
						try {
							File file = PdfGenerator.generatePdfFile(request, response, String.valueOf(creditTender.getAssignNid()) + ".pdf", CustomConstants.CREDIT_CONTRACT, creditContract);
							if(file!=null){
								files.add(file);
							}
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				}
			}
		}
		ZIPGenerator.generateZip(response, files, borrowNid);*/
    }

    /**
     * 机构垫付还款同步回调
     *
     * @param request
     * @param callBackBean
     * @auther: wgx
     * @date: 2018/10/13
     * @return
     */
    @ApiOperation(value = "代偿冻结的同步回调", notes = "代偿冻结的同步回调")
    @PostMapping("/getRepaySyncReturn")
    public ModelAndView repayReturn(HttpServletRequest request, @RequestBody BankCallBean callBackBean) {
        logger.debug("代偿冻结同步回调开始");
        String orderId = request.getParameter("orderId");
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isBlank(orderId)) {
            modelAndView.addObject("message", "还款失败,失败原因：" + repayManageService.getBankRetMsg(callBackBean.getRetCode()));
            logger.info("【代偿冻结同步回调】担保机构还款失败,错误码：{}", callBackBean.getRetCode());
            return modelAndView;
        }
        logger.info("【代偿冻结同步回调】担保机构还款申请成功,订单号:{}", orderId);
        return modelAndView;
    }

    /**
     * 机构垫付还款异步回调
     *
     * @param request
     * @param callBackBean
     * @auther: wgx
     * @date: 2018/10/13
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "代偿冻结的异步回调", notes = "代偿冻结的异步回调")
    @PostMapping("/getRepayAsyncReturn")
    public BankCallResult repayAsyncReturn(HttpServletRequest request, @RequestBody BankCallBean callBackBean) throws IOException {
        BankCallResult result = new BankCallResult();
        result.setStatus(false);
        String orderId = request.getParameter("orderId");
        boolean isBatchRepay = Boolean.parseBoolean(request.getParameter("isBatchRepay"));// 是否批量还款
        if (StringUtils.isBlank(orderId)) {
            logger.error("【代偿冻结异步回调】获取冻结订单号失败！");
            return result;
        }
        List<BankRepayOrgFreezeLogVO> orgLogList = repayManageService.getBankRepayOrgFreezeLogList(null, orderId);
        if (orgLogList == null || orgLogList.size() == 0) {
            logger.error("【代偿冻结异步回调】还款申请资金已解冻！订单号：{}", orderId);
            return result;
        }
        logger.info("【代偿冻结异步回调】{}还款冻结开始处理。订单号：{}", isBatchRepay ? "批量" : "", orderId);
        String accountId = callBackBean.getAccountId();//电子账号
        Integer userId = orgLogList.get(0).getRepayUserId();
        String userName = orgLogList.get(0).getRepayUserName();
        // 担保机构还款冻结状态查询
        boolean hasFreeze = repayManageService.queryBalanceFreeze(Integer.valueOf(userId), userName, orderId, accountId);
        if (hasFreeze) {
            for (BankRepayOrgFreezeLogVO orgFreezeLog : orgLogList) {
                String borrowNid = orgFreezeLog.getBorrowNid();
                boolean isAllRepay = orgFreezeLog.getAllRepayFlag() == 1;
                int latePeriod = orgFreezeLog.getLatePeriod() == null ? 0 : orgFreezeLog.getLatePeriod();
                try {
                    // 必须在计算还款明细前加锁，防止智投自动投资债转,垫付机构锁放到异步回调中
                    boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrowNid, 300);
                    if (!tranactionSetFlag) {//设置失败
                        logger.error("【代偿冻结异步回调】借款编号：{}，正在处理项目债转！", borrowNid);
                        long retTime = RedisUtils.ttl(RedisConstants.HJH_DEBT_SWAPING + borrowNid);
                        String dateStr = DateUtils.nowDateAddSecond((int) retTime);
                        throw new CheckException(MsgEnum.ERR_AMT_REPAY_AUTO_CREDIT, dateStr);
                    }
                    // 担保机构的还款
                    RepayBean repay = repayManageService.getRepayBean(userId, "3", borrowNid, isAllRepay, latePeriod);
                    if (repay != null) {
                        BigDecimal repayTotal = repay.getRepayAccountAll();
                        if (repayTotal.compareTo(orgFreezeLog.getAmountFreeze()) != 0) {
                            logger.error("【代偿冻结异步回调】借款编号：{}，冻结金额和还款金额不一致！冻结金额：{}，还款总金额：{}",
                                    borrowNid, orgFreezeLog.getAmountFreeze(), repayTotal);
                            if (!isBatchRepay) {
                                return result;
                            }
                            continue;
                        }
                        // 还款后变更数据
                        callBackBean.setOrderId(orderId);
                        //还款后变更数据
                        boolean updateResult = this.repayManageService.updateForRepayRequest(repay, callBackBean, isAllRepay,latePeriod);
                        if (updateResult) {
                            repayManageService.deleteOrgFreezeTempLogs(orderId, borrowNid);
                            // 如果有正在出让的债权,先去把出让状态停止
                            this.repayManageService.updateBorrowCreditStautus(borrowNid);
                            RedisUtils.del(RedisConstants.HJH_DEBT_SWAPING + borrowNid);
                            logger.info("【代偿冻结异步回调】借款编号：{}，还款申请成功。担保机构ID：{}，订单号:{}", borrowNid, userId, orderId);
                        } else {
                            logger.error("【代偿冻结异步回调】借款编号：{}，还款更新数据失败！担保机构ID：{}，订单号:{}", borrowNid, userId, orderId);
                            if (!isBatchRepay) {
                                return result;
                            }
                        }
                    } else {
                        logger.error("【代偿冻结异步回调】借款编号：{}，获取还款数据失败！担保机构ID：{}，订单号:{}", borrowNid, userId, orderId);
                        if (!isBatchRepay) {
                            return result;
                        }
                    }
                } catch (Exception e) {
                    logger.error("【代偿冻结异步回调】借款编号：{}，还款更新数据发生异常！", borrowNid, e);
                    if (!isBatchRepay) {
                        return result;
                    }
                }
                if (!isBatchRepay) {
                    break;
                }
            }
            logger.info("【代偿冻结异步回调】还款申请处理完成。担保机构ID：{}，订单号：{}", userId, orderId);
            if (isBatchRepay) {
                sendMessage(orgLogList.size(), orgLogList.size(), userId);
                RedisUtils.del(RedisConstants.CONCURRENCE_BATCH_ORGREPAY_USERID + userId);
            }
        } else {
            logger.error("【代偿冻结异步回调】担保机构还款未冻结！订单号：{}", orderId);
        }
        result.setStatus(true);
        return result;
    }
}
