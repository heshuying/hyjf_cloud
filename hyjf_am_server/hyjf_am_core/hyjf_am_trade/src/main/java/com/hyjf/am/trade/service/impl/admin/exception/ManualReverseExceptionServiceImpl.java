package com.hyjf.am.trade.service.impl.admin.exception;

import com.hyjf.am.resquest.admin.ManualReverseAddRequest;
import com.hyjf.am.resquest.admin.ManualReverseCustomizeRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountList;
import com.hyjf.am.trade.dao.model.auto.RUser;
import com.hyjf.am.trade.dao.model.customize.trade.ManualReverseCustomize;
import com.hyjf.am.trade.service.admin.exception.ManualReverseExceptionService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手动冲正异常
 * @author hesy
 * @version ManualReverseExceptionServiceImpl, v0.1 2018/7/14 11:01
 */
@Service
public class ManualReverseExceptionServiceImpl extends BaseServiceImpl implements ManualReverseExceptionService {
    /**
     * 手动冲正列表
     * @param requestBean
     * @return
     */
    @Override
    public List<ManualReverseCustomize> selectManualReverseList(ManualReverseCustomizeRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        // 原交易流水号
        if (StringUtils.isNotEmpty(requestBean.getSeqNoSrch())) {
            param.put("seqNoSrch", requestBean.getSeqNoSrch());
        }
        // 用户名
        if (StringUtils.isNotEmpty(requestBean.getUserNameSrch())) {
            param.put("userNameSrch", requestBean.getUserNameSrch());
        }
        // 电子账号
        if (StringUtils.isNotEmpty(requestBean.getAccountIdSrch())) {
            param.put("accountIdSrch", requestBean.getAccountIdSrch());
        }
        // 交易时间开始
        if (StringUtils.isNotEmpty(requestBean.getTxTimeStartSrch())) {
            param.put("txTimeStartSrch", requestBean.getTxTimeStartSrch());
        }
        // 交易时间结束
        if (StringUtils.isNotEmpty(requestBean.getTxTimeEndSrch())) {
            param.put("txTimeEndSrch", requestBean.getTxTimeEndSrch());
        }
        if (requestBean.getLimitStart()!=null && requestBean.getLimitStart() != -1){
            param.put("limitStart", requestBean.getLimitStart());
        }
        if (requestBean.getLimitEnd()!=null && requestBean.getLimitEnd() != -1){
            param.put("limitEnd", requestBean.getLimitEnd());
        }
        return manualReverseCustomizeMapper.selectManualReverseList(param);
    }

    /**
     * 手动冲正总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int countManualReverse(ManualReverseCustomizeRequest requestBean) {
        Map<String, Object> param = new HashMap<String, Object>();
        // 原交易流水号
        if (StringUtils.isNotEmpty(requestBean.getSeqNoSrch())) {
            param.put("seqNoSrch", requestBean.getSeqNoSrch());
        }
        // 用户名
        if (StringUtils.isNotEmpty(requestBean.getUserNameSrch())) {
            param.put("userNameSrch", requestBean.getUserNameSrch());
        }
        // 电子账号
        if (StringUtils.isNotEmpty(requestBean.getAccountIdSrch())) {
            param.put("accountIdSrch", requestBean.getAccountIdSrch());
        }
        // 交易时间开始
        if (StringUtils.isNotEmpty(requestBean.getTxTimeStartSrch())) {
            param.put("txTimeStartSrch", requestBean.getTxTimeStartSrch());
        }
        // 交易时间结束
        if (StringUtils.isNotEmpty(requestBean.getTxTimeEndSrch())) {
            param.put("txTimeEndSrch", requestBean.getTxTimeEndSrch());
        }
        return manualReverseCustomizeMapper.countManualReverse(param);
    }

    /**
     * 手动冲正更新
     * @auther: hesy
     * @date: 2018/7/14
     */
    @Override
    public boolean updateManualReverse(ManualReverseAddRequest requestBean){
        //操作管理员与ip获取
        //String createUserId = request.getParameter("userId");
        //String createIp = request.getParameter("ip");

        //设置日期格式
        //SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            Integer nowTime = GetDate.getNowTime10();
            //冲正金额
            BigDecimal total = new BigDecimal(requestBean.getAmount().trim());

            //获取用户userId
            RUser user = this.getRUser(requestBean.getUserName());
            // 重新获取用户信息
            Account account = this.getAccount(user.getUserId());
            // 写入收支明细
            AccountList accountList = new AccountList();
            // 账户信息
            // 订单号：空
            //accountList.setNid(GetOrderIdUtils.getOrderId2(user.getUserId()));
            accountList.setUserId(user.getUserId());
            accountList.setAmount(total);
            //accountList.setType(2);
            //手动冲正表->收支类型:0收入 1支出
            //list表->1收入2支出
            if ("0".equals(requestBean.getType())) {
                accountList.setType(1);
                //收入
                accountList.setTrade("account_adjustment_up");
            } else if ("1".equals(requestBean.getType())) {
                accountList.setType(2);
                //支出
                accountList.setTrade("account_adjustment_down");
                total = total.negate();
            }
            //操作识别码 balance余额操作 frost冻结操作 await待收操作
            accountList.setTradeCode("balance");
            accountList.setTotal(account.getTotal());
            accountList.setBalance(account.getBalance());
            accountList.setFrost(account.getFrost());
            accountList.setAwait(account.getAwait());
            accountList.setRepay(account.getRepay());
            accountList.setBankTotal(account.getBankTotal().add(total)); // 银行总资产
            accountList.setBankBalance(account.getBankBalance().add(total)); // 银行可用余额
            accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
            accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
            accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
            accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
            accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
            accountList.setBankAwait(account.getBankAwait());// 银行待收总额
            accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
            accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计投资
            accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
            accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
            accountList.setPlanFrost(account.getPlanFrost());
            accountList.setSeqNo(requestBean.getSeqNo());
            accountList.setTxDate(nowTime);
            accountList.setTxTime(nowTime);
            accountList.setBankSeqNo(requestBean.getBankSeqNo());
            accountList.setAccountId(requestBean.getAccountId());
            accountList.setRemark("原交易订单号 " + requestBean.getBankSeqNo());
            accountList.setCreateTime(GetDate.getNowTime());
            //操作员
            //accountList.setOperator(createUserId);
            //操作IP
            //accountList.setIp(createIp);
            accountList.setIsBank(1);
            accountList.setWeb(0);
            accountList.setCheckStatus(1);// 对账状态0：未对账 1：已对账
            accountList.setTradeStatus(1);// 0失败1成功2失败
            //插入资产明细
            this.accountListMapper.insertSelective(accountList);
            //用户相应余额增加
            Account newAccount = new Account();

            newAccount.setUserId(user.getUserId());// 用户Id
            newAccount.setBankTotal(total); // 累加到账户总资产
            newAccount.setBankBalance(total); // 累加可用余额
            newAccount.setBankBalanceCash(total);// 江西银行可用余额
            //余额恢复
            this.adminAccountCustomizeMapper.updateManualReverseSuccess(newAccount);
            this.insertManualReverse(requestBean, "0");
        } catch (Exception e) {
            this.insertManualReverse(requestBean, "1");
            logger.error("手动冲正更新失败", e);
            return false;
        }

        return true;

    }

    private void insertManualReverse(ManualReverseAddRequest requestBean, String status){
        ManualReverseCustomize manualReverseCustomize = new ManualReverseCustomize();
        //原交易系统跟踪号
        manualReverseCustomize.setSeqNo(requestBean.getSeqNo());
        //原交易订单号
        manualReverseCustomize.setBankSeqNo(requestBean.getBankSeqNo());
        //设置日期格式
        //SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        //交易时间
        manualReverseCustomize.setTxTime(GetDate.getNowTime());
        //用户名
        manualReverseCustomize.setUserName(requestBean.getUserName());
//		// 重新获取用户银行卡号
//		if (StringUtils.isEmpty(requestBean.getAccountId())) {
//			requestBean.setAccountId(getAccountId(requestBean.getUserName()));
//		}
        //电子账号
        manualReverseCustomize.setAccountId(requestBean.getAccountId());
        //资金托管平台：1江西银行
        manualReverseCustomize.setIsBank("1");
        //收支类型:0收入 1支出
        manualReverseCustomize.setType(requestBean.getType());
        //交易类型
        manualReverseCustomize.setTransType("充值");
        //操作金额
        manualReverseCustomize.setAmount(requestBean.getAmount());
        //操作结果 操作状态 0 成功 1失败
        manualReverseCustomize.setStatus(status);
        //用户id
        manualReverseCustomize.setCreateUserId(Integer.valueOf(requestBean.getLoginUserId()));
        //插入时间
        manualReverseCustomize.setCreateTime(GetDate.getNowTime10());

        this.manualReverseCustomizeMapper.insertManualReverse(manualReverseCustomize);
    }

}
