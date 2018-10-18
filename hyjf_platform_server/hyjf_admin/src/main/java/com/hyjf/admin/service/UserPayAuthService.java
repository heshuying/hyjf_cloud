/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.am.resquest.user.UserPayAuthRequest;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface UserPayAuthService {

    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    UserPayAuthResponse selectUserMemberList(UserPayAuthRequest request);
    /**
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */
    HjhUserAuthResponse selectUserPayAuthByUserId(int userId);
    /**
     * 获取银行错误返回码
     * @param retCode
     * @return
     */
    String getBankRetMsg(String retCode);
    /**
     * 查看该用户在投资表和标的放款记录中是否存在
     * @param userId
     * @auther: nxl
     * @return
     */
    int isDismissPay(int userId);
    /**
     * 查看该用户在投标的还款记录中是否存在
     * @param userId
     * @auther: nxl
     * @return
     */
    int isDismissRePay(int userId);

    /**
     * 缴费授权解约
     * @param userId
     * @param channel
     * @return
     */
    BankCallBean cancelPayAuth(int userId, String channel);

    /**
     * 更新授权表
     * @param userId
     * @return
     */
    boolean updateCancelPayAuth(int userId);
    /**
     * 解约插入授权记录表
     * @param userId
     * @param retBean
     */
   boolean insertUserAuthLog2(int userId,BankCallBean retBean,String authType);
    /**
     * 查找还款授权列表
     * @param request
     * @return
     */
    UserPayAuthResponse selectRecordListRePay(UserPayAuthRequest request);
    /**
     * 还款授权解约
     * @param userId
     * @param channel
     * @return
     */
    BankCallBean cancelRePayAuth(int userId, String channel);
    /**
     * 还款解约授权
     * @param userId
     * @return
     */
    int updateCancelRePayAuth(int userId);
}
