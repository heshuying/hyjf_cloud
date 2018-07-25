/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.beans.request.BankConfigRequestBean;
import com.hyjf.admin.beans.request.BankSettingRequestBean;
import com.hyjf.admin.beans.request.FeeConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankSettingService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.file.UploadFileUtils;
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
 * @author dangzw
 * @version BankSettingController, v0.1 2018/7/24 22:16
 */
@Api(value = "配置中心银行配置 江西银行", description = "配置中心银行配置 江西银行")
@RestController
@RequestMapping("/hyjf-admin/config/banksetting")
public class BankSettingController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "banksetting";
    @Autowired
    private BankSettingService bankSettingService;

    @ApiOperation(value = "配置中心银行配置 江西银行", notes = "(条件)列表查询")
    @RequestMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initBankSettingList(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, request);
        AdminBankSettingResponse response = this.bankSettingService.selectBankSettingList(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<JxBankConfigVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "配置中心银行配置 江西银行", notes = "画面迁移(含有id更新，不含有id添加)")
    @PostMapping("/info")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult bankSettingInfo(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingResponse response= null ;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean, request);
        String ids =bankSettingRequestBean.getIds();
        if (StringUtils.isNotEmpty(bankSettingRequestBean.getIds())) {
            Integer id = Integer.valueOf(bankSettingRequestBean.getIds());
            response = this.bankSettingService.getRecord(request);
            //String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<JxBankConfigVO>(response.getResult()) ;
    }

    @ApiOperation(value = "配置中心银行配置 江西银行", notes = "江西银行添加")
    @PostMapping("/insert")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBankSetting(@RequestBody BankSettingRequestBean bankSettingRequestBean) {
        AdminBankSettingResponse response = null;
        AdminBankSettingRequest request = new AdminBankSettingRequest();
        BeanUtils.copyProperties(bankSettingRequestBean,request);
        ModelAndView model =new ModelAndView();
        // 调用校验
        if (validatorFieldCheck(model, bankSettingRequestBean) != null) {
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
    private ModelAndView validatorFieldCheck(ModelAndView modelAndView, BankSettingRequestBean form) {
        // 字段校验(非空判断和长度判断)
        if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "name", form.getBankName())) {
            return modelAndView;
        }
        if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "name", form.getBankName(), 50, true)) {
            return modelAndView;
        }
//		if (!ValidatorFieldCheckUtil.validateRequired(modelAndView, "code", form.getBankCode())) {
//			return modelAndView;
//		}
//		if (!ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "code", form.getBankCode(), 10, true)) {
//			return modelAndView;
//		}
        return null;
    }
}
