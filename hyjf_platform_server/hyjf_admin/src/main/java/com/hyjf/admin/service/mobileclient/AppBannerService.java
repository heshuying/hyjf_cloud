package com.hyjf.admin.service.mobileclient;

import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsVO;

/**
 * @author lisheng
 * @version AppBannerService, v0.1 2018/7/11 11:48
 */

public interface AppBannerService {
     /**
      * 查询广告列表
      * @param request
      * @return
      */
     AppBannerResponse getRecordList(AppBannerRequest request);

     /**
      * 获取广告根据id
      * @param adsVO
      * @return
      */
     AppBannerResponse getRecordById(AdsVO adsVO);

     /**
      * 新增广告
      * @param adsVO
      * @return
      */
     AppBannerResponse insertRecord(AdsVO adsVO);

     /**
      * 修改广告
      * @param adsVO
      * @return
      */
     AppBannerResponse updateRecord(AdsVO adsVO);

     /**
      * 修改状态
      * @param adsVO
      * @return
      */
     AppBannerResponse updateStatus(AdsVO adsVO);

     /**
      * 删除广告
      * @param adsVO
      * @return
      */
     AppBannerResponse deleteAppBanner(AdsVO adsVO);


}
