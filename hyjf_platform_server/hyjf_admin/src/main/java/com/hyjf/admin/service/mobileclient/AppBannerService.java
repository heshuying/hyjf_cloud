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
     AppBannerResponse getRecordList(AppBannerRequest request);

     AppBannerResponse insertRecord(AdsWithBLOBsVO adsWithBLOBsVo);

     AppBannerResponse updateRecord(AdsWithBLOBsVO adsWithBLOBsVo);

     AppBannerResponse updateStatus(AdsWithBLOBsVO adsWithBLOBsVo);

     AppBannerResponse deleteAppBanner(AdsWithBLOBsVO adsWithBLOBsVo);


}
