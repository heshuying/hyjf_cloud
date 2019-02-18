/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.AdminTransferExceptionLogCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponRecoverCustomize;
import com.hyjf.am.trade.service.admin.exception.TransferExceptionLogService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author jun
 * @version AdminTransferExceptionLogServiceImpl, v0.1 2018/7/10 11:31
 */
@Service
public class TransferExceptionLogServiceImpl extends BaseServiceImpl implements TransferExceptionLogService {

    /** 用户ID */
    private static final String USERID = "userId";
    /** 还款金额(优惠券用) */
    private static final String VAL_AMOUNT = "val_amount";

    @Autowired
    private CommonProducer commonProducer;
    /**
     * 银行转账列表
     * @param request
     * @return
     */
    @Override
    public List<AdminTransferExceptionLogCustomize> getRecordList(TransferExceptionLogVO request) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(request.getOrderId())){
            paraMap.put("orderId", request.getOrderId());
        }
        if(StringUtils.isNotEmpty(request.getUsername())){
            paraMap.put("username", request.getUsername());
        }
        if(request.getTradeType() != null){
            paraMap.put("type", String.valueOf(request.getTradeType()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStartSrch())){
            paraMap.put("timeStartSrch", request.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(request.getTimeEndSrch())){
            paraMap.put("timeEndSrch", request.getTimeEndSrch());
        }

        paraMap.put("limitStart",request.getLimitStart());
        paraMap.put("limitEnd",request.getLimitEnd());

        return transferExceptionLogCustomizeMapper.selectTransferExceptionList(paraMap);
    }

    /**
     * 银行转账异常数
     * @param request
     * @return
     */
    @Override
    public Integer getCountRecord(TransferExceptionLogVO request) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(request.getOrderId())){
            paraMap.put("orderId", request.getOrderId());
        }
        if(StringUtils.isNotEmpty(request.getUsername())){
            paraMap.put("username", request.getUsername());
        }
        if(request.getTradeType() != null){
            paraMap.put("type", String.valueOf(request.getTradeType()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStartSrch())){
            paraMap.put("timeStartSrch", request.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(request.getTimeEndSrch())){
            paraMap.put("timeEndSrch", request.getTimeEndSrch());
        }

        return transferExceptionLogCustomizeMapper.countTransferException(paraMap);
    }

    /**
     * 更新银行转账信息
     * @param request
     * @return
     */
    @Override
    public Integer updateTransferExceptionLogByUUID(TransferExceptionLogVO request) {
        TransferExceptionLogWithBLOBs target = new TransferExceptionLogWithBLOBs();
        BeanUtils.copyProperties(request, target);
        return transferExceptionLogMapper.updateByPrimaryKeySelective(target);
    }

    /**
     * 获取银行转账异常通过uuid
     * @param uuid
     * @return
     */
    @Override
    public TransferExceptionLog getTransferExceptionLogByUUID(String uuid) {
        return transferExceptionLogMapper.selectByPrimaryKey(uuid);
    }

    /**
     * 转账成功后续处理
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferAfter(JSONObject jsonObject) throws Exception {

        TransferExceptionLogVO transfer= jsonObject.getObject("TransferExceptionLogVO",TransferExceptionLogVO.class);
        BankCallBean resultBean= jsonObject.getObject("BankCallBean",BankCallBean.class);
        //取的优惠券还款记录
        //CouponRecover recover = couponRecoverMapper.selectByPrimaryKey(transfer.getRecoverId());
        CouponRecoverVO recover = jsonObject.getObject("CouponRecoverVO",CouponRecoverVO.class);
        if (recover == null){
            throw new Exception("未查询到对应的优惠券还款记录，[recoverid：" + transfer.getRecoverId() + "]");
        }
        String couponUserCode = getCouponTender(recover.getTenderId());
        if(couponUserCode == null){
            throw new Exception("未查询到对应的CouponTender记录，[tender_nid：" + recover.getTenderId() + "]");
        }
        //取得优惠券出借信息
        BorrowTenderCpnVO borrowTender = jsonObject.getObject("BorrowTenderCpnVO",BorrowTenderCpnVO.class);
        //BorrowTenderCpn borrowTender = this.getCouponTenderInfo(recover.getTenderId());
        if(borrowTender == null) {
            throw new Exception("未查询到对应的优惠券出借记录，[tender_nid：" + recover.getTenderId() + "]");
        }

        boolean isMonth = false;
        // 当前期数
        String periodNow = recover.getRecoverPeriod();
        if ("null".equals(periodNow) || StringUtils.isBlank(periodNow)){
            throw new Exception("未查询到对应的优惠券分期还款的期数，[tender_nid：" + recover.getTenderId() + "]");
        }

        // 剩余还款期数
        Integer periodNext = null;
        if(borrowTender.getTenderType() != 3){
            // 取得借款详情
            Borrow borrow = getBorrow(borrowTender.getBorrowNid());
            if(borrow == null) {
                throw new Exception("未查询到对应的标的记录，[borrow_nid：" + borrowTender.getBorrowNid() + "]");
            }

            // 还款期数
            Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
            // 剩余还款期数
            periodNext = borrowPeriod - Integer.parseInt(periodNow);
            String borrowStyle = borrow.getBorrowStyle();

            // 是否分期(true:月标, false:天标)
            isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

        }

        // 当前还款
        CouponRecoverCustomize currentRecover = null;
        currentRecover = this.getCurrentCouponRecover(recover.getTenderId(),Integer.parseInt(periodNow));
        if (currentRecover == null) {
            throw new Exception("优惠券还款数据不存在。[项目编号：" + borrowTender.getBorrowNid() + "]，" + "[优惠券出借编号：" + recover.getTenderId() + "]");
        }

        // 应还利息
        String recoverInterestStr = StringUtils.isEmpty(currentRecover.getRecoverInterest()) ? "0.00" : currentRecover.getRecoverInterest();
        // 应还本息
        String recoverAccountStr = StringUtils.isEmpty(currentRecover.getRecoverAccount()) ? "0.00" : currentRecover.getRecoverAccount();
        // 应还本金
        String recoverCapitalStr = StringUtils.isEmpty(currentRecover.getRecoverCapital()) ? "0.00" : currentRecover.getRecoverCapital();
        BigDecimal recoverInterest = new BigDecimal(recoverInterestStr);
        BigDecimal recoverAccount = new BigDecimal(recoverAccountStr);
        BigDecimal recoverCapital = new BigDecimal(recoverCapitalStr);

        // 判断该收支明细是否存在时,跳出本次循环
        if (countAccountListByNidCoupon(transfer.getOrderId()) == 0) {

            // 更新账户信息(出借人)
            Account account = new Account();

            int accountCnt;
            // 如果是计划类出借
            if(borrowTender.getTenderType() == 3 ){
                // 更新账户信息(出借人)
                account.setUserId(transfer.getUserId());

                account.setBankBalance(recoverAccount); // 账户余额
                account.setBankInterestSum(recoverAccount);

                // 计划已还总利息
                account.setPlanRepayInterest(recoverAccount);
                // 计划待收收益
                account.setPlanInterestWait(recoverInterest);
                // 计划待收总额
                account.setPlanAccountWait(recoverAccount);

                // 更新用户计划账户
                accountCnt = this.adminAccountCustomizeMapper.updateOfRepayCouponHjh(account);
            }else{
                // 直投类
                account.setUserId(transfer.getUserId());
                account.setBankTotal(BigDecimal.ZERO);// 出借人资金总额 +利息
                account.setBankFrost(BigDecimal.ZERO);// 出借人冻结金额+出借金额(等额本金时出借金额可能会大于计算出的本金之和)
                account.setBankBalance(recoverAccount); // 账户余额
                account.setBankAwait(recoverAccount);// 出借人待收金额+利息+ 本金
                account.setBankAwaitCapital(BigDecimal.ZERO);// 出借人待收本金
                account.setBankAwaitInterest(recoverAccount);// 出借人待收利息
                account.setBankInterestSum(recoverAccount);
                account.setBankInvestSum(BigDecimal.ZERO);// 出借人累计出借
                account.setBankFrostCash(BigDecimal.ZERO);// 江西银行冻结金额

                accountCnt = this.adminAccountCustomizeMapper.updateOfRepayTender(account);
            }

            if (accountCnt == 0) {
                throw new Exception("出借人资金记录(huiyingdai_account)更新失败！" + "[优惠券出借编号：" + recover.getTenderId() + "]");
            }
            // 取得账户信息(出借人)
            account  = getAccount(transfer.getUserId());
            if (account == null) {
                throw new Exception("出借人账户信息不存在。[出借人ID：" + transfer.getUserId() + "]，" + "[优惠券出借编号：" + recover.getTenderId() + "]");
            }

            // 写入收支明细
            AccountList accountList = new AccountList();
            // 转账订单编号
            accountList.setNid(transfer.getOrderId());
            // 出借人
            accountList.setUserId(transfer.getUserId());
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
            accountList.setAccountId(transfer.getAccountId());
            accountList.setTxDate(Integer.parseInt(GetOrderIdUtils.getTxDate()));
            accountList.setTxTime(Integer.parseInt(GetOrderIdUtils.getTxTime()));
            accountList.setSeqNo(transfer.getSeqNo());
            accountList.setBankSeqNo(transfer.getSeqNo());
            accountList.setCheckStatus(0);
            accountList.setTradeStatus(1);
            accountList.setIsBank(1);
            // 余额操作
            accountList.setTradeCode("balance");
            // 创建时间
            accountList.setCreateTime(GetDate.getDate());
            // 操作者
            accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY);

            // 出借收入
            accountList.setAmount(transfer.getTransAmt());
            // 1收入
            accountList.setType(1);

            if(currentRecover.getCouponType()==1){
                //体验金
                accountList.setRemark("体验金："+ couponUserCode);
                // 还款成功
                accountList.setTrade("experience_profit");
            }else if(currentRecover.getCouponType()==2){
                //加息券
                accountList.setRemark("加息券："+ couponUserCode);
                // 还款成功
                accountList.setTrade("increase_interest_profit");
            }else if(currentRecover.getCouponType()==3){
                //加息券
                accountList.setRemark("代金券："+ couponUserCode);
                // 还款成功
                accountList.setTrade("cash_coupon_profit");
            }

            accountList.setIp(""); // 操作IP
//            accountList.setInterest(transfer.getTransAmt()); // 利息
            accountList.setWeb(0); // PC
            int accountListCnt = insertAccountList(accountList);
            if (accountListCnt == 0) {
                throw new Exception("收支明细(huiyingdai_account_list)写入失败！" + "[优惠券出借编号：" + recover.getTenderId() + "]");
            }
        }

// 已收总额
        borrowTender.setRecoverAccountYes(borrowTender.getRecoverAccountYes().add(recoverAccount));
        // 已收本金
        borrowTender.setRecoverAccountCapitalYes(borrowTender.getRecoverAccountCapitalYes().add(recoverCapital));
        // 已收利息
        borrowTender.setRecoverAccountInterestYes(borrowTender.getRecoverAccountInterestYes().add(recoverInterest));
        // 待收总额
        borrowTender.setRecoverAccountWait(borrowTender.getRecoverAccountWait().subtract(recoverAccount));
        // 待收本金
        borrowTender.setRecoverAccountCapitalWait(borrowTender.getRecoverAccountCapitalWait().subtract(recoverCapital));
        // 待收利息
        borrowTender.setRecoverAccountInterestWait(borrowTender.getRecoverAccountInterestWait().subtract(recoverInterest));
        int borrowTenderCnt = borrowTenderCpnMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(borrowTender,BorrowTenderCpn.class));
        if (borrowTenderCnt == 0) {
            throw new Exception("出借表(huiyingdai_borrow_tender)更新失败！" + "[优惠券出借编号：" + recover.getTenderId() + "]");
        }
        CouponRecover crTemp = this.couponRecoverMapper.selectByPrimaryKey(recover.getId());
        // 更新优惠券出借还款表
        CouponRecover cr = new CouponRecover();
        cr.setId(recover.getId());
        // 转账订单编号
        cr.setTransferId(transfer.getOrderId());
        // 已还款
        cr.setRecoverStatus(1);
        // 收益领取状态(已领取)
        cr.setReceivedFlg(5);
        // 转账时间
        cr.setTransferTime(GetDate.getNowTime10());
        // 已经还款时间
        cr.setRecoverYestime(GetDate.getNowTime10());
        // 已还本息
        cr.setRecoverAccountYes(transfer.getTransAmt());
        // 已还利息
        cr.setRecoverInterestYes(crTemp.getRecoverInterest());
        if(currentRecover.getCouponType() == 3){
            // 代金券
            // 已还本金
            cr.setRecoverCapitalYes(crTemp.getRecoverCapital());
        }else{
            // 体验金和加息券
            // 已还本金
            cr.setRecoverCapitalYes(BigDecimal.ZERO);
        }
        // 更新时间
        cr.setUpdateTime(GetDate.getDate());
        // 更新用户
        cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_REPAY));
        // 通知用户
        cr.setNoticeFlg(1);

        this.couponRecoverMapper.updateByPrimaryKeySelective(cr);

        // 插入网站收支明细记录
        //AccountWebList accountWebList = new AccountWebList();
        AccountWebListVO accountWebList= jsonObject.getObject("AccountWebListVO",AccountWebListVO.class);

        //AccountWebList accountWebList = CommonUtils.convertBean(accountWebListVO,AccountWebList.class);

        if(!isMonth){
            // 未分期
            accountWebList.setOrdid(borrowTender.getNid());// 订单号
        }else{
            // 分期
            accountWebList.setOrdid(borrowTender.getNid() + "_" + periodNow);// 订单号
            if(periodNext !=null &&  periodNext > 0){
                // 更新还款期
                crRecoverPeriod(recover.getTenderId(),Integer.parseInt(periodNow)+1);
            }
        }

        accountWebList.setBorrowNid(borrowTender.getBorrowNid()); // 出借编号
        accountWebList.setUserId(borrowTender.getUserId()); // 出借者
        accountWebList.setAmount(Double.valueOf(transfer.getTransAmt().toString())); // 优惠券出借受益
        accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出
        String remark = "";
        if(currentRecover.getCouponType()==1){
            // 体验金
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_TYJ);
            //体验金收益
            accountWebList.setTradeType(CustomConstants.TRADE_COUPON_SY);
        }else if(currentRecover.getCouponType()==2){
            // 加息券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_JXQ);
            //加息券回款
            accountWebList.setTradeType(CustomConstants.TRADE_COUPON_HK);
        }else if(currentRecover.getCouponType()==3){
            // 代金券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_DJQ);
            // 代金券回款
            accountWebList.setTradeType(CustomConstants.TRADE_COUPON_DJ);

        }
        remark = "项目编号："+borrowTender.getBorrowNid()+"<br />优惠券:"+couponUserCode;
        accountWebList.setRemark(remark); // 出借编号
        accountWebList.setCreateTime(GetDate.getNowTime10());

        commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));


        // 添加红包账户明细
        BankMerchantAccount nowBankMerchantAccount = this.getBankMerchantAccount(resultBean.getAccountId());
        nowBankMerchantAccount.setAvailableBalance(nowBankMerchantAccount.getAvailableBalance().subtract(transfer.getTransAmt()));
        nowBankMerchantAccount.setAccountBalance(nowBankMerchantAccount.getAccountBalance().subtract(transfer.getTransAmt()));
        nowBankMerchantAccount.setUpdateTime(GetDate.getDate());

        // 更新红包账户信息
        int updateCount = this.updateBankMerchantAccount(nowBankMerchantAccount);
        if(updateCount > 0){
            UserInfoCustomizeVO userInfoCustomize= jsonObject.getObject("UserInfoCustomizeVO",UserInfoCustomizeVO.class);
//            UserInfoCustomize userInfoCustomize = this.queryUserInfoByUserId(transfer.getUserId());

            // 添加红包明细
            BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
            bankMerchantAccountList.setOrderId(transfer.getOrderId());
            bankMerchantAccountList.setUserId(transfer.getUserId());
            bankMerchantAccountList.setAccountId(transfer.getAccountId());
            bankMerchantAccountList.setAmount(transfer.getTransAmt());
            bankMerchantAccountList.setBankAccountCode(resultBean.getAccountId());
            bankMerchantAccountList.setBankAccountBalance(nowBankMerchantAccount.getAccountBalance());
            bankMerchantAccountList.setBankAccountFrost(nowBankMerchantAccount.getFrost());
            bankMerchantAccountList.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_AUTOMATIC);
            bankMerchantAccountList.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
            bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
            bankMerchantAccountList.setTxDate(Integer.parseInt(resultBean.getTxDate()));
            bankMerchantAccountList.setTxTime(Integer.parseInt(resultBean.getTxTime()));
            bankMerchantAccountList.setSeqNo(resultBean.getSeqNo());
            bankMerchantAccountList.setCreateTime(GetDate.getDate());
            bankMerchantAccountList.setUpdateTime(GetDate.getDate());
            bankMerchantAccountList.setCreateUserId(transfer.getUserId());
            bankMerchantAccountList.setUpdateUserId(transfer.getUserId());
            bankMerchantAccountList.setRegionName(userInfoCustomize.getRegionName());
            bankMerchantAccountList.setBranchName(userInfoCustomize.getBranchName());
            bankMerchantAccountList.setDepartmentName(userInfoCustomize.getDepartmentName());
            bankMerchantAccountList.setCreateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setUpdateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setRemark("优惠券还款异常修复");

            this.bankMerchantAccountListMapper.insertSelective(bankMerchantAccountList);
        }


        logger.info(TransferExceptionLogServiceImpl.class.toString(), "transferAfter", "-----------重新执行还款结束---"+ borrowTender.getBorrowNid() +"---------"+recover.getTransferId()+"---------------");

        return true;
    }


    /**
     *
     * 更新红包账户
     * @param account
     * @return
     */
    private int updateBankMerchantAccount(BankMerchantAccount account){
        return bankMerchantAccountMapper.updateByPrimaryKeySelective(account);
    }


    /**
     *
     * 加载红包账户
     * @param accountCode
     * @return
     */
    private BankMerchantAccount getBankMerchantAccount(String accountCode) {
        BankMerchantAccountExample bankMerchantAccountExample = new BankMerchantAccountExample();
        bankMerchantAccountExample.createCriteria().andAccountCodeEqualTo(accountCode);
        List<BankMerchantAccount> bankMerchantAccounts = bankMerchantAccountMapper.selectByExample(bankMerchantAccountExample);
        if (bankMerchantAccounts != null && bankMerchantAccounts.size() != 0) {
            return bankMerchantAccounts.get(0);
        } else {
            return null;
        }
    }


    /**
     * 获取CouponTender
     * @author hsy
     * @param tenderNid
     * @return
     */
    private String getCouponTender(String tenderNid) {
        Map<String, Object> paraMap = new HashMap<String,Object>();
        paraMap.put("tenderNid", tenderNid);
        String couponUserCode = transferExceptionLogCustomizeMapper.getCouponUserCode(paraMap);

        return couponUserCode;
    }

    /**
     * 取得优惠券出借信息
     * @param couponTenderNid
     * @return
     */
    private BorrowTenderCpn getCouponTenderInfo(String couponTenderNid){
        BorrowTenderCpnExample example = new BorrowTenderCpnExample();
        example.createCriteria().andNidEqualTo(couponTenderNid);
        List<BorrowTenderCpn> btList = this.borrowTenderCpnMapper.selectByExample(example);
        if(btList!=null&&btList.size()>0){
            return btList.get(0);
        }
        return null;
    }


    /**
     * 根据订单编号取得该订单的还款列表
     * @param couponTenderNid
     * @param periodNow
     * @return
     */
    private CouponRecoverCustomize getCurrentCouponRecover(String couponTenderNid, int periodNow) {

        Map<String,Object> paramMap = new HashMap<String ,Object>();
        paramMap.put("tenderNid", couponTenderNid);
        paramMap.put("periodNow", periodNow);
        return this.couponRecoverCustomizeMapper.selectCurrentCouponRecoverFailed(paramMap);

    }

    /**
     * 判断该收支明细是否存在
     * @param nid
     * @return
     */
    public int countAccountListByNidCoupon(String nid) {
        AccountListExample accountListExample = new AccountListExample();
        accountListExample.or(accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("increase_interest_profit"));
        accountListExample.or(accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("experience_profit"));
        return this.accountListMapper.countByExample(accountListExample);
    }


    /**
     * 写入收支明细
     *
     * @param accountList
     * @return
     */
    private int insertAccountList(AccountList accountList) {
        // 写入收支明细
        return this.accountListMapper.insertSelective(accountList);
    }

    /**
     * 更新还款期
     * @param tenderNid 投资订单编号
     * resetRecoverFlg 0:非还款期，1;还款期
     * currentRecoverFlg 0:非还款期，1;还款期
     * @param period 期数
     */
    private void crRecoverPeriod(String tenderNid,int period){
        Map<String,Object> paramMapAll = new HashMap<String,Object>();
        paramMapAll.put("currentRecoverFlg", 0);
        paramMapAll.put("tenderNid", tenderNid);
        this.couponRecoverCustomizeMapper.crRecoverPeriod(paramMapAll);

        Map<String,Object> paramMapCurrent = new HashMap<String,Object>();
        paramMapCurrent.put("currentRecoverFlg", 1);
        paramMapCurrent.put("tenderNid", tenderNid);
        paramMapCurrent.put("period", period);
        this.couponRecoverCustomizeMapper.crRecoverPeriod(paramMapCurrent);
    }
}
