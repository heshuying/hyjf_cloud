/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.resquest.admin.VipUpdateGradeListRequest;

/**
 * @author yaoyong
 * @version VipManageService, v0.1 2018/7/2 14:59
 */
public interface VipManageService {
    JSONObject initVipManage();

    /**
     * 查询vip信息
     * @param vipManageRequest
     * @return
     */
    VipManageResponse searchList(VipManageRequest vipManageRequest);

    /**
     * 查询vip详情列表
     * @param detailListRequest
     * @return
     */
    VipDetailListResponse searchDetailList(VipDetailListRequest detailListRequest);

    /**
     * 查询vip升级详情列表
     * @param vgl
     * @return
     */
    VipUpdateGradeListResponse searchUpdateGradeList(VipUpdateGradeListRequest vgl);
}
