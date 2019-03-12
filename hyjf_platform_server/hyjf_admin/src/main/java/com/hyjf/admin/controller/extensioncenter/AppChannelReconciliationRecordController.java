/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.promotion.ChannelReconciliationService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author fq
 * @version AppChannelReconciliationController, v0.1 2018/9/24 10:04
 */
@Api(tags = "推广中心-APP渠道对账")
@RestController
@RequestMapping("/hyjf-admin/app_channelreconciliation")
public class AppChannelReconciliationRecordController extends BaseController {
    @Autowired
    private ChannelReconciliationService channelService;

    /** 查看权限 */
    public static final String PERMISSIONS = "appchannelrecon";

    @ApiOperation(value = "散标列表查询", notes = "散标列表查询")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult searchAction(@RequestBody ChannelReconciliationRequest request) {
        if (request.getInvestEndTime()==null || request.getInvestStartTime()==null) {
            return new AdminResult(FAIL,"出借时间不能为空");
        }
        ChannelReconciliationResponse response = channelService.searchAppAction(request);
        return new AdminResult(response);
    }

    @ApiOperation(value = "计划列表查询", notes = "计划列表查询")
    @PostMapping("/search_hjh")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult searchHJHAction(@RequestBody ChannelReconciliationRequest request) {
        if (request.getInvestEndTime()==null || request.getInvestStartTime()==null) {
            return new AdminResult(FAIL,"出借时间不能为空");
        }
        ChannelReconciliationResponse response = channelService.searchAppHJHAction(request);
        return new AdminResult(response);
    }

    @ApiOperation(value = "查询所有渠道", notes = "查询所有渠道")
    @PostMapping("/search_utmlist")
    public AdminResult searchUtmList() {
        // 1:app
        List<UtmVO> list = channelService.searchUtmList(1);
        return new AdminResult(list);
    }



    /**
     * 导出功能
     *
     * @param request
     * @param response
     */
        @ApiOperation(value = "导出散标列表", notes = "导出散标列表")
        @PostMapping("/export")
        @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
        public void exportToExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody  ChannelReconciliationRequest channelReconciliationRequest) throws Exception {
            List<UtmVO> list = channelService.searchUtmList(1);
            // 表格sheet名称
            String sheetName = "app渠道对账-散标";
            //sheet默认最大行数
            int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
            // 文件名称
            String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
            // 声明一个工作薄
            SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
            DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
            //查询总条数
            Integer totalCount   = channelService.searchAppActionCount(channelReconciliationRequest);
            int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            Map<String, String> beanPropertyColumnMap = buildMap();
            Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
            String sheetNameTmp = sheetName + "_第1页";
            if (totalCount == 0) {
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
            } else {
                for (int i = 1; i <= sheetCount; i++) {
                    //请求第一页5000条
                    channelReconciliationRequest.setPageSize(defaultRowMaxCount);
                    channelReconciliationRequest.setCurrPage(i);
                    ChannelReconciliationResponse channelReconciliationResponse = channelService.searchAppAction(channelReconciliationRequest);
                    if (channelReconciliationResponse != null && channelReconciliationResponse.getResultList().size()> 0) {
                        sheetNameTmp = sheetName + "_第" + (i ) + "页";
                        helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,channelReconciliationResponse.getResultList());
                    } else {
                        break;
                    }
                }
            }
            DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
        }

	    private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("userName", "用户名");
	        map.put("utmName", "渠道");
	        map.put("registTime", "注册时间");
	        map.put("orderCode", "出借订单");
	        map.put("borrowNid", "项目编号");
	        map.put("borrowPeriod", "标的期限");
	        map.put("investAmount", "出借金额");
	        map.put("firstFlag", "是否首投");
	        map.put("investTime", "出借时间");

	        return map;
	    }
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        IValueFormatter firstFlagAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	            	Integer firstFlag = (Integer) object;
	                   if (firstFlag==1) {
                        return "是";
                    }else {
                 	   return "否";
                    }
	             
	            }
	        };
	        mapAdapter.put("firstFlag", firstFlagAdapter);
	        return mapAdapter;
	    }


    /**
     * 导出功能
     *
     * @param request
     */
    @ApiOperation(value = "导出计划列表", notes = "导出计划列表")
    @PostMapping("/export_hjh")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportHjhAction(HttpServletRequest request, HttpServletResponse response,@RequestBody  ChannelReconciliationRequest channelReconciliationRequest) throws Exception {
        // 表格sheet名称
        String sheetName = "app渠道对账-智投服务";
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //查询总条数
        Integer totalCount = channelService.searchAppHJHCount(channelReconciliationRequest);
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap2();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter2();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        } else {
            for (int i = 1; i <= sheetCount; i++) {
                //请求第一页5000条
                channelReconciliationRequest.setPageSize(defaultRowMaxCount);
                channelReconciliationRequest.setCurrPage(i);
                ChannelReconciliationResponse channelReconciliationResponse = channelService.searchAppHJHAction(channelReconciliationRequest);
                if (channelReconciliationResponse != null && channelReconciliationResponse.getResultList().size() > 0) {
                    sheetNameTmp = sheetName + "_第" + (i) + "页";
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, channelReconciliationResponse.getResultList());
                } else {
                    break;
                }
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }
    //{ "序号", "用户名", "渠道","注册时间", "智投订单号", "智投编号", "服务回报期限", "授权服务金额","是否首投", "投资时间" };
		    private Map<String, String> buildMap2() {
		        Map<String, String> map = Maps.newLinkedHashMap();
		        map.put("userName", "用户名");
		        map.put("utmName", "渠道");
		        map.put("registTime", "注册时间");
		        map.put("orderCode", "智投订单号");
		        map.put("borrowNid", "智投编号");
		        map.put("borrowPeriod", "服务回报期限");
		        map.put("investAmount", "授权服务金额");
		        map.put("firstFlag", "是否首投");
		        map.put("investTime", "出借时间");
		        return map;
		    }
		    private Map<String, IValueFormatter> buildValueAdapter2() {
		        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		        IValueFormatter firstFlagAdapter = new IValueFormatter() {
		            @Override
		            public String format(Object object) {
		            	Integer firstFlag = (Integer) object;
		                   if (firstFlag==1) {
	                        return "是";
	                    }else {
	                 	   return "否";
	                    }
		             
		            }
		        };
		        mapAdapter.put("firstFlag", firstFlagAdapter);
		        return mapAdapter;
		    }

}
