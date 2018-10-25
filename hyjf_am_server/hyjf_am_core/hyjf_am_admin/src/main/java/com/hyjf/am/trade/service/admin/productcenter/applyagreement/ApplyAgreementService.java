package com.hyjf.am.trade.service.admin.productcenter.applyagreement;

import com.hyjf.am.resquest.admin.ApplyAgreementInfoRequest;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.resquest.admin.DownloadAgreementRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;

import java.util.List;

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
    List<ApplyAgreementVO> selectApplyAgreement(ApplyAgreementRequest request, int limitStart, int limitEnd);

    /**
     * 列表
     *
     * @return
     */
    List<TenderAgreementVO> selectLikeByExample(DownloadAgreementRequest request);
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

    /**
     * 获取用户投资协议
     *
     * @param borrowNid
     * @return
     */
    List<BorrowRecover> selectBorrowRecoverList(String borrowNid);

    /**
     * 获取用户债转还款列表
     * @author Zha Daojian
     * @date 2018/8/17 16:28
     * @param      * @param nid
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.CreditRepay>
     **/
    List<CreditRepay> selectCreditRepayList(String nid, int period);

    /**
     * 获取用户汇计划债转还款表
     * @author Zha Daojian
     * @date 2018/8/17 16:28
     * @param nid
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.CreditRepay>
     **/
    List<HjhDebtCreditRepay> selectHjhDebtCreditRepayList(String nid, int period);

    /**
     * 获取用户投资协议
     *
     * @param borrowNid
     * @return
     */
    List<BorrowRecoverPlan> selectBorrowRecoverPlanList(String borrowNid, int period);
    /**
     * 根据contract_id查询垫付协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     * @param contractId
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     **/
    List<ApplyAgreementInfo> selectApplyAgreementInfoByContractId(String contractId);

    /**
     * 保存垫付协议申请-协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     * @param request
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     **/
    int saveApplyAgreementInfo(ApplyAgreementInfoRequest request);

    /**
     * 保存垫付协议申请
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     * @param vo
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     **/
    int saveApplyAgreement(ApplyAgreementVO vo);
}
