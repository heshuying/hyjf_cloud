/**
 * 开户
 */
package com.hyjf.wbs.user.service.bank.impl;

import com.hyjf.wbs.user.dao.model.auto.BankOpenAccountLog;
import com.hyjf.wbs.user.dao.model.auto.BankOpenAccountLogExample;
import com.hyjf.wbs.user.dao.model.auto.UserInfo;
import com.hyjf.wbs.user.dao.model.auto.UserInfoExample;
import com.hyjf.wbs.user.service.bank.BankOpenService;
import com.hyjf.wbs.user.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BankOpenServiceImpl extends BaseServiceImpl implements BankOpenService {
    private Logger logger = LoggerFactory.getLogger(BankOpenServiceImpl.class);


    @Override
    public boolean updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name, String idno, String cardNo) {
        Date date = new Date();
        BankOpenAccountLogExample example = new BankOpenAccountLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andOrderIdEqualTo(logOrderId);
        List<BankOpenAccountLog> bankOpenAccountLogs = this.bankOpenAccountLogMapper.selectByExample(example);
        if (bankOpenAccountLogs != null && bankOpenAccountLogs.size() == 1) {
            BankOpenAccountLog openAccountLog = bankOpenAccountLogs.get(0);
            openAccountLog.setMobile(mobile);
            openAccountLog.setStatus(0);
            openAccountLog.setUpdateTime(date);
            openAccountLog.setUpdateUserId(userId);
            boolean updateFlag = this.bankOpenAccountLogMapper.updateByPrimaryKeySelective(openAccountLog) > 0 ? true
                    : false;
            if (updateFlag) {
                return true;
            } else {
                return false;
            }
        } else {
            BankOpenAccountLog bankOpenAccountLog = new BankOpenAccountLog();
            bankOpenAccountLog.setUserId(userId);
            bankOpenAccountLog.setUserName(userName);
            bankOpenAccountLog.setMobile(mobile);
            bankOpenAccountLog.setStatus(0);
            bankOpenAccountLog.setOrderId(logOrderId);
            bankOpenAccountLog.setCreateTime(date);
            bankOpenAccountLog.setCreateUserId(userId);
            bankOpenAccountLog.setName(name);
            bankOpenAccountLog.setIdNo(idno);
            bankOpenAccountLog.setCardNo(cardNo);
            bankOpenAccountLog.setClient(Integer.parseInt(clientPc));
            boolean flag = this.bankOpenAccountLogMapper.insertSelective(bankOpenAccountLog) > 0 ? true : false;
            return flag;
        }
    }

    @Override
    public UserInfo findUserInfoByCradId(String cardNo) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria cra = example.createCriteria();
        cra.andIdcardEqualTo(cardNo);

        List<UserInfo> list = userInfoMapper.selectByExample(example);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

}
