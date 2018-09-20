package com.hyjf.admin.service.mobileclient;

import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsTypeVO;
import com.hyjf.am.vo.market.AdsWithBLOBsVO;
import org.apache.commons.collections.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
      * @param request
      * @return
      */
     AppBannerResponse getRecordById(AppBannerRequest request);

     /**
      * 新增广告
      * @param adsWithBLOBsVo
      * @return
      */
     AppBannerResponse insertRecord(AdsWithBLOBsVO adsWithBLOBsVo);

     /**
      * 修改广告
      * @param adsWithBLOBsVo
      * @return
      */
     AppBannerResponse updateRecord(AdsWithBLOBsVO adsWithBLOBsVo);

     /**
      * 修改状态
      * @param adsWithBLOBsVo
      * @return
      */
     AppBannerResponse updateStatus(AdsWithBLOBsVO adsWithBLOBsVo);

     /**
      * 删除广告
      * @param adsWithBLOBsVo
      * @return
      */
     AppBannerResponse deleteAppBanner(AdsWithBLOBsVO adsWithBLOBsVo);


}
