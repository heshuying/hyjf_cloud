package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BorrowStyleWithBLOBs extends BorrowStyle implements Serializable {
    /**
     * 算法公式
     *
     * @mbggenerated
     */
    private String contents;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}