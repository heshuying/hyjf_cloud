/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.customize.trade.CouponRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.CouponTenderCustomize;
import com.hyjf.am.trade.service.task.DataRecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version DataRecoverServiceImpl, v0.1 2018/6/25 14:45
 */
@Service
public class DataRecoverServiceImpl implements DataRecoverService {

    @Autowired
    private CouponRecoverCustomizeMapper couponRecoverCustomizeMapper;
    @Autowired
    AdminAccountCustomizeMapper adminAccountCustomizeMapper;

    @Override
    public List<CouponTenderCustomize> selectCouponRecover(String borrowNid, int repayTimeConfig) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("borrowNid", borrowNid);
        // 1 项目到期还款  2 收益期限到期还款
        paramMap.put("repayTimeConfig", repayTimeConfig);
        return couponRecoverCustomizeMapper.selectCouponRecoverAll(paramMap);
    }

    @Override
    public CouponRecoverCustomize selectCurrentCouponRecover(String couponTenderNid, int periodNow) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tenderNid", couponTenderNid);
        paramMap.put("periodNow", periodNow);
        return this.couponRecoverCustomizeMapper.selectCurrentCouponRecover(paramMap);
    }

    @Override
    @Transactional
    public int updateOfRepayTender(Account account) {
        int accountCnt = adminAccountCustomizeMapper.updateOfRepayTender(account);
        if (accountCnt >= 0) {
            return accountCnt;
        }
        return 0;
    }
}
