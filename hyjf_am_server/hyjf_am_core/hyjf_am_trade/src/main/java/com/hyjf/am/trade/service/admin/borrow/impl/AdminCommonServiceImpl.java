/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowStyleExample;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfig;
import com.hyjf.am.trade.dao.model.auto.HjhInstConfigExample;
import com.hyjf.am.trade.service.admin.borrow.AdminCommonService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.CustomConstants;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjun
 * @version AdminCommonServiceImpl, v0.1 2018/8/16 9:38
 */
@Service
public class AdminCommonServiceImpl extends BaseServiceImpl implements AdminCommonService {
    /**
     * 还款方式下拉列表
     *
     * @return
     */
    @Override
    public List<BorrowStyle> selectBorrowStyleList() {
        BorrowStyleExample example = new BorrowStyleExample();
        BorrowStyleExample.Criteria cra = example.createCriteria();
        cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        return borrowStyleMapper.selectByExample(example);
    }

    /**
     * 资产来源下拉列表
     *
     * @return
     */
    @Override
    public List<HjhInstConfig> selectInstConfigList() {
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        cra.andDelFlagEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
        return hjhInstConfigMapper.selectByExample(example);
    }
}
