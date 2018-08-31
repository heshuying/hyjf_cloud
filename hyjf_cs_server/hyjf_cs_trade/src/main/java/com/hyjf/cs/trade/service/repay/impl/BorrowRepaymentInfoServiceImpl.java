package com.hyjf.cs.trade.service.repay.impl;

import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.repay.BorrowRepaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version BorrowRepaymentInfoServiceImpl, v0.1 2018/8/28 10:31
 * @Author: Zha Daojian
 */
@Service
public class BorrowRepaymentInfoServiceImpl extends BaseTradeServiceImpl implements BorrowRepaymentInfoService {
    /**
     * 第三方还款明细查询
     * @param request
     * @return java.util.List<com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO>
     * @author Zha Daojian
     * @date 2018/8/28 10:33
     **/
    @Override
    public List<ApiBorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(ApiBorrowRepaymentInfoRequest request) {
        return amTradeClient.selectBorrowRepaymentInfoList(request);
    }
}
