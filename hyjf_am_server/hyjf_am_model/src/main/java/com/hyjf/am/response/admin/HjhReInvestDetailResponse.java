package com.hyjf.am.response.admin;

import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;

import java.util.List;

/**
 * 汇计划-计划资金-复投详情
 * @Author : huanghui
 */
public class HjhReInvestDetailResponse {

    private List<HjhReInvestDetailVO> ResultList;

    public List<HjhReInvestDetailVO> getResultList() {
        return ResultList;
    }

    public void setResultList(List<HjhReInvestDetailVO> resultList) {
        ResultList = resultList;
    }
}
