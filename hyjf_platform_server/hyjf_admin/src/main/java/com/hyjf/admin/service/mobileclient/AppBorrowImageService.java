package com.hyjf.admin.service.mobileclient;

import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;

/**
 * @author lisheng
 * @version AppBorrowImageService, v0.1 2018/7/12 16:43
 */

public interface AppBorrowImageService {
     AppBorrowImageResponse getRecordList(AppBorrowImageRequest request);

    /**
     * 获取单个
     *
     * @return
     */
     AppBorrowImageResponse getRecord(AppBorrowImageRequest request);

     AppBorrowImageResponse insertRecord(AppBorrowImageRequest record) ;
     AppBorrowImageResponse updateRecord(AppBorrowImageRequest record) ;

     AppBorrowImageResponse deleteRecord(AppBorrowImageRequest record) ;
}
