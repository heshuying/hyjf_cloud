package com.hyjf.cs.trade.service.repay;

import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO;
import com.hyjf.cs.common.service.BaseService;

import java.util.List;

/**
 * @version BorrowRepaymentInfoService, v0.1 2018/8/28 10:31
 * @Author: Zha Daojian
 */
public interface BorrowRepaymentInfoService extends BaseService {

    /**
    * @author Zha Daojian
    * @date 2018/8/28 10:33
    * @param request
    * @return java.util.List<com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO>
    **/
    List<ApiBorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(
            ApiBorrowRepaymentInfoRequest request);
}
