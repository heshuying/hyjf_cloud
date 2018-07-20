package com.hyjf.am.market.service;

import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;

/**
 * 内容中心-广告管理
 *
 * @author：yinhui
 * @Date: 2018/7/19  14:46
 */
public interface ContentAdsService {

    ContentAdsResponse searchActionPage(ContentAdsRequest request);

    /**
     * 添加广告管理
     *
     * @param request
     */
    boolean insertAction(ContentAdsRequest request);

    /**
     * 修改广告管理
     *
     * @param request
     */
    boolean updateAction(ContentAdsRequest request);

    /**
     * 根据id修改广告管理
     *
     * @param id
     */
    boolean deleteById(Integer id);
}
