package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.beans.request.FeeConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.FeeConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/18.
 *  @version FeeConfigController, v0.1 2018/7/18.
 *  银行配置 手续费配置
 */
@Api(tags = "配置中心-银行配置--手续费配置")
@RestController
@RequestMapping("/hyjf-admin/config/feeconfig")
public class FeeConfigController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "feeconfig";
    @Autowired
    private FeeConfigService feeConfigService;

    @ApiOperation(value = "查询配置中心手续费配置", notes = "查询配置中心手续费配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initFeeConfigList(@RequestBody FeeConfigRequestBean feeConfigRequestBean) {
        AdminFeeConfigRequest request = new AdminFeeConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(feeConfigRequestBean, request);
        AdminFeeConfigResponse response = feeConfigService.selectFeeConfigList(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<FeeConfigVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }
    @ApiOperation(value = "手续费配置详情页面", notes = "手续费配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult  feeConfigInfo(@RequestBody FeeConfigRequestBean feeConfigRequestBean) {
        AdminFeeConfigResponse response = null;
        AdminFeeConfigRequest request = new AdminFeeConfigRequest();
        if (StringUtils.isNotEmpty(feeConfigRequestBean.getIds())) {
            Integer id = Integer.valueOf(feeConfigRequestBean.getIds());
            request.setId(id);
            //手续费配置详情查詢
            response = this.feeConfigService.selectFeeConfigInfo(request);
        }else{
            response=new AdminFeeConfigResponse();
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        // 设置银行列表
        List<BankConfigVO> bankConfigVOS = feeConfigService.getBankConfigList(new BankConfigVO());
        response.setBankConfig(bankConfigVOS);
        return new AdminResult<AdminFeeConfigResponse>(response) ;
    }
    @ApiOperation(value = "手续费配置添加", notes = "手续费配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult  insertFeeConfig(@RequestBody FeeConfigRequestBean feeConfigRequestBean){
        AdminFeeConfigResponse response = null;
        AdminFeeConfigRequest request = new AdminFeeConfigRequest();
        BeanUtils.copyProperties(feeConfigRequestBean,request);
        //表单字段校验
        String message = this.validatorFieldCheck(request);
        if (StringUtils.isNotBlank(message)) {
            return new AdminResult<>(FAIL, message);
        }
        BankConfigVO bank = new BankConfigVO();
        // 设置银行列表
        request.setBankConfigList(feeConfigService.getBankConfigList(bank));
        bank.setName(request.getName());
        // 获取手续费列表列表
        List<BankConfigVO> banks = feeConfigService.getBankConfigRecordList(bank,-1,-1);
        request.setBankCode(banks.get(0).getCode());
         // 数据插入
        response = feeConfigService.insertBankConfigRecord(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "手续费配置修改", notes = "手续费配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateFeeConfig(@RequestBody FeeConfigRequestBean feeConfigRequestBean) {
        AdminFeeConfigResponse response = null;
        AdminFeeConfigRequest request = new AdminFeeConfigRequest();
        BeanUtils.copyProperties(feeConfigRequestBean,request);
        //表单字段校验
        String message = this.validatorFieldCheck( request);
        if (StringUtils.isNotBlank(message)) {
            return new AdminResult<>(FAIL, message);
        }
        BankConfigVO bank = new BankConfigVO();
        // 设置银行列表
        request.setBankConfigList(feeConfigService.getBankConfigList(bank));
        bank.setName(request.getName());
        // 获取手续费列表列表
        List<BankConfigVO> banks = feeConfigService.getBankConfigRecordList(bank,-1,-1);
        request.setBankCode(banks.get(0).getCode());
        // 数据插入
        response = feeConfigService.updateBankConfigRecord(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "手续费配置删除", notes = "手续费配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteFeeConfig(@RequestBody FeeConfigRequestBean feeConfigRequestBean) {
        AdminFeeConfigResponse response = null;
        AdminFeeConfigRequest request = new AdminFeeConfigRequest();
        if(StringUtils.isNotBlank(feeConfigRequestBean.getIds())){
            request.setId(Integer.valueOf(feeConfigRequestBean.getIds()));
            response = feeConfigService.deleteFeeConfig(request);
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
     * 调用校验表单方法
     *
     * @param form
     * @return
     */
    private String validatorFieldCheck(AdminFeeConfigRequest form) {
        // 字段校验(非空判断和长度判断)
        if (StringUtils.isBlank(form.getName())||(StringUtils.isNotBlank(form.getName())&&form.getName().length()>50)) {
            return "name不能为空且长度不能超过50！";
        }
        if (StringUtils.isBlank(form.getPersonalCredit())||(StringUtils.isNotBlank(form.getPersonalCredit())&&form.getPersonalCredit().length()>10)) {
            return "personalCredit不能为空且长度不能超过10！";
        }
        if (StringUtils.isBlank(form.getEnterpriseCredit())||(StringUtils.isNotBlank(form.getEnterpriseCredit())&&form.getEnterpriseCredit().length()>10)) {
            return "enterpriseCredit不能为空且长度不能超过10！";
        }
        if (StringUtils.isBlank(form.getQuickPayment())||(StringUtils.isNotBlank(form.getQuickPayment())&&form.getQuickPayment().length()>10)) {
            return "quickPayment不能为空且长度不能超过10！";
        }
        if (StringUtils.isBlank(form.getDirectTakeout())||(StringUtils.isNotBlank(form.getDirectTakeout())&&form.getDirectTakeout().length()>10)) {
            return "directTakeout不能为空且长度不能超过10！";
        }
        if (StringUtils.isBlank(form.getQuickTakeout())||(StringUtils.isNotBlank(form.getQuickTakeout())&&form.getQuickTakeout().length()>10)) {
            return "quickTakeout不能为空且长度不能超过10！";
        }
        if (StringUtils.isBlank(form.getNormalTakeout())||(StringUtils.isNotBlank(form.getNormalTakeout())&&form.getNormalTakeout().length()>10)) {
            return "normalTakeout不能为空且长度不能超过10！";
        }
        return "";
    }
}
