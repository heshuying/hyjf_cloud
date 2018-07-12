package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.trade.dao.model.customize.admin.AdminBorrowCreditTenderCustomize;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询汇转让还款明细count
     * @author zhangyk
     * @date 2018/7/12 14:25
     */
    Integer getCreditRepayListCount(BorrowCreditRepayAmRequest request);


    /**
     * 查询汇转让还款明细list
     * @author zhangyk
     * @date 2018/7/12 14:26
     */
    List<BorrowCreditRepayInfoVO> getCreditRepayList(BorrowCreditRepayAmRequest request);

    /**
     * 查询汇转让还款明细合计行
     * @author zhangyk
     * @date 2018/7/12 14:27
     */
    Map<String,Object> getCreditRepayListSum(BorrowCreditRepayAmRequest request);
}
