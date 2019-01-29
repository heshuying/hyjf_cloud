/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.vo.admin.NifaReportLogVO;

import java.util.List;

/**
 * @author yaoyong
 * @version NifaFileService, v0.1 2018/12/18 16:47
 */
public interface NifaFileDualService {

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
    List<NifaReportLogVO> selectNifaReportLogList();

    /**
     * 更新处理结果
     *
     * @param nifaReportLog
     * @return
     */
    boolean updateNifaReportLog(NifaReportLogVO nifaReportLog);

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
    List<NifaReportLogVO> selectNifaReportLogDownloadPath();

    /**
     * SFTP下载文件
     *
     * @param filePathDate
     * @return
     */
    boolean downloadFiles(String filePathDate);

    /**
     * 统计系统生成zip文件
     *
     * @param businessZhaiqFileName
     * @param beforeSdfDay
     * @return
     */
    boolean insertMonitorMakeZhaiFileReportLog(String businessZhaiqFileName, String beforeSdfDay);

    /**
     * 统计系统生成zip文件
     *
     * @param businessJinrFileName
     * @param beforeSdfDay
     * @return
     */
    boolean insertMonitorMakeJinrFileReportLog(String businessJinrFileName, String beforeSdfDay);

    /**
     * 统计二期接口下载
     *
     * @param nifaReportLog
     * @param feedBackType
     * @param filePathDate
     * @return
     */
    boolean downloadFilesByUrl(NifaReportLogVO nifaReportLog, String feedBackType, String filePathDate);
}
