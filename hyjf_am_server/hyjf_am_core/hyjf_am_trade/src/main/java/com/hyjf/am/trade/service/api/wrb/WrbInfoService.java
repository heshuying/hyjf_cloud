/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.wrb;

import com.hyjf.am.response.trade.wrbInvestRecoverPlanResponse;
import com.hyjf.am.response.user.WrbInvestSumResponse;
import com.hyjf.am.resquest.api.WrbInvestRequest;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderSumCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbInvestRecordCustomize;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version WrbInfoService, v0.1 2018/9/25 11:12
 */
public interface WrbInfoService {
    /**
     * 标的查询
     * @param borrowNid
     * @return
     */
    List<WrbBorrowListCustomize> borrowList(String borrowNid);

    /**
     * 获取某天出借数据
     * @param request
     * @return
     */
    List<BorrowTender> getBorrowTenderList(WrbInvestRequest request);
    /**
     * 获取某天出借情况汇总
     * @param date
     * @return
     */
    WrbInvestSumResponse getDaySum(Date date);


    /**
     * 获取用户优惠券信息
     * @param userId
     * @return
     */
    List<CouponUser> getCouponInfo(String userId);

    /**
     * 根据平台用户id获取账户信息
     * @param userId
     * @return
     */
    Account getAccountInfo(String userId);


    /**
     * 查询标的出借情况
     * @return
     */
    List<WrbBorrowTenderCustomize> getBorrowTenderByBorrowNid(WrbInvestRequest request);

    /**
     * 根据标的号和日期查询出借情况
     * @param request
     * @return
     */
    WrbBorrowTenderSumCustomize getBorrowTenderByBorrowNidAndTime(WrbInvestRequest request);
    /**
     * 根据优惠券编号获取优惠券配置信息
     *
     * @param couponCode
     * @return
     */
    CouponConfig getCouponByCouponCode(String couponCode);

    /**
     *出借记录查询
     * @param params
     * @return
     */
    List<WrbInvestRecordCustomize> getWrbInvestRecord(Map<String, Object> params);

    /**
     *获取出借记录回款计划
     * @param userId
     * @param investRecordId
     * @param borrowNid
     * @return
     */
    List<wrbInvestRecoverPlanResponse.WrbRecoverRecord> getRecoverPlan(String userId, String investRecordId, String borrowNid);
}
