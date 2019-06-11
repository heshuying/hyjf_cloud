/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon.impl;

import com.hyjf.am.resquest.trade.CouponUserSearchRequest;
import com.hyjf.am.trade.dao.mapper.auto.CouponUserMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponUserListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.auto.CouponUserExample;
import com.hyjf.am.trade.dao.model.customize.CouponUserListCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponUserService;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponUserServiceImpl, v0.1 2018/6/19 19:13
 */
@Service
public class CouponUserServiceImpl implements CouponUserService {

    @Autowired
    private CouponUserMapper couponUserMapper;
    @Autowired
    private CouponUserCustomizeMapper couponUserCustomizeMapper;
    @Autowired
    private CouponUserListCustomizeMapper couponUserListCustomizeMapper;

    @Override
    public List<CouponUser> selectCouponUser(int nowBeginDate, int nowEndDate,Integer type) {

        int BeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf));
        int EndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 1));

        //两天后
        int towBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 2));
        int towEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 3));

        //三天后
        int threeBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 3));
        int threeEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 4));

        //七天后
        int sevenBeginDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 7));
        int sevenEndDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf, 8));

        // 取得体验金出借（无真实出借）的还款列表
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(0);
        // 未使用
        criteria.andUsedFlagEqualTo(0);
        criteria.andEndTimeGreaterThanOrEqualTo(nowBeginDate);
        criteria.andEndTimeLessThan(nowEndDate);

        //type 1:即将失效，2：已经失效
        if(1 == type){
            CouponUserExample.Criteria criteria2 = example.createCriteria();
            criteria2.andDelFlagEqualTo(0);
            // 未使用
            criteria2.andUsedFlagEqualTo(0);
            criteria2.andEndTimeGreaterThanOrEqualTo(towBeginDate);
            criteria2.andEndTimeLessThan(towEndDate);
            example.or(criteria2);

            CouponUserExample.Criteria criteria3 = example.createCriteria();
            criteria3.andDelFlagEqualTo(0);
            // 未使用
            criteria3.andUsedFlagEqualTo(0);
            criteria3.andEndTimeGreaterThanOrEqualTo(threeBeginDate);
            criteria3.andEndTimeLessThan(threeEndDate);
            example.or(criteria3);

            CouponUserExample.Criteria criteria7 = example.createCriteria();
            criteria7.andDelFlagEqualTo(0);
            // 未使用
            criteria7.andUsedFlagEqualTo(0);
            // 截止日小于当前时间
            criteria7.andEndTimeGreaterThanOrEqualTo(sevenBeginDate);
            criteria7.andEndTimeLessThan(sevenEndDate);
            example.or(criteria7);
        }



        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
        return couponUserList;
    }

    @Override
    public Integer countCouponValid(Integer userId) {
        return couponUserCustomizeMapper.countCouponValid(userId);
    }


    @Override
    public List<CouponUserListCustomize> selectCouponUserList(Map<String, Object> mapParameter) {
        return couponUserListCustomizeMapper.selectCouponUserList(mapParameter);
    }


    @Override
    public Integer getUserCouponCount(Integer userId, String useFlag) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("usedFlag", useFlag);
        Integer count = couponUserCustomizeMapper.countCouponUser(param);
        return count;
    }

    @Override
    public Integer getIssueNumber(String couponCode) {
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria cra = example.createCriteria();
        cra.andCouponCodeEqualTo(couponCode);
        cra.andDelFlagEqualTo(CustomConstants.FALG_NOR);
        return this.couponUserMapper.countByExample(example);
    }

    @Override
    public int insertCouponUser(CouponUser couponUser) {
        int count = couponUserMapper.insertSelective(couponUser);
        return count;
    }

    @Override
    public boolean getSendRepeat(CouponUserSearchRequest couponUserSearchRequest) {
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andCouponCodeIn(couponUserSearchRequest.getCouponCodeList());
        criteria.andActivityIdEqualTo(couponUserSearchRequest.getActivityId());
        criteria.andUserIdEqualTo(new Integer(couponUserSearchRequest.getUserId()));
        criteria.andDelFlagEqualTo(0);
        List<CouponUser> couponUserList = this.couponUserMapper.selectByExample(couponUserExample);

        return couponUserList == null || couponUserList.size() == 0 ? true : false;
    }
}
