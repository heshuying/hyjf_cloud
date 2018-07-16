package com.hyjf.admin.service.mobileclient;

import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lisheng
 * @version AppBannerService, v0.1 2018/7/11 11:48
 */

public interface AppBannerService {
    public AppBannerResponse getRecordList(AppBannerRequest request);

    public AppBannerResponse insertRecord(AdsWithBLOBsVO adsWithBLOBsVo);

    public AppBannerResponse updateRecord(AdsWithBLOBsVO adsWithBLOBsVo);

    public AppBannerResponse updateStatus(AdsWithBLOBsVO adsWithBLOBsVo);

    public AppBannerResponse deleteAppBanner(AdsWithBLOBsVO adsWithBLOBsVo);

}
