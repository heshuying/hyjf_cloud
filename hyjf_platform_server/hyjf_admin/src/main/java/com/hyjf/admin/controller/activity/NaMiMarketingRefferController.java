package com.hyjf.admin.controller.activity;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NaMiMarketingRefferService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayResponse;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayVO;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2018/11/8.
 */
@Api(tags = "活动中心-纳觅返现活动")
@Controller
@RequestMapping("/hyjf-admin/manager/activity/namimarketing")
public class NaMiMarketingRefferController extends BaseController {

    public static final String PERMISSIONS = "activitylist";
    @Autowired
    private NaMiMarketingRefferService naMiMarketingService;

    /**
     * 邀请人返现明细 画面初始化
     * @param naMiMarketingRequest
     * @return
     */
    @ApiOperation(value = "邀请人返现明细列表", notes = "邀请人返现明细列表")
    @PostMapping("/refferDetailInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NaMiMarketingVO>> refferDetailInit(@RequestBody NaMiMarketingRequest naMiMarketingRequest) {
        NaMiMarketingResponse response = naMiMarketingService.selectNaMiMarketingRefferList(naMiMarketingRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NaMiMarketingVO> resultList = response.getResultList();
        return new AdminResult<ListResult<NaMiMarketingVO>>(ListResult.build(resultList, response.getCount()));
    }

    /**
     * 邀请人返现统计 画面初始化
     * @param naMiMarketingRequest
     * @return
     */
    @ApiOperation(value = "邀请人返现统计列表", notes = "邀请人返现统计列表")
    @PostMapping("/refferTotalInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NaMiMarketingVO>> refferTotalInit(@RequestBody NaMiMarketingRequest naMiMarketingRequest) {
        NaMiMarketingResponse response = naMiMarketingService.selectNaMiMarketingRefferTotalList(naMiMarketingRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NaMiMarketingVO> resultList = response.getResultList();
        return new AdminResult<ListResult<NaMiMarketingVO>>(ListResult.build(resultList, response.getCount()));
    }

    @ApiOperation(value = "纳觅返现月份下拉框", notes = "纳觅返现月份下拉框")
    @GetMapping("/initMonth")
    public AdminResult<NaMiMarketingResponse> initMonth() {
        AdminResult result = new AdminResult();
        NaMiMarketingResponse response = naMiMarketingService.selectMonthList();
        result.setData(response);
        return result;
    }

    @ApiOperation(value = "邀请人返现明细导出", notes = "邀请人返现明细导出")
    @PostMapping("/exportRefferDetailAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportRefferDetailAction(HttpServletRequest req, HttpServletResponse res, @RequestBody NaMiMarketingRequest request) throws Exception{
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "邀请人返现明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

//		form.setCurrPage(1);
//		form.setPageSize(defaultRowMaxCount);
        IntegerResponse response = naMiMarketingService.selectNaMiMarketingRefferCount(request);
        Integer totalCount = response.getResultInt() == null ? 0 : response.getResultInt();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap1();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            request.setPageSize(defaultRowMaxCount);
            request.setCurrPage(i);
            NaMiMarketingResponse naMiMarketingResponse = naMiMarketingService.selectNaMiMarketingRefferList(request);
            List<NaMiMarketingVO> record = naMiMarketingResponse.getResultList();
            if (record != null && record.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  record);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(req, res, fileName, workbook);
    }

    @ApiOperation(value = "邀请人返现统计导出", notes = "邀请人返现统计导出")
    @PostMapping("/exportRefferTotalAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportRefferTotalAction(HttpServletRequest req, HttpServletResponse res, @RequestBody NaMiMarketingRequest request) throws Exception{
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "邀请人返现统计";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

//		form.setCurrPage(1);
//		form.setPageSize(defaultRowMaxCount);
        IntegerResponse response = naMiMarketingService.selectNaMiMarketingRefferTotalCount(request);
        Integer totalCount = response.getResultInt() == null ? 0 : response.getResultInt();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap2();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            request.setPageSize(defaultRowMaxCount);
            request.setCurrPage(i);
            NaMiMarketingResponse naMiMarketingResponse = naMiMarketingService.selectNaMiMarketingRefferTotalList(request);
            List<NaMiMarketingVO> record = naMiMarketingResponse.getResultList();
            if (record != null && record.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  record);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(req, res, fileName, workbook);
    }

    private Map<String, String> buildMap1() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("username", "账户名");
        map.put("truename", "姓名");
        map.put("returnPerformance", "单笔当月产生的业绩（元）");
        map.put("returnAmount", "获得返现金额（元）");
        map.put("refferName", "投资人账户名");
        map.put("tenderNo", "投资订单号");
        map.put("tenderAmount", "单笔投资金额（元）");
        map.put("term", "投资期限");
        map.put("productType", "产品类型");
        map.put("productNo", "产品编号");
        map.put("regTime", "放款时间/加入时间");
        return map;
    }
    private Map<String, String> buildMap2() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("username", "月份");
        map.put("truename", "账户名");
        map.put("returnPerformance", "姓名");
        map.put("returnAmount", "返现合计（元）");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }
   /* *//**
     * 邀请人返现明细 导出
     *
     * @param request
     * @param response
     * @throws Exception
     *//*
    @RequestMapping(NaMiMarketingDefine.EXPORT_REFFER_DETAIL_ACTION)
    @RequiresPermissions(NaMiMarketingDefine.PERMISSIONS_EXPORT)
    public void exportRefferDetailExcel(HttpServletRequest request, HttpServletResponse response, NaMiMarketingBean form) throws Exception {
        // 表格sheet名称
        String sheetName = "邀请人返现明细";
        // 取得数据
        List<NaMiMarketingCustomize> recordList = createPage(request, new ModelAndView(),form);
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{ "序号","账户名", "姓名","单笔当月产生的业绩（元）","获得返现金额（元）", "投资人账户名","投资订单号","单笔投资金额（元）","投资期限","产品类型","产品编号","放款时间/加入时间"};
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
                    NaMiMarketingCustomize bean = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 账户名
                    if (celLength == 0) {
                        cell.setCellValue(rowNum);
                    }
                    // 账户名
                    else if (celLength == 1) {
                        cell.setCellValue(bean.getUsername() == null?"":bean.getUsername());
                    }
                    // 姓名
                    else if (celLength == 2) {
                        cell.setCellValue(bean.getTruename() == null?"" : bean.getTruename());
                    }
                    //单笔当月产生的业绩
                    else if (celLength == 3) {
                        cell.setCellValue(bean.getReturnPerformance()==null? BigDecimal.ZERO+"" : bean.getReturnPerformance()+"");
                    }
                    //  获得返现金额
                    else if (celLength == 4) {
                        cell.setCellValue(bean.getReturnAmount() == null?  BigDecimal.ZERO+"": bean.getReturnAmount()+"");
                    }
                    //  投资人账户名
                    else if (celLength == 5) {
                        cell.setCellValue(bean.getRefferName() == null? "": bean.getRefferName()+"");
                    }
                    //  投资 订单号
                    else if (celLength == 6) {
                        cell.setCellValue(bean.getTenderNo() == null? "": bean.getTenderNo());
                    }
                    //  单笔投资金额
                    else if (celLength == 7) {
                        cell.setCellValue(bean.getTenderAmount() == null? BigDecimal.ZERO+"": bean.getTenderAmount()+"");
                    }
                    //  投资期限
                    else if (celLength == 8) {
                        cell.setCellValue(bean.getTerm() == null? "": bean.getTerm());
                    }
                    //  产品类型
                    else if (celLength == 9) {
                        cell.setCellValue(bean.getProductType() == null? "": bean.getProductType());
                    }
                    //  产品编号
                    else if (celLength == 10) {
                        cell.setCellValue(bean.getProductNo() == null? "": bean.getProductNo());
                    }
                    //  放款时间/加入时间
                    else if (celLength == 11) {
                        cell.setCellValue(bean.getRegTime() == null? "": bean.getRegTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }


    *//**
     * 邀请人返现统计 导出
     *
     * @param request
     * @param response
     * @throws Exception
     *//*
    @RequestMapping(NaMiMarketingDefine.EXPORT_REFFER_TOTAL_ACTION)
    @RequiresPermissions(NaMiMarketingDefine.PERMISSIONS_EXPORT)
    public void exportRefferTotalExcel(HttpServletRequest request, HttpServletResponse response, NaMiMarketingBean form) throws Exception {
        // 表格sheet名称
        String sheetName = "邀请人返现统计";
        // 取得数据
        List<NaMiMarketingCustomize> recordList = refferTotalCreatePage(request, new ModelAndView(),form);
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{ "序号","月份","账户名", "姓名","返现合计（元）"};
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
                    NaMiMarketingCustomize bean = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 账户名
                    if (celLength == 0) {
                        cell.setCellValue(rowNum);
                    }
                    // 月份
                    else if (celLength == 1) {
                        cell.setCellValue(bean.getRegTime() == null?"":bean.getRegTime());
                    }
                    // 账户名
                    else if (celLength == 2) {
                        cell.setCellValue(bean.getUsername() == null?"":bean.getUsername());
                    }
                    // 姓名
                    else if (celLength == 3) {
                        cell.setCellValue(bean.getTruename() == null?"" : bean.getTruename());
                    }
                    //返现合计
                    else if (celLength == 4) {
                        cell.setCellValue(bean.getReturnAmount() == null?  BigDecimal.ZERO+"": bean.getReturnAmount()+"");
                    }
//                    //  返现合计
//                    else if (celLength == 5) {
//                        cell.setCellValue(bean.getReturnAmount() == null?  BigDecimal.ZERO+"": bean.getReturnAmount()+"");
//                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    public List<String>  initMonth(){
        List monthList =new ArrayList<String>();
//        monthList.add("2018-05");
//        monthList.add("2018-06");
//        monthList.add("2018-07");
//        monthList.add("2018-08");
//        monthList.add("2018-09");
//        monthList.add("2018-10");
//        monthList.add("2018-11");
//        monthList.add("2018-12");
//        monthList.add("2019-01");
//        monthList.add("2019-02");
//        monthList.add("2019-03");
        monthList = this.naMiMarketingService.selectMonthList();
        return monthList;
    }

    public Map<String, Object> beanToMap(NaMiMarketingBean form){
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(form.getUsername())){
            paraMap.put("username", form.getUsername().trim());
        }
        if(StringUtils.isNotBlank(form.getTruename())){
            paraMap.put("truename", form.getTruename().trim());
        }
        if(StringUtils.isNotBlank(form.getRefferName())){
            paraMap.put("tenderName", form.getRefferName().trim());
        }
        if(StringUtils.isNotBlank(form.getCol())){
            paraMap.put("col", form.getCol().trim());
        }
        if(StringUtils.isNotBlank(form.getSort())){
            paraMap.put("sort", form.getSort().trim());
        }
        if(StringUtils.isNotBlank(form.getProductType())){
            paraMap.put("productType", form.getProductType().trim());
        }
        if(StringUtils.isNotBlank(form.getProductNo())){
            paraMap.put("productNo", form.getProductNo().trim());
        }
        if(StringUtils.isNotBlank(form.getJoinTimeStart())){
            paraMap.put("joinTimeStart", form.getJoinTimeStart().trim()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(form.getJoinTimeEnd())){
            paraMap.put("joinTimeEnd", form.getJoinTimeEnd().trim()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(form.getMonth())){
            form.setJoinTimeStart(form.getMonth()+"-01");
            form.setJoinTimeEnd(getLastMinuTime(form.getMonth()));
            paraMap.put("joinTimeStart", form.getMonth()+"-01 00:00:00");
            paraMap.put("joinTimeEnd", getLastMinuTime(form.getMonth())+" 23:59:59");
        }
        return paraMap;
    }

    public String getLastMinuTime(String oldTime){
        //String转 date
        Date d= GetDate.stringToDate2(oldTime+"-01");
        //最后一天日期
        Date lastT= GetDate.getLastDayOnMonth(d);
        return GetDate.formatDate(lastT,"yyyy-MM-dd");
    }
*/

}
