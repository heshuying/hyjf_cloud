/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.pushMoney;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.PushMoneyRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.PushMoneyManageService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

import static com.hyjf.admin.common.util.ShiroConstants.*;

/**
 * @author zdj
 * @version PushMoneyManageController, v0.1 2018/7/3 15:16
 * 资金中心->直投提成管理
 */

@Api(value = "资金中心-直投提成管理",tags = "资金中心-直投提成管理")
@RestController
@RequestMapping("/hyjf-admin/finance/pushMoney")
public class PushMoneyManageController extends BaseController {
    @Autowired
    private PushMoneyManageService pushMoneyManageService;

    /** 直投提成列表权限 */
    public static final String PERMISSIONSLIST = "pushMoneyList";

    /** 直投提成管理权限 */
    public static final String PERMISSIONS = "pushMoneyManage";


    @ApiOperation(value = "直投提成管理", notes = "直投提成管理列表查询")
    @PostMapping(value = "/pushmoneylist")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult getPushMoneyList( @RequestBody @Valid PushMoneyRequest request) {
        Map<String,Object> result = new HashMap<>();
        PushMoneyResponse response = pushMoneyManageService.findPushMoneyList(request);
        Integer count = response.getCount();
        count = (count == null)?0:count;
        result.put("count",count);
        List<PushMoneyVO> pushMoneyVOList = response.getResultList();
        result.put("list",pushMoneyVOList);
        return new AdminResult<>(result);
    }
    //计算提成
    @ApiOperation(value = "计算提成", notes = "计算提成")
    @PostMapping(value = "/calculateushmoney")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CALCULATE)
    public JSONObject calculatePushMoney(@RequestBody @Valid PushMoneyRequestBean requestBean){
        // 初始化原子层请求实体
        PushMoneyRequest form = new PushMoneyRequest();
        // 将画面请求request赋值给原子层 request
        BeanUtils.copyProperties(requestBean, form);
        JSONObject jsonObject = new JSONObject();
        // 提成ID
        String borrowNid = form.getBorrowNid();
        // 取得借款API表
        BorrowApicronVO apicron = this.pushMoneyManageService.getBorrowApicronBorrowNid(borrowNid);
        String status= SUCCESS;
        if (apicron == null) {
            jsonObject.put("record","该项目不存在!");
            status = FAIL;
            jsonObject.put("status",status);
            logger.error("未在borrow_api表中，borrowNid:" + borrowNid);
            return jsonObject;
        }
        if (GetterUtil.getInteger(apicron.getWebStatus()) == 1) {
            jsonObject.put("record","该标的提成已经计算完成!");
            status = SUCCESS;
            jsonObject.put("status",status);
            logger.error("提成计算已完成不要重复计算，borrowNid：" + borrowNid);
            return jsonObject;
        }
        int cnt = -1;
        try {
            // 计算提成
            cnt = this.pushMoneyManageService.insertTenderCommissionRecord(apicron.getId(), form);
        } catch (Exception e) {
            //e.printStackTrace();
           jsonObject.put("record","提成计算失败,请重新操作!");
            status = Response.FAIL;
            jsonObject.put("status",status);
            logger.error("计算提成异常", e);
            return jsonObject;
        }

        if (cnt >= 0) {
            jsonObject.put("record","提成计算成功！");
            logger.info("提成计算成功，borrowNid：" + borrowNid);
            status = SUCCESS;
        } else {
            jsonObject.put("record","提成计算失败,请重新操作!");
            logger.info("提成计算失败");
            status = FAIL;
        }
        jsonObject.put("status",status);
        return jsonObject;
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param requestBean
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "直投提成管理记录导出", notes = "直投提成管理记录导出")
    @PostMapping(value = "/exportpushmoney")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportPushMoney(HttpServletRequest request, HttpServletResponse response,@RequestBody PushMoneyRequest requestBean) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

        // 表格sheet名称
        String sheetName = "推广提成列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        requestBean.setCurrPage(1);
        requestBean.setPageSize(defaultRowMaxCount);
        Integer totalCount = pushMoneyManageService.findPushMoneyList(requestBean).getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            requestBean.setPageSize(defaultRowMaxCount);
            requestBean.setCurrPage(i);
            List<PushMoneyVO> pushMoneyVOList = pushMoneyManageService.findPushMoneyList(requestBean).getResultList();
            if (pushMoneyVOList != null && pushMoneyVOList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  pushMoneyVOList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {

        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid", "项目编号");
        map.put("borrowName", "项目标题");
        map.put("borrowPeriodByStyle", "融资期限");
        map.put("account", "融资金额");
        map.put("commission", "提成总额");
        map.put("recoverLastTime", "放款时间");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        return Maps.newHashMap();
    }




    /**
     * 直投提成列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "直投提成列表", notes = "直投提成列表")
    @PostMapping(value = "/pushMoneyList")
    @AuthorityAnnotation(key = PERMISSIONSLIST,value = PERMISSION_VIEW)
    public AdminResult pushMoneyList(@RequestBody PushMoneyRequest request){
        logger.info("enter pushMoneyList controller for admin");
        if(null != request.getCombotreeListSrch() && request.getCombotreeListSrch().length>0){
            // 查询部门处理
            String [] strDepts = pushMoneyManageService.getDeptId(request.getCombotreeListSrch());
            request.setCombotreeListSrch(strDepts);
        }
        //选择1直投类，而非2计划类数据
        request.setTenderType(1);
        Map<String,Object> result = new HashMap<>();
        Integer count = pushMoneyManageService.getPushMoneyListCount(request);
        count = (count == null)?0:count;
        result.put("count",count);
        List<PushMoneyVO> pushMoneyVOList = pushMoneyManageService.searchPushMoneyList(request);
        result.put("pushMoneyVOList",pushMoneyVOList);
        Map<String,Object> totle = pushMoneyManageService.queryPushMoneyTotle(request);
        result.put("pushMoneyTotle",totle);
        return new AdminResult<>(result);
    }

    /**
     * 取得部门信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "直投提成列表取得部门信息", notes = "直投提成列表取得部门信息")
    @PostMapping(value = "/getcrmdepartmentlist")
    public JSONObject getCrmDepartmentListAction() {
        JSONObject jsonObject = new JSONObject();
        // 部门
        String[] list = new String[] {};
        JSONArray ja = pushMoneyManageService.getCrmDepartmentList(list);
        if (ja != null) {
            //在部门树中加入 0=部门（其他）,因为前端不能显示id=0,就在后台将0=其他转换为-10086=其他
            JSONObject jo = new JSONObject();
            jo.put("value", "-10086");
            jo.put("title", "其他");
            JSONArray array = new JSONArray();
            jo.put("key", UUID.randomUUID());
            jo.put("children", array);
            ja.add(jo);

            JSONObject ret= new JSONObject();
            ret.put("data", ja);
            ret.put("status", SUCCESS);
            ret.put("statusDesc", "成功");
            return ret;
        } else {
            jsonObject.put("error", "未查询到该记录！");
            jsonObject.put("status", FAIL);
        }
        return jsonObject;
    }


    /**
     * 直投提成列表导出
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "直投提成列表导出",notes = "直投提成列表导出")
    @PostMapping(value = "/exportPushMoneyDetailExcelAction")
    @AuthorityAnnotation(key = PERMISSIONSLIST,value = PERMISSION_EXPORT)
    public void exportPushMoneyDetailExcelAction(HttpServletRequest request, HttpServletResponse response,@RequestBody PushMoneyRequest requestBean) throws Exception {

        // 是否具有组织机构查看权限
        String isOrganizationView = requestBean.getIsOrganizationView();

        if(null != requestBean.getCombotreeListSrch() && requestBean.getCombotreeListSrch().length>0){
            // 查询部门处理
            String [] strDepts = pushMoneyManageService.getDeptId(requestBean.getCombotreeListSrch());
            requestBean.setCombotreeListSrch(strDepts);
        }
        //选择1直投类，而非2计划类数据
        requestBean.setTenderType(1);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "推广提成发放列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

//        requestBean.setCurrPage(1);
//        requestBean.setPageSize(defaultRowMaxCount);
        Integer totalCount = pushMoneyManageService.getPushMoneyListCount(requestBean);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildDetailsMap(isOrganizationView);
        Map<String, IValueFormatter> mapValueAdapter = buildDetailsValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            requestBean.setPageSize(defaultRowMaxCount);
            requestBean.setCurrPage(i);
            List<PushMoneyVO> pushMoneyVOList = pushMoneyManageService.searchPushMoneyList(requestBean);
            if (pushMoneyVOList != null && pushMoneyVOList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  pushMoneyVOList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildDetailsMap(String isOrganizationView) {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid", "项目编号");
        map.put("rzqx", "融资期限");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("regionName", "分公司");
            map.put("branchName", "分部");
            map.put("departmentName", "团队");
        }
        map.put("username", "提成人");
        map.put("accountId", "电子账号");
        map.put("attribute", "用户属性");
        map.put("usernameTender", "出借人");
        map.put("accountTender", "出借金额");
        map.put("commission", "提成金额");
        map.put("statusName", "状态");
        map.put("tenderTimeView", "出借时间");
        map.put("sendTimeView", "发放时间");
        return map;
    }
    private Map<String, IValueFormatter> buildDetailsValueAdapter() {
        Map<String, IValueFormatter> map = Maps.newHashMap();
        IValueFormatter departmentNameAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String departmentName = (String) object;
                return HtmlUtil.unescape(departmentName);
            }
        };
        IValueFormatter attributeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String attribute = (String) object;
                if ("0".equals(attribute)) {
                    attribute = "无主单";
                } else if ("1".equals(attribute)) {
                    attribute = "有主单";
                } else if ("2".equals(attribute)) {
                    attribute = "线下员工";
                } else if ("3".equals(attribute)) {
                    attribute = "线上员工";
                }
                return attribute;
            }
        };
        IValueFormatter accountTenderAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal accountTender = (BigDecimal) object;
                return accountTender.toString();
            }
        };
        map.put("departmentName",departmentNameAdapter);
        map.put("attribute",attributeAdapter);
        map.put("accountTender",accountTenderAdapter);
        return map;
    }


    @ApiOperation(value = "发提成",notes = "发提成")
    @PostMapping(value = "/confirmPushMoneyAction")
    @AuthorityAnnotation(key = PERMISSIONSLIST,value = PERMISSION_CONFIRM)
    public AdminResult confirmPushMoneyAction(HttpServletRequest request, @RequestBody PushMoneyRequest pushMoneyRequest){
        Integer userId = Integer.valueOf(getUser(request).getId());
        Integer id = pushMoneyRequest.getId();
        JSONObject jsonObject = pushMoneyManageService.pushMoney(request,id,userId);
        String status = jsonObject.getString("status");
        String statusDesc = jsonObject.getString("statusDesc");
        if("success".equals(status)){
            return new AdminResult(SUCCESS,statusDesc);
        }
        return new AdminResult(FAIL,statusDesc);
    }

}
