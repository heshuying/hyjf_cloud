/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author fuqiang
 * @version STZHWhiteListResponse, v0.1 2018/6/12 13:57
 */
public class STZHWhiteListResponse extends Response<STZHWhiteListVO> {
    private int count;

    private List<HjhInstConfigVO> regionList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<HjhInstConfigVO> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<HjhInstConfigVO> regionList) {
        this.regionList = regionList;
    }
}
