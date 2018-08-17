package com.hyjf.admin.client;

import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;

/**
 * @author lisheng
 * @version AppConfigClient, v0.1 2018/7/11 14:39
 */
public interface AppConfigClient {

    public AppBannerResponse findAppBannerList(AppBannerRequest request);

    public AppBannerResponse insertAppBannerList(AdsWithBLOBsVO ads);

    public AppBannerResponse updateAppBannerList(AdsWithBLOBsVO ads);

    public AppBannerResponse updateAppBannerStatus(AdsWithBLOBsVO ads);

    public AppBannerResponse deleteAppBanner(AdsWithBLOBsVO ads);
}
