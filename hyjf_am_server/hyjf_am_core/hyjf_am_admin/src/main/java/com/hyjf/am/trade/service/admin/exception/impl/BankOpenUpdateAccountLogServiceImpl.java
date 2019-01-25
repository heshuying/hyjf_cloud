package com.hyjf.am.trade.service.admin.exception.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.admin.config.SystemConfig;
import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.response.user.OpenAccountEnquiryResponse;
import com.hyjf.am.resquest.admin.OpenAccountEnquiryDefineRequest;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.trade.service.admin.account.AccountDetailService;
import com.hyjf.am.trade.service.admin.exception.BankOpenUpdateAccountLogService;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;
import com.hyjf.am.user.service.admin.exception.BankOpenAccountLogService;
import com.hyjf.am.user.service.admin.membercentre.UserManagerService;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.user.service.front.user.UserInfoService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.OpenAccountEnquiryDefineResultBeanVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @version BankOpenAccountLogSrviceImpl, v0.1 2018/8/21 14:41
 * @Author: Zha Daojian
 */
@Service
public class BankOpenUpdateAccountLogServiceImpl extends BaseServiceImpl implements BankOpenUpdateAccountLogService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private UserManagerService userService;


    @Autowired
    private AccountDetailService accountDetailService;

    @Autowired
    private AppUtmRegService appUtmRegService;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 保存开户掉单Account数据
     * @param requestBean
     * @return
     */
    public OpenAccountEnquiryResponse updateAccount(OpenAccountEnquiryDefineRequest requestBean) {
        OpenAccountEnquiryDefineResultBeanVO resultBean= new OpenAccountEnquiryDefineResultBeanVO();
        OpenAccountEnquiryResponse response =  new OpenAccountEnquiryResponse();
        Integer userId = Integer.parseInt(requestBean.getUserid());
        // 更新trade  的  account表的电子帐户号
        boolean tradeAccountFlag = accountDetailService.updateAccountNumberByUserId(userId,requestBean.getAccountId()) > 0 ? true : false;
        logger.info("开户掉单  更新 trade 的account 结果{}",tradeAccountFlag);

        // add by liuyang 20180227 开户掉单处理成功之后 发送法大大CA认证MQ  start
        // 加入到消息队列
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("userId", String.valueOf(userId));
        try {
            logger.info("开户异步处理，发送MQ，userId:[{}],mqMgId:[{}]",userId,params.get("mqMsgId"));

            commonProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(),params));
        } catch (Exception e) {
            logger.error("开户掉单处理成功之后 发送法大大CA认证MQ消息失败！userId:[{}]",userId);
        }
        resultBean.setStatus(BankCallConstant.BANKOPEN_USER_ACCOUNT_Y);
        resultBean.setResult("开户掉单同步成功!");
        response.setOpenAccountEnquiryDefineResultBeanVO(resultBean);
        return response;
    }
}
