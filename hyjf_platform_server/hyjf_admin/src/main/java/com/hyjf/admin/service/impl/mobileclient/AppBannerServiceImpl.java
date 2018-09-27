package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.mobileclient.AppBannerService;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version AppBannerServiceImpl, v0.1 2018/7/11 11:49
 */
@Service
public class AppBannerServiceImpl  implements AppBannerService {

    @Autowired
    AmAdminClient amAdminClient;

    @Override
    public AppBannerResponse getRecordList(AppBannerRequest request) {
        return amAdminClient.findAppBannerList(request);
    }

    @Override
    public AppBannerResponse getRecordById(AdsVO adsVO) {
        return amAdminClient.getRecordById(adsVO);
    }

    @Override
    public AppBannerResponse insertRecord(AdsVO adsVO) {
        return amAdminClient.insertAppBannerList(adsVO);
    }

    @Override
    public AppBannerResponse updateRecord(AdsVO adsVO) {
        return amAdminClient.updateAppBannerList(adsVO);
    }

    @Override
    public AppBannerResponse updateStatus(AdsVO adsVO) {
        return amAdminClient.updateAppBannerStatus(adsVO);
    }

    @Override
    public AppBannerResponse deleteAppBanner(AdsVO adsVO) {
        return amAdminClient.deleteAppBanner(adsVO);
    }


}
