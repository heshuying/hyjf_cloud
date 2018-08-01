package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
public interface HjhAssetBorrowTypeCustomizeMapper {
    /**
     *
     * 件数
     * @param hjhAssetBorrowTypeCustomize
     * @return
     */
    public int countRecord(HjhAssetBorrowtype hjhAssetBorrowTypeCustomize);

    /**
     *
     * 列表
     * @param form
     * @return
     */
    public List<HjhAssetBorrowtype> getRecordList(AdminBorrowFlowRequest form);
}
