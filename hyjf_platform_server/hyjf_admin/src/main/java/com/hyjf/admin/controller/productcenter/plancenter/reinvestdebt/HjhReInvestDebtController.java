package com.hyjf.admin.controller.productcenter.plancenter.reinvestdebt;

import cn.emay.slf4j.Logger;
import cn.emay.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HjhReInvestDebtService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDebtVO;
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
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投承接债权
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划-复投承接债权")
@RestController
@RequestMapping(value = "/hyjf-admin/hjhReInvestDebt")
public class HjhReInvestDebtController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(HjhReInvestDebtController.class);
    @Autowired
    private HjhReInvestDebtService hjhReInvestDebtService;

    // 权限名称 (子页面.未添加为菜单,无需设置权限)
    public static final String PERMISSIONS = "plancapitallist";

    /**
     * 产品中心  -  汇计划 --  复投承接债权 -- 检索下拉框
     * @return
     */
    @ApiOperation(value = "复投承接债权检索下拉框", notes = "复投承接债权检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        // 初始化承接方式List
        List<Object> undertakingMethodList = new ArrayList<>();

        // 初始化出借方式Map
        Map<String, Object> undertakingMethodMap = new HashedMap();
        Map<String, Object> undertakingMethodMap1 = new HashedMap();

        undertakingMethodMap.put("key", 0);
        undertakingMethodMap.put("value", "自动承接");
        undertakingMethodMap1.put("key", 1);
        undertakingMethodMap1.put("value", "手动承接");

        undertakingMethodList.add(undertakingMethodMap);
        undertakingMethodList.add(undertakingMethodMap1);

        // 初始化还款方式List
        List<Object> repaymentList = new ArrayList<>();

        // 初始化还款方式Map
        Map<String, Object> repaymentMap = new HashedMap();
        Map<String, Object> repaymentMap1 = new HashedMap();
        Map<String, Object> repaymentMap2 = new HashedMap();
        Map<String, Object> repaymentMap3 = new HashedMap();
        Map<String, Object> repaymentMap4 = new HashedMap();

        repaymentMap.put("key", "按天计息，到期还本还息");
        repaymentMap.put("value", "按天计息，到期还本还息");
        repaymentMap1.put("key", "按月计息，到期还本还息");
        repaymentMap1.put("value", "按月计息，到期还本还息");
        repaymentMap2.put("key", "等额本息");
        repaymentMap2.put("value", "等额本息");
        repaymentMap3.put("key", "等额本金");
        repaymentMap3.put("value", "等额本金");
        repaymentMap4.put("key", "先息后本");
        repaymentMap4.put("value", "先息后本");

        repaymentList.add(repaymentMap);
        repaymentList.add(repaymentMap1);
        repaymentList.add(repaymentMap2);
        repaymentList.add(repaymentMap3);
        repaymentList.add(repaymentMap4);

        // Map 集
        Map<String, Object> allMap = new HashedMap();
        allMap.put("undertakingMethodList", undertakingMethodList);
        allMap.put("repaymentList", repaymentList);

        jsonObject.put("status", BaseResult.SUCCESS);
        jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        jsonObject.put("data", allMap);

        return jsonObject;
    }

    /**
     * 资金计划 - 复投债权列表 初始化只需当前日期和planNid
     * @param request
     * @return
     */
    @ApiOperation(value = "复投承接债权检列表", notes = "复投债权列表")
    @PostMapping(value = "/hjhReInvestDebtList")
    public AdminResult<ListResult<HjhReInvestDebtVO>> hjhReInvestDebtList(@RequestBody HjhReInvestDebtRequest request) {

        if (StringUtils.isEmpty(request.getDate())) {
            return new AdminResult<>(FAIL, "Date不能为空!");
        }

        if (StringUtils.isEmpty(request.getPlanNid())) {
            return new AdminResult<>(FAIL, "PlanNid不能为空!");
        }

        // 初始化返回List
        List<HjhReInvestDebtVO> returnList = new ArrayList<>();

        HjhReInvestDebtResponse response = hjhReInvestDebtService.hjhReInvestDebtList(request);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        if (CollectionUtils.isNotEmpty(response.getResultList())) {
            returnList = CommonUtils.convertBeanList(response.getResultList(), HjhReInvestDebtVO.class);
            return new AdminResult<ListResult<HjhReInvestDebtVO>>(ListResult.build2(returnList, response.getCount(), response.getSumHjhReInvestDebtVO()));
        } else {
            return new AdminResult<ListResult<HjhReInvestDebtVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "复投承接债权导出", notes = "复投承接债权列表导出")
    @PostMapping(value = "/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhReInvestDebtRequest requestBean) throws Exception {
        if (StringUtils.isEmpty(requestBean.getDate())){
            logger.error("复投承接债权导出日期为空无法导出!");
        }

        if (StringUtils.isEmpty(requestBean.getPlanNid())){
            logger.error("复投承接债权导出智投编号为空无法导出!");
        }
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "资金计划";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        requestBean.setPageSize(defaultRowMaxCount);
        requestBean.setCurrPage(1);
        // 需要输出的结果列表
        List<HjhReInvestDebtVO> resultList = null;
        HjhReInvestDebtResponse resultResponse = this.hjhReInvestDebtService.hjhReInvestDebtList(requestBean);

        if (resultResponse.getCount() > 0){
            resultList = CommonUtils.convertBeanList(resultResponse.getResultList(), HjhReInvestDebtVO.class);
        }


        Integer totalCount = resultResponse.getCount();

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
            HjhReInvestDebtResponse resultResponse2 = this.hjhReInvestDebtService.hjhReInvestDebtList(requestBean);
            if (resultResponse2 != null && resultResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("AssignPlanOrderId", "智投订单号");
        map.put("AssignPlanNid", "承接智投编号");
        map.put("AssignOrderId", "承接订单号");
        map.put("UserName", "承接人");
        map.put("CreditUserName", "出让人");
        map.put("CreditNid", "债权编号");
        map.put("BorrowNid", "原项目编号");
        map.put("BorrowStyle", "还款方式");
        map.put("AssignCapital", "承接本金");
        map.put("AssignInterestAdvance", "垫付利息");
        map.put("AssignPay", "实际支付金额");
        map.put("AssignType", "承接方式");
        map.put("BorrowPeriod", "项目总期数");
        map.put("AssignPeriod", "承接时所在期数");
        map.put("AssignTime", "承接时间");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }

}
