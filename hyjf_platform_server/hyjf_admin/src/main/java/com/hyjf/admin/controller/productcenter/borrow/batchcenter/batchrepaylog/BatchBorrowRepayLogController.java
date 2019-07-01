package com.hyjf.admin.controller.productcenter.borrow.batchcenter.batchrepaylog;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BatchBorrowRecoverLogService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverLogVo;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 批次还款记录
 * @author hesy
 */
@Api(value = "产品中心-批次中心-批次还款记录",tags ="产品中心-批次中心-批次还款记录")
@RestController
@RequestMapping("/hyjf-admin/batchBorrowRepayLog")
public class BatchBorrowRepayLogController extends BaseController{

    private static final String NAME_CLASS = "REPAY_STATUS";

    private static final String PERMISSIONS = "batchborrowrepay";

    @Autowired
    private BatchBorrowRecoverLogService batchBorrowRecoverService;

    @ApiOperation(value = "批次中心-批次还款记录页面初始化", notes = "页面初始化")
    @PostMapping(value = "/batchBorrowRepayInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public JSONObject batchBorrowRepayInit() {
        JSONObject jsonObject = batchBorrowRecoverService.initPage(NAME_CLASS);
        BatchBorrowRecoverRequest request = new BatchBorrowRecoverRequest();
        JSONObject borrowRepayList = querybatchBorrowRepayList(request);
        if(borrowRepayList != null){
            List<BatchBorrowRecoverLogVo> listAccountDetail = (List<BatchBorrowRecoverLogVo>) borrowRepayList.get(LIST);
            if(listAccountDetail != null && listAccountDetail.size() > 0){
                jsonObject.put("批次还款列表","listAccountDetail");
                jsonObject.put("listAccountDetail",listAccountDetail);
                jsonObject.put("hjhDebtCreditVoListTotal",borrowRepayList.get(TRCORD));
            }
        }
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次还款记录页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/querybatchBorrowRepayList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public JSONObject querybatchBorrowRepayList(@RequestBody BatchBorrowRecoverRequest request) {
        request.setApiType(1);
        request.setNameClass(NAME_CLASS);
        JSONObject jsonObject = batchBorrowRecoverService.queryBatchBorrowRecoverList(request);
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次还款记录页面导出功能", notes = "导出功能")
    @PostMapping(value = "/exportBatchBorrowRepayList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public void exportBatchBorrowRepayList(HttpServletRequest request, @RequestBody BatchBorrowRecoverRequest form,HttpServletResponse response) throws UnsupportedEncodingException {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "批次还款记录列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        form.setPageSize(defaultRowMaxCount);
        form.setApiType(1);
        form.setCurrPage(1);
        //查询导出记录总数
        Integer totalCount = batchBorrowRecoverService.getBatchBorrowRecoverCount(form);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            form.setCurrPage(i);
            form.setPageSize(defaultRowMaxCount);
            JSONObject jsonObject2 = this.querybatchBorrowRepayList(form);
            List<BatchBorrowRecoverLogVo> recordList2 = (List<BatchBorrowRecoverLogVo>) jsonObject2.get(LIST);
            if (recordList2 != null && recordList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  recordList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid","项目编号");
        map.put("seqNo","交易流水号");
        map.put("bankSeqNo","银行交易流水号");
        map.put("instName","资产来源");
        map.put("batchNo","批次号");
        map.put("isRepayOrgFlag","还款角色");
        map.put("userName","还款用户名");
        map.put("periodNow","当前还款期数");
        map.put("borrowPeriod","总期数");
        map.put("borrowAccount","借款金额");
        map.put("batchServiceFee","还款服务费");
        map.put("batchAmount","应还款");
        map.put("repaid","已还款");
        map.put("batchCounts","总笔数");
        map.put("sucCounts","成功笔数");
        map.put("sucAmount","成功金额");
        map.put("failCounts","失败笔数");
        map.put("failAmount","失败金额");
        map.put("createTime","提交时间");
        map.put("updateTime","更新时间");
        map.put("statusStr","批次状态");
        map.put("data","银行回执说明");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter IsRepayOrgFlagAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String flag = "0".equals(object)?"借款人":"担保机构";
                return flag;
            }
        };
        IValueFormatter valueToString = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object.toString();
            }
        };
        mapAdapter.put("isRepayOrgFlag", IsRepayOrgFlagAdapter);
        mapAdapter.put("borrowAccount", valueToString);
        mapAdapter.put("batchServiceFee", valueToString);
        mapAdapter.put("batchAmount", valueToString);
        mapAdapter.put("sucAmount", valueToString);
        mapAdapter.put("failAmount", valueToString);
        mapAdapter.put("repaid", valueToString);

        return mapAdapter;
    }
}
