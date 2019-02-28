package com.hyjf.admin.controller.activity;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NaMiMarketingService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NaMiMarketingResponse;
import com.hyjf.am.resquest.admin.NaMiMarketingRequest;
import com.hyjf.am.vo.admin.NaMiMarketingVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version NaMiMarketingController, v0.1 2018/12/26 11:00
 */
@Api(tags = "活动中心-活动列表-详情")
@RestController
@RequestMapping("/hyjf-admin/manager/activity/namimarketing")
public class NaMiMarketingController  extends BaseController {
    /**
     * 权限关键字
     */
    public static final String PERMISSIONS = "activitylist";
    @Autowired
    NaMiMarketingService naMiMarketingService;

    @ApiOperation(value = "邀请明细列表查询", notes = "邀请明细列表查询")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NaMiMarketingVO>> selectNaMiMarketList(@RequestBody NaMiMarketingRequest naMiMarketingRequest) {
        NaMiMarketingResponse response = naMiMarketingService.getRecordList(naMiMarketingRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NaMiMarketingVO> resultList = response.getResultList();
        if(CollectionUtils.isEmpty(resultList)){
            return new AdminResult<ListResult<NaMiMarketingVO>>(ListResult.build(resultList, 0));
        }
        return new AdminResult<ListResult<NaMiMarketingVO>>(ListResult.build(resultList, response.getCount()));
    }

    @ApiOperation(value = "业绩返现", notes = "业绩返现")
    @PostMapping("/performanceInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NaMiMarketingVO>> performanceInit(@RequestBody NaMiMarketingRequest naMiMarketingRequest) {
        NaMiMarketingResponse response = naMiMarketingService.getPerformanceList(naMiMarketingRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NaMiMarketingVO> resultList = response.getResultList();
        if(CollectionUtils.isEmpty(resultList)){
            return new AdminResult<ListResult<NaMiMarketingVO>>(ListResult.build(resultList, 0));
        }
        return new AdminResult<ListResult<NaMiMarketingVO>>(ListResult.build(resultList, response.getCount()));
    }

    @ApiOperation(value = "业绩返现详情", notes = "业绩返现详情")
    @PostMapping("/performanceInfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<NaMiMarketingResponse> performanceInfo(@RequestBody NaMiMarketingRequest naMiMarketingRequest) {
        NaMiMarketingResponse response = naMiMarketingService.getPerformanceInfo(naMiMarketingRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<NaMiMarketingResponse>(response);
    }

    @ApiOperation(value = "邀请明细列表导出", notes = "邀请明细列表导出")
    @PostMapping("/export")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public void exportNaMiMarketList(HttpServletRequest req,HttpServletResponse res,@RequestBody NaMiMarketingRequest request) throws Exception{
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "邀请明细列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        request.setPageSize(defaultRowMaxCount);
        request.setCurrPage(1);
        NaMiMarketingResponse response = naMiMarketingService.getRecordList(request);
        Integer totalCount = response.getCount();
        if (totalCount == null) {
            totalCount = 0;
        }
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap1();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if(totalCount==0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            sheetNameTmp = sheetName + "_第" + i + "页";
            request.setCurrPage(i);
            List<NaMiMarketingVO> resultList = naMiMarketingService.getRecordList(request).getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,resultList );
            }


        }
        DataSet2ExcelSXSSFHelper.write2Response(req, res, fileName, workbook);
    }

    @ApiOperation(value = "业绩返现导出", notes = "业绩返现导出")
    @PostMapping("/performanceExport")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public void performanceExport(HttpServletRequest req,HttpServletResponse res,@RequestBody NaMiMarketingRequest request)throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "业绩返现列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        request.setPageSize(defaultRowMaxCount);
        request.setCurrPage(1);
        NaMiMarketingResponse response = naMiMarketingService.getPerformanceList(request);
        Integer totalCount = response.getCount();
        if (totalCount == null) {
            totalCount = 0;
        }
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap2();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if(totalCount==0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            sheetNameTmp = sheetName + "_第" + i + "页";
            request.setCurrPage(i);
            List<NaMiMarketingVO> resultList = naMiMarketingService.getPerformanceList(request).getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,resultList);
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(req, res, fileName, workbook);
    }


    private Map<String, String> buildMap1() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("username", "账户名");
        map.put("truename", "姓名");
        map.put("refferName", "邀请人账户名");
        map.put("regTime", "注册日期时间");
        return map;
    }

    private Map<String, String> buildMap2() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("username", "账户名");
        map.put("truename", "姓名");
        map.put("refferName", "邀请人账户名");
        map.put("tenderNo", "投资订单号");
        map.put("tenderAmount", "单笔投资金额（元）");
        map.put("term", "投资期限");
        map.put("productType", "产品类型");
        map.put("productNo", "产品编号");
        map.put("getReturnPerformance", "单笔当月产生的业绩（元）");
        map.put("returnAmount", "单笔返现金额（元）");
        map.put("regTime", "放款时间/加入时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }
}
