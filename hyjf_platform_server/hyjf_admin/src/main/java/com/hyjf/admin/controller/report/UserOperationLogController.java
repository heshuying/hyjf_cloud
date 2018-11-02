/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.report;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.OperationLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.message.UserOperationLogResponse;
import com.hyjf.am.resquest.admin.UserOperationLogRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.ExportExcel;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version UserOperationLogController, v0.1 2018/10/9 15:57
 */
@Api(value ="会员操作日志",tags ="会员中心-会员操作日志")
@RestController
@RequestMapping("/hyjf-admin/manager/operationlog")
public class UserOperationLogController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserOperationLogController.class);

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 页面初始化
     *
     * @param form
     * @return
     */
    @ApiOperation(value = "会员操作日志列表", notes = "会员操作日志列表")
    @PostMapping("/operationLog")
    public AdminResult init(@RequestBody UserOperationLogRequest form) {
        // 创建分页
        UserOperationLogResponse response = operationLogService.getOperationLogList(form);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));

    }

    /**
     * 会员操作日志列表导出
     *
     * @param form
     * @param request
     * @param response
     */
    @ApiOperation(value = "会员操作日志列表导出", notes = "会员操作日志列表导出")
    @PostMapping("/exportoperationlog")
    public void exportExcel(@RequestBody UserOperationLogRequest form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("会员操作日志表格导出");
        //表格sheet名称
        String sheetName = "会员操作日志";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        //解决IE浏览器导出列表中文乱码问题
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
            // win10 ie edge 浏览器 和其他系统的ie
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        }

        // 封装查询条件
        form.setOffset(-1);
        form.setLimit(-1);
        List<UserOperationLogEntityVO> recordList = operationLogService.getOperationLogList(form).getResultList();
        String[] titles = new String[]{"活动类型", "用户角色", "用户名", "操作平台", "备注", "IP", "操作时间"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (recordList != null && recordList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < recordList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                try {
                    for (int celLength = 0; celLength < titles.length; celLength++) {
                        UserOperationLogEntityVO operationLogEntity = recordList.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {
                            if (operationLogEntity.getOperationType() == 1) {
                                cell.setCellValue("登录");
                            }else if (operationLogEntity.getOperationType() == 2) {
                                cell.setCellValue("登出");
                            }else if (operationLogEntity.getOperationType() == 3) {
                                cell.setCellValue("开户");
                            }else if (operationLogEntity.getOperationType() == 4) {
                                cell.setCellValue("出借确认");
                            }else if (operationLogEntity.getOperationType() == 5) {
                                cell.setCellValue("转让确认");
                            }else if (operationLogEntity.getOperationType() == 6) {
                                cell.setCellValue("修改交易密码");
                            }else if (operationLogEntity.getOperationType() == 7) {
                                cell.setCellValue("修改登录密码");
                            }else if (operationLogEntity.getOperationType() == 8) {
                                cell.setCellValue("绑定邮箱");
                            }else if (operationLogEntity.getOperationType() == 9) {
                                cell.setCellValue("修改邮箱");
                            }else if (operationLogEntity.getOperationType() == 10) {
                                cell.setCellValue("绑定银行卡");
                            }else if (operationLogEntity.getOperationType() == 11) {
                                cell.setCellValue("解绑银行卡");
                            }else if (operationLogEntity.getOperationType() == 12) {
                                cell.setCellValue("风险测评");
                            }
                        }else if (celLength == 1) {
                            if (operationLogEntity.getUserRole().equals("1")) {
                                cell.setCellValue("出借人");
                            }else if (operationLogEntity.getUserRole().equals("2")) {
                                cell.setCellValue("借款人");
                            }else if (operationLogEntity.getUserRole().equals("3")) {
                                cell.setCellValue("担保机构");
                            }
                        }else if (celLength == 2) {
                            cell.setCellValue(operationLogEntity.getUserName());
                        }else if (celLength == 3) {
                            if (operationLogEntity.getPlatform() == 0) {
                                cell.setCellValue("PC");
                            }else if (operationLogEntity.getPlatform() == 1) {
                                cell.setCellValue("wechat");
                            }else if (operationLogEntity.getPlatform() == 2) {
                                cell.setCellValue("Andriod");
                            }else if (operationLogEntity.getPlatform() == 3) {
                                cell.setCellValue("IOS");
                            }
                        }else if (celLength == 4) {
                            cell.setCellValue(operationLogEntity.getRemark());
                        }else if (celLength == 5) {
                            cell.setCellValue(operationLogEntity.getIp());
                        }else if (celLength == 6) {
                            cell.setCellValue(GetDate.dateToString(operationLogEntity.getOperationTime()));
                        }
                    }
                } catch (Exception e) {
                    logger.info("导出表格错误 e : {}", e);
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
