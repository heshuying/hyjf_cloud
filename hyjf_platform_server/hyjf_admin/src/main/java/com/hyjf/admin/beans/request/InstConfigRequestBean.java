package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author by xiehuili on 2018/7/6.
 */
public class InstConfigRequestBean extends BaseRequest implements Serializable {

    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
