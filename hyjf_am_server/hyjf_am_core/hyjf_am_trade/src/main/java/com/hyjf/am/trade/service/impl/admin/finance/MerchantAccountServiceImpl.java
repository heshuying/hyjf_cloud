/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.finance;

import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.dao.model.auto.MerchantAccountExample;
import com.hyjf.am.trade.service.admin.finance.MerchantAccountService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountServiceImpl, v0.1 2018/7/5 13:44
 */
@Service
public class MerchantAccountServiceImpl extends BaseServiceImpl implements MerchantAccountService {

    /**
     * 获取商户子账户列表
     * @return
     * @param request
     * @param offset
     * @param limit
     */
    @Override
    public List<MerchantAccount> selectRecordList(MerchantAccountListRequest request, int offset, int limit) {
        MerchantAccountExample example = new MerchantAccountExample();
        example.setOrderByClause("sort ASC");
        return merchantAccountMapper.selectByExample(example);
    }

    @Override
    public Integer updateByPrimaryKeySelective(MerchantAccount merchantAccount) {
        int cnt = merchantAccountMapper.updateByPrimaryKeySelective(merchantAccount);
        return cnt;
    }

    @Override
    public int countByExample(){
        MerchantAccountExample example = new MerchantAccountExample();
        return merchantAccountMapper.countByExample(example);
    }

}
