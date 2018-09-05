/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.dao.model.customize.AdminPoundageCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TradePoundageService, v0.1 2018/9/3 15:20
 */
public interface TradePoundageService extends BaseService {
    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageCount(PoundageListRequest request);
    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AdminPoundageCustomize> searchPoundageList(PoundageListRequest request);

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    AdminPoundageCustomize getPoundageSum(PoundageListRequest request);

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    AdminPoundageCustomize getPoundageById(Integer id);

    /**
     * 审核-更新poundage
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer updatePoundage(PoundageCustomizeVO poundageCustomizeVO);
}
