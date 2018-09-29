/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import com.hyjf.admin.beans.request.NifaReportLogRequestBean;
import com.hyjf.admin.beans.vo.NifaReportLogCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NifaReportLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NifaReportLogResponse;
import com.hyjf.am.resquest.admin.NifaReportLogRequest;
import com.hyjf.am.vo.admin.NifaReportLogVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nxl
 * @version NifaReportLogController, v0.1 2018/8/17 9:27
 */
@Api(value = "数据中心_互金协会报送日志", tags = "数据中心_互金协会报送日志")
@RestController
@RequestMapping("/hyjf-admin/datacenter/nifareportlog")
public class NifaReportLogController extends BaseController {
    @Autowired
    private NifaReportLogService nifaReportLogService;

    private static final String PERMISSIONS = "nifareportlog";

    /**
     * 互金字段定义列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "互金协会报送日志列表显示", notes = "互金协会报送日志列表显示")
    @PostMapping("/selectNifaReportLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NifaReportLogCustomizeVO>> selectNifaReportLogList(@RequestBody NifaReportLogRequestBean requestBean){
        NifaReportLogRequest request = new NifaReportLogRequest();
        BeanUtils.copyProperties(requestBean, request);
        NifaReportLogResponse response = nifaReportLogService.selectNifaReportLogList(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NifaReportLogCustomizeVO> nifaReportLogCustomizeVOList = new ArrayList<NifaReportLogCustomizeVO>();
        if(null!=response.getResultList()&&response.getResultList().size()>0){
            nifaReportLogCustomizeVOList = CommonUtils.convertBeanList(response.getResultList(),NifaReportLogCustomizeVO.class);
        }
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null!=nifaReportLogCustomizeVOList&&nifaReportLogCustomizeVOList.size()>0){
            for(NifaReportLogCustomizeVO nifaReportLogCustomizeVO:nifaReportLogCustomizeVOList){
                //日期处理
                String strCreate = smp.format(nifaReportLogCustomizeVO.getCreateTime());
                nifaReportLogCustomizeVO.setUploadTime(strCreate);
            }
        }
        return new AdminResult<ListResult<NifaReportLogCustomizeVO>>(ListResult.build(nifaReportLogCustomizeVOList, response.getRecordTotal())) ;
    }

    /**
     * 下载文件包
     *
     * @param request
     * @param request
     * @return
     */
    @ApiOperation(value = "下载文件包", notes = "下载文件包")
    @PostMapping("/downLoadFile")
    public void downLoadFile(HttpServletRequest request, HttpServletResponse response, @RequestBody String logId) {
        NifaReportLogVO nifaReportLog = null;
        if(StringUtils.isNotBlank(logId)){
            NifaReportLogResponse nifaReportLogResponse = nifaReportLogService.selectNifaReportLogById(Integer.parseInt(logId));
            if(null!=nifaReportLogResponse){
                nifaReportLog = nifaReportLogResponse.getResult();
            }
        }
        logger.info("==============文件开始下载==============");
        if(null!=nifaReportLog){
            //文件上传状态为成功并且上传地址不为空
            if(StringUtils.isNotBlank(nifaReportLog.getUploadPath())){
                String fileName = nifaReportLog.getUploadName()+".zip";
                String downLoadPath = nifaReportLog.getUploadPath();
                if(!downLoadPath.endsWith("/")){
                    downLoadPath = downLoadPath+"/";
                }
                String donwFile = downLoadPath+fileName;
                logger.info("==============文件id:"+nifaReportLog.getId());
                logger.info("==============文件包信息:"+nifaReportLog.getPackageInformation());
                logger.info("==============文件名:"+fileName);
                logger.info("==============文件下载地址:"+donwFile);
                download(donwFile,fileName,response);
            }else{
                logger.error("==============下载文件不存在==============");
            }
        }
    }
    /**
     * 下载反馈文件
     *
     * @param request
     * @param request
     * @return
     */
    @ApiOperation(value = "下载反馈文件", notes = "下载反馈文件")
    @PostMapping("/downloadFeedbackFile")
    public void downloadFeedbackFile(HttpServletRequest request,HttpServletResponse response, @RequestBody String logId) {
        NifaReportLogVO nifaReportLog = null;
        if(StringUtils.isNotBlank(logId)){
            NifaReportLogResponse nifaReportLogResponse = nifaReportLogService.selectNifaReportLogById(Integer.parseInt(logId));
            if(null!=nifaReportLogResponse){
                nifaReportLog = nifaReportLogResponse.getResult();
            }
        }
        if(null!=nifaReportLog){
            //文件上传状态为成功并且上传地址不为空
            if(StringUtils.isNotBlank(nifaReportLog.getFeedbackPath())){
                String fileName = nifaReportLog.getFeedbackName()+".zip";
                String downLoadPath = nifaReportLog.getFeedbackPath();
                if(!downLoadPath.endsWith("/")){
                    downLoadPath = downLoadPath+"/";
                }
                String donwFile = downLoadPath+fileName;
                logger.info("==============文件id:"+nifaReportLog.getId());
                logger.info("==============文件包信息:"+nifaReportLog.getPackageInformation());
                logger.info("==============反馈文件名:"+fileName);
                logger.info("==============反馈文件下载地址:"+donwFile);
                download(donwFile,fileName,response);
            }else{
                logger.error("==============反馈文件不存在==============");
            }
        }
    }

    /**
     * 下载
     * @param filePath
     * @param fileName
     * @param response
     */
    public void download(String filePath,String fileName, HttpServletResponse response) {
        try {
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.setContentType("multipart/form-data");
            FileInputStream in = new FileInputStream(filePath);
            // 创建输出流
            OutputStream out = response.getOutputStream();
            // 创建缓冲区
            byte buffer[] = new byte[1024];
            int len = 0;
            // 循环将输入流中的内容读取到缓冲区中
            while ((len = in.read(buffer)) > 0) {
                // 输出缓冲区内容到浏览器，实现文件下载
                out.write(buffer, 0, len);
            }
            // 关闭文件流
            in.close();
            // 关闭输出流
            out.close();
        } catch (Exception e) {
            logger.error(NifaReportLogController.class.getName(), "NifaReportLogDown", e);
        }
    }
}
