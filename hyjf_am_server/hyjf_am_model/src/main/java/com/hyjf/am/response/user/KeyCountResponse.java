/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.KeyCountVO;

/**
 * @author tanyy
 * @version KeyCountResponse.java, v0.1 2018年7月17日 下午3:22:17
 */
public class KeyCountResponse extends Response<KeyCountVO>{
    private  Integer  count;
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

}
