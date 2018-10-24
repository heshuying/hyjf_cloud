/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.promotion.PcChannelStatisticsService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.promotion.PcChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.PcChannelStatisticsVO;
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
 * @version PcChannelStatisticsController, v0.1 2018/9/26 11:47
 */
@Api(tags = "推广中心-PC渠道统计")
@RestController
@RequestMapping("/hyjf-admin/pc_channel_statistics")
public class PcChannelStatisticsController extends BaseController {
    @Autowired
    private PcChannelStatisticsService pcChannelStatisticsService;

    @ApiOperation(value = "查询pc渠道统计", notes = "查询pc渠道统计")
    @PostMapping("/search")
    public AdminResult searchAction(@RequestBody PcChannelStatisticsRequest request) {
        PcChannelStatisticsResponse response = pcChannelStatisticsService.searchPcChannelStatistics(request);
        return new AdminResult(response);
    }


    @ApiOperation(value = "导出功能", notes = "导出功能")
    @PostMapping("/export")
    public void export(@RequestBody PcChannelStatisticsRequest request, HttpServletRequest httpRequest,HttpServletResponse response) throws Exception {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "渠道统计";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        int sheetCount = 0;
        String sheetNameTmp = sheetName + "_第1页";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        request.setCurrPage(1);
        request.setPageSize(defaultRowMaxCount);

        PcChannelStatisticsResponse pcChannelStatisticsResponse = this.pcChannelStatisticsService.searchPcChannelStatistics(request);

        if (pcChannelStatisticsResponse == null || pcChannelStatisticsResponse.getCount() <= 0){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = pcChannelStatisticsResponse.getCount();
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, pcChannelStatisticsResponse.getResultList());
        }

        for (int i = 1; i < sheetCount; i++) {
            request.setCurrPage(i+1);
            PcChannelStatisticsResponse PcChannelStatisticsResponse2 = this.pcChannelStatisticsService.searchPcChannelStatistics(request);
            if (PcChannelStatisticsResponse2 != null && PcChannelStatisticsResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  PcChannelStatisticsResponse2.getResultList());
            } else {
                break;
            }
        }

        DataSet2ExcelSXSSFHelper.write2Response(httpRequest, response, fileName, workbook);

    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("sourceName", "渠道");
        map.put("accessNumber", "访问数");
        map.put("registNumber", "注册数");
        map.put("openAccountNumber", "开户数");
        map.put("tenderNumber", "投资人数");
        map.put("cumulativeRecharge", "累计充值");
        map.put("cumulativeInvestment", "累计投资");
        map.put("hztTenderPrice", "汇直投投资金额");
        map.put("hxfTenderPrice", "汇消费投资金额");
        map.put("htlTenderPrice", "汇天利投资金额");
        map.put("htjTenderPrice", "汇添金投资金额");
        map.put("rtbTenderPrice", "汇金理财投资金额");
        map.put("hzrTenderPrice", "汇转让投资金额");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }
}
