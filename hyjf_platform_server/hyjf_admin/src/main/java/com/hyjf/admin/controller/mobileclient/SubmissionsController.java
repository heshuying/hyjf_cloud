package com.hyjf.admin.controller.mobileclient;

import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.mobileclient.SubmissionsService;
import com.hyjf.am.response.config.AppBorrowImageResponse;
import com.hyjf.am.response.config.SubmissionsResponse;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.resquest.config.AppBorrowImageRequest;
import com.hyjf.am.resquest.config.SubmissionsRequest;
import com.hyjf.am.vo.config.AppBorrowImageVO;
import com.hyjf.am.vo.config.SubmissionsCustomizeVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意见反馈
 * @author lisheng
 * @version SubmissionsController, v0.1 2018/7/11 11:25
 */
@RestController
@RequestMapping("/submissions")
public class SubmissionsController extends BaseController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SubmissionsService  submissionsService;

    /**
     * 查询列表
     *
     * @return
     */
    @PostMapping("/getRecordList")
    public SubmissionsResponse findAppBannerData(@RequestBody SubmissionsRequest form) {
        SubmissionsResponse response = new SubmissionsResponse();



        return response;
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/exportListAction")
    public void exportListAction(HttpServletRequest request, SubmissionsRequest form,
                                 HttpServletResponse response) throws Exception {

        // 表格sheet名称
        String sheetName = "意见反馈列表";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        ;
        // 需要输出的结果列表
        // 封装查询条件
        Map<String, Object> searchCon = new HashMap<String, Object>();
        // 用户名
        String userName = StringUtils.isNotEmpty(form.getUserName()) ? form.getUserName() : null;
        searchCon.put("userName", userName);
        form.setUserName(userName);
        // 系统类别
        String sysType = form.getSysType();
        searchCon.put("sysType", sysType);
        // 操作系统版本号
        String sysVersion = form.getSysVersion();
        searchCon.put("sysVersion",sysVersion);
        // 平台版本号
        String platformVersion = form.getPlatformVersion();
        searchCon.put("platformVersion", platformVersion);
        // 手机型号
        String phoneType = form.getPhoneType();
        searchCon.put("phoneType", phoneType);
        // 反馈内容
        String content = form.getContent();
        searchCon.put("content", content);
        // 添加时间-开始
        String addTimeStart = form.getAddTimeStart();
        if(StringUtils.isNotEmpty(addTimeStart)){
            searchCon.put("addTimeStart", addTimeStart);
        }
        // 添加时间-结束
        String addTimeEnd = form.getAddTimeEnd();
        if(StringUtils.isNotEmpty(addTimeEnd)){
            searchCon.put("addTimeEnd", addTimeEnd);
        }
        //TODO List<SubmissionsCustomizeVO> submissionsList = this.submissionsService.queryRecordList(searchCon, -1, -1);
        List<SubmissionsCustomizeVO> submissionsList=null;
        String[] titles = new String[] { "序号", "用户名", "操作系统", "版本号", "手机型号", "反馈内容", "时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (submissionsList != null && submissionsList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < submissionsList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    SubmissionsCustomizeVO submissions = submissionsList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) { // 用户名
                        cell.setCellValue(submissions.getUserName());
                    } else if (celLength == 2) { // 系统
                        cell.setCellValue(submissions.getSysType());
                    } else if (celLength == 3) { // 平台版本号
                        cell.setCellValue(submissions.getPlatformVersion());
                    } else if (celLength == 4) {// 手机型号
                        cell.setCellValue(submissions.getPhoneType());
                    } else if (celLength == 5) {// 反馈内容
                        cell.setCellValue(submissions.getContent());
                    } else if (celLength == 6) {// 时间
                        cell.setCellValue(submissions.getAddTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

}
