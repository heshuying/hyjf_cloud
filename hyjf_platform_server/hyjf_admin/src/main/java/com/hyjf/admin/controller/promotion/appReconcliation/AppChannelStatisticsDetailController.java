package com.hyjf.admin.controller.promotion.appReconcliation;

import com.hyjf.admin.service.promotion.AppChannelReconciliationService;
import com.hyjf.admin.service.promotion.AppChannelStatisticsDetailService;
import com.hyjf.am.response.admin.AppChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.ExportExcel;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * app渠道统计明细
 * @author lisheng
 * @version AppChannelStatisticsDetailController, v0.1 2018/9/21 17:22
 */
@Api(tags ="app渠道统计明细")
@RestController
@RequestMapping("/hyjf-admin/promotion/app/channel/detail")
public class AppChannelStatisticsDetailController {
    @Autowired
    AppChannelStatisticsDetailService appChannelStatisticsDetailService;
    @Autowired
    private AppChannelReconciliationService appChannelReconciliationService;

    @ApiOperation(value = "app渠道统计明细-画面初始化", notes = "app渠道统计明细-画面初始化")
    @PostMapping("/init")
    public AppChannelStatisticsDetailResponse init(@RequestBody AppChannelStatisticsDetailRequest request, @RequestHeader(value="userId")Integer userId){
        AdminUtmReadPermissionsVO adminUtmReadPermissions = this.appChannelReconciliationService.selectAdminUtmReadPermissions(userId);
        AppChannelStatisticsDetailResponse response = appChannelStatisticsDetailService.getstatisticsList(request);
        List<UtmPlatVO> appUtm = appChannelStatisticsDetailService.getAppUtm();
        response.setAdminUtmReadPermissions(adminUtmReadPermissions);
        response.setAppUtm(appUtm);
        return response;
    }


    /**
     * 导出功能
     *
     * @param request
     * @param
     * @param form
     */
    @ApiOperation(value = "app渠道统计明细-导出", notes = "app渠道统计明细-导出")
    @GetMapping("/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, AppChannelStatisticsDetailRequest form) throws Exception {
        // 表格sheet名称
        String sheetName = "app渠道统计明细";
        AppChannelStatisticsDetailResponse appChannelStatisticsDetailResponse = this.appChannelStatisticsDetailService.exportStatisticsList(form);
        List<AppChannelStatisticsDetailVO> recordList = appChannelStatisticsDetailResponse.getResultList();

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "渠道", "用户ID", "用户名",  "注册时间", "开户时间", "首次投资时间", "首投项目类型", "首投项目期限", "首投金额", "累计投资金额" };
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
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    AppChannelStatisticsDetailVO record = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 渠道
                    else if (celLength == 1) {
                        cell.setCellValue(record.getSourceName());
                    }
                    // 用户ID
                    else if (celLength == 2) {
                        cell.setCellValue(record.getUserId());
                    }
                    // 用户名
                    else if (celLength == 3) {
                        cell.setCellValue(record.getUserName());
                    }

                    // 注册时间
                    else if (celLength == 4) {
                        if (record.getRegisterTime() == null) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(GetDate.date2Str(record.getRegisterTime(), GetDate.datetimeFormat));
                        }
                    }
                    // 开户时间
                    else if (celLength == 5) {
                        if (record.getOpenAccountTime() == null) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(GetDate.date2Str(record.getOpenAccountTime(), GetDate.datetimeFormat));
                        }
                    }
                    // 首次投资时间
                    else if (celLength == 6) {
                        if (record.getFirstInvestTime() == null) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(GetDate.formatDateTime(record.getFirstInvestTime()));
                        }
                    }
                    // 首投项目类型
                    else if (celLength == 7) {
                        if (StringUtils.isNotEmpty(record.getInvestProjectType())) {
                            cell.setCellValue(record.getInvestProjectType());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                    // 首投项目期限
                    else if (celLength == 8) {
                        if (StringUtils.isNotEmpty(record.getInvestProjectPeriod())) {
                            cell.setCellValue(record.getInvestProjectPeriod());
                        } else {
                            cell.setCellValue("");
                        }
                    }
                    // 首投金额
                    else if (celLength == 9) {
                        cell.setCellValue(record.getInvestAmount() == null ? "0.00" : record.getInvestAmount().toString());
                    }
                    // 累计投资金额
                    else if (celLength == 10) {
                        cell.setCellValue(record.getCumulativeInvest().toString());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}