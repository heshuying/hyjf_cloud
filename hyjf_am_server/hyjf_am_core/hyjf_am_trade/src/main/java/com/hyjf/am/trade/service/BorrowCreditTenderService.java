package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;

import java.util.List;

public interface BorrowCreditTenderService {

    /**
     * 债转投资信息count
     * @author zhangyk
     * @date 2018/7/11 15:33
     */
    Integer countBorrowCreditTender(BorrowCreditRepayAmRequest request);

    /**
     * 债转投资信息list
     * @author zhangyk
     * @date 2018/7/11 15:34
     */
    List<AdminBorrowCreditTenderCustomize> searchBorrowCreditTenderList(BorrowCreditRepayAmRequest request);

    /**
     * 债转投资信息合计行
     * @author zhangyk
     * @date 2018/7/11 19:43
     */
    BorrowCreditTenderSumVO sumBorrowCreditTender(BorrowCreditRepayAmRequest request);
}
