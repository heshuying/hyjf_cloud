package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;

/**
 * @author hesy
 * @version ApiCronUpdateRequest, v0.1 2018/7/17 16:20
 */
public class ApiCronUpdateRequest {
    /**
     * 待更新的状态值
     */
    Integer status;

    BorrowApicronVO apicronVO;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BorrowApicronVO getApicronVO() {
        return apicronVO;
    }

    public void setApicronVO(BorrowApicronVO apicronVO) {
        this.apicronVO = apicronVO;
    }
}
