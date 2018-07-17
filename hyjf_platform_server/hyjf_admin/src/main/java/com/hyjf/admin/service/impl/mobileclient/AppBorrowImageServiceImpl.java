package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.AppBorrowImageClient;
import com.hyjf.admin.service.mobileclient.AppBorrowImageService;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version AppBorrowImageServiceImpl, v0.1 2018/7/12 16:43
 */
@Service
public class AppBorrowImageServiceImpl implements AppBorrowImageService {
    @Autowired
    AppBorrowImageClient appBorrowImageClient;

    @Override
    public AppBorrowImageResponse getRecordList(AppBorrowImageRequest request) {
        return appBorrowImageClient.searchList(request);


    }

    @Override
    public AppBorrowImageResponse getRecord(AppBorrowImageRequest request) {
        return appBorrowImageClient.searchInfo(request);
    }

    @Override
    public AppBorrowImageResponse insertRecord(AppBorrowImageRequest record) throws Exception {
        return appBorrowImageClient.insertInfo(record);
    }

    @Override
    public AppBorrowImageResponse updateRecord(AppBorrowImageRequest record) throws Exception {
        return appBorrowImageClient.updateInfo(record);

    }

    @Override
    public AppBorrowImageResponse deleteRecord(AppBorrowImageRequest record) throws Exception {
        return appBorrowImageClient.deleteInfo(record);
    }
}
