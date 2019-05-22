package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.vo.activity.UserTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;

import java.math.BigDecimal;
import java.util.Date;
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
     * 根据出借订单号查询已承接金额
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
     * 获取utm注册用户出借次数
     * @param list utm注册用户userid集合
     * @param type 类型:pc,ios,android,wechat
     * @return
     */
    Integer getUtmTenderNum(List<Integer> list, String type);

    /**
     * 获取utm注册用户HZT出借额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getHztTenderPrice(List<Integer> list);

    /**
     * 获取utm注册用户HXF出借额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getHxfTenderPrice(List<Integer> list);

    /**
     * 获取utm注册用户RTB出借额
     * @param list utm注册用户userid集合
     * @return
     */
    BigDecimal getRtbTenderPrice(List<Integer> list);

    /**
     * @Author walter.limeng
     * @Description  取得优惠券出借信息
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
     * 查询固定时间间隔的用户出借列表
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    List<BorrowTender> selectBorrowTenderList(String repairStartDate, String repairEndDate);
    /**
     * 更新标的出借详情表
     * @param borrowTender
     * @return
     */
    Boolean updateBorrowTender(BorrowTender borrowTender);

    /**
     * 根据放款编号获取该标的的出借信息 add by liushouyi
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

    /**
     * 根据用户ID查询用户出借记录
     *
     * @param userId
     * @return
     */
    List<BorrowTender> selectBorrowTenderByUserId(Integer userId);

    List<WrbTenderNotifyCustomizeVO> getBorrowTenderByAddtime(String sourceIdSrch, String dayStart, String dayEnd);

    List<WrbTenderNotifyCustomizeVO> getCreditTenderByAddtime(String dayStart, String dayEnd);

    List<WrbTenderNotifyCustomizeVO> getAccountRechargeByAddtime(String dayStart, String dayEnd);

    List<WrbTenderNotifyCustomizeVO> getBorrowTenderByClient(String source,String dayStart, String dayEnd);

    List<WrbTenderNotifyCustomizeVO> getProductListByClient(String source,String dayStart, String dayEnd);

    List<WrbTenderNotifyCustomizeVO> getDebtPlanAccedeByClient(String source,String dayStart, String dayEnd);

    List<WrbTenderNotifyCustomizeVO> getCreditTenderByClient(String source,String dayStart, String dayEnd);

    BigDecimal getInvestAmountByPeriod(Date startTime, Date endTime);

    BigDecimal getAnnualInvestAmount(Integer userId, Date startTime, Date endTime);

    /**
     * 查询活动期间用户投资
     * @param userId
     * @param startDate
     * @param endDate
     * @param client 客户端
     * @return
     */
    BigDecimal getUserInvestAmount(int userId, Date startDate, Date endDate, Integer client);

    /**
     * 查询投资年化用户top5
     * @param startDate
     * @param endDate
     * @return
     */
    List<UserTenderVO> getSumAnnualInvestAmountTop5(Date startDate, Date endDate);
    /**
     * 根据计划订单号查找投资详情
     * @param accedeOrderId
     * @return
     */
    List<BorrowTender> getBorrowTenderByAccede(String accedeOrderId);
}
