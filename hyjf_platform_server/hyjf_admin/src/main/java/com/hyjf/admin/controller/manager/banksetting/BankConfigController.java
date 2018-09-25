package com.hyjf.admin.controller.manager.banksetting;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.BankConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankConfigService;
import com.hyjf.admin.service.MessagePushNoticesService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/11.
 *  @version BankSettingController, v0.1 2018/7/11.
 *  银行配置
 */
@Api(tags = "配置中心-银行配置--银行配置")
@RestController
@RequestMapping("/hyjf-admin/config/bankconfig")
public class BankConfigController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "banksetting";
    @Autowired
    private MessagePushNoticesService messagePushNoticesService;
    @Autowired
    private BankConfigService bankConfigService;

    @ApiOperation(value = "查询配置中心银行配置", notes = "查询配置中心银行配置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult bankConfigInit(@RequestBody BankConfigRequestBean bankConfigRequestBean) {
        AdminBankConfigRequest request = new AdminBankConfigRequest();
       //可以直接使用
        BeanUtils.copyProperties(bankConfigRequestBean, request);
        AdminBankConfigResponse response = bankConfigService.bankConfigInit(request);
        //获取图片路径-----todo
//        String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<BankConfigVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "银行配置详情页面", notes = "银行配置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult  bankConfigInfo(@RequestBody BankConfigRequestBean bankConfigRequestBean) {
        AdminBankConfigResponse response= null ;
        String ids =bankConfigRequestBean.getIds();
        if(StringUtils.isNotBlank(ids)){
            response= bankConfigService.selectBankConfigById(Integer.valueOf(ids));
        }
//        String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<BankConfigVO>(response.getResult()) ;
    }
    @ApiOperation(value = "银行配置添加", notes = "银行配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult  insertBankConfig(@RequestBody BankConfigRequestBean bankConfigRequestBean ,HttpServletRequest res) {
        AdminSystemVO user = getUser(res);
        bankConfigRequestBean.setCreateUserId(Integer.valueOf(user.getId()));
        bankConfigRequestBean.setUpdateUserId(Integer.valueOf(user.getId()));
        AdminBankConfigRequest request = new AdminBankConfigRequest();
        BeanUtils.copyProperties(bankConfigRequestBean,request);
        AdminBankConfigResponse prs =null;
        //表单字段校验
        String message = this.validatorFieldCheck(request);
        if (StringUtils.isNotBlank(message)) {
            return new AdminResult<>(FAIL, message);
        }
        // form.setLogo("1");
        BankConfigVO bank = new BankConfigVO();
        bank.setName(bankConfigRequestBean.getName());
        List<BankConfigVO> banks = bankConfigService.getBankConfigRecordList(bank);
        if (CollectionUtils.isEmpty(banks)) {
            //可以直接使用
            BeanUtils.copyProperties(bankConfigRequestBean, request);
            // 数据插入
            prs = bankConfigService.insertBankConfigRecord(request);
        }else{
            return new AdminResult<>(FAIL,"名称已经存在");
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "修改银行配置", notes = "修改银行配置")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBankConfig(@RequestBody BankConfigRequestBean bankConfigRequestBean,HttpServletRequest res) {
        AdminSystemVO user = getUser(res);
        bankConfigRequestBean.setUpdateUserId(Integer.valueOf(user.getId()));
        AdminBankConfigRequest request = new AdminBankConfigRequest();
        BeanUtils.copyProperties(bankConfigRequestBean,request);
        //表单字段校验
        String message = this.validatorFieldCheck(request);
        if (StringUtils.isNotBlank(message)) {
            return new AdminResult<>(FAIL, message);
        }
        if (null ==request.getId()) {
            return new AdminResult<>(FAIL, "id不能为空！");
        }
        AdminBankConfigResponse prs =bankConfigService.updateBankConfigRecord(request);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "删除银行配置", notes = "删除银行配置")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBankConfig(@RequestBody BankConfigRequestBean bankConfigRequestBean) {
        AdminBankConfigResponse prs =null;
        if(StringUtils.isNotBlank(bankConfigRequestBean.getIds())){
            Integer id=Integer.valueOf(bankConfigRequestBean.getIds());
            prs = bankConfigService.deleteBankConfigById(id);
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "银行配置上传文件", notes = "上传文件")
    @PostMapping("/uploadFile")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult uploadFile(HttpServletRequest request, HttpServletResponse response) {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = messagePushNoticesService.uploadFile(request);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "银行配置去重校验", notes = "去重校验")
    @PostMapping("/validateBeforeAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult validateBeforeAction(@RequestBody BankConfigRequestBean bankConfigRequestBean) {
        AdminBankConfigRequest request = new AdminBankConfigRequest();
        BeanUtils.copyProperties(bankConfigRequestBean,request);
        AdminBankConfigResponse res = bankConfigService.validateBeforeAction(request);
        return new AdminResult<>(res.getMessage());
    }
    /**
     * 画面校验
     *
     * @param form
     */
    private String validatorFieldCheck(AdminBankConfigRequest form) {
        // 字段校验(非空判断和长度判断)
        if(StringUtils.isBlank(form.getName())){
            return "name 不能为空！";
        }
        if(StringUtils.isNotBlank(form.getName())&&form.getName().length() >50){
            return "name 长度不能超过50！";
        }
        if(StringUtils.isBlank(form.getCode())){
            return "code 不能为空！";
        }
        if(StringUtils.isNotBlank(form.getCode())&&form.getCode().length() >10){
            return "code 长度不能超过10！";
        }
       return "";

    }
}
