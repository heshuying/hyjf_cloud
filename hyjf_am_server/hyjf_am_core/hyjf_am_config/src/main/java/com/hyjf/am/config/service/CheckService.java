/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.CouponCheck;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Map; /**
 * @author yaoyong
 * @version CheckService, v0.1 2018/7/4 11:45
 */
public interface CheckService {
    int countCouponCheck(AdminCouponCheckRequest request);

    List<CouponCheck> searchCouponCheck(AdminCouponCheckRequest request, int offset, int limit);

    void deleteCheckList(int id);

    int insertCoupon(CouponCheck couponCheck);

    CouponCheck selectCoupon(String id);

    Boolean updateCoupon(AdminCouponCheckRequest request);
}
