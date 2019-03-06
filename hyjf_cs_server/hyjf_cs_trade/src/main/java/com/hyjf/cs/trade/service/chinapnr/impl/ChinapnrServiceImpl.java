/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.chinapnr.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.trade.ChinapnrLogVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.AccountBankVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.BankCardBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.chinapnr.ChinapnrService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version ChinapnrServiceImpl, v0.1 2018/9/5 15:27
 */
@Service
public class ChinapnrServiceImpl extends BaseTradeServiceImpl implements ChinapnrService {

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmConfigClient amConfigClient;

    @Autowired
    SystemConfig systemConfig;

    @Override
    public Map<String, Object> toWithdraw(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        // 取得用户当前余额
        AccountVO account = this.getAccountByUserId(userId);
        CheckUtil.check(account != null, MsgEnum.ERR_USER_UNUSUAL);
        // 可提现金额
        map.put("total", CustomConstants.DF_FOR_VIEW.format(account.getBalance()));
        // 查询页面上可以挂载的银行列表
        List<AccountBankVO> banks = amUserClient.getAccountBankByUserId(userId);
        List<BankCardBean> bankcards = new ArrayList<BankCardBean>();
        // 是否有快捷提现卡
        // 是否绑了卡
        Boolean hasBindCard = false;
        // 是否绑了默认提现卡
        Boolean hasBindDefault = false;
        // 是否绑了快捷提现卡
        Boolean hasBindQuick = false;
        // 快捷提现卡卡号
        String defaultCardId = "";
        // 快捷提现卡卡号
        String quickCardId = "";
        if (banks != null && banks.size() != 0) {
            // 是否绑了卡
            hasBindCard = true;
            for (AccountBankVO bank : banks) {
                BankCardBean bankCardBean = new BankCardBean();
                bankCardBean.setId(bank.getId());
                bankCardBean.setBank(bank.getBank());
                BankConfigVO bankConfig = amConfigClient.selectBankConfigByCode(bank.getBank());
                // 应前台要求，logo路径给绝对路径
                bankCardBean.setLogo(systemConfig.getFrontHost()+ bankConfig.getAppLogo());
                // 银行名称 汉字
                bankCardBean.setBank(bankConfig.getName());
                bankCardBean.setCardNo(bank.getAccount());
                /* 卡类型 0普通提现卡1默认卡2快捷支付卡 */
                bankCardBean.setIsDefault(bank.getCardType());
                //判断是否绑定快捷支付卡
                if (bank.getCardType().equals("2")) {
                    hasBindQuick = true;
                    quickCardId = String.valueOf(bank.getUserId());
                    bankcards = new ArrayList<BankCardBean>();
                    bankcards.add(bankCardBean);
                    break;
                } else if (bank.getCardType().equals("1")) {
                    hasBindDefault = true;
                    defaultCardId = String.valueOf(bank.getId());
                }
                bankcards.add(bankCardBean);
            }
        } else {
            // 是否绑了卡
            hasBindCard = false;
        }
        // 取用户信息
        UserVO user = this.getUserByUserId(userId);
       // 是否为企业用户（0普通用户1企业用户企业用户不可绑定其他取现卡）
        map.put("userType", user.getUserType());
        // 是否绑了卡
        map.put("hasBindCard", hasBindCard);
        // 是否绑了快捷支付卡
       map.put("hasBindDefault", hasBindDefault);
        // 是否绑了快捷提现卡
       map.put("hasBindQuick", hasBindQuick);
        // 默认提现卡卡号
       map.put("defaultCardId", defaultCardId);
        // 快捷提现卡卡号
       map.put("quickCardId", quickCardId);
       map.put("banks", bankcards);
       return map;
    }

    @Override
    public void checkParam(Integer userId, String transAmtStr, String bankId) {
        // 检查参数(交易金额是否数字)
        CheckUtil.check(Validator.isNotNull(transAmtStr)&& NumberUtils.isNumber(transAmtStr),MsgEnum.ERR_AMT_WITHDRAW_AMOUNT);
        // 检查参数(交易金额是否大于0)
        BigDecimal transAmt = new BigDecimal(transAmtStr);
        CheckUtil.check(transAmt.compareTo(BigDecimal.ONE) > 0,MsgEnum.ERR_AMT_WITHDRAW_AMOUNT_GREATER_THAN_ONE);
        // 取得用户当前余额
        AccountVO account = this.getAccountByUserId(userId);
        // 投标金额大于可用余额
        CheckUtil.check(account != null&& transAmt.compareTo(account.getBalance()) <= 0,MsgEnum.STATUS_CE000015);
        // 检查参数(银行卡ID是否数字)
        CheckUtil.check(Validator.isNotNull(bankId) && StringUtils.isNumeric(bankId),MsgEnum.ERR_AMT_WITHDRAW_CARD);
    }

    @Override
    public Map<String, Object> cash(Integer userId, String transAmt, String bankId, String cashchl,String userName,String ip) {
        // 取得用户在汇付天下的客户号
        AccountChinapnrVO account = amUserClient.getAccountChinapnr(userId);
        CheckUtil.check(account!=null&&StringUtils.isNotBlank(account.getChinapnrUsrid()),MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        Long chinapnrUsrcustidTender = account.getChinapnrUsrcustid();
        // 取现渠道(FAST:快速取现 , GENERAL:一般取现 , IMMEDIATE:即时取现)
        if (Validator.isNull(cashchl)) {
            cashchl = "GENERAL";
        }
        // 取得银行卡号
        AccountBankVO accountBank = amUserClient.getBankInfo(userId, GetterUtil.getInteger(bankId));
        if (accountBank == null || Validator.isNull(accountBank.getAccount())) {
            throw new CheckException(MsgEnum.ERR_CARD_NOT_BIND);
        }
        // 取得手续费
        String fee = getWithdrawFee(accountBank.getBank());
        // 实际取现金额(洪刚提示跟线上保持一致)
        // 不去掉一块钱手续费
        if (new BigDecimal(transAmt).compareTo(BigDecimal.ZERO) == 0) {
            //提现金额需要大于1元
            throw new CheckException(MsgEnum.ERR_AMT_WITHDRAW_AMOUNT_GREATER_THAN_ONE);
        }
        // 入参扩展域
        JSONArray reqExt = new JSONArray();
        JSONObject reqExtObj = new JSONObject();
        reqExtObj.put("CashFeeDeductType", "I");
        reqExt.add(reqExtObj);
        // 调用汇付接口(提现)
        // 支付工程路径
        String retUrl = systemConfig.getFrontHost()+"/user/withdrawHfDeal";
        String bgRetUrl = "http://CS-TRADE/hyjf-web/chinapnr/withdraw/callback";
        ChinapnrBean bean = new ChinapnrBean();
        // 接口版本号
        bean.setVersion(ChinaPnrConstant.VERSION_20);
        // 消息类型(提现)
        bean.setCmdId(ChinaPnrConstant.CMDID_CASH);
        // 订单号(必须)
        bean.setOrdId(GetOrderIdUtils.getOrderId2(Integer.valueOf(userId)));
        // 用户客户号
        bean.setUsrCustId(String.valueOf(chinapnrUsrcustidTender));
        // 交易金额(必须)
        bean.setTransAmt(CustomUtil.formatAmount(transAmt));
        // 开户银行账号
        bean.setOpenAcctId((accountBank != null && org.apache.commons.lang.StringUtils.isNotBlank(accountBank.getAccount())) ? accountBank.getAccount() : "");
        // 页面返回 URL
        bean.setRetUrl(retUrl);
        // 商户后台应答地址(必须)
        bean.setBgRetUrl(bgRetUrl);
        // 日志类型(写日志用)
        bean.setType("user_cash");
        bean.setReqExt(reqExt.toJSONString());
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(userId));
        params.put("userName", userName);
        params.put("ip",ip);
        params.put("bankId", bankId);
        params.put("fee", CustomUtil.formatAmount(fee));
        params.put("client", "0");
        // 用户提现前处理
        boolean withdrawFlag = updateBeforeCash(bean, params,accountBank);
        if (withdrawFlag) {
            // 跳转到汇付天下画面
            try {
                Map<String, Object> map = ChinapnrUtil.callApiMap(bean);
                return map;
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            throw new CheckException("1","请不要重复操作");
        }
        return null;
    }

    @Override
    public ChinapnrExclusiveLogWithBLOBsVO selectChinapnrExclusiveLog(long id) {
        return csMessageClient.selectChinapnrExclusiveLog(id);
    }

    @Override
    public int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record) {
        return csMessageClient.updateChinapnrExclusiveLog(record);
    }

    @Override
    public String checkCashResult(String ordId) {
        if (Validator.isNull(ordId)) {
            return null;
        }
        // 调用汇付接口(交易状态查询)
        ChinapnrBean bean = new ChinapnrBean();
        // 版本号(必须)
        bean.setVersion(ChinaPnrConstant.VERSION_10);
        // 消息类型(必须)
        bean.setCmdId(ChinaPnrConstant.CMDID_QUERYTRANSSTAT);
        // 订单号(必须)
        bean.setOrdId(ordId);
        // 订单日期(必须)
        bean.setOrdDate(GetOrderIdUtils.getOrderDate());
        // 交易查询类型
        bean.setQueryTransType("CASH");
        // 写log用参数
        bean.setLogUserId(0);
        // 备注
        bean.setLogRemark("交易状态查询(取现)");
        // PC
        bean.setLogClient("0");
        // 调用汇付接口
        ChinapnrBean chinapnrBean = ChinapnrUtil.callApiBg(bean);
        if (chinapnrBean != null) {
            return chinapnrBean.getTransStat();
        }
        return null;
    }

    @Override
    public JSONObject getMsgData(String ordId) {
        if (Validator.isNull(ordId)) {
            return null;
        }

        List<String> respCode = new ArrayList<String>();
        respCode.add(ChinaPnrConstant.RESPCODE_SUCCESS);
        respCode.add(ChinaPnrConstant.RESPCODE_CHECK);
        List<ChinapnrLogVO> list =  csMessageClient.getChinapnrLog(ordId);
        if (list != null && list.size() > 0) {
            String msgData = list.get(0).getMsgdata();
            if (Validator.isNotNull(msgData)) {
                try {
                    JSONObject jo = JSONObject.parseObject(msgData);
                    return jo;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public Integer selectUserIdByUsrcustid(Long chinapnrUsrcustid) {
        return amUserClient.selectUserIdByUsrcustid(chinapnrUsrcustid);
    }

    @Override
    public synchronized boolean handlerAfterCash(ChinaPnrWithdrawRequest chinaPnrWithdrawRequest) {
        return amTradeClient.handlerAfterCash(chinaPnrWithdrawRequest);
    }

    @Override
    public void updateAccountWithdrawByOrdId(String ordId, String reason) {
        amTradeClient.updateAccountWithdrawByOrdId(ordId,reason);
    }

    @Override
    public void updateChinapnrExclusiveLogStatus(long uuid, String status) {
        csMessageClient.updateChinapnrExclusiveLogStatus(uuid,status);
    }

    private boolean updateBeforeCash(ChinapnrBean bean, Map<String, String> params, AccountBankVO accountBank) {
        // 订单号
        String ordId = bean.getOrdId() == null ? bean.get(ChinaPnrConstant.PARAM_ORDID) : bean.getOrdId();
        int count = amTradeClient.countAccountWithdraw(ordId);
        if (count > 0) {
            throw new RuntimeException("提现订单号重复，订单号：" + ordId);
        }
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        // 提现金额
        BigDecimal money = new BigDecimal(bean.getTransAmt());
        // 取得费率
        BigDecimal fee = BigDecimal.ZERO;
        // 实际出账金额
        BigDecimal total = money.add(fee);
        // 用户ID
        Integer userId = GetterUtil.getInteger(params.get("userId"));
        // 银行卡ID
        Integer bankId = GetterUtil.getInteger(params.get("bankId"));
        String bank = null;
        String account = null;
        // 取得银行信息
        if (accountBank != null) {
            bank = accountBank.getBank();
            account = accountBank.getAccount();
        }
        AccountWithdrawVO record = new AccountWithdrawVO();
        record.setUserId(userId);
        // 订单号
        record.setNid(bean.getOrdId());
        // 状态: 0:处理中
        record.setStatus(0);
        // 提现银行卡
        record.setAccount(account);
        // 提现银行
        record.setBank(bank);
        record.setBankId(bankId);
//        record.setBranch(null);
//        record.setProvince(0);
//        record.setCity(0);
        record.setTotal(total);
        record.setCredited(money);
        record.setBankFlag(0);
        record.setFee(BigDecimal.ZERO.toString());
//        record.setAddtime(String.valueOf(nowTime));
        record.setAddIp(params.get("ip"));
        record.setRemark("网站提现");
        // 0pc
        record.setClient(GetterUtil.getInteger(params.get("client")));
        // 提现类型 0主动提现  1代提现
        record.setWithdrawType(0);
        // 插入用户提现记录表
        boolean withdrawFlag = amTradeClient.insertAccountWithdrawLog(record) > 0 ? true : false;
        return withdrawFlag;
    }

    private String getWithdrawFee(String bankCode) {
        // 取得费率
        List<FeeConfigVO> listFeeConfig = this.amConfigClient.getFeeConfig(bankCode == null ? "" : bankCode);
            if (listFeeConfig != null && listFeeConfig.size() > 0) {
                FeeConfigVO feeConfig = listFeeConfig.get(0);
                BigDecimal takout = BigDecimal.ZERO;
                BigDecimal percent = BigDecimal.ZERO;
                if (Validator.isNotNull(feeConfig.getNormalTakeout()) && NumberUtils.isNumber(feeConfig.getNormalTakeout())) {
                    takout = new BigDecimal(feeConfig.getNormalTakeout());
                }
                return takout.add(percent).toString();
            }
        return null;
    }

}
