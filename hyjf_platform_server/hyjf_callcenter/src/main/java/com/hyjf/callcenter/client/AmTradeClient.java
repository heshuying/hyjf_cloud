/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client;

import com.hyjf.am.resquest.callcenter.*;
import com.hyjf.am.vo.callcenter.*;
import com.hyjf.am.vo.trade.RUserVO;

import java.util.List;

/**
 * @author wangjun
 * @version AmTradeClient, v0.1 2018/7/6 17:12
 */
public interface AmTradeClient {
    /**
     * 查询优惠券
     * @param callCenterBaseRequest
     * @return List<CallCenterCouponUserVO>
     * @author wangjun
     */
    List<CallCenterCouponUserVO> selectCouponUserList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 查询优惠券使用（直投产品）
     * @param callCenterBaseRequest
     * @return List<CallCenterCouponTenderVO>
     * @author wangjun
     */
    List<CallCenterCouponTenderVO> selectCouponTenderList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 查询优惠券回款（直投产品）
     * @param callCenterBaseRequest
     * @return List<CallCenterCouponBackMoneyVO>
     * @author wangjun
     */
    List<CallCenterCouponBackMoneyVO> selectCouponBackMoneyList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 取得汇直投投资信息(汇计划)
     * 同步另外文件BorrowInvestCustomizeMapper
     * @param callcenterHtjInvestRequest
     * @return
     */
    List<CallcenterHtjInvestVO> selectBorrowInvestList(CallcenterHtjInvestRequest callcenterHtjInvestRequest);

    /**
     * 取得汇直投投资信息
     * 同步另外文件BorrowInvestCustomizeMapper
     * @param callcenterHztInvestRequest
     * @return
     */
    List<CallcenterHztInvestVO> selectBorrowInvestList(CallcenterHztInvestRequest callcenterHztInvestRequest);

    /**
     * 按照用户名/手机号查询还款明细（直投产品，含承接的债权）
     * @param callCenterBaseRequest
     * @return List<CallCenterHztRepaymentDetailVO>
     * @author wangjun
     */
    List<CallCenterHztRepaymentDetailVO> getHztRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest);
    /**
     * 按照用户名/手机号查询还款明细（汇添金）
     * @param callCenterBaseRequest
     * @return List<CallCenterHtjRepaymentDetailVO>
     * @author wangjun
     */
    List<CallCenterHtjRepaymentDetailVO> getHtjRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest);

	/**
	 * 查询 账户余额
	 * 
	 * @param accountManageBean
	 * @return
	 */
    List<CallCenterBankAccountManageVO> queryAccountInfos(CallCenterBankAccountManageRequest callCenterBankAccountManageRequest);

    List<CallCenterAccountDetailVO> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest);

    /**
     * 查询充值明细
     * @param callCenterBaseRequest
     * @return List<CallCenterRechargeVO>
     * @author wangjun
     */
    List<CallCenterRechargeVO> queryRechargeList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 取得呼叫中心转让信息接口
     * @return
     */
    List<CallCenterBorrowCreditVO> selectBorrowInvestList(SrchTransferInfoRequest srchTransferInfoRequest);
    /**
     * 获取详细列表
     *
     * @param srchTransferInfoRequest
     * @return
     */
    List<CallCenterBorrowCreditVO> selectBorrowCreditTenderList(SrchTransferInfoRequest srchTransferInfoRequest);

    /**
     * 查询提现明细
     *
     * @param callCenterBaseRequest
     * @return List<CallCenterWithdrawVO>
     */
    List<CallCenterWithdrawVO> getWithdrawRecordList(CallCenterBaseRequest callCenterBaseRequest);

    /**
     * 根据用户ID查询推荐人信息
     * @param userId
     * @return
     */
    RUserVO getRefereerInfoByUserId(Integer userId);
}
