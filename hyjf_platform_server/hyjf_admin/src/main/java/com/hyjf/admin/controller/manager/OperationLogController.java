package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.OperationLogRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.OperationLogService;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Api(value = "配置中心操作日志配置")
@RestController
@RequestMapping("/hyjf-admin/config/operationlog")
public class OperationLogController  extends BaseController {



    //权限名称
    private static final String PERMISSIONS = "operationlog";
    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "配置中心操作日志配置", notes = "查询配置中心操作日志配置")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult operationLogConfigInit(@RequestBody OperationLogRequestBean operationLogRequestBean) {
        AdminOperationLogRequest adminRequest= new AdminOperationLogRequest();
        //可以直接使用
        BeanUtils.copyProperties(operationLogRequestBean, adminRequest);
        // 封装查询条件
        Map<String, Object> conditionMap = setCondition(adminRequest);
        AdminOperationLogResponse response =operationLogService.selectOperationLogList(conditionMap,-1,-1);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<FeerateModifyLogVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "配置中心操作日志配置", notes = "查询配置中心操作日志配置")
    @RequestMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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
        response.setUpdateTypes( updateTypeList());
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
    @ApiOperation(value = "配置中心操作日志配置", notes = "导出配置中心操作日志配置")
    @RequestMapping("/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody OperationLogRequestBean operationLogRequestBean) throws Exception {
        AdminOperationLogRequest form= new AdminOperationLogRequest();
        //可以直接使用
        BeanUtils.copyProperties(operationLogRequestBean, form);
        // 表格sheet名称
        String sheetName = "费率操作日志";

        // 封装查询条件
        Map<String, Object> conditionMap = setCondition(form);
        AdminOperationLogResponse operationLogResponseResponse = this.operationLogService.selectOperationLogList(conditionMap,-1,-1);

        List<FeerateModifyLogVO> resultList =  operationLogResponseResponse.getResultList();
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "资产来源", "产品类型", "期限", "自动发标利率", "服务费", "管理费", "收益差率", "逾期利率", "逾期免息天数", "状态", "修改类型", "操作人", "操作时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (resultList != null && resultList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < resultList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    FeerateModifyLogVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 资产来源
                    else if (celLength == 1) {
                        cell.setCellValue(record.getInstName());
                    }
                    // 产品类型
                    else if (celLength == 2) {
                        cell.setCellValue(record.getAssetTypeName());
                    }
                    // 期限
                    else if (celLength == 3) {
                        cell.setCellValue(record.getBorrowPeriod());
                    }
                    // 自动发标利率
                    else if (celLength == 4) {
                        cell.setCellValue(record.getBorrowApr().toString());
                    }
                    // 服务费
                    else if (celLength == 5) {
                        cell.setCellValue(record.getServiceFee());
                    }
                    // 管理费
                    else if (celLength == 6) {
                        cell.setCellValue(record.getManageFee());
                    }
                    // 收益差率
                    else if (celLength == 7) {
                        cell.setCellValue(record.getRevenueDiffRate());
                    }
                    // 逾期利率
                    else if (celLength == 8) {
                        cell.setCellValue(record.getLateInterestRate());
                    }

                    // 逾期免息天数
                    else if (celLength == 9) {
                        cell.setCellValue(record.getLateFreeDays());
                    }
                    // 状态
                    else if (celLength == 10) {
                        cell.setCellValue(nameStates(record.getStatus()));
                    }
                    // 修改类型
                    else if (celLength == 11) {
                        cell.setCellValue(accountEsbStates(record.getModifyType()));
                    }
////                    // 操作人
//                    else if (celLength == 12) {
//                        cell.setCellValue(record.getName());
//                    }
//                    // 操作时间
//                    else if (celLength == 13) {
//                        cell.setCellValue(record.getCreateTimeString());
//                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }



    /**
     * 封装查询条件
     *
     * @param form
     * @return
     */
    private Map<String, Object> setCondition(AdminOperationLogRequest form) {
        String instCodeSrch = StringUtils.isNotEmpty(form.getInstCodeSrch()) ? form.getInstCodeSrch() : null;
        String assetTypeSrch = StringUtils.isNotEmpty(form.getAssetTypeSrch()) ? form.getAssetTypeSrch() : null;
        String borrowPeriodSrch = StringUtils.isNotEmpty(form.getBorrowPeriodSrch()) ? form.getBorrowPeriodSrch() : null;
        String modifyTypeSrch = StringUtils.isNotEmpty(form.getModifyTypeSrch()) ? form.getModifyTypeSrch() : null;
        String userNameSrch = StringUtils.isNotEmpty(form.getUserNameSrch()) ? form.getUserNameSrch() : null;
        String recieveTimeStartSrch = StringUtils.isNotEmpty(form.getRecieveTimeStartSrch()) ? form.getRecieveTimeStartSrch() : null;
        String recieveTimeEndSrch = StringUtils.isNotEmpty(form.getRecieveTimeEndSrch()) ? form.getRecieveTimeEndSrch() : null;

        Map<String, Object> conditionMap = new HashMap<String, Object>();
        conditionMap.put("instCodeSrch", instCodeSrch);
        conditionMap.put("assetTypeSrch", assetTypeSrch);
        conditionMap.put("borrowPeriodSrch", borrowPeriodSrch);
        conditionMap.put("modifyTypeSrch", modifyTypeSrch);
        conditionMap.put("userNameSrch", userNameSrch);
        conditionMap.put("recieveTimeStartSrch", recieveTimeStartSrch);
        conditionMap.put("recieveTimeEndSrch", recieveTimeEndSrch);
        return conditionMap;
    }
    /**
     * 修改类型
     * @return list
     */
    public List<Map<String,String>> updateTypeList(){

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
