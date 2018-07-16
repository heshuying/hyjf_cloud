package com.hyjf.am.vo.market;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author lisheng
 * @version AppBannerVO, v0.1 2018/7/11 14:32
 */

public class AppBannerVO extends BaseVO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 前台时间接收
     */
    private String startCreate;

    private String endCreate;

    private String ids;


    public String getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(String startCreate) {
        this.startCreate = startCreate;
    }

    public String getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(String endCreate) {
        this.endCreate = endCreate;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
