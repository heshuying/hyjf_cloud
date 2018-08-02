package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.*;

import java.util.List;

/**
 * @author lisheng
 * @version WeeklyService, v0.1 2018/7/30 14:28
 */

public interface WeeklyService {


    /**
     * 获取投资金额和预期金额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<BorrowTender> getBorrowTender(int userid, int begin, int end);

    /**
     * 获取投资金额和预期金额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<CreditTender> getCreditTender(int userid, String begin, String end);

    /**
     * 获取汇计划预计
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<HjhAccede> getAccede(int userid, int begin, int end);

    /**
     * 获取优惠券 预期金额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<BorrowTenderCpn> getBorrowTenderCPN(int userid, int begin, int end);

    /**
     * 获取还款总额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<BorrowRecover> getBorrowRecover(int userid, String begin, String end);

    /**
     * 获取还款总额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<CreditRepay> getCreditRepay(int userid,int begin,int end);

    /**
     * 获取债转总额
     * @param userid
     * @param begin
     * @param end
     * @return
     */
    List<CreditRepay> getCreditRepayToCredit(int userid,int begin,int end);

    /**
     * 获取用户可用优惠券
     * @param userid
     * @return
     */
    boolean coupon(int userid);



}
