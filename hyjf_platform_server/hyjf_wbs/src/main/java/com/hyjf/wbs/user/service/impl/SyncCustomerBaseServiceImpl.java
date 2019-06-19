/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.hyjf.wbs.qvo.csuser.ResultEnum;
import com.hyjf.wbs.user.dao.model.customize.BankOpenAccountRecordCustomize;
import com.hyjf.wbs.user.service.BankOpenRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.common.exception.CheckException;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.qvo.CustomerSyncQO;
import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.service.AccountService;
import com.hyjf.wbs.user.dao.model.auto.BankCard;
import com.hyjf.wbs.user.dao.model.auto.User;
import com.hyjf.wbs.user.service.SyncCustomerBaseService;
import com.hyjf.wbs.user.service.UserService;

/**
 * @author cui
 * @version SyncCustomerBaseServiceImpl, v0.1 2019/4/30 14:26
 */
@Service
public class SyncCustomerBaseServiceImpl implements SyncCustomerBaseService {

    private Logger logger=LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankOpenRecordService bankOpenRecordService;

    @Autowired
    private WbsConfig wbsConfig;

    private final int ACCOUNT_OPENED = 1;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public CustomerSyncQO build(WbsRegisterMqVO wbsRegisterMqVO) {
        String userId = wbsRegisterMqVO.getAssetCustomerId();
        String thirdpartyId = wbsRegisterMqVO.getCustomerId();
        String utmId = wbsRegisterMqVO.getUtmId();

        CustomerSyncQO customerSyncQO = new CustomerSyncQO();
        customerSyncQO.setAssetCustomerId(userId);
        customerSyncQO.setEntId(Integer.parseInt(parse(utmId)));
        customerSyncQO.setCustomerId(thirdpartyId);

        if (Strings.isNullOrEmpty(userId)) {
            logger.info("资产端客户ID为空！");
            throw new CheckException(ResultEnum.FAIL.getStatus(),"资产端客户ID为空！");
        } else {
            Integer userIdd = Integer.parseInt(userId);
            User userVO = userService.findUserById(userIdd);
            if (userVO != null) {
                if (userVO.getOpenAccount() != null && userVO.getOpenAccount().intValue() == ACCOUNT_OPENED) {
                    // 余额信息
                    Account accountVO = accountService.getAccount(userIdd);
                    // 开户行信息
                    BankCard bankCardVO = userService.selectBankCardByUserId(userIdd);

                    buildData(userVO, accountVO, bankCardVO, customerSyncQO);

                } else {

                    buildData(userVO, customerSyncQO);
                }
            }
            return customerSyncQO;
        }
    }

    // thirdPropertyIds有顺序
    private String parse(String utmId) {
        Integer utmIdInt = Integer.parseInt(utmId);
        String thirdIds = wbsConfig.getThridPropertyIds();
        logger.debug("传入的utmId【{}】配置的财富端ID信息【{}】", utmId, thirdIds);
        logger.debug("配置纳觅【{}】裕峰瑞【{}】大唐【{}】千乐【{}】", wbsConfig.getUtmNami(), wbsConfig.getUtmYufengrui(),
                wbsConfig.getUtmDatang(), wbsConfig.getUtmQianle());
        String[] thirdIdsArr = thirdIds.split(",");

        String thirdUtmId = thirdIdsArr[0];

        if (utmIdInt.equals(wbsConfig.getUtmNami()) || utmIdInt.equals(wbsConfig.getUtmYufengrui())) {
            thirdUtmId = thirdIdsArr[0];
        } else if (utmIdInt.equals(wbsConfig.getUtmDatang())) {
            thirdUtmId = thirdIdsArr[1];
        } else if (utmIdInt.equals(wbsConfig.getUtmQianle())) {
            thirdUtmId = thirdIdsArr[2];
        }
        logger.debug("解析返回utmId【{}】", thirdUtmId);
        return thirdUtmId;
    }

    private void buildData(User userVO, CustomerSyncQO customerSyncQO) {

        customerSyncQO.setUserName(userVO.getUsername());

        customerSyncQO.setPlatformRegistrationTime(sdf.format(userVO.getRegTime()));

    }

    private void buildData(User userVO, Account accountVO, BankCard bankCardVO, CustomerSyncQO customerSyncQO) {

        buildData(userVO, customerSyncQO);

        //身份证信息
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("userId", Integer.valueOf(userVO.getUserId()));
        BankOpenAccountRecordCustomize bankOpenAccountRecordCustomize = bankOpenRecordService.selectBankAccountList(mapParam);
        if (bankOpenAccountRecordCustomize == null) {
            logger.error("userId【{}】未查到开户记录",userVO.getUserId());
        } else {
            customerSyncQO.setDocumentNo(bankOpenAccountRecordCustomize.getIdCard());
        }

        // 开户行信息
        customerSyncQO.setBankOfDeposit(bankCardVO.getBank());

        customerSyncQO.setPlatformAccountOpeningTime(sdf.format(bankCardVO.getCreateTime()));

        customerSyncQO.setBankReservedPhoneNumber(bankCardVO.getMobile());

        customerSyncQO.setBankCardNumber(bankCardVO.getCardNo());

        // 余额待收信息
        customerSyncQO
                .setPrecipitatedCapital(accountVO.getBalance() == null ? 0 : accountVO.getBalance().doubleValue());

        customerSyncQO
                .setFundsToBeCollected(accountVO.getBankAwait() == null ? 0 : accountVO.getBankAwait().add(accountVO.getPlanAccountWait()).doubleValue());

    }

}
