package com.hyjf.admin.controller.productcenter.plancenter.planrepay;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.HjhRepayRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PlanRepayService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 产品中心 --> 汇计划 --> 订单退出  Warning : 汇计划三期 订单退出改为计划还款
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-计划还款",tags ="产品中心-汇计划-计划还款")
@RestController
@RequestMapping(value = "/hyjf-admin/planrepay")
public class PlanRepayController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PlanRepayService planRepayService;

    // 权限名称
    public static final String PERMISSIONS = "planrepay";

    /**
     * 资金中心 - 订单退出 检索下拉框
     * @return
     */
    @ApiOperation(value = "智投退出检索下拉框", notes = "智投退出检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox() {
        JSONObject jsonObject = new JSONObject();

        // 类型List
        List<Object> orderStatusList = new ArrayList<>();

        // 初始Map
        Map<String, Object> orderStatusMap = new HashedMap();
        Map<String, Object> orderStatusMap1 = new HashedMap();
        Map<String, Object> orderStatusMap2 = new HashedMap();
        //设置对应键值对
        orderStatusMap.put("key", 3);
        orderStatusMap.put("value", "锁定中");
        orderStatusMap1.put("key", 5);
        orderStatusMap1.put("value", "退出中");
        orderStatusMap2.put("key", 7);
        orderStatusMap2.put("value", "已退出");

        orderStatusList.add(orderStatusMap);
        orderStatusList.add(orderStatusMap1);
        orderStatusList.add(orderStatusMap2);

        // 初始组装所有数据的Map
        Map<String, Object> allMap = new HashedMap();

        allMap.put("orderStatusList", orderStatusList);

        jsonObject.put("status", BaseResult.SUCCESS);
        jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        jsonObject.put("data", allMap);

        return jsonObject;
    }

    /**
     * 订单退出初始化列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "智投退出", notes = "智投退出初始化列表")
    @PostMapping(value = "/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhRepayVO>> init(@RequestBody HjhRepayRequestBean repayRequestBean){

        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

//        初始化时应默认清算时间不能为空
//        if (StringUtils.isEmpty(repayRequest.getRepayTimeStart()) && StringUtils.isEmpty(repayRequest.getRepayTimeEnd())){
//            return new AdminResult<>(FAIL, "请输入清算开始和结束时间!");
//        }

//        实际退出时间不能为空
//        if (StringUtils.isNotEmpty(repayRequest.getActulRepayTimeStart()) && StringUtils.isNotEmpty(repayRequest.getActulRepayTimeEnd())){
//            return new AdminResult<>(FAIL, "请输入实际退出开始和结束时间!");
//        }

        //防止前端将起始时间传错误.
//        try {
//            Date timeStart = dateFormat.parse(repayRequest.getRepayTimeStart());
//            Date timeEnd = dateFormat.parse(repayRequest.getRepayTimeEnd());
//
//            if (timeStart.getTime() > timeEnd.getTime()){
//                return new AdminResult<>(FAIL, "清算结束时间应大于等于开始时间!");
//            }

//            Date actuTimeStart = dateFormat.parse(repayRequest.getActulRepayTimeStart());
//            Date actuTimeEnd = dateFormat.parse(repayRequest.getActulRepayTimeEnd());
//            if (actuTimeStart.getTime() > actuTimeEnd.getTime()){
//                return new AdminResult<>(FAIL, "实际退出结束时间应大于等于开始时间!");
//            }
//        }catch (ParseException e){
//            return new AdminResult<>(FAIL, e.getMessage());
//        }

        //初始化返回List
        List<HjhRepayVO> returnList = new ArrayList<>();
        // 查询列表
        HjhRepayResponse hjhRepayResponse = this.planRepayService.selectHjhRepayList(repayRequest);
        if (hjhRepayResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(hjhRepayResponse)){
            return new AdminResult<>(FAIL, hjhRepayResponse.getMessage());
        }

        if (CollectionUtils.isNotEmpty(hjhRepayResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(hjhRepayResponse.getResultList(), HjhRepayVO.class);
            return new AdminResult<ListResult<HjhRepayVO>>(ListResult.build2(returnList, hjhRepayResponse.getCount(), hjhRepayResponse.getSumHjhRepayVO()));
        }else {
            return new AdminResult<ListResult<HjhRepayVO>>(ListResult.build(returnList, 0));
        }
    }


    /**
     * 订单退出初始化列表
     * @param repayRequestBean
     * @return
     */
    @ApiOperation(value = "智投退出", notes = "智投退出列表导出")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhRepayRequestBean repayRequestBean) throws Exception {
        HjhRepayRequest repayRequest = new HjhRepayRequest();
        BeanUtils.copyProperties(repayRequestBean, repayRequest);

        // 具有组织机构查看权限
        String isOrganizationView = repayRequestBean.getIsOrganizationView();

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "智投退出";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        repayRequest.setPageSize(defaultRowMaxCount);
        repayRequest.setCurrPage(1);
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(repayRequestBean.getAccedeOrderIdSrch())){
            repayRequest.setAccedeOrderIdSrch(repayRequestBean.getAccedeOrderIdSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getPlanNidSrch())){
            repayRequest.setPlanNidSrch(repayRequestBean.getPlanNidSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getUserNameSrch())){
            repayRequest.setUserNameSrch(repayRequestBean.getUserNameSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getDebtLockPeriodSrch())){
            repayRequest.setDebtLockPeriodSrch(repayRequestBean.getDebtLockPeriodSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayStatusSrch())){
            repayRequest.setRepayStatusSrch(repayRequestBean.getRepayStatusSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getOrderStatusSrch())){
            repayRequest.setOrderStatusSrch(repayRequestBean.getOrderStatusSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getBorrowStyleSrch())){
            repayRequest.setBorrowStyleSrch(repayRequestBean.getBorrowStyleSrch());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayTimeStart())){
            repayRequest.setRepayTimeStart(repayRequestBean.getRepayTimeStart());
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getRepayTimeEnd())){
            repayRequest.setRepayTimeEnd(repayRequestBean.getRepayTimeEnd());
//            paraMap.put("repayTimeEnd", GetDate.getDayEnd10(form.getRepayTimeEnd()));
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getActulRepayTimeStart())){
            repayRequest.setActulRepayTimeStart(repayRequestBean.getActulRepayTimeStart());
//            paraMap.put("actulRepayTimeStart", GetDate.getDayStart10(form.getActulRepayTimeStart()));
        }
        if(StringUtils.isNotEmpty(repayRequestBean.getActulRepayTimeEnd())){
            repayRequest.setActulRepayTimeEnd(repayRequestBean.getActulRepayTimeEnd());
//            paraMap.put("actulRepayTimeEnd", GetDate.getDayEnd10(form.getActulRepayTimeEnd()));
        }
        // 汇计划三期新增 推荐人查询
        if(StringUtils.isNotEmpty(repayRequestBean.getRefereeNameSrch())){
            repayRequest.setRefereeNameSrch(repayRequestBean.getRefereeNameSrch());
        }

//        List<HjhPlanRepayCustomize> resultList = planRepayListService.exportPlanRepayList(paraMap);
//        List<HjhPlanRepayCustomize> resultList = planRepayListService.exportPlanRepayList(paraMap);
//        List<HjhRepayResponse> resultList = this.planRepayService.selectHjhRepayList(paraMap);

        //初始化返回List
        List<HjhRepayVO> returnList = null;
        // 查询列表
        HjhRepayResponse hjhRepayList = this.planRepayService.selectHjhRepayList(repayRequest);

        if (hjhRepayList.getCount() > 0){
            returnList = CommonUtils.convertBeanList(hjhRepayList.getResultList(), HjhRepayVO.class);
        }


        Integer totalCount = hjhRepayList.getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        int minId = 0;
        Map<String, String> attr=CacheUtil.getParamNameMap("USER_PROPERTY");
        Map<String, String> beanPropertyColumnMap = buildMap(isOrganizationView);
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter(attr);
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, returnList);
        }
        for (int i = 1; i < sheetCount; i++) {
            repayRequest.setPageSize(defaultRowMaxCount);
            repayRequest.setCurrPage(i + 1);
            HjhRepayResponse hjhRepayList2 = this.planRepayService.selectHjhRepayList(repayRequest);
            if (hjhRepayList2 != null && hjhRepayList2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  hjhRepayList2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap(String isOrganizationView) {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("accedeOrderId", "智投订单号");
        map.put("planNid", "智投编号");
        map.put("planName", "智投名称");
        map.put("lockPeriod", "服务回报期限");
        map.put("expectApr", "参考年回报率");
        map.put("userName", "用户名（出借人）");
        map.put("recommendAttr", "出借人用户属性（当前）");
        map.put("inviteUserName", "推荐人(当前)");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("inviteUserRegionName", "分公司(当前)");
            map.put("inviteUserBranchName", "部门(当前)");
            map.put("inviteUserDepartmentName", "团队(当前)");
        }
        map.put("accedeAccount", "授权服务金额(元)");
        map.put("repayInterest", "参考回报(元)");
        map.put("actualRevenue", "实际收益(元)");
        map.put("actualPayTotal", "已退出金额(元)");
        map.put("borrowStyle", "还款方式");
        map.put("orderStatus", "订单状态");
        map.put("repayActualTime", "实际退出时间");
        map.put("repayShouldTime", "预计开始退出时间");
        map.put("lqdServiceFee", "清算服务费");
        map.put("lqdServiceApr", "清算服务费率");
        map.put("investServiceApr", "出借服务费率");
        map.put("lqdProgress", "清算进度");
        map.put("lastQuitTime", "最晚退出时间");
        map.put("joinTime", "授权服务时间");
        map.put("orderLockTime", "开始计息时间");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter(Map<String, String> attr) {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter lockPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                return value+"个月";
            }
        };

        IValueFormatter expectAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                return StringUtils.isBlank(value) ?"":value+"%";
            }
        };

        IValueFormatter borrowStyleAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if(StringUtils.isBlank(value)){
                    return "";
                }if(value.equals("month")){
                    return "等额本息";
                }else if(value.equals("season")){
                    return "按季还款";
                }else if(value.equals("end")){
                    return "按月计息，到期还本还息";
                }else if(value.equals("endmonth")){
                    return "先息后本";
                }else if(value.equals("endday")){
                    return "按天计息，到期还本还息";
                }else if(value.equals("endmonths")){
                    return "按月付息到期还本";
                }else if(value.equals("principal")){
                    return "等额本金";
                }

                return "";
            }
        };

        IValueFormatter orderStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                if(value == 0){
                    return "自动投标中";
                }else if(value == 2){
                    return "自动投标成功";
                }else if(value == 3){
                    return "锁定中";
                }else if(value == 5){
                    return "退出中";
                }else if(value == 7){
                    return "已退出";
                }else if(value == 99){
                    return "自动出借异常";
                }

                return "";
            }
        };

//        IValueFormatter joinTimeAdapter = new IValueFormatter() {
//            @Override
//            public String format(Object object) {
//                String value = (String) object;
//                if(StringUtils.isBlank(value)){
//                    return "0";
//                }else {
//                    return GetDate.timestamptoStrYYYYMMDDHHMMSS(value);
//                }
//            }
//        };

        IValueFormatter orderLockTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer value = (Integer) object;
                if(value == 0){
                    return "0";
                }else {
                    return GetDate.timestamptoStrYYYYMMDDHHMMSS(value);
                }
            }
        };
        IValueFormatter recommendAttrAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
            	String value = (String) object;
            	if(value!=null) {
            		return attr.get(value);
            	}
                return "";
            }
        };
        mapAdapter.put("lockPeriod", lockPeriodAdapter);
        mapAdapter.put("expectApr", expectAprAdapter);
        mapAdapter.put("borrowStyle", borrowStyleAdapter);
        mapAdapter.put("orderStatus", orderStatusAdapter);
        mapAdapter.put("recommendAttr", recommendAttrAdapter);
        mapAdapter.put("orderLockTime", orderLockTimeAdapter);
        return mapAdapter;
    }

}
