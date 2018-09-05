/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.auto.FddTempletExample;
import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.dao.model.auto.NifaReportLogExample;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.task.NifaFileDealService;
import com.hyjf.am.trade.utils.FtpUtil;
import com.hyjf.am.trade.utils.SFTPParameter;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
 * @author PC-LIUSHOUYI
 * @version NifaFileDealServiceImpl, v0.1 2018/9/4 16:53
 */
@Service
public class NifaFileDealServiceImpl extends BaseServiceImpl implements NifaFileDealService {

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
            List<NifaContractTemplateCustomize> nifaContractTemplate = nifaContractTemplateCustomizeMapper.selectNifaContractTemplate();
            if (null != nifaContractTemplate && nifaContractTemplate.size() > 0) {
                // 合同数据变更时压缩最新合同模板
                FddTemplet fddTemplet = this.selectFddTemplet(nifaContractTemplate.get(0).getTempletNid());
                result = downLoadFromUrl(fddTemplet.getFileUrl(), nifaContractTemplate.get(0).getTempletNid() + ".pdf", uploadPath);
                if (result) {
                    sbTemplate.append(uploadPath).append(nifaContractTemplate.get(0).getTempletNid()).append(".pdf,");
                    if (!writeZip(sbTemplate, uploadPath + contractTemplateFileName)) {
                        result = false;
                        logger.info("【互金上传文件】:合同数据变更时压缩最新合同模板失败！");
                    }
                }
                // 更新数据库插入一条数据
                // 上传日志增加记录
                // 插入上传日志
                NifaReportLog nifaReportLogTemplate = new NifaReportLog();
                // 文件包信息
                nifaReportLogTemplate.setPackageInformation(TEMPLE_NAME);
                // 上传时间：初始
                nifaReportLogTemplate.setUploadTime(0);
                // 上传状态：初始
                nifaReportLogTemplate.setFileUploadStatus(0);
                // 反馈文件解析状态：初始
                nifaReportLogTemplate.setFeedbackResult(0);
                // 上传文件包名
                nifaReportLogTemplate.setUploadName(contractTemplateFileName);
                // 上传文件路径
                nifaReportLogTemplate.setUploadPath(uploadPath);
                nifaReportLogTemplate.setCreateTime(new Date());
                nifaReportLogTemplate.setCreateUserId(3);
                boolean flag = nifaReportLogMapper.insertSelective(nifaReportLogTemplate) > 0 ? true : false;
                if (!flag) {
                    result = false;
                    logger.info("【互金上传文件】:合同模板约定条款、上传日志增加记录失败！");
                }
                // list类型转换
                List<Object> listNCT = toObject(nifaContractTemplate);
                // 导出csv文件
                logger.info("【互金上传文件】合同模板约定条款记录生成！！");
                if (createCSVFile(listNCT, null, uploadPath, GENERAL_CONTRACT)) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(GENERAL_CONTRACT).append(".csv,");
                    fileName.append("合同模板约定条款");
                } else {
                    result = false;
                    logger.info("【互金上传文件】:合同模板约定条款导出CSV失败！");
                }
            }
        }

        if (!this.selectNifaReportLogByFileName(businessDataFileName)) {
            // 合同要素信息(记录投资信息)
            List<NifaContractEssenceCustomize> nifaContractEssenceList = nifaContractEssenceCustomizeMapper.selectNifaContractEssenceList();
            if (null != nifaContractEssenceList && nifaContractEssenceList.size() > 0) {
                // list类型转换
                List<Object> listNCE = toObject(nifaContractEssenceList);
                // 导出csv文件
                logger.info("【互金上传文件】合同要素信息记录生成！！");
                if (createCSVFile(listNCE, null, uploadPath, INV_CONTRACT)) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(INV_CONTRACT).append(".csv,");
                    fileName.append("合同要素信息");
                } else {
                    result = false;
                    logger.info("【互金上传文件】:合同要素信息导出CSV失败！");
                }
            }

            // 借款人项目还款记录
            List<NifaRepayInfoCustomize> nifaRepayInfoList = nifaRepayInfoCustomizeMapper.selectNifaRepayInfoList();
            if (null != nifaRepayInfoList && nifaRepayInfoList.size() > 0) {
                // list类型转换
                List<Object> listNRI = toObject(nifaRepayInfoList);
                // 导出csv文件
                logger.info("【互金上传文件】借款人项目还款记录生成！！");
                if (createCSVFile(listNRI, null, uploadPath, PRO_REPAY_RECORD)) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(PRO_REPAY_RECORD).append(".csv,");
                    fileName.append("借款人项目还款记录");
                } else {
                    result = false;
                    logger.info("【互金上传文件】:借款人项目还款记录导出CSV失败！");
                }
            }

            // 合同状态变更
            List<NifaContractStatusCustomize> nifaContractStatus = nifaContractStatusCustomizeMapper.selectNifaContractStatus();
            if (null != nifaContractStatus && nifaContractStatus.size() > 0) {
                // list类型转换
                List<Object> listNCS = toObject(nifaContractStatus);
                // 导出csv文件
                logger.info("【互金上传文件】合同状态变更记录生成！！");
                if (createCSVFile(listNCS, null, uploadPath, CONTRACT_STATE)) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(CONTRACT_STATE).append(".csv,");
                    fileName.append("合同状态变更");
                } else {
                    result = false;
                    logger.info("【互金上传文件】:合同状态变更导出CSV失败！");
                }
            }

            // 出借人回款记录
            List<NifaReceivedPaymentsCustomize> nifaReceivedPaymentsList = nifaReceivedPaymentsCustomizeMapper.selectNifaReceivedPaymentsList();
            if (null != nifaReceivedPaymentsList && nifaReceivedPaymentsList.size() > 0) {
                // list类型转换
                List<Object> listNRP = toObject(nifaReceivedPaymentsList);
                // 导出csv文件
                logger.info("【互金上传文件】出借人回款记录生成！！");
                if (createCSVFile(listNRP, null, uploadPath, INV_RETURN_RECORD)) {
                    if (StringUtils.isNotBlank(fileName)) {
                        fileName.append(",");
                    }
                    sb.append(uploadPath).append(INV_RETURN_RECORD).append(".csv,");
                    fileName.append("出借人回款记录");
                } else {
                    result = false;
                    logger.info("【互金上传文件】:出借人回款记录导出CSV失败！");
                }
            }
            // 上传日志增加记录
            if (result && StringUtils.isNotBlank(fileName)) {
                // 插入上传日志
                NifaReportLog nifaReportLog = new NifaReportLog();
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
                nifaReportLog.setCreateTime(new Date());
                nifaReportLog.setCreateUserId(3);
                boolean flag = nifaReportLogMapper.insertSelective(nifaReportLog) > 0 ? true : false;
                if (!flag) {
                    result = false;
                    logger.info("【互金上传文件】:上传日志增加记录失败！");
                }
            }
            // zip打压缩包
            if (result && StringUtils.isNotBlank(sb)) {
                logger.info("【互金上传文件】压缩包生成！！");
                if (!writeZip(sb, uploadPath + businessDataFileName)) {
                    result = false;
                    logger.info("【互金上传文件】压缩包生成失败！！");
                }
            } else {
                logger.info("【互金上传文件】数据文件未生成！！");
                result = false;
            }
        }
        return result;

    }

    /**
     * 拉取未成功上传的文件名集合
     *
     * @return
     */
    @Override
    public List<NifaReportLog> selectNifaReportLogList() {
        NifaReportLogExample example = new NifaReportLogExample();
        NifaReportLogExample.Criteria cra = example.createCriteria();
        // 获取状态不是成功的数据
        cra.andFileUploadStatusNotEqualTo(1);
        List<NifaReportLog> nifaReportLogList = nifaReportLogMapper.selectByExample(example);
        if (null != nifaReportLogList && nifaReportLogList.size() > 0) {
            return nifaReportLogList;
        }
        return null;
    }

    /**
     * 更新处理结果
     *
     * @param nifaReportLog
     */
    @Override
    public boolean updateNifaReportLog(NifaReportLog nifaReportLog) {
        boolean result = this.nifaReportLogMapper.updateByPrimaryKeySelective(nifaReportLog) > 0 ? true : false;
        if (!result) {
            logger.info("【互金上传文件】更新上传日志表失败！Id:" + nifaReportLog.getId());
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
                        bufferReader.close();
                        inputStreamReader.close();
                        logger.info("【互金上传文件】上传状态异步返回文件解析成功，文件内容：" + localStrBulider.toString());
                        return localStrBulider.toString();
                    } catch (IOException e) {
                        logger.error("【互金上传文件】上传状态异步返回文件读取失败!");
                        e.printStackTrace();
                        return "ERROR";
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("【互金上传文件】上传状态异步返回文件不支持编码格式!");
                    e.printStackTrace();
                    return "ERROR";
                }
            } else {
                logger.error("【互金上传文件】上传状态异步返回文件不存在!");
                return "ERROR";
            }
        } catch (Exception e) {
            logger.error("【互金上传文件】上传状态异步返回文件解析错误！");
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * 获取未成功下载日志的数据
     *
     * @return
     */
    @Override
    public List<NifaReportLog> selectNifaReportLogDownloadPath() {
        NifaReportLogExample example = new NifaReportLogExample();
        example.createCriteria().andFeedbackResultNotEqualTo(1);
        List<NifaReportLog> nifaReportLogList = this.nifaReportLogMapper.selectByExample(example);
        if (null != nifaReportLogList && nifaReportLogList.size() > 0) {
            return nifaReportLogList;
        }
        return null;
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
            e.printStackTrace();
            logger.info("【互金下载反馈文件】下载ftp文件失败、文件夹名称：" + filePathDate);
        }
        return re;
    }

    /**
     * 判断文件是否生成过
     *
     * @param fileName
     * @return
     */
    private boolean selectNifaReportLogByFileName(String fileName) {
        NifaReportLogExample example = new NifaReportLogExample();
        example.createCriteria().andUploadNameEqualTo(fileName);
        List<NifaReportLog> nifaReportLogList = this.nifaReportLogMapper.selectByExample(example);
        if (null != nifaReportLogList && nifaReportLogList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取居间服务协议模板上传信息
     *
     * @param templetId
     * @return
     */
    private FddTemplet selectFddTemplet(String templetId) {
        FddTempletExample example = new FddTempletExample();
        example.createCriteria().andTempletIdEqualTo(templetId);
        List<FddTemplet> fddTempletList = this.fddTempletMapper.selectByExample(example);
        if (null != fddTempletList && fddTempletList.size() > 0) {
            return fddTempletList.get(0);
        }
        return null;
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
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            logger.info("【互金上传文件】info:" + url + " 下载成功。");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("【互金上传文件】居间服务协议模板下载失败！");
            return false;
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
    private boolean createCSVFile(List<Object> dataList, List<Object> head, String outPutPath, String filename) {

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
                csvWtriter.write(objectToStr(data));
            }
            csvWtriter.flush();
        } catch (Exception e) {
            logger.error("【互金上传文件】文件生成失败！文件名：" + outPutPath + filename);
            e.printStackTrace();
            result = false;
        } finally {
            try {
                csvWtriter.close();
                return result;
            } catch (IOException e) {
                logger.error("【互金上传文件】文件关闭失败！文件名：" + outPutPath + filename);
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 实体类拼接成String字符串
     *
     * @param model
     * @return 解析错误返回空字符串
     */
    private String objectToStr(Object model) {
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
                        result = result.concat(StringPool.ASCII_TABLE[01]);
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
            result = result.replace(StringPool.ASCII_TABLE[13],"");
            result = result.replace(StringPool.ASCII_TABLE[10],"");
            // 末尾拼接回车换行
            result = result.concat(StringPool.ASCII_TABLE[13]).concat(StringPool.ASCII_TABLE[10]);
            // 拼接成功返回字符串
            return result;
        } catch (NoSuchMethodException e) {
            logger.error("【互金上传文件】拼接数据失败！");
            e.printStackTrace();
            return "";
        } catch (IllegalAccessException e) {
            logger.error("【互金上传文件】拼接数据失败！");
            e.printStackTrace();
            return "";
        } catch (InvocationTargetException e) {
            logger.error("【互金上传文件】拼接数据失败！");
            e.printStackTrace();
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
}
