/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
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

    /**
     * 根据用户名查询账户余额
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject searchBalanceByUsername(String userName) {
        JSONObject jsonObject = new JSONObject();
        List<UserVO> userVOList = amUserClient.searchBalanceByUsername(userName);
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
}
