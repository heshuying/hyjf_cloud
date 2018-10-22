package com.hyjf.am.trade.service.admin.productcenter.applyagreement;

import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;

import java.util.List;
import java.util.Map;

/**
 * @version ApplyAgreementService, v0.1 2018/10/22 14:30
 * @Author: Zha Daojian
 */
public interface ApplyAgreementService extends BaseService{

    /**
     *  垫付协议申请数目
     *
     * @return
     */
    Integer countApplyAgreement(ApplyAgreementRequest request);

    /**
     * 列表
     *
     * @return
     */
    List<ApplyAgreementVO> selectApplyAgreement(ApplyAgreementRequest request);
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
    List<BorrowRepayAgreementCustomizeVO> selectBorrowRepayPlan(BorrowRepayAgreementAmRequest request, int limitStart, int limitEnd);

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
    List<BorrowRepayAgreementCustomizeVO> selectBorrowRepay(BorrowRepayAgreementAmRequest request, int limitStart, int limitEnd);
}
