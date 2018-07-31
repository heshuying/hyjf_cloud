/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.landingpage;

import java.math.BigDecimal;

/**
 * @author wangjun
 * @version LandingPageService, v0.1 2018/7/30 16:46
 */
public interface LandingPageService {
    /**
     * 获取累计收益
     *
     * @return
     */
    BigDecimal selectInterestSum();
}
