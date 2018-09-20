/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.message;

import com.hyjf.am.vo.BaseVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: FindAliasesForMsgPushRequest, v0.1 2018/9/20 11:06
 */
public class FindAliasesForMsgPushRequest extends BaseVO {
    // 根据手机号【批量】查询推送别名时使用
    private List<String> mobiles;

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }
}
