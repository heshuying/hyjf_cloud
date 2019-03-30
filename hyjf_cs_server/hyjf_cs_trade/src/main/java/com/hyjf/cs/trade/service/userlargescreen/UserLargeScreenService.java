/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.userlargescreen;

import com.hyjf.cs.trade.bean.UserLargeScreenResultBean;
import com.hyjf.cs.trade.bean.UserLargeScreenTwoResultBean;

/**
 * @author tanyy
 * @version UserLargeScreenService, v0.1 2019/3/18 14:28
 */
public interface UserLargeScreenService {

    UserLargeScreenResultBean getOnePage();

    /**
     * 运营大屏接口-屏幕二数据获取
     * @return
     */
    UserLargeScreenTwoResultBean getTwoPage();
}
