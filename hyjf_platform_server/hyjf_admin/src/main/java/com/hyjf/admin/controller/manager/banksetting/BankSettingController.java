/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.beans.request.BankSettingRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankSettingService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author dangzw
 * @version BankSettingController, v0.1 2018/7/24 22:16
 */
@Api(value = "配置中心-银行配置 江西银行", tags = "配置中心银-行配置 江西银行")
@RestController
@RequestMapping(value = "/hyjf-admin/config/banksetting")
public class BankSettingController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "banksetting";
    @Autowired
    private BankSettingService bankSettingService;

    @ApiOperation(value = "列表(条件)查询;江西银行的银行卡配置表", httpMethod = "GET", notes = "列表(条件)查询;江西银行的银行卡配置表")
    @GetMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initBankSettingList(@ModelAttribute BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, request);
        AdminBankSettingResponse response = this.bankSettingService.selectBankSettingList(request);
        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<JxBankConfigVO>>(ListResult.build(response.getResultList(), response.getRecordTotal()));
    }

    @ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", httpMethod = "GET", notes = "画面迁移(含有id更新，不含有id添加)")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "根据id查询详情")
    @GetMapping("/info")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult bankSettingInfo(@ModelAttribute BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, request);
        Integer id = request.getId();
        if (id != null) {
            response = this.bankSettingService.getRecord(request);
            //String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response.getResult());
    }

    @ApiOperation(value = "添加一条数据", httpMethod = "POST", notes = "添加一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "添加内容")
    @PostMapping("/insert")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean,request);
        ModelAndView model =new ModelAndView();
        // 调用校验
        if (validatorFieldCheck(model, request) != null) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验失败");
        }
        JxBankConfigVO bank = new JxBankConfigVO();
        bank.setBankName(request.getBankName());
        List<JxBankConfigVO> banks = bankSettingService.getRecordList(bank, -1, -1);
        if (banks.size() == 0) {
            // 数据插入
            response = this.bankSettingService.insertRecord(request);
        }
        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "修改一条数据", httpMethod = "PUT", notes = "修改一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "修改内容和id")
    @PutMapping("/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean,request);
        ModelAndView model =new ModelAndView();
        // 调用校验
        if (validatorFieldCheck(model, request) != null) {
            return new AdminResult<>(FAIL, "校验失败");
        }
        // 根据id更新
        if (!ValidatorFieldCheckUtil.validateRequired(model, "id", request.getId().toString())) {
            return new AdminResult<>(FAIL, "校验失败");
        }
        // 更新
        response = this.bankSettingService.updateRecord(request);
        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "删除一条数据", httpMethod = "DELETE", notes = "删除一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "被删除数据对应的id")
    @DeleteMapping("/delete")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean ,request);
        Integer id = request.getId();
        if(id != null){
            response = this.bankSettingService.deleteRecord(request);
        }
        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "提交内容校验", httpMethod = "POST", notes = "提交内容校验")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "校验内容")
    @PostMapping("/validateBeforeAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public Map<String, Object> validateBeforeAction(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        JxBankConfigVO jxBankConfig = new JxBankConfigVO();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        BeanUtils.copyProperties(bankSettingRequestBean ,jxBankConfig);
        List<JxBankConfigVO> list = bankSettingService.getRecordList(jxBankConfig, -1, -1);
        if (list != null && list.size() != 0) {
            if (bankSettingRequestBean.getId() != null) {
                Boolean hasnot = true;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId().equals(bankSettingRequestBean.getId())) {
                        hasnot = false;
                        break;
                    }
                }
                if (hasnot) {
                    resultMap.put("success", false);
                    resultMap.put("msg", "银行名称或银行代码不可重复添加");
                } else {
                    resultMap.put("success", true);
                }
            } else {
                resultMap.put("success", false);
                resultMap.put("msg", "银行名称或银行代码不可重复添加");
            }
        } else {
            resultMap.put("success", true);
        }
        return resultMap;
    }

    @ApiOperation(value = "资料上传", httpMethod = "POST", notes = "资料上传")
    @PostMapping(value = "/upLoadFile")
    @ResponseBody
    public AdminResult<JxBankConfigVO> upLoadFile(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String s = bankSettingService.uploadFile(request, response);
        if (response != null) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "列表导出", httpMethod = "GET", notes = "列表导出")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "列表导出内容")
    @GetMapping(value = "/exportregist")
    public void exportAction(HttpServletResponse response, @ModelAttribute BankSettingRequestBean bankSettingRequestBean) throws Exception {
        AdminBankSettingRequest requestBean = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, requestBean);
        // 表格sheet名称
        String sheetName = "银行配置";
        JxBankConfigVO bankRecharge = new JxBankConfigVO();
        //列表
        List<JxBankConfigVO> resultList  =this.bankSettingService.getRecordList(new JxBankConfigVO(), -1, -1);
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] {"序号", "银行名称", "银行联行号","银行ICON","LOGO","支持快捷支付","快捷支付单笔限额","快捷充值单日限额","提现手续费"};
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
                    JxBankConfigVO pInfo = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    else if (celLength == 1) {
                        cell.setCellValue(pInfo.getBankName());
                    }
                    else if (celLength == 2) {
                        cell.setCellValue(pInfo.getPayAllianceCode());
                    }
                    else if (celLength == 3) {
                        cell.setCellValue(pInfo.getBankIcon());
                    }
                    else if (celLength == 4) {
                        cell.setCellValue(pInfo.getBankLogo());
                    }
                    else if (celLength == 5) {
                        if(pInfo.getQuickPayment()==1){
                            cell.setCellValue("是");
                        }else{
                            cell.setCellValue("否");
                        }
                    }
                    else if (celLength == 6) {
                        cell.setCellValue(pInfo.getSingleQuota().doubleValue());
                    }
                    else if (celLength == 7) {
                        cell.setCellValue(pInfo.getSingleCardQuota().doubleValue());
                    }
                    else if (celLength == 8) {
                        cell.setCellValue(pInfo.getFeeWithdraw().doubleValue());
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
    private ModelAndView validatorFieldCheck(ModelAndView modelAndView, AdminBankSettingRequest form) {
        // 字段校验(非空判断和长度判断)
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "name", form.getBankName())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "name", form.getBankName(), 50, true)) {
            return modelAndView;
        }
        return null;
    }
}
