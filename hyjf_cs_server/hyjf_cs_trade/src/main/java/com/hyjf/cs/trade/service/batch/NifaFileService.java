/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

/**
 * @author yaoyong
 * @version NifaFileService, v0.1 2018/12/18 16:47
 */
public interface NifaFileService {
    /**
     * 互金下载反馈文件
     */
    void downloadFile();

    /**
     * 互金上传文件上报数据
     */
    void uploadFile();
}
