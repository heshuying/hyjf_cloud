package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;

import java.math.BigDecimal;
import java.util.List;

public interface BorrowTenderClient {

    public Integer  countUserInvest(Integer userId, String borrowNid);

	public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest);

	public List<FddTempletVO> getFddTempletList(Integer protocolType);

	public int saveTenderAgreement(TenderAgreementVO info);

	public int updateTenderAgreement(TenderAgreementVO tenderAgreement);

    List<BorrowTenderVO> getBorrowTenderListByNid(String nid);

	/**
	 * 根据投资订单号查询已承接金额
	 * @param tenderNid
	 * @return
	 */
	BigDecimal getAssignCapital(String tenderNid);

	/**
	 * 保存债转日志
	 * @param creditTenderLog
	 * @return
	 */
    Integer saveCreditTenderAssignLog(CreditTenderLogVO creditTenderLog);

	/**
	 * 查看是否已经插入网站收支明细
	 * @param logOrderId
	 * @param tenderType  交易类型
	 * @return
	 */
    Integer countAccountWebListByOrdId(String logOrderId, String tenderType);

    /**
     * @Author walter.limeng
     * @Description  取得优惠券投资信息
     * @Date 17:21 2018/7/17
     * @Param couponTenderNid
     * @return
     */
    BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid);

    /**
     * @Author walter.limeng
     * @Description  根据订单编号取得该订单的还款列表
     * @Date 17:32 2018/7/17
     * @Param couponTenderNid
     * @Param periodNow
     * @return
     */
	CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow);
}
