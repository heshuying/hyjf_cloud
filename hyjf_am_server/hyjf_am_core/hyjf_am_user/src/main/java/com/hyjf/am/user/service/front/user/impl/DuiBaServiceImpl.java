/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.service.front.user.DuiBaService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.CreditConsumeResultVO;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;
import org.springframework.stereotype.Service;

/**
 * @author wangjun
 * @version DuiBaServiceImpl, v0.1 2019/6/6 10:39
 */
@Service
public class DuiBaServiceImpl extends BaseServiceImpl implements DuiBaService {

    @Override
    public CreditConsumeResultVO updateUserPoints(CreditConsumeParams consumeParams) {
        return null;
    }
}
