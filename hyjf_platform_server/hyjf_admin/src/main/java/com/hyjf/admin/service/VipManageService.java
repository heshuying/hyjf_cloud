/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.VipDetailListResponse;
import com.hyjf.am.response.admin.VipManageResponse;
import com.hyjf.am.response.admin.VipUpdateGradeListResponse;
import com.hyjf.am.resquest.admin.VipDetailListRequest;
import com.hyjf.am.resquest.admin.VipManageRequest;
import com.hyjf.am.resquest.admin.VipUpdateGradeListRequest;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;

import java.util.List;

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

    /**
     * 查询数据字典表
     * @param user_role
     * @return
     */
    List<ParamNameVO> getParamNameList(String user_role);

    /**
     * 获取部门信息
     * @return
     */
    List<OADepartmentCustomizeVO> getCrmDepartmentList();

    /**
     * 根据用户id获取用户投资信息
     * @param nid
     * @return
     */
    BorrowTenderVO getBorrowTenderList(String nid);
}
