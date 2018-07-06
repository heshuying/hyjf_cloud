/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferServiceImpl, v0.1 2018/7/6 9:15
 */
@Service
public class CustomerTransferServiceImpl extends BaseServiceImpl implements CustomerTransferService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 根据用户名查询账户余额
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject searchBalanceByUsername(String userName) {
        JSONObject jsonObject = new JSONObject();
        List<UserVO> userVOList = amUserClient.searchUserByUsername(userName);
        if(userVOList != null && userVOList.size() == 1){
            UserVO userVO = userVOList.get(0);
            List<AccountChinapnrVO> accountChinapnrVOList = amUserClient.searchAccountChinapnrByUserId(userVO.getUserId());
            if(accountChinapnrVOList != null && accountChinapnrVOList.size() == 1){
                List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(userVO.getUserId());
                if (accountVOList != null && accountVOList.size() == 1) {
                    AccountVO accountVO = accountVOList.get(0);
                    jsonObject.put("status","00");
                    jsonObject.put("result",accountVO.getBalance().toString());
                } else {
                    jsonObject.put("status","error");
                    jsonObject.put("result","未查询到正确的余额信息");
                }
            }else{
                jsonObject.put("status","error");
                jsonObject.put("result","用户未开户，无法转账");
            }
        }else{
            jsonObject.put("status","error");
            jsonObject.put("result","未查询到正确的用户信息");
        }
        return jsonObject;
    }

    /**
     * 参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject checkCustomerTransferParam(CustomerTransferRequest request) {
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotEmpty(request.getOutUserName())){
            String userName = request.getOutUserName();
            List<UserVO> userVOList = amUserClient.searchUserByUsername(userName);
            if(userVOList != null && userVOList.size() == 1){
                UserVO userVO = userVOList.get(0);
                List<AccountChinapnrVO> accountChinapnrVOList = amUserClient.searchAccountChinapnrByUserId(userVO.getUserId());
                if(accountChinapnrVOList != null && accountChinapnrVOList.size() == 1){
                    List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(userVO.getUserId());
                    if(accountVOList != null && accountVOList.size() == 1){
                        jsonObject.put("status","00");
                        jsonObject.put("result","校验参数成功");
                    }else{
                        jsonObject.put("status","error");
                        jsonObject.put("result","未查询到正确的余额信息");
                    }
                }else{
                    jsonObject.put("status","error");
                    jsonObject.put("result","用户未开户，无法转账");
                }
            }else{
                jsonObject.put("status","error");
                jsonObject.put("result","未查询到正确的用户信息");
            }
        }else{
            jsonObject.put("status","error");
            jsonObject.put("result","用户名不能为空");
        }
        return jsonObject;
    }

    /**
     * 根据header中的userId获取登录admin用户信息
     * @auth sunpeikai
     * @param userId 当前admin登录用户的userId
     * @return
     */
    @Override
    public AdminSystemVO getAdminSystemByUserId(Integer userId) {
        return amConfigClient.getUserInfoById(userId);
    }

    /**
     * 向数据库的ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 用户转账-发起转账的参数
     * @return
     */
    @Override
    public boolean insertTransfer(CustomerTransferRequest request) {
        return amTradeClient.insertUserTransfer(request);
    }
}
