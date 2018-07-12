package com.hyjf.am.response.admin;

import com.hyjf.am.vo.trade.hjh.HjhRepayVO;

import java.util.List;

/**
 * @Author : huanghui
 */
public class HjhRepayResponse {

    private List<HjhRepayVO> recordList;

    public List<HjhRepayVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<HjhRepayVO> recordList) {
        this.recordList = recordList;
    }
}
