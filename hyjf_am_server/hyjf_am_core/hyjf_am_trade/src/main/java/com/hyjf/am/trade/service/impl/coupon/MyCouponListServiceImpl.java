package com.hyjf.am.trade.service.impl.coupon;

import com.hyjf.am.trade.dao.mapper.customize.coupon.MyCouponListCustomizeMapper;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListServiceImpl, v0.1 2018/6/22 19:15
 */
@Service
public class MyCouponListServiceImpl implements com.hyjf.am.trade.service.coupon.MyCouponListService {
    @Resource
    MyCouponListCustomizeMapper myCouponListCustomizeMapper;

    /**
     * 检索我的优惠券列表
     * @auther: hesy
     * @date: 2018/6/22
     */
    @Override
    public List<MyCouponListCustomizeVO> selectUserCouponList(String userId, String usedFlag, Integer limitStart, Integer limitEnd){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", usedFlag);
        param.put("userId", userId);

        if (limitStart != null) {
            param.put("limitStart", limitStart);
        }else {
            param.put("limitStart", -1);
        }
        if (limitEnd != null) {
            param.put("limitEnd", limitEnd);
        }else {
            param.put("limitEnd", -1);
        }

        return myCouponListCustomizeMapper.selectMyCouponList(param);
    }

    /**
     * 统计总记录数
     * @param userId
     * @param usedFlag
     * @return
     */
    @Override
    public Integer countUserCouponList(String userId, String usedFlag){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", usedFlag);
        param.put("userId", userId);

        Integer result =  myCouponListCustomizeMapper.countMyCouponList(param);
        if(result == null){
            result = 0;
        }
        return result;
    }
}
