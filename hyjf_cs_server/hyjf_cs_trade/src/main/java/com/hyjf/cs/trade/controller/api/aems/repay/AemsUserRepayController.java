package com.hyjf.cs.trade.controller.api.aems.repay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.AemsErrorCodeConstant;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.AemsRepayRequestBean;
import com.hyjf.cs.trade.bean.AemsRepayResultBean;
import com.hyjf.cs.trade.bean.ResultApiBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.repay.AemsUserRepayService;
import com.hyjf.cs.trade.service.repay.RepayManageService;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * aems用户还款AemsUserRepayController
 */
@RestController
@Api(value = "aems还款接口", tags ="aems还款接口")
@RequestMapping("/aems/repay")
public class AemsUserRepayController extends BaseTradeController {
    
    @Autowired
    private AemsUserRepayService aemsUserRepayService;

    @Autowired
    private RepayManageService repayManageService;

    @ApiOperation(value = "还款申请", notes = "还款申请")
    @PostMapping("/repay")
    public AemsRepayResultBean userRepay(@RequestBody AemsRepayRequestBean requestBean, HttpServletRequest request) {
        AemsRepayResultBean resultBean = new AemsRepayResultBean();
        resultBean.setProductId(requestBean.getProductId());
        resultBean.setAccountId(requestBean.getAccountId());
        resultBean.setRepayPeriods(requestBean.getRepayPeriods());
        JSONObject info = new JSONObject();
        if (Validator.isNull(requestBean.getAccountId()) || Validator.isNull(requestBean.getRepaySouce()) || Validator.isNull(requestBean.getInstCode())
                || Validator.isNull(requestBean.getProductId()) || Validator.isNull(requestBean.getRepayPeriods()) || Validator.isNull(requestBean.getPlatform())) {
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000011);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000011);
            resultBean.setStatusDesc("还款接口请求参数缺失");
            logger.info("-------------------缺少参数！--------------------");
            return resultBean;
        }

        //验签
        if(!SignUtil.AEMSVerifyRequestSign(requestBean, "/aems/repay/repay")){
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000012);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000012);
            resultBean.setStatusDesc("验签失败！");
            logger.info("-------------------验签失败！--------------------");
            return resultBean;
        }

        //校验是否是从当前期开始还款 todo 逾期还款二期
        List<String> periodsList = Arrays.asList("isAllRepay", "currentPeriod");
        if (!periodsList.contains(requestBean.getRepayPeriods())) {
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000013);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000013);
            resultBean.setStatusDesc("还款期数类型有误！");
            logger.info("-------------------还款期数传入有误！--------------------");
            return resultBean;
        }

        UserVO user = aemsUserRepayService.getUserByAccountId(resultBean.getAccountId());
        // 获取用户信息
        if (user == null) {
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000007);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000007);
            resultBean.setStatusDesc("用户信息不存在");
            return resultBean;
        }
        Integer userId = user.getUserId();
        String roleId = null;
        String repaySouce = requestBean.getRepaySouce();
        if("1".equals(repaySouce)) {
            // 借款人
            roleId = "2";
        } else {
            logger.error("还款来源有误......");
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000010);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000010);
            resultBean.setStatusDesc("还款来源有误！");
            return resultBean;
        }
        String userName = user.getUsername();
        String borrowNid = requestBean.getProductId();
        // 对分期标的，是否一次全部还款
        boolean isAllRepay = false;
        if(Objects.equals(requestBean.getRepayPeriods(), "isAllRepay")) {
            isAllRepay = true;
        }

        // 获取用户在银行的客户号
        BankOpenAccountVO userBankOpenAccount = this.aemsUserRepayService.getBankOpenAccount(userId);
        if (userBankOpenAccount == null || StringUtils.isEmpty(userBankOpenAccount.getAccount())) {
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000008);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000008);
            resultBean.setStatusDesc("用户在银行未开户");
            return resultBean;
        }
        BorrowVO borrow = this.aemsUserRepayService.searchRepayProject(userId, roleId, borrowNid);
        /** redis 锁 */
        boolean isRepay = RedisUtils.tranactionSet("repay_borrow_nid" + borrowNid, 60);
        if(!isRepay){
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000009);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000009);
            resultBean.setStatusDesc("项目正在还款中");
            return resultBean;
        }
        // 校验用户/垫付机构的还款
        RepayBean repay = null;
        if (StringUtils.isNotEmpty(roleId) && "2".equals(roleId)) {// 借款人还款校验
            repay = aemsUserRepayService.getRepayBean(userId, roleId, borrow.getBorrowNid(), isAllRepay);
           // 借款人还款校验
            aemsUserRepayService.checkForRepayRequest(requestBean.getProductId(), user, userBankOpenAccount, repay);
            
            int errflag = repay.getFlag();
            if (1 == errflag) {
                resultBean.setStatus(ResultApiBean.ERROR);
                resultBean.setStatusDesc(repay.getMessage());
                return resultBean;
            }
            String ip = GetCilentIP.getIpAddr(request);
            repay.setIp(ip);
            BigDecimal repayTotal = repay.getRepayAccountAll();
            String account = userBankOpenAccount.getAccount();
            // 用户还款
            try {
                if(StringUtils.isNotEmpty(roleId) && "3".equals(roleId)){// 垫付机构还款
                    String txDate = GetOrderIdUtils.getTxDate();// 交易日期
                    String txTime = GetOrderIdUtils.getTxTime();// 交易时间
                    String seqNo = GetOrderIdUtils.getSeqNo(6);// 交易流水号
                    String orderId = txDate + txTime + seqNo;// 交易日期+交易时间+交易流水号
                    //add by cwyang 2017-07-25 还款去重
                    boolean result = repayManageService.checkRepayInfo(null, requestBean.getProductId());
                    if (!result) {
                        resultBean.setStatus(ResultApiBean.ERROR);
                        resultBean.setStatusDesc("项目正在还款中...");
                        return resultBean;
                    }
                    //插入垫付机构冻结信息日志表 add by wgx 2018-09-11
                    repayManageService.insertRepayOrgFreezeLof(userId, orderId, account, requestBean.getProductId(), repay, user.getUsername(), isAllRepay);
                    Map<String, Object> map = repayManageService.getBankRefinanceFreezePage(userId, user.getUsername(), ip, orderId, requestBean.getProductId(), repayTotal, account);
                    resultBean.setData(map);
                    return resultBean;
                } else {
                    String orderId = GetOrderIdUtils.getOrderId2(user.getUserId());
                    //add by cwyang 2017-07-25 还款去重
                    boolean result = repayManageService.checkRepayInfo(null, requestBean.getProductId());
                    if (!result) {
                        resultBean.setStatus(ResultApiBean.ERROR);
                        resultBean.setStatusDesc("项目正在还款中...");
                        return resultBean;
                    }
                    //插入冻结信息日志表 add by cwyang 2017-07-08
                    repayManageService.addFreezeLog(user.getUserId(), orderId, account, requestBean.getProductId(), repayTotal, user.getUsername());
                    // 申请还款冻结资金
                    // 调用江西银行还款申请冻结资金
                    return aemsUserRepayService.getBalanceFreeze(user, requestBean.getProductId(), repay, orderId, account, resultBean, isAllRepay);
                }
            } catch (Exception e) {
                logger.error("还款申请冻结资金异常", e);
                resultBean.setStatus(ResultApiBean.ERROR);
                resultBean.setStatusDesc("还款申请冻结资金异常");
                return resultBean;
            }
        } else {
            logger.error("还款来源有误......");
            resultBean.setStatus(AemsErrorCodeConstant.STATUS_HK000010);
            resultBean.setStatusForResponse(AemsErrorCodeConstant.STATUS_HK000010);
            resultBean.setStatusDesc("还款来源有误！");
            return resultBean;
        }
    }
}
