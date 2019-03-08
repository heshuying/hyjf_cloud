package com.hyjf.admin.controller.productcenter.plancenter.daycreditdetail;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.DayCreditDetailRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DayCreditDetailService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划-转让详情",tags ="产品中心-汇计划-资金计划-转让详情")
@RestController
@RequestMapping(value = "/hyjf-admin/dayCreditDetail")
public class DayCreditDetailController extends BaseController {

    @Autowired
    private DayCreditDetailService dayCreditDetailService;

    // 权限名称 (子页面.未添加为菜单,无需设置权限)
    public static final String PERMISSIONS = "hjhdebtcredit";

    /**
     * 产品中心  -  汇计划 --  转让详情 -- 检索下拉框
     * @return
     */
    @ApiOperation(value = "转让详情检索下拉框", notes = "转让详情权检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        // 转让状态 List
        List<Object> transferStatusList = new ArrayList<>();

        Map<String, Object> transferStatusMap = new HashedMap();
        Map<String, Object> transferStatusMap1 = new HashedMap();
        Map<String, Object> transferStatusMap2 = new HashedMap();
        Map<String, Object> transferStatusMap3 = new HashedMap();

        transferStatusMap.put("key", 0);
        transferStatusMap.put("value", "承接中");
        transferStatusMap1.put("key", 1);
        transferStatusMap1.put("value", "部分承接");
        transferStatusMap2.put("key", 2);
        transferStatusMap2.put("value", "完全承接");
        transferStatusMap3.put("key", 3);
        transferStatusMap3.put("value", "承接终止");

        transferStatusList.add(transferStatusMap);
        transferStatusList.add(transferStatusMap1);
        transferStatusList.add(transferStatusMap2);
        transferStatusList.add(transferStatusMap3);

        // 还款方式 List
        List<Object> repaymentList = new ArrayList<>();

        // 还款方式Map
        Map<String, Object> repaymentMap = new HashedMap();
        Map<String, Object> repaymentMap1 = new HashedMap();
        Map<String, Object> repaymentMap2 = new HashedMap();
        Map<String, Object> repaymentMap3 = new HashedMap();

        repaymentMap.put("key", "month");
        repaymentMap.put("value", "等额本息");
        repaymentMap1.put("key", "end");
        repaymentMap1.put("value", "按月计息，到期还本还息");
        repaymentMap2.put("key", "endmonth");
        repaymentMap2.put("value", "先息后本");
        repaymentMap3.put("key", "endday");
        repaymentMap3.put("value", "按天计息，到期还本息");

        repaymentList.add(repaymentMap);
        repaymentList.add(repaymentMap1);
        repaymentList.add(repaymentMap2);
        repaymentList.add(repaymentMap3);

        // Map 集
        Map<String, Object> allMap = new HashedMap();
        allMap.put("transferStatusList", transferStatusList);
        allMap.put("repaymentList", repaymentList);

        jsonObject.put("status", BaseResult.SUCCESS);
        jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        jsonObject.put("data", allMap);

        return jsonObject;
    }

    /**
     * 资金计划 - 汇计划按天转让记录列表
     * @param request
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "汇计划按天转让记录列表")
    @PostMapping(value = "/hjhDayCreditDetailList")
    public AdminResult<ListResult<DayCreditDetailVO>> init(@RequestBody DayCreditDetailRequestBean request){
        logger.info("汇计划按天转让记录列表请求开始....");
        DayCreditDetailRequest copyRequest = new DayCreditDetailRequest();
        BeanUtils.copyProperties(request, copyRequest);

        //默认应传入清算日期和转让人planNid
        if (StringUtils.isEmpty(request.getDate())){
            return new AdminResult<>(FAIL, "清算日期不能为空!");
        }
        if (StringUtils.isEmpty(request.getPlanNid())){
            return new AdminResult<>(FAIL, "转让人PlanNid不能为空!");
        }

        //初始化返回List
        List<DayCreditDetailVO> returnList = new ArrayList<>();

        //类表查询
        DayCreditDetailResponse response = this.dayCreditDetailService.hjhDayCreditDetailList(copyRequest);

        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info("汇计划按天转让记录列表请求结束....");
        if (CollectionUtils.isNotEmpty(response.getResultList())){
            returnList = CommonUtils.convertBeanList(response.getResultList(), DayCreditDetailVO.class);
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build2(returnList, response.getCount(), response.getSumDayCreditDetailVO()));
        }else {
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 资金计划转让记录 - 按日转让导出
     * @param request
     * @param response
     * @param planCreditBean
     * @throws Exception
     */
    @ApiOperation(value = "资金计划 - 转让详情导出", notes = "资金计划转让详情列表导出")
    @PostMapping(value = "/exportExcel")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, DayCreditDetailRequestBean planCreditBean) throws Exception {

        DayCreditDetailRequest copyRequest = new DayCreditDetailRequest();
        BeanUtils.copyProperties(planCreditBean, copyRequest);

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

        // 表格sheet名称
        String sheetName = "智投服务按日转让记录";

        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";

        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        copyRequest.setPageSize(defaultRowMaxCount);
        copyRequest.setCurrPage(1);

        //初始化返回List
        List<DayCreditDetailVO> resultList = new ArrayList<>();
        DayCreditDetailResponse responseList = this.dayCreditDetailService.hjhDayCreditDetailList(copyRequest);
        if (CollectionUtils.isNotEmpty(responseList.getResultList())){
            resultList = CommonUtils.convertBeanList(responseList.getResultList(), DayCreditDetailVO.class);
        }

        Integer totalCount = responseList.getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
        }
        for (int i = 1; i < sheetCount; i++) {

            copyRequest.setPageSize(defaultRowMaxCount);
            copyRequest.setCurrPage(i + 1);
            DayCreditDetailResponse responseList2 = this.dayCreditDetailService.hjhDayCreditDetailList(copyRequest);
            if (responseList2 != null && responseList2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  responseList2.getResultList());
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
        map.put("creditNid", "债转编号");
        map.put("borrowNid", "原项目编号");
        map.put("repayStyleName", "还款方式");
        map.put("creditCapital", "债权本金（元）");
        map.put("liquidationFairValue", "债权价值（元）");
        map.put("assignCapital", "已转让本金（元）");
        map.put("assignAdvanceInterest", "垫付利息（元）");
        map.put("creditStatusName", "转让状态");
        map.put("liquidatesPeriodView", "项目期数");
        map.put("liquidatesTime", "实际清算时间");
        map.put("endTime", "债转结束时间");

        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }
}
