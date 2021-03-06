package com.hyjf.admin.service.impl.mobileclient;

import com.hyjf.admin.client.AmAdminClient;
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
    AmAdminClient amAdminClient;
    /**
     * 获取广告管理列表数据
     * @param request
     * @return
     */
    @Override
    public AppBorrowImageResponse getRecordList(AppBorrowImageRequest request) {
        return amAdminClient.searchList(request);
    }

    /**
     * 获取详情
     * @param request
     * @return
     */
    @Override
    public AppBorrowImageResponse getRecord(AppBorrowImageRequest request) {
        return amAdminClient.searchInfo(request);
    }

    /**
     * 插入记录
     * @param record
     * @return
     * @throws Exception
     */
    @Override
    public AppBorrowImageResponse insertRecord(AppBorrowImageRequest record)  {
        return amAdminClient.insertInfo(record);
    }

    /**
     * 修改记录
     * @param record
     * @return
     * @throws Exception
     */
    @Override
    public AppBorrowImageResponse updateRecord(AppBorrowImageRequest record)  {
        return amAdminClient.updateInfo(record);

    }

    /**
     * 删除记录
     * @param record
     * @return
     * @throws Exception
     */
    @Override
    public AppBorrowImageResponse deleteRecord(AppBorrowImageRequest record)  {
        return amAdminClient.deleteInfo(record);
    }
}
