/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.client.BatchHjhBorrowRepayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BatchHjhBorrowRepayClientImpl, v0.1 2018/6/25 17:41
 */
@Service
public class BatchHjhBorrowRepayClientImpl implements BatchHjhBorrowRepayClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 计划锁定
     *  @param accedeOrderId
     * @param inverestUserInfo
     * @param commissioUserInfoVO
     * @param bankOpenAccountVO
     * @param userInfoCustomizeVOS
     */
    @Override
    public void updateForLock(String accedeOrderId, UserInfoVO inverestUserInfo, UserInfoVO commissioUserInfoVO, BankOpenAccountVO bankOpenAccountVO, List<UserInfoCustomizeVO> userInfoCustomizeVOS) {
        String url = "http://AM-TRADE/am-trade/planLockQuit/updateLockRepayInfo";
        HjhLockVo hjhLockVo = new HjhLockVo();
        hjhLockVo.setAccedeOrderId(accedeOrderId);
        hjhLockVo.setInverestUserInfo(inverestUserInfo);
        hjhLockVo.setCommissioUserInfoVO(commissioUserInfoVO);
        hjhLockVo.setBankOpenAccountVO(bankOpenAccountVO);
        hjhLockVo.setUserInfoCustomizeVOS(userInfoCustomizeVOS);
        restTemplate.postForEntity(url,hjhLockVo,null);
    }

    /**
     * 计划退出
     *
     * @param accedeOrderId
     */
    @Override
    public void updateForQuit(String accedeOrderId) {
        String url = "http://AM-TRADE/am-trade/planLockQuit/updateQuitRepayInfo/" + accedeOrderId;
        restTemplate.getForEntity(url, String.class);
    }
}
