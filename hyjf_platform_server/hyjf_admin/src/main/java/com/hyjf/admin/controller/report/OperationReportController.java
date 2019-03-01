package com.hyjf.admin.controller.report;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.HjhPlanCapitalRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.OperationReportService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.datacollect.OperationReportVO;
import com.hyjf.am.vo.datacollect.OperationSelectVO;
import com.hyjf.am.vo.datacollect.QuarterOperationReportVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.CommonUtils;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

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
    /**
     * 权限关键字
     */
    public static final String PERMISSIONS = "operationreport";

    @ApiOperation(value = "列表初始化", notes = "运营报告列表查询")
    @PostMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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
        if (year != null && !year.equalsIgnoreCase("")) {
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<OperationReportResponse> initUpdatePage(@PathVariable String id){
        OperationReportResponse response = new OperationReportResponse();
        if (StringUtils.isNotEmpty(id)) {
            response = operationReportService.selectOperationreportCommon(id);
        }
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "进入上半年度报告页面", notes = "进入上半年度报告页面")
    @GetMapping("/inithalfyear/{year}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult<OperationReportResponse> delete(@PathVariable String id){
        OperationReportResponse response = operationReportService.delete(id);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "发布接口有变化", notes = "发布参数请放在operationReport对象里")
    @PostMapping("/publish")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
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
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<OperationReportResponse> insertOrUpdateMonthAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateMonthAction(request);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "季度运营报告新增修改", notes = "季度运营报告新增修改")
    @PostMapping("/insertorupdatequarter")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<OperationReportResponse> insertOrUpdateQuarterAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateQuarterAction(request);
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "上半年度运营报告新增修改", notes = "上半年度运营报告新增修改")
    @PostMapping("/insertorupdatehalfyear")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<OperationReportResponse> insertOrUpdateHalfYearAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateHalfYearAction(request);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "年度运营报告新增修改", notes = "年度运营报告新增修改")
    @PostMapping("/insertorupdateyear")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<OperationReportResponse> insertOrUpdateYearAction(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.insertOrUpdateYearAction(request);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "月度新增修改页面预览", notes = "月度新增修改页面预览")
    @PostMapping("/monthpreview")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<OperationReportResponse> monthPreview(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.monthPreview(request);
        //服务器配置路径跳转预览需要用到
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "年度新增修改页面预览", notes = "年度新增修改页面预览")
    @PostMapping("/yearpreview")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<OperationReportResponse> yearPreview(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.yearPreview(request);
        //服务器配置路径跳转预览需要用到
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "季度新增修改页面预览", notes = "季度新增修改页面预览")
    @PostMapping("/quarterpreview")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<OperationReportResponse> quarterPreview(@RequestBody OperationReportRequest request){
        OperationReportResponse response = operationReportService.quarterPreview(request);
        //服务器配置路径跳转预览需要用到
        return new AdminResult<>(response);
    }
    @ApiOperation(value = "半年度新增修改页面预览", notes = "半年度新增修改页面预览")
    @PostMapping("/halfpreview")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
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
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response,@RequestBody  OperationReportRequest form) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "运营报告数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        Integer totalCount = operationReportService.getRecordCount(form);
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        } else {
            for (int i = 0; i < sheetCount; i++) {
                form.setPageSize(defaultRowMaxCount);
                form.setCurrPage(i + 1);
                OperationReportResponse operationReportResponse2 = operationReportService.getRecordList(form);
                if (operationReportResponse2 != null && operationReportResponse2.getResultList().size() > 0) {
                    sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, operationReportResponse2.getResultList());
                } else {
                    break;
                }
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("cnName", "标题名称");
        map.put("allAmount", "累计交易额");
        map.put("allProfit", "累计赚取收益");
        map.put("registNum", "平台注册人数");
        map.put("successDealNum", "本月（本季/本年）成交笔数");
        map.put("operationAmount", "本月（本季/本年）成交金额");
        map.put("operationProfit", "本月（本季/本年）为用户赚取收益");
        map.put("isRelease", "状态");
        map.put("releaseTimeStr", "发布时间");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter isReleaseAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                if (1 == value) {
                    return "已发布";
                } else {
                    return "未发布";
                }
            }
        };

        mapAdapter.put("isRelease", isReleaseAdapter);
        return mapAdapter;
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
    @PostMapping("/uploadFile")
    public AdminResult uploadFile(HttpServletRequest request) throws Exception {
        AdminResult adminResult = new AdminResult();
        LinkedList<BorrowCommonImage> borrowCommonImages = fileUpLoadUtil.upLoad(request);
        adminResult.setData(borrowCommonImages);
        return adminResult;
    }
}
