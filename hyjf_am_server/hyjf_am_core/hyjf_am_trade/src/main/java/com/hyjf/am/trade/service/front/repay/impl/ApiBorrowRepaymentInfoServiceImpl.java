package com.hyjf.am.trade.service.front.repay.impl;

import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.trade.dao.model.customize.ApiBorrowRepaymentInfoCustomize;
import com.hyjf.am.trade.service.front.repay.ApiBorrowRepaymentInfoService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version BorrowRepaymentInfoServiceImpl, v0.1 2018/8/28 14:48
 * @Author: Zha Daojian
 */
@Service
public class ApiBorrowRepaymentInfoServiceImpl extends BaseServiceImpl implements ApiBorrowRepaymentInfoService {

    /**
     *第三方还款明细查询
    * @author Zha Daojian
    * @date 2018/8/28 15:01
    * @param request
    * @return java.util.List<com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO>
    **/
    public List<ApiBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoList(
            ApiBorrowRepaymentInfoRequest request){
        return  apiBorrowRepaymentInfoCustomizeMapper.apiSearchBorrowRepaymentInfoList(request);

    }
}
