package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ContentArticleVO;

import java.util.List;
import java.util.Map;

public class ContentArticleResponse extends Response<ContentArticleVO> {
    List<Map<String, Object>> responseList;

    private int recordTotal;

    public List<Map<String, Object>> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Map<String, Object>> responseList) {
        this.responseList = responseList;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
