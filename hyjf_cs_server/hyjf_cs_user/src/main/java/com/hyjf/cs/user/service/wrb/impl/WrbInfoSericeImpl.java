package com.hyjf.cs.user.service.wrb.impl;

import com.hyjf.am.response.trade.WrbInvestRecordResponse;
import com.hyjf.am.response.trade.wrbInvestRecoverPlanResponse;
import com.hyjf.am.response.user.WrbAccountResponse;
import com.hyjf.am.response.user.WrbInvestSumResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderSumCustomizeVO;
import com.hyjf.am.resquest.api.WrbInvestRecordRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.wrb.WrbInfoServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    AmUserClient amUserClient;

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
    public BorrowAndInfoVO selectBorrowByBorrowNid(String borrowNid) {
        return amTradeClient.selectBorrowByBorrowNid(borrowNid);
    }

    @Override
    public List<WrbBorrowTenderCustomizeVO> searchBorrowTenderByNidAndTime(String borrowNid, Date investTime) {
        // 查询标的投资情况
        List<WrbBorrowTenderCustomizeVO> list = amTradeClient.selectWrbBorrowTender(borrowNid, investTime);
        if (!CollectionUtils.isEmpty(list)) {
            for (WrbBorrowTenderCustomizeVO vo : list) {
                String userId = vo.getUserId();
                UserVO user = amUserClient.findUserById(Integer.valueOf(userId));
                if (user != null) {
                    vo.setUsername(user.getUsername());
                }
            }
        }
        return null;
    }

    @Override
    public WrbBorrowTenderSumCustomizeVO searchBorrowTenderSumByNidAndTime(String borrowNid, Date investTime) {
        return null;
    }

    @Override
    public List<BorrowTenderVO> getInvestDetail(Date invest_date, Integer limit, Integer page) {
        return amTradeClient.getInvestDetail(invest_date, limit, page);
    }

    @Override
    public WrbInvestSumResponse getDaySum(Date date) {
        return amTradeClient.getDaySum(date);
    }

    @Override
    public WrbAccountResponse getCouponInfo(String userId) {
        return amTradeClient.getCouponInfo(userId);
    }

    @Override
    public WrbAccountResponse getAccountInfo(String userId) {
        return amTradeClient.getAccountInfo(userId);
    }

    @Override
    public WrbInvestRecordResponse getInvestRecord(WrbInvestRecordRequest request) {
        return amTradeClient.getInvestRecord(request);
    }

    @Override
    public wrbInvestRecoverPlanResponse getRecoverPlan(String userId, String investRecordId, String borrowNid) {
        return amTradeClient.getRecoverPlan(userId,investRecordId,borrowNid);
    }

}
