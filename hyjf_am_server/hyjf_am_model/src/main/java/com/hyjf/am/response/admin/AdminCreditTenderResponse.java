package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;

import java.util.Map;

/**
 * 承接明细
 * @author zhangyk
 * @date 2018/7/12 20:10
 */
public class AdminCreditTenderResponse extends Response<BorrowCreditTenderVO> {

    private Integer count;

    private Map<String,Object> sumData;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, Object> getSumData() {
        return sumData;
    }

    public void setSumData(Map<String, Object> sumData) {
        this.sumData = sumData;
    }
}
