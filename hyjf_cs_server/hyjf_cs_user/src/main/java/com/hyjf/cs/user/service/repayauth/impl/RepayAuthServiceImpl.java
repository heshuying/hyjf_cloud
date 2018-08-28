/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.repayauth.impl;

import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.repayauth.RepayAuthPageService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author nxl
 * @version RepayAuthServiceImpl, v0.1 2018/6/14 14:10
 * 缴费授权
 */
@Service
public class RepayAuthServiceImpl extends BaseUserServiceImpl implements RepayAuthPageService {

    @Autowired
    private AmUserClient amUserClient;
    /**
     * 插入日志
     * @param userId
     * @param bean
     * @param client
     */
    @Override
    public void insertUserAuthLog(int userId, BankCallBean bean, Integer client) {
        HjhUserAuthLogVO hjhUserAuthLog = new HjhUserAuthLogVO();
        UserVO userVO = amUserClient.findUserById(userId);
        hjhUserAuthLog.setUserId(userVO.getUserId());
        hjhUserAuthLog.setUserName(userVO.getUsername());
        hjhUserAuthLog.setOrderId(bean.getLogOrderId());
        hjhUserAuthLog.setOrderStatus(2);
        hjhUserAuthLog.setAuthType(6);
        hjhUserAuthLog.setOperateEsb(client);
        hjhUserAuthLog.setCreateUser(userVO.getUserId());
        hjhUserAuthLog.setCreateTime(new Date());
        hjhUserAuthLog.setUpdateTime(new Date());
        hjhUserAuthLog.setUpdateUser(userId);
        hjhUserAuthLog.setDelFlg(0);
        amUserClient.insertUserAuthLog(hjhUserAuthLog);
    }


}
