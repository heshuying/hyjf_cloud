/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;

/**
 * @author yaoyong
 * @version CouponConfigClient, v0.1 2018/7/5 11:50
 */
public interface CouponConfigClient {
    CouponConfigCustomizeResponse getRecordList(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse getCouponConfig(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse saveCouponConfig(CouponConfigRequest request);

    CouponConfigResponse insertAction(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse deleteAction(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse getAuditInfo(CouponConfigRequest ccfr);

    CouponConfigResponse updateAuditInfo(CouponConfigRequest couponConfigRequest);

    CouponUserResponse getIssueNumber(CouponUserRequest cur);
}
