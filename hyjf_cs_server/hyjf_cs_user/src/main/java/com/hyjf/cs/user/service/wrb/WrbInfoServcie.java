package com.hyjf.cs.user.service.wrb;

import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderSumCustomizeVO;

import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version WrbInfoServcie, v0.1 2018/9/20 15:42
 */

public interface WrbInfoServcie {
    /**
     * 查询平台的公告信息
     * @param request
     * @return
     */
    List<MessagePushTemplateVO> getNoticeinfoDetailNew(MsgPushTemplateRequest request);

    /**
     * 根据标的ID查询可投资标的信息
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
    BorrowVO selectBorrowByBorrowNid(String borrowNid);

    /**
     * 根据标的号和投资开始时间查询投资信息
     * @param borrowNid
     * @param investTime
     * @return
     */
    List<WrbBorrowTenderCustomizeVO> searchBorrowTenderByNidAndTime(String borrowNid, Date investTime);

    /**
     * 根据标的号和投资开始时间查询汇总数据
     * @param borrowNid
     * @param investTime
     * @return
     */
    WrbBorrowTenderSumCustomizeVO searchBorrowTenderSumByNidAndTime(String borrowNid, Date investTime);

    /**
     * 获取某天投资情况
     * @param invest_date 投资日期 格式2015-10-10
     * @param limit
     * @param page
     * @return
     */
    List<BorrowTenderVO> getInvestDetail(Date invest_date, Integer limit, Integer page);
}
