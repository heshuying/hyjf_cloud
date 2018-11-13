package com.hyjf.admin.controller.productcenter.borrow.credit;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.HjhDebtCreditService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
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
 * @Auther:yangchangwei
 * @Date:2018/7/3
 * @Description: 汇计划-转让记录
 */
@Api(value = "产品中心-汇计划-转让记录",tags="产品中心-汇计划-转让记录")
@RestController
@RequestMapping("/hyjf-admin/hjhDebtCredit")
public class HjhDebtCreditController extends BaseController{


    private static final String PERMISSIONS = "hjhDebtCredit";

    @Autowired
    private HjhDebtCreditService hjhDebtCreditService;


    @ApiOperation(value = "汇计划-转让记录页面初始化", notes = "页面初始化")
    @PostMapping(value = "/hjhDebtCreditInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject hjhDebtCreditInit() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",SUCCESS);
        List<BorrowStyleVO> styleVOList = hjhDebtCreditService.selectBorrowStyleList();
        if(styleVOList != null && styleVOList.size() > 0){
            jsonObject.put("还款方式列表","borrowStyleList");
            List<DropDownVO> dropDownVOS = ConvertUtils.convertListToDropDown(styleVOList, "id", "name");
            jsonObject.put("borrowStyleList",dropDownVOS);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取还款方式列表失败！");
        }
        //转让状态
        Map<String, String> hjhDebtCreditStatus = CacheUtil.getParamNameMap(CustomConstants.HJH_DEBT_CREDIT_STATUS);
        List<DropDownVO> hjhDebtCreditStatusVo = ConvertUtils.convertParamMapToDropDown(hjhDebtCreditStatus);
        if(hjhDebtCreditStatus != null && hjhDebtCreditStatus.size() > 0){
            jsonObject.put("转让状态列表","hjhDebtCreditStatus");
            jsonObject.put("hjhDebtCreditStatus",hjhDebtCreditStatusVo);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取转让状态列表失败！");
        }
        //汇计划债转还款状态
        Map<String, String> hjhDebtRepayStatus = CacheUtil.getParamNameMap(CustomConstants.HJH_DEBT_REPAY_STATUS);
        List<DropDownVO> hjhDebtRepayStatusVo = ConvertUtils.convertParamMapToDropDown(hjhDebtRepayStatus);
        if(hjhDebtRepayStatus != null && hjhDebtRepayStatus.size() > 0){
            jsonObject.put("还款状态列表","hjhDebtRepayStatus");
            jsonObject.put("hjhDebtRepayStatus",hjhDebtRepayStatusVo);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取还款状态列表失败！");
        }
        HjhDebtCreditListRequest request = new HjhDebtCreditListRequest();
        JSONObject creditDetail = queryHjhDebtCreditDetail(request);
        if(creditDetail != null){
            List<HjhDebtCreditVo> hjhDebtCreditVoList = (List<HjhDebtCreditVo>) creditDetail.get(LIST);
            if(hjhDebtCreditVoList != null && hjhDebtCreditVoList.size() > 0){
                jsonObject.put("汇计划转让列表","hjhDebtCreditVoList");
                jsonObject.put("hjhDebtCreditVoList",hjhDebtCreditVoList);
                jsonObject.put("hjhDebtCreditVoListTotal",creditDetail.get(TRCORD));
            }
        }
        return jsonObject;
    }

    @ApiOperation(value = "汇计划-转让记录页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/queryHjhDebtCreditDetail")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject queryHjhDebtCreditDetail(@RequestBody HjhDebtCreditListRequest request) {
        logger.info("queryHjhDebtCreditDetail start, request is :{}", request);
        JSONObject jsonObject = null;
        HjhDebtCreditReponse hjhDebtCreditReponse = hjhDebtCreditService.queryHjhDebtCreditList(request);
        List<HjhDebtCreditVo> hjhDebtCreditVoList = new ArrayList<HjhDebtCreditVo>();
        if (null != hjhDebtCreditReponse) {
            List<HjhDebtCreditVo> listAccountDetail = hjhDebtCreditReponse.getResultList();
            Integer recordCount = hjhDebtCreditReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                hjhDebtCreditVoList.addAll(listAccountDetail);
            }
            if (hjhDebtCreditVoList.size() > 0) {
                hjhDebtCreditService.queryHjhDebtCreditListStatusName(hjhDebtCreditVoList);
                jsonObject = this.success(recordCount, hjhDebtCreditVoList);
            } else {
                jsonObject = this.success(0,new ArrayList<>());
            }
        }else{
            jsonObject = this.success(0,new ArrayList<>());
        }
        return jsonObject;
    }

//    @ApiOperation(value = "汇计划-转让记录页面导出列表", notes = "页面列表导出")
//    @PostMapping(value = "/exportHjhDebtCreditDetail")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "成功")
//    })
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
//    public JSONObject exportHjhDebtCreditDetail(@RequestBody HjhDebtCreditListRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
//
//        request.setCurrPage(-1);
//        HjhDebtCreditReponse hjhDebtCreditReponse = hjhDebtCreditService.queryHjhDebtCreditList(request);
//
//        List<HjhDebtCreditVo> resultList = hjhDebtCreditReponse.getResultList();
//        hjhDebtCreditService.queryHjhDebtCreditListStatusName(resultList);
//        // 表格sheet名称
//        String sheetName = "汇计划转让记录";
//
//        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//
//        String[] titles = new String[] { "序号", "出让人计划编号", "出让人计划订单号", "清算后计划编号", "出让人", "债转编号", "原项目编号", "原项目收益率", "还款方式", "债权本金","债权价值", "预计实际收益率", "已转让本金", "垫付利息", /*"清算手续费率", "实际服务费",*/"在途资金", "出让人实际到账金额", "实际清算时间", "转让状态", "还款状态","项目总期数 ","清算时所在期数","当期应还款时间" };
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//
//        // 生成一个表格
//        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//        if (resultList != null && resultList.size() > 0) {
//
//            int sheetCount = 1;
//            int rowNum = 0;
//
//            for (int i = 0; i < resultList.size(); i++) {
//                rowNum++;
//                if (i != 0 && i % 60000 == 0) {
//                    sheetCount++;
//                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
//                    rowNum = 1;
//                }
//
//                // 新建一行
//                Row row = sheet.createRow(rowNum);
//                // 循环数据
//                for (int celLength = 0; celLength < titles.length; celLength++) {
//                    HjhDebtCreditVo debtCredit = resultList.get(i);
//
//                    // 创建相应的单元格
//                    Cell cell = row.createCell(celLength);
//
//                    // 序号
//                    if (celLength == 0) {
//                        cell.setCellValue(i + 1);
//                    }
//                    // 出让人计划编号
//                    else if (celLength == 1) {
//                        cell.setCellValue(debtCredit.getPlanNid());
//                    }
//                    // 出让人计划订单号
//                    else if (celLength == 2) {
//                        cell.setCellValue(debtCredit.getPlanOrderId());
//                    }
//                    // 清算后计划编号
//                    else if (celLength == 3) {
//                        cell.setCellValue(debtCredit.getPlanNidNew());
//                    }
//                    // 出让人
//                    else if (celLength == 4) {
//                        cell.setCellValue(debtCredit.getUserName());
//                    }
//                    // 债转编号
//                    else if (celLength == 5) {
//                        cell.setCellValue(debtCredit.getCreditNid());
//                    }
//                    // 原项目编号
//                    else if (celLength == 6) {
//                        cell.setCellValue(debtCredit.getBorrowNid());
//                    }
//                    // 原项目收益率
//                    else if (celLength == 7) {
//                        cell.setCellValue(debtCredit.getBorrowApr()+"%");
//                    }
//                    // 还款方式
//                    else if (celLength == 8) {
//                        cell.setCellValue(debtCredit.getRepayStyleName());
//                    }
//                    // 债权本金
//                    else if (celLength == 9) {
//                        cell.setCellValue(debtCredit.getCreditCapital());
//                    }
//                    // 债权价值
//                    else if (celLength == 10) {
//                        cell.setCellValue(debtCredit.getLiquidationFairValue());
//                    }
//                    // 预计实际收益率
//                    else if (celLength == 11) {
//                        cell.setCellValue(debtCredit.getActualApr()+"%");
//                    }
//                    // 已转让本金
//                    else if (celLength == 12) {
//                        cell.setCellValue(debtCredit.getAssignCapital());
//                    }
//                    // 垫付利息
//                    else if (celLength == 13) {
//                        cell.setCellValue(debtCredit.getAssignAdvanceInterest());
//                    }
//                    // 在途资金
//                    else if (celLength == 14) {
//                        cell.setCellValue(debtCredit.getRemainCredit());
//                    }
//                    // 出让人实际到账金额
//                    else if (celLength == 15) {
//                        cell.setCellValue(debtCredit.getAccountReceive());
//                    }
//                    // 实际清算时间
//                    else if (celLength == 16) {
//                        cell.setCellValue(debtCredit.getLiquidatesTime());
//                    }
//                    // 转让状态
//                    else if (celLength == 17) {
//                        cell.setCellValue(debtCredit.getCreditStatusName());
//                    }
//                    // 还款状态
//                    else if (celLength == 18) {
//                        cell.setCellValue(debtCredit.getRepayStatusName());
//                    }
//                    // 项目总期数
//                    else if (celLength == 19) {
//                        cell.setCellValue(debtCredit.getBorrowPeriod());
//                    }
//                    // 清算时所在期数
//                    else if (celLength == 20) {
//                        cell.setCellValue(debtCredit.getLiquidatesPeriod());
//                    }
//                    // 当期应还款时间
//                    else if (celLength == 21) {
//                        cell.setCellValue(debtCredit.getRepayNextTime());
//                    }
//
//                }
//            }
//        }
//        // 导出
//        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//        return this.success();
//    }

    @ApiOperation(value = "运营记录-债转标的", notes = "初始化")
    @PostMapping(value = "/queryoptAction")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject queryoptAction() {
        JSONObject jsonObject;
        HjhDebtCreditListRequest creditListRequest = new HjhDebtCreditListRequest();
        creditListRequest.setLiquidatesTimeStart(GetDate.date2Str(new Date(), new SimpleDateFormat("yyyy-MM-dd")));
        jsonObject = queryHjhDebtCreditDetail(creditListRequest);
        return jsonObject;
    }

    @ApiOperation(value = "运营记录-债转标的检索", notes = "检索列表")
    @PostMapping(value = "/queryoptActionSearch")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject queryoptActionSearch(@RequestBody HjhDebtCreditListRequest request) {
        JSONObject jsonObject;
        jsonObject = queryHjhDebtCreditDetail(request);
        return jsonObject;
    }


    @ApiOperation(value = "汇计划-转让记录页面导出列表", notes = "页面列表导出")
    @PostMapping(value = "/exportHjhDebtCreditDetail")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportHjhDebtCreditDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhDebtCreditListRequest requestBean) throws UnsupportedEncodingException {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "汇计划转让记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        requestBean.setPageSize(defaultRowMaxCount);
        requestBean.setCurrPage(1);
        HjhDebtCreditReponse hjhDebtCreditReponse = hjhDebtCreditService.queryHjhDebtCreditList(requestBean);

        List<HjhDebtCreditVo> resultList = hjhDebtCreditReponse.getResultList();
        hjhDebtCreditService.queryHjhDebtCreditListStatusName(resultList);


        Integer totalCount = hjhDebtCreditReponse.getRecordTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        int minId = 0;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
        }
        for (int i = 1; i < sheetCount; i++) {

            requestBean.setPageSize(defaultRowMaxCount);
            requestBean.setCurrPage(i + 1);
            HjhDebtCreditReponse hjhDebtCreditReponse2 = hjhDebtCreditService.queryHjhDebtCreditList(requestBean);
            if (hjhDebtCreditReponse2 != null && hjhDebtCreditReponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  hjhDebtCreditReponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("planNid", "出让人智投编号");
        map.put("planOrderId", "出让人智投订单号");
        map.put("planNidNew", "清算后智投编号");
        map.put("userName", "出让人");
        map.put("userName", "债转编号");
        map.put("borrowNid", "原项目编号");
        map.put("borrowApr", "原项目收益率");
        map.put("repayStyleName", "还款方式");
        map.put("creditCapital", "债权本金");
        map.put("liquidationFairValue", "债权价值");
        map.put("actualApr", "预计实际收益率");
        map.put("assignCapital", "已转让本金");
        map.put("assignAdvanceInterest", "垫付利息");
        map.put("remainCredit", "在途资金");
        map.put("accountReceive", "出让人实际到账金额");
        map.put("liquidatesTime", "实际清算时间");
        map.put("creditStatusName", "转让状态");
        map.put("repayStatusName", "还款状态");
        map.put("borrowPeriod", "项目总期数");
        map.put("liquidatesPeriod", "清算时所在期数");
        map.put("repayNextTime", "当期应还款时间");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter borrowAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                return StringUtils.isBlank(value) ?"":value+"%";
            }
        };

        IValueFormatter actualAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                return StringUtils.isBlank(value) ?"":value+"%";
            }
        };

        mapAdapter.put("borrowApr", borrowAprAdapter);
        mapAdapter.put("actualApr", actualAprAdapter);
        return mapAdapter;
    }

}
