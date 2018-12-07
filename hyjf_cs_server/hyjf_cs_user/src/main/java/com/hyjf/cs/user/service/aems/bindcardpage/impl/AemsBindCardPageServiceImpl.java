package com.hyjf.cs.user.service.aems.bindcardpage.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.user.bean.AemsBindCardPageRequestBean;
import com.hyjf.cs.user.bean.BindCardPageBean;
import com.hyjf.cs.user.service.aems.bindcardpage.AemsBindCardPageService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户绑卡Serice实现类
 * 
 * @author liuyang
 *
 */
@Service
public class AemsBindCardPageServiceImpl extends BaseUserServiceImpl implements AemsBindCardPageService {
	// 声明log日志
		//private Logger logger = LoggerFactory.getLogger(AemsBindCardPageServiceImpl.class);
    /**
     * 绑卡接口请求成功后业务处理
     * @param bean
     * @throws ParseException
     */
    @Override
    public void updateAfterBindCard(BankCallBean bean) throws ParseException {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户信息
        UserVO userVO = amUserClient.findUserById(userId);
        // 获取用户已绑卡数量
        int count = amUserClient.countUserCardValid(bean.getLogUserId());
        // 如果已经有绑卡则不做操作
        if(count > 0) {
            return;
        }

        String cardNo = "";

        // 同步银行卡信息
        BankCallBean callBean = new BankCallBean();
        callBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        callBean.setTxCode(BankCallConstant.TXCODE_CARD_BIND_DETAILS_QUERY);
        callBean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        callBean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        callBean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        callBean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        callBean.setAccountId(bean.getAccountId());// 存管平台分配的账号
        callBean.setState("1"); // 查询状态 0-所有（默认） 1-当前有效的绑定卡
        callBean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.parseInt(bean.getLogUserId())));
        callBean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        callBean.setLogRemark("绑卡关系查询");
        callBean.setLogUserId(String.valueOf(bean.getLogUserId()));
        // 调用汇付接口 4.4.11 银行卡查询接口
        BankCallBean bankCallBean = BankCallUtils.callApiBg(callBean);
        String respCode = bankCallBean == null ? "" : bankCallBean.getRetCode();
        // 如果接口调用成功
        if (BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
            String usrCardInfolist = bankCallBean.getSubPacks();
            JSONArray array = JSONObject.parseArray(usrCardInfolist);
            if (array != null && array.size() != 0) {
                List<BankCardRequest> bankCardList = new ArrayList<BankCardRequest>();
                for (int j = 0; j < array.size(); j++) {
                    JSONObject obj = array.getJSONObject(j);
                    cardNo = obj.getString("cardNo");
                    // 根据银行卡号查询银行ID
                    String bankId = amConfigClient.queryBankIdByCardNo(cardNo);
                    if(bankId == null) {
                        bankId = "0";
                    }
                    logger.info("根据银行卡号查询出的bankId：" + bankId);
                    // 查询银行配置信息
                    JxBankConfigVO bankConfig = amConfigClient.getJxBankConfigById(Integer.parseInt(bankId));

                    BankCardRequest bank = new BankCardRequest();
                    bank.setUserId(userId);
                    bank.setUserName(userVO.getUsername());
                    bank.setStatus(1);// 默认都是1
                    bank.setCardNo(cardNo);
                    bank.setBankId(bankId == null ? 0 : Integer.valueOf(bankId));
                    if(bankConfig != null){
                        bank.setBank(bankConfig.getBankName());
                    }
                    // 银行联号
                    String payAllianceCode = "";
                    // 调用江西银行接口查询银行联号
                    BankCallBean payAllianceCodeQueryBean = this.payAllianceCodeQuery(cardNo, userId);
                    if (payAllianceCodeQueryBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(payAllianceCodeQueryBean.getRetCode())) {
                        payAllianceCode = payAllianceCodeQueryBean.getPayAllianceCode();
                    }
                    // 如果此时银联行号还是为空,根据bankId查询本地存的银联行号
                    if (StringUtils.isBlank(payAllianceCode) && bankConfig != null) {
                        payAllianceCode = bankConfig.getPayAllianceCode();
                    }
                    bank.setPayAllianceCode(payAllianceCode);
                    SimpleDateFormat sdf = GetDate.yyyymmddhhmmss;
                    bank.setCreateTime(sdf.parse(obj.getString("txnDate") + obj.getString("txnTime")));
                    bank.setCreateUserId(userId);
                    bank.setCreateUsername(userVO.getUsername());
                    bankCardList.add(bank);
                }

                for (BankCardRequest bank : bankCardList) {
                    boolean isInsertFlag = amUserClient.insertUserCard(bank) > 0 ? true : false;
                    if (!isInsertFlag) {
                        throw new CheckException(MsgEnum.ERR_CARD_SAVE);
                    }
                }
            }
        }

        // 插入操作记录表
        String bankId = amConfigClient.queryBankIdByCardNo(cardNo);
        if(bankId == null) {
            bankId = "0";
        }
        // 查询银行配置信息
        JxBankConfigVO bankConfig = amConfigClient.getJxBankConfigById(Integer.parseInt(bankId));

        BankCardLogRequest bankCardLogRequest = new BankCardLogRequest();
        bankCardLogRequest.setUserId(userId);
        bankCardLogRequest.setUserName(userVO.getUsername());
        bankCardLogRequest.setBankCode(String.valueOf(bankId));
        bankCardLogRequest.setCardNo(cardNo);
        if(bankConfig != null){
            bankCardLogRequest.setBankName(bankConfig.getBankName());
        }
        bankCardLogRequest.setCardType(0);// 卡类型 0普通提现卡1默认卡2快捷支付卡
        bankCardLogRequest.setOperationType(0);// 操作类型 0绑定 1删除
        bankCardLogRequest.setStatus(0);// 成功
        bankCardLogRequest.setCreateTime(GetDate.getNowTime());// 操作时间
        boolean isUpdateFlag = amUserClient.insertBindCardLog(bankCardLogRequest) > 0 ? true : false;
        if (!isUpdateFlag) {
            throw new CheckException(MsgEnum.ERR_CARD_SAVE);
        }
        BankCardVO retCard = amUserClient.queryUserCardValid(String.valueOf(userId), cardNo);
        if (retCard != null && StringUtils.isNotBlank(bean.getMobile())) {
            BankCardRequest bankCard=new BankCardRequest();
            bankCard.setId(retCard.getId());
            bankCard.setMobile(bean.getMobile());
            amUserClient.updateUserCard(bankCard);
        }

    }

    /***
     * 调用江西银行
    * @author Zha Daojian
    * @date 2018/12/6 15:32
    * @param bean
    * @return org.springframework.web.servlet.ModelAndView
    **/
    @Override
    public ModelAndView getCallbankMV(BindCardPageBean bean) {
        ModelAndView mv = new ModelAndView();
        // 获取共同参数
        String orderDate = GetOrderIdUtils.getOrderDate();
        String idType = BankCallConstant.ID_TYPE_IDCARD;

        // 调用开户接口
        BankCallBean bindCardBean = new BankCallBean();
        bindCardBean.setTxCode(bean.getTxCode());// 消息类型(用户开户)
        bindCardBean.setIdType(idType);
        bindCardBean.setIdNo(bean.getIdNo());
        bindCardBean.setName(bean.getName());
        bindCardBean.setAccountId(bean.getAccountId());
        bindCardBean.setUserIP(bean.getUserIP());
        bindCardBean.setRetUrl(bean.getRetUrl());
        bindCardBean.setSuccessfulUrl(bean.getSuccessfulUrl());
        bindCardBean.setForgotPwdUrl(bean.getForgetPassworedUrl());
        bindCardBean.setNotifyUrl(bean.getNotifyUrl());
        // 页面调用必须传的
        String orderId = GetOrderIdUtils.getOrderId2(bean.getUserId());
        bindCardBean.setLogBankDetailUrl(BankCallConstant.BANK_URL_BIND_CARD_PAGE);
        bindCardBean.setLogOrderId(orderId);
        bindCardBean.setLogOrderDate(orderDate);
        bindCardBean.setLogUserId(String.valueOf(bean.getUserId()));
        bindCardBean.setLogRemark("外部服务接口:绑卡页面");
        bindCardBean.setLogIp(bean.getUserIP());
        bindCardBean.setLogClient(Integer.parseInt(bean.getPlatform()));
        try {
            mv = BankCallUtils.callApi(bindCardBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 绑卡校验API端
     * @param bankCardRequestBean
     * @return
     */
    @Override
    public Map<String,String> checkParamBindCardPageApi(AemsBindCardPageRequestBean bankCardRequestBean) {
        Map<String,String> resultMap = new HashMap<>();
        BankOpenAccountVO openAccountVO = amUserClient.selectBankOpenAccountByAccountId(bankCardRequestBean.getAccountId());
        if(openAccountVO == null){
            resultMap.put("key","CE000004");
            resultMap.put("msg","没有根据电子银行卡找到用户");
            logger.info("没有根据电子银行卡找到用户");
            return resultMap;
        }
        UserVO userVO = this.getUsersById(openAccountVO.getUserId());
        if(userVO.getIsSetPassword() != 1){
            resultMap.put("key","TP000002");
            resultMap.put("msg","用户未设置交易密码");
            logger.info("用户未设置交易密码");
            return resultMap;
        }
        int count = amUserClient.countUserCardValid(String.valueOf(userVO.getUserId()));
        if(count > 0){
            resultMap.put("key","BC000001");
            resultMap.put("msg","用户已绑定银行卡,请先解除绑定,然后重新操作");
            logger.info("用户已绑定银行卡,请先解除绑定,然后重新操作");
            return resultMap;
        }

        return resultMap;
    }

    /**
     * 银联行号查询
     * @param cardNo
     * @param userId
     * @return
     */
    private BankCallBean payAllianceCodeQuery(String cardNo,Integer userId) {
        BankCallBean bean = new BankCallBean();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(cardNo);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(orderDate);
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("联行号查询");
        bean.setLogClient(0);
        return BankCallUtils.callApiBg(bean);
    }

}
