/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.CouponConfigClient;
import com.hyjf.admin.service.CouponConfigService;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version CouponConfigServiceImpl, v0.1 2018/7/5 11:24
 */
@Service
public class CouponConfigServiceImpl implements CouponConfigService {
    @Autowired
    CouponConfigClient couponConfigClient;
    /**
     * 获取优惠券发行列表
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigCustomizeResponse getRecordList(CouponConfigRequest couponConfigRequest) {
        return couponConfigClient.getRecordList(couponConfigRequest);
    }

    /**
     * 获取编辑页信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse getCouponConfig(CouponConfigRequest couponConfigRequest) {
        return couponConfigClient.getCouponConfig(couponConfigRequest);
    }

    /**
     * 保存修改信息
     * @param request
     * @return
     */
    @Override
    public CouponConfigResponse saveCouponConfig(CouponConfigRequest request) {
        return couponConfigClient.saveCouponConfig(request);
    }

    /**
     * 添加发行信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse insertAction(CouponConfigRequest couponConfigRequest) {
        return couponConfigClient.insertAction(couponConfigRequest);
    }

    /**
     * 删除发行信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse deleteAction(CouponConfigRequest couponConfigRequest) {
        return couponConfigClient.deleteAction(couponConfigRequest);
    }

    /**
     * 获取审核信息
     * @param ccfr
     * @return
     */
    @Override
    public CouponConfigResponse getAuditInfo(CouponConfigRequest ccfr) {
        return couponConfigClient.getAuditInfo(ccfr);
    }

    /**
     * 更新审核信息
     * @param couponConfigRequest
     * @return
     */
    @Override
    public CouponConfigResponse updatrAudit(CouponConfigRequest couponConfigRequest) {
        return couponConfigClient.updateAuditInfo(couponConfigRequest);
    }

    /**
     * 根据优惠券编号查询
     * @param cur
     * @return
     */
    @Override
    public CouponUserResponse getIssueNumber(CouponUserRequest cur) {
        return couponConfigClient.getIssueNumber(cur);
    }
}
