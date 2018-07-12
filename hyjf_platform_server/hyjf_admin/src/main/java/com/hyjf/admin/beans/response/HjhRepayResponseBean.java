package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;

public class HjhRepayResponseBean {

    private Integer total;

    private List<HjhRepayVO> recordList;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<HjhRepayVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<HjhRepayVO> recordList) {
        this.recordList = recordList;
    }
}
