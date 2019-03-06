/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh;

import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * 汇计划自动结束前一天未完全承接的债转
 *
 * @author liuyang
 * @version HjhAutoEndCreditService, v0.1 2018/6/28 10:49
 */
public interface HjhAutoEndCreditService extends BaseService {

    /**
     * 检索当天的未完全承接完成的债转
     *
     * @return
     */
    List<HjhDebtCredit> hjhDebtCreditList();


    /**
     * 更新债转状态
     *
     * @param hjhDebtCredit
     */
    void updateHjhDebtCreditStatus(HjhDebtCredit hjhDebtCredit) throws Exception;
}
