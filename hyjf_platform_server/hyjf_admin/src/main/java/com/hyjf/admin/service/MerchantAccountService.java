/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountService, v0.1 2018/7/5 10:24
 */
public interface MerchantAccountService {
    /**
     * 更新商户子账户的金额信息
     * @param merchantAccounts
     * @return
     */
    boolean updateMerchantAccount( List<MerchantAccountVO> merchantAccounts);

    /**
     * 查询商户配置表相应的账户配置
     * @return
     */
    MerchantAccountResponse selectRecordList(MerchantAccountListRequest request);
}
