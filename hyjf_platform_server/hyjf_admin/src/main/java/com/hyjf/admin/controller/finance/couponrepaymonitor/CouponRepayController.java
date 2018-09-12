/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.couponrepaymonitor;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.CouponRepayService;
import com.hyjf.am.response.admin.AdminCouponRepayMonitorCustomizeResponse;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.vo.admin.AdminCouponRepayMonitorCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void exportAction(HttpServletResponse response, CouponRepayRequest form) throws UnsupportedEncodingException {
        // 表格sheet名称
        String sheetName = "优惠券还款监测";

        Map<String, Object> paraMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(form.getTimeStartSrch())){
            paraMap.put("timeStartSrch", form.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(form.getTimeEndSrch())){
            paraMap.put("timeEndSrch", form.getTimeEndSrch());
        }
        List<AdminCouponRepayMonitorCustomizeVO> resultList = this.couponRepayService.selectRecordList(form);
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] { "序号", "日期", "星期", "加息券待还统计", "加息券实际还款", "差额（实际-预测）"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (resultList != null && resultList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < resultList.size(); i++) {
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
                    AdminCouponRepayMonitorCustomizeVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 日期
                    else if (celLength == 1) {
                        cell.setCellValue(record.getDay());
                    }
                    // 星期
                    else if (celLength == 2) {
                        cell.setCellValue(record.getWeek());
                    }
                    // 加息券待还收益
                    else if (celLength == 3) {
                        cell.setCellValue(String.valueOf(record.getInterestWaitTotal()));
                    }
                    // 加息券已还收益
                    else if (celLength == 4) {
                        cell.setCellValue(String.valueOf(record.getInterestYesTotal()));
                    }
                    // 差额
                    else if (celLength == 5) {
                        cell.setCellValue(String.valueOf(record.getRepayGap()));
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
