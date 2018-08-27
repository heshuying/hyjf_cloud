/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.List;

import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;

/**
 * @author PC-LIUSHOUYI
 * @version AdminBankAccountCheckCustomizeMapper, v0.1 2018/7/2 21:50
 */
public interface AdminBankAccountCheckCustomizeMapper {
    /**
     * 查询所有用户开户行数据
     * @param customize
     * @return
     */
    List<AdminBankAccountCheckCustomizeVO> queryAllBankOpenAccount(AdminBankAccountCheckCustomizeVO customize);

    /**
     * 根据流水号查找对应的未入账交易明细
     * @param bankSeqNo
     * @return
     */
    AdminBankAccountCheckCustomizeVO queryAccountDeatilByBankSeqNo(String bankSeqNo);

}
