package com.hyjf.am.user.service.admin.exception.impl;

import com.hyjf.am.resquest.user.BankOpenAccountLogRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize;
import com.hyjf.am.user.service.admin.exception.BankOpenAccountLogService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version BankOpenAccountLogSrviceImpl, v0.1 2018/8/21 14:41
 * @Author: Zha Daojian
 */
@Service
public class BankOpenAccountLogServiceImpl extends BaseServiceImpl implements BankOpenAccountLogService {

    private final Logger logger = LoggerFactory.getLogger(getClass());



    /**
     * 通过手机号和身份证查询用户信息
    * @author Zha Daojian
    * @date 2018/8/21 18:53
    * @param request
    * @return com.hyjf.am.user.dao.model.customize.OpenAccountEnquiryCustomize
    **/
    @Override
    public OpenAccountEnquiryCustomize accountEnquiry(BankOpenAccountLogRequest request) {
         return this.adminAccountCustomizeQuiryMapper.selectAccountEnquiry(request);
    }


    /**
     * 获取掉单用户信息
     *
     * @param mobile,idcard
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     **/
    @Override
    public List<BankOpenAccountLog> bankOpenAccountLogSelect(String mobile,String idcard ) {
        logger.info("bankOpenAccountLogSelect:::::::::");
        // 借款人用户
        BankOpenAccountLogExample openAccoutLogExample = new BankOpenAccountLogExample();
        BankOpenAccountLogExample.Criteria crt = openAccoutLogExample.createCriteria();
        if(StringUtils.isNoneEmpty(idcard)){
            crt.andIdNoEqualTo(idcard);
        }
        if(StringUtils.isNoneEmpty(mobile)){
            crt.andMobileEqualTo(mobile);
        }
        List<BankOpenAccountLog> openAccountLogs = this.bankOpenAccountLogMapper.selectByExample(openAccoutLogExample);
        if (openAccountLogs != null && openAccountLogs.size()> 0) {
            return openAccountLogs;
        }
        return null;
    }

    /**
     * 获取掉单用户信息
     *
     * @param orderId
     * @return java.util.List<com.hyjf.admin.beans.vo.BankOpenAccountLogVO>
     * @author Zha Daojian
     * @date 2018/8/21 13:54
     **/
    @Override
    public BankOpenAccountLog selectBankOpenAccountLogByOrderId(String orderId) {

        logger.info("selectBankOpenAccountLogByOrderId:::::::::userId=[{}]",orderId);
        // 借款人用户
        BankOpenAccountLogExample openAccoutLogExample = new BankOpenAccountLogExample();
        BankOpenAccountLogExample.Criteria crt = openAccoutLogExample.createCriteria();
        crt.andOrderIdEqualTo(orderId);
        List<BankOpenAccountLog> openAccountLogs = this.bankOpenAccountLogMapper.selectByExample(openAccoutLogExample);
        if (openAccountLogs != null && openAccountLogs.size() == 1) {
            return openAccountLogs.get(0);
        }
        return null;
    }

    /**
     * 查询返回的电子账号是否已开户
     *
     * @param accountId
     * @return java.lang.Boolean
     * @author Zha Daojian
     * @date 2018/8/23 9:36
     **/
    @Override
    public Boolean checkAccountByAccountId(String accountId) {
        // 根据account查询用户是否开户
        BankOpenAccountExample example = new BankOpenAccountExample();
        example.createCriteria().andAccountEqualTo(accountId);
        List<BankOpenAccount> bankOpenList = this.bankOpenAccountMapper.selectByExample(example);
        if (bankOpenList != null && bankOpenList.size() > 0) {
            for (int i = 0; i < bankOpenList.size(); i++) {
                Integer userId = bankOpenList.get(i).getUserId();
                UserExample userExample = new UserExample();
                userExample.createCriteria().andUserIdEqualTo(userId);
                List<User> user = this.usersMapper.selectByExample(userExample);
                if (user != null && user.size() > 0) {
                    for (int j = 0; j < user.size(); j++) {
                        User info = user.get(j);
                        Integer bankOpenFlag = info.getBankOpenAccount();
                        if (bankOpenFlag == 1) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    /**
     * 删除用户开户日志
     *
     * @param userId
     * @return java.lang.Boolean
     * @author Zha Daojian
     * @date 2018/8/23 9:36
     **/
    @Override
    public Boolean deleteBankOpenAccountLogByUserId(Integer userId) {
        BankOpenAccountLogExample accountLogExample = new BankOpenAccountLogExample();
        accountLogExample.createCriteria().andUserIdEqualTo(userId);
        boolean deleteLogFlag = this.bankOpenAccountLogMapper.deleteByExample(accountLogExample) > 0 ? true : false;
        return  deleteLogFlag;
    }
}
