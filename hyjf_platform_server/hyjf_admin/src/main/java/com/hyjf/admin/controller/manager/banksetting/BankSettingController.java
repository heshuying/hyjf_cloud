/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.beans.request.BankSettingRequestBean;
import com.hyjf.admin.common.result.AdminResult;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author dangzw
 * @version BankSettingController, v0.1 2018/7/24 22:16
 */
@Api(tags = "配置中心-银行配置 江西银行")
@RestController
@RequestMapping(value = "/hyjf-admin/config/banksetting")
public class BankSettingController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BankSettingController.class);
    //权限名称
    private static final String PERMISSIONS = "banksetting";

    @Autowired
    private BankSettingService bankSettingService;

    @Value("${file.domain.url}")
    private String DOMAIN_URL;

    @ApiOperation(value = "列表(条件)查询;江西银行的银行卡配置表", httpMethod = "GET", notes = "列表(条件)查询;江西银行的银行卡配置表")
    @PostMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initBankSettingList(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/list");
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, request);
        AdminBankSettingResponse response = this.bankSettingService.selectBankSettingList(request);
        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/list");
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", httpMethod = "GET", notes = "画面迁移(含有id更新，不含有id添加)")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "根据id查询详情")
    @PostMapping("/info")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult bankSettingInfo(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/info");
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        AdminBankSettingResponse response = new AdminBankSettingResponse();
        BeanUtils.copyProperties(bankSettingRequestBean, request);
        Integer id = request.getId();
        if (id != null) {
            response = this.bankSettingService.getRecord(request);
            response.setFileDomainUrl(DOMAIN_URL);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/info");
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "添加一条数据", httpMethod = "POST", notes = "添加一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "添加内容")
    @PostMapping("/insert")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/insert");
        AdminBankSettingResponse response = null;
        if (bankSettingRequestBean.getBankName() == null || bankSettingRequestBean.getBankName().equals("")){
            // 失败返回
            return new AdminResult<>(FAIL, "请求参数bankName不能为空！");
        }
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
        if (CollectionUtils.isEmpty(banks)) {
            try {
                // 数据插入
                response = this.bankSettingService.insertRecord(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(response == null) {
            return new AdminResult<>(FAIL, "银行名称重复");
        }
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/insert");
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改一条数据", httpMethod = "PUT", notes = "修改一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "修改内容和id")
    @PostMapping("/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/update");
        if (bankSettingRequestBean.getId() == null){
            // 失败返回
            return new AdminResult<>(FAIL, "修改id不能为空！");
        }
        if (bankSettingRequestBean.getBankName() == null || bankSettingRequestBean.getBankName().equals("")){
            // 失败返回
            return new AdminResult<>(FAIL, "请求参数bankName不能为空！");
        }
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
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/update");
        return new AdminResult<>();
    }

    @ApiOperation(value = "删除一条数据", httpMethod = "DELETE", notes = "删除一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "被删除数据对应的id")
    @PostMapping("/delete")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/delete");
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
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/delete");
        return new AdminResult<>();
    }

    @ApiOperation(value = "保存之前的去重验证", httpMethod = "POST", notes = "保存之前的去重验证")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "被校验信息")
    @PostMapping("/validateBeforeAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult validateBeforeAction(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/validateBeforeAction");
        JxBankConfigVO jxBankConfig = new JxBankConfigVO();
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
                    return new AdminResult<>(FAIL, "被修改对象id不存在");
                }
            } else {
                return new AdminResult<>(FAIL, "银行名称或银行代码不可重复添加");
            }
        }
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/validateBeforeAction");
        return new AdminResult<>();
    }

    @ApiOperation(value = "资料上传", httpMethod = "POST", notes = "资料上传")
    @PostMapping(value = "/upLoadFile")
    @ResponseBody
    public AdminResult upLoadFile(HttpServletResponse response, HttpServletRequest request) throws Exception {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/upLoadFile");
        String files = bankSettingService.uploadFile(request, response);
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/upLoadFile");
        if (StringUtils.isNotBlank(files)) {
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "列表导出", httpMethod = "GET", notes = "列表导出")
    @PostMapping(value = "/exportregist")
    public void exportAction(HttpServletResponse response) throws Exception {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/exportregist");
        // 表格sheet名称
        String sheetName = "银行配置";
        JxBankConfigVO bankRecharge = new JxBankConfigVO();
        //列表
        List<JxBankConfigVO> resultList  =this.bankSettingService.getRecordList(new JxBankConfigVO(), -1, -1);
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
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
        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/exportregist");
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
