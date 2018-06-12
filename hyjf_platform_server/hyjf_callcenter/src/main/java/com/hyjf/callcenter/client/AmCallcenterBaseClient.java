/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.UserBean;

/**
 * @author wangjun
 * @version AmCallcenterBaseClient, v0.1 2018/6/6 10:02
 */
public interface AmCallcenterBaseClient {
    UserVO getUser(UserBean bean);
}
