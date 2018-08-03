package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.beans.request.FeeConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.FeeConfigService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/18.
 *  @version FeeConfigController, v0.1 2018/7/18.
 *  银行配置 手续费配置
 */
@Api(value = "配置中心银行配置 手续费配置",tags ="配置中心银行配置 手续费配置")
@RestController
@RequestMapping("/hyjf-admin/config/feeconfig")
public class FeeConfigController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "feeconfig";
    @Autowired
    private FeeConfigService feeConfigService;

    @ApiOperation(value = "配置中心银行配置-手续费配置", notes = "查询配置中心手续费配置")
    @RequestMapping("/init")
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
    @ApiOperation(value = "配置中心银行配置-手续费配置", notes = "手续费配置详情页面")
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
            // 设置银行列表
            List<BankConfigVO> bankConfigVOS = feeConfigService.getBankConfigList(new BankConfigVO());
            response.getResult().setBankConfig(bankConfigVOS);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<FeeConfigVO>(response.getResult()) ;
    }
    @ApiOperation(value = "配置中心银行配置-手续费配置", notes = "手续费配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult  insertFeeConfig(@RequestBody FeeConfigRequestBean feeConfigRequestBean){
        AdminFeeConfigResponse response = null;
        AdminFeeConfigRequest request = new AdminFeeConfigRequest();
        BeanUtils.copyProperties(feeConfigRequestBean,request);
        ModelAndView model =new ModelAndView();
        //表单字段校验
        model = this.validatorFieldCheck(model, request);
        if (!model.isEmpty()) {
            return new AdminResult<>(FAIL, "校验失败");
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

    @ApiOperation(value = "配置中心-手续费配置", notes = "手续费配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateFeeConfig(@RequestBody FeeConfigRequestBean feeConfigRequestBean) {
        AdminFeeConfigResponse response = null;
        AdminFeeConfigRequest request = new AdminFeeConfigRequest();
        BeanUtils.copyProperties(feeConfigRequestBean,request);
        ModelAndView model =new ModelAndView();
        //表单字段校验
        model = this.validatorFieldCheck(model, request);
        if (!model.isEmpty()) {
            return new AdminResult<>(FAIL, "校验失败");
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

    @ApiOperation(value = "配置中心-手续费配置", notes = "手续费配置删除")
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
     * @param modelAndView
     * @param form
     * @return
     */
    private ModelAndView validatorFieldCheck(ModelAndView modelAndView, AdminFeeConfigRequest form) {
        // 字段校验(非空判断和长度判断)
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "name", form.getName())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "name", form.getName(), 50, true)) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "personalCredit", form.getPersonalCredit())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "personalCredit", form.getPersonalCredit(), 10,
                true)) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "enterpriseCredit", form.getEnterpriseCredit())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "enterpriseCredit", form.getEnterpriseCredit(), 10,
                true)) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "quickPayment", form.getQuickPayment())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "quickPayment", form.getQuickPayment(), 10,
                true)) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "directTakeout", form.getDirectTakeout())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "directTakeout", form.getDirectTakeout(), 10,
                true)) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "quickTakeout", form.getQuickTakeout())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "quickTakeout", form.getQuickTakeout(), 10,
                true)) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "normalTakeout", form.getNormalTakeout())) {
            return modelAndView;
        }

        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "normalTakeout", form.getNormalTakeout(), 10,
                true)) {
            return modelAndView;
        }

        return null;
    }
}
