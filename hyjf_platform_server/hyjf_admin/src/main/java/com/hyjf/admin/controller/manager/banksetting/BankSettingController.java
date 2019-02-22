/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.manager.banksetting;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.BankSettingRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankSettingService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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

    @ApiOperation(value = "列表(条件)查询;江西银行的银行卡配置表", httpMethod = "POST", notes = "列表(条件)查询;江西银行的银行卡配置表")
    @PostMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initBankSettingList(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/list");

        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, request);

        try {
            // 数据查询
            response = this.bankSettingService.selectBankSettingList(request);
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", request.toString(), e);
            return new AdminResult<>(FAIL, "Admin江西银行数据查询异常！具体原因详见日志");
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/list");
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "画面迁移(含有id更新，不含有id添加)", httpMethod = "POST", notes = "画面迁移(含有id更新，不含有id添加)")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "根据id查询详情")
    @PostMapping("/info")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult bankSettingInfo(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/info");

        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, request);
        if (request.getId() == null) {
            return new AdminResult<>(FAIL, "id字段为必传！");
        }

        try {
            // 数据查询
            response = this.bankSettingService.getRecord(request);
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", request.toString(), e);
            return new AdminResult<>(FAIL, "Admin江西银行数据查询异常！具体原因详见日志");
        }

        response.setFileDomainUrl(DOMAIN_URL);

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
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
        if (StringUtils.isBlank(bankSettingRequestBean.getBankName())){
            return new AdminResult<>(FAIL, "请求参数bankName不能为空！");
        }

        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean,request);
        ModelAndView model =new ModelAndView();

        // 调用校验
        if (validatorFieldCheck(model, request) != null) {
            return new AdminResult<>(FAIL, "表单校验失败");
        }

        JxBankConfigVO bank = new JxBankConfigVO();
        bank.setBankName(request.getBankName());

        List<JxBankConfigVO> banks = null;
        try {
            // 数据查询
            banks = bankSettingService.getRecordList(bank, -1, -1);
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", bank.toString(), e);
            return new AdminResult<>(FAIL, "Admin江西银行数据查询异常！具体原因详见日志");
        }

        if (CollectionUtils.isEmpty(banks)) {
            try {
                // 数据插入
                response = this.bankSettingService.insertRecord(request);
            } catch (Exception e) {
                logger.info("Admin江西银行数据插入异常！requestParam:{}", request.toString(), e);
                return new AdminResult<>(FAIL, "Admin江西银行数据插入异常！具体原因详见日志");
            }
        }

        if(response == null) {
            return new AdminResult<>(FAIL, "银行名称重复");
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/insert");
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改一条数据", httpMethod = "POST", notes = "修改一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "修改内容和id")
    @PostMapping("/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/update");

        if (bankSettingRequestBean.getId() == null){
            return new AdminResult<>(FAIL, "id字段为必传！");
        }
        if (StringUtils.isBlank(bankSettingRequestBean.getBankName())){
            return new AdminResult<>(FAIL, "请求参数bankName不能为空！");
        }

        AdminBankSettingResponse response = null;
        ModelAndView model = new ModelAndView();
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean,request);

        // 调用校验
        if (validatorFieldCheck(model, request) != null) {
            return new AdminResult<>(FAIL, "表单校验失败");
        }
        // 根据id更新
        if (!ValidatorFieldCheckUtil.validateRequired(model, "id", request.getId().toString())) {
            return new AdminResult<>(FAIL, "id字段校验失败");
        }

        try {
            // 数据修改
            response = this.bankSettingService.updateRecord(request);
        } catch (Exception e) {
            logger.info("Admin江西银行数据修改异常！requestParam:{}", request.toString(), e);
            return new AdminResult<>(FAIL, "Admin江西银行数据修改异常！具体原因详见日志");
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info(BankSettingController.class.toString(), "endLog -- /hyjf-admin/config/banksetting/update");
        return new AdminResult<>();
    }

    @ApiOperation(value = "删除一条数据", httpMethod = "POST", notes = "删除一条数据")
    @ApiParam(required = true, name = "bankSettingRequestBean", value = "被删除数据对应的id")
    @PostMapping("/delete")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        logger.info(BankSettingController.class.toString(), "startLog -- /hyjf-admin/config/banksetting/delete");

        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean ,request);
        if(request.getId() == null){
            return new AdminResult<>(FAIL, "id字段为必传！");
        }

        try {
            // 数据删除
            response = this.bankSettingService.deleteRecord(request);
        } catch (Exception e) {
            logger.info("Admin江西银行数据删除异常！requestParam:{}", request.toString(), e);
            return new AdminResult<>(FAIL, "Admin江西银行数据删除异常！具体原因详见日志");
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
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

        List<JxBankConfigVO> list = null;
        JxBankConfigVO jxBankConfig = new JxBankConfigVO();
        BeanUtils.copyProperties(bankSettingRequestBean ,jxBankConfig);

        try {
            // 数据查询
            list = bankSettingService.getRecordList(jxBankConfig, -1, -1);
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", jxBankConfig.toString(), e);
            return new AdminResult<>(FAIL, "Admin江西银行数据查询异常！具体原因详见日志");
        }

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

    @Autowired
    private FileUpLoadUtil fileUpLoadUtil;

    @ApiOperation(value = "资料上传", httpMethod = "POST", notes = "资料上传")
    @PostMapping(value = "/upLoadFile")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            adminResult.setData(fileUpLoadUtil.upLoad(request));
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "列表导出", httpMethod = "POST", notes = "列表导出")
    @PostMapping(value = "/exportregist")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody  AdminBankSettingRequest adminRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "银行配置";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        adminRequest.setCurrPage(-1);
        adminRequest.setPageSize(defaultRowMaxCount);
        AdminBankSettingResponse settingResponse = this.bankSettingService.selectBankSettingList(adminRequest);
        Integer totalCount = settingResponse.getRecordTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;

        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            adminRequest.setPageSize(defaultRowMaxCount);
            adminRequest.setCurrPage(i);
            AdminBankSettingResponse bankSettingResponse = this.bankSettingService.selectBankSettingList(adminRequest);
            List<JxBankConfigVO> bankConfigVOList = bankSettingResponse.getResultList();
            for(JxBankConfigVO vo : bankConfigVOList){
                if(vo.getQuickPayment() == 1){

                    if(vo.getMonthCardQuota().compareTo(BigDecimal.ZERO) == 0){
                        vo.setMonthCardQuotaStr("无限");
                    }else{
                        vo.setMonthCardQuotaStr(String.valueOf(vo.getMonthCardQuota()));
                    }
                }else{
                    vo.setMonthCardQuotaStr("0.00");
                }
            }
            if (bankConfigVOList != null && bankConfigVOList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  bankConfigVOList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("bankName", "银行名称");
        map.put("payAllianceCode", "银行联行号");
        map.put("bankIcon", "银行ICON");
        map.put("bankLogo", "LOGO");
        map.put("quickPayment", "支持快捷支付");
        map.put("singleQuota", "快捷充值单笔限额");
        map.put("singleCardQuota", "快捷充值单日限额");
        map.put("monthCardQuotaStr", "快捷充值单月限额");
        map.put("feeWithdraw", "提现手续费");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();

        IValueFormatter quickPaymentAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer quickPayment = (Integer) object;
                if(quickPayment==1){
                    return "是";
                }else{
                    return "否";
                }
            }

        };

        IValueFormatter bigDecimalAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal number = (BigDecimal) object;
                return String.valueOf(number);
            }
        };

        mapAdapter.put("quickPayment", quickPaymentAdapter);
        mapAdapter.put("singleQuota", bigDecimalAdapter);
        mapAdapter.put("singleCardQuota", bigDecimalAdapter);
        mapAdapter.put("feeWithdraw", bigDecimalAdapter);
//        mapAdapter.put("monthCardQuota", monthCardQuotaAdapter);
        return mapAdapter;
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
