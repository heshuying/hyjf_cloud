/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AutoTenderExceptionRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.promotion.ChannelReconciliationService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.response.admin.promotion.ChannelReconciliationResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.ChannelReconciliationRequest;
import com.hyjf.am.vo.admin.AdminPlanAccedeListCustomizeVO;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.admin.promotion.channel.ChannelReconciliationVO;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
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
import java.util.Objects;

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
        List<UtmVO> list = channelService.searchUtmList(1);
        if (request.getUtmPlat() == null) {
            List<String> utmList = new ArrayList<>();
            for (UtmVO vo : list) {
                if (Objects.equals(vo.getDelFlag(), 0)) {
                    utmList.add(vo.getSourceId().toString());
                }
            }
            String[] integers = new String[utmList.size()];
            String[] array = utmList.toArray(integers);
            request.setUtmPlat(array);
        }
        ChannelReconciliationResponse response = channelService.searchAppAction(request);
        return new AdminResult(response);
    }

    @ApiOperation(value = "计划列表查询", notes = "计划列表查询")
    @PostMapping("/search_hjh")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult searchHJHAction(@RequestBody ChannelReconciliationRequest request) {
        List<UtmVO> list = channelService.searchUtmList(1);
        if (request.getUtmPlat() == null) {
            List<String> utmList = new ArrayList<>();
            for (UtmVO vo : list) {
                if (Objects.equals(vo.getDelFlag(), 0)) {
                    utmList.add(vo.getSourceId().toString());
                }
            }
            String[] integers = new String[utmList.size()];
            String[] array = utmList.toArray(integers);
            request.setUtmPlat(array);
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


    public void exportAction(@RequestBody ChannelReconciliationRequest request, HttpServletResponse response) throws Exception {

        List<UtmVO> list = channelService.searchUtmList(1);

        // 表格sheet名称
        String sheetName = "PC渠道对账-散标";

        if (request.getUtmPlat() == null) {
            List<String> utmList = new ArrayList<>();
            for (UtmVO vo : list) {
                if (Objects.equals(vo.getDelFlag(), 0)) {
                    utmList.add(vo.getSourceId().toString());
                }
            }
            String[] integers = new String[utmList.size()];
            String[] array = utmList.toArray(integers);
            request.setUtmPlat(array);
        }

        ChannelReconciliationResponse channelReconciliationResponse = channelService.searchAppAction(request);
        if(channelReconciliationResponse != null) {
            List<ChannelReconciliationVO> recordList = channelReconciliationResponse.getResultList();

            String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

            String[] titles = new String[] { "序号", "用户名", "渠道","注册时间","投资订单", "借款编号", "标的期限", "投资金额","是否首投", "投资时间" };
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
                        ChannelReconciliationVO record = recordList.get(i);

                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);

                        // 序号
                        if (celLength == 0) {
                            cell.setCellValue(i + 1);
                        }
                        // 用户名
                        else if (celLength == 1) {
                            cell.setCellValue(record.getUserName()==null?"":record.getUserName());
                        }
                        // 渠道
                        else if (celLength == 2) {
                            cell.setCellValue(record.getUtmName()==null?"":record.getUtmName());
                        }
                        // 注册时间
                        else if (celLength == 3) {
                            cell.setCellValue(record.getRegistTime()==null?"":record.getRegistTime());
                        }
                        // 投资订单
                        else if (celLength == 4) {
                            cell.setCellValue(record.getOrderCode()==null?"":record.getOrderCode());
                        }
                        // 借款编号
                        else if (celLength == 5) {
                            cell.setCellValue(record.getBorrowNid()==null?"":record.getBorrowNid());
                        }
                        // 标的期限
                        else if (celLength == 6) {
                            cell.setCellValue(record.getBorrowPeriod()==null?"":record.getBorrowPeriod());
                        }
                        // 投资金额
                        else if (celLength == 7) {
                            cell.setCellValue(record.getInvestAmount()==null?"":record.getInvestAmount());
                        }
                        // 是否首投
                        else if (celLength == 8) {
                            if(record.getFirstFlag() != null&&record.getFirstFlag().intValue()==1){
                                cell.setCellValue("是");
                            }else{
                                cell.setCellValue("否");
                            }
                        }
                        // 投资时间
                        else if (celLength == 9) {
                            cell.setCellValue(record.getInvestTime()==null?"":record.getInvestTime());
                        }
                    }
                }
            }
            // 导出
            ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        }

    }
    /**
     * 导出功能
     *
     * @param request
     * @param response
     * @param form
     */
    @ApiOperation(value = "导出散标列表", notes = "导出散标列表")
    @PostMapping("/export")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	 public void exportToExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody ChannelReconciliationRequest channelReconciliationRequest) throws Exception {
        List<UtmVO> list = channelService.searchUtmList(1);

        // 表格sheet名称
        String sheetName = "PC渠道对账-散标";

        if (channelReconciliationRequest.getUtmPlat() == null) {
            List<String> utmList = new ArrayList<>();
            for (UtmVO vo : list) {
                if (Objects.equals(vo.getDelFlag(), 0)) {
                    utmList.add(vo.getSourceId().toString());
                }
            }
            String[] integers = new String[utmList.size()];
            String[] array = utmList.toArray(integers);
            channelReconciliationRequest.setUtmPlat(array);
        }

        ChannelReconciliationResponse channelReconciliationResponse = channelService.searchAppAction(channelReconciliationRequest);
        List<ChannelReconciliationVO> recordList=null;
        if(channelReconciliationResponse != null) {
           recordList = channelReconciliationResponse.getResultList();
        }else {
        	recordList=new ArrayList<ChannelReconciliationVO>();
        }
	        //sheet默认最大行数
	        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
	        
	        Integer totalCount = recordList.size();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = sheetName + "_第1页";
	        if (totalCount == 0) {
	        	
	            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
	        }else {
	            // 当前下载数据超过一页上限
	            if(defaultRowMaxCount < recordList.size()) {
              helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(0, defaultRowMaxCount));
              for (int i = 1; i < sheetCount; i++) {
                  sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                  helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(defaultRowMaxCount * i, defaultRowMaxCount * (i + 1)));
              }
          } else {
              helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(0, recordList.size()));
          }
      }
	        
	        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	    }
	    private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("userName", "用户名");
	        map.put("utmName", "渠道");
	        map.put("registTime", "注册时间");
	        map.put("orderCode", "投资订单");
	        map.put("borrowNid", "借款编号");
	        map.put("borrowPeriod", "标的期限");
	        map.put("investAmount", "投资金额");
	        map.put("firstFlag", "是否首投");
	        map.put("investTime", "投资时间");

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



    public void exportHjhAction(@RequestBody ChannelReconciliationRequest request, HttpServletResponse response) throws Exception {

        // 表格sheet名称
        String sheetName = "PC渠道对账-智投服务";

        List<UtmVO> list = channelService.searchUtmList(1);

        if (request.getUtmPlat() == null) {
            List<String> utmList = new ArrayList<>();
            for (UtmVO vo : list) {
                if (Objects.equals(vo.getDelFlag(), 0)) {
                    utmList.add(vo.getSourceId().toString());
                }
            }
            String[] integers = new String[utmList.size()];
            String[] array = utmList.toArray(integers);
            request.setUtmPlat(array);
        }

        ChannelReconciliationResponse channelReconciliationResponse = channelService.searchAppHJHAction(request);

        if (channelReconciliationResponse != null) {
            List<ChannelReconciliationVO> recordList = channelReconciliationResponse.getResultList();
            String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

            String[] titles = new String[] { "序号", "用户名", "渠道","注册时间", "智投订单号", "智投编号", "服务回报期限", "授权服务金额","是否首投", "投资时间" };
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
                        ChannelReconciliationVO record = recordList.get(i);

                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);

                        // 序号
                        if (celLength == 0) {
                            cell.setCellValue(i + 1);
                        }
                        // 用户名
                        else if (celLength == 1) {
                            cell.setCellValue(record.getUserName()==null?"":record.getUserName());
                        }
                        // 渠道
                        else if (celLength == 2) {
                            cell.setCellValue(record.getUtmName()==null?"":record.getUtmName());
                        }
                        // 注册时间
                        else if (celLength == 3) {
                            cell.setCellValue(record.getRegistTime()==null?"":record.getRegistTime());
                        }
                        // 投资订单
                        else if (celLength == 4) {
                            cell.setCellValue(record.getOrderCode()==null?"":record.getOrderCode());
                        }
                        // 借款编号
                        else if (celLength == 5) {
                            cell.setCellValue(record.getBorrowNid()==null?"":record.getBorrowNid());
                        }
                        // 标的期限
                        else if (celLength == 6) {
                            cell.setCellValue(record.getBorrowPeriod()==null?"":record.getBorrowPeriod());
                        }
                        // 投资金额
                        else if (celLength == 7) {
                            cell.setCellValue(record.getInvestAmount()==null?"":record.getInvestAmount());
                        }
                        // 是否首投
                        else if (celLength == 8) {
                            if(record.getFirstFlag() != null&&record.getFirstFlag().intValue()==1){
                                cell.setCellValue("是");
                            }else{
                                cell.setCellValue("否");
                            }
                        }
                        // 投资时间
                        else if (celLength == 9) {
                            cell.setCellValue(record.getInvestTime()==null?"":record.getInvestTime());
                        }
                    }
                }
            }
            // 导出
            ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        }
    }
    /**
     * 导出功能
     *
     * @param request
     * @param modelAndView
     * @param form
     */
    @ApiOperation(value = "导出计划列表", notes = "导出计划列表")
    @PostMapping("/export_hjh")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	 public void exportHjhAction(HttpServletRequest request, HttpServletResponse response, @RequestBody ChannelReconciliationRequest channelReconciliationRequest) throws Exception {
        // 表格sheet名称
        String sheetName = "PC渠道对账-智投服务";
        List<UtmVO> list = channelService.searchUtmList(1);

        if (channelReconciliationRequest.getUtmPlat() == null) {
            List<String> utmList = new ArrayList<>();
            for (UtmVO vo : list) {
                if (Objects.equals(vo.getDelFlag(), 0)) {
                    utmList.add(vo.getSourceId().toString());
                }
            }
            String[] integers = new String[utmList.size()];
            String[] array = utmList.toArray(integers);
            channelReconciliationRequest.setUtmPlat(array);
        }

        ChannelReconciliationResponse channelReconciliationResponse = channelService.searchAppHJHAction(channelReconciliationRequest);

            List<ChannelReconciliationVO> recordList =null;
	        if(channelReconciliationResponse != null) {
	           recordList = channelReconciliationResponse.getResultList();
	        }else {
	        	recordList=new ArrayList<ChannelReconciliationVO>();
	        }
		        //sheet默认最大行数
		        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

		        // 文件名称
		        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		        // 声明一个工作薄
		        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		        
		        Integer totalCount = recordList.size();

		        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		        Map<String, String> beanPropertyColumnMap = buildMap2();
		        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter2();
		        String sheetNameTmp = sheetName + "_第1页";
		        if (totalCount == 0) {
		        	
		            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		        }else {
		            // 当前下载数据超过一页上限
		            if(defaultRowMaxCount < recordList.size()) {
	              helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(0, defaultRowMaxCount));
	              for (int i = 1; i < sheetCount; i++) {
	                  sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
	                  helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(defaultRowMaxCount * i, defaultRowMaxCount * (i + 1)));
	              }
	          } else {
	              helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(0, recordList.size()));
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
		        map.put("investTime", "投资时间");
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
