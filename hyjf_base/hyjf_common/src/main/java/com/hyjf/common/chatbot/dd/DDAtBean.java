/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.chatbot.dd;

import java.util.List;

/**
 * '@对象的实体类
 * @author liubin
 * @version DDAtBean, v0.1 2018/7/25 14:49
 */
public class DDAtBean {
    // 被@人的手机号
    private List atMobiles;
    // @所有人时:true,否则为:false
    private boolean isAtAll = false;

    public List getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(List atMobiles) {
        this.atMobiles = atMobiles;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }
}
