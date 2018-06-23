package com.hyjf.am.resquest.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;

import java.util.List;

/**
 * 投资掉单请求参数封装
 * @author jun
 * @date 20180623
 */
public class BatchBorrowTenderCustomizeRequest extends Request {

    private List<BatchBorrowTenderCustomizeVO> batchBorrowTenderCustomizeList;

    public List<BatchBorrowTenderCustomizeVO> getBatchBorrowTenderCustomizeList() {
        return batchBorrowTenderCustomizeList;
    }

    public void setBatchBorrowTenderCustomizeList(List<BatchBorrowTenderCustomizeVO> batchBorrowTenderCustomizeList) {
        this.batchBorrowTenderCustomizeList = batchBorrowTenderCustomizeList;
    }
}
