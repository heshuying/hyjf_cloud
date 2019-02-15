package com.hyjf.cs.user.service.wrb;

import com.hyjf.am.response.trade.WrbInvestRecordResponse;
import com.hyjf.am.response.trade.wrbInvestRecoverPlanResponse;
import com.hyjf.am.response.user.WrbAccountResponse;
import com.hyjf.am.response.user.WrbInvestSumResponse;
import com.hyjf.am.resquest.api.WrbInvestRecordRequest;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderSumCustomizeVO;

import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version WrbInfoService, v0.1 2018/9/20 15:42
 */

public interface WrbInfoService {
    /**
     * 查询平台的公告信息
     * @param request
     * @return
     */
    List<MessagePushTemplateVO> getNoticeinfoDetailNew(MsgPushTemplateRequest request);

    /**
     * 根据标的ID查询可出借标的信息
     * @param borrowNid
     * @return
     */
    List<WrbBorrowListCustomizeVO> searchBorrowListByNid(String borrowNid);

    /**
     * 根据优惠券编号获取优惠券配置信息
     * @param couponCode
     * @return
     */
    CouponConfigVO getCouponByCouponCode(String couponCode);

    /**
     * 根据borrowNid查询borrow
     * @param borrowNid
     * @return
     */
    BorrowAndInfoVO selectBorrowByBorrowNid(String borrowNid);

    /**
     * 根据标的号和出借开始时间查询出借信息
     * @param borrowNid
     * @param investTime
     * @return
     */
    List<WrbBorrowTenderCustomizeVO> searchBorrowTenderByNidAndTime(String borrowNid, Date investTime);

    /**
     * 根据标的号和出借开始时间查询汇总数据
     * @param borrowNid
     * @param investTime
     * @return
     */
    WrbBorrowTenderSumCustomizeVO searchBorrowTenderSumByNidAndTime(String borrowNid, Date investTime);

    /**
     * 获取某天出借情况
     * @param invest_date 出借日期 格式2015-10-10
     * @param limit
     * @param page
     * @return
     */
    List<BorrowTenderVO> getInvestDetail(Date invest_date, Integer limit, Integer page);

    /**
     *获取某天出借情况汇总
     * @param date
     * @return
     */
    WrbInvestSumResponse getDaySum(Date date);

    /**
     * 获取用户优惠券信息
     * @param userId
     * @return
     */
    WrbAccountResponse getCouponInfo(String userId);

    /**
     * 根据平台用户id获取账户信息
     * @param userId
     * @return
     */
    WrbAccountResponse getAccountInfo(String userId);

    /**
     * 出借记录查询
     * @param request
     * @return 出借记录
     * @throws Exception
     */
    WrbInvestRecordResponse getInvestRecord(WrbInvestRecordRequest request);

    /**
     * 获取出借记录回款计划
     * @param userId
     * @param investRecordId 流水号
     * @param borrowNid
     * @return
     */
    wrbInvestRecoverPlanResponse getRecoverPlan(String userId, String investRecordId, String borrowNid);

}
