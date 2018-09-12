/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.trade.dao.model.auto.PoundageDetail;
import com.hyjf.am.trade.dao.model.auto.PoundageLedger;
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

    /**
     * 查询手续费分账配置
     * @auth sunpeikai
     * @param
     * @return
     */
    PoundageLedger getPoundageLedgerById(int id);

    /**
     * 查询详情的count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPoundageDetailCount(AdminPoundageDetailRequest request);

    /**
     * 查询详情的list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PoundageDetail> searchPoundageDetailList(AdminPoundageDetailRequest request);
}
