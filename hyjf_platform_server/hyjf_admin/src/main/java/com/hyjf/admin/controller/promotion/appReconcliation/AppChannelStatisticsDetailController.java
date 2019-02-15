package com.hyjf.admin.controller.promotion.appReconcliation;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.promotion.AppChannelReconciliationService;
import com.hyjf.admin.service.promotion.AppChannelStatisticsDetailService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * app渠道统计明细
 * @author lisheng
 * @version AppChannelStatisticsDetailController, v0.1 2018/9/21 17:22
 */
@Api(tags ="app渠道统计明细")
@RestController
@RequestMapping("/hyjf-admin/promotion/app/channel/detail")
public class AppChannelStatisticsDetailController extends BaseController {
    @Autowired
    AppChannelStatisticsDetailService appChannelStatisticsDetailService;
    @Autowired
    private AppChannelReconciliationService appChannelReconciliationService;
    /** 查看权限 */
    public static final String PERMISSIONS = "appchanneldetail";

    @ApiOperation(value = "app渠道统计明细-画面初始化", notes = "app渠道统计明细-画面初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AppUtmRegResponse init(@RequestBody AppChannelStatisticsDetailRequest appChannelStatisticsDetailRequest, HttpServletRequest request){
        AdminSystemVO user = getUser(request);
        Integer userId = Integer.valueOf(user.getId());
        AdminUtmReadPermissionsVO adminUtmReadPermissions = this.appChannelReconciliationService.selectAdminUtmReadPermissions(userId);
        AppUtmRegResponse response = appChannelStatisticsDetailService.getstatisticsList(appChannelStatisticsDetailRequest);
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
    /*@ApiOperation(value = "app渠道统计明细-导出", notes = "app渠道统计明细-导出")
    @GetMapping("/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, AppChannelStatisticsDetailRequest form) throws Exception {
        // 表格sheet名称
        String sheetName = "app渠道统计明细";
        AppUtmRegResponse appChannelStatisticsDetailResponse = this.appChannelStatisticsDetailService.exportStatisticsList(form);
        List<AppUtmRegVO> recordList = appChannelStatisticsDetailResponse.getResultList();

        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "渠道", "用户ID", "用户名",  "注册时间", "开户时间", "首次出借时间", "首投项目类型", "首投项目期限", "首投金额", "累计出借金额" };
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
                    AppUtmRegVO record = recordList.get(i);

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
                    // 首次出借时间
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
                    // 累计出借金额
                    else if (celLength == 10) {
                        cell.setCellValue(record.getCumulativeInvest().toString());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

    /**
     * 导出功能
     *
     * @param request
     * @param
     * @param form
     */
    @ApiOperation(value = "app渠道统计明细-导出", notes = "app渠道统计明细-导出")
    @PostMapping("/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response,@RequestBody AppChannelStatisticsDetailRequest form) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "app渠道统计明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        AppUtmRegResponse appUtmRegResponse = this.appChannelStatisticsDetailService.exportStatisticsList(form);
        List<AppUtmRegVO> resultList = appUtmRegResponse.getResultList();


        Integer totalCount = resultList.size();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";

        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }

        for (int i = 1; i <= sheetCount; i++) {
            //请求第一页5000条
            form.setPageSize(defaultRowMaxCount);
            form.setCurrPage(i);
            List<AppUtmRegVO> resultResponse2 = appChannelStatisticsDetailService.paging(form,resultList);
            if (resultResponse2 != null && resultResponse2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2);
            } else {
                break;
            }
        }

        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("sourceName", "渠道");
        map.put("userId", "用户ID");
        map.put("userName", "用户名");
        map.put("registerTime", "注册时间");
        map.put("openAccountTime", "开户时间");
        map.put("firstInvestTimeT", "首次出借时间");
        map.put("investProjectType", "首投项目类型");
        map.put("investProjectPeriod", "首投项目期限");
        map.put("investAmount", "首投金额");
        map.put("cumulativeInvest", "累计出借金额");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter registerTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date value = (Date) object;
                return GetDate.date2Str(value, GetDate.datetimeFormat);
            }
        };

        IValueFormatter openAccountTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date value = (Date) object;
                return GetDate.date2Str(value, GetDate.datetimeFormat);
            }
        };

        IValueFormatter firstInvestTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                return GetDate.formatDateTime(value);
            }
        };

        mapAdapter.put("registerTime", registerTimeAdapter);
        mapAdapter.put("openAccountTime", openAccountTimeAdapter);
        mapAdapter.put("firstInvestTime", firstInvestTimeAdapter);
        return mapAdapter;
    }
}