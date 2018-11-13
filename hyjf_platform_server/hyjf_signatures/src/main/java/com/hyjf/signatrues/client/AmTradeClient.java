package com.hyjf.signatrues.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.MyCreditListQueryResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.am.vo.app.AppNewAgreementVO;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTenderCreditInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTradeListCustomizeVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.IncreaseInterestInvestVO;
import com.hyjf.am.vo.trade.account.*;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.trade.repay.*;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.signatrues.bean.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AmTradeClient, v0.1 2018/6/19 15:44
 */
public interface AmTradeClient {

    /**
     * 获取borrow对象
     * @param borrowId
     * @return
     */
    BorrowAndInfoVO getBorrowByNid(String borrowId);

    /**
     * 根据borrowNid获取BorrowInfoVO对象
     * @param borrowNid
     * @return
     */
    BorrowInfoVO getBorrowInfoByNid(String borrowNid);

    public BorrowUserVO getBorrowUser(String borrowNid);

    /**
     * 借款信息
     * @param borrowNid
     * @return
     */
    BorrowManinfoVO getBorrowManinfo(String borrowNid);

    public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest);

    public List<FddTempletVO> getFddTempletList(Integer protocolType);

    public int saveTenderAgreement(TenderAgreementVO info);

    public int updateTenderAgreement(TenderAgreementVO tenderAgreement);

    /**
     * 根据投资订单号获取协议列表
     * @param nid
     * @return
     */
    List<TenderAgreementVO> selectTenderAgreementByNid(String nid);

    /**
     * 获取还款方式
     * @param borrowStyle
     * @return
     */
    public BorrowStyleVO getBorrowStyle(String borrowStyle);

    /**
     * 获取投资协议集合
     * @param tenderNid
     * @return
     */
    List<TenderAgreementVO> getTenderAgreementListByTenderNidAndStatusNot2(String tenderNid);

    /**
     * 会计划投资详情
     * @param params
     * @return
     */
    public UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params);

    List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request);

    List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String,Object> params);

    /**
     * 汇计划债转协议下载
     * @return
     */
    public List<HjhDebtCreditTenderVO> selectHjhCreditTenderListByAssignOrderId(String assignOrderId);

    /**
     * 查询汇计划债转投资表
     * @param request
     * @return
     */
    List<HjhDebtCreditTenderVO> getHjhDebtCreditTenderList(HjhDebtCreditTenderRequest request);

    List<TenderToCreditDetailCustomizeVO> selectHJHWebCreditTenderDetail(Map<String,Object> params);

    /**
     * 获取债转信息
     * @param request1
     * @return
     */
    public List<HjhDebtCreditVO> getHjhDebtCreditList(HjhDebtCreditRequest request1) ;

    List<BorrowTenderVO> getBorrowTenderListByNid(String nid);


    /**
     * 根据加入计划订单，取得加入订单
     * @author liubin
     * @date 2018/7/04 19:26
     */
    HjhAccedeVO getHjhAccedeByAccedeOrderId(String contract_id);

    List<CreditTenderVO> selectCreditTender(String assignNid);

    /**
     * 根据contract_id查询垫付协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 15:47
     * @param contractId
     * @return com.hyjf.am.response.admin.ApplyAgreementInfoResponse
     **/
    List<ApplyAgreementInfoVO>  selectApplyAgreementInfoByContractId(String contractId);

    /**
     * 根据borrowNid查询风控信息
     * @author zhangyk
     * @date 2018/8/10 15:21
     */
    BorrowInfoWithBLOBsVO selectBorrowInfoWithBLOBSVOByBorrowId(String borrowNid);

    /**
     * 通过主键获取投资协议
     * @param tenderAgreementID
     * @return
     */
    TenderAgreementVO getTenderAgreementInfoByPrimaryKey(String tenderAgreementID);

    BorrowRecoverVO selectBorrowRecoverByTenderNid(String tenderAgreementID);

    void updateBorrowRecover(BorrowRecoverVO borrowRecover);

    List<BorrowCreditVO> getBorrowCreditList(BorrowCreditRequest request1);


}
