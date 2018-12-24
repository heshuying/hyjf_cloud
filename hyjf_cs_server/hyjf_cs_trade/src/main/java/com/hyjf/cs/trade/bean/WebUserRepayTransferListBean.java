package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.user.WebUserRepayTransferCustomizeVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : huanghui
 */
public class WebUserRepayTransferListBean implements Serializable {

    private List<WebUserRepayTransferCustomizeVO> list;

    public List<WebUserRepayTransferCustomizeVO> getList() {
        return list;
    }

    public void setList(List<WebUserRepayTransferCustomizeVO> list) {
        this.list = list;
    }
}
