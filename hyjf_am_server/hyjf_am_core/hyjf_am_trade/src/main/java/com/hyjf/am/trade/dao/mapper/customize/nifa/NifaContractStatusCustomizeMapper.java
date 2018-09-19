/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.nifa;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.customize.NifaContractStatusCustomize;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaContractStatusCustomizeMapper, v0.1 2018/7/6 11:39
 */
public interface NifaContractStatusCustomizeMapper {

    /**
     * 获取前一天合同状态变更记录
     *
     * @return
     */
    List<NifaContractStatusCustomize> selectNifaContractStatus();

    /**
     * 获取未更新合同状态的完全债转的订单
     *
     * @return
     */
    List<BorrowRecover> selectBorrowRecoverCredit();
}
