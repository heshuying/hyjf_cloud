package com.hyjf.admin.controller.manager;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AccountBalanceService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhInfoAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author：yinhui
 * @Date: 2018/8/7  9:32
 */
@Api(tags = "数据中心-汇计划统计")
@RestController
@RequestMapping("/hyjf-admin/manager/statis")
public class AccountBalanceController extends BaseController {

    @Autowired
    private AccountBalanceService accountBalanceService;

    /** 权限 */
    public static final String PERMISSIONS = "hjhstatis";

    @ApiOperation(value = "数据中心-汇计划统计", notes = "数据中心-汇计划统计 查询")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
    public AdminResult search(@RequestBody HjhAccountBalanceRequest request) {
        String time = request.getTime();
        HjhInfoAccountBalanceResponse response = null;
        if (("month").equals(time)) {
            response = accountBalanceService.getSearchListByMonth(request);
            if (response == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, response.getMessage());

            }
        } else {
            response = accountBalanceService.getSearchListByDay(request);
            if (response == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, response.getMessage());

            }
        }
        return new AdminResult(response);
    }

    /**
     * 导出日报功能
     *
     * @param form
     */
    @ApiOperation(value = "数据中心-汇计划统计", notes = "数据中心-汇计划统计 导出日交易量")
    @PostMapping("/exportActionByDay")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT )
    public void exportActionByDay(@RequestBody HjhAccountBalanceRequest form,HttpServletRequest request,HttpServletResponse response) throws Exception {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

        //请求第一页5000条
        form.setPageSize(defaultRowMaxCount);
        form.setCurrPage(1);
        HjhInfoAccountBalanceResponse resultResponse = accountBalanceService.getSearchListByDay(form);
        // 表格sheet名称
        String sheetName = "每日交易量";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        Integer totalCount = resultResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if(totalCount==0){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,resultResponse.getResultList());
        }

        for (int i = 1; i < sheetCount; i++) {
            //请求第一页5000条
            form.setPageSize(defaultRowMaxCount);
            form.setCurrPage(i+1);
            HjhInfoAccountBalanceResponse resultResponse2 = accountBalanceService.getSearchListByDay(form);
            if (resultResponse2.getResultList() != null && resultResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("rptDate","日期");
        map.put("investAccount","原始资产交易额(元)");
        map.put("creditAccount","债转资产交易额(元)");
        map.put("reinvestAccount","复出借金额(元)");
        map.put("addAccount","新加入资金额(元)");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter dateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String format = new SimpleDateFormat("yyyy-MM-dd ").format((Date)object);
                return StringUtils.isEmpty(format) ? StringUtils.EMPTY : format;
            }
        };

        IValueFormatter valueFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object != null ? object.toString() : "0";
            }
        };

        mapAdapter.put("rptDate", dateAdapter);
        mapAdapter.put("investAccount", valueFormatAdapter);
        mapAdapter.put("creditAccount", valueFormatAdapter);
        mapAdapter.put("reinvestAccount", valueFormatAdapter);
        mapAdapter.put("addAccount", valueFormatAdapter);
        return mapAdapter;
    }

    /**
     * 按月导出功能
     *
     * @param form
     */
    @ApiOperation(value = "数据中心-汇计划统计", notes = "数据中心-汇计划统计 导出月交易量")
    @PostMapping("/exportActionMonth")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT )
    public void exportActionMonth(@RequestBody HjhAccountBalanceRequest form,HttpServletRequest request,HttpServletResponse response) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        //请求第一页5000条
        form.setPageSize(defaultRowMaxCount);
        form.setCurrPage(1);
        HjhInfoAccountBalanceResponse resultResponse = accountBalanceService.getSearchListByMonth(form);
        // 表格sheet名称
        String sheetName = "每月交易量";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        Integer totalCount = resultResponse.getCount();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = monthBuildMap();
        Map<String, IValueFormatter> mapValueAdapter = monthBuildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if(totalCount==0){
            helper.export(workbook, sheetName + "_第" + (1) + "页", beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,resultResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            //请求第一页5000条
            form.setPageSize(defaultRowMaxCount);
            form.setCurrPage(i+1);
            HjhInfoAccountBalanceResponse resultResponse2 = accountBalanceService.getSearchListByMonth(form);
            if (resultResponse2.getResultList() != null && resultResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, IValueFormatter> monthBuildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter dateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return null==object||"".equals(object) ? StringUtils.EMPTY : object.toString();
            }
        };

        IValueFormatter valueFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object != null ? object.toString() : "0";
            }
        };

        mapAdapter.put("dataFormt", dateAdapter);
        mapAdapter.put("investAccount", valueFormatAdapter);
        mapAdapter.put("creditAccount", valueFormatAdapter);
        mapAdapter.put("reinvestAccount", valueFormatAdapter);
        mapAdapter.put("addAccount", valueFormatAdapter);
        return mapAdapter;
    }

    private Map<String, String> monthBuildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("dataFormt","日期");
        map.put("investAccount","原始资产交易额(元)");
        map.put("creditAccount","债转资产交易额(元)");
        map.put("reinvestAccount","复出借金额(元)");
        map.put("addAccount","新加入资金额(元)");
        return map;
    }
}
