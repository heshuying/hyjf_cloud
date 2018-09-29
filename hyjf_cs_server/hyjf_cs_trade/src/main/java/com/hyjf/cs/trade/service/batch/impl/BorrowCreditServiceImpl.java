/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.batch.BorrowCreditService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditServiceImpl, v0.1 2018/6/23 17:12
 */
@Service
public class BorrowCreditServiceImpl extends BaseTradeServiceImpl implements BorrowCreditService {

    @Autowired
    private AmTradeClient borrowCreditClient;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * 获取债转状态为0的数据
     * @return
     */
    @Override
    public List<BorrowCreditVO> selectBorrowCreditList() {
        return borrowCreditClient.selectBorrowCreditList();
    }

    /**
     * 更新债转状态
     * @return
     */
    @Override
    public Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO) {
        return borrowCreditClient.updateBorrowCredit(borrowCreditVO);
    }

    @Override
    public UserInfoVO findUsersInfoById(int userId) {
        return amUserClient.findUsersInfoById(userId);
    }


}
