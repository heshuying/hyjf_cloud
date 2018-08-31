/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;

import javax.validation.Valid;
import java.util.List;

/**
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

    CouponUserResponse getIssueNumber(String couponCode);

    /**
     * 获取项目类别
     * @return
     */
    List<BorrowProjectTypeVO> getCouponProjectTypeList();

    /**
     * 通过用户Id获取审核人的真实姓名
     * @param userId
     * @return
     */
    String getAdminInfoByUserId(String userId);
}
