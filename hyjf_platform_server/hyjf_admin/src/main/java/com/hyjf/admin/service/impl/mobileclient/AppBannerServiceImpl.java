package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.AppConfigClient;
import com.hyjf.admin.service.mobileclient.AppBannerService;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version AppBannerServiceImpl, v0.1 2018/7/11 11:49
 */
@Service
public class AppBannerServiceImpl  implements AppBannerService {

    @Autowired
    AppConfigClient appConfigClient;
    @Override
    public AppBannerResponse getRecordList(AppBannerRequest request) {
        return appConfigClient.findAppBannerList(request);
    }

    @Override
    public AppBannerResponse insertRecord(AdsWithBLOBsVO adsWithBLOBsVo) {
        return appConfigClient.insertAppBannerList(adsWithBLOBsVo);
    }

    @Override
    public AppBannerResponse updateRecord(AdsWithBLOBsVO adsWithBLOBsVo) {
        return appConfigClient.updateAppBannerList(adsWithBLOBsVo);
    }

    @Override
    public AppBannerResponse updateStatus(AdsWithBLOBsVO adsWithBLOBsVo) {
        return appConfigClient.updateAppBannerStatus(adsWithBLOBsVo);
    }

    @Override
    public AppBannerResponse deleteAppBanner(AdsWithBLOBsVO adsWithBLOBsVo) {
        return appConfigClient.deleteAppBanner(adsWithBLOBsVo);
    }

}
