/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.couponrepaymonitor;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.CouponRepayService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.AdminCouponRepayMonitorCustomizeResponse;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.vo.admin.AdminCouponRepayMonitorCustomizeVO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author zhangqingqing
 * @version CouponRepayController, v0.1 2018/7/9 11:12
 */
@Api(value = "资金中心-平台账户-加息券还款统计",tags ="资金中心-平台账户-加息券还款统计")
@RestController
@RequestMapping("/hyjf-admin/finance/couponrepaymonitor")
public class CouponRepayController extends BaseController {

    @Autowired
    private CouponRepayService couponRepayService;

    @ApiOperation(value = "统计图表画面 ",notes = "统计图表画面 ")
    @PostMapping(value = "/chart")
    public AdminResult chart(@RequestBody CouponRepayRequest form) {
        Map<String, Object> map = new HashMap<>();
        form.setLimitStart(0);
        form.setLimitEnd(1);
        List<AdminCouponRepayMonitorCustomizeVO> recordList = this.couponRepayService.selectRecordList(form);
        if (recordList != null && !recordList.isEmpty()) {
            map.put("latestUpdateTime", recordList.get(0).getUpdateTime());
        } else {
            map.put("latestUpdateTime", "");
        }
        return new AdminResult(map);
    }

    @ApiOperation(value = "统计图表画面",notes = "统计图表画面")
    @PostMapping(value = "/repayStatisticAction", produces = "application/json; charset=UTF-8")
    public AdminResult repayStatisticAction(){
        CouponRepayRequest form = new CouponRepayRequest();
        List<AdminCouponRepayMonitorCustomizeVO> recordList = this.couponRepayService.selectRecordList(form);
        String[] days;
        BigDecimal[] moneyWaitSum;
        BigDecimal[] moneyYesSum;
        if(recordList != null && !recordList.isEmpty()){
            days = new String[recordList.size()];
            moneyWaitSum = new BigDecimal[recordList.size()];
            moneyYesSum = new BigDecimal[recordList.size()];
            int j = 0;
            for(int i=recordList.size()-1; i>= 0; i--){
                days[j] = recordList.get(i).getDay();
                moneyWaitSum[j] = recordList.get(i).getInterestWaitTotal();
                moneyYesSum[j] = recordList.get(i).getInterestYesTotal();
                j++;
            }
        }else {
            days = new String[0];
            moneyWaitSum = new BigDecimal[0];
            moneyYesSum = new BigDecimal[0];
        }
        //返回结果
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("days", days);
        map.put("moneyWaitSum", moneyWaitSum);
        map.put("moneyYesSum", moneyYesSum);
        return new AdminResult(map);
    }

    /**
     * 画面初始化
     *
     * @param
     * @param form
     * @return
     */
    @ApiOperation(value = "画面初始化",notes = "画面初始化")
    @PostMapping(value = "/init")
    public AdminResult init(@RequestBody CouponRepayRequest form) {
        Map<String,Object> result = new HashMap<>();
        AdminCouponRepayMonitorCustomizeResponse response = couponRepayService.couponRepayMonitorCreatePage(form);
        if(response == null||response.getRecordTotal()==0) {
            return new AdminResult<>(ListResult.build(null,0));
        }
        List<AdminCouponRepayMonitorCustomizeVO> recordList = response.getResultList();
        result.put("recordList", recordList);
        result.put("latestUpdateTime",recordList.get(0).getUpdateTime());
        result.put("total",response.getRecordTotal());
        List<AdminCouponRepayMonitorCustomizeVO> recordListSum = this.couponRepayService.selectInterestSum(form);
        if(recordListSum != null && !recordListSum.isEmpty() && recordListSum.get(0) != null){
            result.put("interestWaitSum", recordListSum.get(0).getInterestWaitTotalAll());
            result.put("interestYesSum", recordListSum.get(0).getInterestYesTotalAll());
        }
        return new AdminResult(result);
    }

    /**
     * 数据导出
     * @param response
     * @param form
     * @throws Exception
     */
    @ApiOperation(value = "数据导出",notes = "数据导出")
    @PostMapping("/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponRepayRequest form) throws UnsupportedEncodingException {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "优惠券还款检测";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        int sheetCount = 0;
        String sheetNameTmp = sheetName + "_第1页";
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        form.setCurrPage(1);
        form.setPageSize(defaultRowMaxCount);

        AdminCouponRepayMonitorCustomizeResponse couponRepayResponse = couponRepayService.couponRepayMonitorCreatePage(form);
        if (couponRepayResponse == null || couponRepayResponse.getRecordTotal() <= 0  ){
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else{
            int totalCount = couponRepayResponse.getRecordTotal();
            sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, couponRepayResponse.getResultList());
        }

        for (int i = 1; i < sheetCount; i++) {
            form.setCurrPage(i+1);
            AdminCouponRepayMonitorCustomizeResponse couponRepayResponse2 = couponRepayService.couponRepayMonitorCreatePage(form);
            List<AdminCouponRepayMonitorCustomizeVO> couponRepayList = new ArrayList<>();
            if (couponRepayResponse2 != null){
                couponRepayList = couponRepayResponse2.getResultList();
            }
            if (!CollectionUtils.isEmpty(couponRepayList)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  couponRepayList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }


    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("day", "日期");
        map.put("week", "星期");
        map.put("interestWaitTotal", "加息券代还统计");
        map.put("interestYesTotal", "加息券实际还款");
        map.put("repayGap", "差额(实际-预测)");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter interestWaitTotalAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal interestWaitTotal = (BigDecimal) object;
                return String.valueOf(interestWaitTotal);
            }
        };
        IValueFormatter interestYesTotalAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal interestYesTotal = (BigDecimal) object;
                return String.valueOf(interestYesTotal);
            }
        };
        IValueFormatter repayGapAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal repayGap = (BigDecimal) object;
                return String.valueOf(repayGap);
            }
        };
        mapAdapter.put("interestWaitTotal",interestWaitTotalAdapter);
        mapAdapter.put("interestYesTotal",interestYesTotalAdapter);
        mapAdapter.put("repayGap",repayGapAdapter);
        return mapAdapter;
    }
}
