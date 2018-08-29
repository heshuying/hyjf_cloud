package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

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

    /**
     * 资料上传
     *
     * @return
     * @throws Exception
     */
    LinkedList<BorrowCommonImage> uploadFile(HttpServletRequest request) throws Exception;

}
