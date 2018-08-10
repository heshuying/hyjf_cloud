package com.hyjf.am.trade.service.callcenter;

import com.hyjf.am.resquest.callcenter.*;
import com.hyjf.am.trade.dao.model.auto.RUser;
import com.hyjf.am.trade.dao.model.customize.*;

import java.util.List;

/**
 * @author wangjun
 * @version CallCenterTradeService, v0.1 2018/6/11 17:52
 */
public interface CallCenterTradeService {
    /**
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @param callCenterBaseRequest
     * @return List<CallCenterHztRepaymentDetailCustomize>
     * @author wangjun
     */
     List<CallCenterHztRepaymentDetailCustomize> getHztRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest);
    /**
     * 按照用户名/手机号查询还款明细（汇添金）
     * @param callCenterBaseRequest
     * @return List<CallCenterHtjRepaymentDetailCustomize>
     * @author wangjun
     */
     List<CallCenterHtjRepaymentDetailCustomize> getHtjRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 查询资金明细
     * @param callCenterAccountDetailRequest
     * @return List<CallCenterAccountDetailCustomize>
     * @author wangjun
     */
     List<CallCenterAccountDetailCustomize> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest);

    /**
     * 查询充值明细
     * @param centerBaseRequest
     * @return List<CallCenterRechargeCustomize>
     * @author wangjun
     */
    List<CallCenterRechargeCustomize> queryRechargeList(CallCenterBaseRequest centerBaseRequest);

    /**
     * 查询提现明细
     * @param centerBaseRequest
     * @return List<CallCenterRechargeCustomize>
     * @author wangjun
     */
    List<CallCenterWithdrawCustomize> getWithdrawRecordList(CallCenterBaseRequest centerBaseRequest);
    
    /**
     * 查询投资明细(汇直投)
     * @param centerBaseRequest
     * @return List<CallCenterRechargeCustomize>
     * @author libin
     */
    List<CallcenterHztInvestCustomize> getBorrowInvestList(CallcenterHztInvestRequest callcenterHztInvestRequest);
    /**
     * 按照用户名/手机号查询转让信息
     * @param centerBaseRequest
     * @return List<CallCenterRechargeCustomize>
     * @author libin
     */
    List<CallCenterBorrowCreditCustomize> getBorrowCreditList(SrchTransferInfoRequest srchTransferInfoRequest);
    /**
     * 按照用户名/手机号查询承接债权信息
     * @param centerBaseRequest
     * @return List<CallCenterRechargeCustomize>
     * @author libin
     */
    List<CallCenterBorrowCreditCustomize> getBorrowCreditTenderList(SrchTransferInfoRequest srchTransferInfoRequest);

    /**
     * 查询优惠券
     * @param centerBaseRequest
     * @return List<CallCenterCouponBackMoneyCustomize>
     * @author wangjun
     */
    List<CallCenterCouponUserCustomize> getUserCouponInfoList(CallCenterBaseRequest centerBaseRequest);

    /**
     * 查询优惠券使用（直投产品）
     * @param centerBaseRequest
     * @return List<CallCenterCouponBackMoneyCustomize>
     * @author wangjun
     */
    List<CallCenterCouponTenderCustomize> getUserCouponTenderList(CallCenterBaseRequest centerBaseRequest);

    /**
     * 查询优惠券回款（直投产品）
     * @param centerBaseRequest
     * @return List<CallCenterCouponBackMoneyCustomize>
     * @author wangjun
     */
    List<CallCenterCouponBackMoneyCustomize> getUserCouponBackMoneyList(CallCenterBaseRequest centerBaseRequest);
    
    /**
     * 查询投资明细(汇添金)
     * @param centerBaseRequest
     * @return List<CallCenterCouponBackMoneyCustomize>
     * @author libin
     */
    List<CallcenterHtjInvestCustomize> getHtjBorrowInvestList(CallcenterHtjInvestRequest callcenterHtjInvestRequest);

    /**
     * 根据用户ID查询推荐人信息
     * @param userId
     * @return
     */
    RUser getRefereerInfoByUserId(Integer userId);
}
