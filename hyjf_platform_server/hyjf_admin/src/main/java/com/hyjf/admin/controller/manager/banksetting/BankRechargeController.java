package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankRechargeService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankRechargeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRechargeConfigRequest;
import com.hyjf.am.vo.config.BankRechargeLimitConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Api(value = "配置中心银行配置-快捷充值限额", tags = "配置中心银行配置-快捷充值限额")
@RestController
@RequestMapping("/hyjf-admin/config/bankrecharge")
public class BankRechargeController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "bankrecharge";
    @Autowired
    private BankRechargeService bankRechargeService;

    @ApiOperation(value = "查询配置中心快捷充值限额", notes = "查询配置中心快捷充值限额")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult bankRechargeInit(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        AdminBankRechargeConfigResponse response=bankRechargeService.bankRechargeInit(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<BankRechargeLimitConfigVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "查询配置中心快捷充值查询", notes = "查询配置中心快捷充值查询")
    @PostMapping("/getBankRecordList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public List<BankConfigVO> getBankRecordList() {
        //分页查询的时候查询快捷支付银行列表接口
        return bankRechargeService.getBankRecordList();
    }

    @ApiOperation(value = "快捷充值限额详情页面", notes = "快捷充值限额详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult  bankRechargeConfigInfo(@RequestBody AdminBankRechargeConfigRequest adminRequest) {
        AdminBankRechargeConfigResponse response=null;
        if(StringUtils.isNotBlank(adminRequest.getIds())){
            adminRequest.setId(Integer.valueOf(adminRequest.getIds()));
            response=bankRechargeService.selectBankRechargeConfigInfo(adminRequest);
            List<BankConfigVO> bankConfigVOS = bankRechargeService.getBankRecordList();
            if(!CollectionUtils.isEmpty(bankConfigVOS)&&response.getResult() != null){
                //设置银行列表（快捷卡）
                response.getResult().setBankConfigs(bankConfigVOS);
            }
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<BankRechargeLimitConfigVO>(response.getResult()) ;
    }

    @ApiOperation(value = "快捷充值限额添加", notes = "快捷充值限额添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBankRechargeConfig(@RequestBody  AdminBankRechargeConfigRequest adminRequest)  {
        ModelAndView modelAndView = new ModelAndView();
        // 调用校验
        if (validatorFieldCheck(modelAndView, adminRequest) != null) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验字段填写为空或长度太长，不符合要求!");
        }
        AdminBankRechargeConfigResponse prs = bankRechargeService.saveBankRechargeConfig(adminRequest);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "快捷充值限额修改", notes = "快捷充值限额修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBankRechargeConfig(@RequestBody AdminBankRechargeConfigRequest adminRequest)  {
        ModelAndView modelAndView = new ModelAndView();
        // 调用校验
        if (validatorFieldCheck(modelAndView, adminRequest) != null) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验字段填写为空或长度太长，不符合要求 !");
        }
        // // 根据id更新
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "id", String.valueOf(adminRequest.getId()))) {
            return new AdminResult<>(FAIL, "id字段不能为空！");
        }
        AdminBankRechargeConfigResponse prs = bankRechargeService.updateBankRechargeConfig(adminRequest);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "快捷充值限额删除", notes = "快捷充值限额删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBankRechargeConfig(@RequestBody AdminBankRechargeConfigRequest adminRequest)  {
        AdminBankRechargeConfigResponse response=null;
        if(adminRequest.getId() != null){
            response=bankRechargeService.deleteBankRechargeConfig(adminRequest);
        }
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }

    /**
     * 导出功能
     *
     */
    @ApiOperation(value = "快捷充值限额导出", notes = "快捷充值限额导出")
    @PostMapping("/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletResponse response ,@RequestBody AdminBankRechargeConfigRequest adminRequest) throws Exception {
        // 表格sheet名称
        String sheetName = "快捷充值限额配置";
        BankRechargeLimitConfigVO bankRecharge = new BankRechargeLimitConfigVO();
        //列表
        List<BankRechargeLimitConfigVO> resultList  = this.bankRechargeService.exportRecordList(bankRecharge);
        //获取银行列表(快捷支付卡)
        List<BankConfigVO> bankList = bankRechargeService.getBankRecordList();
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] {"序号", "银行", "接入方式","银行卡类型","单笔充值限额（元）","单卡单日累计限额（元）","状态" };
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
                    BankRechargeLimitConfigVO pInfo = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    else if (celLength == 1) {
                        bank: for(int j = 0;j < bankList.size();j++){
                            if(pInfo.getBankId().equals(bankList.get(j).getId())){
                                cell.setCellValue(bankList.get(j).getName());
                                break bank;
                            }
                        }
                    }
                    else if (celLength == 2) {
                        if(pInfo.getAccessCode() == null){
                            cell.setCellValue("");
                        }else if(pInfo.getAccessCode() == 0){
                            cell.setCellValue("全国");
                        }
                    }
                    else if (celLength == 3) {
                        if(pInfo.getBankType() == null){
                            cell.setCellValue("");
                        }else if(pInfo.getBankType() == 0){
                            cell.setCellValue("借记卡");
                        }
                    }
                    else if (celLength == 4) {
                        if(pInfo.getSingleQuota() == null){
                            cell.setCellValue("无限额");
                        }else{
                            cell.setCellValue(String.valueOf(pInfo.getSingleQuota()));
                        }
                    }
                    else if (celLength == 5) {
                        if(pInfo.getSingleCardQuota() == null){
                            cell.setCellValue("无限额");
                        }else{
                            cell.setCellValue(String.valueOf(pInfo.getSingleCardQuota()));
                        }
                    }
                    else if (celLength == 6) {
                        if(pInfo.getStatus() == null){
                            cell.setCellValue("");
                        }else if(pInfo.getStatus() == 0){
                            cell.setCellValue("启用");
                        }else{
                            cell.setCellValue("禁用");
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }

    /**
     * 调用校验表单方法
     *
     * @param modelAndView
     * @param form
     * @return
     */
    private ModelAndView validatorFieldCheck(ModelAndView modelAndView, AdminBankRechargeConfigRequest form) {
        // 字段校验(非空判断和长度判断)
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "bankId", form.getBankId().toString())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "bankId", String.valueOf(form.getBankId()), 11, true)) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "singleQuota", String.valueOf(form.getSingleQuota()))) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "singleQuota", String.valueOf(form.getSingleQuota()), 13, true)) {
            return modelAndView;
        }
        return null;
    }
}
