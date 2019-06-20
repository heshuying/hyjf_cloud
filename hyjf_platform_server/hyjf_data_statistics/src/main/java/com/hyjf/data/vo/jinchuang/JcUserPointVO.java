/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.vo.jinchuang;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcUserPointVO, v0.1 2019/6/19 11:42
 */
public class JcUserPointVO extends BaseVO implements Serializable {

    private JSONObject jo;

    public JSONObject getJo() {
        return jo;
    }

    public void setJo(JSONObject jo) {
        this.jo = jo;
    }
}
