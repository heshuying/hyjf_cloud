package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.trade.dao.model.customize.ApiBorrowRepaymentInfoCustomize;

import java.util.List;

/**
 * @version ApiBorrowRepaymentInfoCustomizeMapper, v0.1 2018/8/28 15:05
 * @Author: Zha Daojian
 */
public interface ApiBorrowRepaymentInfoCustomizeMapper{



    /**
     * 投资明细列表--外部api调用
    * @author Zha Daojian
    * @date 2018/8/28 15:12
    * @param rquest
    * @return java.util.List<com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO>
    **/
     List<ApiBorrowRepaymentInfoCustomize> apiSearchBorrowRepaymentInfoList(ApiBorrowRepaymentInfoRequest rquest);
}
