/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.dao.mapper.auto.CommissionLogMapper;
import com.hyjf.am.trade.dao.mapper.customize.PushMoneyCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.PushMoneyCustomize;
import com.hyjf.am.trade.service.admin.finance.PushMoneyManageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zdj
 * @version PushMoneyManageServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class PushMoneyManageServiceImpl extends BaseServiceImpl implements PushMoneyManageService {

    @Autowired
    private PushMoneyCustomizeMapper pushMoneyCustomizeMapper;
    @Autowired
    private CommissionLogMapper commissionLogMapper;
    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 提成管理 （列表）
     *
     * @return
     */
    @Override
    public List<PushMoneyCustomize> selectPushMoneyList(PushMoneyRequest request) {
        return pushMoneyCustomizeMapper.queryPushMoneyList(request);
    }

    /**
     * 提成管理 （列表条数）
     *
     * @return
     */
    @Override
    public int countPushMoney(PushMoneyRequest request) {
        return pushMoneyCustomizeMapper.queryPushMoneyCount(request);
    }

    /**
     * 提成列表count
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public int getPushMoneyListCount(PushMoneyRequest request) {
        return pushMoneyCustomizeMapper.queryPushMoneyDetailCount(request);
    }

    /**
     * 取得提成配置
     *
     * @param
     * @return
     * @auth zdj
     */
    @Override
    public List<PushMoney> getPushMoney(PushMoneyRequest request) {
        PushMoneyExample example = new PushMoneyExample();
        example.createCriteria().andProjectTypeEqualTo(1).andTypeEqualTo(request.getType());
        List<PushMoney> list = pushMoneyMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 提成列表list
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<PushMoneyCustomize> searchPushMoneyList(PushMoneyRequest request) {
        // 部门
/*        if (Validator.isNotNull(request.getCombotreeSrch())) {
            if (request.getCombotreeSrch().contains(StringPool.COMMA)) {
                String[] list = request.getCombotreeSrch().split(StringPool.COMMA);
                request.setCombotreeListSrch(list);
            } else {
                request.setCombotreeListSrch(new String[] { request.getCombotreeSrch() });
            }
        }*/
        return pushMoneyCustomizeMapper.queryPushMoneyDetail(request);
    }

    /**
     * 直投提成列表
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public Map<String, Object> queryPushMoneyTotle(PushMoneyRequest request) {
        return pushMoneyCustomizeMapper.queryPushMoneyTotle(request);
    }

    @Override
    public int queryCrmCuttype(Integer userId) {
        return pushMoneyCustomizeMapper.queryCrmCuttype(userId);
    }


    /**取得借款API表
     * @author Zha Daojian
     * @date 2018/10/24 19:33
     * @param borrowNid
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowApicron>
     **/
    @Override
    public List<BorrowApicron> selectBorrowApicronListByBorrowNid(String borrowNid) {
        BorrowApicronExample example = new BorrowApicronExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid).andApiTypeEqualTo(0);
        return this.borrowApicronMapper.selectByExample(example);
    }

    /**
     * 发提成
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public int updateTenderCommissionRecord(PushMoneyRequest request) {
        int ret = 0;
        TenderCommissionVO tenderCommissionVO = request.getTenderCommissionVO();

        BankCallBeanVO bankBean = request.getBankCallBeanVO();

        BankOpenAccountVO bankOpenAccountVO = request.getBankOpenAccountVO();

        if (bankBean != null) {
            // 增加时间
            Integer time = GetDate.getMyTimeInMillis();
            // 发放人ID
            Integer userId = tenderCommissionVO.getUserId();
            // 出借人ID
            Integer tenderUserId = tenderCommissionVO.getTenderUserId();
            // 操作者用户名
            String operator = request.getLoginUserName();

            // 更新发放状态
            tenderCommissionVO.setStatus(1);// 已发放
            tenderCommissionVO.setUpdateTime(GetDate.getNowTime());
            tenderCommissionVO.setSendTime(time);
            ret += tenderCommissionMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(tenderCommissionVO, TenderCommission.class));

            // 写入发放记录表
            CommissionLog commissionLog = new CommissionLog();
            commissionLog.setUserId(tenderUserId);
            commissionLog.setSpreadsUserId(userId);
            commissionLog.setNid(bankBean.getLogOrderId());

            commissionLog.setType("full");
            commissionLog.setSpreadsType("tender");
            commissionLog.setAccountType("capital");
            commissionLog.setScales(getScales(tenderCommissionVO.getBorrowNid(), userId).toString());
            commissionLog.setBorrowNid(tenderCommissionVO.getBorrowNid());
            commissionLog.setTenderId(tenderCommissionVO.getTenderId());
            commissionLog.setRepayId(0);
            commissionLog.setAccountAll(BigDecimal.ZERO);
            commissionLog.setAccountCapital(tenderCommissionVO.getAccountTender());
            commissionLog.setAccountInterest(BigDecimal.ZERO);
            commissionLog.setAccount(tenderCommissionVO.getCommission());
            commissionLog.setRemark("");
            commissionLog.setCreateTime(new Date());
            commissionLog.setCreateIp("");
            commissionLog.setPayStatus(1);
            commissionLog.setIsValid(1);
            ret += commissionLogMapper.insertSelective(commissionLog);

            // 更新账户信息
            AccountExample accountExample = new AccountExample();
            AccountExample.Criteria accountCriteria = accountExample.createCriteria();
            accountCriteria.andUserIdEqualTo(userId);
            Account account = accountMapper.selectByExample(accountExample).get(0);
            BigDecimal bankBalanceCash = account.getBankBalanceCash() == null ? BigDecimal.ZERO : account.getBankBalanceCash();
            BigDecimal money = new BigDecimal(bankBean.getTxAmount());// 提成
            account.setBankBalance(account.getBankBalance().add(money));
            account.setBankTotal(account.getBankTotal().add(money)); // 累加到账户总资产
            account.setBankBalanceCash(bankBalanceCash.add(money));

            ret += this.accountMapper.updateByExampleSelective(account, accountExample);

            // 写入收支明细
            AccountList accountList = new AccountList();
            accountList.setNid(bankBean.getLogOrderId());
            accountList.setSeqNo(bankBean.getSeqNo());
            accountList.setTxDate(Integer.parseInt(bankBean.getTxDate()));
            accountList.setTxTime(Integer.parseInt(bankBean.getTxTime()));
            accountList.setBankSeqNo(bankBean.getTxDate() + bankBean.getTxTime() + bankBean.getSeqNo());
            accountList.setCheckStatus(0);
            accountList.setTradeStatus(1);
            accountList.setUserId(userId);
            accountList.setAccountId(bankOpenAccountVO.getAccount());
            accountList.setAmount(money);
            accountList.setType(1);// 1收入2支出3冻结
            accountList.setTrade("borrow_spreads_tender");
            accountList.setTradeCode("balance");
            accountList.setBankTotal(account.getBankTotal()); // 银行总资产
            accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
            accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
            accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
            accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
            accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
            accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
            accountList.setBankAwait(account.getBankAwait());// 银行待收总额
            accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
            accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
            accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
            accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
            accountList.setPlanFrost(account.getPlanFrost());
            accountList.setTotal(account.getTotal());
            accountList.setBalance(account.getBalance());
            accountList.setFrost(account.getFrost());
            accountList.setAwait(account.getAwait());
            accountList.setRepay(account.getRepay());
            accountList.setRemark(tenderCommissionVO.getBorrowNid());
            accountList.setCreateTime(GetDate.getNowTime());
            accountList.setOperator(operator);
            accountList.setIp(bankBean.getLogIp());
/*        accountList.setIsUpdate(0);
        accountList.setBaseUpdate(0);
        accountList.setInterest(null);*/
            accountList.setWeb(2);
            //accountList.setIsBank(bankBean != null ? 1 : 0);
            accountList.setIsBank(1);
            ret += this.accountListMapper.insertSelective(accountList);

            // 插入网站收支明细记录
            AccountWebListVO accountWebList = new AccountWebListVO();
            accountWebList.setOrdid(bankBean.getLogOrderId());// 订单号
            accountWebList.setUserId(accountList.getUserId()); // 出借者
            accountWebList.setAmount(Double.valueOf(accountList.getAmount().toString())); // 管理费
            accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入 2支出
            accountWebList.setTrade(CustomConstants.TRADE_TGTC); // 提成
            accountWebList.setTradeType(CustomConstants.TRADE_TGTC_NM); // 出借推广提成
            accountWebList.setRemark(getBorrowNidByOrdId(accountList.getNid())); // 出借推广提成
            //accountWebList.setCreateTime(GetterUtil.getInteger(accountList.getCreateTime()));
            accountWebList.setCreateTime(GetDate.getTime10(accountList.getCreateTime()));
            try {
                logger.debug("发送收支明细:[{}]",JSON.toJSONString(accountWebList));
                boolean success = commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
                if(success){
                    ret += 1;
                }
            } catch (MQException e) {
                logger.error("发送MQ失败:" + e);
            }
            BankMerchantAccount nowBankMerchantAccount = this.getBankMerchantAccount(bankBean.getAccountId());
            nowBankMerchantAccount.setAvailableBalance(nowBankMerchantAccount.getAvailableBalance().subtract(money));
            nowBankMerchantAccount.setAccountBalance(nowBankMerchantAccount.getAccountBalance().subtract(money));
            nowBankMerchantAccount.setUpdateTime(GetDate.getNowTime());

            // 更新红包账户信息
            int updateCount = bankMerchantAccountMapper.updateByPrimaryKeySelective(nowBankMerchantAccount);
            if (updateCount > 0) {
                UserInfoCustomizeVO userInfoCustomize = pushMoneyCustomizeMapper.queryUserInfoByUserId(userId);

                // 添加红包明细
                BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
                bankMerchantAccountList.setOrderId(bankBean.getLogOrderId());
                bankMerchantAccountList.setBorrowNid(tenderCommissionVO.getBorrowNid());
                bankMerchantAccountList.setUserId(tenderCommissionVO.getUserId());
                bankMerchantAccountList.setAccountId(bankOpenAccountVO.getAccount());
                bankMerchantAccountList.setAmount(money);
                bankMerchantAccountList.setBankAccountCode(bankBean.getAccountId());
                bankMerchantAccountList.setBankAccountBalance(nowBankMerchantAccount.getAccountBalance());
                bankMerchantAccountList.setBankAccountFrost(nowBankMerchantAccount.getFrost());
                bankMerchantAccountList.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_AUTOMATIC);
                bankMerchantAccountList.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
                bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
                bankMerchantAccountList.setTxDate(Integer.parseInt(bankBean.getTxDate()));
                bankMerchantAccountList.setTxTime(Integer.parseInt(bankBean.getTxTime()));
                bankMerchantAccountList.setSeqNo(bankBean.getSeqNo());
                bankMerchantAccountList.setCreateTime(new Date());
                bankMerchantAccountList.setUpdateTime(new Date());
                bankMerchantAccountList.setUpdateTime(new Date());
                bankMerchantAccountList.setCreateUserId(userId);
                bankMerchantAccountList.setUpdateUserId(userId);
                bankMerchantAccountList.setRemark("出借推广提成");
                if(userInfoCustomize != null){
                    bankMerchantAccountList.setRegionName(StringUtils.isBlank(userInfoCustomize.getRegionName())?"":userInfoCustomize.getRegionName());
                    bankMerchantAccountList.setBranchName(StringUtils.isBlank(userInfoCustomize.getBranchName())?"":userInfoCustomize.getBranchName());
                    bankMerchantAccountList.setDepartmentName(StringUtils.isBlank(userInfoCustomize.getDepartmentName())?"":userInfoCustomize.getDepartmentName());
                    bankMerchantAccountList.setCreateUserName(StringUtils.isBlank(userInfoCustomize.getUserName())?"":userInfoCustomize.getUserName());
                    bankMerchantAccountList.setUpdateUserName(StringUtils.isBlank(userInfoCustomize.getUserName())?"":userInfoCustomize.getUserName());
                }else{
                    bankMerchantAccountList.setRegionName("");
                    bankMerchantAccountList.setBranchName("");
                    bankMerchantAccountList.setDepartmentName("");
                    bankMerchantAccountList.setCreateUserName("");
                    bankMerchantAccountList.setUpdateUserName("");
                }
                this.bankMerchantAccountListMapper.insertSelective(bankMerchantAccountList);
            }


            // 纯发短信接口
            Map<String, String> replaceMap = new HashMap<String, String>();
            replaceMap.put("val_amount", tenderCommissionVO.getCommission().toString());
            SmsMessage smsMessage =
                    new SmsMessage(userId, replaceMap, null, null, MessageConstant.SMS_SEND_FOR_USER, null,
                            CustomConstants.PARAM_TPL_SDTGTC, CustomConstants.CHANNEL_TYPE_NORMAL);
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
                        smsMessage));
            } catch (MQException e) {
                logger.error(e.getMessage());
            }
            //
            UserInfoCustomizeVO userInfo = pushMoneyCustomizeMapper.queryUserInfoByUserId(userId);
            if (userInfo != null) {
                Map<String, String> param = new HashMap<String, String>();
                if (userInfo.getTrueName() != null && userInfo.getTrueName().length() > 1) {
                    param.put("val_name", userInfo.getTrueName().substring(0, 1));
                } else {
                    param.put("val_name", userInfo.getTrueName());
                }
                if ("1".equals(userInfo.getSex())) {
                    param.put("val_sex", "先生");
                } else if ("2".equals(userInfo.getSex())) {
                    param.put("val_sex", "女士");
                } else {
                    param.put("val_sex", "");
                }
                param.put("val_amount", tenderCommissionVO.getCommission().toString());
                AppMsMessage appMsMessage = new AppMsMessage(null, param, userInfo.getMobile(), MessageConstant.APP_MS_SEND_FOR_MOBILE,
                        CustomConstants.JYTZ_TPL_SDTGTC);

                try {
                    commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(userId),
                            appMsMessage));
                } catch (MQException e) {
                    logger.error("发送app消息失败..", e);
                }
            }

        }

        return ret;
    }

    /**
     * 取得提成利率
     *
     * @param borrowNid
     * @param userId
     */
    private BigDecimal getScales(String borrowNid, Integer userId) {
        BigDecimal rate = BigDecimal.ZERO;

        if (Validator.isNotNull(borrowNid) && Validator.isNotNull(userId)) {
            // 取得借款数据
            String borrowStyle = null;
            BorrowExample example = new BorrowExample();
            example.createCriteria().andBorrowNidEqualTo(borrowNid);
            List<Borrow> borrowList = this.borrowMapper.selectByExample(example);
            if (borrowList != null && borrowList.size() > 0) {
                borrowStyle = borrowList.get(0).getBorrowStyle();
            }

            UserInfoCustomizeVO usersInfo = pushMoneyCustomizeMapper.queryUserInfoByUserId(userId);
            if (usersInfo != null) {
                String type = "";
                // 提成发放人时线上用户或51老用户
                if (usersInfo.getAttribute() == 3) {
                    type = "线上用户";
                }/* else if (usersInfo.getIs51() == 1) {
                    type = "51老用户";
                }*/

                // 取得提成利率
                PushMoney pushMoney = getPushMoney(type);
                if (pushMoney != null && NumberUtils.isNumber(pushMoney.getDayTender())) {
                    if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                        rate = new BigDecimal(pushMoney.getDayTender());
                    } else {
                        rate = new BigDecimal(pushMoney.getMonthTender());
                    }
                }
            }
        }
        return rate;
    }

    /**
     * 根据出借订单号取出借编号
     *
     * @param ordId
     * @return
     */
    private String getBorrowNidByOrdId(String ordId) {
        BorrowTenderExample example = new BorrowTenderExample();
        example.createCriteria().andNidEqualTo(ordId);
        List<BorrowTender> list = this.borrowTenderMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0).getBorrowNid();
        }
        return null;
    }

    /**
     * 加载红包账户
     *
     * @param accountCode
     * @return
     */
    public BankMerchantAccount getBankMerchantAccount(String accountCode) {
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
     * 取得提成配置
     *
     * @param type
     * @return
     */
    private PushMoney getPushMoney(String type) {
        PushMoneyExample example = new PushMoneyExample();
        example.createCriteria().andProjectTypeEqualTo(1).andTypeEqualTo(type);
        List<PushMoney> list = this.pushMoneyMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
