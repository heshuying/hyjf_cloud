/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.poundage;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PoundageService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: PoundageController, v0.1 2018/9/3 11:47
 */
@Api(value = "资金中心-手续费分账",tags = "资金中心-手续费分账")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/poundage")
public class PoundageController extends BaseController {

    @Autowired
    private PoundageService poundageService;

    /**
     * 按条件查询手续费分账列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "手续费分账列表",notes = "手续费分账列表")
    @PostMapping(value = "/poundagelist")
    public AdminResult<ListResult<PoundageCustomizeVO>> poundageList(@RequestBody PoundageListRequest request){
        Integer count = poundageService.getPoundageCount(request);
        count = (count == null)?0:count;
        PoundageCustomizeVO poundageSum = poundageService.getPoundageSum(request);
        List<PoundageCustomizeVO> poundageCustomizeVOList = poundageService.searchPoundageList(request);
        return new AdminResult<>(ListResult.build2(poundageCustomizeVOList,count,poundageSum));
    }

    /**
     * 根据id查询手续费分账
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "根据id查询手续费分账",notes = "根据id查询手续费分账")
    @PostMapping(value = "/poundagedetail")
    public AdminResult<PoundageCustomizeVO> poundageDetail(HttpServletRequest request, @RequestBody PoundageListRequest poundageListRequest){
        Integer userId = Integer.valueOf(getUser(request).getId());
        Integer id = poundageListRequest.getIdSer();
        PoundageCustomizeVO poundageCustomizeVO = null;
        if(id != null){
            poundageCustomizeVO = poundageService.getPoundageById(userId,id);
        }
        return new AdminResult<>(poundageCustomizeVO);
    }

    /**
     * 审核
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "审核",notes = "审核")
    @PostMapping(value = "/audit")
    public AdminResult audit(HttpServletRequest request,@RequestBody PoundageCustomizeVO poundageCustomizeVO){
        Integer userId = Integer.valueOf(getUser(request).getId());
        poundageCustomizeVO.setStatus(poundageCustomizeVO.STATUS_AUDIT);
        poundageCustomizeVO.setUpdater(userId);
        poundageCustomizeVO.setUpdateTime(GetDate.getNowTime10());
        boolean isSuccess = poundageService.updatePoundage(poundageCustomizeVO);
        if(isSuccess){
            return new AdminResult(SUCCESS,SUCCESS_DESC);
        }else{
            return new AdminResult(FAIL,FAIL_DESC);
        }
    }

    /**
     * 佣金分账
     * 1.查询银行平台账户管理费账户余额
     * 2.调用江西银行接口分佣
     * 成功:更新手续费分账信息--更新分账时间和分账状态（分账成功）;
     * 更新转入用户账户信息--更新转入用户江西银行总资产和江西银行可用余额;
     * 插入交易明细（资金中心-资金明细）--交易类型:分账,收支类型：收入;
     * 插入手续费账户明细（资金中心-银行平台用户-手续费账户明细）--交易类型:分账,收支类型:支出.
     * 失败:更新手续费分账信息--更新分账状态（分账失败）;
     * 插入手续费分账异常信息（异常中心-手续费异常处理）--可根据订单号和手续费账号查询状态.
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "佣金分账",notes = "佣金分账")
    @PostMapping(value = "/poundagetransfer")
    public AdminResult poundageTransfer(HttpServletRequest request,@RequestBody PoundageCustomizeVO poundageCustomizeVO){
        // 检查参数正确性
        poundageService.checkParams(poundageCustomizeVO);
        Integer userId = Integer.valueOf(getUser(request).getId());
        poundageCustomizeVO.setUpdater(userId);
        JSONObject jsonObject = poundageService.poundageTransfer(poundageCustomizeVO);
        return new AdminResult();
    }


    /**
     * 手续费分账列表导出
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "手续费分账列表导出",notes = "手续费分账列表导出")
    @PostMapping(value = "/exportpoundagelist")
    public void exportPoundageList(HttpServletRequest request, HttpServletResponse response,@RequestBody PoundageListRequest poundageListRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "手续费分账";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        poundageListRequest.setCurrPage(-1);
        Integer totalCount = poundageService.getPoundageCount(poundageListRequest);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            poundageListRequest.setPageSize(defaultRowMaxCount);
            poundageListRequest.setCurrPage(i);
            List<PoundageCustomizeVO> recordList = poundageService.searchPoundageList(poundageListRequest);
            if (recordList != null && recordList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "收款方用户名");
        map.put("realName", "收款方姓名");
        map.put("amount", "总分账金额");
        map.put("quantity", "总分账笔数");
        map.put("poundageTime", "分账时间段");
        map.put("status", "分账状态");
        map.put("addTime", "分账时间");
        map.put("nid", "分账订单号");
        map.put("seqNo", "分账流水号");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter poundageTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String poundageTimeStr = (String) object;
                Integer poundageTime = Integer.parseInt(poundageTimeStr);
                return poundageTime == null ? "" : GetDate.timestamptoStrYYYYMMDD(poundageTime);
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer status = (Integer) object;
                return PoundageCustomizeVO.getStatusStr(status);
            }
        };
        IValueFormatter addTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer addTime = (Integer) object;
                return addTime == null ? "" : GetDate.timestamptoStrYYYYMMDDHHMMSS(addTime);
            }
        };
        mapAdapter.put("poundageTime", poundageTimeAdapter);
        mapAdapter.put("status", statusAdapter);
        mapAdapter.put("addTime", addTimeAdapter);
        return mapAdapter;
    }

}
