package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;

/**
 * @Author : huanghui
 */
public class DayCreditDetailResponse extends Response<DayCreditDetailVO> {

    private  Integer  count;

    private DayCreditDetailVO sumDayCreditDetailVO;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public DayCreditDetailVO getSumDayCreditDetailVO() {
        return sumDayCreditDetailVO;
    }

    public void setSumDayCreditDetailVO(DayCreditDetailVO sumDayCreditDetailVO) {
        this.sumDayCreditDetailVO = sumDayCreditDetailVO;
    }
}
