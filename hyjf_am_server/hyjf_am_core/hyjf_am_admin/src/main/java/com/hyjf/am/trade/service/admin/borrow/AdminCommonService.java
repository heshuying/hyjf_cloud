/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow;

import java.util.List;

import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.service.BaseService;

/**
 * @author wangjun
 * @version AdminCommonService, v0.1 2018/8/16 9:38
 */
public interface AdminCommonService extends BaseService {
    /**
     * 还款方式下拉列表
     *
     * @return
     */
    List<BorrowStyle> selectBorrowStyleList();

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    List<HjhInstConfig> selectInstConfigList();
}
