package com.hyjf.cs.market.service;

/**
 * @author lisheng
 * @version MsgMailService, v0.1 2018/8/13 17:52
 */

public interface MsgMailService {
    Integer updateStatusByUserId(Integer userId, String smsOpenStatus , String emailOpenStatus);
}
