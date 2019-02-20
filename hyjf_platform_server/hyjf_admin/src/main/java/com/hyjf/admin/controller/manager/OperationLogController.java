package com.hyjf.admin.controller.manager;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.OperationLogRequestBean;
import com.hyjf.admin.beans.response.OperationLogResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.OperationLogService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Api(tags ="配置中心-操作日志配置")
@RestController
@RequestMapping("/hyjf-admin/config/operationlog")
public class OperationLogController  extends BaseController {



    //权限名称
    private static final String PERMISSIONS = "operationlog";
    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "查询配置中心操作日志配置", notes = "查询配置中心操作日志配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult operationLogConfigInit(@RequestBody OperationLogRequestBean operationLogRequestBean) {
        AdminOperationLogRequest adminRequest= new AdminOperationLogRequest();
        //可以直接使用
        BeanUtils.copyProperties(operationLogRequestBean, adminRequest);
        // 封装查询条件
        Map<String, Object> conditionMap = setCondition(adminRequest);
        AdminOperationLogResponse response =operationLogService.selectOperationLogList(conditionMap,-1,-1);
        OperationLogResponseBean responseBean = new OperationLogResponseBean();
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        responseBean.setResultList(response.getResultList());
        responseBean.setRecordTotal(response.getRecordTotal());
        // 资产来源  inst_code机构编号 inst_name机构名称
        List<HjhInstConfigVO> hjhInstConfigs=this.operationLogService.getHjhInstConfig();
        responseBean.setHjhInstConfigList(hjhInstConfigs);
        //产品类型   asset_type  asset_type_name资产类型名称
        List<HjhAssetTypeVO> hjhAssetTypes = this.operationLogService.getHjhAssetType();
//        //前端直接显示assetType会有错误，做个参数拼接
//        for(HjhAssetTypeVO vo : hjhAssetTypes){
//            vo.setAssetType(vo.getInstCode()+"-"+vo.getAssetType());
//        }
        responseBean.setHjhAssetTypes(hjhAssetTypes);
        //修改类型
        Map<String,String> map =updateTypeList();
        List<DropDownVO> listBorrowTypes = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(map);
        responseBean.setUpdateType(listBorrowTypes);
        return new AdminResult<OperationLogResponseBean>(responseBean) ;
    }

    @ApiOperation(value = "查询配置中心操作日志配置详情", notes = "查询配置中心操作日志配置详情")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult operationLogConfigInfoList(@RequestBody OperationLogRequestBean operationLogRequestBean) {
        AdminOperationLogResponse response = new AdminOperationLogResponse();
        AdminOperationLogRequest adminRequest= new AdminOperationLogRequest();
        //可以直接使用
        BeanUtils.copyProperties(operationLogRequestBean, adminRequest);
        // 资产来源  inst_code机构编号 inst_name机构名称
        List<HjhInstConfigVO> hjhInstConfigs=this.operationLogService.getHjhInstConfig();
        response.setHjhInstConfigList(hjhInstConfigs);
        //产品类型   asset_type  asset_type_name资产类型名称
        List<HjhAssetTypeVO> hjhAssetTypes = this.operationLogService.getHjhAssetType();
        response.setHjhAssetTypes(hjhAssetTypes);
        response.setUpdateTypes( updateTypesList());
        // 封装查询条件
        Map<String, Object> conditionMap = setCondition(adminRequest);
        conditionMap.put("paginatorPage",adminRequest.getPaginatorPage());
        response = operationLogService.selectOperationLogList(conditionMap,-1,-1);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<FeerateModifyLogVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    /**
     * 数据导出
     *
     * @param request
     * @param operationLogRequestBean
     * @return
     */
    @ApiOperation(value = "导出配置中心操作日志配置", notes = "导出配置中心操作日志配置")
    @PostMapping("/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody OperationLogRequestBean operationLogRequestBean) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

        AdminOperationLogRequest form= new AdminOperationLogRequest();
        //可以直接使用
        BeanUtils.copyProperties(operationLogRequestBean, form);
        //请求第一页5000条
        form.setPageSize(defaultRowMaxCount);
        form.setCurrPage(1);
        // 封装查询条件
        Map<String, Object> conditionMap = setCondition(form);
        AdminOperationLogResponse operationLogResponseResponse = this.operationLogService.selectOperationLogList(conditionMap,-1,-1);

        List<FeerateModifyLogVO> resultList =  operationLogResponseResponse.getResultList();
        // 表格sheet名称
        String sheetName = "费率操作日志";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        Integer totalCount = operationLogResponseResponse.getRecordTotal();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,resultList);
        }
        for (int i = 1; i < sheetCount; i++) {
            //请求第一页5000条
            form.setPageSize(defaultRowMaxCount);
            form.setCurrPage(i+1);
            // 封装查询条件
            Map<String, Object> conditionMap2 = setCondition(form);
            AdminOperationLogResponse operationLogResponseResponse2 = this.operationLogService.selectOperationLogList(conditionMap2,-1,-1);
            List<FeerateModifyLogVO> resultList2 =  operationLogResponseResponse2.getResultList();
            if (resultList2 != null && resultList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("instName","资产来源");
        map.put("assetTypeName","产品类型");
        map.put("borrowPeriod","期限");
        map.put("borrowApr","自动发标利率");
        map.put("serviceFee","服务费");
        map.put("manageFee","管理费");
        map.put("revenueDiffRate","收益差率");
        map.put("lateInterestRate","逾期利率");
        map.put("lateFreeDays","逾期免息天数");
        map.put("status","状态");
        map.put("modifyType","修改类型");
        map.put("user","操作人");
        map.put("time","操作时间");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter valueToStringAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
               return object.toString();
            }
        };
        IValueFormatter nameStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (object.equals(0)) {
                    return "启用";
                }
                if (object.equals(1)) {
                    return "禁用";
                }
                return null;
            }
        };
        IValueFormatter accountEsbStatesAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                    if (object.equals(1)) {
                        return "增加";
                    }
                    if (object.equals(2)) {
                        return "修改";
                    }
                    if (object.equals(3)) {
                        return "删除";
                    }
                    return null;
            }
        };

        mapAdapter.put("borrowApr", valueToStringAdapter);
        mapAdapter.put("status", nameStatusAdapter);
        mapAdapter.put("modifyType", accountEsbStatesAdapter);
        return mapAdapter;
    }

    /**
     * 封装查询条件
     *
     * @param form
     * @return
     */
    private Map<String, Object> setCondition(AdminOperationLogRequest form) {
//        String[] strArrray = null;
//        if(StringUtils.isNotEmpty(form.getAssetTypeSrch())){
//            strArrray=form.getAssetTypeSrch().split("-");
//        }
        String instCodeSrch = StringUtils.isNotEmpty(form.getInstCodeSrch()) ? form.getInstCodeSrch() : null;
        String assetTypeSrch = StringUtils.isNotEmpty(form.getAssetTypeSrch()) ? form.getAssetTypeSrch() : null;
//        String instCodeSrch = StringUtils.isNotEmpty(form.getAssetTypeSrch()) ? strArrray[0] : null;
//        String assetTypeSrch = StringUtils.isNotEmpty(form.getAssetTypeSrch()) ? strArrray[1] : null;
        String borrowPeriodSrch = StringUtils.isNotEmpty(form.getBorrowPeriodSrch()) ? form.getBorrowPeriodSrch() : null;
        String modifyTypeSrch = StringUtils.isNotEmpty(form.getModifyTypeSrch()) ? form.getModifyTypeSrch() : null;
        String userNameSrch = StringUtils.isNotEmpty(form.getUserNameSrch()) ? form.getUserNameSrch() : null;
        String recieveTimeStartSrch = StringUtils.isNotEmpty(form.getRecieveTimeStartSrch()) ? form.getRecieveTimeStartSrch()+" 00:00:00" : null;
        String recieveTimeEndSrch = StringUtils.isNotEmpty(form.getRecieveTimeEndSrch()) ? form.getRecieveTimeEndSrch()+" 23:59:59" : null;
        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("instCodeSrch", instCodeSrch);
        conditionMap.put("assetTypeSrch", assetTypeSrch);
        conditionMap.put("borrowPeriodSrch", borrowPeriodSrch);
        conditionMap.put("modifyTypeSrch", modifyTypeSrch);
        conditionMap.put("userNameSrch", userNameSrch);
        conditionMap.put("recieveTimeStartSrch", recieveTimeStartSrch);
        conditionMap.put("recieveTimeEndSrch", recieveTimeEndSrch);
        conditionMap.put("pageSize",form.getPageSize()==0?10:form.getPageSize());
        conditionMap.put("currPage",form.getCurrPage());
        return conditionMap;
    }

    /**
     * 修改类型
     * @return list
     */
    public List<Map<String,String>> updateTypesList(){

        List list=new ArrayList();
        Integer i = 0;
        for (; i < 4; i++) {
            Map<String,String> map=new HashMap();
            switch (i) {
                case 0:
//				map.put("typeId", i.toString());
//				map.put("name", "全部");
                    break;
                case 1:
                    map.put("typeId", i.toString());
                    map.put("name", "增加");
                    break;
                case 2:
                    map.put("typeId", i.toString());
                    map.put("name", "修改");
                    break;
                case 3:
                    map.put("typeId", i.toString());
                    map.put("name", "删除");
                    break;
                default:
                    break;
            }
            list.add(map);
        }
        return list;



    }
    /**
     * 修改类型
     * @return list
     */
    public Map<String,String> updateTypeList(){
        Map<String,String> map=new HashMap();
        map.put( "增加","1");
        map.put( "修改","2");
        map.put( "删除","3");
        return map;
    }
    //判断修改类型表
    public String accountEsbStates(Integer string) {
//        if (string==0) {
//            return "全部";
//        }
        if (string==1) {
            return "增加";
        }
        if (string==2) {
            return "修改";
        }
        if (string==3) {
            return "删除";
        }
        return null;

    }
    //判断状态
    public String nameStates(Integer string) {
        if (string==0) {
            return "启用";
        }
        if (string==1) {
            return "禁用";
        }
        return null;

    }

}
