package com.hyjf.am.response.market;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.market.AdsTypeVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.am.vo.market.AppBannerVO;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author lisheng
 * @version AppBannerResponse, v0.1 2018/7/11 14:34
 */

public class AppBannerResponse extends Response<AdsVO> {

    private int recordTotal;
    private List<AdsTypeVO> adsTypeList;
    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public List<AdsTypeVO> getAdsTypeList() {
        return adsTypeList;
    }

    public void setAdsTypeList(List<AdsTypeVO> adsTypeList) {
        this.adsTypeList = adsTypeList;
    }
}
