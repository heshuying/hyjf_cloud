package com.hyjf.admin.controller.productcenter.plancenter.reinvestdetail;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.HjhReInvestDetailRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HjhReInvestDetailService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投原始标的
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划-复投原始标的")
@RestController
@RequestMapping(value = "/hyjf-admin/hjhReInvestDetail")
public class HjhReInvestDetailController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(HjhReInvestDetailController.class);
    @Autowired
    private HjhReInvestDetailService hjhReInvestDetailService;

    // 权限名称 (子页面.未添加为菜单,无需设置权限)
    public static final String PERMISSIONS = "plancapitallist";

    @ApiOperation(value = "复投原始标的检索下拉框", notes = "复投原始标的检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        //初始出借方式List
        List<Object> investmentMethodList = new ArrayList<>();

        //初始出借方式Map
        Map<String, Object> investmentMethodListMap1 = new HashedMap();
        Map<String, Object> investmentMethodListMap2 = new HashedMap();
        Map<String, Object> investmentMethodListMap3 = new HashedMap();

        // 设置key value
        investmentMethodListMap1.put("key", 0);
        investmentMethodListMap1.put("value", "手动投标");
        investmentMethodListMap2.put("key", 1);
        investmentMethodListMap2.put("value", "预约投标");
        investmentMethodListMap3.put("key", 2);
        investmentMethodListMap3.put("value", "自动投标");

        // 将Map加入List
        investmentMethodList.add(investmentMethodListMap1);
        investmentMethodList.add(investmentMethodListMap2);
        investmentMethodList.add(investmentMethodListMap3);

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
        allMap.put("investmentMethodList", investmentMethodList);
        allMap.put("repaymentList", repaymentList);

        jsonObject.put("status", BaseResult.SUCCESS);
        jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        jsonObject.put("data", allMap);

        return jsonObject;
    }

    /**
     * 获取复投原始标的列表,初始化只需当前日期和planNid
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "复投原始标的列表", notes = "复投原始标的列表")
    @PostMapping(value = "/hjhReInvest")
    public AdminResult<ListResult<HjhReInvestDetailVO>> hjhReInvestList(@RequestBody HjhReInvestDetailRequestBean requestBean){

        HjhReInvestDetailRequest reInvestDetailRequest = new HjhReInvestDetailRequest();
        BeanUtils.copyProperties(requestBean, reInvestDetailRequest);

        if (StringUtils.isEmpty(requestBean.getDate())){
            return new AdminResult<>(FAIL, "Date不能为空!");
        }

        if (StringUtils.isEmpty(requestBean.getPlanNid())){
            return new AdminResult<>(FAIL, "智投编号不能为空!");
        }

        // 初始化范湖List
        List<HjhReInvestDetailVO> returnList = new ArrayList<>();

        HjhReInvestDetailResponse investDetailResponse = this.hjhReInvestDetailService.getHjhReInvestDetailList(reInvestDetailRequest);

        if (investDetailResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

//        if (!Response.isSuccess(reInvestDetailRequest)){
//            return new AdminResult<>(FAIL, investDetailResponse.getMessage());
//        }

        if (CollectionUtils.isNotEmpty(investDetailResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(investDetailResponse.getResultList(), HjhReInvestDetailVO.class);
            return new AdminResult<ListResult<HjhReInvestDetailVO>>(ListResult.build(returnList, investDetailResponse.getCount()));
        }else {
            return new AdminResult<ListResult<HjhReInvestDetailVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "复投原始标的列表导出", notes = "复投详情列表导出")
    @PostMapping(value = "/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhReInvestDetailRequestBean requestBean) throws Exception {

        if (StringUtils.isEmpty(requestBean.getDate())){
            logger.error("复投原始标的列表导出日期为空无法导出!");
        }

        if (StringUtils.isEmpty(requestBean.getPlanNid())){
            logger.error("复投原始标的列表导出智投编号为空无法导出!");
        }

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "资金明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        HjhReInvestDetailRequest hjhReInvestDetailCustomize = new HjhReInvestDetailRequest();

        hjhReInvestDetailCustomize.setAccedeOrderIdSrch(requestBean.getAccedeOrderIdSrch());
        hjhReInvestDetailCustomize.setBorrowNidSrch(requestBean.getBorrowNidSrch());
        hjhReInvestDetailCustomize.setBorrowStyleSrch(requestBean.getBorrowStyleSrch());
        hjhReInvestDetailCustomize.setInvestTypeSrch(requestBean.getInvestTypeSrch());
        hjhReInvestDetailCustomize.setLockPeriodSrch(requestBean.getLockPeriodSrch());
        hjhReInvestDetailCustomize.setUserNameSrch(requestBean.getUserNameSrch());
        hjhReInvestDetailCustomize.setBorrowStyleSrch(requestBean.getBorrowStyleSrch());
        hjhReInvestDetailCustomize.setInvestTypeSrch(requestBean.getInvestTypeSrch());
        hjhReInvestDetailCustomize.setPlanNid(requestBean.getPlanNid());
        hjhReInvestDetailCustomize.setDate(requestBean.getDate());
        //请求第一页5000条
        hjhReInvestDetailCustomize.setPageSize(defaultRowMaxCount);
        hjhReInvestDetailCustomize.setCurrPage(1);
        // 需要输出的结果列表
        // 取得数据
        List<HjhReInvestDetailVO> resultList = null;

        HjhReInvestDetailResponse resultResponse = this.hjhReInvestDetailService.getHjhReInvestDetailList(hjhReInvestDetailCustomize);
        logger.info(HjhReInvestDetailController.class + ";defaultRowMaxCount:" + defaultRowMaxCount + ";总条数:" + resultResponse.getCount());
        if (resultResponse.getCount() > 0) {
            logger.info(HjhReInvestDetailController.class + ";defaultRowMaxCount:" + defaultRowMaxCount + ";总条数:" + resultResponse.getCount() + ";列表:" + resultResponse.getResultList().get(0).toString());
            resultList = CommonUtils.convertBeanList(resultResponse.getResultList(), HjhReInvestDetailVO.class);
        }


        Integer totalCount = resultResponse.getCount();

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

            hjhReInvestDetailCustomize.setPageSize(defaultRowMaxCount);
            hjhReInvestDetailCustomize.setCurrPage(i + 1);
            HjhReInvestDetailResponse resultResponse2 = this.hjhReInvestDetailService.getHjhReInvestDetailList(hjhReInvestDetailCustomize);
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
        map.put("accedeOrderId", "智投订单号");
        map.put("planNid", "智投编号");
        map.put("userName", "用户名");
        map.put("inviteUserName", "推荐人");
        map.put("userAttribute", "用户属性");
        map.put("borrowNid", "项目编号");
        map.put("expectApr", "年化利率");
        map.put("borrowPeriodView", "借款期限");
        map.put("accedeAccount", "出借金额（元）");
        map.put("borrowStyle", "还款方式");
        map.put("investType", "出借方式");
        map.put("countInterestTime", "开始计息时间");
        map.put("addTime", "出借时间");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter userAttributeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if ("0".equals(value)) {
                    return "无主单";
                } else if ("1".equals(value)) {
                    return "有主单";
                } else if ("2".equals(value)) {
                    return "线下员工";
                } else if ("3".equals(value)) {
                    return "线上员工";
                }else {
                    return value;
                }
            }
        };

        IValueFormatter investTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if ("0".equals(value)) {
                    return "手动投标";
                } else if ("1".equals(value)) {
                    return "预约投标";
                } else if ("2".equals(value)) {
                    return "自动投标";
                }else {
                    return value;
                }
            }
        };

        mapAdapter.put("userAttribute", userAttributeAdapter);
        mapAdapter.put("investType", investTypeAdapter);
        return mapAdapter;
    }

}
