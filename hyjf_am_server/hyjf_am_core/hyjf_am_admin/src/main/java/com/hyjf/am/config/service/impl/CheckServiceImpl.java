/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.dao.mapper.auto.CouponCheckMapper;
import com.hyjf.am.config.dao.model.auto.CouponCheck;
import com.hyjf.am.config.dao.model.auto.CouponCheckExample;
import com.hyjf.am.config.service.CheckService;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.common.util.GetDate;

/**
 * @author yaoyong
 * @version CheckServiceImpl, v0.1 2018/7/4 11:45
 */
@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    CouponCheckMapper couponCheckMapper;

    /**
     * 根据 条件查询表中记录数
     * @param mapParam
     * @return
     */
    @Override
    public int countCouponCheck(AdminCouponCheckRequest request) {
        CouponCheckExample couponCheckExample = new CouponCheckExample();
        CouponCheckExample.Criteria criteria = couponCheckExample.createCriteria();
        //未删除
        criteria.andDeFlagEqualTo(0);

        String createTimeStart =request.getCreateTimeStart();
        String createTimeEnd =request.getCreateTimeEnd();
        Integer status = request.getStatus();
        if (StringUtils.isNotBlank(createTimeStart) && StringUtils.isNotBlank(createTimeEnd)) {
            criteria.andCreateTimeBetween( GetDate.str2Timestamp(createTimeStart), GetDate.str2Timestamp(createTimeEnd));
        }

        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        return couponCheckMapper.countByExample(couponCheckExample);
    }

    /**
     * 列表查询
     * @param request
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<CouponCheck> searchCouponCheck(AdminCouponCheckRequest request, int limitStart, int limitEnd) {
        CouponCheckExample couponCheckExample = new CouponCheckExample();
        couponCheckExample.setOrderByClause("create_time desc");
        CouponCheckExample.Criteria criteria = couponCheckExample.createCriteria();
        //未删除
        criteria.andDeFlagEqualTo(0);

        String createTimeStart = request.getCreateTimeStart();
        String createTimeEnd = request.getCreateTimeEnd();
        Integer status = request.getStatus();

        if (StringUtils.isNotBlank(createTimeStart) && StringUtils.isNotBlank(createTimeEnd)) {
            criteria.andCreateTimeBetween( GetDate.str2Timestamp(createTimeStart), GetDate.str2Timestamp(createTimeEnd));
        }

        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (limitStart != -1) {
            couponCheckExample.setLimitStart(limitStart);
            couponCheckExample.setLimitEnd(limitEnd);
        }

        List<CouponCheck> couponChecks = couponCheckMapper.selectByExample(couponCheckExample);
        return couponChecks;
    }

    /**
     * 删除优惠券信息
     * @param id
     */
    @Override
    public void deleteCheckList(int id) {
        CouponCheck couponCheck = new CouponCheck();
        couponCheck.setDeFlag(1);
        CouponCheckExample couponCheckExample = new CouponCheckExample();
        CouponCheckExample.Criteria criteria = couponCheckExample.createCriteria();
        criteria.andIdEqualTo(id);
        couponCheckMapper.updateByExampleSelective(couponCheck, couponCheckExample);
    }

    /**
     * 插入优惠券信息
     * @param couponCheck
     * @return
     */
    @Override
    public int insertCoupon(CouponCheck couponCheck) {
        int count = couponCheckMapper.insert(couponCheck);
        return count;
    }

    /**
     * 根据id查询优惠券
     * @param id
     * @return
     */
    @Override
    public CouponCheck selectCoupon(String id) {
        return couponCheckMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public Boolean updateCoupon(AdminCouponCheckRequest request) {
        String id = String.valueOf(request.getId());
        Integer status = Integer.valueOf(request.getStatus());
        String remark = request.getRemark();
        String[] split = id.split(",");
        String ids = split[0];
        CouponCheck couponCheck = new CouponCheck();
        couponCheck.setStatus(status);
        couponCheck.setRemark(remark);
        CouponCheckExample couponCheckExample = new CouponCheckExample();
        CouponCheckExample.Criteria criteria = couponCheckExample.createCriteria();
        criteria.andIdEqualTo(Integer.valueOf(ids));
        return this.couponCheckMapper.updateByExampleSelective(couponCheck, couponCheckExample) > 0 ? true : false;
    }
}
