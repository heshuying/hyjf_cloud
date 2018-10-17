/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.UserPayAuthService;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.am.resquest.user.HjhUserAuthLogRequest;
import com.hyjf.am.resquest.user.UserPayAuthRequest;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author nixiaoling
 * @version UserCenterServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class UserPayAuthServiceImpl extends BaseServiceImpl implements UserPayAuthService {


    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private SystemConfig systemConfig;


    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    @Override
    public UserPayAuthResponse selectUserMemberList(UserPayAuthRequest request) {
        return amUserClient.selectUserPayAuthList(request);
    }
    /**
     * 根据用户id查询用户签约授权信息
     * @param userId
     * @return
     */
    @Override
    public HjhUserAuthResponse selectUserPayAuthByUserId(int userId) {
       return amUserClient.selectUserPayAuthByUserId(userId);
    }
    /**
     * 获取银行错误返回码
     * @param retCode
     * @return
     */
    @Override
    public String getBankRetMsg(String retCode) {
        return amConfigClient.getBankRetMsg(retCode);
    }

    /**
     * 查看该用户在投资表和标的放款记录中是否存在
     * @param userId
     * @auther: nxl
     * @return
     */
    @Override
    public int isDismissPay(int userId) {
        return amUserClient.isDismissPay(userId);
    }
    /**
     * 查看该用户在投标的还款记录中是否存在
     * @param userId
     * @auther: nxl
     * @return
     */
    @Override
    public int isDismissRePay(int userId) {
        return amUserClient.isDismissRePay(userId);
    }

    /**
     * 缴费授权解约
     * @param userId
     * @param channel
     * @return
     */
    @Override
    public BankCallBean cancelPayAuth(int userId, String channel) {
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        selectbean.setTxCode(BankCallConstant.TXCODE_AUTO_PAY_AUTH_CANCEL);
        selectbean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        selectbean.setBankCode(systemConfig.getBANK_BANKCODE());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(channel);

        BankOpenAccountVO bankOpenAccount = this.getBankOpenAccount(userId);
        if (bankOpenAccount != null) {
            selectbean.setAccountId(bankOpenAccount.getAccount());// 电子账号
        }
        selectbean.setOrderId(GetOrderIdUtils.getUsrId(userId));// 订单号

        selectbean.setMaxAmt("2000000");
        selectbean.setTxType("2");
//		HjhUserAuth hjhUserAuth = this.getHjhUserAuthByUserId(userId);
//		if (hjhUserAuth != null) {
//			selectbean.setOrgOrderId(hjhUserAuth.getAutoOrderId());// 原订单号
//		}
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 调用接口
        BankCallBean retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }
    /**
     * 更新授权表
     * @param userId
     * @return
     */
    @Override
    public boolean updateCancelPayAuth(int userId){
        return amUserClient.updateCancelPayAuth(userId);
    }
    /**
     * 解约插入授权记录表
     * @param userId
     * @param retBean
     */
    @Override
    public boolean insertUserAuthLog2(int userId,BankCallBean retBean,String authType){
        HjhUserAuthLogRequest hjhUserAuthRequest = new HjhUserAuthLogRequest();
        UserVO userVO = amUserClient.findUserById(userId);
        hjhUserAuthRequest.setUserId(userVO.getUserId());
        hjhUserAuthRequest.setUserName(userVO.getUsername());
        hjhUserAuthRequest.setOrderId(retBean.getLogOrderId());
        hjhUserAuthRequest.setOrderStatus(1);
        hjhUserAuthRequest.setAuthType(Integer.parseInt(authType));
        hjhUserAuthRequest.setOperateEsb(0);
        hjhUserAuthRequest.setCreateUserId(userVO.getUserId());
        hjhUserAuthRequest.setCreateTime(new Date());
        hjhUserAuthRequest.setUpdateTime(new Date());
        hjhUserAuthRequest.setUpdateUserId(userId);
        hjhUserAuthRequest.setDelFlag(0);
        return amUserClient.insertUserAuthLog2(hjhUserAuthRequest);

    }
}
