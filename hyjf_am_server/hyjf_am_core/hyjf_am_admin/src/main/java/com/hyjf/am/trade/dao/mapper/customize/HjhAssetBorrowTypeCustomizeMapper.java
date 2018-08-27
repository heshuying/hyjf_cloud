package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.resquest.admin.AdminBorrowFlowRequest;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;

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
    public int countRecord(AdminBorrowFlowRequest hjhAssetBorrowTypeCustomize);

    /**
     *
     * 列表
     * @param form
     * @return
     */
    public List<HjhAssetBorrowtype> getRecordList(AdminBorrowFlowRequest form);
}
