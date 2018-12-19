package com.hyjf.cs.trade.controller.web.repay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
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
import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.TradeConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.ZIPGenerator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
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
import org.springframework.util.CollectionUtils;
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
    private static final String REQUSET_MAPPING = "/hyjf-web/repay";
    /** 用户ID */
    private static final String VAL_USERID = "userId";
    /**总还款数量*/
    private static final String VAL_ALLCOUNT = "allCount";
    /**成功数量*/
    private static final String VAL_SUCCESSCOUNT = "successCount";
    /**失败数量*/
    private static final String VAL_FAILCOUNT = "failCount";

    /** 批次代偿的同步回调 */
    public static final String REPAY_SYNC_RETURN_ACTION = "/getRepaySyncReturn";

    /** 批次代偿的异步回调 */
    public static final String REPAY_ASYNC_RETURN_ACTION = "/getRepayAsyncReturn";

    // 根据用户ID和模版号给某用户发短信
    public static final String SMSSENDFORUSER = "smsSendForUser";

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
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        if ("2".equals(userVO.getRoleId())) {
            AccountVO account = repayManageService.getAccountByUserId(userVO.getUserId());
            // 1.待还款总额
            BigDecimal repay = account.getBankWaitCapital().add(account.getBankWaitInterest());
            BigDecimal repayMangeFee = repayManageService.getUserRepayFeeWaitTotal(userVO.getUserId());
            repay = repay.add(repayMangeFee);
            resultMap.put("repayMoney",CustomConstants.DF_FOR_VIEW.format(repay));
        }
        // 根据RoleId 判断用户为垫付机构
        else if ("3".equals(userVO.getRoleId())) {
            // 1.待垫付总额
            BigDecimal repay = repayManageService.getOrgRepayWaitTotal(userVO.getUserId());
            BigDecimal repayMangeFee = repayManageService.getOrgRepayFeeWaitTotal(userVO.getUserId());
            repay = repay.add(repayMangeFee);
            resultMap.put("repayMoney",CustomConstants.DF_FOR_VIEW.format(repay));
        }

        resultMap.put("roleId", userVO.getRoleId());
        resultMap.put("userId", userVO.getUserId());
        resultMap.put("repayAuthOn","0");
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        resultMap.put("repayAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoRepayStatus());
        // 是否开启服务费授权 0未开启  1已开启
        resultMap.put("paymentAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoPaymentStatus());
        // 是否开启服务费授权 0未开启  1已开启
        resultMap.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        result.setData(resultMap);
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
     * 用户代还标的,债转详情.
     * @param userId
     * @param borrowNid
     * @return
     * @Author : huanghui
     */
    @ApiOperation(value = "用户待还标的-债转详情", notes = "用户待还标的-债转详情")
    @PostMapping(value = "/user_repay_detail")
    public WebResult<Map<String,Object>>  userRepayDetail(@RequestHeader(value = "userId") Integer userId, @RequestHeader(value = "borrowNid") String borrowNid){
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();

        // 根据用户ID 查询用户信息
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        /** 当前用户已登录并且标的NID不为空 */
        String verificationFlag = null;
        if (userVO != null && StringUtils.isNotBlank(borrowNid)){
            WebUserTransferBorrowInfoCustomizeVO borrowInfo = this.repayManageService.getUserTransferBorrowInfo(borrowNid);
            // 单纯的作为验证标识.

            if (borrowInfo.getPlanNid() != null){
                verificationFlag = borrowInfo.getPlanNid();
            }else {
                verificationFlag = null;
            }

            //居间协议
            Integer fddStatus = 0;
            List<TenderAgreementVO> tenderAgreementsNid =  this.repayManageService.selectTenderAgreementByNid(borrowNid);
            if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                TenderAgreementVO tenderAgreement = tenderAgreementsNid.get(0);
                fddStatus = tenderAgreement.getStatus();
                //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                if(fddStatus.equals(3)){
                    fddStatus = 1;
                }else {
                    //隐藏下载按钮
                    fddStatus = 0;
                }
            }else {
                //下载老版本协议
                fddStatus = 1;
            }

            // 计算到账金额
            if (borrowInfo.getSucSmount() != null){
                BigDecimal oldYesAccount = borrowInfo.getSucSmount();
                borrowInfo.setSucSmount(oldYesAccount.subtract(borrowInfo.getServiceFee()));
            }else {
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
    public  WebResult<List<WebUserRepayTransferCustomizeVO>> userRepayDetailAjax(@RequestBody WebUserRepayTransferRequest repayTransferRequest){
//        WebResult<WebUserRepayTransferCustomizeVO> result = new WebResult<>();
        WebResult<List<WebUserRepayTransferCustomizeVO>> result = new WebResult<List<WebUserRepayTransferCustomizeVO>>();
        result.setData(Collections.emptyList());

        List<WebUserRepayTransferCustomizeVO> repayTransferCustomizeVO = null;
        repayTransferCustomizeVO =  this.repayManageService.selectUserRepayTransferDetailList(repayTransferRequest);
        if (repayTransferCustomizeVO != null){
            result.setData(repayTransferCustomizeVO);
        }
        result.setStatus(BaseResult.SUCCESS);
        result.setStatusDesc(BaseResult.SUCCESS_DESC);
        return result;
    }

    /**
     * 垫付机构待还款列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "垫付机构待还款列表", notes = "垫付机构待还款列表")
    @PostMapping(value = "/wait_org_list", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectOrgRepayWaitList(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        logger.info("垫付机构待还款列表开始，userId:{}", userVO.getUserId());

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

            requestBean.setLimitStart(null);
            requestBean.setLimitEnd(null);
            List<RepayListCustomizeVO> resultListAll = repayManageService.selectOrgRepayList(requestBean);
            BigDecimal repayMoneyTotal = BigDecimal.ZERO;
            if(!CollectionUtils.isEmpty(resultListAll)){
                for (RepayListCustomizeVO customize : resultListAll) {
                    repayMoneyTotal=repayMoneyTotal.add(new BigDecimal(customize.getRealAccountYes()));
                }
                resultMap.put("repayMoneyTotal", repayMoneyTotal);
                resultMap.put("repayMoneyNum", resultListAll.size());
            }
            resultMap.put("recordList",resultList);
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("获取垫付机构待还款列表异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }

        result.setPage(page);
        return result;
    }

    /**
     * 垫付机构已还款列表
     * @param requestBean
     */
    @ApiOperation(value = "垫付机构已还款列表", notes = "垫付机构已还款列表")
    @PostMapping(value = "/repayed_org_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectOrgRepayedList(@RequestHeader(value = "userId") Integer userId, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        WebViewUserVO userVO = repayManageService.getUserFromCache(userId);
        logger.info("垫付机构待还款列表开始，userId:{}", userVO.getUserId());

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
            logger.error("获取垫付机构已还款列表异常", e);
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
            logger.info("请求参数borrowNid为空");
            result.setStatus(WebResult.FAIL);
            result.setStatusDesc("请求参数borrowNid为空");
            return result;
        }

        // 是否一次性还款
        boolean isAllRepay = false;
        detailRequestBean.setAllRepay(false);
        if(requestBean.getIsAllRepay() != null && "1".equals(requestBean.getIsAllRepay())) {
            isAllRepay = true;
            detailRequestBean.setAllRepay(true);
        }

        detailRequestBean.setBorrowNid(requestBean.getBorrowNid());
        if(userVO != null){
            detailRequestBean.setUserId(userVO.getUserId());
            detailRequestBean.setUserName(userVO.getUsername());
            detailRequestBean.setRoleId(userVO.getRoleId());
        }

        try {
            detaiResult = repayManageService.getRepayDetailData(detailRequestBean);
        } catch (Exception e) {
            logger.error("获取还款详情页面数据异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            result.setData(Collections.emptyMap());
            return result;
        }
        if(detaiResult!= null && "1".equals(detaiResult.getString("onlyAllRepay"))) {
            isAllRepay = true;
        }

        resultMap.put("isAllRepay", isAllRepay ? "1" : "0");
        resultMap.put("paymentAuthStatus", "");
        resultMap.put("repayAuthStatus", "");
        resultMap.put("repayAuthOn","0");
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        resultMap.put("repayAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoRepayStatus());
        // 是否开启服务费授权 0未开启  1已开启
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

        BorrowInfoVO borrowInfoVO = repayManageService.getBorrowInfoByNid(requestBean.getBorrowNid());
        if(borrowInfoVO == null){
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("标的不存在，borrowNid：" + requestBean.getBorrowNid());
            return webResult;
        }
        resultMap.put("borrowNid", borrowInfoVO.getBorrowNid());
        resultMap.put("borrowName", StringUtils.isBlank(borrowInfoVO.getName()) ? borrowInfoVO.getProjectName() : borrowInfoVO.getName());
        webResult.setData(resultMap);

        /** redis 锁 */
        boolean isRepay = RedisUtils.tranactionSet(RedisConstants.CONCURRENCE_REPAY_REQUEST + requestBean.getBorrowNid(), 60);
        if(!isRepay){
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("项目正在还款中...");
            return webResult;
        }

        String pAllRepay = requestBean.getIsAllRepay();
        boolean isAllRepay = false;
        if(pAllRepay != null && "1".equals(pAllRepay)) {
            isAllRepay = true;
        }
        String roleId = userVO.getRoleId();
        RepayBean repayBean = repayManageService.getRepayBean(userVO.getUserId(), userVO.getRoleId(), requestBean.getBorrowNid(), isAllRepay);
        if ("3".equals(roleId)) {// 垫付机构还款校验
            repayManageService.checkForRepayRequestOrg(requestBean.getBorrowNid(), requestBean.getPassword(),userVO, repayBean,0);
        } else { // 借款人还款校验
            repayManageService.checkForRepayRequest(requestBean.getBorrowNid(), requestBean.getPassword(),userVO, repayBean);
        }
        int errflag = repayBean.getFlag();
        if (1 == errflag) {
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc(repayBean.getMessage());
            return webResult;
        }
        String ip = GetCilentIP.getIpAddr(request);
        repayBean.setIp(ip);
        BigDecimal repayTotal = repayBean.getRepayAccountAll();
        String account = userVO.getBankAccount();
        // 用户还款
        try {
            if(StringUtils.isNotEmpty(roleId) && "3".equals(roleId)){// 垫付机构还款
                String txDate = GetOrderIdUtils.getTxDate();// 交易日期
                String txTime = GetOrderIdUtils.getTxTime();// 交易时间
                String seqNo = GetOrderIdUtils.getSeqNo(6);// 交易流水号
                String orderId = txDate + txTime + seqNo;// 交易日期+交易时间+交易流水号
                //add by cwyang 2017-07-25 还款去重
                boolean result = repayManageService.checkRepayInfo(null, requestBean.getBorrowNid());
                if (!result) {
                    webResult.setStatus(WebResult.ERROR);
                    webResult.setStatusDesc("项目正在还款中...");
                    return webResult;
                }
                //插入垫付机构冻结信息日志表 add by wgx 2018-09-11
                repayManageService.insertRepayOrgFreezeLof(userId, orderId, account, requestBean.getBorrowNid(), repayBean, userVO.getUsername(), isAllRepay);
                Map<String, Object> map = repayManageService.getBankRefinanceFreezePage(userId, userVO.getUsername(), ip, orderId, requestBean.getBorrowNid(), repayTotal, account);
                webResult.setData(map);
                return webResult;
            } else {
                String orderId = GetOrderIdUtils.getOrderId2(userVO.getUserId());
                //add by cwyang 2017-07-25 还款去重
                boolean result = repayManageService.checkRepayInfo(null, requestBean.getBorrowNid());
                if (!result) {
                    webResult.setStatus(WebResult.ERROR);
                    webResult.setStatusDesc("项目正在还款中...");
                    return webResult;
                }
                //插入冻结信息日志表 add by cwyang 2017-07-08
                repayManageService.addFreezeLog(userVO.getUserId(), orderId, account, requestBean.getBorrowNid(), repayTotal, userVO.getUsername());
                // 申请还款冻结资金
                // 调用江西银行还款申请冻结资金
                return repayManageService.getBalanceFreeze(userVO, requestBean.getBorrowNid(), repayBean, orderId, account, webResult, isAllRepay);
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
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("未登录");
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

        // 缴费授权
        //Users users = this.repayService.getUsers(Integer.parseInt(userId));
        //modelAndView.addObject("paymentAuthStatus", users.getPaymentAuthStatus());
        //update by jijun 2018/04/09 合规接口改造一期
        resultMap.put("paymentAuthStatus","");

        //还款授权
        //HjhUserAuth hjhUserAuth=repayService.getHjhUserAuthByUserId(Integer.parseInt(userId));
//        if(hjhUserAuth==null){
//            //modelAndView.addObject("repayAuthStatus", "0");
//            modelAndView.addObject("repayAuthStatus", "");
//        }else{
//            //update by jijun 2018/04/09 合规接口改造一期
//            modelAndView.addObject("repayAuthStatus", "");
//            //modelAndView.addObject("repayAuthStatus", hjhUserAuth.getAutoRepayStatus());
//        }

        resultMap.put("repayAuthStatus","");
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        // 是否开启服务费授权 0未开启  1已开启
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
            webResult.setStatus(WebResult.ERROR);
            webResult.setData("990");
            webResult.setStatusDesc("未登录");
            return webResult;
        }

        String msg = "";
        if (!userVO.isBankOpenAccount()) {
            msg = "998";
            logger.info("==============垫付机构:" + userVO.getUserId() + "批量还款失败,用户未开户!");
            webResult.setData(msg);
            webResult.setStatusDesc("用户未开户");
            return webResult;
        }
        boolean isBalance = comperToOrgUserBalance(userVO.getUserId(), userVO.getBankAccount(), new BigDecimal(requestBean.getRepayTotal()));
        if (!isBalance) {//余额不足
            msg = "997";
            logger.info("==============垫付机构:" + userVO.getUserId() + "批量还款失败,用户银行可用余额不足!");
            webResult.setData(msg);
            webResult.setStatusDesc("余额不足");
            return webResult;
        }
        boolean result = RedisUtils.exists(RedisConstants.CONCURRENCE_BATCH_ORGREPAY_USERID + userVO.getUserId());
        if(result){
            msg = "999";
            logger.info("==============垫付机构:" + userVO.getUserId() + "校验处->批量还款失败,项目正在还款中!");
            webResult.setData(msg);
            webResult.setStatusDesc("项目正在还款中");
            return webResult;
        }
        boolean isTime = companyRepayTime(requestBean.getStartDate(),requestBean.getEndDate(),userVO.getUserId());
        if(isTime){
            msg = "996";
            logger.info("==============垫付机构:" + userVO.getUserId() + "校验处->批量还款失败,还款区间大于28天!");
            webResult.setData(msg);
            webResult.setStatusDesc("还款区间大于28天!");
            return webResult;
        }
        // 服务费授权和还款授权校验
        boolean isPaymentAuth = this.authService.checkPaymentAuthStatus(userId);
        if (!isPaymentAuth) {
            msg = "996";
            logger.info("==============垫付机构:" + userVO.getUserId() + "校验处->批量还款失败,没缴费授权!");
            webResult.setData(msg);
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
                webResult.setStatus(WebResult.ERROR);
                webResult.setStatusDesc("批量还款失败,项目正在还款中!");
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
                logger.error("垫付机构还款申请完成，发送短信异常", e);
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
                    return  true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                logger.info("==================校验还款区间是否大于28天报错===================");
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

        logger.info("批次还款请求,收到报文后对合法性检查后的异步回调开始");
        BankCallResult result = new BankCallResult();
        bean.convert();
        String respCode = StringUtils.isBlank(bean.getRetCode()) ? null : bean.getRetCode();// 返回码
        if (StringUtils.isBlank(respCode)) {
            logger.info("还款校验回调，返回码为空！");
            return JSONObject.toJSONString(result, true);
        }
        String txDate = bean.getTxDate();
        String txTime = bean.getTxTime();
        String seqNo = bean.getSeqNo();
        String bankSeqNo = txDate + txTime + seqNo;
        BorrowApicronVO apicron = this.repayManageService.selectBorrowApicron(bankSeqNo);
        if (Validator.isNull(apicron)) {
            logger.info("还款校验回调，未查询到放款请求记录！银行唯一订单号：" + bankSeqNo);
            // 更新相应的放款请求校验失败
            return JSONObject.toJSONString(result, true);
        }
        // 当前批次放款状态
        int repayStatus = apicron.getStatus();
        if (repayStatus == CustomConstants.BANK_BATCH_STATUS_SENDED) {
            String borrowNid = apicron.getBorrowNid();
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                logger.info("批次还款请求,收到报文后,数据合法性异常");
                String retMsg = bean.getRetMsg();
                logger.error("还款校验回调失败！银行返回信息：" + retMsg);
                apicron.setData(retMsg);
                apicron.setFailTimes((apicron.getFailTimes() + 1));
                // 更新任务API状态为放款校验失败
                boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, TradeConstant.STATUS_VERIFY_FAIL);
                if (!apicronResultFlag) {
                    throw new Exception("更新还款任务为放款请求失败失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
                }
                // 更新相应的放款请求校验失败
                return JSONObject.toJSONString(result, true);
            }
            // 更新相应的放款请求校验成功
            boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, TradeConstant.STATUS_VERIFY_SUCCESS);
            if (!apicronResultFlag) {
                throw new Exception("更新还款任务为放款请求成功失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
            }
        }
        result.setStatus(true);
        logger.info("批次还款请求,收到报文后对合法性检查后的异步回调结束");
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

        logger.info("批次还款请求,业务处理结果的异步回调开始");
        BankCallResult result = new BankCallResult();
        bean.convert();
        String respCode = StringUtils.isBlank(bean.getRetCode()) ? null : bean.getRetCode();// 返回码
        if (StringUtils.isBlank(respCode)) {
            logger.info("还款结果回调，返回码为空！");
            return JSONObject.toJSONString(result, true);
        }
        String txDate = bean.getTxDate();
        String txTime = bean.getTxTime();
        String seqNo = bean.getSeqNo();
        String bankSeqNo = txDate + txTime + seqNo;
        BorrowApicronVO apicron = this.repayManageService.selectBorrowApicron(bankSeqNo);
        if (Validator.isNull(apicron)) {
            logger.info("还款结果回调，未查询到放款请求记录！银行唯一订单号：" + bankSeqNo);
            // 更新相应的放款请求校验失败
            return JSONObject.toJSONString(result, true);
        }
        // 当前批次放款状态
        int repayStatus = apicron.getStatus();
        String borrowNid = apicron.getBorrowNid();// 項目编号
        int borrowUserId = apicron.getUserId();// 放款用户
        if (repayStatus == CustomConstants.BANK_BATCH_STATUS_VERIFY_SUCCESS) {
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                String retMsg = bean.getRetMsg();
                logger.error("还款结果回调失败！银行返回信息：" + retMsg);
                apicron.setData(retMsg);
                apicron.setFailTimes((apicron.getFailTimes() + 1));
                // 更新任务API状态为放款校验失败
                boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, TradeConstant.STATUS_LOAN_FAIL);
                if (!apicronResultFlag) {
                    throw new Exception("更新还款任务为放款结果失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
                }
                // 更新相应的放款请求校验失败
                return JSONObject.toJSONString(result, true);
            } else {
                // update 2018/10/17   wgx  银行异步回调成功认为还款成功, 直接发送消息处理还款
                String sucCount = bean.getSucCounts();// 成功笔数
                Integer txCounts = apicron.getTxCounts();
                if (sucCount != null && txCounts != null && !sucCount.equals(txCounts.toString())) {
                    String failCounts = bean.getFailCounts();// 失败笔数
                    String failAmount = bean.getFailAmount();// 失败金额
                    if(StringUtils.isNotBlank(failCounts)) {
                        logger.error("【还款业务处理结果异步通知】银行唯一订单号：" + bankSeqNo + ",失败笔数:{}，失败金额:{}", failCounts, failAmount);
                    }
                }
                try {
                    // 还款任务
                    if (StringUtils.isBlank(apicron.getPlanNid())) {
                        // 直投类还款
                        commonProducer.messageSend(new MessageContent(MQConstant.BORROW_REPAY_ZT_RESULT_TOPIC,
                                apicron.getBorrowNid(), apicron));
                        logger.info("【还款业务处理结果异步通知】发送散标还款结果处理消息:还款项目编号:[{}]", apicron.getBorrowNid());
                    } else {
                        // 计划类还款
                        commonProducer.messageSend(new MessageContent(MQConstant.BORROW_REPAY_PLAN_RESULT_TOPIC,
                                apicron.getBorrowNid(), apicron));
                        logger.info("【还款业务处理结果异步通知】发送计划还款结果处理消息:还款项目编号:[{}],计划编号:{}",
                                apicron.getBorrowNid(), apicron.getPlanNid());
                    }
                } catch (MQException e) {
                    e.printStackTrace();
                    logger.info("【还款业务处理结果异步通知】发送还款结果处理消息失败:还款项目编号:[{}]{}",
                            apicron.getBorrowNid(), StringUtils.isNotBlank(apicron.getPlanNid()) ? ",计划编号:" + apicron.getPlanNid() : "");
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
						e.printStackTrace();
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
							e.printStackTrace();
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
            logger.info("【代偿冻结同步回调】垫付机构还款失败,错误码：{}", callBackBean.getRetCode());
            return modelAndView;
        }
        logger.info("【代偿冻结同步回调】垫付机构还款申请成功,订单号:{}", orderId);
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
            logger.info("【代偿冻结异步回调】获取冻结订单号失败！");
            return result;
        }
        List<BankRepayOrgFreezeLogVO> orgLogList = repayManageService.getBankRepayOrgFreezeLogList(null, orderId);
        if (orgLogList == null || orgLogList.size() == 0) {
            logger.info("【代偿冻结异步回调】还款申请资金已解冻,订单号：{}", orderId);
            return result;
        }
        logger.info("【代偿冻结异步回调】{}还款冻结开始处理，订单号：{}", isBatchRepay ? "批量" : "", orderId);
        String accountId = callBackBean.getAccountId();//电子账号
        Integer userId = orgLogList.get(0).getRepayUserId();
        String userName = orgLogList.get(0).getRepayUserName();
        // 垫付机构还款冻结状态查询
        boolean hasFreeze = repayManageService.queryBalanceFreeze(Integer.valueOf(userId), userName, orderId, accountId);
        if (hasFreeze) {
            for (BankRepayOrgFreezeLogVO orgFreezeLog : orgLogList) {
                String borrowNid = orgFreezeLog.getBorrowNid();
                boolean isAllRepay = orgFreezeLog.getAllRepayFlag() == 1;
                try {
                    // 垫付机构的还款
                    RepayBean repay = repayManageService.getRepayBean(userId, "3", borrowNid, isAllRepay);
                    if (repay != null) {
                        // 还款后变更数据
                        callBackBean.setOrderId(orderId);
                        //还款后变更数据
                        boolean updateResult = this.repayManageService.updateForRepayRequest(repay, callBackBean, isAllRepay);
                        if (updateResult) {
                            repayManageService.deleteOrgFreezeTempLogs(orderId, borrowNid);
                            // 如果有正在出让的债权,先去把出让状态停止
                            this.repayManageService.updateBorrowCreditStautus(borrowNid);

                            logger.info("【代偿冻结异步回调】垫付机构:" + userId + "还款申请成功,标的号:{},订单号:{}", borrowNid, orderId);
                        } else {
                            logger.error("【代偿冻结异步回调】垫付机构:" + userId + "还款更新数据失败,标的号:{},订单号:{}", borrowNid, orderId);
                            if (!isBatchRepay) {
                                return result;
                            }
                        }
                    } else {
                        logger.info("【代偿冻结异步回调】获取垫付机构:" + userId + "还款信息失败,标的号:{},订单号:{}", borrowNid, orderId);
                        if (!isBatchRepay) {
                            return result;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("【代偿冻结异步回调】垫付机构:" + userId + "还款调用银行接口异常,标的号:{},订单号:{}", borrowNid, orderId);
                    if (!isBatchRepay) {
                        return result;
                    }
                }
                if (!isBatchRepay) {
                    break;
                }
            }
            logger.info("【代偿冻结异步回调】垫付机构:{}还款申请处理完成,订单号:{}", userId, orderId);
            if (isBatchRepay) {
                sendMessage(orgLogList.size(), orgLogList.size(), userId);
                //RedisUtils.del("batchOrgRepayUserid_" + userId);
            }
        } else {
            logger.info("【代偿冻结异步回调】垫付机构还款未冻结,订单号:{}", orderId);
        }
        result.setStatus(true);
        return result;
    }
}
