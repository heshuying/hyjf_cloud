/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.NifaContractTemplateResponse;
import com.hyjf.am.response.admin.NifaReportLogResponse;
import com.hyjf.am.response.hgreportdata.nifa.*;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaReportLogVO;
import com.hyjf.am.vo.hgreportdata.nifa.*;
import com.hyjf.am.vo.trade.BorrowVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.*;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.bean.hgreportdata.nifa.*;
import com.hyjf.cs.trade.service.batch.NifaFileDualService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version NifaFileServiceImpl, v0.1 2018/12/18 16:47
 */
@Service
public class NifaFileDualServiceImpl extends BaseTradeServiceImpl implements NifaFileDualService {

    /**
     * 合同要素信息 CSV文件名
     */
    private static String INV_CONTRACT = "inv_contract";
    /**
     * 合同模板约定条款 CSV文件名
     */
    private static String GENERAL_CONTRACT = "general_contract";
    /**
     * 借款人项目还款记录 CSV文件名
     */
    private static String PRO_REPAY_RECORD = "pro_repay_record";
    /**
     * 合同状态变更 CSV文件名
     */
    private static String CONTRACT_STATE = "contract_state";
    /**
     * 出借人回款记录 CSV文件名
     */
    private static String INV_RETURN_RECORD = "inv_return_record";
    /**
     * 合同模板 PDF文件名
     */
    private static String TEMPLE_NAME = "合同模板";

    /**
     * 获取上传地址前缀
     */
    @Value("${hyjf.nifa.upload.path}")
    private String UPLOAD_PATH;

    /**
     * 下载保存路径
     */
    @Value("${hyjf.nifa.download.path}")
    private String DOWNLOAD_PATH;

    /**
     * ftp服务器地址
     */
    @Value("${hyjf.nifa.hostName}")
    private String HOST_NAME;

    /**
     * ftp服务器用户名
     */
    @Value("${hyjf.nifa.userName}")
    private String USER_NAME;

    /**
     * ftp服务器密码
     */
    @Value("${hyjf.nifa.passWord}")
    private String PASS_WORD;

    /**
     * ftp服务器端口
     */
    @Value("${hyjf.nifa.hostPost}")
    private String HOST_POST;

    /**
     * ftp服务器下载地址
     */
    @Value("${hyjf.nifa.download.url}")
    private String DOWNLOAD_URL;

    private String baseTradeUrl = "http://AM-TRADE/am-trade/nifaFileDeal/";
    private String baseMessageUrl = "http://CS-MESSAGE/cs-message/nifaStatistical/";

    @Autowired
    BaseClient baseClient;

    /**
     * 生成产品登记业务数据zip合同模板文件zip
     *
     * @param businessDataFileName
     * @param contractTemplateFileName
     * @return
     */
    @Override
    public boolean insertMakeFileReportLog(String businessDataFileName, String contractTemplateFileName) {

        // 获取前一天日期yyyyMMdd
        String beforDay = GetDate.yyyyMMdd.format(GetDate.countDate(new Date(), 5, -1));

        boolean result = true;

        // 上传文件生成地址
        String uploadPath = UPLOAD_PATH;
        if (!uploadPath.endsWith("/")) {
            uploadPath = uploadPath.concat("/").concat(beforDay).concat("/");
        } else {
            uploadPath = uploadPath.concat(beforDay).concat("/");
        }

        // 需要打压缩zip的文件集合
        StringBuffer sb = new StringBuffer();
        // 打压缩zip合同模板
        StringBuffer sbTemplate = new StringBuffer();
        // 生成csv文件名中文集合
        StringBuffer fileName = new StringBuffer();

        // true说明文件已存在
        if (!this.selectNifaReportLogByFileName(contractTemplateFileName)) {
            // 合同模板约定条款
            List<NifaContractTemplateBean> nifaContractTemplate = selectNifaContractTemplate();
            if (null != nifaContractTemplate && nifaContractTemplate.size() > 0) {
                // 合同数据变更时压缩最新合同模板
                // redis获取合同模板的下载地址
                String key = RedisConstants.TEMPLATE_UPLOAD_URL + nifaContractTemplate.get(0).getTempletNid();
                String httpUrl = RedisUtils.get(key);
                // redis未存放从数据库取
                if (StringUtils.isBlank(httpUrl)) {
                    FddTempletVO fddTemplet = this.selectFddTemplet(nifaContractTemplate.get(0).getTempletNid());
                    if (null != fddTemplet) {
                        httpUrl = fddTemplet.getFileUrl();
                        // redis设值
                        RedisUtils.set(key, httpUrl);
                    } else {
                        logger.error("【互金上传文件】:合同模板下载地址为获取到！模板编号：" + nifaContractTemplate.get(0).getTempletNid());
                    }
                }
                result = downLoadFromUrl(httpUrl, nifaContractTemplate.get(0).getTempletNid() + ".pdf", uploadPath);
                if (result) {
                    sbTemplate.append(uploadPath).append(nifaContractTemplate.get(0).getTempletNid()).append(".pdf,");
                    if (!writeZip(sbTemplate, uploadPath + contractTemplateFileName)) {
                        result = false;
                        logger.error("【互金上传文件】:合同数据变更时压缩最新合同模板失败！");
                    }
                }
                // 更新数据库插入一条数据
                // 上传日志增加记录
                // 插入上传日志
                NifaReportLogVO nifaReportLogVO = new NifaReportLogVO();
                // 文件包信息
                nifaReportLogVO.setPackageInformation(TEMPLE_NAME);
                // 上传时间：初始
                nifaReportLogVO.setUploadTime(0);
                // 上传状态：初始
                nifaReportLogVO.setFileUploadStatus(0);
                // 反馈文件解析状态：初始
                nifaReportLogVO.setFeedbackResult(0);
                // 上传文件包名
                nifaReportLogVO.setUploadName(contractTemplateFileName);
                // 上传文件路径
                nifaReportLogVO.setUploadPath(uploadPath);
                // 数据日期
                nifaReportLogVO.setHistoryDate(beforDay);
                nifaReportLogVO.setCreateTime(new Date());
                nifaReportLogVO.setCreateUserId(3);
                // 更新放到原子层
                if (!insertNifaReportLog(nifaReportLogVO)) {
                    result = false;
                    logger.error("【互金上传文件】:合同模板约定条款、上传日志增加记录失败！");
                }
                // list类型转换
                List<Object> listNCT = toObject(nifaContractTemplate);
                // 导出csv文件
                logger.info("【互金上传文件】合同模板约定条款记录生成！！");
                if (createCSVFile(listNCT, null, uploadPath, GENERAL_CONTRACT, StringPool.ASCII_TABLE[01])) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(GENERAL_CONTRACT).append(".csv,");
                    fileName.append("合同模板约定条款");
                } else {
                    result = false;
                    logger.error("【互金上传文件】:合同模板约定条款导出CSV失败！");
                }
            }
        }

        if (!this.selectNifaReportLogByFileName(businessDataFileName)) {
            // 合同要素信息(记录出借信息)
            List<NifaContractEssenceBean> nifaContractEssenceList = selectNifaContractEssenceList();
            if (null != nifaContractEssenceList && nifaContractEssenceList.size() > 0) {
                // list类型转换
                List<Object> listNCE = toObject(nifaContractEssenceList);
                // 导出csv文件
                logger.info("【互金上传文件】合同要素信息记录生成！！");
                if (createCSVFile(listNCE, null, uploadPath, INV_CONTRACT, StringPool.ASCII_TABLE[01])) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(INV_CONTRACT).append(".csv,");
                    fileName.append("合同要素信息");
                } else {
                    result = false;
                    logger.error("【互金上传文件】:合同要素信息导出CSV失败！");
                }
            }

            // 借款人项目还款记录
            List<NifaRepayInfoBean> nifaRepayInfoList = selectNifaRepayInfoList();
            if (null != nifaRepayInfoList && nifaRepayInfoList.size() > 0) {
                // list类型转换
                List<Object> listNRI = toObject(nifaRepayInfoList);
                // 导出csv文件
                logger.info("【互金上传文件】借款人项目还款记录生成！！");
                if (createCSVFile(listNRI, null, uploadPath, PRO_REPAY_RECORD, StringPool.ASCII_TABLE[01])) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(PRO_REPAY_RECORD).append(".csv,");
                    fileName.append("借款人项目还款记录");
                } else {
                    result = false;
                    logger.error("【互金上传文件】:借款人项目还款记录导出CSV失败！");
                }
            }

            // 合同状态变更
            List<NifaContractStatusBean> nifaContractStatus = selectNifaContractStatus();
            if (null != nifaContractStatus && nifaContractStatus.size() > 0) {
                // list类型转换
                List<Object> listNCS = toObject(nifaContractStatus);
                // 导出csv文件
                logger.info("【互金上传文件】合同状态变更记录生成！！");
                if (createCSVFile(listNCS, null, uploadPath, CONTRACT_STATE, StringPool.ASCII_TABLE[01])) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(CONTRACT_STATE).append(".csv,");
                    fileName.append("合同状态变更");
                } else {
                    result = false;
                    logger.error("【互金上传文件】:合同状态变更导出CSV失败！");
                }
            }

            // 出借人回款记录
            List<NifaReceivedPaymentsBean> nifaReceivedPaymentsList = selectNifaReceivedPaymentsList();
            if (null != nifaReceivedPaymentsList && nifaReceivedPaymentsList.size() > 0) {
                // list类型转换
                List<Object> listNRP = toObject(nifaReceivedPaymentsList);
                // 导出csv文件
                logger.info("【互金上传文件】出借人回款记录生成！！");
                if (createCSVFile(listNRP, null, uploadPath, INV_RETURN_RECORD, StringPool.ASCII_TABLE[01])) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(INV_RETURN_RECORD).append(".csv,");
                    fileName.append("出借人回款记录");
                } else {
                    result = false;
                    logger.error("【互金上传文件】:出借人回款记录导出CSV失败！");
                }
            }
            // 上传日志增加记录
            if (result && StringUtils.isNotBlank(fileName)) {
                // 插入上传日志
                NifaReportLogVO nifaReportLog = new NifaReportLogVO();
                // 文件包信息
                nifaReportLog.setPackageInformation(fileName.toString());
                // 上传时间：初始
                nifaReportLog.setUploadTime(0);
                // 上传状态：初始
                nifaReportLog.setFileUploadStatus(0);
                // 反馈文件解析状态：初始
                nifaReportLog.setFeedbackResult(0);
                // 上传文件包名
                nifaReportLog.setUploadName(businessDataFileName);
                // 上传文件路径
                nifaReportLog.setUploadPath(uploadPath);
                // 数据日期
                nifaReportLog.setHistoryDate(beforDay);
                nifaReportLog.setCreateTime(new Date());
                nifaReportLog.setCreateUserId(3);
                if (!insertNifaReportLog(nifaReportLog)) {
                    result = false;
                    logger.error("【互金上传文件】:上传日志增加记录失败！");
                }
            }
            // zip打压缩包
            if (result && StringUtils.isNotBlank(sb)) {
                logger.info("【互金上传文件】压缩包生成！！");
                if (!writeZip(sb, uploadPath + businessDataFileName)) {
                    result = false;
                    logger.error("【互金上传文件】压缩包生成失败！！");
                }
            } else {
                logger.error("【互金上传文件】数据文件未生成！！");
                result = false;
            }
        }
        return result;

    }

    /**
     * 解析实时反馈文件
     *
     * @param filePathName
     * @return
     */
    @Override
    public String UploadResultFileRead(String filePathName) {
        try {
            File file = new File(filePathName);
            StringBuilder localStrBulider = new StringBuilder();
            if (file.isFile() && file.exists()) {
                try {
                    FileInputStream inputStreamReader = new FileInputStream(file);
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStreamReader, "UTF-8"));

                    String lineStr = null;
                    try {
                        while ((lineStr = bufferReader.readLine()) != null) {
                            logger.info(lineStr);
                            localStrBulider.append(lineStr);
                        }
                        logger.info("【互金上传文件】上传状态异步返回文件解析成功，文件内容：" + localStrBulider.toString());
                        return localStrBulider.toString();
                    } catch (IOException e) {
                        logger.error("【互金上传文件】上传状态异步返回文件读取失败!");
                        logger.error(e.getMessage());
                        return "ERROR";
                    } finally {
                        if (bufferReader != null) {
                            bufferReader.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("【互金上传文件】上传状态异步返回文件不支持编码格式!");
                    logger.error(e.getMessage());
                    return "ERROR";
                }
            } else {
                logger.error("【互金上传文件】上传状态异步返回文件不存在!");
                return "ERROR";
            }
        } catch (Exception e) {
            logger.error("【互金上传文件】上传状态异步返回文件解析错误！");
            logger.error(e.getMessage());
            return "ERROR";
        }
    }

    /**
     * SFTP下载文件
     *
     * @param filePathDate
     * @return
     */
    @Override
    public boolean downloadFiles(String filePathDate) {
        SFTPParameter para = new SFTPParameter();
        Boolean re = false;

        //ftp服务器地址
        para.hostName = HOST_NAME;
        //ftp服务器用户名
        para.userName = USER_NAME;
        //ftp服务器密码
        para.passWord = PASS_WORD;
        //ftp服务器端口
        para.port = HOST_POST == null ? 0 : Integer.valueOf(HOST_POST);
        //ftp服务器文件目录
        para.downloadPath = "/error/" + filePathDate;

        //本地保存目录
        String savePath = "";
        if (DOWNLOAD_PATH.endsWith("/")) {
            savePath = DOWNLOAD_PATH + filePathDate;
        } else {
            savePath = DOWNLOAD_PATH + "/" + filePathDate;
        }

        //如果文件夹不存在则创建
        File file = new File(savePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        // 设定本地保存目录
        para.savePath = savePath;

        try {
            if (!FtpUtil.downloadFiles(para)) {
                logger.info("【互金下载反馈文件】下载ftp文件失败、文件夹名称：" + filePathDate);
            } else {
                // 下载后文件打压缩包
                StringBuffer sb = new StringBuffer();
                sb.append(savePath + "/err_package.txt,");
                sb.append(savePath + "/file_count.txt,");
                sb.append(savePath + "/err_inv_contract.txt,");
                sb.append(savePath + "/err_general_contract.txt,");
                sb.append(savePath + "/err_pro_repay_record.txt,");
                sb.append(savePath + "/err_contract_state.txt,");
                sb.append(savePath + "/err_inv_return_record.txt,");
                if (writeZip(sb, savePath + "/" + filePathDate)) {
                    re = true;
                } else {
                    logger.info("【互金下载反馈文件】文件压缩失败、文件夹名称：" + filePathDate);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.info("【互金下载反馈文件】下载ftp文件失败、文件夹名称：" + filePathDate);
        }
        return re;
    }

    /**
     * 统计系统生成zip文件
     *
     * @param businessZhaiqFileName
     * @param beforeSdfDay
     * @return
     */
    @Override
    public boolean insertMonitorMakeZhaiFileReportLog(String businessZhaiqFileName, String beforeSdfDay) {

        // --> 生成标的信息文件
//        Query query = new Query();
//        Criteria criteria = Criteria.where("reportStatus").is("0").and("historyData").is(beforeSdfDay);
//        query.addCriteria(criteria);
//        List<NifaBorrowInfoVO> nifaBorrowInfoEntities = nifaBorrowInfoDao.find(query);
        NifaBorrowInfoVO request = new NifaBorrowInfoVO();
        request.setReportStatus("0");
        request.setHistoryData(beforeSdfDay);
        List<NifaBorrowInfoVO> nifaBorrowInfoEntities = selectNifaBorrowInfoByHistoryDate(request);
        if (null == nifaBorrowInfoEntities || nifaBorrowInfoEntities.size() <= 0) {
            logger.info("【互金上传文件】:统计二期未查询到互联网债权类融资项目信息！");
            return true;
        }

        // 获取前一天日期yyyyMMdd
        String beforDay = beforeSdfDay.replaceAll("-", "");
        // 生成csv文件名中文集合
        String fileName = "互联网债权类融资项目信息，互联网债权类融资出借人信息，互联网债权类融资借款人信息";

        // 判定当前文件是否已生成
        businessZhaiqFileName = selectMaxFileName(businessZhaiqFileName, beforDay, fileName);
        if (StringUtils.isBlank(businessZhaiqFileName)) {
            logger.error("【互金上传文件】:统计二期互联网债权类融资项目信息文件名处理错误！");
            return false;
        }

        boolean result = true;
        // 上传文件生成地址
        String uploadPath = UPLOAD_PATH;
        if (!uploadPath.endsWith("/")) {
            uploadPath = uploadPath.concat("/").concat(beforDay).concat("/");
        } else {
            uploadPath = uploadPath.concat(beforDay).concat("/");
        }

        // 需要打压缩zip的文件集合
        StringBuffer sb = new StringBuffer();

        // list类型转换
        List<Object> listBIE = toObject(nifaBorrowInfoEntities);
        // 导出txt文件
        logger.info("【互金上传文件】统计二期标的信息生成txt！！");
        boolean re;
        try {
            re = createTxtFile(listBIE, null, uploadPath, CustomConstants.NIFA_BORROW_INFO_TYPE, "|");
            if (re) {
                sb.append(uploadPath).append(CustomConstants.NIFA_BORROW_INFO_TYPE).append(".txt,");
            } else {
                logger.error("【互金上传文件】:互联网债权类融资项目信息导出TXT失败！");
                return false;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("【互金上传文件】统计二期标的信息生成txt抛出异常！！");
            return false;
        }

        // 处理借款人和出借人数据
//        for (NifaBorrowInfoVO nifaBorrowInfoEntity : nifaBorrowInfoEntities) {
//            // --> 获取出借人信息
//            Query queryWait = new Query();
//            Criteria criteriaWait = Criteria.where("message").is(nifaBorrowInfoEntity.getMessage()).and("reportStatus").is("0");
//            queryWait.addCriteria(criteriaWait);
//            // 更新需要导出文件的出借人数据为2：待处理
//            List<NifaTenderInfoVO> nifaTenderInfoEntities = this.nifaTenderInfoDao.find(queryWait);
//            if (null != nifaTenderInfoEntities && nifaTenderInfoEntities.size() >= 0) {
//                Update update = new Update();
//                update.set("reportStatus", "2").set("updateTime", new Date());
//                nifaTenderInfoDao.updateAll(queryWait, update);
//            } else {
//                logger.error("【互金上传文件】统计二期标获取标的对应的出借人信息失败！！message:" + nifaBorrowInfoEntity.getMessage());
//            }
//            // 更新需要导出文件的借款人数据为2：待处理
//            List<NifaBorrowerInfoVO> nifaBorrowerInfoEntities = this.nifaBorrowerInfoDao.find(queryWait);
//            if (null != nifaBorrowerInfoEntities && nifaBorrowerInfoEntities.size() >= 0) {
//                Update update = new Update();
//                update.set("reportStatus", "2").set("updateTime", new Date());
//                nifaBorrowerInfoDao.updateAll(queryWait, update);
//            } else {
//                logger.error("【互金上传文件】统计二期标获取标的对应的借款人信息失败！！message:" + nifaBorrowInfoEntity.getMessage());
//            }
//        }

//        Query queryTender = new Query();
//        Criteria criteriaTender = Criteria.where("reportStatus").is("2");
//        queryTender.addCriteria(criteriaTender);
//        List<NifaTenderInfoVO> nifaTenderInfoEntities = this.nifaTenderInfoDao.find(queryTender);
        List<NifaTenderInfoVO> nifaTenderInfoEntities = selectNifaTenderInfo(nifaBorrowInfoEntities);
        if (null == nifaTenderInfoEntities || nifaTenderInfoEntities.size() <= 0) {
            logger.error("【互金上传文件】统计二期标获取出借人信息失败！！" + nifaBorrowInfoEntities);
            return false;
        } else {
            // list类型转换
            List<Object> listTIE = toObject(nifaTenderInfoEntities);
            // 导出csv文件
            logger.info("【互金上传文件】统计二期标的信息生成txt！！");
            try {
                re = createTxtFile(listTIE, null, uploadPath, CustomConstants.NIFA_LENDER_INFO_TYPE, "|");
                if (re) {
                    sb.append(uploadPath).append(CustomConstants.NIFA_LENDER_INFO_TYPE).append(".txt,");
                    // 导出到文件后处理数据库数据为1:处理成功
//                Update update = new Update();
//                update.set("reportStatus", "1").set("updateTime", new Date());
//                nifaTenderInfoDao.updateAll(queryTender, update);
                    updateTenderInfo(nifaBorrowInfoEntities);
                } else {
                    result = false;
                    logger.info("【互金上传文件】:互联网债权类融资出借人信息导出TXT失败！");
                }
            } catch (IOException e) {
                result = false;
                logger.error(e.getMessage());
                logger.error("【互金上传文件】统计二期标的信息生成txt失败！！");
            }
        }

        // --> 获取借款人信息
        List<NifaBorrowerInfoVO> nifaBorrowerInfoEntities = selectNifaBorrowerInfo(nifaBorrowInfoEntities);
        if (null != nifaBorrowerInfoEntities && nifaBorrowerInfoEntities.size() > 0) {
            // list类型转换
            List<Object> listBerIE = toObject(nifaBorrowerInfoEntities);
            // 导出txt文件
            logger.info("【互金上传文件】统计二期标的信息生成txt！！");
            try {
                re = createTxtFile(listBerIE, null, uploadPath, CustomConstants.NIFA_BORROWER_INFO_TYPE, "|");
                if (re) {
                    sb.append(uploadPath).append(CustomConstants.NIFA_BORROWER_INFO_TYPE).append(".txt,");
                    // 更新相应借款人信息
                    updateBorrowerInfo(nifaBorrowInfoEntities);
                } else {
                    result = false;
                    logger.info("【互金上传文件】:互联网债权类融资借款人信息导出TXT失败！");
                }
            } catch (IOException e) {
                result = false;
                logger.error(e.getMessage());
                logger.error("【互金上传文件】统计二期标的信息生成txt失败！！");
            }
        } else {
            logger.error("【互金上传文件】统计二期标获取投资人信息失败！！" + nifaBorrowInfoEntities);
            return false;
        }

        // 上传日志增加记录
        if (result && StringUtils.isNotBlank(fileName)) {
            // 插入上传日志
            NifaReportLogVO nifaReportLog = new NifaReportLogVO();
            // 处理历史数据日期
            nifaReportLog.setHistoryDate(beforDay);
            // 文件包信息
            nifaReportLog.setPackageInformation(fileName);
            // 上传时间：初始
            nifaReportLog.setUploadTime(0);
            // 上传状态：初始
            nifaReportLog.setFileUploadStatus(0);
            // 反馈文件解析状态：初始
            nifaReportLog.setFeedbackResult(0);
            // 上传文件包名
            nifaReportLog.setUploadName(businessZhaiqFileName);
            // 上传文件路径
            nifaReportLog.setUploadPath(uploadPath);
            nifaReportLog.setCreateTime(new Date());
            nifaReportLog.setCreateUserId(3);
            boolean flag = insertNifaReportLog(nifaReportLog);
            if (!flag) {
                result = false;
                logger.info("【互金上传文件】:上传日志增加记录失败！");
            }
        }
        // zip打压缩包
        if (result && StringUtils.isNotBlank(sb)) {
            logger.info("【互金上传文件】统计二期压缩包生成！！");
            if (writeZip(sb, uploadPath + businessZhaiqFileName)) {
                updateNifaBorrowInfoByHistoryDate(request);
            } else {
                result = false;
                logger.info("【互金上传文件】统计二期压缩包生成失败！！");
            }
        } else {
            logger.info("【互金上传文件】统计二期数据文件未生成！！");
            result = false;
        }
        return result;
    }

    /**
     * 统计系统生成zip文件
     *
     * @param businessJinrFileName
     * @param beforeSdfDay
     * @return
     */
    @Override
    public boolean insertMonitorMakeJinrFileReportLog(String businessJinrFileName, String beforeSdfDay) {

        // --> 生成债转标的信息文件

        NifaCreditInfoVO request = new NifaCreditInfoVO();
        request.setReportStatus("0");
        request.setHistoryData(beforeSdfDay);
        List<NifaCreditInfoVO> nifaCreditInfoEntities = selectNifaCreditInfo(request);
        if (null == nifaCreditInfoEntities || nifaCreditInfoEntities.size() <= 0) {
            logger.info("【互金上传文件】统计二期未查询到债转标的信息！！");
            return true;
        }

        // 获取处理日期yyyyMMdd
        String beforDay = beforeSdfDay.replaceAll("-", "");
        // 生成csv文件名中文集合
        String fileNameCredit = "互联网金融产品及收益权转让融资项目信息，互联网金融产品及收益权转让融资受让人信息";

        // 判定当前文件是否已生成
        businessJinrFileName = selectMaxFileName(businessJinrFileName, beforDay, fileNameCredit);
        if (StringUtils.isBlank(businessJinrFileName)) {
            logger.info("【互金上传文件】:统计二期债转标的信息文件名处理错误！");
            return false;
        }

        boolean result = true;
        // 上传文件生成地址
        String uploadPath = UPLOAD_PATH;
        if (!uploadPath.endsWith("/")) {
            uploadPath = uploadPath.concat("/").concat(beforDay).concat("/");
        } else {
            uploadPath = uploadPath.concat(beforDay).concat("/");
        }
        // 需要打压缩zip的文件集合
        StringBuffer sbCredit = new StringBuffer();

        // list类型转换
        List<Object> listCIE = toObject(nifaCreditInfoEntities);
        // 导出csv文件
        logger.info("【互金上传文件】统计二期债转标的信息生成txt！！");
        boolean re = false;
        try {
            re = createTxtFile(listCIE, null, uploadPath, CustomConstants.NIFA_CREDIT_INFO_TYPE, "|");
            if (re) {
                sbCredit.append(uploadPath).append(CustomConstants.NIFA_CREDIT_INFO_TYPE).append(".txt,");
            } else {
                result = false;
                logger.info("【互金上传文件】:互联网金融产品及收益权转让融资项目信息导出TXT失败！");
            }
        } catch (IOException e) {
            result = false;
            logger.error(e.getMessage());
            logger.error("【互金上传文件】统计二期债转标的信息生成txt抛出异常！！");
        }

        // --> 生成债转标的信息文件
        List<NifaCreditTransferVO> nifaCreditTransferEntities = selectNifaCreditTransfer(nifaCreditInfoEntities);
        if (null != nifaCreditTransferEntities && nifaCreditTransferEntities.size() > 0) {
            // list类型转换
            List<Object> listCerIE = toObject(nifaCreditTransferEntities);
            // 导出csv文件
            logger.info("【互金上传文件】统计二期债转标的信息生成txt！！");
            try {
                re = createTxtFile(listCerIE, null, uploadPath, CustomConstants.NIFA_CREDITER_INFO_TYPE, "|");
                if (re) {
                    sbCredit.append(uploadPath).append(CustomConstants.NIFA_CREDITER_INFO_TYPE).append(".txt,");
                    updateCreditTransfer(nifaCreditInfoEntities);
                } else {
                    result = false;
                    logger.info("【互金上传文件】:审计二期债转债转标的承接人信息导出TXT失败！");
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                logger.error("【互金上传文件】统计二期债转标的承接人信息生成txt抛出异常！！");
            }
        }

        // 上传日志增加记录
        if (result && StringUtils.isNotBlank(fileNameCredit)) {
            // 插入上传日志
            NifaReportLogVO nifaReportLog = new NifaReportLogVO();
            // 处理历史数据日期
            nifaReportLog.setHistoryDate(beforDay);
            // 文件包信息
            nifaReportLog.setPackageInformation(fileNameCredit);
            // 上传时间：初始
            nifaReportLog.setUploadTime(0);
            // 上传状态：初始
            nifaReportLog.setFileUploadStatus(0);
            // 反馈文件解析状态：初始
            nifaReportLog.setFeedbackResult(0);
            // 上传文件包名
            nifaReportLog.setUploadName(businessJinrFileName);
            // 上传文件路径
            nifaReportLog.setUploadPath(uploadPath);
            nifaReportLog.setCreateTime(new Date());
            nifaReportLog.setCreateUserId(3);
            boolean flag = insertNifaReportLog(nifaReportLog);
            if (!flag) {
                result = false;
                logger.info("【互金上传文件】:审计二期债转信息上传日志增加记录失败！");
            }
        }
        // zip打压缩包
        if (result && StringUtils.isNotBlank(sbCredit)) {
            logger.info("【互金上传文件】审计二期债转信息压缩包生成！！");
            if (writeZip(sbCredit, uploadPath + businessJinrFileName)) {
                updateNifaCreditInfo(request);
            } else {
                result = false;
                logger.info("【互金上传文件】审计二期债转信息压缩包生成失败！！");
            }
        } else {
            logger.info("【互金上传文件】审计二期债转信息数据文件未生成！！");
            result = false;
        }
        return result;
    }

    /**
     * 通过前置程序下载反馈文件
     *
     * @param nifaReportLog
     * @param feedBackType
     * @param filePathDate
     * @return
     */
    @Override
    public boolean downloadFilesByUrl(NifaReportLogVO nifaReportLog, String feedBackType, String filePathDate) {
        //本地保存目录
        String savePath = "";
        if (DOWNLOAD_PATH.endsWith("/")) {
            savePath = DOWNLOAD_PATH + filePathDate;
        } else {
            savePath = DOWNLOAD_PATH + "/" + filePathDate;
        }

        //如果文件夹不存在则创建
        File file = new File(savePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }


        String requestURL = DOWNLOAD_URL.concat("?systemid=1&stype=").concat(feedBackType)
                // 下载文件的地址
                .concat("&sourcePath=").concat("/feedbackfile/")
                .concat(filePathDate.substring(0, 4)).concat("-")
                .concat(filePathDate.substring(4, 6)).concat("-")
                .concat(filePathDate.substring(6, 8)).concat("/")
                // 下载文件名称
                .concat(nifaReportLog.getUploadName()).concat("1.enc&")
                // 下载路径
                .concat("targetPath=").concat(savePath);

        // 文件上传请求
        String uploadResult = HttpDeal.get(requestURL);
        // 上传结果解析
        JSONObject jsonObject = JSONObject.parseObject(uploadResult);
        if (!"true".equals(jsonObject.get("success"))) {
            logger.error("【互金下载文件】下载错误，返回错信息：" + jsonObject + ",下载请求地址：" + requestURL);
            return false;
        }
        return true;
    }

    /**
     * 查询该天放款成功的数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<BorrowApicronVO> selectBorrowApicron(String historyData) {
        String url = baseTradeUrl + "selectBorrowApicron/" + historyData;
        BorrowApicronResponse response = this.baseClient.getExe(url, BorrowApicronResponse.class);
        List<BorrowApicronVO> list = response.getResultList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 查询该天还款成功数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<BorrowRepayVO> selectBorrowRepayByHistoryData(String historyData) {
        String url = baseTradeUrl + "selectBorrowRepayByHistoryData/" + historyData;
        BorrowRepayResponse response = this.baseClient.getExe(url, BorrowRepayResponse.class);
        List<BorrowRepayVO> list = response.getResultList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 查询该天分期还款成功数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> selectBorrowRepayPlanByHistoryData(String historyData) {
        String url = baseTradeUrl + "selectBorrowRepayPlanByHistoryData/" + historyData;
        BorrowRepayPlanResponse response = this.baseClient.getExe(url, BorrowRepayPlanResponse.class);
        List<BorrowRepayPlanVO> list = response.getResultList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 查询该天日期插入mongo的放还款标的
     *
     * @param historyData
     * @return
     */
    @Override
    public List<NifaBorrowInfoVO> selectNifaBorrowInfoByHistoryData(String historyData) {
        String url = baseMessageUrl + "selectNifaBorrowInfoByHistoryData/" +historyData;
        NifaBorrowInfoResponse response = this.baseClient.getExe(url,NifaBorrowInfoResponse.class);
        List<NifaBorrowInfoVO> list = response.getResultList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 查询该天放款数据
     *
     * @param historyData
     * @return
     */
    @Override
    public List<BorrowAndInfoVO> selectBorrowByHistoryDate(String historyData) {
        String url = baseTradeUrl + "selectBorrowByHistoryDate/" + historyData;
        BorrowResponse response = this.baseClient.getExe(url, BorrowResponse.class);
        List<BorrowAndInfoVO> list = response.getResultList();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list;
    }

    /**
     * 字符串+1方法，该方法将其结尾的整数+1,适用于任何以整数结尾的字符串,不限格式，不限分隔符。
     *
     * @param testStr 要+1的字符串
     * @return +1后的字符串
     * @throws NumberFormatException
     * @author liushouyi
     */
    private String addOne(String testStr) {
        //根据不是数字的字符拆分字符串
        String[] strs = testStr.split("[^0-9]");
        //取出最后一组数字
        String numStr = strs[strs.length - 1];
        //如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常
        if (numStr != null && numStr.length() > 0) {
            //取出字符串的长度
            int n = numStr.length();
            //将该数字加一
            Long num = Long.parseLong(numStr) + 1;
            String added = String.valueOf(num);
            n = Math.min(n, added.length());
            //拼接字符串
            return testStr.subSequence(0, testStr.length() - n) + added;
        } else {
            return "";
        }
    }

    /**
     * 居间服务协议模板下载
     *
     * @param urlStr
     * @param fileName
     * @param savePath
     * @return
     */
    private boolean downLoadFromUrl(String urlStr, String fileName, String savePath) {
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            fos.write(getData);

            logger.info("【互金上传文件】info:" + url + " 下载成功。");
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.error("【互金上传文件】居间服务协议模板下载失败！");
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error("io流关闭错误");
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("io流关闭错误");
                }
            }

        }
    }

    /**
     * 实体类list转List<Object>
     *
     * @param list
     * @param <E>
     * @return
     */
    private <E> List<Object> toObject(List<E> list) {
        List<Object> objlist = new ArrayList<Object>();
        for (Object e : list) {
            Object obj = (Object) e;
            objlist.add(obj);
        }
        return objlist;
    }

    /**
     * CSV文件生成方法
     *
     * @param dataList
     * @param head       标题、暂时不用
     * @param outPutPath
     * @param filename
     * @return
     */
    private boolean createCSVFile(List<Object> dataList, List<Object> head, String outPutPath, String filename, String col) {

        boolean result = true;
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            // 建立空csv文件
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // 如果生产文件乱码，windows下用GBK，linux用UTF-8
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GBK"), 1024);
            // 写入文件内容
            for (Object data : dataList) {
                csvWtriter.write(objectToStr(data, col));
            }
//            csvWtriter.newLine();
            csvWtriter.flush();
        } catch (Exception e) {
            logger.error("【互金上传文件】文件生成失败！文件名：" + outPutPath + filename);
            logger.error(e.getMessage());
            result = false;
        } finally {
            try {
                if (csvWtriter != null) {
                    csvWtriter.close();
                }
            } catch (IOException e) {
                logger.error("【互金上传文件】文件关闭失败！文件名：" + outPutPath + filename);
                logger.error(e.getMessage());
            }
        }

        return result;

    }

    /**
     * 实体类拼接成String字符串
     *
     * @param model
     * @param col
     * @return 解析错误返回空字符串
     */
    private String objectToStr(Object model, String col) {
        try {
            String result = "";
            // 获取实体类的所有属性，返回Field数组
            Field[] field = model.getClass().getDeclaredFields();
            // 获取属性的名字
            String[] modelName = new String[field.length];
            String[] modelType = new String[field.length];
            for (int i = 0; i < field.length; i++) {
                // 获取属性的名字
                String name = field[i].getName();
                modelName[i] = name;
                // 获取属性类型
                String type = field[i].getGenericType().toString();
                modelType[i] = type;

                //关键。。。可访问私有变量
                field[i].setAccessible(true);

                // 将属性的首字母大写
                name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
                        .toUpperCase());

                // 忽略serialVersionUID字段、保证serialVersionUID在实体类的最后一个
                if ("SerialVersionUID".equals(name)) {
                    continue;
                } else {
                    if (i != 0) {
                        // 拼接分割字符串SOH（ASCII码为01）
                        result = result.concat(col);
                    }
                    // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                    // 调用getter方法获取属性值
                    if (null != m.invoke(model)) {
                        // 拼接各个字段的值，空的情况只拼接分隔符
                        result = result.concat(m.invoke(model).toString());
                    }
                }
            }
            // 去掉行数据中多余的回车换行
            result = result.replace(StringPool.ASCII_TABLE[13], "");
            result = result.replace(StringPool.ASCII_TABLE[10], "");
            // 末尾拼接回车换行
            result = result.concat(StringPool.ASCII_TABLE[13]).concat(StringPool.ASCII_TABLE[10]);
            // 拼接成功返回字符串
            return result;
        } catch (NoSuchMethodException e) {
            logger.error("【互金上传文件】拼接数据失败！");
            logger.error(e.getMessage());
            return "";
        } catch (IllegalAccessException e) {
            logger.error("【互金上传文件】拼接数据失败！");
            logger.error(e.getMessage());
            return "";
        } catch (InvocationTargetException e) {
            logger.error("【互金上传文件】拼接数据失败！");
            logger.error(e.getMessage());
            return "";
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 生成txt文件并写入数据
     *
     * @param dataList
     * @param head
     * @param outPutPath
     * @param filename
     * @param col        分隔符
     * @return
     */
    private boolean createTxtFile(List<Object> dataList, List<Object> head, String outPutPath, String filename, String col) throws IOException {
        boolean result = true;
        /* 写入Txt文件 */
        // 相对路径，如果没有则要建立一个新的output。txt文件
        File writename = null;
        BufferedWriter out = null;
        try {
            writename = new File(outPutPath + File.separator + filename + ".txt");
            // 父级文件夹不存在时创建一个
            File parent = writename.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            // 创建新文件
            writename.createNewFile();
            out = new BufferedWriter(new FileWriter(writename));
            // 写入文件内容
            for (Object data : dataList) {
                out.write(objectToStr(data, col));
            }
            // 把缓存区内容压入文件
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = false;
        } finally {
            // 最后关闭文件
            if (null != out) {
                out.close();
            }
        }
        return result;
    }

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    @Override
    public List<NifaReportLogVO> selectNifaReportLogDownloadPath() {
        String url = baseTradeUrl + "selectNifaReportLogDownloadPath";
        NifaReportLogResponse response = this.baseClient.getExe(url, NifaReportLogResponse.class);
        List<NifaReportLogVO> nifaReportLogVOList = response.getResultList();
        if (CollectionUtils.isEmpty(nifaReportLogVOList)) {
            return null;
        }
        return nifaReportLogVOList;
    }

    /**
     * 拉取未成功上传的文件名集合
     *
     * @return
     */
    @Override
    public List<NifaReportLogVO> selectNifaReportLogList() {
        String url = baseTradeUrl + "selectNifaReportLogList";
        NifaFileReportLogResponse response = this.baseClient.getExe(url, NifaFileReportLogResponse.class);
        List<NifaReportLogVO> nifaReportLogVOList = response.getResultList();
        if (CollectionUtils.isEmpty(nifaReportLogVOList)) {
            return null;
        }
        return nifaReportLogVOList;
    }

    /**
     * 判断文件是否生成过
     *
     * @param fileName
     * @return
     */
    @Override
    public boolean selectNifaReportLogByFileName(String fileName) {
        String url = baseTradeUrl + "selectNifaReportLogByFileName/" + fileName;
        NifaFileReportLogResponse response = this.baseClient.getExe(url, NifaFileReportLogResponse.class);
        List<NifaReportLogVO> nifaReportLogVOList = response.getResultList();
        if (CollectionUtils.isEmpty(nifaReportLogVOList)) {
            return false;
        }
        return true;
    }

    /**
     * 更新处理结果
     *
     * @param nifaReportLog
     */
    @Override
    public boolean updateNifaReportLog(NifaReportLogVO nifaReportLog) {
        String url = baseTradeUrl + "updateNifaReportLog";
        BooleanResponse response = this.baseClient.postExe(url, nifaReportLog, BooleanResponse.class);
        return response.getResultBoolean();
    }

    /**
     * 获取当天数据已处理次数生成文件名
     *
     * @param fileName
     * @param beforDate
     * @param fileType
     * @return
     */
    private String selectMaxFileName(String fileName, String beforDate, String fileType) {
        // 已存在取最大文件名最后三位加一
        String url = baseTradeUrl + "selectMaxFileName/" + beforDate + "/" + fileType;
        NifaReportLogResponse response = this.baseClient.getExe(url, NifaReportLogResponse.class);
        List<NifaReportLogVO> nifaReportLogVOList = response.getResultList();
        if (null != nifaReportLogVOList && nifaReportLogVOList.size() > 0) {
            fileName = addOne(nifaReportLogVOList.get(0).getUploadName());
        }
        return fileName;

    }

    /**
     * 获取居间服务协议模板上传信息
     *
     * @param templetId
     * @return
     */
    private FddTempletVO selectFddTemplet(String templetId) {
        String url = baseTradeUrl + "selectFddTemplet/" + templetId;
        FddTempletResponse response = this.baseClient.getExe(url, FddTempletResponse.class);
        FddTempletVO fddTempletVO = response.getResult();
        if (null != fddTempletVO) {
            return fddTempletVO;
        }
        return null;
    }

    /**
     * 获取最新的合同模板
     *
     * @return
     */
    private List<NifaContractTemplateBean> selectNifaContractTemplate() {
        String url = baseTradeUrl + "selectNifaContractTemplate";
        NifaContractTemplateResponse response = this.baseClient.getExe(url, NifaContractTemplateResponse.class);
        List<NifaContractTemplateVO> nifaContractTemplateVOList = response.getResultList();
        if (!CollectionUtils.isEmpty(nifaContractTemplateVOList)) {
            return CommonUtils.convertBeanList(nifaContractTemplateVOList, NifaContractTemplateBean.class);
        }
        return null;
    }

    /**
     * 插入上送记录
     *
     * @param nifaReportLogVO
     * @return
     */
    private boolean insertNifaReportLog(NifaReportLogVO nifaReportLogVO) {
        String url = baseTradeUrl + "insertNifaReportLog";
        BooleanResponse response = this.baseClient.postExe(url, nifaReportLogVO, BooleanResponse.class);
        return response.getResultBoolean();
    }

    /**
     * 查询未上送的合同要素数据
     *
     * @return
     */
    private List<NifaContractEssenceBean> selectNifaContractEssenceList() {
        String url = baseTradeUrl + "selectNifaContractEssenceList";
        NifaContractEssenceResponse response = this.baseClient.getExe(url, NifaContractEssenceResponse.class);
        List<NifaContractEssenceVO> nifaContractTemplateVOList = response.getResultList();
        if (!CollectionUtils.isEmpty(nifaContractTemplateVOList)) {
            return CommonUtils.convertBeanList(nifaContractTemplateVOList, NifaContractEssenceBean.class);
        }
        return null;
    }

    /**
     * 查询为上送的还款数据
     *
     * @return
     */
    private List<NifaRepayInfoBean> selectNifaRepayInfoList() {
        String url = baseTradeUrl + "selectNifaRepayInfoList";
        NifaRepayInfoResponse response = this.baseClient.getExe(url, NifaRepayInfoResponse.class);
        List<NifaRepayInfoVO> nifaContractTemplateVOList = response.getResultList();
        if (!CollectionUtils.isEmpty(nifaContractTemplateVOList)) {
            return CommonUtils.convertBeanList(nifaContractTemplateVOList, NifaRepayInfoBean.class);
        }
        return null;
    }

    /**
     * 查询未上送的合同状态数据
     *
     * @return
     */
    private List<NifaContractStatusBean> selectNifaContractStatus() {
        String url = baseTradeUrl + "selectNifaContractStatus";
        NifaContractStatusResponse response = this.baseClient.getExe(url, NifaContractStatusResponse.class);
        List<NifaContractStatusVO> lsit = response.getResultList();
        if (!CollectionUtils.isEmpty(lsit)) {
            return CommonUtils.convertBeanList(lsit, NifaContractStatusBean.class);
        }
        return null;
    }

    /**
     * 查询为上送的回款记录
     *
     * @return
     */
    private List<NifaReceivedPaymentsBean> selectNifaReceivedPaymentsList() {
        String url = baseTradeUrl + "selectNifaReceivedPaymentsList";
        NifaReceivedPaymentsResponse response = this.baseClient.getExe(url, NifaReceivedPaymentsResponse.class);
        List<NifaReceivedPaymentsVO> lsit = response.getResultList();
        if (!CollectionUtils.isEmpty(lsit)) {
            return CommonUtils.convertBeanList(lsit, NifaReceivedPaymentsBean.class);
        }
        return null;
    }

    /**
     * 查询未上送的借款信息
     *
     * @param request
     * @return
     */
    private List<NifaBorrowInfoVO> selectNifaBorrowInfoByHistoryDate(NifaBorrowInfoVO request) {
        String url = baseMessageUrl + "selectNifaBorrowInfoByHistoryDate";
        NifaBorrowInfoResponse response = this.baseClient.postExe(url, request, NifaBorrowInfoResponse.class);
        List<NifaBorrowInfoVO> list = response.getResultList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 处理完成借款信息后更新mongo数据状态
     *
     * @param request
     */
    private boolean updateNifaBorrowInfoByHistoryDate(NifaBorrowInfoVO request) {
        String url = baseMessageUrl + "updateNifaBorrowInfoByHistoryDate";
        BooleanResponse response = this.baseClient.postExe(url, request, BooleanResponse.class);
        return response.getResultBoolean();
    }

    /**
     * 查询相应标的的投资人信息
     *
     * @param listQuery
     * @return
     */
    private List<NifaTenderInfoVO> selectNifaTenderInfo(List<NifaBorrowInfoVO> listQuery) {
        String url = baseMessageUrl + "selectNifaTenderInfo";
        NifaTenderInfoResponse response = this.baseClient.postExe(url, listQuery, NifaTenderInfoResponse.class);
        List<NifaTenderInfoVO> list = response.getResultList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 更新相应标的的投资人信息
     *
     * @param listQuery
     */
    private boolean updateTenderInfo(List<NifaBorrowInfoVO> listQuery) {
        String url = baseMessageUrl + "updateTenderInfo";
        BooleanResponse response = this.baseClient.postExe(url,listQuery, BooleanResponse.class);
        return response.getResultBoolean();
    }

    /**
     * 拉取相应借款人信息
     *
     * @param listQuery
     * @return
     */
    private List<NifaBorrowerInfoVO> selectNifaBorrowerInfo(List<NifaBorrowInfoVO> listQuery) {
        String url = baseMessageUrl + "selectNifaBorrowerInfo";
        NifaBorrowerInfoResponse response = this.baseClient.postExe(url,listQuery, NifaBorrowerInfoResponse.class);
        List<NifaBorrowerInfoVO> list = response.getResultList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 更新相应标的的借款人信息
     *
     * @param listQuery
     */
    private boolean updateBorrowerInfo(List<NifaBorrowInfoVO> listQuery) {
        String url = baseMessageUrl + "updateBorrowerInfo";
        BooleanResponse response = this.baseClient.postExe(url,listQuery, BooleanResponse.class);
        return response.getResultBoolean();
    }

    /**
     * 查询未上送的债转信息
     *
     * @param request
     * @return
     */
    private List<NifaCreditInfoVO> selectNifaCreditInfo(NifaCreditInfoVO request) {
        String url = baseMessageUrl + "selectNifaCreditInfo";
        NifaCreditInfoResponse response = this.baseClient.postExe(url, request, NifaCreditInfoResponse.class);
        List<NifaCreditInfoVO> list = response.getResultList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 更新上送的债转信息
     *
     * @param request
     */
    private boolean updateNifaCreditInfo(NifaCreditInfoVO request) {
        String url = baseMessageUrl + "updateNifaCreditInfo";
        BooleanResponse response = this.baseClient.postExe(url, request, BooleanResponse.class);
        return response.getResultBoolean();
    }

    /**
     * 查询未上送的债转承接人信息
     *
     * @param listQuery
     * @return
     */
    private List<NifaCreditTransferVO> selectNifaCreditTransfer(List<NifaCreditInfoVO> listQuery) {
        String url = baseMessageUrl + "selectNifaCreditTransfer";
        NifaCreditTransferResponse response = this.baseClient.postExe(url,listQuery, NifaCreditTransferResponse.class);
        List<NifaCreditTransferVO> list = response.getResultList();
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    /**
     * 更新相应承接人上送状态
     *
     * @param listQuery
     */
    private boolean updateCreditTransfer(List<NifaCreditInfoVO> listQuery) {
        String url = baseMessageUrl + "updateCreditTransfer";
        BooleanResponse response = this.baseClient.postExe(url,listQuery, BooleanResponse.class);
        return response.getResultBoolean();
    }

}
