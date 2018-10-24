package com.hyjf.admin.controller.manager.banksetting;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankRechargeService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.IntegerResponse;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Api(tags = "配置中心-银行配置--快捷充值限额")
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
        }else{
            response=new AdminBankRechargeConfigResponse();
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<BankConfigVO> bankConfigVOS = bankRechargeService.getBankRecordList();
        if(!CollectionUtils.isEmpty(bankConfigVOS)){
            //设置银行列表（快捷卡）
            response.setBankConfigs(bankConfigVOS);
        }
        return new AdminResult<AdminBankRechargeConfigResponse>(response) ;
    }

    @ApiOperation(value = "快捷充值限额添加", notes = "快捷充值限额添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBankRechargeConfig(@RequestBody  AdminBankRechargeConfigRequest adminRequest)  {
        ModelAndView modelAndView = new ModelAndView();
        // 调用校验
        if (StringUtils.isNotBlank(validatorFieldCheck(adminRequest))) {
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
        if (StringUtils.isNotBlank(validatorFieldCheck(adminRequest))) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验字段填写为空或长度太长，不符合要求 !");
        }
        // // 根据id更新
        if (StringUtils.isBlank(String.valueOf(adminRequest.getId()))) {
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

    @ApiOperation(value = "快捷充值限额校验", notes = "快捷充值限额校验")
    @PostMapping("/checkAction")
    public AdminResult checkAction(@RequestBody AdminBankRechargeConfigRequest adminRequest)  {
        String message="";
        // // 检查银行卡是否重复
        IntegerResponse response = this.bankRechargeService.bankIsExists(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        if (response.getResultInt()  > 0) {
            message= "银行已经存在，请重新输入。";
        }
        // 没有错误时,返回y
        if (StringUtils.isBlank(message)) {
            message= "ok";
        }
        return new AdminResult<String>(message) ;
    }

    /**
     * 导出功能
     *
     */
    @ApiOperation(value = "快捷充值限额导出", notes = "快捷充值限额导出")
    @GetMapping("/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody AdminBankRechargeConfigRequest adminRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "快捷充值限额配置";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        adminRequest.setCurrPage(-1);
        adminRequest.setPageSize(defaultRowMaxCount);
        AdminBankRechargeConfigResponse configResponse = bankRechargeService.bankRechargeInit(adminRequest);
        Integer totalCount = configResponse.getRecordTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        //获取银行列表(快捷支付卡)
        List<BankConfigVO> bankList = bankRechargeService.getBankRecordList();

        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter(bankList);
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            adminRequest.setPageSize(defaultRowMaxCount);
            adminRequest.setCurrPage(i);
            AdminBankRechargeConfigResponse rechargeConfigResponse = bankRechargeService.bankRechargeInit(adminRequest);
            List<BankRechargeLimitConfigVO> rechargeLimitConfigVOList = rechargeConfigResponse.getResultList();
            if (rechargeLimitConfigVOList != null && rechargeLimitConfigVOList.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  rechargeLimitConfigVOList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }
    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("bankId", "银行");
        map.put("accessCode", "接入方式");
        map.put("bankType", "银行卡类型");
        map.put("singleQuota", "单笔充值限额（元）");
        map.put("singleCardQuota", "单卡单日累计限额（元）");
        map.put("status", "状态");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter(List<BankConfigVO> bankList) {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter bankIdAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer bankId = (Integer) object;
                for (BankConfigVO bankConfigVO : bankList) {
                    logger.info("bankId:[{}]",bankId);
                    if (bankId.equals(bankConfigVO.getId())) {
                        logger.info("id:[{}],name:[{}]",bankConfigVO.getId(),bankConfigVO.getName());
                        return bankConfigVO.getName();
                    }
                }
                return "";
            }
        };
        IValueFormatter accessCodeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer accessCode = (Integer) object;
                if(accessCode == null){
                    return "";
                }else if(accessCode == 0){
                    return "全国";
                }
                return "";
            }
        };
        IValueFormatter bankTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer bankType = (Integer) object;
                if(bankType == null){
                    return "";
                }else if(bankType == 0){
                    return "借记卡";
                }
                return "";
            }
        };
        IValueFormatter singleQuotaAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal singleQuota = (BigDecimal) object;
                if(singleQuota == null){
                    return "无限额";
                }else{
                    return String.valueOf(singleQuota);
                }
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer status = (Integer) object;
                if(status == null){
                    return "";
                }else if(status == 0){
                    return "启用";
                }else{
                    return "禁用";
                }
            }
        };

        mapAdapter.put("bankId", bankIdAdapter);
        mapAdapter.put("accessCode", accessCodeAdapter);
        mapAdapter.put("bankType", bankTypeAdapter);
        mapAdapter.put("singleQuota", singleQuotaAdapter);
        mapAdapter.put("singleCardQuota", singleQuotaAdapter);
        mapAdapter.put("status", statusAdapter);
        return mapAdapter;
    }


    /**
     * 调用校验表单方法
     *
     * @param form
     * @return
     */
    private String validatorFieldCheck(AdminBankRechargeConfigRequest form) {
        // 字段校验(非空判断和长度判断)
        if (null == form.getBankId()||(StringUtils.isNotBlank(String.valueOf(form.getBankId()))&&String.valueOf(form.getBankId()).length()>11)) {
            return "银行不能为空且长度不能超过11位！";
        }
        if (null == form.getSingleQuota()||(StringUtils.isNotBlank(String.valueOf(form.getSingleQuota()))&&String.valueOf(form.getSingleQuota()).length()>13)) {
            return "单笔充值限额不能为空且长度不能超过13位！";
        }
        return "";
    }
}
