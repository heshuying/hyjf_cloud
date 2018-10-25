/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter.promotion;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.beans.request.UserManagerRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlatformCountService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;
import com.hyjf.common.util.AsteriskProcessUtil;
import com.hyjf.common.util.CustomConstants;
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
 * @author fuqiang
 * @version PlatformCountController, v0.1 2018/7/18 17:46
 */
@Api(tags = "数据中心-平台统计")
@RestController
@RequestMapping("/hyjf-admin/promotion/platformcount")
public class PlatformCountController extends BaseController {
    @Autowired
    private PlatformCountService platformCountService;

    @ApiOperation(value = "数据中心-平台统计列表查询", notes = "数据中心-平台统计列表查询")
    @PostMapping("/searchaction")
    public AdminResult<List<PlatformCountCustomizeVO>> searchAction(@RequestBody PlatformCountRequestBean requestBean) {
        List<PlatformCountCustomizeVO> response = platformCountService.searchAction(requestBean);
        if (CollectionUtils.isEmpty(response)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(response);
    }


    public void exportAction(HttpServletRequest request, HttpServletResponse response, PlatformCountRequestBean form) throws Exception {
        // 表格sheet名称
        String sheetName = "平台统计";

        PlatformCountCustomizeVO platformCountCustomize = new PlatformCountCustomizeVO();

        List<PlatformCountCustomizeVO> recordList = platformCountService.searchAction(form);

        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "平台", "注册数", "开户数", "投资人数", "累计充值", "累计投资", "汇直投投资金额", "汇消费投资金额", "汇天利投资金额", "汇添金投资金额", "汇计划投资金额", "汇转让投资金额" };
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
                    PlatformCountCustomizeVO record = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 平台
                    else if (celLength == 1) {
                        cell.setCellValue(record.getSourceName());
                    }
                    // 访问数
                    /*else if (celLength == 2) {
                        cell.setCellValue(record.getAccessNumber());
                    }*/
                    // 注册数
                    else if (celLength == 2) {
                        cell.setCellValue(record.getRegistNumber());
                    }
                    // 开户数
                    else if (celLength == 3) {
                        cell.setCellValue(record.getAccountNumber());
                    }
                    // 投资人数

                    else if (celLength == 4) {
                        cell.setCellValue(record.getTenderNumber());
                    }
                    // 累计充值
                    else if (celLength == 5) {
                        cell.setCellValue(record.getRechargePrice());
                    }
                    // 累计投资
                    else if (celLength == 6) {
                        cell.setCellValue(record.getTenderPrice());
                    }
                    // 汇直投投资金额
                    else if (celLength == 7) {
                        cell.setCellValue(record.getHztTenderPrice());
                    }
                    // 汇消费投资金额
                    else if (celLength == 8) {
                        cell.setCellValue(record.getHxfTenderPrice());
                    }
                    // 汇天利投资金额
                    else if (celLength == 9) {
                        cell.setCellValue(record.getHtlTenderPrice());
                    }
                    // 汇添金投资金额
                    else if (celLength == 10) {
                        cell.setCellValue(record.getHtjTenderPrice());
                    }
                    // 汇计划投资金额
                    else if (celLength == 11) {
                        cell.setCellValue(record.getHjhTenderPrice());
                    }
                    // 汇转让投资金额
                    else if (celLength == 12) {
                        cell.setCellValue(record.getHzrTenderPrice());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }
    /**
     * 导出功能
     * @param request
     * @param response
     * @param form
     * @throws Exception
     */
    @ApiOperation(value = "导出excel", notes = "导出excel")
    @PostMapping("/exportAction")
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response, PlatformCountRequestBean form) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "平台统计";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        PlatformCountCustomizeVO platformCountCustomize = new PlatformCountCustomizeVO();

        List<PlatformCountCustomizeVO> recordList = platformCountService.searchAction(form);
        
        Integer totalCount = recordList.size();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        int minId = 0;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
        	
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
        	 helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList);
        }
//        for (int i = 1; i < sheetCount; i++) {
//
//            managerRequest.setPageSize(defaultRowMaxCount);
//            managerRequest.setCurrPage(i+1);
//            UserManagerResponse userManagerResponse2 = userCenterService.selectUserMemberList(managerRequest);
//            if (userManagerResponse2 != null && userManagerResponse2.getResultList().size()> 0) {
//                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
//                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  userManagerResponse2.getResultList());
//            } else {
//                break;
//            }
//        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("sourceName", "平台");
        map.put("accessNumber", "访问数");
        map.put("registNumber", "注册数");
        map.put("accountNumber", "开户数");
        map.put("tenderNumber", "投资人数");
        map.put("rechargePrice", "累计充值");
        map.put("tenderPrice", "累计投资");
        map.put("hztTenderPrice", "汇直投投资金额");
        map.put("hxfTenderPrice", "汇消费投资金额");
        map.put("htlTenderPrice", "汇天利投资金额");
        map.put("htjTenderPrice", "汇添金投资金额");
        map.put("hjhTenderPrice", "汇计划投资金额");
        map.put("hzrTenderPrice", "汇转让投资金额");
    
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }
}
