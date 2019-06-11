/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.user.CreditConsumeResultVO;
import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;

/**
 * @author wangjun
 * @version DuiBaService, v0.1 2019/6/6 10:37
 */
public interface DuiBaService extends BaseService {
    /**
     * 兑吧扣积分回调相关业务处理
     * @param consumeParams
     * @return
     */
    CreditConsumeResultVO updateUserPoints(CreditConsumeParams consumeParams);
}
