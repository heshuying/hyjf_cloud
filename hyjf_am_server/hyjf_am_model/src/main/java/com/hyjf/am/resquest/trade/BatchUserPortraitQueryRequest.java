/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitQueryRequest, v0.1 2018/6/29 9:21
 */
public class BatchUserPortraitQueryRequest implements Serializable {
    private List<BatchUserPortraitQueryVO> batchUserPortraitQueryVOList;

    public List<BatchUserPortraitQueryVO> getBatchUserPortraitQueryVOList() {
        return batchUserPortraitQueryVOList;
    }

    public void setBatchUserPortraitQueryVOList(List<BatchUserPortraitQueryVO> batchUserPortraitQueryVOList) {
        this.batchUserPortraitQueryVOList = batchUserPortraitQueryVOList;
    }

    @Override
    public String toString() {
        return "BatchUserPortraitQueryRequest{" +
                "batchUserPortraitQueryVOList=" + batchUserPortraitQueryVOList +
                '}';
    }
}
