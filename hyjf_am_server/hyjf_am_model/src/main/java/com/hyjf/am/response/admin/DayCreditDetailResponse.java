package com.hyjf.am.response.admin;

import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;

import java.util.List;

public class DayCreditDetailResponse {

    private List<DayCreditDetailVO> ResultList;

    public List<DayCreditDetailVO> getResultList() {
        return ResultList;
    }

    public void setResultList(List<DayCreditDetailVO> resultList) {
        ResultList = resultList;
    }
}
