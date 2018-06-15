/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.trans;

import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.BaseService;

/**
 * @author zhangqingqing
 * @version MobileModifyService, v0.1 2018/6/14 16:47
 */
public interface MobileModifyService extends BaseService{
    boolean checkForMobileModify(String newMobile, String smsCode);

    MobileModifyResultBean queryForMobileModify(Integer userId);
}
