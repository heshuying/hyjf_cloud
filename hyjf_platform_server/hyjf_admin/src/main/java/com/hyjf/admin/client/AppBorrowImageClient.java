package com.hyjf.admin.client;

import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.vo.config.AppBorrowImageVO;

/**
 * @author lisheng
 * @version AppBorrowImageClient, v0.1 2018/7/13 9:41
 */

public interface AppBorrowImageClient {
    public AppBorrowImageResponse searchList(AppBorrowImageRequest appBorrowImageRequest);

    public AppBorrowImageResponse searchInfo(AppBorrowImageRequest appBorrowImageRequest);

    public AppBorrowImageResponse insertInfo(AppBorrowImageRequest appBorrowImageRequest);

    public AppBorrowImageResponse updateInfo(AppBorrowImageRequest appBorrowImageRequest);

    public AppBorrowImageResponse deleteInfo(AppBorrowImageRequest appBorrowImageRequest);

}
