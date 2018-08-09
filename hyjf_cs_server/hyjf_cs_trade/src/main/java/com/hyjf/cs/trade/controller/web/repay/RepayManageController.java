package com.hyjf.cs.trade.controller.web.repay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.TradeConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.repay.RepayManageService;
import com.hyjf.cs.trade.vo.RepayDetailRequestVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款管理相关页面接口
 * @author hesy
 * @version RepayManageController, v0.1 2018/6/23 14:09
 */
@Api(value = "web端-还款管理相关页面接口", tags ="web端-还款管理相关页面接口")
@RestController
@RequestMapping("/hyjf-web/repay")
public class RepayManageController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(RepayManageController.class);

    @Autowired
    RepayManageService repayManageService;

    /**
     * 用户还款页面统计数据查询
     * @param token
     */
    @ApiOperation(value = "用户还款页面统计数据", notes = "用户还款页面统计数据查询")
    @PostMapping(value = "/repay_page_data", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectRepayPageData(@RequestHeader(value = "token", required = true) String token){
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
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

        result.setData(resultMap);
        return result;
    }

    @ApiOperation(value = "平台登录密码校验", notes = "平台登录密码校验")
    @ApiImplicitParam(name = "paraMap", value = "{password:string}", dataType = "Map")
    @PostMapping(value = "/pwd_check", produces = "application/json; charset=utf-8")
    public WebResult<String> pwdCheck(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String,String> paraMap){
        WebResult<String> result = new WebResult<>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
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
    public WebResult<List<RepayListCustomizeVO>> selectRepayWaitList(@RequestHeader(value = "token", required = true) String token, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
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
     * @param token
     * @param requestBean
     * @param request
     * @return
     */
    @ApiOperation(value = "用户已还款列表", notes = "用户已还款列表")
    @PostMapping(value = "/repayed_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectRepayedList(@RequestHeader(value = "token", required = true) String token, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
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
     * 垫付机构待还款列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "垫付机构待还款列表", notes = "垫付机构待还款列表")
    @PostMapping(value = "/wait_org_list", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectOrgRepayWaitList(@RequestHeader(value = "token", required = true) String token, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
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
     * @param token
     * @param requestBean
     * @param request
     * @return
     */
    @ApiOperation(value = "垫付机构已还款列表", notes = "垫付机构已还款列表")
    @PostMapping(value = "/repayed_org_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectOrgRepayedList(@RequestHeader(value = "token", required = true) String token, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
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
     * 获取用户本期还款金额
     * @param token
     * @param requestBean
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户本期还款金额", notes = "获取用户本期还款金额")
    @PostMapping(value = "/repay_money_total", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> getRepayMoneyTotal(@RequestHeader(value = "token", required = true) String token, @RequestBody RepayListRequest requestBean, HttpServletRequest request){
        WebResult<Map<String,Object>> result = new WebResult<>();
        Map<String,Object> resultMap = new HashMap<>();
        BigDecimal repayMoneyTotal=new BigDecimal(0);
        int repayMoneyNum=0;
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
        logger.info("获取用户本期还款金额开始，userId:{}", userVO.getUserId());
        if(userVO != null){
            requestBean.setUserId(userVO.getUserId().toString());
            requestBean.setRoleId(userVO.getRoleId());
            requestBean.setRepayStatus("0");
            requestBean.setBorrowNid(requestBean.getBorrowNid());
            requestBean.setEndDate(requestBean.getEndDate());
            requestBean.setStartDate(requestBean.getStartDate());
            List<RepayListCustomizeVO> resultList = repayManageService.selectUserRepayedList(requestBean);// 查询记录（个人和机构）
            if(!CollectionUtils.isEmpty(resultList)){
                repayMoneyNum=resultList.size();
                for (RepayListCustomizeVO customize : resultList) {
                    repayMoneyTotal=repayMoneyTotal.add(new BigDecimal(customize.getRealAccountYes()));
                }
            }
        }
        resultMap.put("repayMoneyTotal",repayMoneyTotal);
        resultMap.put("repayMoneyNum",repayMoneyNum);
        result.setData(resultMap);
        return result;
    }
    /**
     * 还款详情页面数据
     * @auther: hesy
     * @date: 2018/7/9
     */
    @ApiOperation(value = "还款详情页面数据", notes = "还款详情页面数据")
    @PostMapping(value = "/repay_detail", produces = "application/json; charset=utf-8")
    public WebResult repayDetail(@RequestHeader(value = "token", required = true) String token, @RequestBody RepayDetailRequestVO requestBean, HttpServletRequest request){
        WebResult result = new WebResult();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        ProjectBean projectBean = new ProjectBean();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
        if(StringUtils.isBlank(requestBean.getBorrowNid())){
            logger.info("请求参数borrowNid为空");
            result.setStatus(WebResult.FAIL);
            result.setStatusDesc("请求参数borrowNid为空");
            return result;
        }
        projectBean.setBorrowNid(requestBean.getBorrowNid());
        if(userVO != null){
            projectBean.setUserId(userVO.getUserId().toString());
            projectBean.setUsername(userVO.getUsername());
            projectBean.setRoleId(userVO.getRoleId());
        }

        try {
            projectBean = repayManageService.searchRepayProjectDetail(projectBean);
        } catch (Exception e) {
            logger.error("获取还款详情页面数据异常", e);
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
        }
        resultMap.put("paymentAuthStatus", "");
        resultMap.put("repayAuthStatus", "");
        resultMap.put("repayProject", projectBean);
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
    public WebResult repayRequest(@RequestHeader(value = "token", required = true) String token, @RequestBody RepayRequest requestBean, HttpServletRequest request){
        WebResult webResult = new WebResult();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);

        /** redis 锁 */
        boolean reslut = RedisUtils.tranactionSet("repay_borrow_nid" + requestBean.getBorrowNid(), 60);
        if(!reslut){
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc("项目正在还款中...");
            return webResult;
        }

        RepayBean repayBean = repayManageService.checkForRepayRequest(requestBean,userVO,0);
        int errflag = repayBean.getFlag();
        if (1 == errflag) {
            webResult.setStatus(WebResult.ERROR);
            webResult.setStatusDesc(repayBean.getMessage());
            return webResult;
        }
        String ip = GetCilentIP.getIpAddr(request);
        repayBean.setIp(ip);
        BigDecimal repayTotal = repayBean.getRepayAccountAll();

        // 用户还款
        try {
            String orderId = GetOrderIdUtils.getOrderId2(userVO.getUserId());
            String account = userVO.getBankAccount();
            //add by cwyang 2017-07-25 还款去重
            boolean result = repayManageService.checkRepayInfo(userVO.getUserId(), requestBean.getBorrowNid());
            if (!result) {
                webResult.setStatus(WebResult.ERROR);
                webResult.setStatusDesc("项目正在还款中...");
                return webResult;
            }
            //插入冻结信息日志表 add by cwyang 2017-07-08
            repayManageService.addFreezeLog(userVO.getUserId(),orderId,account,requestBean.getBorrowNid(),repayTotal,userVO.getUsername());
            // 申请还款冻结资金
            // 调用江西银行还款申请冻结资金
            BankCallBean bean = new BankCallBean();
            bean.setAccountId(account);// 电子账号
            bean.setOrderId(orderId); // 订单号
            bean.setTxAmount(String.valueOf(repayTotal));// 交易金额
            bean.setProductId(requestBean.getBorrowNid());
            bean.setFrzType("0");
            bean.setLogOrderId(orderId);// 订单号
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogUserId(String.valueOf(userVO.getUserId()));
            bean.setLogUserName(userVO.getUsername());
            bean.setLogClient(0);
            bean.setLogIp(ip);
            bean.setProductId(requestBean.getBorrowNid());
            BankCallBean callBackBean = BankCallUtils.callApiBg(bean);
            String respCode = callBackBean == null ? "" : callBackBean.getRetCode();
            // 申请冻结资金失败
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                if (!"".equals(respCode)) {
                    this.repayManageService.deleteFreezeLogByOrderId(orderId);
                }
                logger.info("调用还款申请冻结资金接口失败:" + callBackBean.getRetMsg() + "订单号:" + callBackBean.getOrderId());
                webResult.setStatus(WebResult.ERROR);
                webResult.setStatusDesc("还款失败，请稍后再试...");
                return webResult;
            }
            //还款后变更数据
            this.repayManageService.updateForRepayRequest(repayBean, callBackBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webResult;
    }

    /**
     * 收到报文后对合法性检查后的异步回调
     * @auther: hesy
     * @date: 2018/7/17
     */
    @ApiOperation(value = "收到报文后对合法性检查后的异步回调", notes = "收到报文后对合法性检查后的异步回调")
    @PostMapping("/repayVerifyReturn")
    public String repayVerifyReturnAction(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCallBean bean) throws Exception {

        logger.info("批次还款请求,收到报文后对合法性检查后的异步回调开始");
        BankCallResult result = new BankCallResult();
        bean.convert();
        String respCode = StringUtils.isBlank(bean.getRetCode()) ? null : bean.getRetCode();// 返回码
        if (StringUtils.isBlank(respCode)) {
            logger.info("放款校验回调，返回码为空！");
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
                logger.info("放款校验回调失败！银行返回信息：" + retMsg);
                apicron.setData(retMsg);
                apicron.setFailTimes((byte)(apicron.getFailTimes() + 1));
                // 更新任务API状态为放款校验失败
                boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, TradeConstant.STATUS_VERIFY_FAIL);
                if (!apicronResultFlag) {
                    throw new Exception("更新放款任务为放款请求失败失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
                }
                // 更新相应的放款请求校验失败
                return JSONObject.toJSONString(result, true);
            }
            // 更新相应的放款请求校验成功
            boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, TradeConstant.STATUS_VERIFY_SUCCESS);
            if (!apicronResultFlag) {
                throw new Exception("更新放款任务为放款请求成功失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
            }
        }
        result.setStatus(true);
        logger.info("批次还款请求,收到报文后对合法性检查后的异步回调结束");
        return JSONObject.toJSONString(result, true);

    }

    /**
     * 业务处理结果的异步回调
     *
     * @param request
     * @param response
     * @param bean
     * @throws Exception
     */
    @ApiOperation(value = "业务处理结果的异步回调", notes = "业务处理结果的异步回调")
    @PostMapping("/repayResultReturn")
    public String repayResultReturn(HttpServletRequest request, HttpServletResponse response, @RequestBody BankCallBean bean) throws Exception {

        logger.info("批次还款请求,业务处理结果的异步回调开始");
        BankCallResult result = new BankCallResult();
        bean.convert();
        String respCode = StringUtils.isBlank(bean.getRetCode()) ? null : bean.getRetCode();// 返回码
        if (StringUtils.isBlank(respCode)) {
            logger.info("放款结果回调，返回码为空！");
            return JSONObject.toJSONString(result, true);
        }
        String txDate = bean.getTxDate();
        String txTime = bean.getTxTime();
        String seqNo = bean.getSeqNo();
        String bankSeqNo = txDate + txTime + seqNo;
        BorrowApicronVO apicron = this.repayManageService.selectBorrowApicron(bankSeqNo);
        if (Validator.isNull(apicron)) {
            logger.info("放款结果回调，未查询到放款请求记录！银行唯一订单号：" + bankSeqNo);
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
                logger.info("放款结果回调失败！银行返回信息：" + retMsg);
                apicron.setData(retMsg);
                apicron.setFailTimes((byte)(apicron.getFailTimes() + 1));
                // 更新任务API状态为放款校验失败
                boolean apicronResultFlag = repayManageService.updateBorrowApicron(apicron, TradeConstant.STATUS_LOAN_FAIL);
                if (!apicronResultFlag) {
                    throw new Exception("更新放款任务为放款结果失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
                }
                // 更新相应的放款请求校验失败
                return JSONObject.toJSONString(result, true);
            } else {
                // 查询批次放款状态
                BankCallBean batchResult = this.repayManageService.batchQuery(apicron);
                if (Validator.isNotNull(batchResult)) {
                    // 批次放款返回码
                    String retCode = StringUtils.isNotBlank(batchResult.getRetCode()) ? batchResult.getRetCode() : "";
                    if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                        // 批次放款状态
                        String batchState = batchResult.getBatchState();
                        if (StringUtils.isNotBlank(batchState)) {
                            // 如果是批次处理失败
                            if (batchState.equals(BankCallConstant.BATCHSTATE_TYPE_FAIL)) {
                                String failMsg = batchResult.getFailMsg();// 失败原因
                                if (StringUtils.isNotBlank(failMsg)) {
                                    apicron.setData(failMsg);
                                    apicron.setFailTimes((byte)(apicron.getFailTimes() + 1));
                                    // 更新任务API状态
                                    boolean apicronResultFlag = this.repayManageService.updateBorrowApicron(apicron, CustomConstants.BANK_BATCH_STATUS_FAIL);
                                    if (apicronResultFlag) {
                                        result.setStatus(true);
                                        return JSONObject.toJSONString(result, true);
                                    } else {
                                        throw new Exception("更新状态为（放款请求失败）失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
                                    }
                                }
//								else {
//									// 查询批次交易明细，进行后续操作
//									boolean batchDetailFlag = this.repayManageService.batchDetailsQuery(apicron);
//									// 进行后续失败的放款的放款请求
//									if (batchDetailFlag) {
//										result.setStatus(true);
//										return JSONObject.toJSONString(result, true);
//									} else {
//										throw new Exception("放款失败后，查询放款明细失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
//									}
//								}
                            }
//							// 如果是批次处理成功
//							else if (batchState.equals(BankCallConstant.BATCHSTATE_TYPE_SUCCESS)) {
//								// 查询批次交易明细，进行后续操作
//								boolean batchDetailFlag = this.repayManageService.batchDetailsQuery(apicron);
//								if (batchDetailFlag) {
//									result.setStatus(true);
//									return JSONObject.toJSONString(result, true);
//								} else {
//									throw new Exception("放款成功后，查询放款明细失败。[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
//								}
//							} else {
//								result.setStatus(true);
//								return JSONObject.toJSONString(result, true);
//							}
                        } else {
                            throw new Exception("放款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
                        }
                    } else {
                        String retMsg = batchResult.getRetMsg();
                        throw new Exception("放款状态查询失败！银行返回信息：" + retMsg + ",[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
                    }
                } else {
                    throw new Exception("放款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]," + "[借款编号：" + borrowNid + "]");
                }
            }
        } else {
            result.setStatus(true);
            return JSONObject.toJSONString(result, true);
        }
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }
}
