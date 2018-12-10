package com.hyjf.admin.controller.productcenter.borrow.borrowcreditpayment;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowCreditListResultBean;
import com.hyjf.admin.beans.BorrowCreditRepayResultBean;
import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowCreditTenderService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * admin: 汇转让：还款信息
 * @author zhangyk
 * @date 2018/7/11 13:42
 */
@Api(value = "产品中心-汇转让-还款信息",tags ="产品中心-汇转让-还款信息")
@RestController
@RequestMapping("/hyjf-admin/borrow/creditPayment")
public class AdminBorrowCreditPaymentController {


    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;

    @Autowired
    public SystemConfig systemConfig;

    public static final String PERMISSIONS = "borrowcreditrepay";

    /**
     * com.hyjf.admin.manager.borrow.borrowcreditrepay.BorrowCreditRepayController.search()
     * @author zhangyk
     * @date 2018/8/29 10:24
     */
    @ApiOperation(value = "还款信息", notes = "还款信息")
    @PostMapping("/getList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    @ResponseBody
    public Object  getBorrowCreditPaymentList(@RequestBody BorrowCreditRepayRequest request){
        AdminResult result = borrowCreditTenderService.getBorrowCreditRepayList(request);
        return result;
    }

    @ApiOperation(value = "还款信息导出", notes = "还款信息导出")
    @PostMapping("/exportData")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public void  exportBorrowCreditList(@RequestBody BorrowCreditRepayRequest request, HttpServletRequest servletRequest, HttpServletResponse response) throws UnsupportedEncodingException {
        int totalCount = borrowCreditTenderService.selectBorrowCreditRepayCount(request);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        defaultRowMaxCount = 3;//todo 测试导出
        String sheetName = "汇转让-还款信息";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            //请求第一页5000条
            request.setPageSize(defaultRowMaxCount);
            request.setCurrPage(i);
            AdminResult borrowCreditList = borrowCreditTenderService.getBorrowCreditRepayList(request);
            if (borrowCreditList != null) {
                BorrowCreditRepayResultBean data = (BorrowCreditRepayResultBean) borrowCreditList.getData();
                if (data != null && data.getRecordList().size() > 0) {
                    sheetNameTmp = sheetName + "_第" + (i) + "页";
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  data.getRecordList());
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(servletRequest, response, fileName, workbook);
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName","承接人");
        map.put("creditNid","债转编号");
        map.put("creditUserName","出让人");
        map.put("bidNid","项目编号");
        map.put("assignNid","订单号");
        map.put("assignCapital","应收本金");
        map.put("assignInterest","应收利息");
        map.put("assignAccount","应收本息");
        map.put("assignRepayAccount","已收本息");
        map.put("creditFee","还款服务费");
        map.put("status","还款状态");
        map.put("addTime","债权承接时间");
        map.put("assignRepayNextTime","下次还款时间");
        return map;
    }

    @ApiOperation(value = "还款信息明细", notes = "还款信息明细")
    @PostMapping("/infoDetail")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    @ResponseBody
    public Object getCreditRepayInfoDetail(@RequestBody BorrowCreditRepayRequest request){
       AdminResult result =  borrowCreditTenderService.getBorrowCreditRepayInfoList(request);
       return result;
    }

}
