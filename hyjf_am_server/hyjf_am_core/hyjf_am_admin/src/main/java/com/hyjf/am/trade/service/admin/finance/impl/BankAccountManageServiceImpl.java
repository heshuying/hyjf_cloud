/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.resquest.admin.BankAccountManageRequest;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.trade.bean.ResultBean;
import com.hyjf.am.trade.bean.SynBalanceBean;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BankAccountManageCustomize;
import com.hyjf.am.trade.service.admin.finance.BankAccountManageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageServiceImpl, v0.1 2018/6/29 14:02
 */
@Service
public class BankAccountManageServiceImpl extends BaseServiceImpl implements BankAccountManageService {

    @Value("${hyjf.bank.instcode}")
    private String bankInstCode;

    @Value("${hyjf.bank.bankcode}")
    private String bankBankCode;


    private static final Logger logger = LoggerFactory.getLogger(BankAccountManageServiceImpl.class);
    /**
     * 更新账户余额
     *
     * @param accountVO
     * @return
     */
    @Override
    public Integer updateAccount(AccountVO accountVO) {
        Account account = new Account();
        // 可提现
        if (Validator.isNotNull(accountVO.getBalance())) {
            account.setBankBalanceCash(accountVO.getBalance());
        }
        // 不可提现
        if (Validator.isNotNull(accountVO.getFrost())) {
            account.setBankFrostCash(accountVO.getFrost());
        }
        if (Validator.isNotNull(accountVO.getUserId())){
            account.setUserId(accountVO.getUserId());
        }
        AccountExample example = new AccountExample();
        example.createCriteria().andUserIdEqualTo(accountVO.getUserId());
        // 更新账户表
        boolean result = this.accountMapper.updateByExampleSelective(account, example) > 0 ? true : false;
        return result ? 1 : 0;
    }

    /**
     * 银行账户管理线下对账
     *
     * @param adminBankAccountCheckCustomizeVO
     * @return
     */
    @Override
    public String updateAccountCheck(AdminBankAccountCheckCustomizeVO adminBankAccountCheckCustomizeVO) {
        // 手动银行对账
        String msg = "success";
        String accountId = "";
        String userName = "";
        Integer userId = adminBankAccountCheckCustomizeVO.getUserId();
        String startTime = adminBankAccountCheckCustomizeVO.getStartDate();
        String endTime = adminBankAccountCheckCustomizeVO.getEndDate();
        String ip = adminBankAccountCheckCustomizeVO.getIp();
        String payment = adminBankAccountCheckCustomizeVO.getPayment();
        String cardId = adminBankAccountCheckCustomizeVO.getCardId();
        try {
            AdminBankAccountCheckCustomizeVO customize = new AdminBankAccountCheckCustomizeVO();
            customize.setUserId(adminBankAccountCheckCustomizeVO.getUserId());
            List<AdminBankAccountCheckCustomizeVO> bankOpenAccountList = this.adminBankAccountCheckCustomizeMapper.queryAllBankOpenAccount(customize);
            /** redis 锁 */
            boolean reslut = RedisUtils.tranactionSet(RedisConstants.SYN_VALANCE + adminBankAccountCheckCustomizeVO.getUserId(), 30);
            // 如果没有设置成功，说明有请求来设置过
            if (!reslut) {
                msg = "不能重复对账";
                return msg;
            }
            if (bankOpenAccountList != null && bankOpenAccountList.size() > 0) {
                for (int i = 0; i < 1; i++) {
                    accountId = bankOpenAccountList.get(0).getAccountId();
                    userName = bankOpenAccountList.get(0).getUserName();
                }
                List<ResultBean> resultList = queryAllAccountDetails(userId, accountId, startTime, endTime);
                if (resultList != null && resultList.size() > 0) {
                    // 遍历循环返回列表进行入账处理
                    updateBankAccountCheck(resultList, userId, userName, accountId, ip, payment, cardId);
                } else {
                    msg = "该时间段没有需要对账的交易!";
                }
            } else {
                msg = "该用户未开户!";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("========The " + userId + " queryAccountDetail is Error !");
            msg = userName + "对账异常!";
        }
        return msg;
    }


    /**
     * 获得接口返回单个用户的所有线下充值明细
     *
     * @param endTime
     * @param startTime
     * @return
     */
    public List<ResultBean> queryAllAccountDetails(Integer userId, String accountId, String startTime, String endTime) {
        // 获得接口返回的所有交易明细
        List<UnderLineRecharge> codeList=getUnderLineRechargeList();
        List<ResultBean> recordList = new ArrayList<ResultBean>();
        int pageSize = 10;
        for (UnderLineRecharge code : codeList){
            // 分页数据
            int pageNum = 1;

            Date checkEndDate = getDateByString(endTime);
            Date checkStartDate = getDateByString(startTime);

            String startDate = getDateString(checkStartDate, 2);
            String endDate = getDateString(checkEndDate, 2);

            List<ResultBean> list = new ArrayList<ResultBean>();
            // 调用查询明细接口 查所有交易明细
            String inpDate = "";
            String inpTime = "";
            String relDate = "";
            String traceNo = "";
            do {
                BankCallBean bean = this.queryAccountDetailsNew(userId, accountId, startDate, endDate, code.getCode(), String.valueOf(pageNum),
                        inpDate, inpTime, relDate, traceNo);
                if (bean == null) {
                    logger.info(this.getClass().getName(), "同步余额失败");
                    continue;
                }
                //返回失败
                if (!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())) {
                    logger.info(this.getClass().getName(), "-------------------调用查询接口失败，失败编码：" + bean.getRetCode() + "--------------------");
                    continue;
                }
                //解析返回数据(记录为空)
                String content = bean.getSubPacks();
                if (StringUtils.isEmpty(content)) {
                    continue;
                }
                list = JSONArray.parseArray(bean.getSubPacks(), ResultBean.class);
                recordList.addAll(list);
                // 获得最后一条交易记录 并准备下一次查询用的参数
                if (list != null && list.size() > 0) {
                    ResultBean lastResult = list.get(list.size() - 1);
                    inpDate = lastResult.getInpDate();
                    inpTime = lastResult.getInpTime();
                    relDate = lastResult.getRelDate();
                    traceNo = String.valueOf(lastResult.getTraceNo());
                }
                pageNum++;
            } while (list != null &&list.size() == pageSize);
        }
        logger.info(this.getClass().getName(), "-------------------" + recordList.size() + "同步余额总条数--------------------");
        return recordList;
    }

    /**
     * 从Redis获取线下充值类型List
     * @return
     * @Author pcc
     */
    private List<UnderLineRecharge> getUnderLineRechargeList() {
        //从Redis获取线下充值类型List
        String codeStringList = RedisUtils.get(RedisConstants.UNDER_LINE_RECHARGE_TYPE);
        JSONArray redisCodeList = JSONArray.parseArray(codeStringList);

        if (StringUtils.isBlank(codeStringList) || redisCodeList.size() <= 0) {
            logger.info(this.getClass().getName(), "---------------------------线下充值类型Redis为空!-------------------------");
            UnderLineRechargeRequest request = new UnderLineRechargeRequest();
            List<UnderLineRecharge> codeList = selectUnderLineRechargeList(request);
            return codeList;
        }else{
            List<UnderLineRecharge> codeList =new ArrayList<UnderLineRecharge>();
            for(Object code : redisCodeList) {
                UnderLineRecharge underLineRecharge=new UnderLineRecharge();
                underLineRecharge.setCode(code.toString());
                codeList.add(underLineRecharge);
            }
            return codeList;
        }
    }

    /**
     * 获取数据表中线下充值类型
     * @return
     * @Author : huanghui
     */
    public List<UnderLineRecharge> selectUnderLineRechargeList(UnderLineRechargeRequest request){
        UnderLineRechargeExample example = new UnderLineRechargeExample();
        UnderLineRechargeExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(request.getCode()) && !"".equals(request.getCode())){
            criteria.andCodeEqualTo(request.getCode());
        }

        // 启用状态的
        criteria.andStatusEqualTo(0);
        example.setLimitStart(request.getLimitStart());
        example.setLimitEnd(request.getLimitEnd());
        example.setOrderByClause("create_time DESC");
        return underLineRechargeMapper.selectByExample(example);
    }

    /**
     * 调用交易明细查询接口获得交易明细 已改
     *
     * @param userId
     * @param accountId
     * @param startDate
     * @param endDate
     * @param type
     * @param transType
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Deprecated
    public BankCallBean queryAccountDetails(Integer userId, String accountId, String startDate, String endDate, String type, String transType,
                                            String pageNum, String pageSize, String inpDate, String inpTime, String relDate, String traceNo) {
        // 参数不正确
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(type)) {
            return null;
        }
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_ACCOUNT_DETAILS_QUERY);
        // 修改手机号增强
        // accountDetailsQuery2
        // 机构代码
        bean.setInstCode(bankInstCode);
        bean.setBankCode(bankBankCode);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 起始日期
        bean.setStartDate(startDate);
        // 结束日期
        bean.setEndDate(endDate);
        // 交易种类 0-所有交易 1-转入交易 2-转出交易 9-指定交易类型
        bean.setType(type);
        if ("9".equals(type)) {
            // 交易类型
            bean.setTranType(transType);
        }
        // 翻页标识  空：首次查询；1：翻页查询；
        if (StringUtils.isNotEmpty(pageNum) && !"1".equals(pageNum)) {
            bean.setRtnInd("1");
        } else {
            bean.setRtnInd("");
        }
        bean.setInpDate(inpDate);
        bean.setInpTime(inpTime);
        bean.setRelDate(relDate);
        bean.setTraceNo(traceNo);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        // 调用接口
        return BankCallUtils.callApiBg(bean);
    }

    /**
     * 调用近两日线下充值明细查询获得交易明细
     *
     * @param userId
     * @param accountId
     * @param startDate
     * @param endDate
     * @param transType
     * @param pageNum
     * @return
     */
    public BankCallBean queryAccountDetailsNew(Integer userId, String accountId, String startDate, String endDate, String transType, String pageNum, String inpDate, String inpTime, String relDate, String traceNo) {
        // 参数不正确
        if (StringUtils.isEmpty(accountId) || StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(transType)) {
            return null;
        }
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_OFFLINE_RECHARGE_DETAILS_QUERY);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 起始日期
        bean.setStartDate(startDate);
        // 结束日期
        bean.setEndDate(endDate);

        // 交易类型
        bean.setTranType(transType);
        // 翻页标识  空：首次查询；1：翻页查询；
        if (StringUtils.isNotEmpty(pageNum)&&!"1".equals(pageNum)) {
            bean.setRtnInd("1");
        } else {
            bean.setRtnInd("");
        }
        bean.setInpDate(inpDate);
        bean.setInpTime(inpTime);
        bean.setRelDate(relDate);
        bean.setTraceNo(traceNo);
        // 操作者ID
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 订单时间
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogRemark("admin线下充值明细查询");
        bean.setLogUserId(String.valueOf(userId));
        // 调用接口
        return BankCallUtils.callApiBg(bean);
    }

    /**
     * 开始对单个用户进行入账处理
     *
     * @param resultList
     */
    public void updateBankAccountCheck(List<ResultBean> resultList, Integer userId, String userName, String accountId, String ip, String payment, String cardId) {
        // 开始对单个用户进行入账处理
        logger.info("==============cwyang Start bankAccountCheck!=======");
        if (resultList.size() > 0) {
            for (int i = 0; i < resultList.size(); i++) {


                String bankSeqNo = null;
                try {
                    ResultBean bean = resultList.get(i);
                    // 如果江西银行不返回电子账户号  就设置本地的电子帐户号
                    if (bean.getAccountId() == null) {
                        bean.setAccountId(accountId);
                    }
                    String orFlage = bean.getOrFlag();
                    bankSeqNo = bean.getInpDate() + bean.getInpTime() + bean.getTraceNo() + "";
                    String describe = bean.getDescribe();
                    logger.info("============cwyang The bankseqNo is " + bankSeqNo);
                    logger.info("============cwyang The describe is " + describe);
                    AdminBankAccountCheckCustomizeVO customize = null;
                    if (StringUtils.isNotBlank(bankSeqNo) && ("O".equals(orFlage) || "0".equals(orFlage))) {
                        //判断是否是线下充值
                        boolean isType = getIsRechargeTransType(bean.getTranType());
                        if (isType) {
                            // 校验交易明细是否已经插入当前笔充值
                            AccountListExample accountListExample = new AccountListExample();
                            //因为使用银行接口（近两日线下充值明细查询）与原来的接口（近两日存管子账户资金交易明细查询）同一笔线下充值数据所返回的交易时间不一致，所以现在取消放重校验里面的交易时间校验
                            /*accountListExample.createCriteria().andTxDateEqualTo(Integer.parseInt(synBalanceBean.getInpDate())).andTxTimeEqualTo(Integer.parseInt(synBalanceBean.getInpTime()))
                            .andSeqNoEqualTo(synBalanceBean.getTraceNo() + "").andTypeEqualTo(CustomConstants.TYPE_IN)
                            .andBankSeqNoEqualTo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());*/


                            accountListExample.createCriteria().andTxDateEqualTo(Integer.parseInt(bean.getInpDate()))
                                    .andSeqNoEqualTo(bean.getTraceNo() + "").andTypeEqualTo(CustomConstants.TYPE_IN).andUserIdEqualTo(userId);

                            List<AccountList> accountLists = accountListMapper.selectByExample(accountListExample);

                            /*customize = this.adminBankAccountCheckCustomizeMapper.queryAccountDeatilByBankSeqNo(bankSeqNo);*/
                            if (accountLists != null && accountLists.size() != 0) {
                                // 该线下交易已对账,不再进行处理
                                logger.info("该笔线下充值已对账,不予处理!bankseqNo is " + bankSeqNo);
                            } else {
                                // 开始处理线下交易,将线下交易插入对应库表
                                updateOfflineTranscation(bean, userId, userName, bankSeqNo, ip, payment, cardId);
                            }
                        }
                    } else {
                        logger.error("The bankSeqNo is " + bankSeqNo);
                        logger.error("The orFlage is " + orFlage);
                        logger.error("=========线下充值对账异常==========");
                    }
                } catch (Exception e) {
                    logger.error("该笔对账异常! bankseqno is " + bankSeqNo);
                }
            }
        } else {
            logger.info("=========The resultSize is empty!=====");
        }
        logger.info("==============cwyang End bankAccountCheck!=======");
    }

    /**
     * 是否属于线下充值类型
     *
     * @param tranType
     * @return
     */
    private boolean isRechargeTransTypeOld(String tranType) {
        if (BankCallConstant.TRANS_TYPE_7610.equals(tranType) || BankCallConstant.TRANS_TYPE_7611.equals(tranType) || BankCallConstant.TRANS_TYPE_7612.equals(tranType)
                || BankCallConstant.TRANS_TYPE_7613.equals(tranType) || BankCallConstant.TRANS_TYPE_7617.equals(tranType) || BankCallConstant.TRANS_TYPE_7820.equals(tranType)
                || BankCallConstant.TRANS_TYPE_7821.equals(tranType) || BankCallConstant.TRANS_TYPE_7823.equals(tranType)
                || BankCallConstant.TRANS_TYPE_7826.equals(tranType) || BankCallConstant.TRANS_TYPE_7938.equals(tranType)
                || BankCallConstant.TRANS_TYPE_7939.equals(tranType)) {
            return true;
        }
        return false;
    }

    /**
     * 开始线下处理
     *
     * @param bean
     * @param userName
     */
    private void updateOfflineTranscation(ResultBean bean, int userId, String userName, String bankSeqNo, String ip, String payment, String cardId) throws Exception {
        // 当前时间
        Integer nowTime = GetDate.getNowTime10();
        Account account = this.getAccount(userId);
        logger.info("========TraceNo:" + bean.getTraceNo());
        logger.info("========InpDate:" + bean.getInpDate());
        logger.info("========InpDate:" + bean.getInpDate());
        logger.info("========TxAmount:" + bean.getTxAmount());

        if (account == null || account.getUserId() != userId) {
            return;
        }
        // 添加重复校验
        SynBalanceBean synBalanceBean = new SynBalanceBean();
        BeanUtils.copyProperties(bean, synBalanceBean);
        // 校验交易明细是否已经插入当前笔充值
        AccountListExample accountListExample = new AccountListExample();
        accountListExample.createCriteria().andTxDateEqualTo(Integer.parseInt(synBalanceBean.getInpDate())).andTxTimeEqualTo(Integer.parseInt(synBalanceBean.getInpTime()))
                .andSeqNoEqualTo(synBalanceBean.getTraceNo() + "").andTypeEqualTo(CustomConstants.TYPE_IN)
                .andBankSeqNoEqualTo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
        List<AccountList> accountLists = accountListMapper.selectByExample(accountListExample);
        if (accountLists != null && accountLists.size() != 0) {
            return;
        }
        // 校验充值信息是否已经插入当前笔充值
        AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
        accountRechargeExample.createCriteria().andTxDateEqualTo(Integer.parseInt(synBalanceBean.getInpDate())).andTxTimeEqualTo(Integer.parseInt(synBalanceBean.getInpTime()))
                .andSeqNoEqualTo(synBalanceBean.getTraceNo()).andBankSeqNoEqualTo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
        List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(accountRechargeExample);
        if (accountRecharges != null && accountRecharges.size() != 0) {
            return;
        }
        // 更新用户账户信息
        Account updateAccount = new Account();
        updateAccount.setUserId(account.getUserId());
        // 累加到账户总资产
        updateAccount.setBankTotal(synBalanceBean.getTxAmount());
        // 累加可用余额(江西账户)
        updateAccount.setBankBalance(synBalanceBean.getTxAmount());
        boolean isAccountUpdateFlag = adminAccountCustomizeMapper.updateOfSynBalance(updateAccount) > 0 ? true : false;
        if (!isAccountUpdateFlag) {
            logger.error("同步线下充值,更新用户账户信息失败~~~~,用户ID:" + account.getUserId());
        }
        // TODO 上市活动 是否需要保留
//        CommonSoaUtils.listedTwoRecharge(userId, synBalanceBean.getTxAmount());
        // 重新获取用户账户信息
        account = this.getAccount(userId);
        // 生成交易明细
        AccountList accountList = new AccountList();
        accountList.setNid(GetOrderIdUtils.getOrderId2(account.getUserId()));
        accountList.setUserId(account.getUserId());
        accountList.setAmount(synBalanceBean.getTxAmount());
        // 收入
        accountList.setType(CustomConstants.TYPE_IN);
        accountList.setTrade("recharge_offline");
        accountList.setTradeCode("balance");
        accountList.setAccountId(synBalanceBean.getAccountId());
        // 银行总资产
        accountList.setBankTotal(account.getBankTotal());
        // 银行可用余额
        accountList.setBankBalance(account.getBankBalance());
        // 银行冻结金额
        accountList.setBankFrost(account.getBankFrost());
        // 银行待还本金
        accountList.setBankWaitCapital(account.getBankWaitCapital());
        // 银行待还利息
        accountList.setBankWaitInterest(account.getBankWaitInterest());
        // 银行待收本金
        accountList.setBankAwaitCapital(account.getBankAwaitCapital());
        // 银行待收利息
        accountList.setBankAwaitInterest(account.getBankAwaitInterest());
        // 银行待收总额
        accountList.setBankAwait(account.getBankAwait());
        // 银行累计收益
        accountList.setBankInterestSum(account.getBankInterestSum());
        // 银行累计出借
        accountList.setBankInvestSum(account.getBankInvestSum());
        // 银行待还金额
        accountList.setBankWaitRepay(account.getBankWaitRepay());
        //汇计划账户可用余额
        accountList.setPlanBalance(account.getPlanBalance());
        accountList.setPlanFrost(account.getPlanFrost());
        accountList.setTotal(account.getTotal());
        accountList.setBalance(account.getBalance());
        accountList.setFrost(account.getFrost());
        accountList.setAwait(account.getAwait());
        accountList.setRepay(account.getRepay());
        accountList.setRemark("线下充值");
        accountList.setCreateTime(GetDate.getNowTime());
//        accountList.setBaseUpdate(GetDate.getNowTime10());
        accountList.setOperator(account.getUserId() + "");
//        accountList.setIsUpdate(0);
//        accountList.setBaseUpdate(0);
        // 新表是否删除？
//        accountList.setInterest(null);
        accountList.setWeb(0);
        accountList.setTxDate(Integer.parseInt(synBalanceBean.getInpDate()));
        accountList.setTxTime(Integer.parseInt(synBalanceBean.getInpTime()));
        accountList.setSeqNo(synBalanceBean.getTraceNo() + "");
        accountList.setBankSeqNo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
        // 资金托管平台 0:汇付,1:江西银行
        accountList.setIsBank(1);
        accountList.setTradeStatus(1);
        // 插入交易明细
        boolean accountListUpdateFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
        if (!accountListUpdateFlag) {
            throw new Exception("线下充值后,同步用户余额,插入交易明细失败~~,用户ID:" + userId);
        }
        AccountRecharge record = new AccountRecharge();
        // 订单号
        record.setNid(GetOrderIdUtils.getOrderId2(account.getUserId()));
        // 用户ID
        record.setUserId(account.getUserId());
        // 用户 名
        record.setUsername(userName);
        // 状态 0：失败；1：成功 2:充值中
        record.setStatus(2);
        // 金额
        record.setMoney(synBalanceBean.getTxAmount());
        // 银行卡号
        record.setCardid(cardId);
        // 电子账号
        record.setAccountId(account.getAccountId());
        // 手续费扣除方式
//        record.setFeeFrom(null);
        // 费用
        record.setFee(BigDecimal.ZERO);
        // 垫付费用
//        record.setDianfuFee(BigDecimal.ZERO);
        // 实际到账余额
        record.setBalance(synBalanceBean.getTxAmount());
        // 所属银行
        record.setPayment(payment);
        // 网关类型：QP快捷充值 B2C个人网银充值 B2B企业网银充值
        record.setGateType("OFFLINE");
        // 类型.1网上充值.0线下充值
        record.setType(0);
        // 备注
        record.setRemark("线下充值");
        record.setCreateTime(GetDate.getNowTime());
        record.setOperator(account.getUserId() + "");
//        record.setAddtime(String.valueOf(nowTime));
        // 操作平台 0pc 1WX 2AND 3IOS 4other
        record.setClient(0);
        // 资金托管平台 0:汇付,1:江西银行
        record.setIsBank(1);
        // 操作机器ip地址
        record.setAddIp(ip);
        record.setTxDate(Integer.parseInt(synBalanceBean.getInpDate()));
        record.setTxTime(Integer.parseInt(synBalanceBean.getInpTime()));
        record.setSeqNo(synBalanceBean.getTraceNo());
        record.setBankSeqNo(synBalanceBean.getInpDate() + synBalanceBean.getInpTime() + synBalanceBean.getTraceNo());
        // 插入用户充值记录表
        boolean isRechargeFlag = this.accountRechargeMapper.insertSelective(record) > 0 ? true : false;
        if (!isRechargeFlag) {
            throw new Exception("线下充值后,插入充值记录失败~~!用户ID:" + userId);
        }
    }

    /**
     * 根据用户ID获取开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public Account getAccount(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> listAccount = accountMapper.selectByExample(example);
        if (listAccount != null && listAccount.size() > 0) {
            return listAccount.get(0);
        }
        return null;
    }


    @Override
    public Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest) {
        // 部门
        if (Validator.isNotNull(bankAccountManageRequest.getCombotreeListSrch())) {

            String[] combotreeListSrch = bankAccountManageRequest.getCombotreeListSrch();
            if (Arrays.asList(combotreeListSrch).contains("-10086")) {

                //将-10086转换为 0 , 0=部门为 ‘其他’
                for (int i = 0; i < combotreeListSrch.length; i++) {
                    String st = combotreeListSrch[i];
                    if (("-10086").equals(st)) {
                        combotreeListSrch[i] = "0";
                    }
                }
            }
            bankAccountManageRequest.setCombotreeListSrch(combotreeListSrch);
        }
        Integer accountCount = null;

        // 为了优化检索查询，判断参数是否全为空，为空不进行带join count
        accountCount = adminBankAccountManageCustomizeMapper.queryAccountCount(bankAccountManageRequest);
        return accountCount;
    }

    /**
     * 账户管理页面查询列表
     *
     * @param bankAccountManageRequest
     * @return
     */
    @Override
    public List<BankAccountManageCustomize> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest) {
        // 部门
        if (Validator.isNotNull(bankAccountManageRequest.getCombotreeListSrch())) {

            String[] combotreeListSrch = bankAccountManageRequest.getCombotreeListSrch();
            if (Arrays.asList(combotreeListSrch).contains("-10086")) {

                //将-10086转换为 0 , 0=部门为 ‘其他’
                for (int i = 0; i < combotreeListSrch.length; i++) {
                    String st = combotreeListSrch[i];
                    if (("-10086").equals(st)) {
                        combotreeListSrch[i] = "0";
                    }
                }
            }
            bankAccountManageRequest.setCombotreeListSrch(combotreeListSrch);
        }

        List<BankAccountManageCustomize> accountInfos = adminBankAccountManageCustomizeMapper.queryAccountInfos(bankAccountManageRequest);
        return accountInfos;
    }

    /**
     * add by liushouyi 时间转换工具类
     * 根据type的类型进行转换
     * 转换成 对应格式的字符串时间
     * 1:yyyy-MM-dd 2:yyyyMMdd
     * @param date
     * @return
     */
    public static String getDateString(Date date,int type){
        String dateStr = "";
        if (date != null && type > 0) {
            SimpleDateFormat format = new SimpleDateFormat();
            if (1 == type) {
                format.applyPattern("yyyy-MM-dd");
            }else if(2 == type){
                format.applyPattern("yyyyMMdd");
            }else{
                return "";
            }
            dateStr = format.format(date);
        }
        return dateStr;
    }

    /**
     * add by liushouyi
     * 根据yyyy-mm-dd 格式的字符串转换成 date类型的日期
     * @param date
     * @return
     */
    public static Date getDateByString(String date){
        Date dated = null;
        if (date!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dated = format.parse(date);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return dated;
    }
    /**
     * add by nxl 更新account表的account_id
     * @param account
     * @return
     */
    @Override
    public Integer updAccountId(Account account){
        int intUpd =accountMapper.updateByPrimaryKeySelective(account);
        if(intUpd<=0){
            throw new RuntimeException("更新accountId失败,id为:"+account.getId());
        }
        return intUpd;
    }

}
