package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.WebProjectListCsVO;

import java.io.Serializable;
import java.util.List;

public class BorrowProjectListBean implements Serializable {

    private List<WebProjectListCsVO> list;

    private int nowTime;

    public List<WebProjectListCsVO> getList() {
        return list;
    }

    public void setList(List<WebProjectListCsVO> list) {
        this.list = list;
    }

    public int getNowTime() {
        return nowTime;
    }

    public void setNowTime(int nowTime) {
        this.nowTime = nowTime;
    }
}
