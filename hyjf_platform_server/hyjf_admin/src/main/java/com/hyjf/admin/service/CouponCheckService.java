/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.user.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yaoyong
 * @version CouponCheckService, v0.1 2018/7/3 16:33
 */
public interface CouponCheckService {
    /**
     * 获取优惠券手动批量发放列表
     * @param acr
     * @return
     */
    CouponCheckResponse serchCouponList(AdminCouponCheckRequest acr);

    /**
     * 删除优惠券信息
     * @param acr
     * @return
     */
    CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr);

    /**
     * 上传文件
     * @param request
     * @param response
     * @return
     */
    CouponCheckResponse uploadFile(HttpServletRequest request, HttpServletResponse response);

    /**
     * 下载文件
     * @param id
     * @param response
     */
    void downloadFile(String id, HttpServletResponse response);

    /**
     * 批量审核优惠券
     * @param path
     * @param response
     * @return
     */
    boolean batchCheck(Integer path, HttpServletResponse response, String userId) throws Exception;

    /**
     *修改审核状态
     * @param request
     * @return
     */
    boolean updateCoupon(AdminCouponCheckRequest request);
}