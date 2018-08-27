package com.hyjf.admin.service;

import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;

/**
 * 内容中心 -广告管理
 * @author：yinhui
 * @Date: 2018/7/19  14:19
 */
public interface ContentAdsService {

    ContentAdsResponse searchAction(ContentAdsRequest request);

    ContentAdsResponse inserAction(ContentAdsRequest request);

    ContentAdsResponse infoaction(Integer id);

    ContentAdsResponse updateAction(ContentAdsRequest request);

    ContentAdsResponse statusaction(Integer id);

    ContentAdsResponse deleteById(Integer id);

    ContentAdsResponse adsTypeList();

}
