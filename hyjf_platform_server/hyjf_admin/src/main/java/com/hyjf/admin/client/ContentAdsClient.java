package com.hyjf.admin.client;

import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;

/**
 * 内容中心 -广告管理
 * @author：yinhui
 * @Date: 2018/7/19  14:24
 */
public interface ContentAdsClient {

    ContentAdsResponse searchAction(ContentAdsRequest request);

    ContentAdsResponse inserAction(ContentAdsRequest request);

    ContentAdsResponse updateAction(ContentAdsRequest request);

    ContentAdsResponse deleteById(Integer id);
}
