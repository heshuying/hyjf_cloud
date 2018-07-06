/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;

import javax.validation.Valid; /**
 * @author yaoyong
 * @version CouponConfigService, v0.1 2018/7/5 11:24
 */
public interface CouponConfigService {
    /**
     * 获取优惠券发行列表
     * @param couponConfigRequest
     * @return
     */
    CouponConfigCustomizeResponse getRecordList(CouponConfigRequest couponConfigRequest);

    /**
     * 获取编辑页信息
     * @param couponConfigRequest
     * @return
     */
    CouponConfigResponse getCouponConfig(CouponConfigRequest couponConfigRequest);

    /**
     * 保存修改信息
     * @param request
     * @return
     */
    CouponConfigResponse saveCouponConfig(CouponConfigRequest request);

    /**
     * 添加发行信息
     * @param couponConfigRequest
     * @return
     */
    CouponConfigResponse insertAction(CouponConfigRequest couponConfigRequest);

    /**
     * 删除发行信息
     * @param couponConfigRequest
     * @return
     */
    CouponConfigResponse deleteAction(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse getAuditInfo(CouponConfigRequest ccfr);

    CouponConfigResponse updatrAudit(CouponConfigRequest couponConfigRequest);

    CouponUserResponse getIssueNumber(CouponUserRequest cur);
}
