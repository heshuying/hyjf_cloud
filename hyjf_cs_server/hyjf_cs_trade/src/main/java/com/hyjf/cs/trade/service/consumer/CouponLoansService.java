package com.hyjf.cs.trade.service.consumer;

import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 10:34
 * @Description: CouponLoansService
 */
public interface CouponLoansService {
    /**
     * @Author walter.limeng
     * @Description  获取汇计划投资列表（优惠券）
     * @Date 10:37 2018/7/17
     * @Param orderId 订单ID
     * @return 
     */
    List<BorrowTenderCpnVO> getBorrowTenderCpnHjhList(String orderId);

    /**
     * @Author walter.limeng
     * @Description  优惠券单独投资时用
     * @Date 10:47 2018/7/17
     * @Param orderIdCoupon
     * @return 
     */
    List<BorrowTenderCpnVO> getBorrowTenderCpnHjhCouponOnlyList(String orderIdCoupon);

    /**
     * @Author walter.limeng
     * @Description  更新放款状态(优惠券)
     * @Date 10:51 2018/7/17
     * @Param borrowTenderCpn
     * @return
     */
    Integer updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn);

    /**
     * @Author walter.limeng
     * @Description  汇计划自动放款（优惠券）
     * @Date 10:59 2018/7/17
     * @Param borrowTenderCpn
     * @Param orderId
     * @return
     */
    List<Map<String,String>> updateCouponRecoverHjh(BorrowTenderCpnVO borrowTenderCpn, String orderId) throws Exception;

    /**
     * @Author walter.limeng
     * @Description  短信发送
     * @Date 15:08 2018/7/17
     * @Param msgList
     * @return
     */
    void sendSmsCoupon(List<Map<String,String>> msgList);

    /**
     * @Author walter.limeng
     * @Description  App消息推送（优惠券投资成功）
     * @Date 15:15 2018/7/17
     * @Param msgList
     * @return 
     */
    void sendAppMSCoupon(List<Map<String,String>> msgList);

    /**
     * @Author walter.limeng
     * @Description  根据borrowNid获取散标优惠券放款数据
     * @Date 18:12 2018/7/18
     * @Param borrowNid
     * @return
     */
    List<BorrowTenderCpnVO> getBorrowTenderCpnList(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  更新放款状态(优惠券)
     * @Date 18:21 2018/7/18
     * @Param borrowTenderCpn
     * @return 
     */
    List<Map<String,String>> updateCouponRecover(BorrowTenderCpnVO borrowTenderCpn);
}
