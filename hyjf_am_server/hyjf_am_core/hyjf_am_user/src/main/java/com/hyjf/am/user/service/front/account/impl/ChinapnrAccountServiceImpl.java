/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.account.impl;

import com.hyjf.am.user.service.front.account.ChinapnrAccountService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version ChinapnrAccountServiceImpl, v0.1 2018/9/8 11:21
 */
@Service
public class ChinapnrAccountServiceImpl extends BaseServiceImpl implements ChinapnrAccountService {

    @Override
    public int selectUserIdByUsrcustid(Long chinapnrUsrcustid) {
        return accountChinapnrCustomizeMapper.selectUserIdByUsrcustid(chinapnrUsrcustid);
    }
}
