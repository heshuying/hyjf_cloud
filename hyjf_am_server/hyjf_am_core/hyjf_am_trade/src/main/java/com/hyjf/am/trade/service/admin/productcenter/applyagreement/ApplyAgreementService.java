package com.hyjf.am.trade.service.admin.productcenter.applyagreement;

import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 垫付协议管理
 * @version ApplyAgreementService, v0.1 2018/8/14 14:25
 * @Author: Zha Daojian
 */
public interface ApplyAgreementService extends BaseService {

    /**
     * 获取列表数量
     * @param request
     * @return
     */
    Integer getListTotal(ApplyAgreementRequest request);

    /**
     * 获取列表
     * @param request
     * @return
     */
    List<ApplyAgreement> getList(ApplyAgreementRequest request);

    /**
     * 垫付协议申请明细列表页--分期列表总数量
     * @param map
     * @return
     */
    Integer getListTotalPlan(Map map);

    /**
     * 垫付协议申请明细列表页--分期
     * @param map
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<BorrowRepayAgreementCustomize> getListPlay(Map map, int limitStart, int limitEnd);

    /**
     * 垫付协议申请明细列表页--不分期列表总数量
     * @param map
     * @return
     */
    Integer getListTotal(Map map);

    /**
     * 垫付协议申请明细列表页--不分期
     * @param map
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<BorrowRepayAgreementCustomize> getList(Map map, int limitStart, int limitEnd);

    /**
     * 批量生成垫付债转协议
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    Integer generateContract(BorrowRepayAgreementRequest request);



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


}
