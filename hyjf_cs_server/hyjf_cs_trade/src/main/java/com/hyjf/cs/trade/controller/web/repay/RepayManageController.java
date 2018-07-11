package com.hyjf.cs.trade.controller.web.repay;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequest;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.RepayManageService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款管理相关页面接口
 * @author hesy
 * @version RepayManageController, v0.1 2018/6/23 14:09
 */
@Api(value = "Web端还款管理相关页面接口")
@RestController
@RequestMapping("/web/repay")
public class RepayManageController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(RepayManageController.class);

    @Autowired
    RepayManageService repayManageService;

    /**
     * 用户待还款列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "用户待还款列表", notes = "用户待还款列表")
    @PostMapping(value = "/repay_wait_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectRepayWaitList(@RequestHeader(value = "token", required = true) String token, RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
        logger.info("用户待还款列表开始，userId:{}", userVO.getUserId());

        // 请求参数校验
        requestBean.setStatus("0");
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
    public WebResult<List<RepayListCustomizeVO>> selectRepayedList(@RequestHeader(value = "token", required = true) String token, RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
        logger.info("用户已还款列表开始，userId:{}", userVO.getUserId());

        // 请求参数校验
        requestBean.setStatus("1");
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
    public WebResult<List<RepayListCustomizeVO>> selectOrgRepayWaitList(@RequestHeader(value = "token", required = true) String token, RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
        logger.info("用户待还款列表开始，userId:{}", userVO.getUserId());

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
     * 垫付机构已还款列表
     * @param token
     * @param requestBean
     * @param request
     * @return
     */
    @ApiOperation(value = "垫付机构已还款列表", notes = "垫付机构已还款列表")
    @PostMapping(value = "/repayed_org_list", produces = "application/json; charset=utf-8")
    public WebResult<List<RepayListCustomizeVO>> selectOrgRepayedList(@RequestHeader(value = "token", required = true) String token, RepayListRequest requestBean, HttpServletRequest request){
        WebResult<List<RepayListCustomizeVO>> result = new WebResult<List<RepayListCustomizeVO>>();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);
        logger.info("用户待还款列表开始，userId:{}", userVO.getUserId());

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
    public WebResult repayDetail(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request){
        WebResult result = new WebResult();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        ProjectBean projectBean = new ProjectBean();
        WebViewUserVO userVO = repayManageService.getUsersByToken(token);

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
    public WebResult repayRequest(@RequestHeader(value = "token", required = true) String token, RepayRequest requestBean, HttpServletRequest request){
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
}
