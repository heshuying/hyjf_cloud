/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.trade.dao.model.auto.SubCommission;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;
import com.hyjf.am.vo.admin.SubCommissionVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: SubCommissionService, v0.1 2018/7/10 10:15
 */
public interface SubCommissionService {

    /**
     * 查询发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param
     * @return
     */
    List<SubCommissionListConfig> searchSubCommissionListConfig();

    /**
     * 插入发起账户分佣数据
     * @auth sunpeikai
     * @param
     * @return
     */
    boolean insertSubCommission(SubCommissionVO subCommissionVO);

    /**
     * 根据订单号查询分佣信息
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    SubCommission searchSubCommissionByOrderId(String orderId);

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
    List<SubCommission> searchSubCommissionList(SubCommissionRequest request);
    /**
     * 发起平台账户分佣
     * @auth sunpeikai
     * @param
     * @return
     */
    JSONObject subCommission(SubCommissionRequest request);
}
