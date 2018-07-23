/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.trans;

import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.text.ParseException;

/**
 * @author zhangqingqing
 * @version MobileModifyService, v0.1 2018/6/14 16:47
 */
public interface MobileModifyService extends BaseUserService {
    boolean checkForMobileModify(String newMobile, String smsCode);

    MobileModifyResultBean queryForMobileModify(Integer userId);

	boolean checkForMobileModifyOpened(String newMobile, String smsCode, String srvAuthCode);

	BankCallBean callMobileModify(Integer userId, String newMobile, String smsCode, String srvAuthCode);

    void updateUserCAMQ(int userId) throws ParseException, MQException;
}
