package com.hyjf.admin.service.mobileclient;

import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;

/**
 * @author lisheng
 * @version AppBorrowImageService, v0.1 2018/7/12 16:43
 */

public interface AppBorrowImageService {
    public AppBorrowImageResponse getRecordList(AppBorrowImageRequest request);

    /**
     * 获取单个
     *
     * @return
     */
    public AppBorrowImageResponse getRecord(AppBorrowImageRequest request);

    public AppBorrowImageResponse insertRecord(AppBorrowImageRequest record) throws Exception;
    public AppBorrowImageResponse updateRecord(AppBorrowImageRequest record) throws Exception;

    public AppBorrowImageResponse deleteRecord(AppBorrowImageRequest record) throws Exception;
}
