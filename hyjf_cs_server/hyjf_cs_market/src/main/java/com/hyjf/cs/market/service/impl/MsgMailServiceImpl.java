package com.hyjf.cs.market.service.impl;

import com.hyjf.cs.market.client.AmUserClient;
import com.hyjf.cs.market.service.MsgMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version MsgMailServiceImpl, v0.1 2018/8/13 17:52
 */
@Service
public class MsgMailServiceImpl implements MsgMailService {

    @Autowired
    AmUserClient amUserClient;

    /**
     * 修改短信与邮件是否开启状态
     * @param userId
     * @param smsOpenStatus
     * @param emailOpenStatus
     * @return
     */
    @Override
    public Integer updateStatusByUserId(Integer userId, String smsOpenStatus, String emailOpenStatus) {
        return amUserClient.updateStatusByUserId(userId, smsOpenStatus, emailOpenStatus);
    }
}
