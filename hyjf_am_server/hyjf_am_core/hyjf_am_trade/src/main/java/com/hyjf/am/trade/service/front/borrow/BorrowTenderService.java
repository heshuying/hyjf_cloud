package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BorrowTenderService {

    Integer getCountBorrowTenderService(Integer userId, String borrowNid);

    BorrowTender selectBorrowTender(BorrowTenderRequest request);

    List<FddTemplet> getFddTempletList(Integer protocolType);

    int saveTenderAgreement(TenderAgreement tenderAgreement);

    int updateTenderAgreement(TenderAgreement tenderAgreement);

    List<BorrowTender> getBorrowTenderListByNid(String nid);

    /**
     * 根据投资订单号查询已承接金额
     * @param tenderNid
     * @return
     */
    BigDecimal getAssignCapitalByTenderNid(String tenderNid);

    /**
     * 保存债转信息
     * @param bean
     * @return
     */
    Integer saveCreditTenderAssignLog(CreditTenderLog bean);

    /**
     * 获取utm注册用户投资次数
     * @param list utm注册用户userid集合
     * @param type 类型:pc,ios,android,wechat
     * @return
     */
    Integer getUtmTenderNum(List<Integer> list, String type);

    /**
     * 获取utm注册用户HZT投资额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getHztTenderPrice(List<Integer> list);

    /**
     * 获取utm注册用户HXF投资额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getHxfTenderPrice(List<Integer> list);

    /**
     * 获取utm注册用户RTB投资额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getRtbTenderPrice(List<Integer> list);

    /**
     * @Author walter.limeng
     * @Description  取得优惠券投资信息
     * @Date 17:25 2018/7/17
     * @Param couponTenderNid
     * @return
     */
    BorrowTenderCpn getCouponTenderInfo(String couponTenderNid);

    /**
     * @Author walter.limeng
     * @Description  根据订单编号取得该订单的还款列表
     * @Date 17:37 2018/7/17
     * @Param couponTenderNid
     * @Param periodNow
     * @return
     */
    CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, Integer periodNow);

    /**
     * @Author walter.limeng
     * @Description  更新borrowTenderCpn表
     * @Date 11:16 2018/7/18
     * @Param borrowTenderCpn
     * @return
     */
    Integer updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn);

    /**
     * 通过borrowId获取对应的总钱数
     * @param params
     * @return
     */
    String countMoneyByBorrowId(Map<String,Object> params);
    /**
     * 查询固定时间间隔的用户投资列表
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    List<BorrowTender> selectBorrowTenderList(String repairStartDate, String repairEndDate);
    /**
     * 更新标的投资详情表
     * @param borrowTender
     * @return
     */
    Boolean updateBorrowTender(BorrowTender borrowTender);

    /**
     * 根据放款编号获取该标的的投资信息 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    List<BorrowTender> getBorrowTenderListByBorrowNid(String borrowNid);

    /**
     * 查询用户投次数
     * @param userId
     * @return
     */
    Integer selectTenderCount(Integer userId);
}
