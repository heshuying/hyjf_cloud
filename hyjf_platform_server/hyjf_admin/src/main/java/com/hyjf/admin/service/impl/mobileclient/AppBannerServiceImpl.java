package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.mobileclient.AppBannerService;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsTypeVO;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version AppBannerServiceImpl, v0.1 2018/7/11 11:49
 */
@Service
public class AppBannerServiceImpl  implements AppBannerService {

    @Autowired
    AmMarketClient amMarketClient;
    @Override
    public AppBannerResponse getRecordList(AppBannerRequest request) {
        return amMarketClient.findAppBannerList(request);
    }

    @Override
    public AppBannerResponse insertRecord(AdsWithBLOBsVO adsWithBLOBsVo) {
        return amMarketClient.insertAppBannerList(adsWithBLOBsVo);
    }

    @Override
    public AppBannerResponse updateRecord(AdsWithBLOBsVO adsWithBLOBsVo) {
        return amMarketClient.updateAppBannerList(adsWithBLOBsVo);
    }

    @Override
    public AppBannerResponse updateStatus(AdsWithBLOBsVO adsWithBLOBsVo) {
        return amMarketClient.updateAppBannerStatus(adsWithBLOBsVo);
    }

    @Override
    public AppBannerResponse deleteAppBanner(AdsWithBLOBsVO adsWithBLOBsVo) {
        return amMarketClient.deleteAppBanner(adsWithBLOBsVo);
    }


}
