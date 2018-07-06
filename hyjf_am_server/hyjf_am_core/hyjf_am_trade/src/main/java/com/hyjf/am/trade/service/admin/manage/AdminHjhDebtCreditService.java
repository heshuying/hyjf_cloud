package com.hyjf.am.trade.service.admin.manage;

import com.hyjf.am.trade.dao.model.customize.admin.AdminHjhDebtCreditCustomize;

import java.util.List;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description:   后台列表查询 汇计划-转让记录
 */
public interface AdminHjhDebtCreditService {
    /**
     * 获取列表总数量
     * @param map
     * @return
     */
    Integer getListTotal(Map map);

    /**
     * 获取汇计划转让列表
     * @param map
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<AdminHjhDebtCreditCustomize> getList(Map map, int limitStart, int limitEnd);
}
