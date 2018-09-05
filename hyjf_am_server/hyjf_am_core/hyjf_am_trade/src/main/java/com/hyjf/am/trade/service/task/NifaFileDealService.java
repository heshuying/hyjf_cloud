/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task;

import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaFileDealService, v0.1 2018/9/4 16:53
 */
public interface NifaFileDealService extends BaseService {

    /**
     * 生成产品登记业务数据zip合同模板文件zip
     *
     * @param businessDataFileName
     * @param contractTemplateFileName
     * @return
     */
    boolean insertMakeFileReportLog(String businessDataFileName, String contractTemplateFileName);

    /**
     * 拉取未成功上传的文件名集合
     *
     * @return
     */
    List<NifaReportLog> selectNifaReportLogList();

    /**
     * 更新处理结果
     *
     * @param nifaReportLog
     * @return
     */
    boolean updateNifaReportLog(NifaReportLog nifaReportLog);

    /**
     * 解析实时反馈文件
     *
     * @param filePathName
     * @return
     */
    String UploadResultFileRead(String filePathName);

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    List<NifaReportLog> selectNifaReportLogDownloadPath();

    /**
     * SFTP下载文件
     *
     * @param filePathDate
     * @return
     */
    boolean downloadFiles(String filePathDate);
}
