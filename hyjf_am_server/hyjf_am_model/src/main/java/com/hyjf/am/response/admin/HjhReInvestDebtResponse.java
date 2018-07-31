package com.hyjf.am.response.admin;

import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;

import java.util.List;

public class HjhReInvestDebtResponse {

    private List<HjhPlanCapitalCustomizeVO> ResultList;

    public List<HjhPlanCapitalCustomizeVO> getResultList() {
        return ResultList;
    }

    public void setResultList(List<HjhPlanCapitalCustomizeVO> resultList) {
        ResultList = resultList;
    }
}
