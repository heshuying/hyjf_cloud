/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.task;

import com.hyjf.am.response.trade.BorrowTenderCpnResponse;
import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.response.trade.CouponTenderCustomizeResponse;
import com.hyjf.am.resquest.trade.RepayDataRecoverRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.CouponTenderCustomize;
import com.hyjf.am.trade.mq.AccountWebListProducer;
import com.hyjf.am.trade.service.AccountListService;
import com.hyjf.am.trade.service.BorrowTenderCpnService;
import com.hyjf.am.trade.service.CouponRecoverService;
import com.hyjf.am.trade.service.task.DataRecoverService;
import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountWebListVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yaoy
 * @version DataRecoverController, v0.1 2018/6/25 14:06
 */
@RestController
@RequestMapping("am-trade/batch")
public class DataRecoverController {

    @Autowired
    private DataRecoverService dataRecoverService;
    @Autowired
    private AccountListService accountListService;
    @Autowired
    private BorrowTenderCpnService borrowTenderCpnService;
    @Autowired
    private CouponRecoverService couponRecoverService;

    @GetMapping("/selectCouponRecover/{borrowNid}/{repayTimeConfig}")
    public CouponTenderCustomizeResponse selectCouponRecover(@PathVariable String borrowNid,@PathVariable int repayTimeConfig) {
        CouponTenderCustomizeResponse response = new CouponTenderCustomizeResponse();
        List<CouponTenderCustomize> couponTenderCustomizeList = dataRecoverService.selectCouponRecover(borrowNid,repayTimeConfig);
        if (!CollectionUtils.isEmpty(couponTenderCustomizeList)) {
            List<CouponTenderCustomizeVO> couponTenderCustomizeVOList = CommonUtils.convertBeanList(couponTenderCustomizeList,CouponTenderCustomizeVO.class);
            response.setResultList(couponTenderCustomizeVOList);
        }
        return response;
    }

    @GetMapping("/getCurrentCouponRecover/{couponTenderNid}/{periodNow}")
    public CouponRecoverCustomizeResponse selectCurrentCouponRecover(@PathVariable String couponTenderNid,@PathVariable int periodNow) {
        CouponRecoverCustomizeResponse response = new CouponRecoverCustomizeResponse();
        CouponRecoverCustomize couponRecoverCustomize = dataRecoverService.selectCurrentCouponRecover(couponTenderNid,periodNow);
        if (couponRecoverCustomize != null) {
            CouponRecoverCustomizeVO couponRecoverCustomizeVO = new CouponRecoverCustomizeVO();
            BeanUtils.copyProperties(couponRecoverCustomize,couponRecoverCustomizeVO);
            response.setResult(couponRecoverCustomizeVO);
        }
        return response;
    }

    @RequestMapping("/repayDataRecover")
    public void repayDataRecover(@RequestBody @Valid RepayDataRecoverRequest request) {
        CouponRecoverCustomizeVO currentRecover = request.getCouponRecoverCustomizeVO();
        BankOpenAccountVO bankOpenAccount = request.getBankOpenAccountVO();
        int count = request.getCount();
        Integer tenderUserId = Integer.valueOf(request.getUserId());
        String ip = request.getIp();
        String couponUserCode = request.getCouponUserCode();
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        // 应还利息
        String recoverInterestStr = StringUtils.isEmpty(currentRecover.getRecoverInterest()) ? "0.00" : currentRecover.getRecoverInterest();
        // 应还本息
        String recoverAccountStr = StringUtils.isEmpty(currentRecover.getRecoverAccount()) ? "0.00" : currentRecover.getRecoverAccount();
        // 应还本金
        String recoverCapitalStr = StringUtils.isEmpty(currentRecover.getRecoverCapital()) ? "0.00" : currentRecover.getRecoverCapital();
        BigDecimal recoverInterest = new BigDecimal(recoverInterestStr);
        BigDecimal recoverAccount = new BigDecimal(recoverAccountStr);
        BigDecimal recoverCapital = new BigDecimal(recoverCapitalStr);
        CouponRecover cr = new CouponRecover();
        cr.setId(currentRecover.getId());
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
        if (count == 0) {
            // 更新账户信息(投资人)
            Account account = new Account();
            account.setUserId(tenderUserId);

            account.setBankBalance(recoverAccount); // 账户余额
            account.setBankTotal(BigDecimal.ZERO);// 投资人资金总额 +利息
            account.setBankFrost(BigDecimal.ZERO);// 投资人冻结金额+投资金额(等额本金时投资金额可能会大于计算出的本金之和)
            account.setBankAwait(recoverAccount);// 投资人待收金额+利息+
            // 本金
            account.setBankAwaitCapital(BigDecimal.ZERO);// 投资人待收本金
            account.setBankAwaitInterest(recoverAccount);// 投资人待收利息
            account.setBankInterestSum(recoverAccount);
            account.setBankInvestSum(BigDecimal.ZERO);// 投资人累计投资
            account.setBankFrostCash(BigDecimal.ZERO);// 江西银行冻结金额

            int accountCnt = dataRecoverService.updateOfRepayTender(account);
            if (accountCnt == 0) {
                throw new RuntimeException("投资人资金记录(ht_account)更新失败！");
            }
            // 写入收支明细
            AccountList accountList = new AccountList();
            // 转账订单编号
            accountList.setNid(orderId);
            // 投资人
            accountList.setUserId(tenderUserId);
            accountList.setBankAwait(account.getBankAwait());
            accountList.setBankAwaitCapital(account.getBankAwaitCapital());
            accountList.setBankAwaitInterest(account.getBankAwaitInterest());
            accountList.setBankBalance(account.getBankBalance());
            accountList.setBankFrost(account.getBankFrost());
            accountList.setBankInterestSum(account.getBankInterestSum());
            accountList.setBankTotal(account.getBankTotal());
            accountList.setBankWaitCapital(account.getBankWaitCapital());
            accountList.setBankWaitInterest(account.getBankWaitInterest());
            accountList.setBankWaitRepay(account.getBankWaitRepay());
            accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
            accountList.setPlanFrost(account.getPlanFrost());
            accountList.setAccountId(bankOpenAccount.getAccount());
            accountList.setTxDate(Integer.parseInt(GetOrderIdUtils.getTxDate()));
            accountList.setTxTime(Integer.parseInt(GetOrderIdUtils.getTxTime()));
            accountList.setSeqNo(seqNo);
            accountList.setBankSeqNo(seqNo);
            accountList.setCheckStatus(0);
            accountList.setTradeStatus(1);
            accountList.setIsBank(1);

            // 投资收入
            accountList.setAmount(recoverAccount);
            // 1收入
            accountList.setType(1);
            String trade = StringUtils.EMPTY;
            if (currentRecover.getCouponType() == 1) {
                trade = "experience_profit";
            } else if (currentRecover.getCouponType() == 2) {
                trade = "increase_interest_profit";
            } else if (currentRecover.getCouponType() == 3) {
                trade = "cash_coupon_profit";
            }
            // 还款成功
            accountList.setTrade(trade);
            // 余额操作
            accountList.setTradeCode("balance");
            // 投资人资金总额
            accountList.setTotal(account.getTotal());
            // 投资人可用金额
            accountList.setBalance(account.getBalance());
            // 投资人冻结金额
            accountList.setFrost(account.getFrost());
            // 投资人待收金额
            accountList.setAwait(account.getAwait());
            // 创建时间
            accountList.setCreateTime(GetDate.getTimestamp());
            // 更新时间
//          accountList.setBaseUpdate(nowTime);
            // 操作者
            accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY);
            String remark = StringUtils.EMPTY;
            if (currentRecover.getCouponType() == 1) {
                remark = "体验金：" + couponUserCode;
            } else if (currentRecover.getCouponType() == 2) {
                remark = "加息券：" + couponUserCode;
            } else if (currentRecover.getCouponType() == 3) {
                remark = "代金券：" + couponUserCode;
            }
            accountList.setRemark(remark);
            accountList.setIp(ip); // 操作IP
//            accountList.setIsUpdate(0);
//            accountList.setBaseUpdate(0);
            // accountList.setInterest(recoverInterest); // 利息
            accountList.setWeb(0); // PC
            int accountListCnt = accountListService.insertAccountList(accountList);
            if (accountListCnt == 0) {
                throw new RuntimeException("收支明细(ht_account_list)写入失败！");
            }
        }
    }

    @RequestMapping("updateRepayDataRecover")
    public void updateRepayDataRecover(@RequestBody @Valid RepayDataRecoverRequest request) {
        CouponRecoverCustomizeVO currentRecover = request.getCouponRecoverCustomizeVO();
        BorrowTenderCpnVO borrowTenderCpn = request.getBorrowTenderCpnVO();
        String couponUserCode = request.getCouponUserCode();
        String borrowNid = request.getBorrowNid();
        Integer tenderUserId = Integer.valueOf(request.getUserId());
        String borrowStyle = request.getBorrowStyle();
        int periodNow = request.getPeriodNow();

        CouponRecover cr = new CouponRecover();
        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);

        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        // 应还利息
        String recoverInterestStr = StringUtils.isEmpty(currentRecover.getRecoverInterest()) ? "0.00" : currentRecover.getRecoverInterest();
        // 应还本息
        String recoverAccountStr = StringUtils.isEmpty(currentRecover.getRecoverAccount()) ? "0.00" : currentRecover.getRecoverAccount();
        // 应还本金
        String recoverCapitalStr = StringUtils.isEmpty(currentRecover.getRecoverCapital()) ? "0.00" : currentRecover.getRecoverCapital();
        BigDecimal recoverInterest = new BigDecimal(recoverInterestStr);
        BigDecimal recoverAccount = new BigDecimal(recoverAccountStr);
        BigDecimal recoverCapital = new BigDecimal(recoverCapitalStr);

        borrowTenderCpn.setRecoverAccountYes(borrowTenderCpn.getRecoverAccountYes().add(recoverAccount));
        // 已收本金
        borrowTenderCpn.setRecoverAccountCapitalYes(borrowTenderCpn.getRecoverAccountCapitalYes().add(recoverCapital));
        // 已收利息
        borrowTenderCpn.setRecoverAccountInterestYes(borrowTenderCpn.getRecoverAccountInterestYes().add(recoverInterest));
        // 待收总额
        borrowTenderCpn.setRecoverAccountWait(borrowTenderCpn.getRecoverAccountWait().subtract(recoverAccount));
        // 待收本金
        borrowTenderCpn.setRecoverAccountCapitalWait(borrowTenderCpn.getRecoverAccountCapitalWait().subtract(recoverCapital));
        // 待收利息
        borrowTenderCpn.setRecoverAccountInterestWait(borrowTenderCpn.getRecoverAccountInterestWait().subtract(recoverInterest));
        BorrowTenderCpn borrowTenderCpn1 = new BorrowTenderCpn();
        BeanUtils.copyProperties(borrowTenderCpn1,borrowTenderCpn);
        int borrowTenderCnt = borrowTenderCpnService.updateByPrimaryKeySelective(borrowTenderCpn1);
        if (borrowTenderCnt == 0) {
            throw new RuntimeException("投资表(hyjf_borrow_tender_cpn)更新失败！");
        }
        // 更新优惠券投资还款表
        // 转账订单编号
        cr.setTransferId(orderId);
        // 已还款
        cr.setRecoverStatus(1);
        // 收益领取状态(已领取)
        cr.setReceivedFlg(5);
        // 转账时间
        cr.setTransferTime(nowTime);
        // 已经还款时间
        cr.setRecoverYestime(nowTime);
        // 已还利息
        cr.setRecoverInterestYes(recoverInterest);
        // 已还本息
        cr.setRecoverAccountYes(recoverAccount);
        if (currentRecover.getCouponType() == 3) {
            // 代金券
            // 已还本金
            cr.setRecoverCapitalYes(recoverCapital);
        } else {
            // 体验金和加息券
            // 已还本金
            cr.setRecoverCapitalYes(BigDecimal.ZERO);
        }
        // 更新用户
        //cr.setUpdateUserId(CustomConstants.OPERATOR_AUTO_REPAY); todo 确定一下这个字段有啥用
        // 通知用户
        cr.setNoticeFlg(1);
        this.couponRecoverService.updateByPrimaryKeySelective(cr);
        // 插入网站收支明细记录
        AccountWebListVO accountWebList = new AccountWebListVO();
        if (!isMonth) {
            // 未分期
            accountWebList.setOrdid(borrowTenderCpn.getNid());// 订单号
        } else {
            // 分期
            accountWebList.setOrdid(borrowTenderCpn.getNid() + "_" + periodNow);// 订单号
//            if (periodNext > 0) {
//                // 更新还款期
//                this.crRecoverPeriod(couponTenderNid, periodNow + 1);
//            }
        }
        accountWebList.setBorrowNid(borrowNid); // 投资编号
        accountWebList.setUserId(tenderUserId); // 投资者
        accountWebList.setAmount(recoverAccount); // 优惠券投资受益
        accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出

        String tradeType = StringUtils.EMPTY;
        if (currentRecover.getCouponType() == 1) {
            // 体验金
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_TYJ);
            // 体验金收益
            tradeType = CustomConstants.TRADE_COUPON_SY;
        } else if (currentRecover.getCouponType() == 2) {
            // 加息券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_JXQ);
            // 加息券回款
            tradeType = CustomConstants.TRADE_COUPON_HK;
        } else if (currentRecover.getCouponType() == 3) {
            // 代金券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_DJQ);
            // 代金券回款
            tradeType = CustomConstants.TRADE_COUPON_DJ;
        }
        accountWebList.setTradeType(tradeType); // 加息券回款
        String remark = "项目编号：" + borrowNid + "<br />优惠券:" + couponUserCode;
        accountWebList.setRemark(remark); // 投资编号
        accountWebList.setCreateTime(nowTime);
        // TODO: 2018/6/27
        // 插入account_web_list表涉及user库表查询
//        int accountWebListCnt = insertAccountWebList(accountWebList);
//        if (accountWebListCnt == 0) {
//            throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)更新失败！" + "[投资订单号：" + borrowTenderCpn.getNid() + "]");
//        }
    }

    @GetMapping("/getCouponTenderInfo/{couponTenderNid}")
    public BorrowTenderCpnResponse getCouponTenderInfo(@PathVariable String couponTenderNid) {
        BorrowTenderCpnResponse response = new BorrowTenderCpnResponse();
        BorrowTenderCpn borrowTenderCpn = borrowTenderCpnService.getCouponTenderInfo(couponTenderNid);
        if (borrowTenderCpn != null) {
            BorrowTenderCpnVO borrowTenderCpnVO = new BorrowTenderCpnVO();
            BeanUtils.copyProperties(borrowTenderCpn,borrowTenderCpnVO);
            response.setResult(borrowTenderCpnVO);
        }
        return response;
    }
}
