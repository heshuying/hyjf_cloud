/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: SubCommissionService, v0.1 2018/7/10 9:45
 */
public interface SubCommissionService {
    /**
     * 发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param loginUserId 当前登录用户id
     * @return
     */
    JSONObject searchDetails(Integer loginUserId);

    /**
     * 平台账户分佣
     * @auth sunpeikai
     * @param loginUserId 当前登录用户id
     * @param request 插入数据参数
     * @return
     */
    JSONObject subCommission(Integer loginUserId, SubCommissionRequest request);

    /**
     * 根据筛选条件查询分佣数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getSubCommissionCount(SubCommissionRequest request);

    /**
     * 根据筛选条件查询分佣数据list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<SubCommissionVO> searchSubCommissionList(SubCommissionRequest request);

    /**
     * 根据nameClass获取数据字典表的下拉列表
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    List<ParamNameVO> searchParamNameList(String nameClass);
}
