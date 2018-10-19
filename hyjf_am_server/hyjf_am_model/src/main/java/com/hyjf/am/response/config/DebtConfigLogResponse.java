package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.DebtConfigLogVO;

import java.util.List;


public class DebtConfigLogResponse extends Response<DebtConfigLogVO> {
    private int count;
    private List<DebtConfigLogVO> logVOList;
    private DebtConfigRequest debtConfigRequest;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DebtConfigLogVO> getLogVOList() {
        return logVOList;
    }

    public void setLogVOList(List<DebtConfigLogVO> logVOList) {
        this.logVOList = logVOList;
    }

    public DebtConfigRequest getDebtConfigRequest() {
        return debtConfigRequest;
    }

    public void setDebtConfigRequest(DebtConfigRequest debtConfigRequest) {
        this.debtConfigRequest = debtConfigRequest;
    }
}
