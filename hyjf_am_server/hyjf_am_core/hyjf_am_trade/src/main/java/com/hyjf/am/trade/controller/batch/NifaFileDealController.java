/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.service.task.NifaFileDealService;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaFileDealController, v0.1 2018/9/4 16:36
 */
@Api(value = "互金上传下载文件任务")
@RestController
@RequestMapping("/am-trade/nifa_file_deal")
public class NifaFileDealController extends BaseController {

    @Autowired
    private NifaFileDealService nifaFileDealService;

    /**
     * 获取上传地址前缀
     */
    @Value("${hyjf.nifa.upload.url}")
    private String UPLOAD_URL;

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    /**
     * 反馈文件下载地址
     */
    @Value("${hyjf.nifa.feedback.path}")
    private String FEED_BACK_PATH;

    /**
     * 互金反馈文件下载地址
     */
    @Value("${hyjf.nifa.download.path}")
    private String DOWNLOAD_PATH;

    /**
     * 互金上传文件上报数据
     *
     * @return
     */
    @RequestMapping("/upload_file")
    public boolean uploadFile() {
        try {
            logger.info("------【互金上传文件】上传开始------");

            // 地址末尾撇拼接反斜杠
            String feedBackPath = FEED_BACK_PATH;
            if (!feedBackPath.endsWith("/")) {
                feedBackPath = feedBackPath.concat("/");
            }

            // 获取前一天日期yyyyMMdd
            String beforDay = GetDate.yyyyMMdd.format(GetDate.countDate(new Date(), 5, -1));
            // 登记系统业务数据文件名
            String businessDataFileName = COM_SOCIAL_CREDIT_CODE.concat(beforDay).concat("33001");
            // 登记系统合同模板文件
            String contractTemplateFileName = COM_SOCIAL_CREDIT_CODE.concat(beforDay).concat("34001");

            // 拉取数据生成文件并更新数据库
            boolean fileMakeResult = nifaFileDealService.insertMakeFileReportLog(businessDataFileName, contractTemplateFileName);
            if (!fileMakeResult) {
                logger.error("【互金上传文件】文件作成失败！");
            }

            // 拉取未成功上传的文件名集合（可能会包含之前上传未成功的数据）
            List<NifaReportLog> nifaReportLogList = nifaFileDealService.selectNifaReportLogList();
            if (nifaReportLogList == null || nifaReportLogList.size() == 0) {
                logger.info("【互金上传文件】未获取到需要上传的数据！");
                return true;
            }

            // 循环文件名集合
            for (NifaReportLog nifaReportLog : nifaReportLogList) {
                // 记录更新时间
                nifaReportLog.setUpdateTime(new Date());

                // 判断该条数据对应上传文件类型（后面有时间在数据库增加类型字段）
                String uploadType = nifaReportLog.getUploadName().substring(26, 28);

                // 拼接请求地址
                String requestURL = UPLOAD_URL.concat("?systemid=2&stype=")
                        .concat(uploadType).concat("&sourcePath=")
                        .concat(nifaReportLog.getUploadPath())
                        .concat(nifaReportLog.getUploadName())
                        .concat(".zip");
                requestURL.replace(":", "%3A");
                requestURL.replace("/", "%2F");

                // 文件上传请求
                String uploadResult = HttpDeal.get(requestURL);
                // 上传结果解析
                JSONObject jsonObject = JSONObject.parseObject(uploadResult);
                if (!"true".equals(jsonObject.get("success"))) {
                    logger.error("【互金上传文件】上传错误，返回错误信息：" + jsonObject);
                    // 更新上传结果
                    nifaReportLog.setFileUploadStatus(2);
                    nifaFileDealService.updateNifaReportLog(nifaReportLog);
                    continue;
                }

                // 读取上传异步回调文件
                // 异步回传文件地址与前置服务配置匹配写到环境变量
                String filePathName = feedBackPath.concat(nifaReportLog.getUploadName()).concat(".txt");
                String fileReadResult = nifaFileDealService.UploadResultFileRead(filePathName);
                if (StringUtils.isBlank(fileReadResult)) {
                    logger.error("【互金上传文件】互金上传文件反馈信息为空");
                    nifaReportLog.setFileUploadStatus(2);
                } else if ("ERROR".equals(fileReadResult)) {
                    logger.error("【互金上传文件】上传反馈文件解析失败");
                    nifaReportLog.setFileUploadStatus(2);
                } else {
                    logger.info("【互金上传文件】上传状态：" + fileReadResult);
                    nifaReportLog.setFileUploadStatus(1);
                }
                // 更新上传结果
                boolean result = nifaFileDealService.updateNifaReportLog(nifaReportLog);
                if (!result) {
                    logger.error("【互金上传文件】更新上传结果失败!");
                }
            }
        } catch (Exception e) {
            logger.error("------【互金上传文件】上传失败------");
            e.printStackTrace();
            return false;
        } finally {
            logger.info("------【互金上传文件】上传结束------");
            return true;
        }
    }

    /**
     * 互金下载反馈文件
     *
     * @return
     */
    @RequestMapping("/download_file")
    public boolean downFile() {
        logger.info("------【互金下载反馈文件】处理开始------");
        try {

            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

            // 获取当天日期yyyyMMdd
            String nowDay = yyyyMMdd.format(new Date());
            // 获取未成功下载日志的数据
            List<NifaReportLog> nifaReportLogList = nifaFileDealService.selectNifaReportLogDownloadPath();
            if (null == nifaReportLogList || nifaReportLogList.size() <= 0) {
                logger.info("【互金下载反馈文件】未获取到需要下载反馈文件的数据！");
                return true;
            }

            // 循环文件名集合
            for (NifaReportLog nifaReportLog : nifaReportLogList) {
                // 记录更新时间
                nifaReportLog.setUpdateTime(new Date());
                String filePathDate = nifaReportLog.getUploadName().substring(18, 26);
                // 当天上传的数据当天不下载反馈文件
                if(filePathDate.equals(nowDay)){
                    continue;
                }
                Integer downloadResult = this.nifaFileDealService.downloadFiles(filePathDate) ? 1 : 2;
                // 更新结果
                nifaReportLog.setFeedbackResult(downloadResult);
                // 文件地址
                nifaReportLog.setFeedbackPath(DOWNLOAD_PATH + filePathDate);
                // 文件名称
                nifaReportLog.setFeedbackName(filePathDate);
                // 更新下载结果
                boolean result = nifaFileDealService.updateNifaReportLog(nifaReportLog);
                if (!result) {
                    logger.error("【互金下载反馈文件】更新下载结果失败!");
                }
            }
        } catch (Exception e) {
            logger.info("------【互金下载反馈文件】处理失败------");
            e.printStackTrace();
            return false;
        }
        logger.info("------【互金下载反馈文件】处理结束------");
        return true;
    }

}
