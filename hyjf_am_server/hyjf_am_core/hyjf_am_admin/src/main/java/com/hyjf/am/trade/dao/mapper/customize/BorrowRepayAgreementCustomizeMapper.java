package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;

import java.util.List;

/**
 * @version BorrowRepayAgreementCustomizeMapper, v0.1 2018/8/14 17:16
 * @Author: Zha Daojian
 */
public interface BorrowRepayAgreementCustomizeMapper {

    /**
     * 垫付协议申请明细列表页--分期列表总数量
     *
     * @param request
     * @return
     */
    int countBorrowRepayPlan(BorrowRepayAgreementAmRequest request);

    /**
     * 垫付协议申请明细列表页--分期列表
     *
     * @param request
     * @return
     */
    List<BorrowRepayAgreementCustomizeVO> selectBorrowRepayPlan(BorrowRepayAgreementAmRequest request);

    /**
     * 垫付协议申请明细列表页--列表总数量
     *
     * @param request
     * @return
     */
    int countBorrowRepay(BorrowRepayAgreementAmRequest request);

    /**
     * 垫付协议申请明细列表页--列表
     *
     * @param request
     * @return
     */
    List<BorrowRepayAgreementCustomizeVO> selectBorrowRepay(BorrowRepayAgreementAmRequest request);
}
