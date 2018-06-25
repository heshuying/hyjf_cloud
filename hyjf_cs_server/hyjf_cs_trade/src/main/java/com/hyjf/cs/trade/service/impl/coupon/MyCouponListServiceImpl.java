package com.hyjf.cs.trade.service.impl.coupon;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListServiceImpl, v0.1 2018/6/23 14:08
 */
@Service
public class MyCouponListServiceImpl extends BaseTradeServiceImpl implements com.hyjf.cs.trade.service.coupon.MyCouponListService {
    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 我的优惠券列表（已使用）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListUsed(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("1");
        return amTradeClient.selectMyCouponList(requestBean);
    }

    /**
     * 我的优惠券列表（未使用）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListUnUsed(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("0");
        return amTradeClient.selectMyCouponList(requestBean);
    }

    /**
     * 我的优惠券列表（已失效）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListInValid(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("4");
        return amTradeClient.selectMyCouponList(requestBean);
    }


}
