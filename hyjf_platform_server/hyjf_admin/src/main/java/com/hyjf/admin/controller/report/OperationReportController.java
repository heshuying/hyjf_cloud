package com.hyjf.admin.controller.report;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.OperationReportService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.datacollect.OperationReportVO;
import com.hyjf.am.vo.datacollect.OperationSelectVO;
import com.hyjf.am.vo.datacollect.QuarterOperationReportVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportController, v0.1 2018/7/02 11:17
 */
@Api(value ="运营报告配置",tags ="内容中心-运营报告配置")
@RestController
@RequestMapping("/hyjf-admin/report/operationreport")
public class OperationReportController extends BaseController {

    @Autowired
    private OperationReportService operationReportService;
    @Autowired
    private SystemConfig  systemConfig;
    @Autowired
    private FileUpLoadUtil fileUpLoadUtil;

    @ApiOperation(value = "列表初始化", notes = "运营报告列表查询")
    @PostMapping("/list")
    public AdminResult<ListResult<OperationReportVO>> list(@RequestBody OperationReportRequest request) {
        OperationReportResponse response = operationReportService.getRecordList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "进入季度报告页面", notes = "进入季度报告页面")
    @GetMapping("/initQuarter/{operationReportType}/{year}")
    public AdminResult<OperationReportResponse> initQuarter(@PathVariable Integer operationReportType, @PathVariable String year){
        OperationReportResponse response = new OperationReportResponse();
        OperationReportVO operationReport = new OperationReportVO();
        QuarterOperationReportVO quarterOperationReport = new QuarterOperationReportVO();
        operationReport.setOperationReportType(operationReportType);
        if (operationReportType == 5) {
            quarterOperationReport.setQuarterType(1);
        } else if (operationReportType == 6) {
            quarterOperationReport.setQuarterType(3);
        }
        if (year != null && year != "") {
            operationReport.setYear(year);
        } else {
            operationReport.setYear(String.valueOf(GetDate.getYear()));
        }
        response.setOperationReport(operationReport);
        response.setQuarterOperationReport(quarterOperationReport);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "月度报告新增和公用修改頁面", notes = "月度报告新增和公用修改頁面")
    @GetMapping("/initupdatepage/{id}")
    public AdminResult<OperationReportResponse> initUpdatePage(@PathVariable String id){
        OperationReportResponse response = new OperationReportResponse();
        if (StringUtils.isNotEmpty(id)) {
            response = operationReportService.selectOperationreportCommon(id);
        }
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "进入上半年度报告页面", notes = "进入上半年度报告页面")
    @GetMapping("/inithalfyear/{year}")
    public AdminResult<OperationReportResponse> initHalfYear(@PathVariable String year){
        OperationReportResponse response = new OperationReportResponse();
        OperationReportVO operationReport = new OperationReportVO();
        operationReport.setOperationReportType(3);
        if (year != null && year != "") {
            operationReport.setYear(year);
        } else {
            operationReport.setYear(String.valueOf(GetDate.getYear()));
        }
        response.setOperationReport(operationReport);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "进入年度报告页面", notes = "进入年度报告页面")
    @GetMapping("/inityear/{year}")
    public AdminResult<OperationReportResponse> initYear(@PathVariable String year){
        OperationReportResponse response = new OperationReportResponse();
        OperationReportVO operationReport = new OperationReportVO();
        operationReport.setOperationReportType(4);
        if (year != null && year != "") {
            operationReport.setYear(year);
        } else {
            operationReport.setYear(String.valueOf(GetDate.getYear()));
        }
        response.setOperationReport(operationReport);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "删除信息", notes = "删除信息")
    @GetMapping("/delete/{id}")
    public AdminResult<OperationReportResponse> delete(@PathVariable String id){
        OperationReportResponse response = operationReportService.delete(id);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "发布接口有变化", notes = "发布参数请放在operationReport对象里")
    @PostMapping("/publish")
    public AdminResult<OperationReportResponse> publish(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.publish(request);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "运营报告下拉框初始化", notes = "运营报告下拉框初始化")
    @GetMapping ("/infomonthoperation")
    public AdminResult<ListResult<OperationSelectVO>> infoMonthOperation(){
        //下拉框初始化
        List<OperationSelectVO> selectList = initSelect();
        return new AdminResult(selectList);
    }
    @ApiOperation(value = "月度运营报告新增修改", notes = "月度运营报告新增修改")
    @PostMapping("/insertorupdatemonth")
    public AdminResult<OperationReportResponse> insertOrUpdateMonthAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateMonthAction(request);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "季度运营报告新增修改", notes = "季度运营报告新增修改")
    @PostMapping("/insertorupdatequarter")
    public AdminResult<OperationReportResponse> insertOrUpdateQuarterAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateQuarterAction(request);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "上半年度运营报告新增修改", notes = "上半年度运营报告新增修改")
    @PostMapping("/insertorupdatehalfyear")
    public AdminResult<OperationReportResponse> insertOrUpdateHalfYearAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateHalfYearAction(request);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "年度运营报告新增修改", notes = "年度运营报告新增修改")
    @PostMapping("/insertorupdateyear")
    public AdminResult<OperationReportResponse> insertOrUpdateYearAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateYearAction(request);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "月度新增修改页面预览", notes = "月度新增修改页面预览")
    @PostMapping("/monthpreview")
    public AdminResult<OperationReportResponse> monthPreview(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.monthPreview(request);
        //服务器配置路径跳转预览需要用到
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "年度新增修改页面预览", notes = "年度新增修改页面预览")
    @PostMapping("/yearpreview")
    public AdminResult<OperationReportResponse> yearPreview(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.yearPreview(request);
        //服务器配置路径跳转预览需要用到
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "季度新增修改页面预览", notes = "季度新增修改页面预览")
    @PostMapping("/quarterpreview")
    public AdminResult<OperationReportResponse> quarterPreview(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.quarterPreview(request);
        //服务器配置路径跳转预览需要用到
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "半年度新增修改页面预览", notes = "半年度新增修改页面预览")
    @PostMapping("/halfpreview")
    public AdminResult<OperationReportResponse> halfPreview(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.halfPreview(request);
        //服务器配置路径跳转预览需要用到
        return new AdminResult<>(response);
    }
    /**
     * @Description 数据导出--运营报告
     * @Author
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "数据导出--运营报告", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody OperationReportRequest form) throws Exception {
        // 表格sheet名称
        String sheetName = "运营报告数据";
        OperationReportResponse operationReportResponse = operationReportService.getRecordList(form);
        List<OperationReportVO> recordList =  operationReportResponse.getResultList();
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"标题名称", "累计交易额", "累计赚取收益", "平台注册人数", "本月（本季/本年）成交笔数", "本月（本季/本年）成交金额", "本月（本季/本年）为用户赚取收益", "状态", "发布时间"};

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
                    OperationReportVO bean = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 标题名称
                    if (celLength == 0) {
                        cell.setCellValue(bean.getCnName());
                    }
                    // 累计交易额
                    else if (celLength == 1) {
                        cell.setCellValue(bean.getAllAmount() == null ? "" : String.valueOf(bean.getAllAmount()));
                    }
                    // 累计赚取收益
                    else if (celLength == 2) {
                        cell.setCellValue(bean.getAllProfit() == null ? "" : String.valueOf(bean.getAllProfit()));
                    }
                    // 平台注册人数
                    else if (celLength == 3) {
                        cell.setCellValue(bean.getRegistNum() == null ? "" : String.valueOf(bean.getRegistNum()));
                    }
                    // 本月（本季/本年）成交笔数
                    else if (celLength == 4) {
                        cell.setCellValue(bean.getSuccessDealNum() == null ? "" : String.valueOf(bean.getSuccessDealNum()));
                    }
                    // 本月（本季/本年）成交金额
                    else if (celLength == 5) {
                        cell.setCellValue(bean.getOperationAmount() == null ? "" : String.valueOf(bean.getOperationAmount()));
                    }
                    // 本月（本季/本年）为用户赚取收益
                    else if (celLength == 6) {
                        cell.setCellValue(bean.getOperationProfit() == null ? "" : String.valueOf(bean.getOperationProfit()));
                    }
                    // 状态
                    else if (celLength == 7) {
                        if (1 == bean.getIsRelease().intValue()) {
                            cell.setCellValue("已发布");
                        } else {
                            cell.setCellValue("未发布");
                        }
                    }
                    // 发布时间
                    else if (celLength == 8) {
                        cell.setCellValue(bean.getReleaseTimeStr());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    private List<OperationSelectVO> initSelect() {
        List<OperationSelectVO> selectList = new ArrayList<OperationSelectVO>();
        OperationSelectVO bean1 = new OperationSelectVO();
        bean1.setCode("1");
        bean1.setName("月度运营报告");
        selectList.add(bean1);
        OperationSelectVO bean5 = new OperationSelectVO();
        bean5.setCode("5");
        bean5.setName("第一季度运营报告");
        selectList.add(bean5);
        OperationSelectVO bean3 = new OperationSelectVO();
        bean3.setCode("3");
        bean3.setName("半年度运营报告");
        selectList.add(bean3);
        OperationSelectVO bean6 = new OperationSelectVO();
        bean6.setCode("6");
        bean6.setName("第三季度运营报告");
        selectList.add(bean6);
        OperationSelectVO bean4 = new OperationSelectVO();
        bean4.setCode("4");
        bean4.setName("年度运营报告");
        selectList.add(bean4);
        return selectList;
    }

    /**
     * 资料上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "资料上传", notes = "资料上传")
    @RequestMapping("/uploadFile")
    public AdminResult uploadFile(HttpServletRequest request) throws Exception {
        AdminResult adminResult = new AdminResult();
        LinkedList<BorrowCommonImage> borrowCommonImages = fileUpLoadUtil.upLoad(request);
        adminResult.setData(borrowCommonImages);
        return adminResult;
    }
}
