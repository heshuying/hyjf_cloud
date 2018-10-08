package com.hyjf.cs.user.service.wrb.impl;

import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderSumCustomizeVO;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.service.wrb.WrbInfoServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version WrbNoticeinfoSericeImpl, v0.1 2018/9/20 15:43
 */
@Service
public class WrbInfoSericeImpl implements WrbInfoServcie {
    @Autowired
    AmConfigClient amConfigClient;
    @Autowired
    AmTradeClient amTradeClient;


    @Override
    public List<MessagePushTemplateVO> getNoticeinfoDetailNew(MsgPushTemplateRequest request) {
        return amConfigClient.searchList(request);
    }

    @Override
    public List<WrbBorrowListCustomizeVO> searchBorrowListByNid(String borrowNid) {
        return amTradeClient.searchBorrowListByNid(borrowNid);
    }

    @Override
    public CouponConfigVO getCouponByCouponCode(String couponCode) {
        return amTradeClient.getCouponByCouponCode(couponCode);
    }

    @Override
    public BorrowVO selectBorrowByBorrowNid(String borrowNid) {
        return null;
    }

    @Override
    public List<WrbBorrowTenderCustomizeVO> searchBorrowTenderByNidAndTime(String borrowNid, Date investTime) {
        return null;
    }

    @Override
    public WrbBorrowTenderSumCustomizeVO searchBorrowTenderSumByNidAndTime(String borrowNid, Date investTime) {
        return null;
    }

    @Override
    public List<BorrowTenderVO> getInvestDetail(Date invest_date, Integer limit, Integer page) {
        return null;
    }

}
