package com.hyjf.am.response.admin;

import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalVO;

import java.util.List;

/**
 * 汇计划-计划资金
 * @Author : huanghui
 */
public class HjhPlanCapitalResponse {


    private List<HjhPlanCapitalVO> ResultList;

    public List<HjhPlanCapitalVO> getResultList() {
        return ResultList;
    }

    public void setResultList(List<HjhPlanCapitalVO> resultList) {
        ResultList = resultList;
    }
}
